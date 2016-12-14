package gameLogic;

import java.util.ArrayList;
import java.util.Random;

import menuEntities.UnitButton;

import org.lwjgl.input.Keyboard;

import entities.units.*;

import render.*;

public class LogicCore {

	Random random = new Random();
	TextTool toText;

	int numSouls = 5; //the currency to spawn skeletons
	final int  INTIAL_POPULATION = 14;
	int maxMinions = INTIAL_POPULATION + 1;
	int numSpires;
	int enemyPool;
	int spawnBuffer;
	int enemiesKilled;
	int[] skills = {1, 4};
	float spawnBarrier = 350;
	float distanceTravelled;
	float furthestDistance;
	boolean lose = false;
	boolean[] spawn = {false, false}; //spawn warriors or archers
	boolean[] upgrade = {false, false, false, false}; //upgrades
	
	boolean scrolling;
	
	float furthestMinion;


	RenderCore renderer;
	ArrayList<Skeleton> minions = new ArrayList<Skeleton>();
	ArrayList<Human> humans = new ArrayList<Human>();
	MagicBlob player;
	UnitButton[] spawner;


	public LogicCore() {
		player = new MagicBlob();
		toText = new TextTool();
		spawner = new UnitButton[2];
		for (int i = 0; i < spawner.length; i++) {
			spawner[i] = new UnitButton(20, 200 + (80 * i), 50, 0.5f * i, 0.5f * (i+1));
		}
		
		renderer = new RenderCore(minions, humans, player, spawner);
		minions.add(new Skeleton(random.nextFloat(), player.getPosition()));
		humans.add(new Human(random.nextFloat()));


		distanceTravelled = 0;


	}
	
	public void reset() {
		minions.clear();
		humans.clear();
		
		numSouls = 5; //the currency to spawn skeletons
		maxMinions = INTIAL_POPULATION + 1;
		numSpires = 0;
		enemiesKilled = 0;
		skills[0] = 1;
		skills[1] = 4;
		spawnBarrier = 350;
		distanceTravelled = 0;
		furthestDistance = 0;
		lose = false;
		scrolling = false;
		
		renderer.reset();
		player.reset();
		
		minions.add(new Skeleton(random.nextFloat(), player.getPosition()));
		humans.add(new Human(random.nextFloat()));
	}


	public void update() {

		player.move();

//		testFunctions();


		for (int i = (minions.size() - 1); i >= 0; i--) {
			minions.get(i).update(player.getPosition());
			if(scrolling) {
				minions.get(i).scrollRight();
			}
			if (minions.get(i).needsRemoved()) {
				minions.remove(i);
			}
		}

		for (int i = (humans.size() - 1); i >= 0; i--) {
			humans.get(i).update();
			if(scrolling) {
				humans.get(i).scrollRight();
			}			
			if (humans.get(i).needsRemoved()) {
				humans.remove(i);
				numSouls ++;
				enemiesKilled ++;
			}
		}

		setSkeletonTargets();
		setHumanTargets();
		//check collision//
		skeletonCollision();
		humanCollision();

		updateInputs();
		spawnButtons();
		increaseSkills();
		
		renderer.update(numSouls, maxMinions, scrolling, upgrade[3]);
		
		toText.renderText(toText.PLAIN, 0, 10, " Damage: " + skills[0] + "  Health: " + skills[1] + "    Max Minions: " + maxMinions + "    Spires: " + numSpires);
		toText.renderText(toText.PLAIN, 0, 47, "   Spell 1     Spell 2    Spell 3    Spell 4     Spell 5");

		
		if (scrolling) {
			distanceTravelled ++;
			spawnAlgorithm();
		}

		if(distanceTravelled > furthestDistance) {
			furthestDistance = distanceTravelled;
		}

		if (minions.size() == 0) {
			player.kill();
		}
		if (player.isDead()) {
			lose = true;
		}
	}
	
	private void spawnButtons() {
		if ((spawner[0].update() || spawn[0]) && numSouls >= 1) {
			numSouls --;
			minions.add(new Skeleton(random.nextFloat(), player.getPosition()));
			minions.get(minions.size() - 1).setStrength(skills[0]);
			minions.get(minions.size() - 1).setHealth(skills[1]);
		}
		if ((spawner[1].update() || spawn[1]) && numSouls >= 2) {
			numSouls -=2;
			minions.add(new SkelArcher(random.nextFloat(), player.getPosition()));
			minions.get(minions.size() - 1).setStrength(skills[0] * .8f);
			minions.get(minions.size() - 1).setHealth(skills[1] * .8f);
		}
		
	}
	
