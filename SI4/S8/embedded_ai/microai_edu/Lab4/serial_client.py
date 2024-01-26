#!/usr/bin/env python3

# Copyright 2021 (c) Pierre-Emmanuel Novac <penovac@unice.fr> Université Côte d'Azur, CNRS, LEAT. All rights reserved.

import socket
import serial
from serial.serialutil import SerialException
import sys
import time
import os

def main(dev: str='/dev/ttyACM0', baudrate: int=115200, timeout: int=10):
	host = '127.0.0.1'
	port = 41601
	sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
	sock.connect((host, port))

	print(f'Waiting on {dev}…')
	ser = None
	while ser is None:
		if os.path.exists(dev):
			try:
				ser = serial.Serial(dev, baudrate, timeout=timeout)
			except:
				pass
		time.sleep(0.1)
	ser.write('READY\r\n'.encode('cp437')) # Acknowledge connection before receiving data
	while True:
		try:
			r = ser.readline().decode('cp437')
			print(r, end='')
			sock.sendall(r.encode('utf-8'))

		except SerialException: # Handle serial port disconnect, try to reconnect
			ser.close()
			ser = None

			print(f'Waiting on {dev}…')
			while ser is None:
				if os.path.exists(dev):
					try:
						ser = serial.Serial(dev, baudrate, timeout=timeout)
					except:
						pass
				time.sleep(0.1)
			ser.write('READY\r\n'.encode('cp437'))

if __name__ == '__main__':
	main(*sys.argv[1:])
