import time

# decorator time
def timeit(func):
    def wrapper(*args, **kwargs):
        start = time.time()
        result = func(*args, **kwargs)
        # time in ms
        round_time = round((time.time() - start) * 1000, 2)
        print(f"Time: {round_time}ms for {func.__name__} with args: {args}")
        return result
    return wrapper
