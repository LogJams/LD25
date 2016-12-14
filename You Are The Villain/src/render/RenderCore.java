package render;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import entities.units.*;
import entities.weapons.Arrow;

import menuEntities.UnitButton;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.*;
import org.newdawn.slick.util.*;



public class RenderCore {

	TextTool toText;
	ArrayList<Skeleton> minions;
	ArrayList<Human> humans = new ArrayList<Human>();
	ArrayList<EvilBuilding> eBuildings = new ArrayList<EvilBuilding>();
	ArrayList<HumanHouse> gBuildings = new ArrayList<HumanHouse>();
	ArrayList<Arrow> arrows;
	MagicBlob controller;

	private float distanceTravelled;
	private boolean drawTutorial = true;
	private Texture backgroundElements;
	private Texture skyTex;
	private Texture humanBuildings;
	private Texture evilBuildings;
	private Texture minionTex;
	private Texture humanTex;
	private Texture controllerTex;
	private Texture projectileTex;
	private UnitButton[] spawnButtons;

	private int houseBuffer = -337;

	private Random random = new Random();

	Texture swordIcon;
	Texture buttonTexture;

	Texture tutorial;

	int textureOffset = 1000;


	public RenderCore(ArrayList<Skeleton> minions, ArrayList<Human> humans, MagicBlob controller, UnitButton[] spawnButtons) {
		this.minions = minions;
		this.humans = humans;
		this.controller = controller;
		arrows = new ArrayList<Arrow>();
		toText = new TextTool();
		this.spawnButtons = spawnButtons;


		loadImages();
	}

	public void reset() {
		textureOffset = 1000;
		distanceTravelled = 0;
		drawTutorial = true;
	}

	public void update(int souls, int maxMinions, boolean scrolling, boolean newSpire) {

		if (newSpire) {
			int type = 3;
			if (distanceTravelled / 3000 < 3) {
				type = (int) distanceTravelled / 3000;
			}
			eBuildings.add(new EvilBuilding(950, 250, 350, type));


		}



		drawSky();


		drawBackground();

		if (drawTutorial) {
			drawTutorial();
		}

		drawHumans();
		drawMinions();

		if (distanceTravelled > 4) {
			drawTutorial = false;
		}


		drawcontroller();


		if (scrolling) {
			textureOffset ++;
			distanceTravelled += 0.005;
			houseBuffer++;
			for (int i = 0; i < eBuildings.size() ; i++) {
				eBuildings.get(i).move();
			}for (int i = 0; i < gBuildings.size() ; i++) {
				gBuildings.get(i).move();
			}

		} 

		if (textureOffset > 3000) {
			textureOffset = 1000;
		} else if (textureOffset < 1000) {
			textureOffset = 3000;
		}

		drawUI(souls, maxMinions);

		testUI();

		testUIButtons();


	}


	public void testUI() {

		swordIcon.bind();
		GL11.glBegin(GL11.GL_QUADS);
		for (int i = 0; i < spawnButtons.length; i++) {
			spawnButtons[i].draw();
		}
		GL11.glEnd();
	}

	public void testUIButtons() {
		buttonTexture.bind();
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2d(0, 0);
		GL11.glVertex2d(0, 0);

		GL11.glTexCoord2d(1, 0);
		GL11.glVertex2d(560, 0);

		GL11.glTexCoord2d(1, 1);
		GL11.glVertex2d(560,80);

		GL11.glTexCoord2d(0, 1);
		GL11.glVertex2d(0, 80);
		GL11.glEnd();
	}

	public void drawUI(int souls, int maxMinions) {
		toText.renderText(toText.CREEPY, 575, 0, "MINIONS: " + minions.size() + "/" + maxMinions);
		toText.renderText(toText.CREEPY, 575, 40, "SOULS: " + souls);
	}

	private void drawcontroller() {
		controllerTex.bind();
		GL11.glBegin(GL11.GL_QUADS);
		controller.draw();
		GL11.glEnd();

	}

	public void drawBackground() {
		backgroundElements.bind();
		GL11.glBegin(GL11.GL_QUADS);

		drawMountains();
		GL11.glEnd();
		drawBuildings();

		backgroundElements.bind();
		GL11.glBegin(GL11.GL_QUADS);
		drawHills();
		drawRoad();

		GL11.glEnd();
	}

	public void drawRoad() {
		for (int i = 8; i >= 0; i--) {
			GL11.glTexCoord2d(0, 3/4f);
			GL11.glVertex2d((1000 * i) - ((textureOffset * 2.5f) + 100), Display.getHeight() - 135);

			GL11.glTexCoord2d(1, 3/4f);
			GL11.glVertex2d((1000 * (i + 1)) - ((textureOffset * 2.5f) + 100), Display.getHeight() - 135);

			GL11.glTexCoord2d(1, 1);
			GL11.glVertex2d((1000 * (i + 1)) - ((textureOffset * 2.5f) + 100), Display.getHeight() + 50);

			GL11.glTexCoord2d(0, 1);
			GL11.glVertex2d((1000 * i) - ((textureOffset * 2.5f) + 100), Display.getHeight() + 50);
		}
	}

	public void drawMountains() {
		//draws mountains\\\
		for (int i = 0; i <= 4; i++) {
			GL11.glTexCoord2d(0, 0);
			GL11.glVertex2d((1000 * i) - (textureOffset * 1f), 75);

			GL11.glTexCoord2d(1, 0);
			GL11.glVertex2d((1000 * (i + 1)) - (textureOffset * 1f), 75);

			GL11.glTexCoord2d(1, 2/4f);
			GL11.glVertex2d((1000 * (i + 1)) - (textureOffset * 1f), 495);

			GL11.glTexCoord2d(0, 2/4f);
			GL11.glVertex2d((1000 * i) - (textureOffset * 1f), 495);
		}

	}

