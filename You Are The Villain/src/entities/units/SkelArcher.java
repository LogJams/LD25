package entities.units;

import java.util.ArrayList;

import entities.weapons.Arrow;

public class SkelArcher extends Skeleton {
	
	private ArrayList<Arrow> arrows;

	public SkelArcher(float speedVariance, float position) {
		super(speedVariance, position);
		range = 500;
		NEEDY_FACTOR = 100;
		ATTACK_SPEED = -40;

		arrows = new ArrayList<Arrow>();
	}
	
	@Override
	public void update(float playerPosition) {
		super.update(playerPosition);
		
		for (int i = arrows.size() - 1; i >= 0; i--) {
			arrows.get(i).update(target);
			if (arrows.get(i).needsRemoved()) {
				arrows.remove(i);
				System.out.println("Removed!");
			}
		}
	}
	
	
	@Override
	public void attack() {
		arrows.add(new Arrow(xPosition, strength));
	}
	
	public ArrayList<Arrow> getArrows() {
		return arrows;
	}
}
