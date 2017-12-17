import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
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
	InetAddress gpAddress;
	int gpPort;

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

	public InterfaceClient(/*MulticastSocket ms /*Socket clientSocket*/) throws IOException {
		
		//out = new PrintStream(clientSocket.getOutputStream());
		// Layout GUI
		panelMulticast.add(gpAddrLabel);
		panelMulticast.add(gpAddrField);
		panelMulticast.add(gpPortLabel);
		panelMulticast.add(gpPortField);
		btnMulticast.addActionListener(new ActionListener() {
			/**
			 * @param e
			 *            
			 */
			public void actionPerformed(ActionEvent e) {
				try
				{
					gpAddress = InetAddress.getByName(gpAddrField.getText()); 
		      	 gpPort = Integer.parseInt(gpPortField.getText());
		      	 ms = new MulticastSocket(gpPort);
		      	 ms.joinGroup(gpAddress);
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
				//out.println(msgField.getText());
				String toSend = msgField.getText();
				try {
					ms.send(new DatagramPacket (toSend.getBytes(), toSend.length(),gpAddress, gpPort));
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				msgField.setText("");
			}
		});
	}

}
