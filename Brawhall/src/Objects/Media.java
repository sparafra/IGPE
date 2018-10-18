package Objects;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;

public class Media 
{
	Toolkit tk;
	
	String Path;
	
	//Object Type
	HashMap<String, HashMap<PlayerState, LinkedList<Image>>> Characters;
	HashMap<String, HashMap<PlayerState, LinkedList<Image>>> Blocks;
	HashMap<String, HashMap<PlayerState, LinkedList<Image>>> Buttons;
	HashMap<String, HashMap<PlayerState, LinkedList<Image>>> Backgrounds;
	

	//All Media
	HashMap<ObjectId, HashMap<String, HashMap<PlayerState, LinkedList<Image>>>> Media;
	
	HashMap<String, HashMap<String, Float>> PlayerSpecs;
	
	int CurrentFrame =0;
	PlayerState LastState = PlayerState.FALLINGFORWARD;
	
	LinkedList<String> charactersName;
	
	
	public Media()
	{
		tk = Toolkit.getDefaultToolkit();
		
		charactersName = new LinkedList<String>();
		
		PlayerSpecs = new HashMap<String, HashMap<String, Float>>();
		
		Media = new HashMap<ObjectId, HashMap<String, HashMap<PlayerState, LinkedList<Image>>>>();

		Characters = new HashMap<String, HashMap<PlayerState, LinkedList<Image>>>();
		Blocks = new HashMap<String, HashMap<PlayerState, LinkedList<Image>>>();
		Buttons = new HashMap<String, HashMap<PlayerState, LinkedList<Image>>>();
		Backgrounds = new HashMap<String, HashMap<PlayerState, LinkedList<Image>>>();
		
		
		LoadMedia();
	}
	
