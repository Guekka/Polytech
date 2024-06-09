import numpy as np
from numba import cuda
import numba as nb

def firstKernel():
    """
    prints hello on gpu
    """

    @cuda.jit
    def hello():
        print("Hello from thread ", cuda.threadIdx.x)

    hello[1, 4]()

firstKernel()
