package Network;

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
	InputStream is = null;
	ObjectInputStream ois = null;
	OutputStream os = null;
	ObjectOutputStream oos = null;
	
	Socket socket = null;
	String message="";
	boolean messageReaded = false;
	String StateClient;
	boolean InGame = false;
	//Player P;
	
	
	public Client(String Ip)throws Exception
	{
		try
		{
			// open a socket connection
			socket = new Socket(Ip, 4000);
			// Apre i canali I/O
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintStream(socket.getOutputStream(), true);
			
			//is = socket.getInputStream();
			//ois = new ObjectInputStream(is);
			
			//os = socket.getOutputStream();
			//oos = new ObjectOutputStream(os);
			
			// Legge dal server
			//message = (String)ois.readObject();
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
				//if(!InGame)
				//{
					/*
					message = (String)ois.readObject();
					System.out.print("Messaggio Ricevuto Dal Client: " + message);
					messageReaded = false;
					*/
					if(in.ready())
					{
						message = in.readLine();
						//System.out.print("Messaggio Ricevuto : " + message);
						messageReaded = false;
					}
				//}
				//else
				//{
					//P = (Player)ois.readObject();
					//messageReaded = false;
				//}
			}
			catch(Exception e) {}
		}
	}
	public String getMessage() {return message;}
	public void sendMessage(String Data)
	{
		/*
		try {
			oos.writeObject(Data);
			oos.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
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
	/*
	public void sendPlayerObject(Player Pl)
	{
		try {
			oos.writeObject(Pl);
			oos.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public Player getPlayerObject() {return P;}*/
}