	public void LoadMedia()
	{
		
		Path = "";
		try {
			Path = new File(".").getCanonicalPath();
			Path += "\\bin\\Images\\";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println(Path);
		
		LinkedList<File> Folders = getFolders(Path); //Search Folders (ex Background, Buttons, Blocks, Character)
		for(int k=0; k<Folders.size(); k++)
		{
			LinkedList<File> Folders1 = getFolders(Path+Folders.get(k).getName()+"\\"); //Search SubFolders (ex IronMan, Hulk of Character, Start, Setting of Buttons ecc.)
			for(int i=0; i<Folders1.size(); i++)
			{
				HashMap<PlayerState, LinkedList<Image>> objectName = new HashMap<PlayerState, LinkedList<Image>>();

				LinkedList<File> Folders2 = getFolders(Path+Folders.get(k).getName()+"\\"+Folders1.get(i).getName()+"\\"); //Search SubFolders (ex Left, Right ecc of IronMan, Left, Right ecc of Hulk, Null of Buttons ecc)
				for(int j=0; j<Folders2.size(); j++)
				{
					LinkedList<File> Files = getFiles(Path+Folders.get(k).getName()+"\\"+Folders1.get(i).getName()+"\\"+Folders2.get(j).getName()+"\\"); //Search Files (ex Images of Left Folder of IronMan, Images of Null Folder of Standard Button)
					LinkedList<Image> Frames = new LinkedList<Image>();

					for(int t=0; t<Files.size(); t++)
					{
						if(Folders2.get(j).getName().equals("Specs"))
						{
							PlayerSpecs.put(Folders1.get(i).getName(), loadSpecs(Files.get(t)));
							
						}
						else
						{
							String PathFiles = Path+Folders.get(k).getName()+"\\"+Folders1.get(i).getName()+"\\"+Folders2.get(j).getName()+"\\" + Files.get(t).getName();
							Frames.add(tk.getImage(PathFiles));
	                		//System.out.println(PathFiles);
						}
					}
					if(Folders2.get(j).getName().equals("Right"))
					{
						objectName.put(PlayerState.FORWARD, Frames);
					}
					else if(Folders2.get(j).getName().equals("Left"))
					{
						objectName.put(PlayerState.BACK, Frames);
					}
					else if(Folders2.get(j).getName().equals("SteadyRight"))
					{
						objectName.put(PlayerState.STEADYFORWARD, Frames);
					}
					else if(Folders2.get(j).getName().equals("SteadyLeft"))
					{
						objectName.put(PlayerState.STEADYBACK, Frames);
					}
					else if (Folders2.get(j).getName().equals("JumpingForward"))
					{
						objectName.put(PlayerState.JUMPINGFORWARD, Frames);
					}
					else if (Folders2.get(j).getName().equals("JumpingBack"))
					{
						objectName.put(PlayerState.JUMPINGBACK, Frames);
					}
					else if (Folders2.get(j).getName().equals("FallingForward"))
					{
						objectName.put(PlayerState.FALLINGFORWARD, Frames);
					}
					else if (Folders2.get(j).getName().equals("FallingBack"))
					{
						objectName.put(PlayerState.FALLINGBACK, Frames);
					}
					else if (Folders2.get(j).getName().equals("CrouchingForward"))
					{
						objectName.put(PlayerState.CROUCHINGFORWARD, Frames);
					}
					else if (Folders2.get(j).getName().equals("CrouchingBack"))
					{
						objectName.put(PlayerState.CROUCHINGBACK, Frames);
					}
					else if (Folders2.get(j).getName().equals("Icon"))
					{
						objectName.put(PlayerState.PREVIEW, Frames);
					}
					else if (Folders2.get(j).getName().equals("AttackForward"))
					{
						objectName.put(PlayerState.ATTACKINGFORWARD, Frames);
					}
					else if (Folders2.get(j).getName().equals("AttackBack"))
					{
						objectName.put(PlayerState.ATTACKINGBACK, Frames);
					}
					else if(Folders2.get(j).getName().equals("Null"))
					{
						objectName.put(PlayerState.NULL, Frames);
					}
					else if(Folders2.get(j).getName().equals("Waiting"))
					{
						objectName.put(PlayerState.WAITING, Frames);
					}
				}
				if(Folders.get(k).getName().equals("Characters"))
				{
					Characters.put(Folders1.get(i).getName(), objectName);
					charactersName.add(Folders1.get(i).getName());
					System.out.println(Folders1.get(i).getName() + "  Aggiunto");
				}
				else if(Folders.get(k).getName().equals("Blocks"))
				{
					Blocks.put(Folders1.get(i).getName(), objectName);
				}
				else if(Folders.get(k).getName().equals("Buttons"))
				{
					Buttons.put(Folders1.get(i).getName(), objectName);

				}
				else if(Folders.get(k).getName().equals("Backgrounds"))
				{
					Backgrounds.put(Folders1.get(i).getName(), objectName);
				}
			}
		}
		
		Media.put(ObjectId.CHARACTER, Characters);
		Media.put(ObjectId.BUTTON, Buttons);
		Media.put(ObjectId.BACKGROUND, Backgrounds);
		Media.put(ObjectId.BLOCK, Blocks);
		
	}
	
	public Image getImage(ObjectId Type, PlayerState S, String Name, int Frames)
	{
		return Media.get(Type).get(Name).get(S).get(Frames);
	}
	public int getCharacters() {return Media.get(ObjectId.CHARACTER).size();} 
	public LinkedList<String> getCharactersName() 
	{
		return charactersName;
	}
	public int nextCharacterFrames(PlayerState S, String Name)
	{
		if(LastState != S)
			CurrentFrame =0;
		LastState = S;
		if(S == PlayerState.JUMPINGFORWARD || S == PlayerState.JUMPINGBACK || S == PlayerState.FALLINGFORWARD || S == PlayerState.FALLINGBACK || S == PlayerState.CROUCHINGFORWARD || S == PlayerState.CROUCHINGBACK)
		{
			if(CurrentFrame < Media.get(ObjectId.CHARACTER).get(Name).get(S).size() -1)
			{
				CurrentFrame++;
			}
		}
		else if (S == PlayerState.FORWARD || S == PlayerState.BACK || S == PlayerState.ATTACKINGFORWARD || S == PlayerState.ATTACKINGBACK)
		{
			if(CurrentFrame < Media.get(ObjectId.CHARACTER).get(Name).get(S).size() -1)
			{
				CurrentFrame++;
			}
			else
			{
				CurrentFrame = 0;
			}
		}
		
		return CurrentFrame;
	}
	private LinkedList<File> getFolders(String Path)
	{
		File directory = new File(Path);
        //get all the files from a directory
        File[] fList = directory.listFiles();
        LinkedList<File> Folders = new LinkedList<File>();
        for (File file : fList)
        {
            if (file.isDirectory())
            {
            	Folders.add(file);
            }
        }
        return Folders;
	}
	private LinkedList<File> getFiles(String Path)
	{
		File directory = new File(Path);
        //get all the files from a directory
        File[] fList = directory.listFiles();
        LinkedList<File> Files = new LinkedList<File>();
        for (File file : fList)
        {
            if (file.isFile())
            {
            	Files.add(file);
            }
        }
        return Files;
	}
	
	private HashMap<String, Float> loadSpecs(File f) 
	{
		try
		{
			System.out.println(f.getPath());
			BufferedReader br = new BufferedReader(new FileReader(f));
			HashMap<String, Float> specs = new HashMap<String, Float>();
			String st;
			while ((st = br.readLine()) != null)
			{
				System.out.println(st);
				
				String attribute = st.substring(0, st.indexOf('='));
				Float value = Float.parseFloat(st.substring(st.indexOf('=')+1));
				//System.out.println(attribute + value); 
				specs.put(attribute, value);
			}
			br.close();
			return specs;
		}catch(IOException e) {return null;}
	}
		
	//public HashMap<String, Float> getPlayerSpecs(String PlayerName){return PlayerSpecs.get(PlayerName);}
	public HashMap<String, Float> getPlayerSpecs(String PlayerName){return PlayerSpecs.get(PlayerName);}
}
