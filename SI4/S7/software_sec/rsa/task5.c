#include "rsa.c"

// Verify a signature

int main()
{
    // We will use our public key to decrypt a message
    // that has been encrypted with the private key,
    // And then compare the message with our decrypted result.
    BIGNUM *S1 = BN_new();
    BN_hex2bn(&S1, "643D6F34902D9C7EC90CB0B2BCA36C47FA37165C0005CAB026C0542CBDB6802F");

    BIGNUM *S2 = BN_new();
    BN_hex2bn(&S2, "4E96B0012354774DD6C90215F0A51D356D08D9D64064C8703962C414378CE7F3");

    BIGNUM *n = BN_new();
    BN_hex2bn(&n, "AE1CD4DC432798D933779FBD46C6E1247F0CF1233595113AA51B450F18116115");

    BIGNUM *e = BN_new();
    BN_hex2bn(&e, "010001");

    // Here we decrypt the message with the public key.
    BIGNUM *dec = BN_new();
    dec = rsa_decrypt(S1, e, n);
    printf("the message for first signature: ");

    printHX(BN_bn2hex(dec));
    printf("\n");

    // Here we decrypt the second message with the public key.
    dec = rsa_decrypt(S2, e, n);
    printf("the message for second signature: ");

    // We should see a corrupted output here.
    printHX(BN_bn2hex(dec));
}