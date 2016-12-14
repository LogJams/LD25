package entities;

public interface Drawable {
	
	final int FOOTMAN = 0;
	//final int ARCHER = 2; unimplemented
	//final int SPEARMAN = 3; unimplemented
	
	int skeletonType = FOOTMAN;
	
	public void draw();
}
