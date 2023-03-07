{pkgs ? import <nixpkgs> {}}:
with pkgs; let
in
  mkShell {
    buildInputs = [
      android-studio
      gradle
      jdk17
    ];

    shellHook = ''
      export JAVA_HOME"=${jdk17.home};
    '';
  }
