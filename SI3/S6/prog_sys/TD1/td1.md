# Programmation système : TD1

## Exercice 1 : expliquer l'architecture du code fourni

Le code fourni réalise un tri d'une liste "aléatoire" (avec seed fixée).

Pour éviter de dépendre d'un seul algorithme de tri, la fonction `main` a uniquement accès au prototype de `sort(int *array, int size)`. Les implémentations sont fournies dans différents fichiers comme `merge.c` ou `bubble.c`.

Ce mécanisme permet de sélectionner l'implémentation à la compilation : il suffit de compiler le bon fichier. Si nous en compilons plusieurs en même temps, le *linker* émettra une erreur.

En pratique, cette manière de faire est assez limitée. Si les implémentations sont fixées, on utilisera plutôt des macros. Si l'utilisateur peut ajouter son implémentation, alors nous pourrons utiliser des bibliothèques dynamiques que nous verrons par la suite.

## Exercice 2 : dépendances de `basicExe`

```sh
$ ldd tri_bubble-basicExe.exe

        linux-vdso.so.1 (0x00007ffff7fc4000)
        libc.so.6 => /nix/store/lqz6hmd86viw83f9qll2ip87jhb7p1ah-glibc-2.35-224/lib/libc.so.6 (0x00007ffff7db5000)
        /nix/store/lqz6hmd86viw83f9qll2ip87jhb7p1ah-glibc-2.35-224/lib/ld-linux-x86-64.so.2 => /nix/store/lqz6hmd86viw83f9qll2ip87jhb7p1ah-glibc-2.35-224/lib64/ld-linux-x86-64.so.2 (0x00007ffff7fc6000)
```

Nous pouvons voir que nous dépendons de 3 bibliothèques dynamiques.

## Exercice 3 : comment rendre le programme indépendant ?

Le programme sera indépendant si on le compile avec des bibliothèques statiques. Pour cela, il nous suffit d'utiliser le paramètre `-static` de `gcc`.

Nous constatons que le programme utilisant des bibliothèques statiques est bien plus grand. C'est parfaitement logique : il inclut toutes les fonctions dont il a besoin (et même plus).

Il contient donc beaucoup plus de code.

## Exercice 4 : bibliothèque statique

Pour créer une bibliothèque statique et nous en servir, nous utilisons les commandes suivantes :
```sh
ar -r lib.a fichier.o
ranlib lib.a
gcc -o exe main.o lib.a
```
Nous constatons que cette version a une taille très légèrement inférieure a la première version, `basicExe`. En effet, le passage par une bibliothèque a permis à `gcc` de détecter que le fichier `unused.o` est inutilisé. Il ne sera donc pas inclus.

```sh
$ size tri_bubble-staticLib.exe
   text    data     bss     dec     hex filename
   3383     668      12    4063     fdf tri_bubble-staticLib.exe

$ size tri_bubble-basicExe.exe
   text    data     bss     dec     hex filename
   4822     668      12    5502    157e tri_bubble-basicExe.exe
```
La partie impactée est `text` qui contient les instructions.

```sh
$ diff <(nm -j tri_bubble-basicExe.exe) <(nm -j tri_bubble-staticLib.exe)
2,4d1
< bar
< bar.localalias
< baz
21d17
< foo
35d30
< qux
```
En effet, certains symboles ont bien été supprimés. Ils semblent correspondre à ceux présents dans `unused.c`.

## Exercice 5 : bibliothèque partagée

Pour créer une bibliothèque partagée et nous en servir, nous utilisons les commandes suivantes :
```sh
gcc -fpic -shared -o lib.so fichier.o
gcc -o exe main.o lib.so
```

## Exercice 6 : activation de tous les algorithmes

Voir `Makefile`.

## Exercice 7 : chargement dynamique

Voir `Makefile`, `main_dynload.c` et `load_library.c`.

## Exercice 8 : ajout d'un nouvel algorithmme de tri

L'avantage du chargement dynamique est qu'il permet de rajouter un nouvel algorithme facilement, après compilation et distribution.

