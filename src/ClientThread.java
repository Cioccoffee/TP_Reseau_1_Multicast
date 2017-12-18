/***
 * ClientThread
 * Example of a TCP server
 * Date: 14/12/08
 * Authors:
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ClientThread extends Thread {
	
	//private Socket clientSocket;
	private InterfaceClient ic;
	private MulticastSocket ms;
//	private InetAddress gpAddr;
//	private int gpPort;
	private boolean sender;
	public boolean keepOn = true;

	ClientThread(MulticastSocket ms, boolean send, InterfaceClient ic) {
		
		this.ms = ms;
		//ms.joinGroup(gpAddress);
		this.sender = send;
		this.ic = ic;
	}
	
	/**
	 * receives a request from client then sends an echo to the client
	 * 
	 * @param clientSocket
	 *            the client socket
	 **/

	public void run() {

		if (sender) {
			runSending();
		} else {
			runReceiving();
		}
	}

	public void runSending() {

		try {
			
		

		} catch (Exception e) {
			System.err.println("Error in runSending:" + e);
		}
	}

	public void runReceiving() {
		//String out = "";
		try {
			System.out.println("received");
			BufferedReader socIn = null;
			//socIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

			while (true) {
				//String out = "";
				//String out = socIn.readLine(); // lit depuis le socket
				
				int sizereceived = 10000;
				byte[] bytereceived = new byte[sizereceived];
				
				ms.receive(new DatagramPacket(bytereceived,sizereceived));
				String msgreceived = "";
				
				msgreceived = bytereceived.toString();
				msgreceived = new String(bytereceived);
				System.out.println(msgreceived);

				ic.messageArea.append(msgreceived + "\n");
				System.out.println(msgreceived);

				//history.add(out);
			}

		} catch (Exception e) {
			System.err.println("Error in runReceiving:" + e);
		}

	}

}
