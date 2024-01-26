#include <zephyr/kernel.h>
#include <zephyr/drivers/adc.h>
#include <zephyr/drivers/gpio.h>
#include <zephyr/drivers/sensor.h>


#define SLEEP_TIME_MS 100

static const struct device *port_io = DEVICE_DT_GET(DT_NODELABEL(gpioa));
static const struct device *port_led = DEVICE_DT_GET(DT_NODELABEL(gpiob));
static const struct device *port_swbtn = DEVICE_DT_GET(DT_NODELABEL(gpioc));
static const struct device *port_7seg01 = DEVICE_DT_GET(DT_NODELABEL(gpiod));
static const struct device *port_7seg23 = DEVICE_DT_GET(DT_NODELABEL(gpioe));

#define GPIO_DT_SPEC_GET_GPIOS(child) GPIO_DT_SPEC_GET(child, gpios)

#define CHILD_COUNT(child) 1 + 
#define PROP_COUNT(node, prop, idx) 1 +

#define LEDS_COUNT (DT_FOREACH_CHILD(DT_PATH(leds), CHILD_COUNT) 0)
static const struct gpio_dt_spec leds[LEDS_COUNT] = {
  DT_FOREACH_CHILD_SEP(DT_PATH(leds), GPIO_DT_SPEC_GET_GPIOS, (,))
};

#define KEYS_COUNT (DT_FOREACH_CHILD(DT_PATH(gpio_keys), CHILD_COUNT) 0)
static const struct gpio_dt_spec keys[KEYS_COUNT] = {
  DT_FOREACH_CHILD_SEP(DT_PATH(gpio_keys), GPIO_DT_SPEC_GET_GPIOS, (,))
};

#define SEVENSEGMENT0_PINS_COUNT (DT_FOREACH_PROP_ELEM(DT_NODELABEL(sevensegment0), gpios, PROP_COUNT) 0)
static const struct gpio_dt_spec sevensegment0[SEVENSEGMENT0_PINS_COUNT] = {
  DT_FOREACH_PROP_ELEM_SEP(DT_NODELABEL(sevensegment0), gpios, GPIO_DT_SPEC_GET_BY_IDX, (,)), 
};

#define SEVENSEGMENT1_PINS_COUNT (DT_FOREACH_PROP_ELEM(DT_NODELABEL(sevensegment1), gpios, PROP_COUNT) 0)
static const struct gpio_dt_spec sevensegment1[SEVENSEGMENT1_PINS_COUNT] = {
  DT_FOREACH_PROP_ELEM_SEP(DT_NODELABEL(sevensegment1), gpios, GPIO_DT_SPEC_GET_BY_IDX, (,)), 
};

#define SEVENSEGMENT2_PINS_COUNT (DT_FOREACH_PROP_ELEM(DT_NODELABEL(sevensegment2), gpios, PROP_COUNT) 0)
static const struct gpio_dt_spec sevensegment2[SEVENSEGMENT2_PINS_COUNT] = {
  DT_FOREACH_PROP_ELEM_SEP(DT_NODELABEL(sevensegment2), gpios, GPIO_DT_SPEC_GET_BY_IDX, (,)), 
};

#define SEVENSEGMENT3_PINS_COUNT (DT_FOREACH_PROP_ELEM(DT_NODELABEL(sevensegment3), gpios, PROP_COUNT) 0)
static const struct gpio_dt_spec sevensegment3[SEVENSEGMENT3_PINS_COUNT] = {
  DT_FOREACH_PROP_ELEM_SEP(DT_NODELABEL(sevensegment3), gpios, GPIO_DT_SPEC_GET_BY_IDX, (,)), 
};

static const struct gpio_dt_spec sevenseg0_dp = GPIO_DT_SPEC_GET_BY_IDX(DT_NODELABEL(sevensegment0), gpios, 7);
static const struct gpio_dt_spec sevenseg1_dp = GPIO_DT_SPEC_GET_BY_IDX(DT_NODELABEL(sevensegment1), gpios, 7);
static const struct gpio_dt_spec sevenseg2_dp = GPIO_DT_SPEC_GET_BY_IDX(DT_NODELABEL(sevensegment2), gpios, 7);
static const struct gpio_dt_spec sevenseg3_dp = GPIO_DT_SPEC_GET_BY_IDX(DT_NODELABEL(sevensegment3), gpios, 7);


/* Light sensor */
static const struct adc_dt_spec light0 = ADC_DT_SPEC_GET(DT_PATH(light0));

/* Accelerometer */
static const struct device *accel0 = DEVICE_DT_GET(DT_ALIAS(accel0));

// Active low
//static const int sevensegLUT[] = { 0xC0, 0xF9, 0xA4, 0xB0, 0x99, 0x92, 0x82, 0xF8, 0x80, 0x90, 0x88, 0x83, 0xC6, 0xA1, 0x86, 0x8E };
// Active high
static const int sevensegLUT[] = { 0x3F, 0x06, 0x5B, 0x4F, 0x66, 0x6D, 0x7D, 0x07, 0x7F, 0x6F, 0x77, 0x7C, 0x39, 0x5E, 0x79, 0x71 };

#define USER_PRIO 2

static void user_function(void *p1, void *p2, void *p3) {
  while (1) {
  	printk("Hellorld!\n");
    k_msleep(1000);
  }
}

K_THREAD_DEFINE(user, 256,
                user_function, NULL, NULL, NULL,
                USER_PRIO, 0, 0);

static inline float out_ev(struct sensor_value *val) {
	return (val->val1 + (float)val->val2 / 1000000);
}


