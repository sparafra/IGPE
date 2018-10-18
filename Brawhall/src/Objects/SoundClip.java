package Objects;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SoundClip {

	String soundName;
	String Path;
	Clip clip;
	
	public static enum Volume {
	      MUTE, LOW, MEDIUM, HIGH
	   }
	   
	public static Volume volume = Volume.LOW;
	   
	public SoundClip(String soundName)
	{
		this.soundName = soundName;
		try {
	         // Use URL (instead of File) to read from disk and JAR.
			 System.out.println(this.getClass().getClassLoader().getResource("\\Sound\\"+soundName));
	         URL url = this.getClass().getClassLoader().getResource("\\Sound\\"+soundName);
	         // Set up an audio input stream piped from the sound file.
	         AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
	         // Get a clip resource.
	         clip = AudioSystem.getClip();
	         // Open audio clip and load samples from the audio input stream.
	         clip.open(audioInputStream);
	      } catch (UnsupportedAudioFileException e) {
	         e.printStackTrace();
	      } catch (IOException e) {
	         e.printStackTrace();
	      } catch (LineUnavailableException e) {
	         e.printStackTrace();
	      }
	}
	
	public void Play()
	{
		if (volume != Volume.MUTE) 
		{
	         if (!clip.isRunning())
	         {
	        	 clip.setFramePosition(0); // rewind to the beginning
	        	 clip.start();     // Start playing
	         }
	         
	    }
	}
	public void Stop()
	{
		if (volume != Volume.MUTE) 
		{
	         if (clip.isRunning())
	        	 clip.stop(); 
	    }
	}
}
