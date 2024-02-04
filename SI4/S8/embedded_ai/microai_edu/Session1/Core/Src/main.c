/* USER CODE BEGIN Header */
/**
 ******************************************************************************
 * @file           : main.c
 * @brief          : Main program body
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
/* Includes ------------------------------------------------------------------*/
#include "main.h"

/* Private includes ----------------------------------------------------------*/
/* USER CODE BEGIN Includes */
#include <stdbool.h>
#include <stdarg.h>
#include <stdlib.h>
/* USER CODE END Includes */

/* Private typedef -----------------------------------------------------------*/
/* USER CODE BEGIN PTD */

/* USER CODE END PTD */

/* Private define ------------------------------------------------------------*/
/* USER CODE BEGIN PD */

/* USER CODE END PD */

/* Private macro -------------------------------------------------------------*/
/* USER CODE BEGIN PM */

/* USER CODE END PM */

/* Private variables ---------------------------------------------------------*/
UART_HandleTypeDef huart1;

/* USER CODE BEGIN PV */

/* USER CODE END PV */

/* Private function prototypes -----------------------------------------------*/
void SystemClock_Config(void);
static void MX_GPIO_Init(void);
static void MX_USART1_UART_Init(void);
/* USER CODE BEGIN PFP */

/* USER CODE END PFP */

/* Private user code ---------------------------------------------------------*/
/* USER CODE BEGIN 0 */
static const int even_leds = LED0_Pin | LED2_Pin | LED6_Pin | LED8_Pin | LED10_Pin | LED12_Pin | LED14_Pin;
static const int odd_leds = LED1_Pin | LED5_Pin | LED7_Pin | LED9_Pin | LED11_Pin | LED13_Pin | LED15_Pin;
static const int leds = even_leds | odd_leds;

void log(const char *msg, ...)
{
    char buffer[256];
    va_list args;
    va_start(args, msg);
    vsprintf(buffer, msg, args);
    va_end(args);

    HAL_UART_Transmit(&huart1, (uint8_t *)buffer, strlen(buffer), 0x200);
    HAL_UART_Transmit(&huart1, (uint8_t *)"\r\n", 2, 0x200);
}

void light_only_leds(int leds)
{
    HAL_GPIO_WritePin(LED0_GPIO_Port, ~leds, GPIO_PIN_SET);
    HAL_GPIO_WritePin(LED0_GPIO_Port, leds, GPIO_PIN_RESET);
}

void display_digit(GPIO_TypeDef *GPIOx, uint16_t digit, bool is_lower_byte, bool has_dot)
{
    /* Check the parameters */
    assert_param(IS_GPIO_PIN(GPIO_Pin));
    if (digit > 9)
        return; // TODO: handle error

    int lut[10] = {
        0b00111111, // 0
        0b00000110, // 1
        0b01011011, // 2
        0b01001111, // 3
        0b01100110, // 4
        0b01101101, // 5
        0b01111101, // 6
        0b00000111, // 7
        0b01111111, // 8
        0b01101111, // 9
    };

    const int shift = is_lower_byte ? 8 : 0;

    const int reset_pin = 0b11111111 << shift;
    HAL_GPIO_WritePin(GPIOx, reset_pin, GPIO_PIN_SET);

    int value = lut[digit] << shift;
    if (has_dot)
        value |= 0b10000000 << shift;

    HAL_GPIO_WritePin(GPIOx, value, GPIO_PIN_RESET);
}

uint16_t get_digit(int number, uint8_t idx)
{
    for (int i = 0; i < idx; i++)
        number /= 10;
    return number % 10;
}

