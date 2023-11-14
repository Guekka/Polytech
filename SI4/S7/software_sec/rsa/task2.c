#include "rsa.c"

// encrypt a message

int main()
{
    BIGNUM *enc = BN_new();
    BIGNUM *dec = BN_new();

    // Assign the private key
    BIGNUM *d = BN_new();
    BN_hex2bn(&d, "74D806F9F3A62BAE331FFE3F0A68AFE35B3D2E4794148AACBC26AA381CD7D30D");

    // Assign the public key
    BIGNUM *n = BN_new();
    BN_hex2bn(&n, "DCBFFE3E51F62E09CE7032E2677A78946A849DC4CDDE3A4D0CB81629242FB1A5");
    printBN("the public key is: ", n);

    // Assign the Modulus
    BIGNUM *e = BN_new();
    BN_hex2bn(&e, "010001");

    // We are going to encrypt the message 'A top secret!'.
    BIGNUM *M = BN_new();
    BN_hex2bn(&M, "4120746f702073656372657421");

    printBN("the plaintext message is: ", M);
    enc = rsa_encrypt(M, e, n);
    printBN("the encrypted message is: ", enc);
    dec = rsa_decrypt(enc, d, n);
    printf("the decrypted message is: ");
    printHX(BN_bn2hex(dec));
}