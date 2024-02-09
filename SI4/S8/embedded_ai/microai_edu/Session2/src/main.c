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

/* Light sensor */
static const struct adc_dt_spec light0 = ADC_DT_SPEC_GET(DT_PATH(light0));

/* Accelerometer */
static const struct device *accel0 = DEVICE_DT_GET(DT_ALIAS(accel0));

// Active low
// static const int sevensegLUT[] = { 0xC0, 0xF9, 0xA4, 0xB0, 0x99, 0x92, 0x82, 0xF8, 0x80, 0x90, 0x88, 0x83, 0xC6, 0xA1, 0x86, 0x8E };
// Active high
static const int sevensegLUT[] = {0x3F, 0x06, 0x5B, 0x4F, 0x66, 0x6D, 0x7D, 0x07, 0x7F, 0x6F, 0x77, 0x7C, 0x39, 0x5E, 0x79, 0x71};

#define USER_PRIO 0
// Define the delays D1 and D2
#define DELAY_TASK1 K_MSEC(1000)
#define DELAY_TASK2 K_MSEC(500)
// Define the counter variable
static int counter = 0;
#define STACK_SIZE 256

// Define the message queue size and message size
#define MSGQ_SIZE 10
#define MSG_SIZE sizeof(int)

// Define the message queue
K_MSGQ_DEFINE(msgq, MSG_SIZE, MSGQ_SIZE, 4);

void display_show_value(uint16_t i)
{
    gpio_port_set_masked(port_7seg01, 0x00FF, sevensegLUT[i % 10]);
    gpio_port_set_masked(port_7seg01, 0xFF00, sevensegLUT[(i / 10) % 10] << 8);
    gpio_port_set_masked(port_7seg23, 0x00FF, sevensegLUT[(i / 100) % 10]);
    gpio_port_set_masked(port_7seg23, 0xFF00, sevensegLUT[(i / 1000) % 10] << 8);
}

// Task 1
void task1(void *p1, void *p2, void *p3)
{
    printk("Task 1\n");
    while (1)
    {
        // Send counter value to Task 2
        k_msgq_put(&msgq, &counter, K_FOREVER);

        // Increment counter
        counter++;

        // Wait for delay D1
        k_sleep(DELAY_TASK1);
    }
}

// Task 2
void task2(void *p1, void *p2, void *p3)
{
    printk("Task 2\n");
    int received_value;

    while (1)
    {
        // Receive value from Task 1
        k_msgq_get(&msgq, &received_value, K_FOREVER);

        // Display received value on 7-segment display
        display_show_value(received_value);

        // Wait for delay D2
        k_sleep(DELAY_TASK2);
    }
}

// Define the thread for Task 1
K_THREAD_DEFINE(task1_thread_id, STACK_SIZE,
                task1, NULL, NULL, NULL,
                USER_PRIO, 0, 0);

// Define the thread for Task 2
K_THREAD_DEFINE(task2_thread_id, STACK_SIZE,
                task2, NULL, NULL, NULL,
                USER_PRIO, 0, 0);

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
