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
    DT_FOREACH_CHILD_SEP(DT_PATH(leds), GPIO_DT_SPEC_GET_GPIOS, (, ))};

#define KEYS_COUNT (DT_FOREACH_CHILD(DT_PATH(gpio_keys), CHILD_COUNT) 0)
static const struct gpio_dt_spec keys[KEYS_COUNT] = {
    DT_FOREACH_CHILD_SEP(DT_PATH(gpio_keys), GPIO_DT_SPEC_GET_GPIOS, (, ))};

#define SEVENSEGMENT0_PINS_COUNT (DT_FOREACH_PROP_ELEM(DT_NODELABEL(sevensegment0), gpios, PROP_COUNT) 0)
static const struct gpio_dt_spec sevensegment0[SEVENSEGMENT0_PINS_COUNT] = {
    DT_FOREACH_PROP_ELEM_SEP(DT_NODELABEL(sevensegment0), gpios, GPIO_DT_SPEC_GET_BY_IDX, (, )),
};

#define SEVENSEGMENT1_PINS_COUNT (DT_FOREACH_PROP_ELEM(DT_NODELABEL(sevensegment1), gpios, PROP_COUNT) 0)
static const struct gpio_dt_spec sevensegment1[SEVENSEGMENT1_PINS_COUNT] = {
    DT_FOREACH_PROP_ELEM_SEP(DT_NODELABEL(sevensegment1), gpios, GPIO_DT_SPEC_GET_BY_IDX, (, )),
};

#define SEVENSEGMENT2_PINS_COUNT (DT_FOREACH_PROP_ELEM(DT_NODELABEL(sevensegment2), gpios, PROP_COUNT) 0)
static const struct gpio_dt_spec sevensegment2[SEVENSEGMENT2_PINS_COUNT] = {
    DT_FOREACH_PROP_ELEM_SEP(DT_NODELABEL(sevensegment2), gpios, GPIO_DT_SPEC_GET_BY_IDX, (, )),
};

#define SEVENSEGMENT3_PINS_COUNT (DT_FOREACH_PROP_ELEM(DT_NODELABEL(sevensegment3), gpios, PROP_COUNT) 0)
static const struct gpio_dt_spec sevensegment3[SEVENSEGMENT3_PINS_COUNT] = {
    DT_FOREACH_PROP_ELEM_SEP(DT_NODELABEL(sevensegment3), gpios, GPIO_DT_SPEC_GET_BY_IDX, (, )),
};

static const struct gpio_dt_spec sevenseg0_dp = GPIO_DT_SPEC_GET_BY_IDX(DT_NODELABEL(sevensegment0), gpios, 7);
static const struct gpio_dt_spec sevenseg1_dp = GPIO_DT_SPEC_GET_BY_IDX(DT_NODELABEL(sevensegment1), gpios, 7);
static const struct gpio_dt_spec sevenseg2_dp = GPIO_DT_SPEC_GET_BY_IDX(DT_NODELABEL(sevensegment2), gpios, 7);
static const struct gpio_dt_spec sevenseg3_dp = GPIO_DT_SPEC_GET_BY_IDX(DT_NODELABEL(sevensegment3), gpios, 7);

// Active high
static const int sevensegLUT[] = {0x3F, 0x06, 0x5B, 0x4F, 0x66, 0x6D, 0x7D, 0x07, 0x7F, 0x6F, 0x77, 0x7C, 0x39, 0x5E, 0x79, 0x71};

#define USER_PRIO 2
#define STACK_SIZE 256

void display_show_value(uint16_t i)
{
    gpio_port_set_masked(port_7seg01, 0x00FF, sevensegLUT[i % 10]);
    gpio_port_set_masked(port_7seg01, 0xFF00, sevensegLUT[(i / 10) % 10] << 8);
    gpio_port_set_masked(port_7seg23, 0x00FF, sevensegLUT[(i / 100) % 10]);
    gpio_port_set_masked(port_7seg23, 0xFF00, sevensegLUT[(i / 1000) % 10] << 8);
}

void store_result(int *results, int result, int size)
{
    for (int i = 0; i < size; i++)
    {
        if (results[i] == 0)
        {
            results[i] = result;
            break;
        }
    }
}

void display_average(int *results, int size)
{
    int sum = 0;
    int count = 0;
    for (int i = 0; i < size; i++)
    {
        if (results[i] == 0)
            break;
        sum += results[i];
        ++count;
    }

    const int average = sum / count;
    display_value(average);
}

void store_button_press_time(int *store_to)
{
    const int time = HAL_GetTick();
    const int BOUNCE_DELAY = 300;
    const int delta = time - *store_to;
    if (delta < BOUNCE_DELAY)
        return; // button probably activated twice

    *store_to = time;
}

