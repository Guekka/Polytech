#include <stdio.h>
#include <string.h>
#include <openssl/bn.h>

BIGNUM *get_rsa_priv_key(BIGNUM *p, BIGNUM *q, BIGNUM *e)
{
	/*
		given two large prime numbers, compute a private key
		using the modulo inverse of the totatives of the product p*q
	*/
	BN_CTX *ctx = BN_CTX_new();
	BIGNUM *p_minus_one = BN_new();
	BIGNUM *q_minus_one = BN_new();
	BIGNUM *one = BN_new();
	BIGNUM *tt = BN_new();

	BN_dec2bn(&one, "1");
	BN_sub(p_minus_one, p, one);
	BN_sub(q_minus_one, q, one);
	BN_mul(tt, p_minus_one, q_minus_one, ctx);

	BIGNUM *res = BN_new();
	BN_mod_inverse(res, e, tt, ctx);
	BN_CTX_free(ctx);
	return res;
}

BIGNUM *rsa_encrypt(BIGNUM *message, BIGNUM *mod, BIGNUM *pub_key)
{
	/*
		compute the RSA cipher on message
		the ciphertext is congruent to: message^mod (modulo pub_key)
	*/
	BN_CTX *ctx = BN_CTX_new();
	BIGNUM *enc = BN_new();
	BN_mod_exp(enc, message, mod, pub_key, ctx);
	BN_CTX_free(ctx);
	return enc;
}

BIGNUM *rsa_decrypt(BIGNUM *enc, BIGNUM *priv_key, BIGNUM *pub_key)
{
	/*
		compute the original message: (message ^ mod) ^ pub_key
	*/
	BN_CTX *ctx = BN_CTX_new();
	BIGNUM *dec = BN_new();
	BN_mod_exp(dec, enc, priv_key, pub_key, ctx);
	BN_CTX_free(ctx);
	return dec;
}

int hex_to_int(char c)
{
	if (c >= 97)
		c = c - 32;
	int first = c / 16 - 3;
	int second = c % 16;
	int result = first * 10 + second;
	if (result > 9)
		result--;
	return result;
}

int hex_to_ascii(const char c, const char d)
{
	int high = hex_to_int(c) * 16;
	int low = hex_to_int(d);
	return high + low;
}

void printHX(const char *st)
{
	int length = strlen(st);
	if (length % 2 != 0)
	{
		printf("%s\n", "invalid hex length");
		return;
	}
	int i;
	char buf = 0;
	for (i = 0; i < length; i++)
	{
		if (i % 2 != 0)
			printf("%c", hex_to_ascii(buf, st[i]));
		else
			buf = st[i];
	}
	printf("\n");
}

void printBN(char *msg, BIGNUM *a)
{
	char *number_str = BN_bn2hex(a);
	printf("%s 0x%s\n", msg, number_str);
	OPENSSL_free(number_str);
}