package ui.menu;

import java.io.IOException;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import render.TextTool;

import menuEntities.Button;

public class MenuRender {
	
	TextTool toText;
	
	Button a;
	Button b;
	Button c;
	
	Texture background;

	public MenuRender(Button a, Button b, Button c) {
		this.a = a;
		this.b = b;
		this.c = c;
		toText = new TextTool();
		
		loadBackground();
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
		
		
		a.draw();
		b.draw();
		c.draw();
		
		drawTitle();
	}
	
	public void drawTitle() {
		toText.renderText(toText.TITLE, 300, 20, "Killer Crusade");
	}
	
	public void loadBackground() {
		try {
			background = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/backgrounds/MainBG.png"));
		} catch (IOException e) {
			System.out.println("Failed to load main background");
			e.printStackTrace();
		}

	}
}
