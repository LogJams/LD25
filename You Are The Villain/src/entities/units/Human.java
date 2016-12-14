package entities.units;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import entities.*;

public class Human implements Animated, Killable, Movable, Collidable {

	//drawing constants\\
	public int HEIGHT = 150;
	public int WIDTH = 100;
	//animation constants\\
	public final int STAND = 0; //walk frame 3 (row 1)
	public final int WALK = 1; // frames 0 to 4 (row 1)
	public final int ATTACK = 2; //frames 0 to 2, 1 and 2 are a single frame (row 2)
	public final int DIE = 3;
	public final int FRAME_BUFFER = -9;
	//movement information\\
	int state = STAND;
	boolean moveForward;
	float xPosition;
	float speedVariance;
	//animation information\\
	int frame = 0;
	int frameIndex = -9;
	boolean forward = true;
	//combat information\\
	float maxHealth = 3;
	float health = 0;
	float strength = 1;
	float range = 50;
	boolean alive = false;
	boolean remove = false;
	Killable target;


	public Human(float speedVariance) {
		xPosition = Display.getWidth() - WIDTH;
		health = maxHealth;
		alive = true;
		state = WALK;
		this.speedVariance = speedVariance;
	}

	public void update() {

		if (target != null && target.getPosition() > xPosition - range && target.getPosition() < xPosition && target.isAlive()) {
			state = ATTACK;
		} else {
			state = WALK;
			move();
		}

		if (health <= 0) {
			alive = false;
			state = DIE;
		}
	}

	@Override
	public void move() {
		

		if (target != null && target.isAlive() && target.getPosition() > xPosition  && state == WALK) {		
			moveForward = false;
			state = WALK;
			updateAnimation();
		}else if(target != null && target.isAlive() && state == WALK && target.getPosition() < xPosition - range) {
			moveForward = true;
			state = WALK;
			updateAnimation();
		} else {
			state = STAND;
		}
		
		if (state == WALK && moveForward) {
			xPosition -= (speed + speedVariance);

		} else if (state == WALK) {
			xPosition += (speed + speedVariance);
		}

	}

	@Override
	public void damage(float strength) {
		health -= strength;
	}

	@Override
	public void updateAnimation() {
		frameIndex++;
		if (state == STAND) {
			frame = 2;
			frameIndex = FRAME_BUFFER;
		} //end stand animation cycle 
		else if (state == WALK) {

			if (frameIndex >= 0 && forward) {
				frame++;
				frameIndex = FRAME_BUFFER;
				if (frame > 4) {
					frame = 3;
					forward = false;
				}
			}if (frameIndex >= 0 && !forward) {
				frame--;
				frameIndex = FRAME_BUFFER;
				if (frame < 0) {
					frame = 1;
					forward = true;
				}
			}
		} //end walk animation cycle
		else if (state == ATTACK) {
			if (frameIndex >= 0 ) {
				frame++;
				frameIndex = FRAME_BUFFER;
				if (frame > 1) {
					frame = 0;
					state = STAND;
					target.damage(strength);
				}
			}
		} //end attack animation cycle

		else if (state == DIE) {

			if (frameIndex >= 0) {
				frame++;
				frameIndex = FRAME_BUFFER + 2;
				if (frame > 4) {
					frame = 4;
					remove = true;
				}
			}
		} //end die animation cycle
	}


	@Override
	public void draw() {

		updateAnimation();

		if (state == WALK || state == STAND) {
			drawRowOne();
		}if (state == ATTACK) {
			drawAttack();
		}if (state == DIE) {
			drawDeath();
		}

		if (xPosition < -WIDTH) {
			state = DIE;
		} 



	}

	//Row One of the texture map, contains walk and stand animations
	public void drawRowOne() {
		GL11.glTexCoord2d((1/5f) * (frame + 1), 1/3f);
		GL11.glVertex2d(xPosition, Display.getHeight() - 8);

		GL11.glTexCoord2d((1/5f) * frame, 1/3f);
		GL11.glVertex2d(xPosition + WIDTH, Display.getHeight() - 8);

		GL11.glTexCoord2d((1/5f) * frame,0);
		GL11.glVertex2d(xPosition + WIDTH, Display.getHeight() - HEIGHT - 8);

		GL11.glTexCoord2d((1/5f) * (frame + 1),0);
		GL11.glVertex2d(xPosition, Display.getHeight() - HEIGHT - 8);
	}

