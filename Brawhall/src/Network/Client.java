package Network;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.LinkedList;

public class Client extends Thread
{
	BufferedReader in = null;
	PrintStream out = null;
	
	
	
	Socket socket = null;
	LinkedList<String> messages;

	String StateClient;
	boolean InGame = false;
	
	
	
	public Client(String Ip)throws Exception
	{
		messages=new LinkedList<String>();
		try
		{
			// open a socket connection
			socket = new Socket(Ip, 4000);
			// Apre i canali I/O
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintStream(socket.getOutputStream(), true);
			
			// Legge dal server
			//message = (String)ois.readObject();
			messages.push( in.readLine());
			
			StateClient = "Connected";
			this.start();
		}
		catch(Exception e) 
		{ 
			StateClient = "Waiting";
			e.printStackTrace();
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
						String message=in.readLine();
						if(message!=null) {
							messages.push( message);
							
						}
					}
			}
			catch(Exception e) {}
		}
	}
	public String getMessage() {
			return messages.poll();
	}
	public void sendMessage(String Data)
	{
		out.println(Data);
		out.flush();
	}
	public void reputMessage(String s) { messages.addFirst(s); }
	public void CloseConnection()
	{
		try
		{
			out.close();
			in.close();
		}
		catch(Exception e){};
	}
	public void setInGame(boolean G) {InGame=G;}
	public String getStateClient() {return StateClient;};
}

