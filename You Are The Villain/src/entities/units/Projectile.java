package entities.units;

public class Projectile {

	int position;
	int height;
	float size;
	float xSpeed;
	float ySpeed;
	
	boolean gravity;
	
	
	public Projectile(int position, int height, float xSpeed, float ySpeed) {
		this.position = position;
		this.height = height;
		this.xSpeed = xSpeed;
		this.ySpeed = ySpeed;
	}
	
	public void move() {
		position += xSpeed;
		if (gravity) {
			ySpeed -= 1;
			height += ySpeed;
		}
	}
}
