{pkgs ? import <nixpkgs> {}}:
pkgs.mkShell {
  nativeBuildInputs = with pkgs; [
    gcc
    gdb
    glibc.static
    glibc
    strace
  ];
}
