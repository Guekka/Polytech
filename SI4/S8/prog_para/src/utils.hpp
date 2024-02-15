#pragma once

#include <chrono>

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
