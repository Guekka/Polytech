Zephyr project for EDU32F103 v0.4 board including configurations for VS Code.

## Requirements

### Ubuntu (22.04 or newer)

Install required system tools:
```
sudo apt install -y cmake ninja-build python3-pip python3-venv git device-tree-compiler wget openocd
```

Create and activate a Python virtual environment:
```
python3 -m venv .venv
source .venv/bin/activate
```

Install West, pyelftools and anytree:
```
pip install west pyelftools anytree
```

Install Zephyr SDK with ARM toolchain:
```
wget https://github.com/zephyrproject-rtos/sdk-ng/releases/download/v0.16.4/zephyr-sdk-0.16.4_linux-x86_64_minimal.tar.xz
tar -xvf zephyr-sdk-0.16.4_linux-x86_64_minimal.tar.xz
pushd zephyr-sdk-0.16.4
./setup.sh -t arm-zephyr-eabi -h -c
popd
```

Initialize workspace:
```
west init -l manifest
```

Clone workspace dependencies (zephyr, cmsis, stm32_hal):
```
west update --fetch-opt=--filter=tree:0
```

### Windows

Execute `InstallTools.cmd` to install the following required system tools (as administrator):
 - Chocolatey
 - CMake
 - Ninja
 - Python3.11
 - Git
 - DTC
 - Wget
 - OpenOCD
 - EDU32F103 driver

Execute `ConfigureWorkspace.cmd` to set up the local workspace (as user):
 - Create a Python virtual environment
 - Install West
 - Install Zephyr SDK with ARM toolchain
 - Initialize workspace
 - Clone workspace dependencies (zephyr, cmsis, stm32_hal)
 - Install pyelftools

### VS Code extensions
Extensions are suggested when first opening the project, otherwise call the "Extensions: Show Recommended Extensions" command with Ctrl+Shift+P, or install them manually from VSIX:
- Microsoft C/C++: https://marketplace.visualstudio.com/items?itemName=ms-vscode.cpptools
- Microsoft Serial Monitor: https://marketplace.visualstudio.com/items?itemName=ms-vscode.vscode-serial-monitor
- Marus Cortex-Debug: https://marketplace.visualstudio.com/items?itemName=marus25.cortex-debug
- Marus Cortex-Debug Device Support Pack STM32F1: https://marketplace.visualstudio.com/items?itemName=marus25.cortex-debug-dp-stm32f1

## How to use

### VS Code

#### Open the project in VS Code
- Start VS Code
- Open this directory
- Install the recommended extensions

#### Build and run with VS Code
- Make sure the EDU32F103 board is plugged in to the USB port, you may need to enter bootloader mode (press and hold `BOOT0` then press and release `RST#`, finally release `BOOT0` after a couple of seconds)
- Click `Start Debugging` in the `Run` menu, this will compile the source code, send the binary to the MCU, start it, and open the debugger

#### Visualize the serial output
- In VS Code, click `New Terminal` in the `Terminal`
- Select the `Serial Monitor` tab in the bottom terminal window
- Select the correct serial port (e.g., `/dev/ttyUSB1`)
- Make sure baud rate is set to 115200
- Click `Start Monitoring`

### Command line tools

#### Activate Python virtual environment
On Linux:
```
source .venv/bin/activate
```

On Windows:
```
Set-ExecutionPolicy Bypass -Scope Process -Force
.\.venv\Scripts\Activate.ps1
```

#### Build project
```
west build -b edu32f103
```

#### Flash firmware to the target
```
west flash
```

#### Debug project on the target
```
west debug
```

#### Read serial output
On Linux:
```
minicom -D/dev/ttyUSB1
```
