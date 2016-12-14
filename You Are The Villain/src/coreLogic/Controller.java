package coreLogic;

public class Controller {

	GameLogicCore game;
	MenuLogicCore menu;
	AudioLogic audio;

	State gameState = State.MENU;

	enum State {
		MENU, INGAME, GAMEOVER;
	}

	Controller() {

		game = new GameLogicCore();
		menu = new MenuLogicCore();
		audio = new AudioLogic();

	}

	public void update() {

		switch(gameState) {

		case MENU:

			if (menu.update()) {
				gameState = State.INGAME;
			}

			if (!menu.isCredits()) {
				audio.playIntroMusic();
			} else {
				audio.playCredits();
			}
			
			break;

		case INGAME:


			if (game.update()) {
				gameState = State.MENU;
			}

			if (game.isOver()) {
				gameState = State.GAMEOVER;
			}

			audio.playBattleMusic(game.getMusicType());

			break;

		case GAMEOVER:

			if (game.updateEnd()) {
				gameState = State.MENU;
				menu.reset();
				game.reset();
				audio.reset();
			}

			break;

		}//end game state

	}



}
