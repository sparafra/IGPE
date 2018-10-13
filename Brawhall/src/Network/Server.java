package Network;
import java.io.*;
import java.net.*;
import java.util.*;

public class Server extends Thread
{
	
	private ServerSocket Server;
	String StateServer;
	public Server() throws Exception
	{
		//new Server();
		Server = new ServerSocket(4000);
		System.out.println("Il Server è in attesa sulla porta 4000.");
		this.start();
	}
	public void run()
	{
		while(true)
		{
			try 
			{
				System.out.println("In attesa di Connessione.");
				Socket client = Server.accept();
				StateServer = "Waiting";
				System.out.println("Connessione accettata da: "+ client.getInetAddress());
				Connect c = new Connect(client);
				StateServer = "Connected";
			}
			catch(Exception e) {}
		}
	}
	public String getStateServer() {return StateServer;};
	
	
}
