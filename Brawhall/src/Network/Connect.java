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

public class Connect extends Thread 
{
	private Socket client = null;
	BufferedReader in = null;
	PrintStream out = null;
	InputStream is = null;
	ObjectInputStream ois = null;
	OutputStream os = null;
	ObjectOutputStream oos = null;
	
	String message="";
	boolean messageReaded = false;
	boolean InGame = false;
	//Player P;
	
	public Connect() {}
	
	public Connect(Socket clientSocket)
	{
		client = clientSocket;
		try
		{
			in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			out = new PrintStream(client.getOutputStream(), true);
			
			//is = client.getInputStream();
			//ois = new ObjectInputStream(is);
			
			//os = client.getOutputStream();
			//oos = new ObjectOutputStream(os);
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
			//oos.writeObject(new String("Generico messaggio dal Server"));
			//oos.flush();
			out.println("Generico messaggio dal Server");
			out.flush();
			
			while(true)
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
						//System.out.print("Messaggio Ricevuto Dal Client: " + message);
						messageReaded = false;
					}
				//}
				//else
				//{
					//P = (Player)ois.readObject();
					//messageReaded = false;
				//}
			}
			
		}
		catch(Exception e) {}
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
	public boolean getMessageReaded() {return messageReaded;} 
	public void setMessageReaded(boolean R) {messageReaded = R;}
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
