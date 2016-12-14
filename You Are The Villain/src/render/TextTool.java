package render;


import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;

public class TextTool {
	
	public int HAPPY = 0;
	public int CREEPY = 1;
	public int PLAIN = 2;
	public int TITLE = 3;
	
	private UnicodeFont cinnamon;
	private UnicodeFont benegraphic;
	private UnicodeFont grantham;
	private UnicodeFont title;

	@SuppressWarnings("unchecked")
	public TextTool() {
		
		try {
			cinnamon = new UnicodeFont("res/fonts/Cinnamon.ttf", 70, false, false);
			benegraphic = new UnicodeFont("res/fonts/Benegraphic.ttf", 40, false, false);
			grantham = new UnicodeFont("res/fonts/Grantham.ttf", 20, false, false);
			title = new UnicodeFont("res/fonts/Benegraphic.ttf", 150, true, false);
		} catch (SlickException e) {
			System.out.println("Failed to load fonts");
			e.printStackTrace();
		}
		
		cinnamon.addNeheGlyphs();
		cinnamon.getEffects().add(new ColorEffect(java.awt.Color.BLACK));
		benegraphic.addNeheGlyphs();
		benegraphic.getEffects().add(new ColorEffect(java.awt.Color.RED));
		grantham.addNeheGlyphs();
		grantham.getEffects().add(new ColorEffect(java.awt.Color.WHITE));
		title.addNeheGlyphs();
		title.getEffects().add(new ColorEffect(java.awt.Color.BLACK));
		
		try {
			cinnamon.loadGlyphs();
			benegraphic.loadGlyphs();
			grantham.loadGlyphs();
			title.loadGlyphs();
		} catch (SlickException e) {
			System.out.println("Failed to initialize fonts");
			e.printStackTrace();
		}
		
	}
	
	
	public void renderText(int type, float x, float y, String text) {
		if (type == HAPPY) {
			cinnamon.drawString(x, y, text);
		} else if (type == CREEPY) {
			benegraphic.drawString(x, y, text);
		} else if (type == PLAIN) {
			grantham.drawString(x, y, text);
		}
		else if (type == TITLE) {
			title.drawString(x, y, text);
		}
		else {
			System.err.println("Font not recognized");
		}
	}
}
