/**
 * from network/..
 * javac network/TcpClient.java
 * java network.TCPClient 
 */
package network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
//import java.util.Scanner;

public class TCPClient {
	public static void main(String[] args) throws Exception {
		
		String severAddress="127.0.0.1";  // localhost
		int severPort=8765;
		String clientMsg = "";
		String serverMsg = "";
		
		boolean quit=false;
		int vocali;
		int consonanti;

		try {
			// Create connection to server socket
			System.out.print("Client: Connessione al server=" + severAddress + ":" + severPort + " ... ");
			Socket socket = new Socket(severAddress, severPort); 
			System.out.println("Connected");

			// Create input and output streams to read/write data
			// Input stream tramite la classe java Scanner per i dati inseriti dall'utente 
			BufferedReader inUserStream = new BufferedReader(new InputStreamReader(System.in));
			//Scanner scanner = new Scanner(System.in);
			// Input stream per i dati provenienti dal socket 
			DataInputStream inSocketStream = new DataInputStream(socket.getInputStream());
			// Output stream 
			DataOutputStream outSocketStream = new DataOutputStream(socket.getOutputStream());
			
			while (quit==false) {
				// Prompt user to enter some text or 'quit'
				System.out.print("Client: inserisci il messaggio da inviare> ");
				//clientMsg = scanner.nextLine();
				clientMsg = inUserStream.readLine();

				// Send the entered text to server
				System.out.println("Client: invio il messaggio: " + clientMsg);
				outSocketStream.writeUTF(clientMsg);
				outSocketStream.flush();

				// Read data from socket input stream
				serverMsg = inSocketStream.readUTF();
				System.out.println("Client: ricevuto il messaggio: " + serverMsg);
				
				//Controllo vocali e consonanti
				vocali=0;
				consonanti=0;
				for (int i=0; i < clientMsg.length(  ); i++){
					if(Character.isLetter(clientMsg.charAt(i)))
						if(clientMsg.charAt(i)=='a'||clientMsg.charAt(i)=='A'||clientMsg.charAt(i)=='e'||clientMsg.charAt(i)=='E'||clientMsg.charAt(i)=='i'||
						clientMsg.charAt(i)=='I'||clientMsg.charAt(i)=='o'||clientMsg.charAt(i)=='O'||clientMsg.charAt(i)=='u'||clientMsg.charAt(i)=='U')
							vocali++;
						else 
							consonanti++;
				}

				if(vocali==consonanti){
					quit=true;
				}
			}

			// Close resources
			outSocketStream.close();
			inSocketStream.close();
			inUserStream.close();
			socket.close();
			//scanner.close();			
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
