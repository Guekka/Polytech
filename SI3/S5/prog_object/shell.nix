{ pkgs ? import <nixpkgs> {} }:
  pkgs.mkShell {
      # nativeBuildInputs is usually what you want -- tools you need to run
      nativeBuildInputs = [ pkgs.jdk pkgs.jetbrains.idea-ultimate pkgs.javaPackages.junit_4_12 ];
  }

