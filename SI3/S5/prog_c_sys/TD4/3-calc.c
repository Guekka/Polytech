#include <stdarg.h>
#include <stdlib.h>
#include <stdbool.h>
#include <assert.h>

int evaluate_single(char operator, int lhs, int rhs) {
    switch (operator) {
        case '-':
            return lhs - rhs;
        case '+':
            return lhs + rhs;
        case '*':
            return lhs * rhs;
        case '/':
            return lhs / rhs;
        default:
            exit(1);
    }
}

int evaluate(char operator, int operand1, ...) {
    va_list args;
    va_start(args, operand1);
    int result = operand1;
    while (true) {
        int operand = va_arg(args, int);
        if (operand < 0)
            break;

        result = evaluate_single(operator, result, operand);
    }
    va_end(args);
    return result;
}

int main() {
    evaluate('+', 1, 2, 3, -1);

    const int eight = evaluate('*', 2, 2, 2, -1);
    assert(eight == 8);

    assert(evaluate('-', 10, 8, 2, -1) == 0);
}