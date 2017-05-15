

# DDoS attack

A hacker uses DDoS attacks in order to prevent legitimate users from accessing the service of a provider. The attacker does this through the use of an attack that streams multiple illegitimate requests to the victim, i.e. a High-Rate Flooding (HRF) attack. There have been various classifications of DDoS attacks.

## TCP three-way handshake

A TCP SYN flooding attack is an example of a network layer flooding attack, and it is one of the most common and powerful flooding methods. It exploits the vulnerabilities of the TCP three-way handshake.

In a normal TCP connection, the client initiates the connection by sending a SYN packet to the server, as a way of requesting a connection. Upon receiving the connection request, the server will open a connection session and respond with a SYN_ACK packet; by doing this the server stores details of the requested TCP connection in the memory stack and allocates resources to this open session.  The connection remains in a half-open state, i.e. the SYN_RECVD state. To complete the three-way handskake with the server, the client will need to confirm the connection and respond with an ACK packet. The server will then check the memory stack for an existing connection request, and the TCP connection will be moved from the SYN_RECVD state to ESTABLISHED state. If there is no ACK packet sent within a specific period of time, the connection will timeout and therefore releasing the allocated resources.

## TCP SYN flooding attack

In a TCP SYN flooding attack, the attacker streams large volumes of SYN packets towards the victim server. These packets normally contain spoofed IP addresses, i.e. IP addresses that are non-existent or are not utilized. TCP SYN floods can also be launched using compromised machines with legitimate IP addresses, however the machines need to be configured in such a way that it does not respond or acknowledge a SYN_ACK packet from the victim server.

In this way the server will not receive any ACK packet from the clients for the "half-open" connection request. During the high rate flooding attack, and for a period of time, the server will maintain a large volume of incomplete three-way handshake and allocates resource towards the fictitious connection requests. The server will gather more fictitious requests and eventually exhaust its resources. The will prevent new requests, including legitimate client requests, from being further processed by the server.

## Reference
* Machaka, Pheeha, Antoine Bagula, and Fulufhelo Nelwamondo. "Using exponentially weighted moving average algorithm to defend against DDoS attacks." Pattern Recognition Association of South Africa and Robotics and Mechatronics International Conference (PRASA-RobMech), 2016. IEEE, 2016.
