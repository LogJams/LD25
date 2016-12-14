package coreLogic;

import gameLogic.LogicCore;

import org.lwjgl.input.Keyboard;

import render.RENDer;

public class GameLogicCore {

	LogicCore logic;
	RENDer theEnd;
	
//	private int highScore;
	
	public GameLogicCore() {
		
		logic = new LogicCore();

	}

	public boolean update() {
		boolean changeState = false; //if the state should change to a menu


		logic.update();

		if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
			changeState = true;
		}

		return changeState;
	}
	
	public int getMusicType() {
		
		int musicType = 0;
		
		if (logic.getNumMinions() + logic.getNumHumans() > 30) {
			musicType = 2;
		}else if (logic.getNumMinions() + logic.getNumHumans() > 18) {
			musicType = 1;
		}
		
		return musicType;
	}
	
	public boolean updateEnd() {
		return theEnd.update();
	}
	
	public boolean isOver() {
		if (logic.isOver()) {
			theEnd = new RENDer(logic.getScores());
		}
		return logic.isOver();
	}
	
	public void reset() {
		logic.reset();
	}
}
