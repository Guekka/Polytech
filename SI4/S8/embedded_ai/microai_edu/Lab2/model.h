#define SINGLE_FILE
/**
  ******************************************************************************
  * @file    number.hh
  * @author  Pierre-Emmanuel Novac <penovac@unice.fr>, LEAT, CNRS, Université Côte d'Azur, France
  * @version 1.0.0
  * @date    2 february 2021
  * @brief   Template generating plain C code for the implementation of Convolutional Neural Networks on MCU
  */

#ifndef __NUMBER_H__
#define __NUMBER_H__

#include <stdint.h>

#define FIXED_POINT	9	// Fixed point scaling factor, set to 0 when using floating point
#define NUMBER_MIN	-32768	// Max value for this numeric type
#define NUMBER_MAX	32767	// Min value for this numeric type
typedef int16_t number_t;		// Standard size numeric type used for weights and activations
typedef int32_t long_number_t;	// Long numeric type used for intermediate results

#ifndef min
static inline long_number_t min(long_number_t a, long_number_t b) {
	if (a <= b)
		return a;
	return b;
}
#endif

#ifndef max
static inline long_number_t max(long_number_t a, long_number_t b) {
	if (a >= b)
		return a;
	return b;
}
#endif

#if FIXED_POINT > 0 // Scaling/clamping for fixed-point representation
static inline long_number_t scale_number_t(long_number_t number) {
	return number >> FIXED_POINT;
}
static inline number_t clamp_to_number_t(long_number_t number) {
	return (number_t) max(NUMBER_MIN, min(NUMBER_MAX, number));
}
#else // No scaling/clamping required for floating-point
static inline long_number_t scale_number_t(long_number_t number) {
	return number;
}
static inline number_t clamp_to_number_t(long_number_t number) {
	return (number_t) number;
}
#endif


#endif //__NUMBER_H__
/**
  ******************************************************************************
  * @file    conv.cc
  * @author  Pierre-Emmanuel Novac <penovac@unice.fr>, LEAT, CNRS, Université Côte d'Azur, France
  * @version 1.0.0
  * @date    24 march 2020
  * @brief   Template generating plain C code for the implementation of Convolutional Neural Networks on MCU
  */

#ifndef SINGLE_FILE
#include "number.h"
#endif

#define INPUT_CHANNELS      9
#define INPUT_SAMPLES       128
#define CONV_FILTERS        2
#define CONV_KERNEL_SIZE    3
#define CONV_STRIDE         1

#define ZEROPADDING_LEFT    0
#define ZEROPADDING_RIGHT   0

#define CONV_OUTSAMPLES     ( ( (INPUT_SAMPLES - CONV_KERNEL_SIZE + ZEROPADDING_LEFT + ZEROPADDING_RIGHT) / CONV_STRIDE ) + 1 )

#define ACTIVATION_RELU

typedef number_t conv1d_output_type[CONV_FILTERS][CONV_OUTSAMPLES];

static inline void conv1d(
  const number_t input[INPUT_CHANNELS][INPUT_SAMPLES],               // IN
  const number_t kernel[CONV_FILTERS][INPUT_CHANNELS][CONV_KERNEL_SIZE], // IN

  const number_t bias[CONV_FILTERS],						                // IN

  number_t output[CONV_FILTERS][CONV_OUTSAMPLES]) {               // OUT

  unsigned short pos_x, z, k; 	// loop indexes for output volume
  unsigned short x;
  short input_x;
  long_number_t	kernel_mac;
  static long_number_t	output_acc[CONV_OUTSAMPLES];
  long_number_t tmp;

  for (k = 0; k < CONV_FILTERS; k++) { 
    for (pos_x = 0; pos_x < CONV_OUTSAMPLES; pos_x++) { 
      output_acc[pos_x] = 0;
	    for (z = 0; z < INPUT_CHANNELS; z++) {

        kernel_mac = 0; 
        for (x = 0; x < CONV_KERNEL_SIZE; x++) {
          input_x = pos_x * CONV_STRIDE - ZEROPADDING_LEFT + x;
          if (input_x < 0 || input_x >= INPUT_SAMPLES) // ZeroPadding1D
            tmp = 0;
          else
            tmp = input[z][input_x] * kernel[k][z][x]; 
          kernel_mac = kernel_mac + tmp; 
        }

	      output_acc[pos_x] = output_acc[pos_x] + kernel_mac; 
      }
      output_acc[pos_x] = scale_number_t(output_acc[pos_x]);

      output_acc[pos_x] = output_acc[pos_x] + bias[k]; 

    }

    for (pos_x = 0; pos_x < CONV_OUTSAMPLES; pos_x++) {
#ifdef ACTIVATION_LINEAR
      output[k][pos_x] = clamp_to_number_t(output_acc[pos_x]);
#elif defined(ACTIVATION_RELU)
      // Activation function: ReLU
      if (output_acc[pos_x] < 0)
        output[k][pos_x] = 0;
      else
        output[k][pos_x] = clamp_to_number_t(output_acc[pos_x]);
#endif
    }
  }
}