	public void drawAttack() {
		if (frame == 0) {

			GL11.glTexCoord2d((1/5f), 2/3f);
			GL11.glVertex2d(xPosition, Display.getHeight() - 8);

			GL11.glTexCoord2d(0, 2/3f);
			GL11.glVertex2d(xPosition + WIDTH, Display.getHeight() - 8);

			GL11.glTexCoord2d(0, 1/3f);
			GL11.glVertex2d(xPosition + WIDTH, Display.getHeight() - HEIGHT - 8);

			GL11.glTexCoord2d((1/5f), 1/3f);
			GL11.glVertex2d(xPosition, Display.getHeight() - HEIGHT - 8);

		} else { //this drawing takes 2 sprite sheets

			GL11.glTexCoord2d((1/5f) * 3, 2/3f);
			GL11.glVertex2d(xPosition- (0.5 * WIDTH), Display.getHeight() - 8);

			GL11.glTexCoord2d((1/5f), 2/3f);
			GL11.glVertex2d(xPosition + (1.5 * WIDTH), Display.getHeight() - 8);

			GL11.glTexCoord2d((1/5f), 1/3f);
			GL11.glVertex2d(xPosition + (1.5 * WIDTH), Display.getHeight() - HEIGHT - 8);

			GL11.glTexCoord2d((1/5f) * 3, 1/3f);
			GL11.glVertex2d(xPosition - (0.5 * WIDTH), Display.getHeight() - HEIGHT - 8);

		}
	}

	public void drawDeath() {
		GL11.glTexCoord2d((1/5f) * (frame + 1), 1);
		GL11.glVertex2d(xPosition, Display.getHeight() - 8);

		GL11.glTexCoord2d((1/5f) * frame, 1);
		GL11.glVertex2d(xPosition + WIDTH, Display.getHeight() - 8);

		GL11.glTexCoord2d((1/5f) * frame, 2/3f);
		GL11.glVertex2d(xPosition + WIDTH, Display.getHeight() - HEIGHT - 8);

		GL11.glTexCoord2d((1/5f) * (frame + 1), 2/3f);
		GL11.glVertex2d(xPosition, Display.getHeight() - HEIGHT - 8);
	}



	@Override
	public boolean isAlive() {
		return alive;
	}

	public boolean needsRemoved() {
		return remove;
	}

	@Override
	public void moveScreen(float distance) {
		xPosition -= distance;
	}

	@Override
	public float getPosition() {
		return xPosition;
	}

	@Override
	public void setTarget(Killable k) {
		if (state != DIE) {
			target = k;
		} else {
			target = null;
		}
	}

	@Override
	public void heal(int amount) {
		if (health < maxHealth) {
			health += amount;
		}

	}

	@Override
	public float getSize() {
		return WIDTH;
	}

	@Override
	public void checkCollision(Collidable c) {
		if (c.getState() != DIE) {
			if (xPosition + (WIDTH / 3) < (c.getPosition() + (c.getSize() / 3)) && xPosition + (WIDTH * 2 / 3) > (c.getPosition() + (c.getSize() / 3))
					&& state!= ATTACK && state != DIE) {
				//I contain their left edge
				xPosition -= speed;

			} else if (xPosition + (WIDTH / 3) < (c.getPosition() + (c.getSize() * 2 / 3)) && xPosition + (WIDTH * 2 / 3) > (c.getPosition() + (c.getSize() * 2 / 3))
					&& state!= ATTACK && state != DIE) {
				//I contain their right edge
				xPosition += speed;

			} 
		}

	}

	public void scrollRight() {
		xPosition -= 1;
	}

	public int getState() {
		return state;
	}

	public void addStrength(double addition) {
		strength += addition;
	}

}
