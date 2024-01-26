# Create and activate local Python venv
"Creating Python virtual environment in $(Get-Location)\.venv\"
python -m venv .venv
if ($?) {
    "Python virtual environment created."
} else {
    Write-Error "Python virtual environment creation failed!"
}
"Activating Python virtual environment in $(Get-Location)\.venv\"
.venv\Scripts\activate.ps1
if ($?) {
    "Python virtual environment activated."
} else {
    Write-Error "Python virtual environment activation failed!"
}

# Link .venv/bin to .venv/Scripts because someone decades ago decided to break cross-platform compatibility for no reason
"Linking .venv/bin to .venv/Scripts"
Push-Location .venv
New-Item -ItemType Junction -Path "bin" -Target "Scripts"
if ($?) {
    "Directory junction created."
} else {
    Write-Error "Could not create directory junction!"
}
Pop-Location

# Install West
"Installing West."
pip install west
if ($?) {
    "West successfully installed."
} else {
    Write-Error "Could not install West!"
}

# Install Zephyr SDK with ARM toolchain
"Downloading Zephyr Minimal SDK"
Invoke-WebRequest -URI "https://github.com/zephyrproject-rtos/sdk-ng/releases/download/v0.16.4/zephyr-sdk-0.16.4_windows-x86_64_minimal.7z" -OutFile zephyr-sdk-0.16.4_windows-x86_64_minimal.7z
if ($?) {
    "SDK download."
} else {
    Write-Error "Could not download SDK!"
}
"Extracting Zephyr Minimal SDK"
7z x -y zephyr-sdk-0.16.4_windows-x86_64_minimal.7z
if ($?) {
    "SDK extracted."
} else {
    Write-Error "Could not extract SDK!"
}
Push-Location zephyr-sdk-0.16.4
"Installing Zephyr SDK with ARM toolchain"
.\setup.cmd /t arm-zephyr-eabi /h /c
if ($?) {
    "SDK installed."
} else {
    Write-Error "Could not install SDK!"
}
Pop-Location

# Initalize local Zephyr workspace
"Initializing Zephyr workspace."
west init -l manifest
if ($?) {
    "Workspace initialized."
} else {
    Write-Error "Could not initialize workspace!"
}

# Clone Zephyr modules
"Downloading Zephyr workspace dependencies."
west update --fetch-opt=--filter=tree:0
if ($?) {
    "Zephyr workspace dependencies downloaded."
} else {
    Write-Error "Could not download Zephyr workspace dependencies!"
}

# Install additional dependencies
"Installing pyelftools and anytree"
# requirements.txt attemps to install half of the Internet
#pip install -r zephyr\scripts\requirements.txt
# So install only what we absolutely need manually instead
pip install pyelftools anytree
if ($?) {
    "pyelftools and anytree successfully installed."
} else {
    Write-Error "Could not install pyelftools or anytree!"
}
