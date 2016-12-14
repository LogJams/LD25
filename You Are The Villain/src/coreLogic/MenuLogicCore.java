package coreLogic;


import java.io.IOException;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import render.FXPlayer;
import render.TextTool;

import menuEntities.Button;
import ui.menu.Credits;
import ui.menu.MenuRender;
import ui.menu.PauseMenu;

enum STATE {
	MAIN, PAUSE, CREDITS;
}

public class MenuLogicCore {

	TextTool toText;

	FXPlayer fx;
	Credits creditsScreen;

	public MenuRender render;
	public PauseMenu pause;
	public Button story;
	public Button endless;
	public Button credits;
	public Button pauseButton;

	Texture buttonTex;

	STATE menuType = STATE.MAIN;


	public MenuLogicCore() {

		fx = new FXPlayer(0);
		creditsScreen = new Credits();
		toText = new TextTool();
		loadButton();
		endless = new Button("Endless Mode", 400, 200, 400, 100, buttonTex, 0, 0.5f, 0, toText.HAPPY);
		story = new Button("Story Mode", 400, 310, 400, 100, buttonTex, 0.5f, 1, 30, toText.HAPPY);
		credits = new Button("Credits", 400, 420, 400, 100, buttonTex, 0.5f, 1, 35, toText.HAPPY);
		pauseButton = new Button("Resume", 480, 400, 150, 75, buttonTex, 0.5f, 1, 55, toText.CREEPY);
		render = new MenuRender(story, endless, credits);
		pause = new PauseMenu(pauseButton);


	}
	
	public void reset() {
		menuType = STATE.MAIN;
	}




	public boolean update() {

		boolean changeState = false;

		switch(menuType) {

		case MAIN:

			
			changeState = renderMenu();

			if (changeState) {
				menuType = STATE.PAUSE;
				fx.playSound(0);

			}
			
			if (credits.update()) {
				menuType = STATE.CREDITS;
			}


			break;

		case PAUSE:

			if (renderPause()) {
				changeState = true;
			}

			break;


		case CREDITS:

			if (creditsScreen.update()) {
				menuType = STATE.MAIN;
			}
			
			break;
		}

		return changeState;

	}



	public boolean renderMenu() {
		boolean changeState = false; //if the state should change to in game


		if (endless.update()) {
			changeState = true;
		}

		render.update();

		return changeState;
	}

	public boolean renderPause() {

		pause.update();

		return pauseButton.update();
	}



	void loadButton() {
		try {
			buttonTex = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/ui/MainButtons.png"));
		} catch (IOException e) {
			System.out.println("Failed to load button textures");
			e.printStackTrace();
		}

	}
	
	public boolean isCredits() {
		boolean ret = false;
		
		switch (menuType) {
		
		case CREDITS:
			ret = true;
			
			break;
			
		default:
			break;
		}
		
		
		return ret;
	}




}
