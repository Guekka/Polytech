#include <chrono>
#include <cmath>
#include <execution>
#include <iostream>
#include <numeric>

template<class T>
struct TimeIt
{
    T value;
    std::chrono::duration<double> duration;
};

template<class F>
[[nodiscard]] auto timed(F &&f) noexcept
    requires std::invocable<F> && (!std::is_void_v<std::invoke_result_t<F>>)
{
    const auto start = std::chrono::high_resolution_clock::now();
    auto val         = std::forward<F>(f)();
    const auto end   = std::chrono::high_resolution_clock::now();

    const auto duration = std::chrono::duration_cast<std::chrono::milliseconds>(end - start);

    return TimeIt{val, duration};
}

// monostate if return type is void
template<class F>
auto timed(F &&f) noexcept
    requires std::invocable<F> && std::is_void_v<std::invoke_result_t<F>>
{
    const auto start = std::chrono::high_resolution_clock::now();
    std::forward<F>(f)();
    const auto end = std::chrono::high_resolution_clock::now();

    const auto duration = std::chrono::duration_cast<std::chrono::milliseconds>(end - start);
    return duration;
}

[[nodiscard]] auto prefix_sum(std::span<const int64_t> a, bool parallel) -> std::vector<int64_t>
{
    std::vector<int64_t> result(a.size());
    const auto log_n = static_cast<size_t>(std::log2(a.size()));
    for (size_t i = 0; i <= log_n; ++i)
#pragma omp parallel for default(none) shared(a, result) firstprivate(i) if (parallel)
        for (size_t j = 0; j < a.size(); ++j)
            result[j] = a[j] + (j >= (1U << i) ? a[j - (1U << i)] : 0);

    return result;
}

[[nodiscard]] auto prefix_sum_cpp(std::span<const int64_t> a) -> std::vector<int64_t>
{
    auto ret = std::vector<int64_t>(a.size());
    std::inclusive_scan(std::execution::par, a.begin(), a.end(), ret.begin());
    return ret;
}

[[nodiscard]] auto generate_array(size_t n) -> std::vector<int64_t>
{
    std::vector<int64_t> a(n);
    std::generate(a.begin(), a.end(), [i = 0]() mutable { return i++; });
    return a;
}

struct Args
{
    size_t size;
};

[[nodiscard]] auto parse_args(int argc, char **argv) -> Args
{
    if (argc != 2)
    {
        std::cerr << "Usage: " << argv[0] << " <size>\n";
        std::exit(1);
    }

    return {static_cast<size_t>(std::stoull(argv[1]))};
}

auto main(int argc, char **argv) -> int
{
    const auto len   = parse_args(argc, argv).size;
    const auto input = generate_array(len);

    const auto [output_seq, duration_seq] = timed([&input] { return prefix_sum(input, false); });
    std::cout << "Sequential: " << duration_seq << '\n';

    const auto [output_omp, duration_omp] = timed([&input] { return prefix_sum(input, true); });
    std::cout << "Parallel OMP: " << duration_omp << '\n';

    const auto [output_cpp, duration_cpp] = timed([&input] { return prefix_sum_cpp(input); });
    std::cout << "Parallel STL: " << duration_cpp << '\n';
}
