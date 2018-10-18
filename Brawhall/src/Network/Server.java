package Network;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server extends Thread
{
	
	private ServerSocket Server;
	String StateServer;
	ArrayList<Connect> listConnect;
	
	public Server() throws Exception
	{
		
		listConnect = new ArrayList<Connect>();
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
				listConnect.add(c);
				StateServer = "Connected";
			}
			catch(Exception e) {}
		}
	}
	public String getStateServer() {return StateServer;};
	public String getMessage(int idConnection) {return listConnect.get(idConnection).getMessage(); }
	public void sendMessage(int idConnection, String message) {listConnect.get(idConnection).sendMessage(message);}
	public boolean getMessageReaded(int idConnection) {return listConnect.get(idConnection).getMessageReaded();}
	public void setMessageReaded(int idConnection, boolean R) {listConnect.get(idConnection).setMessageReaded(R);}
	public void setInGame(int idConnection, boolean G) { listConnect.get(idConnection).setInGame(G);}
	
}
