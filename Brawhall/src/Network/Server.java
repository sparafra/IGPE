package Network;
import java.io.*;
import java.net.*;
import java.util.*;

public class Server extends Thread
{
	
	private ServerSocket Server;
	
	public Server() throws Exception
	{
		//new Server();
		Server = new ServerSocket(4000);
		System.out.println("Il Server � in attesa sulla porta 4000.");
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
				System.out.println("Connessione accettata da: "+ client.getInetAddress());
				Connect c = new Connect(client);
			}
			catch(Exception e) {}
		}
	}
	
}
