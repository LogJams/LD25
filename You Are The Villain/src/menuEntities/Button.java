package menuEntities;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;

import render.TextTool;

public class Button {

	TextTool toText;
	int type;

	String text;	
	float xPosition;
	float yPosition;
	float textOffset;
	float width;
	float height;

	float texStart;
	float texStop;

	Texture texture;

	boolean clicked;
	
	public Button() {};

	public Button(String text, float xPosition, float yPosition, float width, float height, Texture texture, float texStart, float texStop, float textOffset, int type) {
		this.text = text;
		this.xPosition = xPosition;
		this.yPosition = yPosition;
		this.width = width;
		this.height = height;
		this.texture = texture;
		this.texStart = texStart;
		this.texStop = texStop;
		this.textOffset = textOffset;
		this.type = type;

		toText = new TextTool();
	}
	public boolean update() {
		boolean active = false;
		boolean activated = false;

		int mouseY = 600 - Mouse.getY();
		if (Mouse.getX() < xPosition + width && Mouse.getX() > xPosition &&
				mouseY < yPosition + height &&mouseY > yPosition) {
			active = true;
		}

		if (active && Mouse.isButtonDown(0)) {
			clicked = true;
		} else if (!active) {
			clicked = false;
		}
		
		if (active && clicked && !Mouse.isButtonDown(0)){
			activated = true;
			clicked = false;
		}
		return activated;
	}

	public void draw() {
		texture.bind();
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2d(0, texStart);
		GL11.glVertex2d(xPosition, yPosition);

		GL11.glTexCoord2d(1, texStart);
		GL11.glVertex2d(xPosition + width, yPosition);

		GL11.glTexCoord2d(1, texStop);
		GL11.glVertex2d(xPosition + width, yPosition + height);

		GL11.glTexCoord2d(0, texStop);
		GL11.glVertex2d(xPosition, yPosition + height);
		GL11.glEnd();


		toText.renderText(type, xPosition + (width / 70f) + textOffset, yPosition + (height / 5f), text);
	}
}
