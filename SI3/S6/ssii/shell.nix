{ pkgs ? import (fetchTarball "https://github.com/NixOS/nixpkgs/archive/c17904b9802a35acf088ee315b669bd4be8f7633.tar.gz") {}
}:
with pkgs;
  mkShell {
    buildInputs = [
      # Defines a python + set of packages.
      (python3.withPackages (ps:
        with ps;
        with python3Packages; [
          vscodium
          jupyter
          ipython
          ipykernel
          isort
          notebook
          wget

          numpy
          matplotlib
          librosa
          scikitimage
        ]))
    ];
  }
