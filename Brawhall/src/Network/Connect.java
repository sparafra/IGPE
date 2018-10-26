package Network;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.LinkedList;

public class Connect extends Thread 
{
	private Socket client = null;
	BufferedReader in = null;
	PrintStream out = null;

	LinkedList<String> messages;
	boolean messageReaded = false;
	boolean InGame = false;
	
	public Connect() {}
	
	public Connect(Socket clientSocket)
	{
		client = clientSocket;
		messages=new LinkedList<String>();
		try
		{
			in = new BufferedReader(new InputStreamReader(client.getInputStream()));
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
			
			
			while(true)
			{
					if(in.ready())
					{
						String message=in.readLine();
						if(message!=null) {
							messages.push( message);
							
						}
					}

			}
			
		}
		catch(Exception e) {}
	}
	public String getMessage() {return messages.poll();}
	public void sendMessage(String Data)
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
	public void setInGame(boolean G) {InGame=G;}
}
