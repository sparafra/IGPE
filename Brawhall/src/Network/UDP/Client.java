package Network.UDP;
import java.io.*;
import java.net.*;

public class Client extends Thread{

	//Creazione della Socket per l'invio del Datagramma con porta Client dinamica
    DatagramSocket Client;
    
	String message="";
	boolean messageReaded = false;
	String StateClient;
	boolean InGame = false;
	InetAddress IpServer;
	int Port;
	
	public Client(String Ip) throws IOException 
	{
	       IpServer = InetAddress.getByName("192.168.1.5");
	       Port = 4000;
	       Client = new DatagramSocket();
	       StateClient = "Connected";
	       this.start();
	       
	       /*
	       byte [] msg = {0};
	 
	       //Creazione della Socket per l'invio del Datagramma con porta Client dinamica
	       DatagramSocket s = new DatagramSocket();
	 
	       //Creazione del pacchetto da inviare al Server
	       DatagramPacket hi = new DatagramPacket(msg, msg.length, addr, 7777);
	 
	       //Ciclo infinito per inserimento testo del Client
	       while (true) 
	       {
	         System.out.print("Inserisci: ");
	         String userInput = stdIn.readLine();
	         System.out.println("userInput: " + userInput);
	         msg = userInput.getBytes();
	         hi.setData(msg);
	         hi.setLength(msg.length);
	 
	         //Invio del pacchetto sul Socket
	         s.send(hi);
	       }
	       */
	}
	/*
	public static void main (String[] args) throws IOException 
	{
		Client udpClient = new Client();
	    udpClient.avvio();
	}*/
	public void run()
	{
		while(true)
		{
			try
			{
				byte[] buf = new byte[500];
			    System.out.println("In attesa di chiamate dai Server... ");
			    DatagramPacket recv = new DatagramPacket(buf, buf.length);
			    Client.receive(recv);
			 
			       //Informazioni sul Client che ha effettuato la chiamata
			    IpServer = recv.getAddress();
			    String client = IpServer.getHostName();
			    int porta = recv.getPort();
			    System.out.println("In chiamata Server: "+ client + " porta: " + porta);
			    StateClient = "Connected";
			 
			       //Messaggio ricevuto dal Client
			       message = (new String(recv.getData()).trim());
			       messageReaded = false;
			       System.out.println("Il Server ha scritto: " + message);
			}
			catch(Exception e) {}
		}
	}
	public String getMessage() {return message;}
	public void sendMessage(String Data) 
	{
		try {
		byte [] msg = {0};
		msg = Data.getBytes();
		//Creazione del pacchetto da inviare al Server
	    DatagramPacket hi = new DatagramPacket(msg, msg.length, IpServer, Port);
	    
	    hi.setData(msg);
	    hi.setLength(msg.length);
	 
	    //Invio del pacchetto sul Socket
	    Client.send(hi);
		}
		catch(IOException e) 
		{
			e.printStackTrace();
		}
		
	}
	public boolean getMessageReaded() {return messageReaded;}
	public void setMessageReaded(boolean R) {messageReaded = R;}
	public void CloseConnection()
	{
		try
		{
			//out.close();
			//in.close();
		}
		catch(Exception e){};
	}
	public void setInGame(boolean G) {InGame=G;}
	public String getStateClient() {return StateClient;};
}
