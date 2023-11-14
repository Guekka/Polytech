#include "rsa.c"

// Sign a message

int main()
{

    // Assign the Modulus
    BIGNUM *e = BN_new();
    BN_hex2bn(&e, "010001");

    BIGNUM *n = BN_new();
    BN_hex2bn(&n, "DCBFFE3E51F62E09CE7032E2677A78946A849DC4CDDE3A4D0CB81629242FB1A5");

    // message: "I owe you $2000."
    BIGNUM *M = BN_new();
    BN_hex2bn(&M, "49206f776520796f752024323030302e");

    // message: "I owe you $3000."
    BIGNUM *M2 = BN_new();
    BN_hex2bn(&M2, "49206f776520796f752024333030302e");

    BIGNUM *d = BN_new();
    BN_hex2bn(&d, "74D806F9F3A62BAE331FFE3F0A68AFE35B3D2E4794148AACBC26AA381CD7D30D");

    // Since we already have the private key, all we need to do is encrypt.
    BIGNUM *enc = BN_new();
    enc = rsa_encrypt(M, d, n);
    printBN("the first signature is: ", enc);

    // To verify the operations were conducted correctly, we decrypt as well.
    BIGNUM *dec = BN_new();
    dec = rsa_decrypt(enc, e, n);
    printf("the first message is: ");
    printHX(BN_bn2hex(dec));

    printf("\n");

    enc = rsa_encrypt(M2, d, n);
    printBN("the second signature is: ", enc);
    dec = rsa_decrypt(enc, e, n);
    printf("the second message is: ");
    printHX(BN_bn2hex(dec));
}