#undef INPUT_CHANNELS
#undef INPUT_SAMPLES
#undef CONV_FILTERS
#undef CONV_KERNEL_SIZE
#undef CONV_STRIDE
#undef ZEROPADDING_LEFT
#undef ZEROPADDING_RIGHT
#undef CONV_OUTSAMPLES
#undef ACTIVATION_RELU
/**
  ******************************************************************************
  * @file    weights/conv.cc
  * @author  Pierre-Emmanuel Novac <penovac@unice.fr>, LEAT, CNRS, Université Côte d'Azur, France
  * @version 1.0.0
  * @date    24 march 2020
  * @brief   Template generating plain C code for the implementation of Convolutional Neural Networks on MCU
  */

#define INPUT_CHANNELS    9
#define CONV_FILTERS      2
#define CONV_KERNEL_SIZE  3


const int16_t conv1d_bias[CONV_FILTERS] = {-17, -211}
;

const int16_t conv1d_kernel[CONV_FILTERS][INPUT_CHANNELS][CONV_KERNEL_SIZE] = {{{-426, 266, 46}
, {-38, -77, 67}
, {173, 123, -194}
, {324, -172, 0}
, {276, -80, 148}
, {74, -381, 274}
, {126, -361, 261}
, {30, -173, 324}
, {124, -252, 212}
}
, {{-58, -306, -39}
, {-97, -191, 41}
, {-50, 15, -83}
, {-188, -99, -63}
, {127, -52, 17}
, {-426, -293, -84}
, {-246, -338, 11}
, {-268, -154, -43}
, {-282, -158, 186}
}
}
;

#undef INPUT_CHANNELS
#undef CONV_FILTERS
#undef CONV_KERNEL_SIZE
/**
  ******************************************************************************
  * @file    flatten.cc
  * @author  Pierre-Emmanuel Novac <penovac@unice.fr>, LEAT, CNRS, Université Côte d'Azur, France
  * @version 1.0.0
  * @date    24 march 2020
  * @brief   Template generating plain C code for the implementation of Convolutional Neural Networks on MCU
  */

#ifndef SINGLE_FILE
#include "number.h"
#endif

#define INPUT_DIM [126][2]
#define OUTPUT_DIM 252

//typedef number_t *flatten_output_type;
typedef number_t flatten_output_type[OUTPUT_DIM];

#define flatten //noop (IN, OUT)  OUT = (number_t*)IN

#undef INPUT_DIM
#undef OUTPUT_DIM

/**
  ******************************************************************************
  * @file    fc.cc
  * @author  Pierre-Emmanuel Novac <penovac@unice.fr>, LEAT, CNRS, Université Côte d'Azur, France
  * @version 1.0.0
  * @date    24 march 2020
  * @brief   Template generating plain C code for the implementation of Convolutional Neural Networks on MCU
  */

#ifndef SINGLE_FILE
#include "number.h"
#endif

#define INPUT_SAMPLES 252
#define FC_UNITS 6
#define ACTIVATION_LINEAR

typedef number_t dense_output_type[FC_UNITS];

static inline void dense(
  const number_t input[INPUT_SAMPLES], 			      // IN
	const number_t kernel[FC_UNITS][INPUT_SAMPLES],  // IN

	const number_t bias[FC_UNITS],			              // IN

	number_t output[FC_UNITS]) {			                // OUT

  //TODO: Fill algorithm for Fully Connected layer computation
}

#undef INPUT_SAMPLES
#undef FC_UNITS
#undef ACTIVATION_LINEAR
/**
  ******************************************************************************
  * @file    weights/fc.cc
  * @author  Pierre-Emmanuel Novac <penovac@unice.fr>, LEAT, CNRS, Université Côte d'Azur, France
  * @version 1.0.0
  * @date    24 march 2020
  * @brief   Template generating plain C code for the implementation of Convolutional Neural Networks on MCU
  */

#define INPUT_SAMPLES 252
#define FC_UNITS 6


const int16_t dense_bias[FC_UNITS] = {392, 40, -1047, 279, 142, 317}
;

