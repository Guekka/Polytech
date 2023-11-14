{
  inputs = {
    nixpkgs.url = "github:NixOS/nixpkgs/nixos-23.05";
  };

  outputs = {nixpkgs, ...}: let
    pkgs = import nixpkgs {
      system = "x86_64-linux";
      config.allowUnfree = true;
    };
  in {
    devShells.x86_64-linux.default = pkgs.mkShell {
      buildInputs = [
        # Defines a python + set of packages.
        (pkgs.python3.withPackages (ps:
          with ps;
          with pkgs.python3Packages; [
            yapf
          ]))
      ];
    };
  };
}
