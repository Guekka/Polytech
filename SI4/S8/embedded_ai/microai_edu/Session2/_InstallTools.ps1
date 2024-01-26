# Chocolatey
"Installing Chocolatey."
Set-ExecutionPolicy Bypass -Scope Process -Force
[System.Net.ServicePointManager]::SecurityProtocol = [System.Net.ServicePointManager]::SecurityProtocol -bor 3072
iex ((New-Object System.Net.WebClient).DownloadString('https://community.chocolatey.org/install.ps1'))
if ($?) {
    "Chocolatey installed."
} else {
    Write-Error "Chocolatey installation failed!"
}

# CMake
"Installing CMake."
choco install -y cmake --installargs 'ADD_CMAKE_TO_PATH=System'
if ($?) {
    "CMake installed."
} else {
    Write-Error "CMake installation failed!"
}

# Ninja
"Installing Ninja."
choco install -y ninja
if ($?) {
    "Ninja installed."
} else {
    Write-Error "Ninja installation failed!"
}

# Python 3.11
"Installing Python 3.11."
choco install -y python311
if ($?) {
    "Python 3.11 installed."
} else {
    Write-Error "Python 3.11 installation failed!"
}

# Git
"Installing Git."
choco install -y git
if ($?) {
    "Git installed."
} else {
    Write-Error "Git installation failed!"
}

# DTC
"Installing DTC."
choco install -y dtc-msys2
if ($?) {
    "DTC installed."
} else {
    Write-Error "DTC installation failed!"
}

# Wget
"Installing Wget."
choco install -y wget
if ($?) {
    "Wget installed."
} else {
    Write-Error "Wget installation failed!"
}

# OpenOCD
"Installing OpenOCD."
choco install -y openocd
if ($?) {
    "OpenOCD installed."
} else {
    Write-Error "OpenOCD installation failed!"
}

# Install Driver
"Downloading EDU32F103 driver: InstallDriver_EDU32F103_v0.3_silent.exe"
Invoke-WebRequest -URI "https://bitbucket.org/edge-team-leat/microai_edu/downloads/InstallDriver_EDU32F103_v0.3_silent.exe" -OutFile InstallDriver_EDU32F103_v0.3_silent.exe
"Installing EDU32F103 driver."
.\InstallDriver_EDU32F103_v0.3_silent.exe
if ($?) {
    "EDU32F103 driver installed."
} else {
    Write-Error "EDU32F103 driver installation failed!"
}