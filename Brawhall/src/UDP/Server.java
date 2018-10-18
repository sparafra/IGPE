package UDP;
import java.io.*;
import java.net.*;
import java.util.ArrayList;


public class Server extends Thread {

	private DatagramSocket Server;
	String StateServer;
	//ArrayList<Connect> listConnect;
	
	String message="";
	boolean messageReaded = false;
	boolean InGame = false;
	InetAddress IpClient;
	int Port;
	int PortToSend;
	public Server() throws Exception
	{
		Port = 4000;
		//listConnect = new ArrayList<Connect>();
		Server = new DatagramSocket(Port);
		System.out.println("Il Server è in attesa sulla porta 4000.");
		this.start();
	}
	public void run() {
		 
		try {
	     //Attivo la Socket sul Server in ascolto sulla porta 7777
	     
	 
	     //Informazioni sul Server in ascolto
	     InetAddress indirizzo = Server.getLocalAddress();
	     String serverIp = indirizzo.getHostAddress();
	     int port = Server.getLocalPort();
	     System.out.println("In ascolto Server UDP: "+ serverIp + " porta: " + port);
	     StateServer = "Waiting";
	     
	     //Ciclo infinito per ascolto dei Client
	     while (true) {
	 
	       //Preparazione delle informazioni da ricevere
	       byte[] buf = new byte[500];
	       System.out.println("In attesa di chiamate dai Client... ");
	       DatagramPacket recv = new DatagramPacket(buf, buf.length);
	       Server.receive(recv);
	 
	       //Informazioni sul Client che ha effettuato la chiamata
	       IpClient = recv.getAddress();
	       String client = IpClient.getHostName();
	       PortToSend = recv.getPort();
	       System.out.println("In chiamata Client: "+ client + " porta: " + PortToSend);
	       StateServer = "Connected";
	 
	       //Messaggio ricevuto dal Client
	       message = (new String(recv.getData()).trim());
	       messageReaded = false;
	       System.out.println("Il Client ha scritto: " + message);
	     }
		}catch ( IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   }
	/*
	   public static void main (String[] args) throws IOException {
	     Server udpServer = new Server();
	     udpServer.start();
	   }
	   */
	  public String getStateServer() {return StateServer;};
	  public String getMessage(int idConnection) {return message; }
	  public void sendMessage(int idConnection, String message) throws IOException 
	  {
		  byte [] msg = {0};
		  msg = message.getBytes();
		//Creazione del pacchetto da inviare al Server
	      DatagramPacket hi = new DatagramPacket(msg, msg.length, IpClient, PortToSend);
	      hi.setData(msg);
	      hi.setLength(msg.length);
	 
	      //Invio del pacchetto sul Socket
	      Server.send(hi);
	  }
	  public boolean getMessageReaded(int idConnection) {return messageReaded;}
	  public void setMessageReaded(int idConnection, boolean R) {messageReaded = R;}
	  public void setInGame(int idConnection, boolean G) { InGame = G;}
		
}
