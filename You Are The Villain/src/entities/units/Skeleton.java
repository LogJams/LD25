package entities.units;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import entities.*;

public class Skeleton implements Animated, Killable, Movable, Collidable {

	//drawing constants\\
	public int HEIGHT = 150;
	public int WIDTH = 100;
	//animation constants\\
	public final int STAND = 0; //walk frame 3 (row 1)
	public final int WALK = 1; // frames 0 to 4 (row 1)
	public final int ATTACK = 2; //frames 0 to 2, 1 and 2 are a single frame (row 2)
	public final int DIE = 3;
	public final int BIRTH = 4;
	public final int FRAME_BUFFER = -9;
	protected int ATTACK_SPEED = -9;
	public int NEEDY_FACTOR = 40;
	//movement information\\
	int state = STAND;
	float xPosition;
	boolean moveForward;
	float speedVariance;
	//animation information\\
	int frame = 0;
	int frameIndex = -9;
	boolean forward = true;
	//combat information\\
	float maxHealth = 4;
	float health = 0;
	float strength = 1;
	float range = 55;
	boolean alive = false;
	boolean remove = false;
	Killable target;


	public Skeleton(float speedVariance, float position) {
		xPosition = position;
		health = maxHealth;
		alive = true;
		state = BIRTH;
		this.speedVariance = speedVariance;
		NEEDY_FACTOR += speedVariance;
	}

	public void update(float playerPosition) {


		if (target != null && target.getPosition() > xPosition && target.getPosition() < xPosition + range && target.isAlive() && state != DIE) {
			state = ATTACK;
		} else if (state != DIE) {
			state = STAND;
		}
		
		if (playerPosition > xPosition + 60 + NEEDY_FACTOR && state != ATTACK && state != BIRTH && state != DIE) {
			state = WALK;
			moveForward = true;
		} else if (playerPosition < xPosition - NEEDY_FACTOR && state != ATTACK && state != BIRTH && state != DIE) {
			state = WALK;
			moveForward = false;
		} else if ((state != ATTACK && state != BIRTH && state != ATTACK)) {
			state = STAND;
		}
		

		move();

		if (health <= 0) {
			alive = false;
			state = DIE;
		}
		
		if (xPosition < 0 && state != DIE) {
			xPosition = 0;
		}
	}

	@Override
	public void move() {

		if (state == WALK && moveForward) {
			xPosition += (speed + speedVariance);
			updateAnimation();
		} else if (state == WALK) {
			xPosition -= (speed + speedVariance);
			updateAnimation();
		}

	}

	@Override
	public void damage(float strength) {
		health -= strength;
	}
	
