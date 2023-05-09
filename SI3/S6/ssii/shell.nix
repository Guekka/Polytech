{ pkgs ? import <nixpkgs> {}
}:
with pkgs;
  mkShell {
    buildInputs = [
      # Defines a python + set of packages.
      (python3.withPackages (ps:
        with ps;
        with python3Packages; [
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
	  seaborn
	  yapf
        ]))
    ];
  }