int number_of_toggled_switches()
{
    const int switches[] = {SW0_Pin, SW1_Pin, SW2_Pin, SW3_Pin, SW4_Pin, SW5_Pin, SW6_Pin, SW7_Pin};
    int count = 0;
    for (int i = 0; i < 8; i++)
    {
        if (HAL_GPIO_ReadPin(GPIOC, switches[i]) == GPIO_PIN_SET)
            ++count;
    }
    return count;
}

static int ticks_when_test_started = 0;
static int ticks_when_user_pressed = 0;
static int results[10] = {0};

bool is_signal_time(int number_of_switches)
{
    const bool random_validated = rand() < RAND_MAX / 100000;
    const int minimum = 3000 + number_of_switches * 1000;

    const int elapsed = HAL_GetTick() - ticks_when_test_started;

    return random_validated && elapsed > minimum;
}

void HAL_GPIO_EXTI_Callback(uint16_t GPIO_Pin)
{
    switch (GPIO_Pin)
    {
    case BTN1_Pin:
        store_button_press_time(&ticks_when_test_started);
        srand(HAL_GetTick());
        log("Test started. Ticks: %i", ticks_when_test_started);

        break;
    case BTN2_Pin:
        log("Displaying average");
        display_average(results, 10);
        break;
    case BTN4_Pin:
        store_button_press_time(&ticks_when_user_pressed);
        log("User pressed the button. Ticks: %i", ticks_when_user_pressed);
        break;
    }
}

int init(void)
{
    int ret = 0;

    if (!device_is_ready(port_io) || !device_is_ready(port_led) || !device_is_ready(port_swbtn) || !device_is_ready(port_7seg01) || !device_is_ready(port_7seg23) || !adc_is_ready_dt(&light0) || !device_is_ready(accel0))
    {
        return 1;
    }

    for (size_t i = 0; i < LEDS_COUNT; i++)
    {
        ret += gpio_pin_configure_dt(leds + i, GPIO_OUTPUT_ACTIVE);
    }
    for (size_t i = 0; i < KEYS_COUNT; i++)
    {
        ret += gpio_pin_configure_dt(keys + i, GPIO_INPUT);
    }
    for (size_t i = 0; i < SEVENSEGMENT0_PINS_COUNT; i++)
    {
        ret += gpio_pin_configure_dt(sevensegment0 + i, GPIO_OUTPUT_ACTIVE);
    }
    for (size_t i = 0; i < SEVENSEGMENT1_PINS_COUNT; i++)
    {
        ret += gpio_pin_configure_dt(sevensegment1 + i, GPIO_OUTPUT_ACTIVE);
    }
    for (size_t i = 0; i < SEVENSEGMENT2_PINS_COUNT; i++)
    {
        ret += gpio_pin_configure_dt(sevensegment2 + i, GPIO_OUTPUT_ACTIVE);
    }
    for (size_t i = 0; i < SEVENSEGMENT3_PINS_COUNT; i++)
    {
        ret += gpio_pin_configure_dt(sevensegment3 + i, GPIO_OUTPUT_ACTIVE);
    }

    ret += adc_channel_setup_dt(&light0);

    /* Light sensor init*/
    // ADC buffer
    uint16_t light0_buf;
    struct adc_sequence light0_sequence = {
        .buffer = &light0_buf,
        .buffer_size = sizeof(light0_buf), // buffer size in bytes, not number of samples
    };

    adc_sequence_init_dt(&light0, &light0_sequence);

    /* Accelerometer init*/
    struct sensor_value accel0_config;
    /* set accel/gyro sampling frequency to 104 Hz */
    accel0_config.val1 = 26;
    accel0_config.val2 = 0;
    if (sensor_attr_set(accel0, SENSOR_CHAN_ACCEL_XYZ, SENSOR_ATTR_SAMPLING_FREQUENCY, &accel0_config))
    {
        printk("Cannot set sampling frequency for accelerometer.\n");
    }
    if (sensor_attr_set(accel0, SENSOR_CHAN_GYRO_XYZ, SENSOR_ATTR_SAMPLING_FREQUENCY, &accel0_config))
    {
        printk("Cannot set sampling frequency for gyro.\n");
    }

    return ret;
}

int main(void)
{
    if (init() < 0)
    {
        return -1;
    }

    display_show_value(0);

    k_thread_start(task1_thread_id);
    k_thread_start(task2_thread_id);

    while (1)
    {
        k_msleep(SLEEP_TIME_MS);
    }

    return -1; // Should never reach this
}
