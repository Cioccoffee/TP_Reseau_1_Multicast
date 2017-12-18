import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.Socket;

import javax.swing.*;

public class InterfaceClient {

	BufferedReader in;
	// PrintStream out;
	JFrame frame = new JFrame("Chat");

	MulticastSocket ms;
	InetAddress gpAddress = InetAddress.getByName("224.0.0.1");
	int gpPort = 3000;
	
	ClientThread ctSendingToServer;
	ClientThread ctReceivingFromServer;
	
	String id = "user";
	JTextField idField = new JTextField(20);
	JLabel idLabel = new JLabel("your pseudo :");
	
	JTextField msgField = new JTextField(40);
	JTextArea messageArea = new JTextArea(40, 40);

	JLabel gpAddrLabel = new JLabel("IP of the group :");
	JTextField gpAddrField = new JTextField(20);
	JLabel gpPortLabel = new JLabel("Port of the group :");;
	JTextField gpPortField = new JTextField(10);
	JButton btnMulticast = new JButton("Join group");
	JPanel panelMulticast = new JPanel();

	public MulticastSocket getMS() {
		return ms;
	}

	public InterfaceClient(/* MulticastSocket ms /*Socket clientSocket */) throws IOException {

		// out = new PrintStream(clientSocket.getOutputStream());
		// Layout GUI
		panelMulticast.add(idLabel);
		panelMulticast.add(idField);
		panelMulticast.add(gpAddrLabel);
		panelMulticast.add(gpAddrField);
		panelMulticast.add(gpPortLabel);
		panelMulticast.add(gpPortField);
		InterfaceClient ic = this;
		btnMulticast.addActionListener(new ActionListener() {
			/**
			 * @param e
			 * 
			 */
			public void actionPerformed(ActionEvent e) {
				try {
					id = idField.getText();
					gpAddress = InetAddress.getByName(gpAddrField.getText());
					gpPort = Integer.parseInt(gpPortField.getText());
					ms = new MulticastSocket(gpPort);
					ms.joinGroup(gpAddress);
//					// reading
					ctSendingToServer = new ClientThread(ms, true, ic);
					ctSendingToServer.start();

					// listening
					ctReceivingFromServer = new ClientThread(ms, false, ic);
					ctReceivingFromServer.start();

				} catch (IOException e1) {
					e1.printStackTrace();
				}

			}
		});
		panelMulticast.add(btnMulticast);
		msgField.setEditable(true);
		msgField.setSelectedTextColor(Color.BLUE);
		messageArea.setEditable(false);
		frame.getContentPane().add(panelMulticast, "North");
		frame.getContentPane().add(msgField, "South");
		frame.getContentPane().add(new JScrollPane(messageArea), "Center");
		frame.pack();

		// Add Listeners
		msgField.addActionListener(new ActionListener() {
			/**
			 * @param e
			 *            Envoie le message lorsque le client appuie sur la
			 *            touche Entrée.
			 */
			public void actionPerformed(ActionEvent e) {
				// out.println(msgField.getText());
				String toSend = id +" : "+ msgField.getText();
				System.out.println(toSend.getBytes().length);
				try {
					ms.send(new DatagramPacket(toSend.getBytes(), toSend.getBytes().length, gpAddress, gpPort));
					// creating a history file with all messages
					FileWriter fw = new FileWriter("history.txt",true);
					BufferedWriter bw = new BufferedWriter(fw);
					bw.write(toSend+"\n");
					bw.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				msgField.setText("");
			}
		});
	}

}
