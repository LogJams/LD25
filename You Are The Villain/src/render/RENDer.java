package render;

import java.io.IOException;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import menuEntities.Button;

public class RENDer {
	
	int kills;
	int souls;
	float maxDistance;
	
	private Texture button;
	
	TextTool text;
	
	Button mainMenu;
	
	
	public RENDer(float[] scores) { //receives {furthestDistance, enemiesKilled, numSouls} from LogicCore at game end\\
		maxDistance = Math.round(scores[0] * 0.005f * 1000) / 1000f;
		kills = (int) scores[1];
		souls = (int) scores[2];
		
		loadTextures();
		
		text = new TextTool();
		mainMenu = new Button("Main Menu", 400, 455, 320, 70, button, 0.5f, 1, 90, text.CREEPY);
	}
	
	public boolean update(){
		
		text.renderText(text.CREEPY, 450, 150, "Humans Slaughtered: " + kills);
		text.renderText(text.CREEPY, 450, 200, "Excess Souls: " + souls);
		text.renderText(text.CREEPY, 450, 250, "Crusade Distance: " + maxDistance);
		int score = (int) (kills + souls + (maxDistance * 10));
		text.renderText(text.CREEPY, 450, 350, "Total Score: " + score);
		
		mainMenu.draw();
		
		return mainMenu.update();
	}
	
	public int getScore() {
		return (int) (kills + souls + (maxDistance * 10));
	}
	
	public void loadTextures() {
		try {
			button = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/ui/MainButtons.png"));
		} catch (IOException e) {
			System.out.println("Error Loading Textures");
			e.printStackTrace();
		}
	}

}
