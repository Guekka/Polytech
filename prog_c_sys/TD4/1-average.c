#include <stdarg.h>
#include <assert.h>
#include <stdbool.h>
#include <math.h>

bool double_eq(double a, double b) {
    return fabsf(b - a) < 0.000001;
}

double moyenne(int count, ...) {
    if (count == 0)
        return 0;

    va_list args;
    va_start(args, count);


    double sum = 0;
    for (int i = 0; i < count; ++i) {
        sum += va_arg(args, double);
    }
    va_end(args);

    return sum / count;
}

int main() {
    assert (double_eq(moyenne(2, 10.0, 15.0), 12.5));
    assert (double_eq(moyenne(5, 10.0, 15.0, 18.5, 0.0, 3.5), 9.4));
    assert (double_eq(moyenne(0), 0.0));

    return 0;
}