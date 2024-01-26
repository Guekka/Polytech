#!/usr/bin/env python3

# Copyright 2021 (c) Pierre-Emmanuel Novac <penovac@unice.fr> Université Côte d'Azur, CNRS, LEAT. All rights reserved.

from collections import namedtuple
import sys
import time
import statistics
import serial

def evaluate(x_path, y_path, dev: str='/dev/ttyACM0', baudrate: int=115200, timeout: int=30, limit: int=None):
    # Convert command line args
    baudrate = int(baudrate)
    timeout = int(timeout)
    limit = int(limit) if limit is not None else None

    print('Waiting for READY from the target…')
    s = serial.Serial(dev, baudrate, timeout=timeout)
    print(s.name)
    r = s.readline().decode('cp437')
    if 'READY' not in r:
        return None

    Result = namedtuple('Result', ['i', 'y_pred', 'score', 'time', 'y_true'], defaults=[-1, -1, -1, -1, -1])
    results = [] # read from target
    correct = 0 # Keep track of the number of correct predictions for accuracy
    with open(x_path) as x_file, open(y_path) as y_file:
        for i, (x_line, y_line) in enumerate(zip(x_file, y_file)):
            # Find class number for true label
            y_line = [float(y) for y in y_line.split(',')]
            y_true = y_line.index(max(y_line))
        
            print(f'{i}: ', end='')
            s.write(x_line.encode('cp437')) # Send test vector

            r = s.readline() # Read acknowledge
            tstart = time.time() # Start timer
            r = r.decode('cp437')
            if not r or int(r) != len(x_line): # Timed out or didn't receive all the data
                print(f'Transmission error: {r} != {len(x_line)}')
                return None

            r = s.readline() # Read result
            tstop = time.time() # Stop timer
            r = r.decode('cp437').rstrip().split(',')
            r = Result(*r, time=tstop-tstart, y_true=y_true)

            print(r)
            results.append(r)
            
            # Correct class was predicted
            if int(r.y_pred) == r.y_true:
                correct += 1

            # Only infer 'limit' vectors
            if limit is not None and i + 1 >= limit:
                break

    avg_time = statistics.mean([r.time for r in results])

    # Compute accuracy
    accuracy = correct / len(results)

    return namedtuple('Stats', ['avg_time', 'accuracy'])(avg_time, accuracy=accuracy)

if __name__ == '__main__':
    print(sys.argv[1:])
    res = evaluate(*sys.argv[1:])
    print(res)
