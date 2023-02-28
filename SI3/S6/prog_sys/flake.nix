{
  description = "C++ example flake for Zero to Nix";

  inputs = {
    nixpkgs.url = "github:NixOS/nixpkgs";
  };

  outputs = { self, nixpkgs }:
    let
      nameValuePair = name: value: { inherit name value; };
      genAttrs = names: f: builtins.listToAttrs (map (n: nameValuePair n (f n)) names);
      allSystems = [ "x86_64-linux" "aarch64-linux" "x86_64-darwin" "aarch64-darwin" ];
      forAllSystems = f: genAttrs allSystems (system: f {
        pkgs = import nixpkgs { inherit system; };
      });
    in
    {
      packages = forAllSystems ({ pkgs }: {
        default =
          let
            cppDependencies = with pkgs; [ gcc gnumake glibc glibc.static ];
          in
          pkgs.stdenv.mkDerivation {
            name = "zero-to-nix-cpp";
            buildInputs = cppDependencies;
            src = self;
            buildPhase = ''
              make clean all
            '';
            installPhase = ''
              mkdir -p $out/bin
              cp *.exe $out/bin/
            '';
            shellHook = ''
              export LD_LIBRARY_PATH=.:$LD_LIBRARY_PATH;
            '';
          };
      });
    };
}
