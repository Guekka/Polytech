{
  inputs = {
    nixpkgs.url = "github:NixOS/nixpkgs/nixos-unstable";
  };

  nixConfig.sandbox = "relaxed";


  outputs = {
    self,
    nixpkgs,
    ...
  }: let
    pkgs = import nixpkgs {
      system = "x86_64-linux";
      config.allowUnfree = true;
    };

        zephyrSdk = import ./sdk.nix {
          inherit pkgs;
          system = "x86_64-linux";
        };
  in {
    devShell.x86_64-linux = pkgs.mkShell {
      nativeBuildInputs = let
        python = pkgs.python3.withPackages (packages:
          with packages; [
            west
            pyelftools
            anytree
          ]);
      in
        with pkgs; [
          stm32cubemx
          gcc-arm-embedded
          openocd
          gnumake
          gdb
          cmake
          python
          ninja
          dtc
          wget
          zephyrSdk
        ];
      # When shell is created, start with a few Zephyr related environment variables defined.
      shellHook = ''
        export ZEPHYR_TOOLCHAIN_VARIANT=zephyr
        export ZEPHYR_SDK_INSTALL_DIR=${zephyrSdk}/${zephyrSdk.pname}-${zephyrSdk.version}
      '';
    };
  };
}
