package entities.units;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

public class EvilBuilding extends Building {
	
	private int type;
	private int yPosition;
	private final int FINAL_POSITION = 250;

	public EvilBuilding(float position, int width, int height, int type) {
		super(position, width, height);
	}

	public void draw() {

		if (yPosition < FINAL_POSITION) {
			yPosition ++;
		}

		GL11.glTexCoord2d((1/3f) * type, 1);
		GL11.glVertex2d(position, Display.getHeight() - yPosition);

		GL11.glTexCoord2d((1/3f) * (type + 1), 1);
		GL11.glVertex2d(position + width, Display.getHeight() - yPosition);

		GL11.glTexCoord2d((1/3f) * (type + 1), .5);
		GL11.glVertex2d(position + width, Display.getHeight() - yPosition - height);

		GL11.glTexCoord2d((1/3f) * type, .5);
		GL11.glVertex2d(position, Display.getHeight() - yPosition - height);
	}

}
