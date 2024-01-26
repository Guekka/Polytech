{
  pkgs ? import <nixpkgs> {},
  system ? builtins.currentSystem,
}:
with pkgs; let
  pname = "zephyr-sdk";
  version = "0.16.4";

  # SDK uses slightly different system names, use this variable to fix them up for use in the URL
  system_fixup = {
    x86_64-linux = "linux-x86_64";
    aarch64-linux = "linux-aarch64";
    x86_64-darwin = "macos-x86_64";
    aarch64-darwin = "macos-aarch64";
  };

  # Use the minimal installer as starting point
  url = "https://github.com/zephyrproject-rtos/sdk-ng/releases/download/v${version}/${pname}-${version}_${system_fixup.${system}}_minimal.tar.xz";
in
  stdenv.mkDerivation {
    # Allows derivation to access network
    #
    # This is needed due to the way setup.sh works with the minimal toolchain. The script uses wget to retrieve the
    # specific toolchain variants over the network.
    #
    # Users of this package must set options to indicate that the sandbox conditions can be relaxed for this package.
    # These are:
    # - In the appropriate nix.conf file (depends on multi vs single user nix installation), add the line: sandbox = relaxed
    # - When used in a flake, set the flake's config with this line: nixConfig.sandbox = "relaxed";
    # - Same as above, but disabling the sandbox completely: nixConfig.sandbox = false;
    # - From the command line with nix <command>, add one of these options:
    #   - --option sandbox relaxed
    #   - --option sandbox false
    #   - --no-sandbox
    #   - --relaxed-sandbox
    __noChroot = true;

    inherit pname version;

    src = builtins.fetchurl {
      inherit url;
      sha256 = "sha256:0b8bagby1l2skksbvk0l8av2392m97klhgfxzlmdaxgy11zxkf9w";
    };

    # Our source is right where the unzip happens, not in a "src/" directory (default)
    sourceRoot = ".";

    # Build scripts require each packages
    nativeBuildInputs = [pkgs.cacert pkgs.which pkgs.wget pkgs.cmake pkgs.autoPatchelfHook];

    # For patching elf
    buildInputs = [ stdenv.cc.cc pkgs.python38];

    # Required to prevent CMake running in configuration phases
    dontUseCmakeConfigure = true;

    # Run setup script
    # TODO: Allow user to specify which targets to install
    # TODO: Figure out if host tools could be ported to Nix
    buildPhase = ''
      bash ${pname}-${version}/setup.sh -t arm-zephyr-eabi
    '';

    # Move SDK directory into installation directory
    installPhase = ''
      mkdir -p $out
      mv $name $out/
    '';
  }
