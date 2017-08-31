import java.util.Random;
import java.awt.Shape;

import javax.swing.JComponent;

public class stationary {

	private EZImage building; // make EZImage variable called building
	private EZSound soundEffect; // make EZSound variable called soundEffect

	private int x; // x position of building
	private int y; // y position of building
	private String fileName; // file name of picture
	private String soundFile; // sound file name for sound effect
	private boolean safe; // create boolean called safe
	// constructor for buildings

	public stationary(String filename, String soundfile, int posx, int posy) {

		// make x and y positions of buildings equal to inputed position
		x = posx;
		y = posy;

		// initialize fileName and soundFile to equal inputed String
		fileName = filename;
		soundFile = soundfile;

		// add building Image
		building = EZ.addImage(fileName, posx, posy);
		
		//add sound effect
		soundEffect = EZ.addSound(soundFile);
		
		// initialize safe to true for all buildings;
		safe = true;

	}

	// check if building is touching object
	public boolean isInside(int posx, int posy) {
		if (safe = false) {
			return false;
		} else {
			return building.isPointInElement(posx, posy);
		}
	}

	// makes safe = false
	public void crash() {
		safe = false;
		//play sound effect
		soundEffect.play();
	}

	// hides building
	public void hide() {
		building.hide();
		
		
		// moves building to -500,-500
		building.translateTo(-500, -500);
	}
}
