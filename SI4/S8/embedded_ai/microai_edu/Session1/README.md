STM32CubeMX blank project for EDU32F103 v0.4 board using Makefile and including configurations for VS Code.

## Requirements

### System software

#### Ubuntu
- VS Code: https://code.visualstudio.com/sha/download?build=stable&os=linux-deb-x64
- STM32CubeMX: https://sw-center.st.com/packs/resource/library/stm32cube_mx_v6100-lin.zip
- ARM-None-EABI toolchain: `sudo apt install gcc-arm-none-eabi`
- GDB MultiArch (or ARM-None-EABI): `sudo apt install gdb-multiarch`
- OpenOCD: `sudo apt install openocd`
- Make: `sudo apt install make`

#### Windows
- VS Code: https://code.visualstudio.com/sha/download?build=stable&os=win32-x64-user
- STM32CubeMX: https://www.st.com/content/ccc/resource/technical/software/sw_development_suite/group0/d7/f0/6a/65/f9/28/4d/31/stm32cubemx-win-v6-10-0/files/stm32cubemx-win-v6-10-0.zip/jcr:content/translations/en.stm32cubemx-win-v6-10-0.zip

To automatically install all the following dependencies and the STM32Cube MCU package, run the "Install.cmd" script. Otherwise, install them manually:
- Arm GNU Toolchain:
    - https://developer.arm.com/-/media/Files/downloads/gnu/13.2.rel1/binrel/arm-gnu-toolchain-13.2.rel1-mingw-w64-i686-arm-none-eabi.exe
    - Check `Add path to environment variable` at the end of installation.
    - OR: `choco install -y gcc-arm-embedded`
- Chocolatey: `Set-ExecutionPolicy Bypass -Scope Process -Force; [System.Net.ServicePointManager]::SecurityProtocol = [System.Net.ServicePointManager]::SecurityProtocol -bor 3072; iex ((New-Object System.Net.WebClient).DownloadString('https://community.chocolatey.org/install.ps1'))`
- Make: `choco install make`
- OpenOCD: `choco install -y openocd`
- EDU32F103 Driver: https://bitbucket.org/edge-team-leat/microai_edu/downloads/InstallDriver_EDU32F103_v0.3.exe

### STM32Cube MCU package
- STM32CubeF1 (rename to `stm32cube_fw_f1_v180.zip`): https://www.st.com/content/ccc/resource/technical/software/firmware/40/db/b8/d5/bd/a7/41/b1/stm32cubef1.zip/files/stm32cubef1.zip/jcr:content/translations/en.stm32cubef1.zip
- STM32CubeF1 1.8.5 patch (rename to `stm32cube_fw_f1_v185.zip`): https://www.st.com/content/ccc/resource/technical/software/library/group0/39/89/07/72/a0/fe/4c/71/stm32cubef1-v1-8-5/files/stm32cubef1-v1-8-5.zip/jcr:content/translations/en.stm32cubef1-v1-8-5.zip

Extract to ~/STM32Cube/Repository, rename directory from v1.8.0 to v1.8.5

### VS Code extensions
Extensions are suggested when first opening the project, otherwise call the "Extensions: Show Recommended Extensions" command with Ctrl+Shift+P, or install them manually from VSIX:
- Microsoft C/C++: https://marketplace.visualstudio.com/items?itemName=ms-vscode.cpptools
- Microsoft Makefile Tools: https://marketplace.visualstudio.com/items?itemName=ms-vscode.makefile-tools
- Microsoft Serial Monitor: https://marketplace.visualstudio.com/items?itemName=ms-vscode.vscode-serial-monitor
- Marus Cortex-Debug: https://marketplace.visualstudio.com/items?itemName=marus25.cortex-debug
- Marus Cortex-Debug Device Support Pack STM32F1: https://marketplace.visualstudio.com/items?itemName=marus25.cortex-debug-dp-stm32f1

## How to use

### Generate base project source code with STM32CubeMX
- Start STM32CubeMX
- Load `EDU32F103_v0.4.ioc`
- Generate Code

### Open the project in VS Code
- Start VS Code
- Open this directory
- Install the recommended extensions

### Edit the source code
- In VS Code, open the `Core/Src/main.c` file
- Insert the following source code between the `USER CODE BEGIN WHILE` and `USER CODE END WHILE` markers:
```
    static unsigned int i = 0;

	  static char send_msg[255] = {'\0'};
	  size_t len = snprintf(send_msg, 255, "Hellord! %u\r\n", i);
	  HAL_UART_Transmit(&huart1, (const uint8_t *)send_msg, len, 0x200);

    i++;

    HAL_Delay(1000);
```
- You can edit the source code as you please but make sure to only edit inside the `USER CODE BEGIN` and `USER CODE END` markers

### Build and run with VS Code
- Make sure the EDU32F103 board is plugged in to the USB port, you may need to enter bootloader mode (press and hold `BOOT0` then press and release `RST#`, finally release `BOOT0` after a couple of seconds)
- Click `Start Debugging` in the `Run` menu, this will compile the source code, send the binary to the MCU, start it, and open the debugger

### Visualize the serial output
- In VS Code, click `New Terminal` in the `Terminal`
- Select the `Serial Monitor` tab in the bottom terminal window
- Select the correct serial port (e.g., `/dev/ttyUSB1`)
- Make sure baud rate is set to 115200
- Click `Start Monitoring`

## Command line usage

### Build
```
make
```

### Flash
```
openocd -f openocd.cfg -c init -c "reset halt; flash write_image erase ./build/EDU32F103_v0.4.elf; reset"
```
