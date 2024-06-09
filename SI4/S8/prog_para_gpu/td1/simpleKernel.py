from numba import cuda
import numba as nb
import sys

@cuda.jit
def kernel1D() :
    local_id = cuda.threadIdx.x
    global_id = cuda.grid(1)
    if local_id==0:
        print("local_id",local_id, "global_id", global_id)


@cuda.jit
def kernel2D() :
    local_idX = cuda.threadIdx.x
    local_idY = cuda.threadIdx.y

    global_idX, global_idY = cuda.grid(2)

    if local_idX==0 and local_idY == 0:
        print("(",local_idX,",",local_idY,") in ","(",cuda.blockIdx.x,",",cuda.blockIdx.y,") -> (", global_idX, ",", global_idY,")")


def run1D():
    #4 blocks of 16 threads
    threadsPerBlock = 16
    blocksPerGrid = 4
    print("Starting",sys._getframe(  ).f_code.co_name)
    print("threadsPerBlock ", threadsPerBlock)
    print("blocksPerGrid", blocksPerGrid)
    kernel1D[blocksPerGrid,threadsPerBlock]()
    cuda.synchronize()


def run2D():
     #4*4 blocks of 16*2 threads
    threadsPerBlock = (16,2)
    blocksPerGrid = (4,4)
    print("Starting",sys._getframe(  ).f_code.co_name)
    print("threadsPerBlock ", threadsPerBlock)
    print("blocksPerGrid", blocksPerGrid)
    kernel2D[blocksPerGrid,threadsPerBlock]()
    cuda.synchronize()



if __name__ == '__main__':

    run1D()
    run2D()