Nous avons seulement à créer une nouvelle bibliothèque partagée (`.so`) et à la mettre dans un dossier spécifié dans le `LD_LIBRARY_PATH`. Le programme pourra maintenant l'utiliser, sans recompilation. Il faudra simplement lui passer le nom en argument.

## Exercice 9 : chargement sans argument

Voir `main_dynload.c` et `load_library.c`.

## Exercice 10 : bibliothèques utilisées par différents programmes

Python : `ldd $(which python)`
```
        linux-vdso.so.1 (0x00007fff7355d000)
        libpython3.10.so.1.0 => /nix/store/iw1vmh509hcbby8dbpsaanbri4zsq7dj-python3-3.10.10/lib/libpython3.10.so.1.0 (0x00007faef5e97000)
        libcrypt.so.1 => /nix/store/c06np1spdb2bbsfp5x3716d529mrxw7b-libxcrypt-4.4.33/lib/libcrypt.so.1 (0x00007faef5e5b000)
        libdl.so.2 => /nix/store/76l4v99sk83ylfwkz8wmwrm4s8h73rhd-glibc-2.35-224/lib/libdl.so.2 (0x00007faef5e56000)
        libm.so.6 => /nix/store/76l4v99sk83ylfwkz8wmwrm4s8h73rhd-glibc-2.35-224/lib/libm.so.6 (0x00007faef5d76000)
        libgcc_s.so.1 => /nix/store/76l4v99sk83ylfwkz8wmwrm4s8h73rhd-glibc-2.35-224/lib/libgcc_s.so.1 (0x00007faef5d5c000)
        libc.so.6 => /nix/store/76l4v99sk83ylfwkz8wmwrm4s8h73rhd-glibc-2.35-224/lib/libc.so.6 (0x00007faef5b51000)
        /nix/store/76l4v99sk83ylfwkz8wmwrm4s8h73rhd-glibc-2.35-224/lib/ld-linux-x86-64.so.2 => /nix/store/76l4v99sk83ylfwkz8wmwrm4s8h73rhd-glibc-2.35-224/lib64/ld-linux-x86-64.so.2 (0x00007faef62a9000)
```

