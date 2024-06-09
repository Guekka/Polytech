from numba import cuda
import numba as nb
import numpy as np
import sys
import math

@cuda.jit(device=True)
def deviceFunction(tab, index):
    tab[index] = index

@cuda.jit
def kernel1D(tab) :
    local_id = cuda.threadIdx.x
    global_id = cuda.grid(1)

    if global_id< tab.shape[0]:
        deviceFunction(tab, global_id)


def run(size):
    threadsPerBlock=16
    blocksPerGrid=math.ceil(size/threadsPerBlock)
    print("Starting",sys._getframe(  ).f_code.co_name)
    print("threadsPerBlock ", threadsPerBlock)
    print("blocksPerGrid", blocksPerGrid)
    #Generate array of size with zeros
    A = np.zeros(size, dtype=np.uint16)
    #Send array to device
    d_A = cuda.to_device(A)
    #Execute kernel
    kernel1D[ blocksPerGrid,threadsPerBlock](d_A)
    cuda.synchronize()
    #Copy back the modified array
    A = d_A.copy_to_host()
    #Print a subsequence to check
    print(A[-10:])


if __name__ == '__main__':
    run(1500)
