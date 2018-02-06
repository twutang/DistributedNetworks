/*
 * Created on 01-Mar-2016
 */
package rmi;

import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

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
			if (System.setSecurityManager() == null;) {
				System.setSecurityManager(new RMISecurityManager());
			}
				// TO-DO: Bind to RMIServer
				try {
			   		iRMIServer = (RMIServerI) Naming.lookup(urlServer);
				} catch (MalformedURL Exception e) {
					// don't know this e.printStackTrace();
				} catch (MalformedURL Exception e) {
					e.printStackTrace();
				}

					// TO-DO: Attempt to send messages the specified number of times
					for(int i = 0; i < numMessages; i++) {
						MessageInfo message = new MessageInfo(numMessages, i);
						iRMIServer.receiveMessage(message);
					 }
			} catch (RemoteException e) {
				//System.pout.println("Remote Exception: " + e);
			}
		}
}
