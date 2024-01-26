# LoRaWAN on DK-AIoT
## The Things Network
1. Register an account
2. Create an Application
3. Add an _End device_ with _Manual device registration_
	- Frequency Plan: Europe 863-870 SF9
	- LoRaWAN version: 1.0.0
	- Show advanced activation, LoRaWAN class and cluster settings
		- Activation mode: ABP
	- DevEUI: Generate
	- Device Address: Generate
	- AppSKey: Generate
	- NwkSKey: Generate
4. Add `decodeUplink.js` as a _Custom Javascript formatter_ in the _Payload formatters_, _Uplink_ tab of the _End device_

## LoRaWAN settings in Arduino code

### Install LibLacuna
Copy the `LibLacuna-0.9.3-RC1/` directory into the `Arduino/libraries` folder of your home directory.

### Set the LoRaWAN keys
Get the keys from the _Overview_ tab of the _End device_ in The Things Network. Click the "eye" button to show the keys and click the `<>` (array formatting) button to see the data to put into the C code (_msb_ format).

Copy the keys into the C code as follows:

- _Device address_ → `deviceAddress`
- _NwkSKey_ →  `networkKey`
- _AppSKey_ → `appKey`


## After resetting board
After resetting the board, if no data are received in The Things Network, you may have to reset the session by going to the _General Settings_ tab of your _End device_ and click _Reset session and MAC state_ under the _Network layer_ section.
