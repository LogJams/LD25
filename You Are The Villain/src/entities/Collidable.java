package entities;

public interface Collidable {
	
	
	void checkCollision(Collidable c);

	float getPosition();
	
	float getSize();

	int getState();
}