	public void increaseSkills() {
		int strengthCost = 2;
		int healthCost = 3;
		int popCost = 5;
		int spireCost = 6;
		
		if (upgrade[0] && numSouls >= skills[0] * strengthCost) {
			numSouls -= (skills[0] * strengthCost);
			skills[0] ++;
		} if (upgrade[1] && numSouls >= skills[1] * healthCost) {
			numSouls -= (skills[1] * healthCost);
			skills[1] ++;
		} if (upgrade[2] && numSouls >= (maxMinions - INTIAL_POPULATION) * popCost) { //14 is the inital pop cap
			numSouls -= ((maxMinions - INTIAL_POPULATION) * popCost);
			maxMinions ++;
		} if (upgrade[3] && numSouls >= numSpires * spireCost) {
			numSouls -= (numSpires * spireCost);
			numSpires ++;
		} else {
			upgrade[3] = false;
		}
	}


	public void updateInputs() {
		for (int i = 0; i < spawn.length; i ++) {
			spawn[i] = false;
			upgrade[i] = false;
			upgrade[i + 2] = false;
		}
		while (Keyboard.next()) {
			if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
				scrolling = !scrolling;
			} if (Keyboard.isKeyDown(Keyboard.KEY_Q)) {
				spawn[0] = true;
			} if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
				spawn[1] = true;
			}if (Keyboard.isKeyDown(Keyboard.KEY_1)) {
				upgrade[0] = true;
			}if (Keyboard.isKeyDown(Keyboard.KEY_2)) {
				upgrade[1] = true;
			}if (Keyboard.isKeyDown(Keyboard.KEY_3)) {
				upgrade[2] = true;
			}if (Keyboard.isKeyDown(Keyboard.KEY_4)) {
				upgrade[3] = true;
			}
		}
		
	}

	public void setSkeletonTargets() {
		int closest = -1;
		float smallestDistance = 500;
		for (int i = 0; i < minions.size() ; i++) {
			for (int j = 0 ; j < humans.size(); j++) {
				if (Math.abs(humans.get(j).getPosition() - minions.get(i).getPosition()) < smallestDistance && humans.get(j).getState() != humans.get(j).DIE) {
					smallestDistance  = humans.get(j).getPosition() - minions.get(i).getPosition();
					closest = j;
				}
			}
			if (closest >= 0) {
				minions.get(i).setTarget(humans.get(closest));
			}
		}
	}

	public void setHumanTargets() {

		int nearestIndex = -1; //purposely invalid value
		float nearest = -999999999; //purposely invalid value
		for (int i = 0; i < minions.size(); i++) {
			if (minions.get(i).getPosition() > nearest && minions.get(i).getState() != minions.get(i).DIE) {
				nearest = minions.get(i).getPosition();
				nearestIndex = i;
			}
		}
		furthestMinion = nearest;
		if (nearestIndex >= 0) {
			for (int i = 0; i < humans.size(); i++) {
				humans.get(i).setTarget(minions.get(nearestIndex));
			}
		}
		
	}

	public void skeletonCollision() {
		if (minions.size() > 0) {
			for (int i = 0; i < minions.size() ; i++) {
				for (int j = 0 ; j < minions.size(); j++) {
					if (i != j) {
						minions.get(i).checkCollision(minions.get(j));
					}
				}
			}
		}
	}

	public void humanCollision() {
		for (int i = 0; i < humans.size() ; i++) {
			for (int j = 0 ; j < humans.size(); j++) {
				if (i != j) {
					humans.get(i).checkCollision(humans.get(j));
				}
			}
		}
	}

	public void spawnAlgorithm() {

		float dificulty = furthestDistance * .001f;
		
		
		
		spawnBuffer ++;
		
		if (spawnBuffer > 0) {
			spawnBuffer = -60;// + (int) dificulty;
			for (int i = 0; i < dificulty ; i+= 2){ 
				humans.add(new Human(0));
				humans.get(humans.size() - 1).addStrength(dificulty * .1);
			}
		}
		
	}

	public boolean isOver() {
		return lose;
	}

	public int getNumMinions() {
		return minions.size();
	}
	

	public int getNumHumans() {
		return humans.size();
	}
	
	public float[] getScores() {
		float[] scores = {furthestDistance, enemiesKilled, numSouls};
		return scores;
	}
}
