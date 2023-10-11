{
  inputs = {
    nixpkgs.url = "github:NixOS/nixpkgs/nixos-22.11";
  };

  outputs = {nixpkgs, ...}: let
    pkgs = nixpkgs.legacyPackages.x86_64-linux;
  in {
    devShells.x86_64-linux.default = pkgs.mkShell {
      buildInputs = [
        # Defines a python + set of packages.
        (pkgs.python3.withPackages (ps:
          with ps;
          with pkgs.python3Packages; [
            jupyter
            ipython
            ipykernel
            isort
            notebook
            wget

            numpy
            matplotlib
            scikitimage
            scikit-learn
            scikit-learn-extra
            yapf
          ]))
      ];
    };
  };
}
