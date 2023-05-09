{
  inputs = {
    nixpkgs.url = "github:nixos/nixpkgs/nixos-22.11";
    flake-utils.url = "github:numtide/flake-utils";
  };
  outputs = {
    self,
    nixpkgs,
    flake-utils,
    ...
  }:
    flake-utils.lib.eachDefaultSystem (system: let
      pkgs = import nixpkgs {inherit system;};

      # from hello.c
      hello-world = pkgs.stdenv.mkDerivation {
        name = "hello-world";
        nativeBuildInputs = [ pkgs.gcc ];
        src = ./src;
        installPhase = ''
            mkdir -p $out/bin
            mv hello.exe $out/bin/
        '';
      };
    in {
      defaultPackage = pkgs.dockerTools.buildLayeredImage {
        name = "hello-nix";
        config = {
          Cmd = ["${hello-world}/bin/hello"];
        };
      };
    });
}
