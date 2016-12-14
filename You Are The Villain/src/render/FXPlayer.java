package render;

import java.io.IOException;

import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.util.ResourceLoader;

public class FXPlayer {
	
	public final int HAPPY = 0;
	
	Audio[] sounds = new Audio[1];
	
	public FXPlayer(int type) {
		
		if (type == HAPPY) {
			loadHappySounds();
		}
	}
	
	public void playSound(int sound) {
		sounds[sound].playAsSoundEffect(1, 1, false);
	}
	
	public void loadHappySounds() {
		try {
			sounds[0] = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("res/Audio/SFX/Start Sound Button.wav"));
			
		} catch (IOException e) {
			System.out.println("Failed to load SFX");
			e.printStackTrace();
		}
	}
}
