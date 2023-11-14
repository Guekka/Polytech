from timeit import timeit

@timeit
def naive_fibo(n: int) -> int:
    if n <= 1:
        return n
    else:
        return naive_fibo(n-1) + naive_fibo(n-2)

@timeit
def list_fibo(n: int) -> int:
    fibo = [0, 1]
    for i in range(2, n+1):
        fibo.append(fibo[i-1] + fibo[i-2])
    return fibo[n]

@timeit
def fibo_formula(n: int) -> int:
    return round(((1 + 5**0.5) / 2)**n - ((1 - 5**0.5) / 2)**n)


def main():
    #print(naive_fibo(25))
    print(list_fibo(1000))
    print(fibo_formula(1000))

if __name__ == '__main__':
    main()