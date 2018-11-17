package Objects;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;

public final class Media 
{
	static Toolkit tk;
	
	static String Path;
	
	//Object Type
	static HashMap<String, HashMap<PlayerState, LinkedList<Image>>> Characters;
	static HashMap<String, HashMap<PlayerState, LinkedList<Image>>> Blocks;
	static HashMap<String, HashMap<PlayerState, LinkedList<Image>>> Buttons;
	static HashMap<String, HashMap<PlayerState, LinkedList<Image>>> Backgrounds;
	

	//All Medias
	static HashMap<ObjectId, HashMap<String, HashMap<PlayerState, LinkedList<Image>>>> Medias;
	static HashMap<String, HashMap<String, Float>> PlayerSpecs;
	static LinkedList<String> CharacterNames;
	
	static HashMap<String, LinkedList<String>> Levels;
	
	public Media()
	{
		
	}
	
	public static void LoadMedia()
	{

		Toolkit tk;
		
		String Path;
		
		//Object Type
		HashMap<String, HashMap<PlayerState, LinkedList<Image>>> Characters;
		HashMap<String, HashMap<PlayerState, LinkedList<Image>>> Blocks;
		HashMap<String, HashMap<PlayerState, LinkedList<Image>>> Buttons;
		HashMap<String, HashMap<PlayerState, LinkedList<Image>>> Backgrounds;
		
		HashMap<String, LinkedList<String>> Levels;

		//All Medias
		HashMap<ObjectId, HashMap<String, HashMap<PlayerState, LinkedList<Image>>>> Medias;
		HashMap<String, HashMap<String, Float>> PlayerSpecs;
		LinkedList<String> CharacterNames;
		
		LinkedList<String> jsonLevel;
		
		tk = Toolkit.getDefaultToolkit();
		
		CharacterNames = new LinkedList<String>();
		
		PlayerSpecs = new HashMap<String, HashMap<String, Float>>();
		
		Medias = new HashMap<ObjectId, HashMap<String, HashMap<PlayerState, LinkedList<Image>>>>();

		Characters = new HashMap<String, HashMap<PlayerState, LinkedList<Image>>>();
		Blocks = new HashMap<String, HashMap<PlayerState, LinkedList<Image>>>();
		Buttons = new HashMap<String, HashMap<PlayerState, LinkedList<Image>>>();
		Backgrounds = new HashMap<String, HashMap<PlayerState, LinkedList<Image>>>();
		
		jsonLevel = new LinkedList<String>();
		
		Levels = new HashMap<String, LinkedList<String>>();
		
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
						else if(Folders2.get(j).getName().equals("Json"))
						{
							jsonLevel.add(loadJson(Files.get(t)));
							System.out.println(Folders1.get(i).getName());
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
					CharacterNames.add(Folders1.get(i).getName());
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
				else if(Folders.get(k).getName().equals("Levels"))
				{
					Levels.put(Folders1.get(i).getName(), jsonLevel);
				}
			}
		}
		
		Medias.put(ObjectId.CHARACTER, Characters);
		Medias.put(ObjectId.BUTTON, Buttons);
		Medias.put(ObjectId.BACKGROUND, Backgrounds);
		Medias.put(ObjectId.BLOCK, Blocks);
		
		Media.Medias= Medias;
		Media.Backgrounds=Backgrounds;
		Media.Characters=Characters;
		Media.Buttons=Buttons;
		Media.Blocks=Blocks;
		
		Media.CharacterNames=CharacterNames;
		Media.PlayerSpecs=PlayerSpecs;
		
		Media.Levels = Levels;
	
	}
	
	public static Image getImage(ObjectId Type, PlayerState S, String Name, int Frames)
	{
		return Media.Medias.get(Type).get(Name).get(S).get(Frames);
	}
	public static LinkedList<Image> getImages(ObjectId Type, PlayerState S, String Name){
		return Media.Medias.get(Type).get(Name).get(S);
	}
	public static int getCharacters() {return Medias.get(ObjectId.CHARACTER).size();} 
	public static LinkedList<String> getCharactersName() 
	{
		return CharacterNames;
	}
	public static int nextCharacterFrames(PlayerState S, String Name)
	{
		return 0;
		/*
		if(LastState != S)
			CurrentFrame =0;
		LastState = S;
		if(S == PlayerState.JUMPINGFORWARD || S == PlayerState.JUMPINGBACK || S == PlayerState.FALLINGFORWARD || S == PlayerState.FALLINGBACK || S == PlayerState.CROUCHINGFORWARD || S == PlayerState.CROUCHINGBACK)
		{
			if(CurrentFrame < Medias.get(ObjectId.CHARACTER).get(Name).get(S).size() -1)
			{
				CurrentFrame++;
			}
		}
		else if (S == PlayerState.FORWARD || S == PlayerState.BACK || S == PlayerState.ATTACKINGFORWARD || S == PlayerState.ATTACKINGBACK)
		{
			if(CurrentFrame < Medias.get(ObjectId.CHARACTER).get(Name).get(S).size() -1)
			{
				CurrentFrame++;
			}
			else
			{
				CurrentFrame = 0;
			}
		}
		
		return CurrentFrame;
	*/}
	private static LinkedList<File> getFolders(String Path)
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
	private static LinkedList<File> getFiles(String Path)
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
	
	private static HashMap<String, Float> loadSpecs(File f) 
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
	private static String loadJson(File f) 
	{
		try
		{
			System.out.println(f.getPath());
			BufferedReader br = new BufferedReader(new FileReader(f));
			String st = br.readLine();
			
			br.close();
			return st;
		}catch(IOException e) {return null;}
	}
	
	public static String getLevel(String LevelName)
	{
		//System.out.println(Levels.get(LevelName).get(0));
		return Levels.get(LevelName).get(0);
	}
		
	public static HashMap<String, Float> getPlayerSpecs(String PlayerName){return PlayerSpecs.get(PlayerName);}

	}
