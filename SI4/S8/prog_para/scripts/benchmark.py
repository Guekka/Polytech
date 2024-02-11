#!/usr/bin/env python3
import itertools
import matplotlib.pyplot as plt
import os
import subprocess
import sys
from dataclasses import dataclass

ARRAY_SIZES = [10_000, 100_000, 1_000_000, 2_000_000, 5_000_000, 30_000_000]
PARALLEL = [True, False]


@dataclass
class BenchmarkResult:
    array_size: int
    time_generating: float
    time_testing_prime: float
    is_parallel: bool = False


def run_benchmark(binary_path, array_size, is_parallel):
    RANDOM_LOWER_BOUND = 1
    RANDOM_UPPER_BOUND = 2_000_000

    args = [binary_path, str(RANDOM_LOWER_BOUND), str(RANDOM_UPPER_BOUND), str(array_size),
            "true" if is_parallel else "false"]
    print(f"Running {' '.join(args)}")
    output = subprocess.check_output(args)
    output = output.decode('utf-8')

    time_generating, time_testing_prime = map(float, output.strip().split("\n")[0].split(","))

    return BenchmarkResult(array_size, time_generating, time_testing_prime, is_parallel)


def print_benchmark_results(results):
    print("Array size | Is parallel | Time generating | Time testing prime")
    for result in results:
        print(f"{result.array_size} | {result.is_parallel} | {result.time_generating} | {result.time_testing_prime}")


def plot_benchmark_results(results):
    fig, ax = plt.subplots()

    # plot separately parallel

    parallel_results = [result for result in results if result.is_parallel]
    ax.plot([result.array_size for result in parallel_results], [result.time_generating for result in parallel_results],
            label="Parallel time generating")
    ax.plot([result.array_size for result in parallel_results],
            [result.time_testing_prime for result in parallel_results],
            label="Parallel time testing prime")

    # plot separately sequential
    sequential_results = [result for result in results if not result.is_parallel]
    ax.plot([result.array_size for result in sequential_results],
            [result.time_generating for result in sequential_results],
            label="Sequential time generating")
    ax.plot([result.array_size for result in sequential_results],
            [result.time_testing_prime for result in sequential_results],
            label="Sequential time testing prime")

    ax.set(xlabel='Array size', ylabel='Time (s)',
           title='Benchmark results')
    ax.grid()
    ax.legend()
    plt.show()


def main():
    print("Benchmarking...")
    if len(sys.argv) < 2:
        print("Usage: python benchmark.py <binary_path>")
        return

    binary_path = sys.argv[1]
    if not os.path.isfile(binary_path):
        print("Binary does not exist")
        return

    combinations = list(itertools.product(ARRAY_SIZES, PARALLEL))
    results = [run_benchmark(binary_path, array_size, parallel) for array_size, parallel in combinations]
    print_benchmark_results(results)

    plot_benchmark_results(results)


if __name__ == "__main__":
    main()
