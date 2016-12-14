package entities.weapons;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import entities.Killable;

public class Arrow {
	
	private boolean remove;
	private float speed = 3.5f;
	private float position;
	private float strength;
	
	private float width = 40;
	private float thickness = 20;
	private float height = 90;

	public Arrow(float position, float strength) {
		this.position = position;
		this.strength = strength;
	}
	public void update(Killable target) {
		position += speed;
		
		if (target != null && target.isAlive() && target.getPosition() < position && target.getPosition() + target.getSize() > position) {
			target.damage(strength);
			remove = true;
		}
		if (position > Display.getWidth()) {
			remove = true;
		}
	}
	
	public void draw() {
		GL11.glTexCoord2d(0, 1);
		GL11.glVertex2d(position, Display.getHeight() - height);

		GL11.glTexCoord2d(1, 1);
		GL11.glVertex2d(position + width, Display.getHeight() - height);

		GL11.glTexCoord2d(1, 0);
		GL11.glVertex2d(position + width, Display.getHeight() - thickness - height);

		GL11.glTexCoord2d(0, 0);
		GL11.glVertex2d(position, Display.getHeight() - thickness - height);
	}
	
	public boolean needsRemoved() {
		return remove;
	}
	
}
