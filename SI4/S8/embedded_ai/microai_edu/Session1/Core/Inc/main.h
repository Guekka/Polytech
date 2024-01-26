/* USER CODE BEGIN Header */
/**
  ******************************************************************************
  * @file           : main.h
  * @brief          : Header for main.c file.
  *                   This file contains the common defines of the application.
  ******************************************************************************
  * @attention
  *
  * Copyright (c) 2024 STMicroelectronics.
  * All rights reserved.
  *
  * This software is licensed under terms that can be found in the LICENSE file
  * in the root directory of this software component.
  * If no LICENSE file comes with this software, it is provided AS-IS.
  *
  ******************************************************************************
  */
/* USER CODE END Header */

/* Define to prevent recursive inclusion -------------------------------------*/
#ifndef __MAIN_H
#define __MAIN_H

#ifdef __cplusplus
extern "C" {
#endif

/* Includes ------------------------------------------------------------------*/
#include "stm32f1xx_hal.h"

/* Private includes ----------------------------------------------------------*/
/* USER CODE BEGIN Includes */

/* USER CODE END Includes */

/* Exported types ------------------------------------------------------------*/
/* USER CODE BEGIN ET */

/* USER CODE END ET */

/* Exported constants --------------------------------------------------------*/
/* USER CODE BEGIN EC */

/* USER CODE END EC */

/* Exported macro ------------------------------------------------------------*/
/* USER CODE BEGIN EM */

/* USER CODE END EM */

/* Exported functions prototypes ---------------------------------------------*/
void Error_Handler(void);

/* USER CODE BEGIN EFP */

/* USER CODE END EFP */

/* Private defines -----------------------------------------------------------*/
#define SEVENSEG2_C_Pin GPIO_PIN_2
#define SEVENSEG2_C_GPIO_Port GPIOE
#define SEVENSEG2_D_Pin GPIO_PIN_3
#define SEVENSEG2_D_GPIO_Port GPIOE
#define SEVENSEG2_E_Pin GPIO_PIN_4
#define SEVENSEG2_E_GPIO_Port GPIOE
#define SEVENSEG2_F_Pin GPIO_PIN_5
#define SEVENSEG2_F_GPIO_Port GPIOE
#define SEVENSEG2_G_Pin GPIO_PIN_6
#define SEVENSEG2_G_GPIO_Port GPIOE
#define BTN5_Pin GPIO_PIN_13
#define BTN5_GPIO_Port GPIOC
#define BTN5_EXTI_IRQn EXTI15_10_IRQn
#define BTN6_Pin GPIO_PIN_14
#define BTN6_GPIO_Port GPIOC
#define BTN6_EXTI_IRQn EXTI15_10_IRQn
#define BTN7_Pin GPIO_PIN_15
#define BTN7_GPIO_Port GPIOC
#define BTN7_EXTI_IRQn EXTI15_10_IRQn
#define SW0_Pin GPIO_PIN_0
#define SW0_GPIO_Port GPIOC
#define SW1_Pin GPIO_PIN_1
#define SW1_GPIO_Port GPIOC
#define SW2_Pin GPIO_PIN_2
#define SW2_GPIO_Port GPIOC
#define SW3_Pin GPIO_PIN_3
#define SW3_GPIO_Port GPIOC
#define SW4_Pin GPIO_PIN_4
#define SW4_GPIO_Port GPIOC
#define SW5_Pin GPIO_PIN_5
#define SW5_GPIO_Port GPIOC
#define LED0_Pin GPIO_PIN_0
#define LED0_GPIO_Port GPIOB
#define LED1_Pin GPIO_PIN_1
#define LED1_GPIO_Port GPIOB
#define LED2_Pin GPIO_PIN_2
#define LED2_GPIO_Port GPIOB
#define SEVENSEG2_DP_Pin GPIO_PIN_7
#define SEVENSEG2_DP_GPIO_Port GPIOE
#define SEVENSEG3_A_Pin GPIO_PIN_8
#define SEVENSEG3_A_GPIO_Port GPIOE
#define SEVENSEG3_B_Pin GPIO_PIN_9
#define SEVENSEG3_B_GPIO_Port GPIOE
#define SEVENSEG3_C_Pin GPIO_PIN_10
#define SEVENSEG3_C_GPIO_Port GPIOE
#define SEVENSEG3_D_Pin GPIO_PIN_11
#define SEVENSEG3_D_GPIO_Port GPIOE
#define SEVENSEG3_E_Pin GPIO_PIN_12
#define SEVENSEG3_E_GPIO_Port GPIOE
#define SEVENSEG3_F_Pin GPIO_PIN_13
#define SEVENSEG3_F_GPIO_Port GPIOE
#define SEVENSEG3_G_Pin GPIO_PIN_14
#define SEVENSEG3_G_GPIO_Port GPIOE
#define SEVENSEG3_DP_Pin GPIO_PIN_15
#define SEVENSEG3_DP_GPIO_Port GPIOE
#define LED10_Pin GPIO_PIN_10
#define LED10_GPIO_Port GPIOB
#define LED11_Pin GPIO_PIN_11
#define LED11_GPIO_Port GPIOB
#define LED12_Pin GPIO_PIN_12
#define LED12_GPIO_Port GPIOB
#define LED13_Pin GPIO_PIN_13
#define LED13_GPIO_Port GPIOB
#define LED14_Pin GPIO_PIN_14
#define LED14_GPIO_Port GPIOB
#define LED15_Pin GPIO_PIN_15
#define LED15_GPIO_Port GPIOB
#define SEVENSEG1_A_Pin GPIO_PIN_8
#define SEVENSEG1_A_GPIO_Port GPIOD
#define SEVENSEG1_B_Pin GPIO_PIN_9
#define SEVENSEG1_B_GPIO_Port GPIOD
#define SEVENSEG1_C_Pin GPIO_PIN_10
#define SEVENSEG1_C_GPIO_Port GPIOD
#define SEVENSEG1_D_Pin GPIO_PIN_11
#define SEVENSEG1_D_GPIO_Port GPIOD
#define SEVENSEG1_E_Pin GPIO_PIN_12
#define SEVENSEG1_E_GPIO_Port GPIOD
#define SEVENSEG1_F_Pin GPIO_PIN_13
#define SEVENSEG1_F_GPIO_Port GPIOD
#define SEVENSEG1_G_Pin GPIO_PIN_14
#define SEVENSEG1_G_GPIO_Port GPIOD
#define SEVENSEG1_DP_Pin GPIO_PIN_15
#define SEVENSEG1_DP_GPIO_Port GPIOD
#define SW6_Pin GPIO_PIN_6
#define SW6_GPIO_Port GPIOC
#define SW7_Pin GPIO_PIN_7
#define SW7_GPIO_Port GPIOC
#define BTN0_Pin GPIO_PIN_8
#define BTN0_GPIO_Port GPIOC
#define BTN0_EXTI_IRQn EXTI9_5_IRQn
#define BTN1_Pin GPIO_PIN_9
#define BTN1_GPIO_Port GPIOC
#define BTN1_EXTI_IRQn EXTI9_5_IRQn
#define BTN2_Pin GPIO_PIN_10
#define BTN2_GPIO_Port GPIOC
#define BTN2_EXTI_IRQn EXTI15_10_IRQn
#define BTN3_Pin GPIO_PIN_11
#define BTN3_GPIO_Port GPIOC
#define BTN3_EXTI_IRQn EXTI15_10_IRQn
#define BTN4_Pin GPIO_PIN_12
#define BTN4_GPIO_Port GPIOC
#define BTN4_EXTI_IRQn EXTI15_10_IRQn
#define SEVENSEG0_A_Pin GPIO_PIN_0
#define SEVENSEG0_A_GPIO_Port GPIOD
#define SEVENSEG0_B_Pin GPIO_PIN_1
#define SEVENSEG0_B_GPIO_Port GPIOD
#define SEVENSEG0_C_Pin GPIO_PIN_2
#define SEVENSEG0_C_GPIO_Port GPIOD
#define SEVENSEG0_D_Pin GPIO_PIN_3
#define SEVENSEG0_D_GPIO_Port GPIOD
#define SEVENSEG0_E_Pin GPIO_PIN_4
#define SEVENSEG0_E_GPIO_Port GPIOD
#define SEVENSEG0_F_Pin GPIO_PIN_5
#define SEVENSEG0_F_GPIO_Port GPIOD
#define SEVENSEG0_G_Pin GPIO_PIN_6
#define SEVENSEG0_G_GPIO_Port GPIOD
#define SEVENSEG0_DP_Pin GPIO_PIN_7
#define SEVENSEG0_DP_GPIO_Port GPIOD
#define LED5_Pin GPIO_PIN_5
#define LED5_GPIO_Port GPIOB
#define LED6_Pin GPIO_PIN_6
#define LED6_GPIO_Port GPIOB
#define LED7_Pin GPIO_PIN_7
#define LED7_GPIO_Port GPIOB
#define LED8_Pin GPIO_PIN_8
#define LED8_GPIO_Port GPIOB
#define LED9_Pin GPIO_PIN_9
#define LED9_GPIO_Port GPIOB
#define SEVENSEG2_A_Pin GPIO_PIN_0
#define SEVENSEG2_A_GPIO_Port GPIOE
#define SEVENSEG2_B_Pin GPIO_PIN_1
#define SEVENSEG2_B_GPIO_Port GPIOE

/* USER CODE BEGIN Private defines */

/* USER CODE END Private defines */

#ifdef __cplusplus
}
#endif

#endif /* __MAIN_H */
