import java.util.Random;
import java.io.FileReader;

public class moving {

	private EZImage monsters; // Create EZImage called monsters
	private EZSound soundEffect; // create EZSound called soundEffect

	private int x, y; // x and y position of monsters
	private int destx, desty; // destination of the monsters
	private int rangex, rangey; // range of monsters motions
	private boolean alive = true; // if monster is still alive
	private String fileName; // file names of each monster
	private String soundFile; // sound file of each monster

	// constructor for moving collectible objects
	public moving(String filename, String soundfile, int posx, int posy, int rx, int ry) {

		// make the the x and y position of monsters equal to inputed numbers
		// for the moving objects, posx and posy
		x = posx;
		y = posy;

		// assign the monster pictures to each object
		fileName = filename;

		// assign sound files to each monster
		soundFile = soundfile;

		// make the range of motion for the monsters equal to the inputed
		// values, rx and ry
		rangex = rx;
		rangey = ry;

		// sets random direction for monsters
		setRandomDirection();

		// adds image and set their position
		monsters = EZ.addImage(fileName, posx, posy);

		soundEffect = EZ.addSound(soundFile);

		// sets where the monster pic is placed
		setMonsterPic(x, y);

		// makes the monster alive when object is first created
		alive = true;
	}

	// set destination that monster is headed
	public void setDestination(int posx, int posy) {

		destx = posx;
		desty = posy;
	}

	// sets position of monsters
	public void setPosition(int posx, int posy) {
		x = posx;
		y = posy;
		setMonsterPic(x, y);
	}

	// set where the monster pic is
	private void setMonsterPic(int posx, int posy) {

		// move monster pic to wherever you inputed
		monsters.translateTo(posx, posy);

	}

	// setting random direction
	public void setRandomDirection() {
		// make a random number generator
		Random randomGenerator = new Random();

		// get rand number from 0 to rangex and use that as dest of monster in x
		int ranx = randomGenerator.nextInt(rangex);

		// do the same for destination of Y
		int rany = randomGenerator.nextInt(rangey);

		// Sets the point where monster is going
		setDestination(ranx, rany);
	}

	// make monster move up
	public void moveUp(int move) {
		y = y - move;

		// set monster pic at new location
		setMonsterPic(x, y);
	}

	// make monster move down
	public void moveDown(int move) {
		y = y + move;

		// set monster pic at new location
		setMonsterPic(x, y);
	}

	// make monster move right
	public void moveRight(int move) {
		x = x + move;

		// set monster pic at new location
		setMonsterPic(x, y);
	}

	public void moveLeft(int move) {
		x = x - move;

		// set monster pic at new location
		setMonsterPic(x, y);
	}

	// make monsters start moving if they are alive
	public void run() {
		if (alive = true) {
			// if the monster is to the right of its destination, move left
			if (x > destx) {
				moveLeft(1);

			}

			// if the monster is to the left of its destination, move right
			if (x < destx) {
				moveRight(1);
			}

			// if monster is below its destination, move up
			if (y > desty) {
				moveUp(1);

			}

			// if monster is above its destination, move down
			if (y < desty) {
				moveDown(1);

			}

			// if monster reaches its destination, give it a new destination
			if ((x == destx) && (y == desty)) {
				setRandomDirection();
			}
		}

	}

	// check if monster is touching posx, posy
	public boolean isInside(int posx, int posy) {

		// returns false if monster is dead
		if (alive == false) {
			return false;

		} else {

			// returns true if monster is touching posx posy
			return monsters.isPointInElement(posx, posy);

		}

	}

	public int getX() {
		return monsters.getXCenter();
	}

	// kill the monster
	public void kill() {
		// set alive to false
		alive = false;
		monsters.translateTo(-500, -500);
		// hide the monster picture
		// monsters.hide();

		// play the soundEffect
		soundEffect.play();

	}

}
