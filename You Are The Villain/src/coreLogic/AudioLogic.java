package coreLogic;

import java.io.IOException;

import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.util.ResourceLoader;

public class AudioLogic {

	private Audio[] titleMusic = new Audio[2];
	private Audio[] battleMusic = new Audio[3];
	private Audio credits;


	AudioLogic() {

		loadSounds();

		titleMusic[0].playAsMusic(1,1,false);
	}
	
	public void reset() {
		titleMusic[0].playAsMusic(1,1,false);
	}

	public void playIntroMusic() {

		if (!titleMusic[0].isPlaying() && !titleMusic[1].isPlaying()) {
			titleMusic[1].playAsMusic(1, 1, true);
		}
	}

	public void playBattleMusic(int audioType) {
		if (audioType < 0 || audioType > 2) {
			audioType = 0;
		}
		
		if (!battleMusic[0].isPlaying() && !battleMusic[1].isPlaying()) {
			battleMusic[audioType].playAsMusic(1, 1, false);
		}

	}

	public void playCredits() {
		

		if (!credits.isPlaying()) {
			credits.playAsMusic(1, 1, false);
		}

	}


	public void loadSounds() {
		try {
			titleMusic[0] = AudioLoader.getAudio("OGG", ResourceLoader.getResourceAsStream("res/Audio/Main Title.ogg"));
			titleMusic[1] = AudioLoader.getAudio("OGG", ResourceLoader.getResourceAsStream("res/Audio/Main Title Loop.ogg"));
			battleMusic[0] = AudioLoader.getAudio("OGG", ResourceLoader.getResourceAsStream("res/Audio/Battle1.ogg"));
			battleMusic[1] = AudioLoader.getAudio("OGG", ResourceLoader.getResourceAsStream("res/Audio/Battle2.ogg"));
			battleMusic[2] = AudioLoader.getAudio("OGG", ResourceLoader.getResourceAsStream("res/Audio/Battle3.ogg"));
			credits = AudioLoader.getAudio("OGG", ResourceLoader.getResourceAsStream("res/Audio/Credits.ogg"));
		} catch (IOException e) {
			System.out.println("Failed to load audio");
			e.printStackTrace();
		}
	}
	
}
