package TCP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;

import object.Player;

public class Client extends Thread
{
	BufferedReader in = null;
	PrintStream out = null;
	
	
	
	Socket socket = null;
	String message="";
	boolean messageReaded = false;
	String StateClient;
	boolean InGame = false;
	
	
	
	public Client(String Ip)throws Exception
	{
		try
		{
			// open a socket connection
			socket = new Socket(Ip, 4000);
			// Apre i canali I/O
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
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
						//System.out.print("Messaggio Ricevuto : " + message);
						messageReaded = false;
					}
			}
			catch(Exception e) {}
		}
	}
	public String getMessage() {return message;}
	public void sendMessage(String Data)
	{
		out.println(Data);
		out.flush();
	}
	public boolean getMessageReaded() {return messageReaded;}
	public void setMessageReaded(boolean R) {messageReaded = R;}
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

