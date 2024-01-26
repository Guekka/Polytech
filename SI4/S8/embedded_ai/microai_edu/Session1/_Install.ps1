# Chocolatey
"Installing Chocolatey."
Set-ExecutionPolicy Bypass -Scope Process -Force
[System.Net.ServicePointManager]::SecurityProtocol = [System.Net.ServicePointManager]::SecurityProtocol -bor 3072
Invoke-Expression ((New-Object System.Net.WebClient).DownloadString('https://community.chocolatey.org/install.ps1'))
if ($?) {
    "Chocolatey installed."
} else {
    Write-Error "Chocolatey installation failed!"
}

# Arm GNU Toolchain
"Installing Arm GNU Toolchain."
choco install -y gcc-arm-embedded
if ($?) {
    "Arm GNU Toolchain installed."
} else {
    Write-Error "Arm GNU Toolchain installation failed!"
}

# Make
"Installing Make."
choco install -y make
if ($?) {
    "Make installed."
} else {
    Write-Error "Make installation failed!"
}

# OpenOCD
"Installing OpenOCD."
choco install -y openocd
if ($?) {
    "OpenOCD installed."
} else {
    Write-Error "OpenOCD installation failed!"
}

# cURL
"Installing cURL."
choco install -y curl
if ($?) {
    "cURL installed."
} else {
    Write-Error "cURL installation failed!"
}

# STM32CubeF1
$stmRepoPath = "$env:USERPROFILE\STM32Cube\Repository"
if (Test-Path -Path "$stmRepoPath\STM32Cube_FW_F1_V1.8.5") {
    "Skipping STM32CubeF1 Firmware package installation: $stmRepoPath\STM32Cube_FW_F1_V1.8.5 already exists"
} else {
    "Installing STM32CubeF1 Firmware package."
    New-Item -ItemType Directory -Path "$stmRepoPath" -Force 
    $userAgent = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36"
    # Copy from current directory, by default try to download from ST server below
    #Copy-Item -Path ".\en.stm32cubef1.zip" -Destination "$stmRepoPath\stm32cube_fw_f1_v180.zip"
    #Copy-Item -Path ".\en.stm32cubef1-v1-8-5.zip" -Destination "$stmRepoPath\stm32cube_fw_f1_v185.zip"
    Push-Location "$stmRepoPath"
    "Downloading STM32CubeF1 v1.8.0."
    # Neither Invoke-WebRequest nor built-in curl support HTTP/2, required by ST server so use Chocolatey-installed curl instead 
    # Invoke-WebRequest supports HTTP2 in Powershell 7.3.0, but 5.1 is installed by default
    $curl = "C:\ProgramData\chocolatey\bin\curl.exe"
    #Invoke-WebRequest -UserAgent "$userAgent" -Headers @{"Accept-Encoding"="gzip"} -HttpVersion 2 -URI "https://www.st.com/content/ccc/resource/technical/software/firmware/40/db/b8/d5/bd/a7/41/b1/stm32cubef1.zip/files/stm32cubef1.zip/jcr:content/translations/en.stm32cubef1.zip" -OutFile "stm32cube_fw_f1_v180.zip"
    & $curl -C - -H "User-Agent: ${userAgent}" --compressed --http2 "https://www.st.com/content/ccc/resource/technical/software/firmware/40/db/b8/d5/bd/a7/41/b1/stm32cubef1.zip/files/stm32cubef1.zip/jcr:content/translations/en.stm32cubef1.zip" -o "stm32cube_fw_f1_v180.zip"
    "Downloading STM32CubeF1 Patch v1.8.5"
    #Invoke-WebRequest -UserAgent $userAgent -Headers @{"Accept-Encoding"="gzip"} -HttpVersion 2 -URI "https://www.st.com/content/ccc/resource/technical/software/library/group0/39/89/07/72/a0/fe/4c/71/stm32cubef1-v1-8-5/files/stm32cubef1-v1-8-5.zip/jcr:content/translations/en.stm32cubef1-v1-8-5.zip" -OutFile "stm32cube_fw_f1_v185.zip"
    & $curl -C - -H "User-Agent: ${userAgent}" --compressed --http2 "https://www.st.com/content/ccc/resource/technical/software/library/group0/39/89/07/72/a0/fe/4c/71/stm32cubef1-v1-8-5/files/stm32cubef1-v1-8-5.zip/jcr:content/translations/en.stm32cubef1-v1-8-5.zip" -o "stm32cube_fw_f1_v185.zip"

    ## Unzip
    # Expand-Archive is way too slow, use ExtractToDirectory instead and merge manually since built-in dotnet 4 does not support overwriting existing files
    #Expand-Archive -Path stm32cube_fw_f1_v180.zip -DestinationPath .\ -Force
    #Expand-Archive -Path stm32cube_fw_f1_v185.zip -DestinationPath .\ -Force
    Add-Type -Assembly "System.IO.Compression.Filesystem"

    "Extracting stm32cube_fw_f1_v185.zip"
    [System.IO.Compression.ZipFile]::ExtractToDirectory("$stmRepoPath\stm32cube_fw_f1_v185.zip", $stmRepoPath)
    # Directory extracted from stm32cube_fw_f1_v185.zip is called STM32Cube_FW_F1_V1.8.0 instead of STM32Cube_FW_F1_V1.8.5, rename
    Rename-Item -Path ".\STM32Cube_FW_F1_V1.8.0" -NewName "STM32Cube_FW_F1_V1.8.5"

    "Extracting stm32cube_fw_f1_v180.zip"
    [System.IO.Compression.ZipFile]::ExtractToDirectory("$stmRepoPath\stm32cube_fw_f1_v180.zip", $stmRepoPath)

    # Merge STM32Cube_FW_F1_V1.8.0 into STM32Cube_FW_F1_V1.8.5, excluding existing files
    "Merging STM32Cube_FW_F1_V1.8.0 into STM32Cube_FW_F1_V1.8.5."
    robocopy STM32Cube_FW_F1_V1.8.0 STM32Cube_FW_F1_V1.8.5 /E /XC /XN /XO /NFL /NDL /MOVE

    # Cleanup base 1.8.0 firmware directory to avoid conflict with the updated 1.8.5 firmware
    "Removing STM32Cube_FW_F1_V1.8.0"
    Remove-Item -Path STM32Cube_FW_F1_V1.8.0 -Recurse -Force 

    Pop-Location
    "STM32CubeF1 firmware package installed to $stmRepoPath\STM32Cube_FW_F1_V1.8.5"
}

# Install Driver
"Downloading EDU32F103 driver: InstallDriver_EDU32F103_v0.4_silent.exe"
Invoke-WebRequest -URI "https://bitbucket.org/edge-team-leat/microai_edu/downloads/InstallDriver_EDU32F103_v0.4_silent.exe" -OutFile InstallDriver_EDU32F103_v0.4_silent.exe
"Installing EDU32F103 v0.4 driver."
.\InstallDriver_EDU32F103_v0.4_silent.exe
if ($?) {
    "EDU32F103 v0.4 driver installed."
} else {
    Write-Error "EDU32F103 v0.4 driver installation failed!"
}