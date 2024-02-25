{
  inputs = {
    nixpkgs.url = "github:NixOS/nixpkgs/nixos-unstable";
    zephyr = {
      url = "github:zephyrproject-rtos/zephyr/v3.5.0";
      flake = false;
    };

    zephyr-nix = {
      url = "github:adisbladis/zephyr-nix";
      inputs.nixpkgs.follows = "nixpkgs";
      inputs.zephyr.follows = "zephyr";
    };
  };

  outputs = {
    self,
    nixpkgs,
    zephyr-nix,
    ...
  }: let
    pkgs = import nixpkgs {
      system = "x86_64-linux";
      config.allowUnfree = true;
    };
    zephyr = zephyr-nix.packages.x86_64-linux;
    zephyrSdk = zephyr.sdk.override {
      targets = [
        "arm-zephyr-eabi"
      ];
    };
  in {
    devShell.x86_64-linux = pkgs.mkShell {
      nativeBuildInputs = with pkgs; [
          stm32cubemx
          gcc-arm-embedded
          openocd
          gnumake
          gdb
          cmake
          python3
          ninja
          dtc
          wget
          zephyrSdk
          zephyr.pythonEnv
          zephyr.hosttools-nix
        ];
    };
  };
}
