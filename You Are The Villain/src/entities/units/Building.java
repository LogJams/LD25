package entities.units;

import entities.Movable;

public abstract class Building implements Movable {
	
	protected float position;
	protected int width;
	protected int height;
	protected boolean remove = false;
	
	public Building(float position, int width, int height) {
		this.position = position;
		this.width = width;
		this.height = height;
	}

	@Override
	public void move() {
		position -= 1.25;
		if (position < -width) {
			remove = true;
		}
	}

	@Override
	public void moveScreen(float distance) {
		//does nothing
	}

	@Override
	public float getPosition() {
		return position;
	}
	
	public abstract void draw();
	
	public boolean needsRemoved() {
		return remove;
	}

}
