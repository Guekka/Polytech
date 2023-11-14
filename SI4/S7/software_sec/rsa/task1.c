#include "rsa.h"

// get private key

int main()
{
    BIGNUM *p = BN_new();
    BN_hex2bn(&p, "F7E75FDC469067FFDC4E847C51F452DFi");
    printBN("p = ", p);

    BIGNUM *q = BN_new();
    BN_hex2bn(&q, "E85CED54AF57E53E092113E62F436F4F");
    printBN("q = ", q);

    BIGNUM *e = BN_new();
    BN_hex2bn(&e, "0D88C3");
    printBN("e = ", e);

    BIGNUM *d = get_rsa_priv_key(p, q, e);
    printBN("d = ", d);
}