const int16_t dense_kernel[FC_UNITS][INPUT_SAMPLES] = {{-335, -41, -237, 63, -334, -265, -419, -392, -87, -136, -26, -248, -155, -286, 53, -130, -39, 25, -100, 401, 169, 307, 293, 225, 397, 24, 181, 91, 49, 30, -173, -48, 436, 326, 355, 183, 192, -59, 249, 336, 213, 293, 74, 113, 130, -25, 109, 31, -15, 42, 89, 326, 94, 167, 146, -67, -44, 239, 205, 202, 69, 215, 156, 269, 89, 267, 271, 299, 210, 102, 16, 291, 506, 398, 265, 267, 574, 384, 436, 620, 512, 311, 593, 81, -541, -148, -41, 48, -47, -103, 37, -175, -112, -105, -62, -108, -116, -562, 110, 101, 55, 207, 377, -188, -126, -417, -400, -59, -16, 321, 129, -283, -254, -560, -78, -610, -71, -185, -444, -83, -612, 382, -438, -200, -233, -78, -1170, -1289, -1022, -1097, -1329, -1154, -1223, -983, -1383, -1647, -1678, -1579, -1111, -37, -397, -58, 399, 274, 48, 43, -50, -366, 468, 133, 108, 60, 52, -46, -794, -524, -559, -141, -159, -132, -312, -816, -785, -588, -558, -433, -417, -176, 82, 26, 113, -177, -47, -48, 110, -2, -177, -32, -19, 17, -22, 116, -109, -138, -97, -16, -23, 3, -101, -45, -11, -26, 7, -92, 40, -19, 184, -383, -70, -137, -249, 92, -199, -193, -360, -310, -192, -231, 41, -204, -528, -206, -146, -140, -184, -207, -77, -154, -73, -114, -193, -159, -199, -134, 91, -162, -141, -28, -58, -22, -93, 55, 164, -124, -90, 5, -24, -167, -38, -66, -40, -58, -25, 107, -45, 25, 0, -78, -74, -92, 55, 74}
, {63, -302, 85, -166, 53, -48, 244, 160, -55, 30, -127, 255, 89, 241, 46, 254, 164, -14, -19, -52, -202, -220, -60, 127, -154, -372, 139, 0, 50, -211, 58, -112, -206, -283, -195, -308, 309, 261, -301, -52, 39, -79, -178, -196, -68, -61, -10, -48, -80, -161, -314, -156, -16, -46, -143, -198, -135, -227, -309, -298, -220, -388, -334, -162, -247, -365, -307, -305, -499, -63, 137, -36, -324, -576, -411, -519, -641, -435, -295, -488, -635, -417, -454, -277, 648, -226, 27, -178, -19, -23, -46, -111, -191, -106, -121, -51, -197, -179, -82, -242, 2, -438, -628, -57, -251, -73, -93, -331, -367, -318, -263, -120, 193, -279, -5, 62, -275, -60, 57, -310, -168, -570, -228, -177, -432, -215, 391, 221, 452, 546, 696, 298, 433, -49, 247, 493, 502, 527, 253, 131, 247, 315, 189, 518, 437, 132, 440, 391, -169, 373, 66, 63, 187, -137, 562, 153, 307, 359, 361, 496, 714, 362, 496, 199, 683, 356, 513, -44, 43, 70, -88, 8, 136, -9, -81, -9, 165, 64, -115, 119, 82, -20, 149, 125, 41, 11, 99, 26, 300, 80, 208, 119, 159, 46, -23, 257, -189, 387, 215, 44, 81, -81, 26, 0, 118, 393, 270, 64, 187, 167, 331, -412, -449, -390, -356, -477, -428, -475, -374, -334, -398, -336, -351, -403, -273, -7, 195, 191, 81, 22, 18, 188, 107, 34, 166, 190, 120, 162, 85, -370, 105, -48, -85, -14, 22, 214, 111, 136, 70, -86, -16, 49}
, {390, 394, 228, 296, 299, 414, 242, 244, 348, 348, 223, 258, 167, 227, 105, -1, 55, 46, 253, -77, -39, 14, -50, -130, -147, 417, 26, -139, -2, 67, 62, 300, -67, -20, 88, 87, -259, 8, 29, -169, -189, -152, 172, 101, -26, 133, -57, 116, 203, 254, 192, 96, 150, -47, 180, 153, 194, 67, 4, 49, 96, 89, 95, -6, -12, 33, -25, 4, -13, 54, -32, -25, -80, 83, 202, 89, 49, -20, -35, -48, 68, 84, -180, -463, -269, -134, -145, -77, -227, -127, -109, 50, -19, -53, 10, -112, 85, 154, -377, -426, -269, -260, -233, -183, -12, 121, 103, -6, 22, -185, -335, 46, -360, 221, -248, 56, -141, -176, -163, -124, 341, -446, 108, 24, 193, -193, 657, 714, 395, 408, 444, 612, 641, 928, 924, 801, 1050, 795, 680, -17, -142, -465, -765, -820, -427, -119, -268, 7, -236, -323, -232, -96, -192, 91, -378, -455, -365, -556, -352, -321, -422, 384, 298, 435, -58, 254, 18, 157, -81, 9, 117, 140, 34, -20, 52, 23, -8, 88, 105, 13, 80, 24, 39, 15, 74, 122, 24, -7, -186, 66, -82, -20, -128, 121, 49, -151, 56, -138, -129, -10, 33, -149, 107, 125, 73, -20, -120, 71, -75, 3, -477, -105, -210, -124, -111, -119, -112, -207, -166, -164, -136, -216, -116, -103, -104, 124, -108, -300, -192, -112, -91, -236, -326, 10, -105, -257, -172, -236, 13, 335, -102, 80, 38, 15, 62, -281, -148, -86, -101, 94, -110, -107}
, {-809, -365, -767, -785, -1260, -1569, -502, -1289, -1239, -1191, -1030, -487, -213, -888, 100, -496, -1163, -239, -192, -485, -195, -437, -347, -1017, -629, -553, -333, -619, 72, 281, -319, -167, 475, -1011, -698, -590, 38, -1233, -155, 1284, -137, -1419, -1324, -735, -1262, -1226, -1432, -670, -982, -481, -1502, -1285, -777, -2053, -2027, -242, -667, 129, 215, -224, 403, 7, -84, 44, -94, 77, -437, 342, 288, 451, -214, 58, 355, -913, 148, 135, -709, -899, 849, 131, -886, -581, -709, 326, -480, 253, 232, 183, 183, 103, 173, 155, 90, 196, 96, 201, 112, -227, 281, 286, 373, -195, 356, 128, -80, -28, 82, 234, 48, 144, 223, 648, 158, 417, 12, 14, 163, -151, 111, 47, -23, 29, -4, 126, 33, -51, -774, -868, 46, 69, -46, -19, -84, 21, -213, 234, 75, -474, -532, -318, 548, 431, 345, 399, 612, -284, 358, 510, -42, 12, 432, 171, -31, -494, 77, 145, 92, 148, 144, 103, 13, -1117, -1063, -976, -732, -986, -441, -129, 52, 70, 59, -117, 188, -193, -51, 174, -548, -426, -518, 61, -93, -374, -170, -274, 246, 199, -301, 185, 357, 510, -333, 81, 95, -321, -138, 467, -132, 299, 341, 267, 110, 332, 231, -243, 325, 226, 185, 78, 108, -44, -279, -152, -180, -91, -223, -209, -167, -113, -173, -81, -179, -110, -79, -189, -100, 1314, -31, -30, -28, -136, -146, -139, -32, -66, -138, -175, -170, -258, 124, 1036, 12, 9, -33, -122, -110, -84, -84, -15, -99, -9, -78, -100}
, {-708, -1262, -948, -1305, -747, -577, -1577, -1713, -1106, -1246, -1378, -1522, 47, -925, -404, 318, 96, -4, -268, -499, -819, -198, -522, -693, -667, -892, -185, -11, -685, -452, 305, -312, -765, 645, -163, -169, -475, 226, -931, -2349, -196, 505, 645, 480, 329, 783, 569, 462, 501, 29, 419, 632, 451, 887, 1090, -11, 231, -288, -338, 153, -797, -130, 43, -143, -639, -90, 125, -1551, -313, -969, 7, -437, -579, -373, -274, -679, -609, -194, -1209, -936, -317, -698, 32, 179, -326, 217, 161, 166, 176, 126, 128, 208, 164, 227, 206, 166, 186, -155, 236, 300, -1219, -518, -1146, -775, -335, -904, -249, -1142, -509, -887, -718, -1640, -349, -1776, -50, -1, -116, -24, -9, 86, 45, -132, -59, -148, -96, -43, 140, -66, -977, -773, -716, -692, -856, -1086, -976, -820, -1199, -1200, -1132, 59, -163, -265, -671, -238, -55, 319, -644, -961, -591, -1102, -906, -420, -225, 541, 430, 329, 269, 94, 97, -49, 144, 718, 670, 642, 351, 452, 250, -128, -116, -77, -49, -58, -454, 59, 50, -285, 69, -31, 143, -317, -83, 84, -185, 45, -1016, -812, -176, -600, -966, -1305, -483, -575, -368, -467, -389, -740, 269, 116, -123, -57, 218, -147, -142, 444, -168, -70, -243, 206, -254, -302, -125, -210, -94, -200, -139, -222, -140, -165, -92, -104, -142, -92, -161, -138, -149, -1072, 147, -42, -32, 178, 51, 57, 16, 222, 164, 67, 164, 195, -138, -908, 59, -28, 67, 73, 51, 43, 93, 26, 98, 66, 101, 67}
, {-226, -550, -47, -51, -326, 21, -220, -283, -216, -249, -122, -227, 193, -19, 122, 136, 111, -195, 128, -266, 48, -83, -158, -112, -39, -4, 219, 55, -18, 393, 9, 76, 195, 179, -238, 54, -77, -218, -85, -7, 213, 154, 8, -92, 403, 158, 154, 83, 184, 45, 300, 38, 2, 259, 219, 336, -384, 200, 53, -157, -113, -208, -281, -124, -256, -278, -203, -341, -160, -251, 52, -147, -227, 26, -277, -107, -311, -273, -26, -227, 3, -244, -143, -916, -29, -835, -927, -899, -972, -989, -983, -1023, -951, -1000, -1056, -917, -975, 671, -529, -62, 524, 612, 566, 513, 490, 535, 507, 527, 609, 415, 503, 541, 436, 566, 437, 347, 410, 372, 506, 382, 320, 342, 350, 426, 416, 397, 440, 433, 462, 488, 371, 329, 258, 44, 47, 179, 47, 18, 226, 65, -2, 118, -47, 104, 28, 35, -42, 50, 20, 43, 201, 116, 23, 53, 307, 252, 157, 0, 79, 55, 141, 437, 339, 159, 182, 158, 5, -37, 6, 88, 130, 86, 16, -26, -15, 16, -60, 1, -121, -94, -77, 74, -103, -28, -69, -172, 10, 36, 163, 84, -32, -52, -68, -32, 49, -9, 91, 66, -146, -204, -87, -117, -6, -185, -5, 70, -45, -152, -54, 177, 196, 203, 222, 217, 216, 301, 241, 268, 166, 156, 243, 238, 273, 207, 208, 173, 104, 70, 39, 57, 206, 32, 112, 74, 18, 71, 110, 132, -45, -79, 94, 169, 29, -6, 115, 142, 65, 184, 39, -12, 42, 25}
}
;

