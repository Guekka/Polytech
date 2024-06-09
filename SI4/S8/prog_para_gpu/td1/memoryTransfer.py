from numba import cuda
import numba as nb
import numpy as np
import sys
import math

@cuda.jit
def writeGlobalID(array) :
    local_id = cuda.threadIdx.x
    global_id = cuda.grid(1)
    array[global_id]= global_id

@cuda.jit
def writeGlobalIDUnevenArray(array):
    local_id = cuda.threadIdx.x
    global_id = cuda.grid(1)
    #check the thread is not outside the array
    if global_id < array.shape[0]:
        array[global_id]= global_id

def runGlobalID():
    threadsPerBlock=16
    blocksPerGrid=32
    print("Starting",sys._getframe(  ).f_code.co_name)
    print("threadsPerBlock ", threadsPerBlock)
    print("blocksPerGrid", blocksPerGrid)
    #Generate array with size matching total number of threads
    A = np.zeros(threadsPerBlock*blocksPerGrid, dtype=np.uint16)
    #Send array to device
    d_A = cuda.to_device(A)
    #Execute kernel
    writeGlobalID[ blocksPerGrid,threadsPerBlock](d_A)
    cuda.synchronize()
    #Copy back the modified array
    A = d_A.copy_to_host()
    #Print a subsequence to check
    print(A[-10:])

def runGlobalIDUnevenArray(size):
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
    writeGlobalIDUnevenArray[ blocksPerGrid,threadsPerBlock](d_A)
    cuda.synchronize()
    #Copy back the modified array
    A = d_A.copy_to_host()
    #Print a subsequence to check
    print(A[-10:])

if __name__ == '__main__':
    runGlobalID()
    runGlobalIDUnevenArray(1500)
