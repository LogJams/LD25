package coreLogic;

import org.lwjgl.LWJGLException;
import org.lwjgl.openal.AL;
import org.lwjgl.opengl.*;


public class Driver {
	
	private int screenWidth = 1200;
	private int screenHeight = 600;
	
	Controller game;
	
	private Driver() {
		
		try {
			Display.setDisplayMode(new DisplayMode(screenWidth, screenHeight));
			Display.setTitle("Killer Crusade "+ (char) 945 +" 0.51");
			Display.create();
			
		} catch (LWJGLException e){
			System.out.println("Error Loading LWJGL Display");
			e.printStackTrace();
		}
						
		/*
		 *
		 * initializations
		 * 
		 */
		initializeDisplaytype();
		initializeLogic();
		
		
		while (!Display.isCloseRequested()) {
			
			/*
			 * game updates
			 */
			
			updateDisplay();
			updateLogic();
			
			
			Display.update();
			Display.sync(60);
		} // end the running loop
		
		AL.destroy();
		Display.destroy();
		
	}
	
	private void initializeDisplaytype() {
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, Display.getWidth(), Display.getHeight(), 0, 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
	}
	
	private void initializeLogic() {
		game = new Controller();
	}
	
	private void updateDisplay() {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
	}

	private void updateLogic() {
		game.update();
	}
	
	public static void main(String[] args) {
		new Driver();
	}
}
