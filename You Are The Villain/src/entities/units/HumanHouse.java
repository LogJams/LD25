package entities.units;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

public class HumanHouse extends Building {

	private int frame = 0;
	private boolean burning;
	private final int yPosition = 255;
	int frameBuffer = -10;
	
	private static int width = 200;
	private static int height = 200;

	public HumanHouse(float position) {
		super(position, width, height);
	}

	public void update() {
		frameBuffer ++;
		if (burning && frameBuffer > 0) {
			frame ++;
			frameBuffer = -10;
			if (frame >= 6) { 
				frame = 1;
			}
		}

		draw();
		
	}

	public void draw() {


		if (frame > 3) {
			GL11.glTexCoord2d((1/4f) * frame, 1);
			GL11.glVertex2d(position, Display.getHeight() - yPosition);

			GL11.glTexCoord2d((1/4f) * (frame + 1), 1);
			GL11.glVertex2d(position + width, Display.getHeight() - yPosition);

			GL11.glTexCoord2d((1/4f) * (frame + 1), .5);
			GL11.glVertex2d(position + width, Display.getHeight() - yPosition - height);

			GL11.glTexCoord2d((1/4f) * frame, .5);
			GL11.glVertex2d(position, Display.getHeight() - yPosition - height);
		} else {
			GL11.glTexCoord2d((1/4f) * frame, .5);
			GL11.glVertex2d(position, Display.getHeight() - yPosition);

			GL11.glTexCoord2d((1/4f) * (frame + 1), .5);
			GL11.glVertex2d(position + width, Display.getHeight() - yPosition);

			GL11.glTexCoord2d((1/4f) * (frame + 1), 0);
			GL11.glVertex2d(position + width, Display.getHeight() - yPosition - height);

			GL11.glTexCoord2d((1/4f) * frame, 0);
			GL11.glVertex2d(position, Display.getHeight() - yPosition - height);
		}
	}
	
	public void setAblaze() {
		burning = true;
	}

}