Java : `ldd $(which java)`
```
        linux-vdso.so.1 (0x00007ffef29eb000)
        libz.so.1 => /nix/store/mg9l7phyhvi16p9g8g3g8fbyj4mr79gq-zlib-1.2.13/lib/libz.so.1 (0x00007f6d24621000)
        libjli.so => /nix/store/jkv8vb24ab371hlsp55fj953kwh01m8n-openjdk-17.0.6+10/lib/openjdk/lib/libjli.so (0x00007f6d2460d000)
        libpthread.so.0 => /nix/store/76l4v99sk83ylfwkz8wmwrm4s8h73rhd-glibc-2.35-224/lib/libpthread.so.0 (0x00007f6d24606000)
        libdl.so.2 => /nix/store/76l4v99sk83ylfwkz8wmwrm4s8h73rhd-glibc-2.35-224/lib/libdl.so.2 (0x00007f6d24601000)
        libfontconfig.so.1 => /nix/store/mv56qyvfin02c96mxrdh60p3c0j05dvn-fontconfig-2.14.0-lib/lib/libfontconfig.so.1 (0x00007f6d245b5000)
        libcups.so.2 => /nix/store/ryxz1bi7axniywwp930zsnfvq3ma85gb-cups-2.4.2-lib/lib/libcups.so.2 (0x00007f6d2452a000)
        libXinerama.so.1 => /nix/store/dd3j88zyc9p4cfjymzwz7rdv0crjx77m-libXinerama-1.1.4/lib/libXinerama.so.1 (0x00007f6d24525000)
        libXrandr.so.2 => /nix/store/r2rsbp1a7i8lznixz2y4r8gnm4gg37x8-libXrandr-1.5.2/lib/libXrandr.so.2 (0x00007f6d24516000)
        libmagic.so.1 => /nix/store/va9r2bdg56jc6qizpm6dyb97lnxfrhsy-file-5.44/lib/libmagic.so.1 (0x00007f6d244ea000)
        libgtk-3.so.0 => /nix/store/0z70flbcxfvkp3qap9jdbi6mjjadwwkx-gtk+3-3.24.36/lib/libgtk-3.so.0 (0x00007f6d23d12000)
        libgio-2.0.so.0 => /nix/store/rcwsvm3zmcpwl71b7r5f9ql599hw6f2b-glib-2.74.5/lib/libgio-2.0.so.0 (0x00007f6d23b2d000)
        libgnomevfs-2.so.0 => /nix/store/n0rh9f06228k6vmvpkzhnj1qarh2a64p-gnome-vfs-2.24.4/lib/libgnomevfs-2.so.0 (0x00007f6d23ac0000)
        libgconf-2.so.4 => /nix/store/k9hi1f417ph87q830g61z1gl791jixbb-gconf-3.2.6/lib/libgconf-2.so.4 (0x00007f6d23a7d000)
        libc.so.6 => /nix/store/76l4v99sk83ylfwkz8wmwrm4s8h73rhd-glibc-2.35-224/lib/libc.so.6 (0x00007f6d23872000)
        libfreetype.so.6 => /nix/store/2jq0rdhc7wb8fj0q82whsj9p50sdmvcv-freetype-2.12.1/lib/libfreetype.so.6 (0x00007f6d237a6000)
        libbz2.so.1 => /nix/store/k0yrr5yq9yghbvvfv0qkblyia576kg4f-bzip2-1.0.8/lib/libbz2.so.1 (0x00007f6d23793000)
        libpng16.so.16 => /nix/store/idx4lcdd1p91dpib06d5mwyhszs6w8xw-libpng-apng-1.6.39/lib/libpng16.so.16 (0x00007f6d2375a000)
        libbrotlidec.so.1 => /nix/store/6ahs2w409ysgvz4r0k1g0kypf6k3iwps-brotli-1.0.9-lib/lib/libbrotlidec.so.1 (0x00007f6d2374a000)
        libexpat.so.1 => /nix/store/inrxvaqv2zkdhg2v5vhqc0257p92wl2q-expat-2.5.0/lib/libexpat.so.1 (0x00007f6d2371f000)
        libavahi-common.so.3 => /nix/store/g98ykbyrxrl2hilf46f08yp246bmnn02-avahi-0.8/lib/libavahi-common.so.3 (0x00007f6d23710000)
        libavahi-client.so.3 => /nix/store/g98ykbyrxrl2hilf46f08yp246bmnn02-avahi-0.8/lib/libavahi-client.so.3 (0x00007f6d236fd000)
        libgnutls.so.30 => /nix/store/9c8zpqq2rnj37k8j84g68adljs6kdqmi-gnutls-3.8.0/lib/libgnutls.so.30 (0x00007f6d234e5000)
        libm.so.6 => /nix/store/76l4v99sk83ylfwkz8wmwrm4s8h73rhd-glibc-2.35-224/lib/libm.so.6 (0x00007f6d23403000)
        libX11.so.6 => /nix/store/y0ym7qckhfldadc4wbwv0hs7765v10rd-libX11-1.8.4/lib/libX11.so.6 (0x00007f6d232bf000)
        libXext.so.6 => /nix/store/qgg9iiyrp9s1cja2h9cblb9cjyy65ssz-libXext-1.3.4/lib/libXext.so.6 (0x00007f6d232aa000)
        libXrender.so.1 => /nix/store/dg1irgbmn4znzbbw2yyc99bh85a1ll3n-libXrender-0.9.10/lib/libXrender.so.1 (0x00007f6d2329d000)
        libgdk-3.so.0 => /nix/store/0z70flbcxfvkp3qap9jdbi6mjjadwwkx-gtk+3-3.24.36/lib/libgdk-3.so.0 (0x00007f6d2318f000)
        libgmodule-2.0.so.0 => /nix/store/rcwsvm3zmcpwl71b7r5f9ql599hw6f2b-glib-2.74.5/lib/libgmodule-2.0.so.0 (0x00007f6d23188000)
        libglib-2.0.so.0 => /nix/store/rcwsvm3zmcpwl71b7r5f9ql599hw6f2b-glib-2.74.5/lib/libglib-2.0.so.0 (0x00007f6d2304c000)
        libgobject-2.0.so.0 => /nix/store/rcwsvm3zmcpwl71b7r5f9ql599hw6f2b-glib-2.74.5/lib/libgobject-2.0.so.0 (0x00007f6d22fe9000)
        libpangocairo-1.0.so.0 => /nix/store/7zqg9hafk1g8z103ps6fywl89w189nz3-pango-1.50.12/lib/libpangocairo-1.0.so.0 (0x00007f6d22fd8000)
        libpango-1.0.so.0 => /nix/store/7zqg9hafk1g8z103ps6fywl89w189nz3-pango-1.50.12/lib/libpango-1.0.so.0 (0x00007f6d22f6f000)
        libharfbuzz.so.0 => /nix/store/09yg78bcs6vibbk7py1k1l9qf1im9lwj-harfbuzz-7.0.0/lib/libharfbuzz.so.0 (0x00007f6d22e57000)
        libcairo.so.2 => /nix/store/ya38n92jwdv6545zj0asli1kqdgcfili-cairo-1.16.0/lib/libcairo.so.2 (0x00007f6d22d1b000)
        libpangoft2-1.0.so.0 => /nix/store/7zqg9hafk1g8z103ps6fywl89w189nz3-pango-1.50.12/lib/libpangoft2-1.0.so.0 (0x00007f6d22cff000)
        libfribidi.so.0 => /nix/store/mb2kzr3caz3x59ssy9d1cn65iwg2b4zy-fribidi-1.0.12/lib/libfribidi.so.0 (0x00007f6d22cdf000)
        libcairo-gobject.so.2 => /nix/store/ya38n92jwdv6545zj0asli1kqdgcfili-cairo-1.16.0/lib/libcairo-gobject.so.2 (0x00007f6d22cd4000)
        libgdk_pixbuf-2.0.so.0 => /nix/store/7h9f215fx7kgmin2k35nlkfknk701dyl-gdk-pixbuf-2.42.10/lib/libgdk_pixbuf-2.0.so.0 (0x00007f6d22ca7000)
        libatk-1.0.so.0 => /nix/store/ck3mrb8yzak4hilan4cgipxpjsc7fv54-at-spi2-core-2.46.0/lib/libatk-1.0.so.0 (0x00007f6d22c7e000)
        libepoxy.so.0 => /nix/store/3w5sakz3znb378b7wlfbr7bss3myhdi3-libepoxy-1.5.10/lib/libepoxy.so.0 (0x00007f6d22b47000)
        libXi.so.6 => /nix/store/kmbps8nyg8nhnwdbcpxkbn5mmcdal5hp-libXi-1.8/lib/libXi.so.6 (0x00007f6d22b33000)
        libatk-bridge-2.0.so.0 => /nix/store/ck3mrb8yzak4hilan4cgipxpjsc7fv54-at-spi2-core-2.46.0/lib/libatk-bridge-2.0.so.0 (0x00007f6d22af6000)
        libtracker-sparql-3.0.so.0 => /nix/store/3yxdzgn2hxxpr73wzwx9rggh864x8cib-tracker-3.4.2/lib/libtracker-sparql-3.0.so.0 (0x00007f6d22964000)
        libXfixes.so.3 => /nix/store/zd5rypkk2h639y4ym6qc3l65dpka3ycc-libXfixes-6.0.0/lib/libXfixes.so.3 (0x00007f6d2295c000)
        libmount.so.1 => /nix/store/qqykcd92y9qhbpdys9z75l4p6ls50ym1-util-linux-minimal-2.38.1-lib/lib/libmount.so.1 (0x00007f6d228f9000)
        libselinux.so.1 => /nix/store/gqlj2nh329ymrawhlrhhd3wx9h5nwi38-libselinux-3.3/lib/libselinux.so.1 (0x00007f6d228cc000)
        libgthread-2.0.so.0 => /nix/store/rcwsvm3zmcpwl71b7r5f9ql599hw6f2b-glib-2.74.5/lib/libgthread-2.0.so.0 (0x00007f6d228c7000)
        libxml2.so.2 => /nix/store/hmh8yakhrp3r95b7r085m4xnzp8crz3b-libxml2-2.10.3/lib/libxml2.so.2 (0x00007f6d22761000)
        libdbus-glib-1.so.2 => /nix/store/h6yjxgxlchk7kc16jwydjdv1gpdqrqvp-dbus-glib-0.112/lib/libdbus-glib-1.so.2 (0x00007f6d22734000)
        libdbus-1.so.3 => /nix/store/q36hdg6hw9vlls7abq0y08gksqr2m2z2-dbus-1.14.4-lib/lib/libdbus-1.so.3 (0x00007f6d226dd000)
        libssl.so.3 => /nix/store/5438qhyypn1kbdnm4312v8db4mkapwhl-openssl-3.0.8/lib/libssl.so.3 (0x00007f6d22630000)
        libcrypto.so.3 => /nix/store/5438qhyypn1kbdnm4312v8db4mkapwhl-openssl-3.0.8/lib/libcrypto.so.3 (0x00007f6d221b3000)
        libavahi-glib.so.1 => /nix/store/g98ykbyrxrl2hilf46f08yp246bmnn02-avahi-0.8/lib/libavahi-glib.so.1 (0x00007f6d221ae000)
        libORBit-2.so.0 => /nix/store/q91aw6w9q0ki6zq9881hkkaxk87ssdqy-ORBit2-2.14.19/lib/libORBit-2.so.0 (0x00007f6d22138000)
        /nix/store/76l4v99sk83ylfwkz8wmwrm4s8h73rhd-glibc-2.35-224/lib/ld-linux-x86-64.so.2 => /nix/store/lqz6hmd86viw83f9qll2ip87jhb7p1ah-glibc-2.35-224/lib64/ld-linux-x86-64.so.2 (0x00007f6d24647000)
        libbrotlicommon.so.1 => /nix/store/6ahs2w409ysgvz4r0k1g0kypf6k3iwps-brotli-1.0.9-lib/lib/libbrotlicommon.so.1 (0x00007f6d22115000)
        libssp.so.0 => /nix/store/2w4k8nvdyiggz717ygbbxchpnxrqc6y9-gcc-12.2.0-lib/lib/libssp.so.0 (0x00007f6d22110000)
        libp11-kit.so.0 => /nix/store/c46qvwfvr5a9lmsfnq0gx8ddjd74wpx5-p11-kit-0.24.1/lib/libp11-kit.so.0 (0x00007f6d21fd8000)
        libidn2.so.0 => /nix/store/vv6rlzln7vhxk519rdsrzmhhlpyb5q2m-libidn2-2.3.2/lib/libidn2.so.0 (0x00007f6d21fb6000)
        libunistring.so.2 => /nix/store/qmnr18aqd08zdkhka695ici96k6nzirv-libunistring-1.0/lib/libunistring.so.2 (0x00007f6d21e0a000)
        libtasn1.so.6 => /nix/store/ysazk78whfld5c25lqf9xrzbjni0x908-libtasn1-4.19.0/lib/libtasn1.so.6 (0x00007f6d21df5000)
        libnettle.so.8 => /nix/store/qj5106amzaz4j54yhyg7iikm14lsmjym-nettle-3.8.1/lib/libnettle.so.8 (0x00007f6d21da6000)
        libhogweed.so.6 => /nix/store/qj5106amzaz4j54yhyg7iikm14lsmjym-nettle-3.8.1/lib/libhogweed.so.6 (0x00007f6d21d5a000)
        libgmp.so.10 => /nix/store/ia34rbsa6d2dalzk5f7hy5jp9zazpv24-gmp-with-cxx-6.2.1/lib/libgmp.so.10 (0x00007f6d21cba000)
        libxcb.so.1 => /nix/store/cnkr9byk03mz454yzx48jkyy0c2rlfjz-libxcb-1.14/lib/libxcb.so.1 (0x00007f6d21c8f000)
        libxkbcommon.so.0 => /nix/store/03bcgkqn4hsf2l2n0ny5xx2c1z0ga6gy-libxkbcommon-1.5.0/lib/libxkbcommon.so.0 (0x00007f6d21c49000)
        libwayland-client.so.0 => /nix/store/sg275pvghxp61hxyirxhz0jl3617f5hh-wayland-1.21.0/lib/libwayland-client.so.0 (0x00007f6d21c35000)
        libwayland-cursor.so.0 => /nix/store/sg275pvghxp61hxyirxhz0jl3617f5hh-wayland-1.21.0/lib/libwayland-cursor.so.0 (0x00007f6d21c2b000)
        libwayland-egl.so.1 => /nix/store/sg275pvghxp61hxyirxhz0jl3617f5hh-wayland-1.21.0/lib/libwayland-egl.so.1 (0x00007f6d21c26000)
        libXcursor.so.1 => /nix/store/2fn1jaymgbl5rz4m1ds7mgimgdkmcimd-libXcursor-1.2.0/lib/libXcursor.so.1 (0x00007f6d21c19000)
        libXdamage.so.1 => /nix/store/lam5038wkrnnzsddcs30mx4jjrhi3ips-libXdamage-1.1.5/lib/libXdamage.so.1 (0x00007f6d21c14000)
        libXcomposite.so.1 => /nix/store/8b686hlck6jkpxm9yx3n755cmqvcnhni-libXcomposite-0.4.5/lib/libXcomposite.so.1 (0x00007f6d21c0d000)
        libpcre2-8.so.0 => /nix/store/lm9jab801iavj3f9gjz6vc1fxhvvg2mj-pcre2-10.42/lib/libpcre2-8.so.0 (0x00007f6d21b72000)
        libffi.so.8 => /nix/store/m0fsvgc5hrn3yjwsl9fvj5m6pyjk2il2-libffi-3.4.4/lib/libffi.so.8 (0x00007f6d21b65000)
        libthai.so.0 => /nix/store/cbj48b5jcdhwmvn282gg3wkrnfvqcn0v-libthai-0.1.29/lib/libthai.so.0 (0x00007f6d21b59000)
        libgraphite2.so.3 => /nix/store/vgasd5zjsvdvq3w7ik13sfyyj1n0m5v7-graphite2-1.3.14/lib/libgraphite2.so.3 (0x00007f6d21b2e000)
        libpixman-1.so.0 => /nix/store/hncbn4imig9h0nacd2j40by7br4d64c3-pixman-0.42.2/lib/libpixman-1.so.0 (0x00007f6d21a83000)
        libEGL.so.1 => /nix/store/yl6ggfn054z9brd4rlzifn3zpiardjz2-libglvnd-1.6.0/lib/libEGL.so.1 (0x00007f6d21a6d000)
        libxcb-shm.so.0 => /nix/store/cnkr9byk03mz454yzx48jkyy0c2rlfjz-libxcb-1.14/lib/libxcb-shm.so.0 (0x00007f6d21a68000)
        libxcb-render.so.0 => /nix/store/cnkr9byk03mz454yzx48jkyy0c2rlfjz-libxcb-1.14/lib/libxcb-render.so.0 (0x00007f6d21a57000)
        libGL.so.1 => /nix/store/yl6ggfn054z9brd4rlzifn3zpiardjz2-libglvnd-1.6.0/lib/libGL.so.1 (0x00007f6d219c9000)
        librt.so.1 => /nix/store/76l4v99sk83ylfwkz8wmwrm4s8h73rhd-glibc-2.35-224/lib/librt.so.1 (0x00007f6d219c4000)
        libjpeg.so.62 => /nix/store/rvv0110i3pg28vrhadhi1qfmhpgj1azy-libjpeg-turbo-2.1.4/lib/libjpeg.so.62 (0x00007f6d21915000)
        libatspi.so.0 => /nix/store/ck3mrb8yzak4hilan4cgipxpjsc7fv54-at-spi2-core-2.46.0/lib/libatspi.so.0 (0x00007f6d218d7000)
        libicuuc.so.72 => /nix/store/jbsbrpz89j9sbk0shpqvk5jy3fddkffq-icu4c-72.1/lib/libicuuc.so.72 (0x00007f6d216d3000)
        libicui18n.so.72 => /nix/store/jbsbrpz89j9sbk0shpqvk5jy3fddkffq-icu4c-72.1/lib/libicui18n.so.72 (0x00007f6d21397000)
        libjson-glib-1.0.so.0 => /nix/store/45h24hh02xmzbzdfxfgmrflklswqzvqb-json-glib-1.6.6/lib/libjson-glib-1.0.so.0 (0x00007f6d2136c000)
        libsqlite3.so.0 => /nix/store/c651qa8kqilglkx8fhxz0ibc4yfndlm8-sqlite-3.40.1/lib/libsqlite3.so.0 (0x00007f6d2121b000)
        libblkid.so.1 => /nix/store/qqykcd92y9qhbpdys9z75l4p6ls50ym1-util-linux-minimal-2.38.1-lib/lib/libblkid.so.1 (0x00007f6d211c3000)
        libpcre.so.1 => /nix/store/x6nnam5hk44mljbk782rcbd92jlnz8r6-pcre-8.45/lib/libpcre.so.1 (0x00007f6d21149000)
        libsystemd.so.0 => /nix/store/gmr0laikghcg8dfphvsv25lclcrhf3l6-systemd-minimal-252.5/lib/libsystemd.so.0 (0x00007f6d2105e000)
        libXau.so.6 => /nix/store/qnwa2z8b9y76almh1mpp1iwnwdvrzxbj-libXau-1.0.9/lib/libXau.so.6 (0x00007f6d21057000)
        libXdmcp.so.6 => /nix/store/3bhillndvhi9pvdkr4bb145170s7h7lz-libXdmcp-1.1.3/lib/libXdmcp.so.6 (0x00007f6d2104f000)
        libdatrie.so.1 => /nix/store/4kz52y95zfn9l8pbn8f5jw1w61lgn0js-libdatrie-2019-12-20-lib/lib/libdatrie.so.1 (0x00007f6d21045000)
        libGLdispatch.so.0 => /nix/store/yl6ggfn054z9brd4rlzifn3zpiardjz2-libglvnd-1.6.0/lib/libGLdispatch.so.0 (0x00007f6d20f8c000)
        libGLX.so.0 => /nix/store/yl6ggfn054z9brd4rlzifn3zpiardjz2-libglvnd-1.6.0/lib/libGLX.so.0 (0x00007f6d20f56000)
        libicudata.so.72 => /nix/store/jbsbrpz89j9sbk0shpqvk5jy3fddkffq-icu4c-72.1/lib/libicudata.so.72 (0x00007f6d1f186000)
        libstdc++.so.6 => /nix/store/2w4k8nvdyiggz717ygbbxchpnxrqc6y9-gcc-12.2.0-lib/lib/libstdc++.so.6 (0x00007f6d1ef60000)
        libgcc_s.so.1 => /nix/store/76l4v99sk83ylfwkz8wmwrm4s8h73rhd-glibc-2.35-224/lib/libgcc_s.so.1 (0x00007f6d1ef46000)
        libcap.so.2 => /nix/store/k5bbrd1d3nfzlzgv276z6kyi6qqpm0ba-libcap-2.66-lib/lib/libcap.so.2 (0x00007f6d1ef38000)
```

On constate que ces deux programmes dépendent de nombreuses bibliothèques dynamiques. On remarque qu'ils ont tous les deux `libc` en commun, ce qui est cohérent avec ce que nous avons vu jusque là : la plupart des langages de programmation en dépendent pour l'interaction avec l'OS.
