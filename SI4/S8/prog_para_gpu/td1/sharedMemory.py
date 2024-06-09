from numba import cuda
import numba as nb
import numpy as np
import sys
import math

THREAD_BLOCK=16

@cuda.jit
def kernelWithShared(array, toShare) :
    global_id = cuda.grid(1)
    #Create an array in shared memory with one cell per thread
    shared_filter=cuda.shared.array(THREAD_BLOCK, dtype=np.int32)
    #Each thread fill one element of the array
    shared_filter[cuda.threadIdx.x] = toShare[cuda.threadIdx.x]
    #perform some computation
    if global_id<array.shape[0] :
        tmp = array[global_id]
        for i in range(0, THREAD_BLOCK):
            tmp+=shared_filter[i]
        array[global_id] = tmp





def run(size):
    threadsPerBlock=THREAD_BLOCK
    blocksPerGrid=math.ceil(size/threadsPerBlock)
    print("Starting",sys._getframe(  ).f_code.co_name)
    print("threadsPerBlock ", threadsPerBlock)
    print("blocksPerGrid", blocksPerGrid)
    A = np.random.randint(1,100,size=size, dtype=np.int32)
    d_A = cuda.to_device(A)

    B = np.empty(THREAD_BLOCK)
    for i in range(0,THREAD_BLOCK):
        B[i]=i-3
    d_B= cuda.to_device(B)
    #Execute kernel
    kernelWithShared[blocksPerGrid,threadsPerBlock](d_A, d_B)
    cuda.synchronize()
    #Copy back the modified array
    A = d_A.copy_to_host()


if __name__ == '__main__':
   run(1000)
