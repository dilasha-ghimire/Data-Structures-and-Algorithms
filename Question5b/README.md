# ISP Application

This Java application simulates an Internet Service Provider (ISP) network management system. It allows users to define a network of devices and find the devices impacted when a particular device loses power.

## Features

- **Add Edge**: Users can define connections between devices in the network.
- **Print Network**: The application can print the network matrix representing device connections.
- **Find Connected Devices**: Users can find devices directly connected to a specified device.
- **Find Impacted Devices**: Users can identify devices impacted when a target device loses power.

## Usage

1. Define the network by adding edges between devices using the `addEdge` method.
2. Print the network matrix using the `printNetwork` method to visualize the connections.
3. Identify directly connected devices to a specified device using the `getConnectedDevices` method.
4. Find impacted devices when a target device loses power using the `findImpactedDevices` method.

## Example

``` java

Suppose we have the following network:

0 -> 1
0 -> 2
1 -> 3
1 -> 6
2 -> 4
4 -> 6
4 -> 5
5 -> 7

// Output - 

01100000
10010010
10001000
01000000
00100110
00001001
01001000
00000100
Impacted Device List: [7, 5]

```