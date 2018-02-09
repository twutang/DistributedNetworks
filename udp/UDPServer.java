/*
 * Created on 01-Mar-2016
 */
package udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Arrays;

import common.MessageInfo;

public class UDPServer {

	private DatagramSocket recvSoc;
	private int totalMessages = -1;
	private int[] receivedMessages;
	private boolean close;

	private void run() {
		int				pacSize;
		byte[]			pacData;
		DatagramPacket 	pac;

		// TO-DO: Receive the messages and process them by calling processMessage(...).
		try {
			while (!close) {

				pacSize = 2048;
				pacData = new byte[pacSize];

				pac = new DatagramPacket(pacData, pacSize);

				// Use a timeout (e.g. 30 secs) to ensure the program doesn't block forever
				for (int i = 0; i < pacSize; i++);
				try {
					recvSoc.setSoTimeout(30*1000);
					recvSoc.receive(pac);
					// processing message
					String pmessage = new String(pac.getData(), pac.getOffset(), pac.getLength());
					processMessage(pmessage);
				} catch (Exception e) {
					e.printStackTrace();
					System.exit(-1);
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void processMessage(String data) {

		MessageInfo message = null;

		// TO-DO: Use the data to construct a new MessageInfo object

		try {
			message = new MessageInfo(data.trim());
		} catch (Exception e) {
			e.printStackTrace();
		}

		// TO-DO: On receipt of first message, initialise the receive buffer

		if (receivedMessages == null) {
			totalMessages = 0;

			receivedMessages = new int[message.totalMessages];
		}

		// TO-DO: Log receipt of the message

		receivedMessages[message.messageNum] = 1;
		totalMessages++;

		// TO-DO: If this is the last expected message, then identify
		//        any missing messages
		if (totalMessages == message.totalMessages) {
			printResult();
		}
	}


	public UDPServer(int rp) {
		// TO-DO: Initialise UDP socket for receiving data
		try {
			recvSoc = new DatagramSocket(rp);
		}
		catch (SocketException e) {
			System.out.println("Error: Could not create socket on port number" + rp);
			System.exit(-1);
		}

		// Make it so the server can run.
		close = false;

		// Done Initialisation
		System.out.println("UDPServer initialised");
	}

	public static void main(String args[]) {
		int	recvPort;

		// Get the parameters from command line
		if (args.length < 1) {
			System.err.println("No arguments present: recv port required");
			System.exit(-1);
		}
		recvPort = Integer.parseInt(args[0]);

		// TO-DO: Construct Server object and start it by calling run().
		UDPServer UDPServer = new UDPServer(recvPort);

		try {
			UDPServer.run();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

	public void printResult() {
		if(receivedMessages == null || totalMessages <= 0) {
		System.out.println("No messages detected.");
			return;
		}

		String missingMessages = "";
		for(int i = 0; i <receivedMessages.length; i++) {
			if(receivedMessages[i] == 0) {
			missingMessages += i + ", ";
			}
		}

		System.out.println("Number of messages received: " + totalMessages);
		System.out.println("Lost Messages: " + missingMessages);
		//reset
		receivedMessages = null;
		totalMessages = -1;
	}
}
