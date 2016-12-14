package entities;

public interface Killable {
	
	
	public void damage(float strength);
	
	public void heal(int amount);
	
	public boolean isAlive();
	
	public float getPosition();
	
	public void setTarget(Killable k);
	
	public float getSize();
}