#undef INPUT_SAMPLES
#undef FC_UNITS
/**
  ******************************************************************************
  * @file    model.hh
  * @author  Pierre-Emmanuel Novac <penovac@unice.fr>, LEAT, CNRS, Université Côte d'Azur, France
  * @version 1.0.0
  * @date    08 july 2020
  * @brief   Template generating plain C code for the implementation of Convolutional Neural Networks on MCU
  */

#ifndef __MODEL_H__
#define __MODEL_H__

#ifndef SINGLE_FILE
#include "number.h"
#endif

#define MODEL_OUTPUT_SAMPLES 6
#define MODEL_INPUT_SAMPLES 128 // node 0 is InputLayer so use its output shape as input shape of the model
#define MODEL_INPUT_CHANNELS 9

void cnn(
  const number_t input[MODEL_INPUT_CHANNELS][MODEL_INPUT_SAMPLES],
  //dense_output_type dense_output);
  number_t output[MODEL_OUTPUT_SAMPLES]);

#endif//__MODEL_H__
/**
  ******************************************************************************
  * @file    model.cc
  * @author  Pierre-Emmanuel Novac <penovac@unice.fr>, LEAT, CNRS, Université Côte d'Azur, France
  * @version 1.0.0
  * @date    24 march 2020
  * @brief   Template generating plain C code for the implementation of Convolutional Neural Networks on MCU
  */

#ifndef SINGLE_FILE
#include "number.h"
#include "model.h"

 // InputLayer is excluded
#include "conv1d.c"
#include "weights/conv1d.c" // InputLayer is excluded
#include "flatten.c" // InputLayer is excluded
#include "dense.c"
#include "weights/dense.c"
#endif

void cnn(
  const number_t input[MODEL_INPUT_CHANNELS][MODEL_INPUT_SAMPLES],
  dense_output_type dense_output) {

  // Output array allocation
  static union {
    conv1d_output_type conv1d_output;
    flatten_output_type flatten_output;
  } activations1;

  //TODO: Fill model layer calls chain
}
