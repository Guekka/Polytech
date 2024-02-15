#include "utils.hpp"

#include <algorithm>
#include <array>
#include <format>
#include <iostream>
#include <random>
#include <vector>

struct RandomBound
{
    uint64_t min;
    uint64_t max;
};

[[nodiscard]] auto random(uint64_t min, uint64_t max) noexcept -> uint64_t
{
    static thread_local std::mt19937 generator(std::random_device{}());
    std::uniform_int_distribution<uint64_t> distribution(min, max);
    return distribution(generator);
}

[[nodiscard]] auto random_vector_seq(size_t size, RandomBound bound) noexcept -> std::vector<uint64_t>
{
    std::vector<uint64_t> v(size);
    std::generate(v.begin(), v.end(), [bound]() { return random(bound.min, bound.max); });
    return v;
}

[[nodiscard]] auto random_vector_omp(size_t size, RandomBound bound) noexcept -> std::vector<uint64_t>
{
    std::vector<uint64_t> v(size);
#pragma omp parallel for default(none) shared(v, bound, size)
    for (size_t i = 0; i < size; ++i)
        v[i] = random(bound.min, bound.max);
    return v;
}

[[nodiscard]] auto random_vector(size_t size, RandomBound bound, bool parallel) noexcept
    -> std::vector<uint64_t>
{
    return parallel ? random_vector_omp(size, bound) : random_vector_seq(size, bound);
}

[[nodiscard]] auto is_prime(uint64_t n) noexcept -> bool
{
    constexpr auto k_base_cases = std::to_array<bool>({false, false, true, true});
    if (n < k_base_cases.size())
        return k_base_cases.at(n);

    for (uint64_t i = 2; i * i <= n; ++i)
        if (n % i == 0)
            return false;

    return true;
}

template<class F>
[[nodiscard]] auto map_vector_seq(const std::vector<uint64_t> &v, F &&f) noexcept
    -> std::vector<std::invoke_result_t<F, uint64_t>>
{
    std::vector<std::invoke_result_t<F, uint64_t>> result;
    result.reserve(v.size());
    std::transform(v.begin(), v.end(), std::back_inserter(result), std::forward<F>(f));
    return result;
}

template<class F>
[[nodiscard]] auto map_vector_omp(const std::vector<uint64_t> &v, F &&f) noexcept
    -> std::vector<std::invoke_result_t<F, uint64_t>>
{
    std::vector<std::invoke_result_t<F, uint64_t>> result(v.size());
#pragma omp parallel for default(none) shared(v, result, f)
    for (size_t i = 0; i < v.size(); ++i)
        result[i] = f(v[i]);
    return result;
}

template<class F>
[[nodiscard]] auto map_vector(const std::vector<uint64_t> &v, F &&f, bool parallel) noexcept
    -> std::vector<std::invoke_result_t<F, uint64_t>>
{
    return parallel ? map_vector_omp(v, std::forward<F>(f)) : map_vector_seq(v, std::forward<F>(f));
}

struct Args
{
    RandomBound bounds;
    size_t array_size;
    bool parallel = false;
};

void main_process(const Args &args)
{
    const auto vec_timed = timed(
        [&args] { return random_vector(args.array_size, args.bounds, args.parallel); });

    const auto time_prime = timed(
        [&vec_timed, &args] { return map_vector(vec_timed.value, is_prime, args.parallel); });

    std::cout << vec_timed.duration.count() << "," << time_prime.duration.count() << "\n";
}

[[nodiscard]] auto parse_args(int argc, char *argv[]) noexcept -> std::optional<Args>
{
    auto args = std::vector<std::string_view>(argv, argv + argc);

    if (args.size() != 5)
    {
        std::cerr << "Error: invalid number of arguments\n";
        return std::nullopt;
    }

    const auto min = std::stoul(std::string(args[1]));
    const auto max = std::stoul(std::string(args[2]));

    if (min > max)
    {
        std::cerr << "Error: min is greater than max\n";
        return std::nullopt;
    }

    const auto size = std::stoul(std::string(args[3]));

    const auto parallel = args[4] == "true";

    return Args{RandomBound{min, max}, size, parallel};
}

auto main(int argc, char *argv[]) -> int
{
    auto args = parse_args(argc, argv);
    if (!args)
        return 1;

    main_process(args.value());
    return 0;
}
