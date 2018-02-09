/*
 * Created on 01-Mar-2016
 */
package rmi;

import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.net.MalformedURLException;
import java.rmi.RMISecurityManager;

import common.MessageInfo;

public class RMIClient {

	public static void main(String[] args) {

		RMIServerI iRMIServer = null;

		// Check arguments for Server host and number of messages
		if (args.length < 2){
			System.out.println("Needs 2 arguments: ServerHostName/IPAddress, TotalMessageCount");
			System.exit(-1);
		}

		String urlServer = new String("rmi://" + args[0] + "/RMIServer");
		int numMessages = Integer.parseInt(args[1]);

		// TO-DO: Initialise Security Manager
		try {
			if (System.getSecurityManager() == null) {
				System.setSecurityManager(new RMISecurityManager());
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// TO-DO: Bind to RMIServer
		try {
			iRMIServer = (RMIServerI) Naming.lookup(urlServer);
			// TO-DO: Attempt to send messages the specified number of times
			for (int i = 0; i < numMessages; i++) {
				MessageInfo message = new MessageInfo(numMessages, i);
				iRMIServer.receiveMessage(message);
			}

			System.out.println("Messages sent");

		} catch (MalformedURLException e) {
			e.printStackTrace(); // Checking for malformed hostname
		} catch (RemoteException e) {
			e.printStackTrace(); // Checking for remote exception
		} catch (NotBoundException e) {
			e.printStackTrace(); // Checking if binding has occurred
		} catch (Exception e) {
			e.printStackTrace(); // General catch
		}

	}
}
