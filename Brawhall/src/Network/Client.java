package Network;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class Client extends Thread
{
	BufferedReader in = null;
	PrintStream out = null;
	Socket socket = null;
	String message;
	String StateClient;
	public static void main(String argv[])
	{
		
		
	}
	public Client(String Ip)throws Exception
	{
		try
		{
			// open a socket connection
			socket = new Socket(Ip, 4000);
			// Apre i canali I/O
			in = new BufferedReader(
					new InputStreamReader(socket.getInputStream()));
			out = new PrintStream(socket.getOutputStream(), true);
			// Legge dal server
			message = in.readLine();
			System.out.print("Messaggio Ricevuto dal Server: " + message);
			StateClient = "Connected";
			this.start();
		}
		catch(Exception e) 
		{ 
			StateClient = "Waiting";
			System.out.println(e.getMessage());
		}
	}
	public void run()
	{
		while(true)
		{
			try
			{
				if(in.ready())
				{
					message = in.readLine();
					System.out.print("Messaggio Ricevuto : " + message);
				}
			}
			catch(Exception e) {}
		}
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
		}
		catch(Exception e){};
	}
	
	public String getStateClient() {return StateClient;};
}

