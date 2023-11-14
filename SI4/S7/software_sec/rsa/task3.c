#include "rsa.c"

// decrypt a message

int main()
{
    BIGNUM *e = BN_new();
    BN_hex2bn(&e, "010001");

    BIGNUM *n = BN_new();
    BN_hex2bn(&n, "DCBFFE3E51F62E09CE7032E2677A78946A849DC4CDDE3A4D0CB81629242FB1A5");
    BIGNUM *enc = BN_new();
    BN_hex2bn(&enc, "8C0F971DF2F3672B28811407E2DABBE1DA0FEBBBDFC7DCB67396567EA1E2493F");

    // Decryption
    BIGNUM *d = BN_new();
    BN_hex2bn(&d, "74D806F9F3A62BAE331FFE3F0A68AFE35B3D2E4794148AACBC26AA381CD7D30D");

    BIGNUM *dec = BN_new();
    dec = rsa_decrypt(enc, d, n);
    printf("the decrypted message for is: ");
    printHX(BN_bn2hex(dec));
}