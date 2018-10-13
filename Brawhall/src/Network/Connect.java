package Network;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class Connect extends Thread 
{
	private Socket client = null;
	BufferedReader in = null;
	PrintStream out = null;
	String message;
	
	public Connect() {}
	
	public Connect(Socket clientSocket)
	{
		client = clientSocket;
		try
		{
			in = new BufferedReader(
					new InputStreamReader(client.getInputStream()));
			out = new PrintStream(client.getOutputStream(), true);
		}
		catch(Exception e1)
		{
			try { client.close(); }
			catch(Exception e) { System.out.println(e.getMessage());}
			return;
		}
		this.start();
	}
	public void run()
	{
		try
		{
			out.println("Generico messaggio dal Server");
			out.flush();
			
			while(true)
			{
				if(in.ready())
				{
					message = in.readLine();
					System.out.print("Messaggio Ricevuto Dal Client: " + message);
				}
			}
			
		}
		catch(Exception e) {}
	}
	public void Send(String Data)
	{
		out.println(Data);
		out.flush();
	}
	public void CloseConnection()
	{
		try
		{
			out.close();
			in.close();
			client.close();
		}
		catch(Exception e){};
	}
}
