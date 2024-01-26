# CMake
"Uninstalling CMake."
choco uninstall -y cmake
if ($?) {
    "CMake uninstalled."
} else {
    Write-Error "CMake uninstallation failed!"
}

# Ninja
"Uninstalling Ninja."
choco uninstall -y ninja
if ($?) {
    "Ninja uninstalled."
} else {
    Write-Error "Ninja uninstallation failed!"
}

# Python 3.11
"Uninstalling Python 3.11."
choco uninstall -y python311
if ($?) {
    "Python 3.11 uninstalled."
} else {
    Write-Error "Python 3.11 uninstallation failed!"
}

# Git
"Uninstalling Git."
choco uninstall -y git
if ($?) {
    "Git uninstalled."
} else {
    Write-Error "Git uninstallation failed!"
}

# DTC
"Uninstalling DTC."
choco uninstall -y dtc-msys2
if ($?) {
    "DTC uninstalled."
} else {
    Write-Error "DTC uninstallation failed!"
}

# Wget
"Uninstalling Wget."
choco uninstall -y wget
if ($?) {
    "Wget uninstalled."
} else {
    Write-Error "Wget uninstallation failed!"
}

# OpenOCD
"Uninstalling OpenOCD."
choco uninstall -y openocd
if ($?) {
    "OpenOCD uninstalled."
} else {
    Write-Error "OpenOCD uninstallation failed!"
}

# Chocolatey
Write-Warning "Chocolatey is not being uninstalled by this script due to the potential issues this can create."
Write-Warning "Please read https://docs.chocolatey.org/en-us/choco/uninstallation if you need to uninstall Chocolatey."

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
