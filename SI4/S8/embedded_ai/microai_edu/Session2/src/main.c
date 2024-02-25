#include <zephyr/kernel.h>
#include <zephyr/drivers/adc.h>
#include <zephyr/drivers/gpio.h>
#include <zephyr/drivers/sensor.h>

#define SLEEP_TIME_MS 100

// Active high
static const int sevensegLUT[] = {0x3F, 0x06, 0x5B, 0x4F, 0x66, 0x6D, 0x7D, 0x07, 0x7F, 0x6F, 0x77, 0x7C, 0x39, 0x5E, 0x79, 0x71};

void display_show_value(uint16_t i);
void display_average(void);
void store_button_press_time(volatile int *store_to);
int number_of_toggled_switches(void);
bool is_signal_time(int number_of_switches);
void user_pressed_button(void);
void turn_off_leds(void);
void turn_on_leds(void);
void reset_state(void);

#define SWITCHES_PRIO 4
#define LED_PRIO 2
#define STACK_SIZE 256
#define RESULT_SIZE 10

const int k_min_ms_before_signal = 3000;
const int k_min_ms_before_signal_per_switch = 300;

static volatile int g_results[RESULT_SIZE] = {0};
static volatile int g_ticks_when_test_started = 0;
static volatile int g_ticks_when_signal_fired = 0;
static volatile int g_ticks_when_user_pressed = 0;
static volatile int g_number_of_switches = 0;
static volatile bool g_should_blink_leds = false;

void led_blinking_task(void);
K_THREAD_DEFINE(led_blinking_task_id, STACK_SIZE, led_blinking_task, NULL, NULL, NULL, LED_PRIO, 0, 0);

void switches_task(void);
K_THREAD_DEFINE(switches_task_id, STACK_SIZE, switches_task, NULL, NULL, NULL, SWITCHES_PRIO, 0, 0);

static struct gpio_callback button_pressed_cb_data;
static void button_pressed(const struct device *dev, struct gpio_callback *cb, unsigned int pins);

int init(void);

int main(void)
{
    if (init() < 0)
    {
        return -1;
    }

    display_show_value(0);
    turn_off_leds();
    printk("Initialization complete\n");

    while (true)
    {
        if (g_ticks_when_test_started == 0 || g_ticks_when_signal_fired != 0)
        {
            k_msleep(SLEEP_TIME_MS);
            continue;
        }

        printk("Test started. Ticks: %i\n", g_ticks_when_test_started);
        g_should_blink_leds = true;

        printk("%i toggled switches at start\n", number_of_toggled_switches());

        while (g_ticks_when_signal_fired != 0 || !is_signal_time(number_of_toggled_switches()))
            k_msleep(10);

        g_ticks_when_signal_fired = k_uptime_get();
        printk("Firing signal at ticks: %i\n", g_ticks_when_signal_fired);
        // light up all LEDs
        g_should_blink_leds = false;
        turn_on_leds();
    }
}

void store_result(int result)
{
    for (int i = 0; i < RESULT_SIZE; i++)
    {
        if (g_results[i] == 0)
        {
            g_results[i] = result;
            break;
        }
    }
}

void display_average()
{
    printk("Displaying average\n");

    int sum = 0;
    int count = 0;
    for (int i = 0; i < RESULT_SIZE; i++)
    {
        if (g_results[i] == 0)
            break;
        sum += g_results[i];
        ++count;
    }

    if (count == 0)
    {
        printk("No results to display\n");
        return;
    }

    printk("Sum: %i\n", sum);

    const int average = sum / count;

    printk("Average: %i\n", average);

    display_show_value(average);
}

void store_button_press_time(volatile int *store_to)
{
    const int time = k_uptime_get();
    const int BOUNCE_DELAY = 300;
    const int delta = time - *store_to;
    if (delta < BOUNCE_DELAY)
        return; // button probably activated twice

    *store_to = time;
}

void switches_task(void)
{
    k_thread_name_set(k_current_get(), "Switches task");
    printk("Switches task started\n");

    while (true)
    {
        g_number_of_switches = number_of_toggled_switches();
        k_msleep(100);
    }
}

bool is_signal_time(int number_of_switches)
{
    const bool random_validated = rand() < RAND_MAX / 100;
    const int minimum = k_min_ms_before_signal + number_of_switches * k_min_ms_before_signal_per_switch;

    const int elapsed = k_uptime_get() - g_ticks_when_test_started;

    return random_validated && elapsed > minimum;
}

void user_pressed_button(void)
{
    if (g_ticks_when_signal_fired == 0)
        // the user pressed the button too early
        return;

    store_button_press_time(&g_ticks_when_user_pressed);
    printk("User pressed the button. Ticks: %i\n", g_ticks_when_user_pressed);

    const int reaction_time = g_ticks_when_user_pressed - g_ticks_when_signal_fired;
    printk("Reaction time: %i\n", reaction_time);

    store_result(reaction_time);

    reset_state();
    display_show_value(reaction_time);
}

void reset_state(void)
{
    turn_off_leds();
    display_show_value(0);
    g_should_blink_leds = false;
    g_ticks_when_signal_fired = 0;
    g_ticks_when_user_pressed = 0;
    g_ticks_when_test_started = 0;
}

/* HARDWARE FUNCTIONS */

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

    // this is not a good seed, but it's good enough for this project
    // using the accelerometer and light sensor would be better I guesss
    srand(k_uptime_get());

    return ret;
}

int number_of_toggled_switches()
{
    int count = 0;
    for (size_t i = SWITCHES_START; i < SWITCHES_END; ++i)
        count += gpio_pin_get(port_swbtn, i);
    return count;
}

void turn_off_leds()
{
    for (size_t i = 0; i < LEDS_COUNT; i++)
        gpio_pin_set(port_led, leds[i].pin, 0);
}

void turn_on_leds()
{
    for (size_t i = 0; i < LEDS_COUNT; i++)
        gpio_pin_set(port_led, leds[i].pin, 1);
}

void turn_on_one_led_out_of_two(int start)
{
    turn_off_leds();
    for (size_t i = start; i < LEDS_COUNT; i += 2)
        gpio_pin_set(port_led, leds[i].pin, 1);
}

void led_blinking_task(void)
{
    k_thread_name_set(k_current_get(), "LED blinking task");
    printk("LED blinking task started\n");

    int i = 0;
    while (true)
    {
        if (!g_should_blink_leds)
        {
            k_msleep(100);
            continue;
        }

        turn_on_one_led_out_of_two(i);
        k_msleep(500);
        i = (i + 1) % 2;
    }
}

static void button_pressed(const struct device *dev, struct gpio_callback *cb, unsigned int pins)
{
    if (pins & BIT(BUTTONS_START + 1))
        store_button_press_time(&g_ticks_when_test_started);

    else if (pins & BIT(BUTTONS_START + 2))
        display_average();

    else if (pins & BIT(BUTTONS_START + 4))
        user_pressed_button();
}

void display_show_value(uint16_t i)
{
    gpio_port_set_masked(port_7seg01, 0x00FF, sevensegLUT[i % 10]);
    gpio_port_set_masked(port_7seg01, 0xFF00, sevensegLUT[(i / 10) % 10] << 8);
    gpio_port_set_masked(port_7seg23, 0x00FF, sevensegLUT[(i / 100) % 10]);
    gpio_port_set_masked(port_7seg23, 0xFF00, sevensegLUT[(i / 1000) % 10] << 8);
}
