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

		Socket echoSocket = null;
		PrintStream socOut = null;
		BufferedReader stdIn = null;
		BufferedReader socIn = null;

		// args doit prendre comme arg l'adresse IP et le port
		// add IP : localhost
		// port > 1024 pour etre un serveur
		if (args.length != 2) {
			System.out.println("Usage: java EchoClient <EchoServer host> <EchoServer port>");
			System.exit(1);
		}

		try {
			// creation socket ==> connexion
			// echoSocket = new Socket(args[0],new Integer(args[1]).intValue());
			/*
			 * socIn = new BufferedReader( new
			 * InputStreamReader(echoSocket.getInputStream())); socOut= new
			 * PrintStream(echoSocket.getOutputStream()); stdIn = new BufferedReader(new
			 * InputStreamReader(System.in)); System.out.println("Connected to the server");
			 */

			InterfaceClient ic = new InterfaceClient();
			ic.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			ic.frame.setVisible(true);

			MulticastSocket ms = ic.getMS();
			// reading
			ClientThread ctSendingToServer = new ClientThread(ms, true, ic);
			ctSendingToServer.start();

			// listening
			ClientThread ctReceivingFromServer = new ClientThread(ms, false, ic);
			ctReceivingFromServer.start();

			// recuperation de l'historique
			File file = new File("D:\\java\\TP1_Reseaux\\src\\history.txt");
			if (!file.exists()) {
				file.createNewFile();
			}

			FileInputStream load = new FileInputStream(file);

			BufferedReader reader = new BufferedReader(new InputStreamReader(load));
			// StringBuilder previousContent = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				// previousContent.append(line);
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

				if (!ctSendingToServer.keepOn)
					break;
			}

		} catch (UnknownHostException e) {
			System.err.println("Don't know about host:" + args[0]);
			System.exit(1);
		} catch (IOException e) {
			System.err.println("Couldn't get I/O for " + "the connection to:" + args[0]);
			System.exit(1);
		}

		String line;

		System.out.println("Program terminated.");
	}
}
