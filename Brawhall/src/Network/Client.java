package Network;

  
import java.io.*; 
import java.net.*; 
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit; 
  
public class Client  
{ 
    final static int ServerPort = 1234; 
  
  LinkedBlockingQueue<String> toSend;
  LinkedBlockingQueue<String> received;
  boolean running=true;
  String StateClient;
    public Client() throws UnknownHostException, IOException  
    { 
       
          
        // getting localhost ip 
        InetAddress ip = InetAddress.getByName("localhost"); 
          
        // establish the connection 
        Socket s = new Socket(ip, ServerPort); 
          
        toSend=new  LinkedBlockingQueue<String>();
        received=new  LinkedBlockingQueue<String>();
        // obtaining input and out streams 
        DataInputStream dis = new DataInputStream(s.getInputStream()); 
        DataOutputStream dos = new DataOutputStream(s.getOutputStream()); 
        StateClient="Connected";
        
        // sendMessage thread 
        Thread sendMessage = new Thread(new Runnable()  
        { 
            @Override
            public void run() { 
                while (running) { 
  
                    // read the message to deliver. 
                    String msg;
					try {
						msg = toSend.take(); 
						dos.writeUTF(msg); 
					} catch (InterruptedException | IOException e) {
						
						e.printStackTrace();
					} 
                     
                }
                try {
					s.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            } 
        }); 
          
        // readMessage thread 
        Thread readMessage = new Thread(new Runnable()  
        { 
            @Override
            public void run() { 
  
                while (running) { 
                    try { 
                        // read the message sent to this client 
                        String msg = dis.readUTF(); 
                        received.put(msg); 
                        
                    } catch (IOException | InterruptedException e) { 
  
                        e.printStackTrace(); 
                    } 
                } 
            } 
        }); 
        running=true;
        sendMessage.start(); 
        readMessage.start(); 
  
    } 
    public void sendMessage(String s) throws InterruptedException {
    	toSend.put(s);
    }
    public String getMessage() throws InterruptedException {
    	return received.poll();
    }
	public String getStateClient() {
		// TODO Auto-generated method stub
		return StateClient;
	}
} 