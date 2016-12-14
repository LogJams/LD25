package entities.units;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import entities.*;

public class MagicBlob implements Animated, Movable{

	int position = 200;
	float speedMod = 1.3f;
	private final int HEIGHT = 300;
	private final int SIZE = 75;

	private int frameIndex = -9;
	private final int FRAME_BUFFER = -9;
	private int frame;

	private boolean dead = false;
	private boolean destroyed = false;

	public void update() {

	}
	
	public void reset() {
		dead = false;
		destroyed = false;
		position = 200;
	}
	@Override
	public void draw() {
		updateAnimation();

		if(!dead) {
		GL11.glTexCoord2d((1/14f) * frame, 1/2f);
		GL11.glVertex2d(position, Display.getHeight() - HEIGHT);

		GL11.glTexCoord2d((1/14f) * (frame + 1), 1/2f);
		GL11.glVertex2d(position + SIZE, Display.getHeight() - HEIGHT);

		GL11.glTexCoord2d((1/14f) * (frame + 1),0);
		GL11.glVertex2d(position + SIZE, Display.getHeight() - (SIZE * 2) - HEIGHT);

		GL11.glTexCoord2d((1/14f) * frame,0);
		GL11.glVertex2d(position, Display.getHeight() - (SIZE * 2) - HEIGHT);
		} else {
			GL11.glTexCoord2d((1/7f) * frame, 1);
			GL11.glVertex2d(position, Display.getHeight() - HEIGHT);

			GL11.glTexCoord2d((1/7f) * (frame + 1), 1);
			GL11.glVertex2d(position + (SIZE * 2), Display.getHeight() - HEIGHT);

			GL11.glTexCoord2d((1/7f) * (frame + 1),1/2f);
			GL11.glVertex2d(position + (SIZE * 2), Display.getHeight() - (SIZE * 2) - HEIGHT);

			GL11.glTexCoord2d((1/7f) * frame,1/2f);
			GL11.glVertex2d(position, Display.getHeight() - (SIZE * 2) - HEIGHT);
		}

	}

	@Override
	public void move() {
		if (Keyboard.isKeyDown(Keyboard.KEY_A) && !Keyboard.isKeyDown(Keyboard.KEY_D) && position > 50) {
			position -= (speed + speedMod);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_D) && !Keyboard.isKeyDown(Keyboard.KEY_A) && position < 950) {
			position += (speed + speedMod);
		}
	}

	@Override
	public void updateAnimation() {
		frameIndex++; 
		if (!dead) {
			if (frameIndex >= 0) {
				frame++;
				frameIndex = FRAME_BUFFER;
				if (frame > 14) {
					frame = 0;
				}
			} //end walk animation cycle


		} else {
			if (frameIndex >= 0) {
				frame++;
				frameIndex = FRAME_BUFFER;
				if (frame > 6) {
					destroyed = true;
				}
			}
		}
	}
	

	public float getPosition() {
		return position;
	}
	@Override
	public void moveScreen(float distance) {
		//does nothing
	}

	
	public void kill() {
		dead = true;
	}
	
	public boolean isDead() {
		return destroyed;
	}
	
}
