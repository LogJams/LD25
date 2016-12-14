package ui.menu;

import java.io.IOException;

import menuEntities.Button;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import render.TextTool;

public class PauseMenu {

	TextTool text;
	Texture background;
	
	Button b;

	public PauseMenu(Button b) {
		loadBackground();
		this.b = b;
		text = new TextTool();
	}


	public void update() {
		background.bind();
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2d(0, 0);
		GL11.glVertex2d(0, 0);

		GL11.glTexCoord2d(1, 0);
		GL11.glVertex2d(Display.getWidth(), 0);

		GL11.glTexCoord2d(1, 1);
		GL11.glVertex2d(Display.getWidth(), Display.getHeight());

		GL11.glTexCoord2d(0, 1);
		GL11.glVertex2d(0, Display.getHeight());
		GL11.glEnd();
		
		b.draw();
				
		text.renderText(text.CREEPY, 450, 100, "GAME IS PAUSED");
	}

	public void loadBackground() {
		try {
			background = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/backgrounds/Paused.png"));
		} catch (IOException e) {
			System.out.println("Failed to load main background");
			e.printStackTrace();
		}
	}

}