void display_ms(uint16_t number)
{
    display_digit(SEVENSEG0_E_GPIO_Port, get_digit(number, 0), false, false);
    display_digit(SEVENSEG1_E_GPIO_Port, get_digit(number, 1), true, false);
    display_digit(SEVENSEG2_E_GPIO_Port, get_digit(number, 2), false, false);
    display_digit(SEVENSEG3_E_GPIO_Port, get_digit(number, 3), true, true);
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
    display_ms(average);
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
/* USER CODE END 0 */

/**
 * @brief  The application entry point.
 * @retval int
 */
int main(void)
{
    /* USER CODE BEGIN 1 */

    /* USER CODE END 1 */

    /* MCU Configuration--------------------------------------------------------*/

    /* Reset of all peripherals, Initializes the Flash interface and the Systick. */
    HAL_Init();

    /* USER CODE BEGIN Init */

    /* USER CODE END Init */

    /* Configure the system clock */
    SystemClock_Config();

    /* USER CODE BEGIN SysInit */

    /* USER CODE END SysInit */

    /* Initialize all configured peripherals */
    MX_GPIO_Init();
    MX_USART1_UART_Init();
    /* USER CODE BEGIN 2 */
    log("Starting");

    display_ms(0);

    // turn off all LEDs
    HAL_GPIO_WritePin(LED0_GPIO_Port, leds, GPIO_PIN_RESET);

    int ticks_when_signal_fired = 0;
    log("%i toggled switches at start", number_of_toggled_switches());

    /* USER CODE END 2 */

    /* Infinite loop */
    /* USER CODE BEGIN WHILE */
    while (1)
    {
        if (ticks_when_test_started == 0)
            continue;

        // test has started. Make the LEDs blink. Half a second even, half a second odd.
        const int time = HAL_GetTick() - ticks_when_test_started;
        const int period = 1000;

        const int is_even = (time / period) % 2 == 0;
        light_only_leds(is_even ? even_leds : odd_leds);

        if (is_signal_time(number_of_toggled_switches()))
        {
            ticks_when_signal_fired = HAL_GetTick();
            log("Firing signal at ticks: %i", ticks_when_signal_fired);
            light_only_leds(leds);
            HAL_Delay(2000);
        }

        if (ticks_when_user_pressed == 0)
            continue;

        if (ticks_when_signal_fired == 0)
            // the user pressed the button too early
            continue;

        // test has started and the button has been pressed. Display the time.
        const int reaction_time = ticks_when_user_pressed - ticks_when_signal_fired;
        log("Reaction time: %i", reaction_time);

        store_result(results, reaction_time, 10);

        display_ms(reaction_time);
        ticks_when_signal_fired = 0;
        ticks_when_user_pressed = 0;
        ticks_when_test_started = 0;

        /* USER CODE END WHILE */

        /* USER CODE BEGIN 3 */
    }
    /* USER CODE END 3 */
}

/**
 * @brief System Clock Configuration
 * @retval None
 */
void SystemClock_Config(void)
{
    RCC_OscInitTypeDef RCC_OscInitStruct = {0};
    RCC_ClkInitTypeDef RCC_ClkInitStruct = {0};

    /** Initializes the RCC Oscillators according to the specified parameters
     * in the RCC_OscInitTypeDef structure.
     */
    RCC_OscInitStruct.OscillatorType = RCC_OSCILLATORTYPE_HSE;
    RCC_OscInitStruct.HSEState = RCC_HSE_ON;
    RCC_OscInitStruct.HSEPredivValue = RCC_HSE_PREDIV_DIV1;
    RCC_OscInitStruct.HSIState = RCC_HSI_ON;
    RCC_OscInitStruct.PLL.PLLState = RCC_PLL_ON;
    RCC_OscInitStruct.PLL.PLLSource = RCC_PLLSOURCE_HSE;
    RCC_OscInitStruct.PLL.PLLMUL = RCC_PLL_MUL9;
    if (HAL_RCC_OscConfig(&RCC_OscInitStruct) != HAL_OK)
    {
        Error_Handler();
    }

    /** Initializes the CPU, AHB and APB buses clocks
     */
    RCC_ClkInitStruct.ClockType = RCC_CLOCKTYPE_HCLK | RCC_CLOCKTYPE_SYSCLK | RCC_CLOCKTYPE_PCLK1 | RCC_CLOCKTYPE_PCLK2;
    RCC_ClkInitStruct.SYSCLKSource = RCC_SYSCLKSOURCE_PLLCLK;
    RCC_ClkInitStruct.AHBCLKDivider = RCC_SYSCLK_DIV1;
    RCC_ClkInitStruct.APB1CLKDivider = RCC_HCLK_DIV2;
    RCC_ClkInitStruct.APB2CLKDivider = RCC_HCLK_DIV1;

    if (HAL_RCC_ClockConfig(&RCC_ClkInitStruct, FLASH_LATENCY_2) != HAL_OK)
    {
        Error_Handler();
    }
}

/**
 * @brief USART1 Initialization Function
 * @param None
 * @retval None
 */
static void MX_USART1_UART_Init(void)
{

    /* USER CODE BEGIN USART1_Init 0 */

    /* USER CODE END USART1_Init 0 */

    /* USER CODE BEGIN USART1_Init 1 */

    /* USER CODE END USART1_Init 1 */
    huart1.Instance = USART1;
    huart1.Init.BaudRate = 115200;
    huart1.Init.WordLength = UART_WORDLENGTH_8B;
    huart1.Init.StopBits = UART_STOPBITS_1;
    huart1.Init.Parity = UART_PARITY_NONE;
    huart1.Init.Mode = UART_MODE_TX_RX;
    huart1.Init.HwFlowCtl = UART_HWCONTROL_NONE;
    huart1.Init.OverSampling = UART_OVERSAMPLING_16;
    if (HAL_UART_Init(&huart1) != HAL_OK)
    {
        Error_Handler();
    }
    /* USER CODE BEGIN USART1_Init 2 */

    /* USER CODE END USART1_Init 2 */
}

/**
 * @brief GPIO Initialization Function
 * @param None
 * @retval None
 */
static void MX_GPIO_Init(void)
{
    GPIO_InitTypeDef GPIO_InitStruct = {0};
    /* USER CODE BEGIN MX_GPIO_Init_1 */
    /* USER CODE END MX_GPIO_Init_1 */

    /* GPIO Ports Clock Enable */
    __HAL_RCC_GPIOE_CLK_ENABLE();
    __HAL_RCC_GPIOC_CLK_ENABLE();
    __HAL_RCC_GPIOB_CLK_ENABLE();
    __HAL_RCC_GPIOD_CLK_ENABLE();
    __HAL_RCC_GPIOA_CLK_ENABLE();

    /*Configure GPIO pin Output Level */
    HAL_GPIO_WritePin(GPIOE, SEVENSEG2_C_Pin | SEVENSEG2_D_Pin | SEVENSEG2_E_Pin | SEVENSEG2_F_Pin | SEVENSEG2_G_Pin | SEVENSEG2_DP_Pin | SEVENSEG3_A_Pin | SEVENSEG3_B_Pin | SEVENSEG3_C_Pin | SEVENSEG3_D_Pin | SEVENSEG3_E_Pin | SEVENSEG3_F_Pin | SEVENSEG3_G_Pin | SEVENSEG3_DP_Pin | SEVENSEG2_A_Pin | SEVENSEG2_B_Pin, GPIO_PIN_RESET);

    /*Configure GPIO pin Output Level */
    HAL_GPIO_WritePin(GPIOB, LED0_Pin | LED1_Pin | LED2_Pin | LED10_Pin | LED11_Pin | LED12_Pin | LED13_Pin | LED14_Pin | LED15_Pin | LED5_Pin | LED6_Pin | LED7_Pin | LED8_Pin | LED9_Pin, GPIO_PIN_RESET);

    /*Configure GPIO pin Output Level */
    HAL_GPIO_WritePin(GPIOD, SEVENSEG1_A_Pin | SEVENSEG1_B_Pin | SEVENSEG1_C_Pin | SEVENSEG1_D_Pin | SEVENSEG1_E_Pin | SEVENSEG1_F_Pin | SEVENSEG1_G_Pin | SEVENSEG1_DP_Pin | SEVENSEG0_A_Pin | SEVENSEG0_B_Pin | SEVENSEG0_C_Pin | SEVENSEG0_D_Pin | SEVENSEG0_E_Pin | SEVENSEG0_F_Pin | SEVENSEG0_G_Pin | SEVENSEG0_DP_Pin, GPIO_PIN_RESET);

    /*Configure GPIO pins : SEVENSEG2_C_Pin SEVENSEG2_D_Pin SEVENSEG2_E_Pin SEVENSEG2_F_Pin
                             SEVENSEG2_G_Pin SEVENSEG2_DP_Pin SEVENSEG3_A_Pin SEVENSEG3_B_Pin
                             SEVENSEG3_C_Pin SEVENSEG3_D_Pin SEVENSEG3_E_Pin SEVENSEG3_F_Pin
                             SEVENSEG3_G_Pin SEVENSEG3_DP_Pin SEVENSEG2_A_Pin SEVENSEG2_B_Pin */
    GPIO_InitStruct.Pin = SEVENSEG2_C_Pin | SEVENSEG2_D_Pin | SEVENSEG2_E_Pin | SEVENSEG2_F_Pin | SEVENSEG2_G_Pin | SEVENSEG2_DP_Pin | SEVENSEG3_A_Pin | SEVENSEG3_B_Pin | SEVENSEG3_C_Pin | SEVENSEG3_D_Pin | SEVENSEG3_E_Pin | SEVENSEG3_F_Pin | SEVENSEG3_G_Pin | SEVENSEG3_DP_Pin | SEVENSEG2_A_Pin | SEVENSEG2_B_Pin;
    GPIO_InitStruct.Mode = GPIO_MODE_OUTPUT_PP;
    GPIO_InitStruct.Pull = GPIO_NOPULL;
    GPIO_InitStruct.Speed = GPIO_SPEED_FREQ_LOW;
    HAL_GPIO_Init(GPIOE, &GPIO_InitStruct);

    /*Configure GPIO pins : BTN5_Pin BTN6_Pin BTN7_Pin BTN0_Pin
                             BTN1_Pin BTN2_Pin BTN3_Pin BTN4_Pin */
    GPIO_InitStruct.Pin = BTN5_Pin | BTN6_Pin | BTN7_Pin | BTN0_Pin | BTN1_Pin | BTN2_Pin | BTN3_Pin | BTN4_Pin;
    GPIO_InitStruct.Mode = GPIO_MODE_IT_RISING;
    GPIO_InitStruct.Pull = GPIO_NOPULL;
    HAL_GPIO_Init(GPIOC, &GPIO_InitStruct);

    /*Configure GPIO pins : SW0_Pin SW1_Pin SW2_Pin SW3_Pin
                             SW4_Pin SW5_Pin SW6_Pin SW7_Pin */
    GPIO_InitStruct.Pin = SW0_Pin | SW1_Pin | SW2_Pin | SW3_Pin | SW4_Pin | SW5_Pin | SW6_Pin | SW7_Pin;
    GPIO_InitStruct.Mode = GPIO_MODE_INPUT;
    GPIO_InitStruct.Pull = GPIO_NOPULL;
    HAL_GPIO_Init(GPIOC, &GPIO_InitStruct);

    /*Configure GPIO pins : LED0_Pin LED1_Pin LED2_Pin LED10_Pin
                             LED11_Pin LED12_Pin LED13_Pin LED14_Pin
                             LED15_Pin LED5_Pin LED6_Pin LED7_Pin
                             LED8_Pin LED9_Pin */
    GPIO_InitStruct.Pin = LED0_Pin | LED1_Pin | LED2_Pin | LED10_Pin | LED11_Pin | LED12_Pin | LED13_Pin | LED14_Pin | LED15_Pin | LED5_Pin | LED6_Pin | LED7_Pin | LED8_Pin | LED9_Pin;
    GPIO_InitStruct.Mode = GPIO_MODE_OUTPUT_PP;
    GPIO_InitStruct.Pull = GPIO_NOPULL;
    GPIO_InitStruct.Speed = GPIO_SPEED_FREQ_LOW;
    HAL_GPIO_Init(GPIOB, &GPIO_InitStruct);

    /*Configure GPIO pins : SEVENSEG1_A_Pin SEVENSEG1_B_Pin SEVENSEG1_C_Pin SEVENSEG1_D_Pin
                             SEVENSEG1_E_Pin SEVENSEG1_F_Pin SEVENSEG1_G_Pin SEVENSEG1_DP_Pin
                             SEVENSEG0_A_Pin SEVENSEG0_B_Pin SEVENSEG0_C_Pin SEVENSEG0_D_Pin
                             SEVENSEG0_E_Pin SEVENSEG0_F_Pin SEVENSEG0_G_Pin SEVENSEG0_DP_Pin */
    GPIO_InitStruct.Pin = SEVENSEG1_A_Pin | SEVENSEG1_B_Pin | SEVENSEG1_C_Pin | SEVENSEG1_D_Pin | SEVENSEG1_E_Pin | SEVENSEG1_F_Pin | SEVENSEG1_G_Pin | SEVENSEG1_DP_Pin | SEVENSEG0_A_Pin | SEVENSEG0_B_Pin | SEVENSEG0_C_Pin | SEVENSEG0_D_Pin | SEVENSEG0_E_Pin | SEVENSEG0_F_Pin | SEVENSEG0_G_Pin | SEVENSEG0_DP_Pin;
    GPIO_InitStruct.Mode = GPIO_MODE_OUTPUT_PP;
    GPIO_InitStruct.Pull = GPIO_NOPULL;
    GPIO_InitStruct.Speed = GPIO_SPEED_FREQ_LOW;
    HAL_GPIO_Init(GPIOD, &GPIO_InitStruct);

    /* EXTI interrupt init*/
    HAL_NVIC_SetPriority(EXTI9_5_IRQn, 0, 0);
    HAL_NVIC_EnableIRQ(EXTI9_5_IRQn);

    HAL_NVIC_SetPriority(EXTI15_10_IRQn, 0, 0);
    HAL_NVIC_EnableIRQ(EXTI15_10_IRQn);

    /* USER CODE BEGIN MX_GPIO_Init_2 */
    /* USER CODE END MX_GPIO_Init_2 */
}

/* USER CODE BEGIN 4 */

/* USER CODE END 4 */

/**
 * @brief  This function is executed in case of error occurrence.
 * @retval None
 */
void Error_Handler(void)
{
    /* USER CODE BEGIN Error_Handler_Debug */
    /* User can add his own implementation to report the HAL error return state */
    __disable_irq();
    while (1)
    {
    }
    /* USER CODE END Error_Handler_Debug */
}

#ifdef USE_FULL_ASSERT
/**
 * @brief  Reports the name of the source file and the source line number
 *         where the assert_param error has occurred.
 * @param  file: pointer to the source file name
 * @param  line: assert_param error line source number
 * @retval None
 */
void assert_failed(uint8_t *file, uint32_t line)
{
    /* USER CODE BEGIN 6 */
    /* User can add his own implementation to report the file name and line number,
       ex: printf("Wrong parameters value: file %s on line %d\r\n", file, line) */
    /* USER CODE END 6 */
}
#endif /* USE_FULL_ASSERT */
