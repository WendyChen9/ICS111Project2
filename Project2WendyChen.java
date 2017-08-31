
//Wendy Chen ICS111
import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Project2WendyChen {

	public static void main(String[] args) throws java.io.IOException {
		// TODO Auto-generated method stub

		EZ.initialize(1800, 950);// Set up EZ Screen of 1500x850 pixels

		// set background color as gray
		EZ.setBackgroundColor(new Color(200, 200, 200));

		// Add text "Score:" in black
		EZText scoreText = EZ.addText(80, 50, "Score:0", new Color(0, 0, 0), 25);

		// Add fireworks Image to center of the screen
		EZImage fireworks = EZ.addImage("fireworks.png", EZ.getWindowWidth() / 2, EZ.getWindowHeight() / 2);

		// hide fireworks picture
		fireworks.hide();

		// Add text "GET REKT" at middle of screen
		EZText victory = EZ.addText("LOUDNOISEBLACK.ttf", EZ.getWindowWidth() / 2, EZ.getWindowHeight() / 2, "VICTORY",
				Color.black, 300);
		// hide victory text
		victory.hide();

		// add background music
		EZSound music = EZ.addSound("Chocobo_music.wav");

		// play background music
		music.play();

		// add victory music
		EZSound victoryMusic = EZ.addSound("Chrono_Cross_OST_-_Victory.wav");

		// set up the buffered reader to read from the text file}
		Scanner fileScanner = new Scanner(new FileReader("project2.txt"));

		// create String command and initialize it the first word on the txt
		// file
		String command = fileScanner.next();

		// create arrays for bx,by (x and y positions of the buildings)
		int[] bx = new int[20];
		int[] by = new int[20];

		// create arrays for mx and my (x and y positions of monsters)
		int[] mx = new int[20];
		int[] my = new int[20];

		// create variable of type int called score and initialize it 0
		int score = 0;

		// create string arrays for file name and sound files of monsters
		String[] fileNameM = new String[20];
		String[] soundFileM = new String[20];

		// create string arrays for file name and sound files of buildings
		String[] fileNameB = new String[20];
		String[] soundFileB = new String[20];

		// while filescanner has next string
		while (fileScanner.hasNext()) {
			// switch function
			switch (command) {

			// if first file word is objects...
			case "objects":
				// Read 20 lines of the text file and store in the building
				// arrays
				for (int i = 0; i < 20; i++) {
					fileNameB[i] = fileScanner.next();
					soundFileB[i] = fileScanner.next();
					bx[i] = fileScanner.nextInt();
					by[i] = fileScanner.nextInt();

				}

				// Read the next 20 lines of the text file and store in the
				// monster
				// array
				for (int i = 0; i < 20; i++) {
					fileNameM[i] = fileScanner.next();
					soundFileM[i] = fileScanner.next();
					mx[i] = fileScanner.nextInt();
					my[i] = fileScanner.nextInt();
				}
				break;
			}
		}
		// an array to store stationary objects i.e. buildings
		stationary buildings[] = new stationary[20];

		// and array to store moving objects i.e monsters
		moving monsters[] = new moving[20];

		// sets positions of buildings and places them there
		for (int i = 0; i < 20; i++) {
			buildings[i] = new stationary(fileNameB[i], soundFileB[i], bx[i], by[i]);

		}

		// sets positions of monsters and places them there
		for (int i = 0; i < 20; i++) {
			monsters[i] = new moving(fileNameM[i], soundFileM[i], mx[i], my[i], 1800, 900);

		}

		// Set up car picture and assign it a variable car
		EZImage car = EZ.addImage("car.png", 1650, 50);

		while (true) {
			// makes the monsters start moving if their x position is not -500
			for (int i = 0; i < 20; i++) {
				if (monsters[i].getX() != -500) {
					monsters[i].run();
				}
			}
			// puts score text in front, so objects will be behind it
			scoreText.pullToFront();

			// create variable of type int named carX and set it to the X
			// coordinate of the center of the car
			int carX = car.getXCenter();

			// create type int variable called carY and set it to Y coordinate
			// of the center of the car
			int carY = car.getYCenter();

			// KEYS CONTROLS
			// if w key is down move car forward
			if (EZInteraction.isKeyDown('w')) {
				car.moveForward(-5);
			}
			// if a key is down move car counterclockwise
			else if (EZInteraction.isKeyDown('a')) {
				car.turnLeft(5);
			}
			// if d key is down turn car clockwise
			else if (EZInteraction.isKeyDown('d')) {
				car.turnRight(5);
			}

			// if s key is down move car backwards
			else if (EZInteraction.isKeyDown('s')) {
				car.moveForward(5);
			}

			for (int i = 0; i < 20; i++) {
				// if the car touches any of the monsters
				if (monsters[i].isInside(carX, carY)) {
					// move monster outside of screen (*Note:Without this line
					// of code, the score increases by a random amount of
					// points)
					monsters[i].setPosition(-500, -500);

					// kill the monster
					monsters[i].kill();

					// add 1 to the score
					score++;

					// change scoreText to show new score
					scoreText.setMsg("Score:" + score);

				}
			}
			for (int i = 0; i < 5; i++) {
				// if car touches any of the buildings
				if (buildings[i].isInside(carX, carY)) {
					// increase score
					buildings[i].crash();
					score--;
					// change score text to show new score
					scoreText.setMsg("Score:" + score);

					// move car back to start position
					car.translateTo(1650, 50);
				}
			}
			// if car touches any of the cats
			for (int i = 6; i < 20; i++) {
				if (buildings[i].isInside(carX, carY)) {

					// hide the cats
					buildings[i].hide();

					// plays the cat sounds
					buildings[i].crash();

					// decrease the score by 1
					score--;

					// change score text to show new score
					scoreText.setMsg("Score:" + score);
				}
			}

			// if all the monsters' x position is at -500
			if ((monsters[0].getX() == -500) && (monsters[1].getX() == -500) && (monsters[2].getX() == -500)
					&& (monsters[3].getX() == -500) && (monsters[4].getX() == -500) && (monsters[5].getX() == -500)
					&& (monsters[6].getX() == -500) && (monsters[7].getX() == -500) && (monsters[8].getX() == -500)
					&& (monsters[9].getX() == -500) && (monsters[10].getX() == -500) && (monsters[11].getX() == -500)
					&& (monsters[12].getX() == -500) && (monsters[13].getX() == -500) && (monsters[14].getX() == -500)
					&& (monsters[15].getX() == -500) && (monsters[16].getX() == -500) && (monsters[17].getX() == -500)
					&& (monsters[18].getX() == -500) && (monsters[19].getX() == -500)) {

				// stop background music
				music.stop();

				// puts fireworks picture at front
				fireworks.pullToFront();

				// puts victory text at front
				victory.pullToFront();

				// show victory text
				victory.show();

				// show fireworks
				fireworks.show();

				// play victory music
				victoryMusic.play();

				// pauses the screen
				EZ.pause(100000);
			}
			//if score is below 0
			if (score < 0) {
				
				//show loser text in middle of screen
				EZText loser = EZ.addText("LOUDNOISEBLACK.ttf", EZ.getWindowWidth() / 2, EZ.getWindowHeight() / 2, "LOSER", Color.black, 300);
				
				//stop music
				music.stop();
				
				//add losing sound
				EZSound lose = EZ.addSound("dead.wav");
				
				//play losing sound
				lose.play();
				
				//pause screen for 3000 miliseconds
				EZ.pause(3000);
				
				//set car back to start point
				car.translateTo(1650, 50);
				
				//initialize score to 0
				score = 0;
				
				//update score board
				scoreText.setMsg("Score:"+score);
				
				//hide loser text
				loser.hide();
				
				//play background music
				music.play();
			}
			// refreshes the screen
			EZ.refreshScreen();
		}

	}
}
