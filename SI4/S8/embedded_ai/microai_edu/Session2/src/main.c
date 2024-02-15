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

// unfortunately, switches and buttons are on the same port
#define SWITCHES_START 0
#define SWITCHES_END 8
#define BUTTONS_START 8
#define BUTTONS_END 16

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

static int ticks_when_test_started = 0;
static int ticks_when_user_pressed = 0;
static int results[10] = {0};

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
    display_show_value(average);
}

void store_button_press_time(int *store_to)
{
    const int time = k_uptime_get();
    const int BOUNCE_DELAY = 300;
    const int delta = time - *store_to;
    if (delta < BOUNCE_DELAY)
        return; // button probably activated twice

    *store_to = time;
}

int number_of_toggled_switches()
{
    int count = 0;
    for (size_t i = SWITCHES_START; i < SWITCHES_END; ++i)
        count += gpio_pin_get(port_swbtn, i);
    return count;
}

bool is_signal_time(int number_of_switches)
{
    const bool random_validated = rand() < RAND_MAX / 100000;
    const int minimum = 3000 + number_of_switches * 1000;

    const int elapsed = k_uptime_get() - ticks_when_test_started;

    return random_validated && elapsed > minimum;
}

static struct gpio_callback button_pressed_cb_data;
static void button_pressed(const struct device *dev, struct gpio_callback *cb, unsigned int pins)
{
    if (pins & BIT(BUTTONS_START + 1))
    {
        store_button_press_time(&ticks_when_test_started);
        printk("Test started. Ticks: %i\n", ticks_when_test_started);
        srand(k_uptime_get());
    }
    if (pins & BIT(BUTTONS_START + 2))
    {
        printk("Displaying average\n");
        display_average(results, 10);
    }
    if (pins & BIT(BUTTONS_START + 4))
    {
        store_button_press_time(&ticks_when_user_pressed);
        printk("User pressed the button. Ticks: %i", ticks_when_user_pressed);
    }
}

int init(void)
{
    int ret = 0;

    if (!device_is_ready(port_io) || !device_is_ready(port_led) || !device_is_ready(port_swbtn) || !device_is_ready(port_7seg01) || !device_is_ready(port_7seg23))
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
        ret += gpio_pin_interrupt_configure_dt(keys + i, GPIO_INT_EDGE_TO_ACTIVE);
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

    // interrupt
    gpio_init_callback(&button_pressed_cb_data, button_pressed,
                       BIT(BUTTONS_START + 1) |
                           BIT(BUTTONS_START + 2) |
                           BIT(BUTTONS_START + 4));
    gpio_add_callback(port_swbtn, &button_pressed_cb_data);

    return ret;
}

int main(void)
{
    if (init() < 0)
    {
        return -1;
    }

    display_show_value(0);
    printk("Starting...\n");

    int ticks_when_signal_fired = 0;
    printk("%i toggled switches at start\n", number_of_toggled_switches());

    while (1)
    {
        // for some reason,
        k_msleep(1);

        if (ticks_when_test_started == 0)
            continue;

        // test has started.

        // and the signal has not been fired yet
        if (ticks_when_signal_fired == 0)
        {
            // Make the LEDs blink. Half a second even, half a second odd.
            const int time = k_uptime_get() - ticks_when_test_started;
            const int period = 1000;

            // turn on even LEDs for half a second, then odd LEDs for half a second
            const int is_even = (time / period) % 2 == 0;
            for (size_t i = 0; i < LEDS_COUNT; i++)
            {
                const int is_on = (i % 2 == 0) == is_even;
                gpio_pin_set_dt(leds + i, is_on);
            }

            if (is_signal_time(number_of_toggled_switches()))
            {
                ticks_when_signal_fired = k_uptime_get();
                printk("Firing signal at ticks: %i\n", ticks_when_signal_fired);
                // light up all LEDs
                for (size_t i = 0; i < LEDS_COUNT; i++)
                    gpio_pin_set(port_led, leds[i].pin, 1);
            }
        }

        if (ticks_when_user_pressed == 0)
            continue;

        if (ticks_when_signal_fired == 0)
            // the user pressed the button too early
            continue;

        // test has started and the button has been pressed. Display the time.
        const int reaction_time = ticks_when_user_pressed - ticks_when_signal_fired;
        printk("Reaction time: %i\n", reaction_time);

        store_result(results, reaction_time, 10);

        display_show_value(reaction_time);
        ticks_when_signal_fired = 0;
        ticks_when_user_pressed = 0;
        ticks_when_test_started = 0;
    }

    return -1; // Should never reach this
}
