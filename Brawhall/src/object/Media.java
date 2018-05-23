package object;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;

import object.State;

public class Media 
{
	Toolkit tk;
	
	String Path;
	
	//Object Type
	HashMap<String, HashMap<State, LinkedList<Image>>> Characters;
	HashMap<String, HashMap<State, LinkedList<Image>>> Blocks;
	HashMap<String, HashMap<State, LinkedList<Image>>> Buttons;
	HashMap<String, HashMap<State, LinkedList<Image>>> Backgrounds;
	

	//All Media
	HashMap<ObjectId, HashMap<String, HashMap<State, LinkedList<Image>>>> Media;
	
	int CurrentFrame =0;
	State LastState = State.FALLING;
	
	LinkedList<String> charactersName;
	
	
	public Media()
	{
		tk = Toolkit.getDefaultToolkit();
		
		charactersName = new LinkedList<String>();
		
		Media = new HashMap<ObjectId, HashMap<String, HashMap<State, LinkedList<Image>>>>();

		Characters = new HashMap<String, HashMap<State, LinkedList<Image>>>();
		Blocks = new HashMap<String, HashMap<State, LinkedList<Image>>>();
		Buttons = new HashMap<String, HashMap<State, LinkedList<Image>>>();
		Backgrounds = new HashMap<String, HashMap<State, LinkedList<Image>>>();
		
		
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
		System.out.println(Path);
		
		LinkedList<File> Folders = getFolders(Path); //Search Folders (ex Background, Buttons, Blocks, Character)
		for(int k=0; k<Folders.size(); k++)
		{
			LinkedList<File> Folders1 = getFolders(Path+Folders.get(k).getName()+"\\"); //Search SubFolders (ex IronMan, Hulk of Character, Start, Setting of Buttons ecc.)
			for(int i=0; i<Folders1.size(); i++)
			{
				HashMap<State, LinkedList<Image>> objectName = new HashMap<State, LinkedList<Image>>();

				LinkedList<File> Folders2 = getFolders(Path+Folders.get(k).getName()+"\\"+Folders1.get(i).getName()+"\\"); //Search SubFolders (ex Left, Right ecc of IronMan, Left, Right ecc of Hulk, Null of Buttons ecc)
				for(int j=0; j<Folders2.size(); j++)
				{
					LinkedList<File> Files = getFiles(Path+Folders.get(k).getName()+"\\"+Folders1.get(i).getName()+"\\"+Folders2.get(j).getName()+"\\"); //Search Files (ex Images of Left Folder of IronMan, Images of Null Folder of Standard Button)
					LinkedList<Image> Frames = new LinkedList<Image>();

					for(int t=0; t<Files.size(); t++)
					{
						String PathFiles = Path+Folders.get(k).getName()+"\\"+Folders1.get(i).getName()+"\\"+Folders2.get(j).getName()+"\\" + Files.get(t).getName();
						Frames.add(tk.getImage(PathFiles));
	                	System.out.println(PathFiles);
					}
					if(Folders2.get(j).getName().equals("Right"))
					{
						objectName.put(State.FORWARD, Frames);
					}
					else if(Folders2.get(j).getName().equals("Left"))
					{
						objectName.put(State.BACK, Frames);
					}
					else if(Folders2.get(j).getName().equals("SteadyRight"))
					{
						objectName.put(State.STEADYFORWARD, Frames);
					}
					else if(Folders2.get(j).getName().equals("SteadyLeft"))
					{
						objectName.put(State.STEADYBACK, Frames);
					}
					else if (Folders2.get(j).getName().equals("Jumping"))
					{
						objectName.put(State.JUMPING, Frames);
					}
					else if (Folders2.get(j).getName().equals("Falling"))
					{
						objectName.put(State.FALLING, Frames);
					}
					else if (Folders2.get(j).getName().equals("Crouching"))
					{
						objectName.put(State.CROUCHING, Frames);
					}
					else if (Folders2.get(j).getName().equals("Icon"))
					{
						objectName.put(State.PREVIEW, Frames);
						System.out.println("Icon");
					}
					else if(Folders2.get(j).getName().equals("Null"))
					{
						objectName.put(State.NULL, Frames);
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
	
	public Image getImage(ObjectId Type, State S, String Name, int Frames)
	{
		return Media.get(Type).get(Name).get(S).get(Frames);
	}
	public int getCharacters() {return Media.get(ObjectId.CHARACTER).size();} 
	public LinkedList<String> getCharactersName() 
	{
		return charactersName;
	}
	public int nextCharacterFrames(State S, String Name)
	{
		if(LastState != S)
			CurrentFrame =0;
		LastState = S;
		if(S == State.JUMPING || S == State.FALLING || S == State.CROUCHING)
		{
			if(CurrentFrame < Media.get(ObjectId.CHARACTER).get(Name).get(S).size() -1)
			{
				CurrentFrame++;
			}
		}
		else if (S == State.FORWARD || S == State.BACK)
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
}
