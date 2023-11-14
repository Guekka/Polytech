#include <stdio.h>
#include <string.h>
#include <openssl/bn.h>

BIGNUM *get_rsa_priv_key(BIGNUM *p, BIGNUM *q, BIGNUM *e);
BIGNUM *rsa_encrypt(BIGNUM *message, BIGNUM *mod, BIGNUM *pub_key);
BIGNUM *rsa_decrypt(BIGNUM *enc, BIGNUM *priv_key, BIGNUM *pub_key);
int hex_to_int(char c);
int hex_to_ascii(const char c, const char d);
void printHX(const char *st);
void printBN(char *msg, BIGNUM *a);