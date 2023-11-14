from timeit import timeit

def binome(n: int, k: int) -> int:
    if k == 0 or k == n:
        return 1

    if n < k:
        return 0

    return binome(n-1, k-1) + binome(n-1, k)

@timeit
def smart_binome(n: int, k: int) -> int:
    # use pascal's triangle

    # initialize the triangle
    triangle = [[1]]
    for i in range(1, n+1):
        triangle.append([1] + [0] * i + [1])

    # fill the triangle
    for i in range(1, n+1):
        for j in range(1, i):
            triangle[i][j] = triangle[i-1][j-1] + triangle[i-1][j]

    return triangle[n][k]

@timeit
def smart_binome_no_storage(n: int, k: int) -> int:
    # use pascal's triangle

    # initialize the triangle
    triangle = [1]
    for i in range(1, n+1):
        triangle.append(1)

    # fill the triangle
    for i in range(1, n):
        for j in range(i-1, 0, -1):
            triangle[j] += triangle[j-1]

    return triangle[k]

def main():
    print(smart_binome(600, 300))
    print(smart_binome_no_storage(600, 300))

if __name__ == '__main__':
    main()

