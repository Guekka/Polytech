{
  inputs = {
    nixpkgs.url = "github:NixOS/nixpkgs/nixos-unstable";
  };

  outputs = {
    self,
    nixpkgs,
    ...
  } @ inputs: let
    pkgs = import nixpkgs {
      system = "x86_64-linux";
      config.allowUnfree = true;
    };
  in {
    devShell.x86_64-linux = pkgs.mkShell {
      nativeBuildInputs = let
        python = pkgs.python3.withPackages (with pkgs.python3Packages; [
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
          python3
          ninja
          dtc
        ];
    };
  };
}