int main(void) {
	int ret = 0;

	if (!device_is_ready(port_io)
  || !device_is_ready(port_led)
  || !device_is_ready(port_swbtn)
  || !device_is_ready(port_7seg01)
  || !device_is_ready(port_7seg23)
  || !adc_is_ready_dt(&light0)
  || !device_is_ready(accel0)) {
		return 1;
	}

  for (size_t i = 0; i < LEDS_COUNT; i++) {
    ret += gpio_pin_configure_dt(leds + i, GPIO_OUTPUT_ACTIVE);
  }
  for (size_t i = 0; i < KEYS_COUNT; i++) {
    ret += gpio_pin_configure_dt(keys + i, GPIO_INPUT);
  }
  for (size_t i = 0; i < SEVENSEGMENT0_PINS_COUNT; i++) {
    ret += gpio_pin_configure_dt(sevensegment0 + i, GPIO_OUTPUT_ACTIVE);
  }
  for (size_t i = 0; i < SEVENSEGMENT1_PINS_COUNT; i++) {
    ret += gpio_pin_configure_dt(sevensegment1 + i, GPIO_OUTPUT_ACTIVE);
  }
  for (size_t i = 0; i < SEVENSEGMENT2_PINS_COUNT; i++) {
    ret += gpio_pin_configure_dt(sevensegment2 + i, GPIO_OUTPUT_ACTIVE);
  }
  for (size_t i = 0; i < SEVENSEGMENT3_PINS_COUNT; i++) {
    ret += gpio_pin_configure_dt(sevensegment3 + i, GPIO_OUTPUT_ACTIVE);
  }

  ret += adc_channel_setup_dt(&light0);

  /* Light sensor init*/
  // ADC buffer
	uint16_t light0_buf;
	struct adc_sequence light0_sequence = {
		.buffer = &light0_buf,
		.buffer_size = sizeof(light0_buf),	// buffer size in bytes, not number of samples
	};

  adc_sequence_init_dt(&light0, &light0_sequence);

  /* Accelerometer init*/
  struct sensor_value accel0_config;
  /* set accel/gyro sampling frequency to 104 Hz */
	accel0_config.val1 = 26;
	accel0_config.val2 = 0;
  if (sensor_attr_set(accel0, SENSOR_CHAN_ACCEL_XYZ, SENSOR_ATTR_SAMPLING_FREQUENCY, &accel0_config)) {
		printk("Cannot set sampling frequency for accelerometer.\n");
  }
  if (sensor_attr_set(accel0, SENSOR_CHAN_GYRO_XYZ, SENSOR_ATTR_SAMPLING_FREQUENCY, &accel0_config)) {
		printk("Cannot set sampling frequency for gyro.\n");
  }

	if (ret < 0) {
		return -1;
	}

	static int i = 0;
	while (1) {

    gpio_port_value_t gpioc_value;
    gpio_port_get(port_swbtn, &gpioc_value);
    ret += gpio_port_set_masked(port_led, 0xFFFF, gpioc_value);

    ret += gpio_port_set_masked(port_7seg01, 0x00FF, sevensegLUT[i%10]);
    ret += gpio_port_set_masked(port_7seg01, 0xFF00, sevensegLUT[(i/10)%10] << 8);
    ret += gpio_port_set_masked(port_7seg23, 0x00FF, sevensegLUT[(i/100)%10]);
    ret += gpio_port_set_masked(port_7seg23, 0xFF00, sevensegLUT[(i/1000)%10] << 8);

    if ((i / 5) % 2) {
      ret += gpio_pin_toggle_dt(&sevenseg0_dp);
      ret += gpio_pin_toggle_dt(&sevenseg1_dp);
      ret += gpio_pin_toggle_dt(&sevenseg2_dp);
      ret += gpio_pin_toggle_dt(&sevenseg3_dp);
    }

    int32_t val_mv = light0_buf;
    ret += adc_read_dt(&light0, &light0_sequence);
		ret += adc_raw_to_millivolts_dt(&light0, &val_mv);

    printk("Light sensor: %d mV\n", val_mv);

    /* Read accelerometer */
    static struct sensor_value accel_x, accel_y, accel_z;
    static struct sensor_value gyro_x, gyro_y, gyro_z;
    static struct sensor_value temp;
    sensor_sample_fetch(accel0);
    sensor_channel_get(accel0, SENSOR_CHAN_ACCEL_X, &accel_x);
    sensor_channel_get(accel0, SENSOR_CHAN_ACCEL_Y, &accel_y);
    sensor_channel_get(accel0, SENSOR_CHAN_ACCEL_Z, &accel_z);
    sensor_channel_get(accel0, SENSOR_CHAN_GYRO_X, &gyro_x);
    sensor_channel_get(accel0, SENSOR_CHAN_GYRO_Y, &gyro_y);
    sensor_channel_get(accel0, SENSOR_CHAN_GYRO_Z, &gyro_z);
    sensor_channel_get(accel0, SENSOR_CHAN_DIE_TEMP, &temp);

    printk("Accelerometer: x=%f, y=%f, z=%f\n", out_ev(&accel_x), out_ev(&accel_y), out_ev(&accel_z));
    printk("Gyroscope: x=%f, y=%f, z=%f\n", out_ev(&gyro_x), out_ev(&gyro_y), out_ev(&gyro_z));
    printk("Temperature: %f\n", out_ev(&temp));

		if (ret < 0) {
			return -1;
		}

		k_msleep(SLEEP_TIME_MS);

		i++;

	}

  return 1;
}
