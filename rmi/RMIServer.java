/*
 * Created on 01-Mar-2016
 */
package rmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;
import java.rmi.registry.Registry;


import common.*;

public class RMIServer extends UnicastRemoteObject implements RMIServerI {

	private int totalMessages = -1;
	private int[] receivedMessages;

	public RMIServer() throws RemoteException {
	}

	public void receiveMessage(MessageInfo msg) throws RemoteException {

		// TO-DO: On receipt of first message, initialise the receive buffer

		if (receivedMessages == null) {
			totalMessages = msg.totalMessages;
			receivedMessages = new int[msg.totalMessages];
		}

		// TO-DO: Log receipt of the message

		receivedMessages[msg.messageNum] = 1;

		// TO-DO: If this is the last expected message, then identify
		//        any missing messages
		if(totalMessages == msg.totalMessages){
			printResult();
		}
	}


	public static void main(String[] args) {

		RMIServer rmis = null;

		// TO-DO: Instantiate the server class
		try {
		// TO-DO: Initialise Security Manager
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}
			rmis = new RMIServer();
		} catch(RemoteException e) {
			e.printStackTrace();
		}

		// TO-DO: Bind to RMI registry
		rebindServer("localhost", rmis);

	}

	protected static void rebindServer(String serverURL, RMIServer server) {
		Registry reg;
		// TO-DO:
		// Start / find the registry (hint use LocateRegistry.createRegistry(...)
		// If we *know* the registry is running we could skip this (eg run rmiregistry in the start script)
		try{
			reg = LocateRegistry.createRegistry(1099);

		// TO-DO:
		// Now rebind the server to the registry (rebind replaces any existing servers bound to the serverURL)
		// Note - Registry.rebind (as returned by createRegistry / getRegistry) does something similar but
		// expects different things from the URL field.
		reg.rebind ("RMIServer", server);
		} catch (RemoteException e) {
			e.printStackTrace();
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