	public void drawHills() {
		for (int i = 5; i >= 0; i--) {
			GL11.glTexCoord2d(0, 2/4f);
			GL11.glVertex2d((1000 * i) - ((textureOffset * 1.5f) - 100), 265);

			GL11.glTexCoord2d(1, 2/4f);
			GL11.glVertex2d((1000 * (i + 1)) - ((textureOffset * 1.5f) - 100), 265);

			GL11.glTexCoord2d(1, 3/4f);
			GL11.glVertex2d((1000 * (i + 1)) - ((textureOffset * 1.5f) - 100), 600);

			GL11.glTexCoord2d(0, 3/4f);
			GL11.glVertex2d((1000 * i) - ((textureOffset * 1.5f) - 100), 600);
		}
	}

	public void drawSky() {

		skyTex.bind();


		GL11.glBegin(GL11.GL_QUADS);

		GL11.glTexCoord2d(0, 1/2f);
		GL11.glVertex2d(-300, -100);

		GL11.glTexCoord2d(1, 1/2f);
		GL11.glVertex2d(Display.getWidth() + 450, -100);

		GL11.glTexCoord2d(1, 1);
		GL11.glVertex2d(Display.getWidth() + 450, 300);

		GL11.glTexCoord2d(0, 1);
		GL11.glVertex2d(-300, 300);

		float opacity = 255;
		if (distanceTravelled > 1) {
			opacity = (50/(distanceTravelled)) - 1;
		}


		GL11.glColor4f(1, 1, 1, opacity);

		GL11.glTexCoord2d(0, 0);
		GL11.glVertex2d(-300, -100);

		GL11.glTexCoord2d(1, 0);
		GL11.glVertex2d(Display.getWidth() + 450, -100);

		GL11.glTexCoord2d(1, 1/2f);
		GL11.glVertex2d(Display.getWidth() + 450 , 300);

		GL11.glTexCoord2d(0, 1/2f);
		GL11.glVertex2d(-300, 300);
		GL11.glEnd();

		GL11.glColor4f(255, 255, 255, 255);
	}

	public void drawBuildings() {

		if (houseBuffer > 0) {
			houseBuffer = -337;
			if (random.nextFloat() < 0.268) {
				gBuildings.add(new HumanHouse(1650));
				if (random.nextFloat() < distanceTravelled / 150f) {
					gBuildings.get(gBuildings.size() - 1).setAblaze();
				}
			}
		}


		humanBuildings.bind();
		GL11.glBegin(GL11.GL_QUADS);
		for (int i = 0; i < gBuildings.size(); i++) {
			gBuildings.get(i).update();
		}
		GL11.glEnd();

		evilBuildings.bind();
		GL11.glBegin(GL11.GL_QUADS);
		for (int i = 0; i < eBuildings.size(); i++) {
			eBuildings.get(i).draw();
		}
		GL11.glEnd();


		for (int i = gBuildings.size() - 1; i >= 0 ; i--) {
			if (gBuildings.get(i).needsRemoved()) {
				gBuildings.remove(i);
			}
		} for (int i = eBuildings.size() - 1; i >= 0 ; i--) {
			if (eBuildings.get(i).needsRemoved()) {
				eBuildings.remove(i);
			}
		}

	}

	public void drawTutorial() {

		tutorial.bind();

		////////////////disappears if scrolling backward\\\\\\\\\\\\\\\\\\\\

		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2d(0, 0);
		GL11.glVertex2d(0 - ((textureOffset - 1000) * 1.5f), 0);

		GL11.glTexCoord2d(1, 0);
		GL11.glVertex2d(1200 - ((textureOffset - 1000) * 1.5f), 0);

		GL11.glTexCoord2d(1, 1);
		GL11.glVertex2d(1200 - ((textureOffset - 1000) * 1.5f), 600);

		GL11.glTexCoord2d(0, 1);
		GL11.glVertex2d(0 - ((textureOffset - 1000) * 1.5f), 600);
		GL11.glEnd();

	}


	public void drawMinions() {

		minionTex.bind();
		GL11.glBegin(GL11.GL_QUADS);
		for (int i = 0; i < minions.size(); i++) {
			minions.get(i).draw();
		}
		GL11.glEnd();

		drawArrows();

	}

	public void drawArrows() {
		projectileTex.bind();
		GL11.glBegin(GL11.GL_QUADS);
		for (int i = 0; i < minions.size(); i++) {
			if (minions.get(i) instanceof SkelArcher) {
				for (int j = 0; j < ((SkelArcher) minions.get(i)).getArrows().size(); j++) {
					((SkelArcher) minions.get(i)).getArrows().get(j).draw();
				}
			}
		}
		GL11.glEnd();

	}

	public void drawHumans() {


		humanTex.bind();
		GL11.glBegin(GL11.GL_QUADS);
		for (int i = 0; i < humans.size(); i++) {
			humans.get(i).draw();
		}
		GL11.glEnd();

	}


	private void loadImages() {
		try {
			backgroundElements = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/backgrounds/Backgrounds.png"));
			skyTex = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/backgrounds/Skies.png"));
			controllerTex = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/characters/Controller.png"));
			minionTex = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/characters/Skeleton.png"));
			humanTex = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/characters/Human.png"));
			humanBuildings = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/structures/Human.png"));
			evilBuildings = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/structures/Evil.png"));
			tutorial = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/backgrounds/Tutorial.png"));
			swordIcon = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/ui/Icons/SwordIcon.png"));
			buttonTexture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/ui/icons/Buttons.png"));
			projectileTex = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/projectiles/Arrow.png"));
		} catch (IOException e) {
			System.out.println("Error Loading Textures");
			e.printStackTrace();
		}
	}

}
