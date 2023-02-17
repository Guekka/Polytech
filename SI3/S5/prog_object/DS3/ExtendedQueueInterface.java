interface ExtendedQueueInterface<T> extends QueueInterface<T>{
    //Remove the tail
    T popTail() throws EmptyQueueException;

    //Add an element at head place
    T pushHead(T x);
}
