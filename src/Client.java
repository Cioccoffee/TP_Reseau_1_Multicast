/***
 * EchoClient
 * Example of a TCP client 
 * Date: 10/01/04
 * Authors:
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Client {

	/**
	 * main method accepts a connection, receives a message from client then sends
	 * an echo to the client
	 * 
	 **/

	public static void main(String[] args) throws IOException {

		try {
			// creation socket ==> connexion
			
			InterfaceClient ic = new InterfaceClient();
			ic.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			ic.frame.setVisible(true);

			
			// recuperation de l'historique
			File file = new File("history.txt");
			if (!file.exists()) {
				file.createNewFile();
			}

			FileInputStream load = new FileInputStream(file);

			BufferedReader reader = new BufferedReader(new InputStreamReader(load));
			String line;
			while ((line = reader.readLine()) != null) {
				ic.messageArea.append(line + "\n");
				System.out.println(line);
			}

			/*
			 * String toAdd = previousContent.toString();
			 * 
			 * //String delims = "\n+"; String[] tokens =
			 * toAdd.split(System.getProperty("line.separator")); for(int i =0; i <
			 * tokens.length; i++) System.out.println(tokens[i]);
			 */

			reader.close();
			load.close();

			while (true) {
//
//				if (!ctSendingToServer.keepOn)
//					break;
			}

		} catch (UnknownHostException e) {
			
			e.printStackTrace();
			System.exit(1);
		} catch (IOException e) {
			
			e.printStackTrace();

			System.exit(1);
		}

		//String line;

		System.out.println("Program terminated.");
	}
}
