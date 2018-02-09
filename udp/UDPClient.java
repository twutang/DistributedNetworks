/*
 * Created on 01-Mar-2016
 */
package udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import common.MessageInfo;

public class UDPClient {

	private DatagramSocket sendSoc;

	public static void main(String[] args) {
		InetAddress	serverAddr = null;
		int			recvPort;
		int 		countTo;
		String 		message;

		// Get the parameters
		if (args.length < 3) {
			System.err.println("Arguments required: server name/IP, recv port, message count");
			System.exit(-1);
		}

		try {
			serverAddr = InetAddress.getByName(args[0]);
		} catch (UnknownHostException e) {
			System.out.println("Bad server address in UDPClient, " + args[0] + " caused an unknown host exception " + e);
			System.exit(-1);
		}
		recvPort = Integer.parseInt(args[1]);
		countTo = Integer.parseInt(args[2]);


		// TO-DO: Construct UDP client class and try to send messages
		UDPClient UDPclient = new UDPClient();

		UDPclient.testLoop(serverAddr, recvPort, countTo);
		System.out.println("Sending messages");

	}

	public UDPClient() {
		// TO-DO: Initialise the UDP socket for sending data
		try {
			sendSoc = new DatagramSocket();
		} catch (SocketException e) {
			e.printStackTrace(); // Expected exception could be inability to create diagramsocket
		}
	}

	private void testLoop(InetAddress serverAddr, int recvPort, int countTo) {

		// TO-DO: Send the messages to the server
		for(int i = 0; i < countTo; i++) {
			MessageInfo message = new MessageInfo(countTo,i);
			send(message.toString(), serverAddr, recvPort);
		}

		System.out.println("Sending messages to server completed.");
	}

	private void send(String payload, InetAddress destAddr, int destPort) {

		byte[]				pktData;
		DatagramPacket		pkt;
		int				payloadSize;

		payloadSize = payload.length();
		pktData = payload.getBytes();

		// TO-DO: build the datagram packet and send it to the server
		pkt = new DatagramPacket(pktData, payloadSize, destAddr, destPort);
		
		try {
			sendSoc.send(pkt);
		} catch (Exception e) {
			e.printStackTrace(); //Expecting IO Exception if message fails to send
		}
	}
}
