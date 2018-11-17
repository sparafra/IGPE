package Network;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.LinkedList;
import java.util.concurrent.LinkedBlockingQueue;

public class Connect extends Thread 
{
	private Socket client = null;
	DataInputStream in = null;
	DataOutputStream out = null;

	LinkedBlockingQueue<Message> messages;
	int id;
	public Connect() {}
	
	public Connect(Socket clientSocket, LinkedBlockingQueue<Message> messages)
	{
		client = clientSocket;
		this.messages=messages;
		try
		{
			in = new DataInputStream(client.getInputStream());
			out = new DataOutputStream(client.getOutputStream());

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
					
						String message=in.readUTF();
						if(message!=null) {
							Message m=new Message(message);
							m.put("client", id);
							messages.put(m);
							System.out.println(message);
						}
					

			}
			
		}
		catch(Exception e) {}
	}
	
	public void sendMessage(String Data)
	{	
		try {
			out.writeUTF(Data);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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


	public void setId(int i) {
		id=i;
		
	}
	
}
