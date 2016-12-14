package menuEntities;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;


public class UnitButton {
		
	float xPosition;
	float yPosition;
	float textOffset;
	float size;

	float texStart;
	float texStop;

	boolean active;
	boolean clicked;
	boolean activated;
	
	public UnitButton(float xPosition, float yPosition, float size, float texStart, float texStop) {
		this.xPosition = xPosition;
		this.yPosition = yPosition;
		this.size = size;
		this.texStart = texStart;
		this.texStop = texStop;

	}

	public boolean update() {
		active = false;
		activated = false;

		int mouseY = 600 - Mouse.getY();
		if (Mouse.getX() < xPosition + size && Mouse.getX() > xPosition &&
				mouseY < yPosition + size &&mouseY > yPosition) {
			active = true;
		}

		if (active && Mouse.isButtonDown(0)) {
			clicked = true;
		} else if (!active) {
			clicked = false;
		}
		
		if (active && clicked && !Mouse.isButtonDown(0)){
			activated = true;
			clicked = false;
		}
		return activated;
	}

	public void draw() {
		GL11.glTexCoord2d(texStart, 0);
		GL11.glVertex2d(xPosition, yPosition);

		GL11.glTexCoord2d(texStop, 0);
		GL11.glVertex2d(xPosition + size, yPosition);

		GL11.glTexCoord2d(texStop, 1);
		GL11.glVertex2d(xPosition + size, yPosition + size);

		GL11.glTexCoord2d(texStart, 1);
		GL11.glVertex2d(xPosition, yPosition + size);
	}
	
	
}
