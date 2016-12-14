package entities;

public interface Movable {
	
	float speed = 3;

		
	public void move();
	
	public void moveScreen(float distance);
	
	public float getPosition();

}