	public void attack() {
		target.damage(strength);
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
				frameIndex = ATTACK_SPEED;
				if (frame > 1) {
					frame = 0;
					state = STAND;
					attack();
				}
			}
		} //end attack animation cycle

		else if (state == DIE) {
			if (frameIndex > 0) {
				frame++;
				frameIndex = FRAME_BUFFER;
				if (frame > 4) {
					remove = true;

				}
			}
		} //end die animation cycle
		else if (state == BIRTH) {
			if (frameIndex > 0) {
				frame++;
				frameIndex = FRAME_BUFFER;
				if (frame > 4) {
					state = STAND;
				}
			}
		}
	}


	@Override
	public void draw() {

		updateAnimation();

		if (state == WALK || state == STAND) {
			drawRowOne();
		}if (state == ATTACK) {
			drawAttack();
		}if (state == BIRTH) {
			drawBirth();
		}if (state == DIE) {
			drawDeath();
		}



	}

	//Row One of the texture map, contains walk and stand animations
	public void drawRowOne() {
		GL11.glTexCoord2d((1/5f) * frame, 1/6f);
		GL11.glVertex2d(xPosition, Display.getHeight() - 8);

		GL11.glTexCoord2d((1/5f) * (frame + 1), 1/6f);
		GL11.glVertex2d(xPosition + WIDTH, Display.getHeight() - 8);

		GL11.glTexCoord2d((1/5f) * (frame + 1),0);
		GL11.glVertex2d(xPosition + WIDTH, Display.getHeight() - HEIGHT - 8);

		GL11.glTexCoord2d((1/5f) * frame,0);
		GL11.glVertex2d(xPosition, Display.getHeight() - HEIGHT - 8);
	}

	public void drawAttack() {
		if (frame == 0) {

			GL11.glTexCoord2d((1/5f) * 0, 2/6f);
			GL11.glVertex2d(xPosition, Display.getHeight() - 8);

			GL11.glTexCoord2d((1/5f) * (frame + 1), 2/6f);
			GL11.glVertex2d(xPosition + WIDTH, Display.getHeight() - 8);

			GL11.glTexCoord2d((1/5f) * (frame + 1),1/6f);
			GL11.glVertex2d(xPosition + WIDTH, Display.getHeight() - HEIGHT - 8);

			GL11.glTexCoord2d((1/5f) * frame,1/6f);
			GL11.glVertex2d(xPosition, Display.getHeight() - HEIGHT - 8);

		} else { //this drawing takes 2 sprite sheets

			GL11.glTexCoord2d((1/5f) * 1, 2/6f);
			GL11.glVertex2d(xPosition- (0.5 * WIDTH), Display.getHeight() - 8);

			GL11.glTexCoord2d((1/5f) * (3), 2/6f);
			GL11.glVertex2d(xPosition + (1.5 * WIDTH), Display.getHeight() - 8);

			GL11.glTexCoord2d((1/5f) * (3),1/6f);
			GL11.glVertex2d(xPosition + (1.5 * WIDTH), Display.getHeight() - HEIGHT - 8);

			GL11.glTexCoord2d((1/5f) * 1, 1/6f);
			GL11.glVertex2d(xPosition - (0.5 * WIDTH), Display.getHeight() - HEIGHT - 8);

		}
	}

	public void drawDeath() {
		GL11.glTexCoord2d((1/5f) * frame, 4/6f);
		GL11.glVertex2d(xPosition, Display.getHeight() - 8);

		GL11.glTexCoord2d((1/5f) * (frame + 1), 4/6f);
		GL11.glVertex2d(xPosition + WIDTH, Display.getHeight() - 8);

		GL11.glTexCoord2d((1/5f) * (frame + 1), 3/6f);
		GL11.glVertex2d(xPosition + WIDTH, Display.getHeight() - HEIGHT - 8);

		GL11.glTexCoord2d((1/5f) * frame, 3/6f);
		GL11.glVertex2d(xPosition, Display.getHeight() - HEIGHT - 8);
	}

	public void drawBirth() {
		GL11.glTexCoord2d((1/5f) * frame, 3/6f);
		GL11.glVertex2d(xPosition, Display.getHeight() - 8);

		GL11.glTexCoord2d((1/5f) * (frame + 1), 3/6f);
		GL11.glVertex2d(xPosition + WIDTH, Display.getHeight() - 8);

		GL11.glTexCoord2d((1/5f) * (frame + 1), 2/6f);
		GL11.glVertex2d(xPosition + WIDTH, Display.getHeight() - HEIGHT - 8);

		GL11.glTexCoord2d((1/5f) * frame, 2/6f);
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
		if (state != BIRTH && state != DIE) {
			target = k;
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

	public void drawBoxTest() {
		GL11.glTexCoord2d(0, 1);
		GL11.glVertex2d(xPosition + (WIDTH / 3), Display.getHeight() - 8);

		GL11.glTexCoord2d(1, 1);
		GL11.glVertex2d(xPosition + (WIDTH * 2 / 3), Display.getHeight() - 8);

		GL11.glTexCoord2d(1, 0);
		GL11.glVertex2d(xPosition + (WIDTH * 2 / 3), Display.getHeight() - HEIGHT - 8);

		GL11.glTexCoord2d(0, 0);
		GL11.glVertex2d(xPosition + (WIDTH / 3), Display.getHeight() - HEIGHT - 8);
	}


	public void scrollRight() {
		if (state == STAND) {
			state = WALK;
		} else {
			xPosition -= 1;
		}
	}

	public int getState() {
		return state;
	}

	public void setStrength(float f) {
		strength = f;
		
	}

	public void setHealth(float i) {
		maxHealth = i;
	}



}
