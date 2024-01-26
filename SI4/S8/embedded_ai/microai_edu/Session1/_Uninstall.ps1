# Arm GNU Toolchain
"Uninstalling Arm GNU Toolchain."
choco uninstall -y gcc-arm-embedded
if ($?) {
    "Arm GNU Toolchain uninstalled."
} else {
    Write-Error "Arm GNU Toolchain uninstallation failed!"
}

# Make
"Uninstalling Make."
choco uninstall -y make
if ($?) {
    "Make uninstalled."
} else {
    Write-Error "Make uninstallation failed!"
}

# OpenOCD
"Uninstalling OpenOCD."
choco uninstall -y openocd
if ($?) {
    "OpenOCD uninstalled."
} else {
    Write-Error "OpenOCD uninstallation failed!"
}

# cURL
"Uninstalling cURL."
choco uninstall -y curl
if ($?) {
    "cURL uninstalled."
} else {
    Write-Error "cURL uninstallation failed!"
}

# Chocolatey
Write-Warning "Chocolatey is not being uninstalled by this script due to the potential issues this can create."
Write-Warning "Please read https://docs.chocolatey.org/en-us/choco/uninstallation if you need to uninstall Chocolatey."

# STM32CubeF1
"Uninstalling STM32CubeF1 Firmware package."
Push-Location "$env:USERPROFILE\STM32Cube\Repository"

Remove-Item -Path stm32cube_fw_f1_v180.zip -Force
Remove-Item -Path stm32cube_fw_f1_v185.zip -Force
Remove-Item -Path STM32Cube_FW_F1_V1.8.0 -Recurse -Force 
Remove-Item -Path STM32Cube_FW_F1_V1.8.5 -Recurse -Force 

Pop-Location
"STM32CubeF1 firmware package uninstalled."

# Uninstall Driver
"Uninstalling all EDU32F103 drivers."
# Find all uninstallers in registry
$packages = Get-ItemProperty "HKLM:\SOFTWARE\Wow6432Node\Microsoft\Windows\CurrentVersion\Uninstall\*" # 32 Bit
$packages += Get-ItemProperty "HKLM:\SOFTWARE\Microsoft\Windows\CurrentVersion\Uninstall\*" # 64 Bit
# Get uninstall string for uninstallers containing "EDU32F103" in their name and run them, adding /S for dpinst to run silently
$packages | Where-Object { $_.DisplayName -and $_.DisplayName.Contains("EDU32F103") } | Select-Object -Property UninstallString | ForEach-Object { cmd /c $_.UninstallString /S }

if ($?) {
    "EDU32F103 driver uninstalled."
} else {
    Write-Error "EDU32F103 driver uninstallation failed!"
}
