package ui.menu;

import org.lwjgl.input.Keyboard;

import render.TextTool;

public class Credits {

	private TextTool text;
	
	private int scrollDistance = 0;
	
	private String animator = "Lead Animator: Will Atchinson";
	private String musicicans = "Music Department: Liam Connoly and Martin Bridge";
	private String programmer = "Programming: Logan Beaver";
	private String cont = "Press Space to Return to the Menu";
	
	public Credits() {
		text = new TextTool();
	}
	
	public boolean update() {
		boolean finished = false;
		
		scrollDistance ++;
		
		text.renderText(text.CREEPY, 390, 650 - scrollDistance, animator);
		text.renderText(text.CREEPY, 390, 800 - scrollDistance, musicicans);
		text.renderText(text.CREEPY, 390, 950 - scrollDistance, programmer);
		
		if (scrollDistance > 700) {
			text.renderText(text.CREEPY, 390, 400, cont);
		}
		
		if (Keyboard.isKeyDown(Keyboard.KEY_SPACE) && scrollDistance > 700) {
			finished = true;
		}
		
		return finished;
	}
}
