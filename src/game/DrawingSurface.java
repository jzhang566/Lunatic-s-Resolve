package game;
import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PImage;

/**
 * DrawingSurface represents a surface that can draw the game
 * @author ayu663
 */
public class DrawingSurface extends PApplet {
	private ArrayList<Integer> keys;
	private Game game1, game2;
	private boolean startMenu;
	private boolean pauseMenu, cutscene;
	private int newWidth, newHeight;
	private int whoWon;
	private boolean gameDone, loreDone;
	private int cutsceneTicks, cst2;
	private PImage[] cutscenes;
	private int cutsceneSkipCD;
	private int maxStage;
	private Sounds s;
	
	/**
	 * Constructs an instance of DrawingSurface
	 * @param width1 Both Game's width
	 * @param height1 Each Game's height
	 */
	public DrawingSurface(int width1, int height1) {
		this.newWidth = width1;
		this.newHeight = height1;
		keys = new ArrayList<Integer>();
		startMenu = true;
		pauseMenu = false;
		gameDone = false;
		loreDone = false;
		cutsceneTicks = 0;
		cutsceneSkipCD = 0;
		cst2 = 0;
		cutscenes = new PImage[7];
	}
	
	/**
	 * Sets up a DrawingSurface by setting up the Game
	 */
	public void setup() {
		textFont(createFont("fonts/redalert1.ttf",30));
		//System.out.println(newWidth + " " + newHeight);
		game1 = new Game(1, newWidth, newHeight);
		game2 = new Game(2, newWidth, newHeight);
		game1.setup(this);
		game2.setup(this);
		maxStage = game1.getStageNum();
		if (game2.getStageNum() > maxStage) maxStage = game2.getStageNum();
		//bad design
		game2.setOtherPlayer(game1.getCurPlayer());
		game1.setOtherPlayer(game2.getCurPlayer());
		cutscenes[0] = loadImage("sprites/start1.png");
		cutscenes[1] = loadImage("sprites/start2.png");
		cutscenes[2] = loadImage("sprites/start3.png");
		cutscenes[3] = loadImage("sprites/start4.png");
		cutscenes[4] = loadImage("sprites/end1-1.png");
		cutscenes[5] = loadImage("sprites/end1-2.png");
		cutscenes[6] = loadImage("sprites/end2.png");
		
		for (int i = 0; i < cutscenes.length; i++) {
			cutscenes[i].resize(width, height);
		}

		s = new Sounds(0);
	}
	
	/**
	 * Draws the games
	 */
	public void draw() {
		game1.giveInputs(this);
		game2.giveInputs(this);
		
		if (isPressed(27)) {
			pauseMenu = true;
		}
		if (pauseMenu) {
			background(90);
			textSize(40);
			text("Paused", width/4,height/4,width/2,height/2);
			textSize(30);
			text("Press ENTER to start game",width/4,height/3,width/2,height/2*3);
			if (isPressed(10)) {
				pauseMenu = false;
			}
		}
		else if (startMenu) {
			background(200);
			textSize(40);
			text("Lunatic's Resolve", width/4,height/4,width/2,height/2);
			textSize(30);
			text("Press SPACE to start game",width/4,height/3,width/2,height/2*3);
			if (isPressed(32)) {
				startMenu = false;
				cutscene = true;
			}
		} else if (cutscene) {
			cutsceneTicks++;
			if (cutsceneSkipCD > 0) cutsceneSkipCD--;
			if (cutsceneTicks < 300) {
				if (cutsceneTicks == 0) s.playVine();
				image(cutscenes[0],0,0);
			} else
			if (cutsceneTicks < 600) {
				if (cutsceneTicks == 300) s.playVine();
				image(cutscenes[1],0,0);
			} else
			if (cutsceneTicks < 900) {
				if (cutsceneTicks == 600) s.playVine();
				image(cutscenes[2],0,0);
			} else
			if (cutsceneTicks < 1200) {
				if (cutsceneTicks == 900) s.playVine();
				image(cutscenes[3],0,0);
			} else {
				if (cutsceneTicks == 1200) s.playVine();
				cutscene = false;
				cutsceneTicks = 0;
				s.stopTrack();
			}
			
			fill(0);
			text("Press Y to skip cutscenes!!!", 0+10, 0, width,height);
			if (cutsceneSkipCD <= 0 &&(isPressed(((int)'Y')) || isPressed(((int)'y')))) {
				//cutscene = false;
				cutsceneTicks = 300*(((int)(cutsceneTicks/300))+1);
				cutsceneSkipCD = 30;
				//s.stopTrack();
			}
		} else if (gameDone) {
			cst2++;
			s.playEnd();
			background(0);
			fill(0, 95, 143);
			if (whoWon == 1) fill(130, 7, 0);
			if (loreDone) {
				if (cst2 < 300) {
					//if (cutsceneTicks == 0) s.playVine();
					if (whoWon == 1) {
						image(cutscenes[4],0,0);
					} else {
						image(cutscenes[5],0,0);
					}
				} else if (cst2 < 600) {
					//if (cutsceneTicks == 300) s.playVine();
					image(cutscenes[6],0,0);
				} else {
					//if (cutsceneTicks == 600) s.playVine();
					fill(255, 255, 255);
					//text("You've killed a jazz band... and your own kind... \n",width/2-400,height/2-100,width,height);
					text("You truly have a Lunatic's Resolve,", width/2-300,height/2,width,height);

					if (whoWon == 1) fill(130, 7, 0);
					if (whoWon == 2) fill(0, 95, 143);
					textSize(50);
					text("Player " +  whoWon, width/2-300,height/2+100,width,height);
				}
				
				
			} else {
				background(200);
				String t = "Player " + whoWon + " won!!";
				if (whoWon == 2) {
					t = t + "\nPlayer 1, you fell off";
				} else {
					t = t + "\nPlayer 2, you fell off";
				}
				
				text(t, width/2-100,height/2,width,height);
			}
			
		}
		else {
			cutsceneTicks = 0;
			clear();
			
			//somehow add black bars in the game - set the Game's dimensions to 
			//fit within these black bars
			//plan: 50 on the left of game1, 50 in middle, 50 on the right of
			//		game2, 50 on top, 100 at bottom for statistics (within game
			//		x bounds also)
			background(100);
			
			
			game2.draw(this);
			game1.draw(this);
			if (game1.gameOver()) {
				whoWon = 2;
				gameDone = true;
			}
			if (game2.gameOver()) {
				whoWon = 1;
				gameDone = true;
			}
			if (game1.gameCompleted()) {
				whoWon = 1;
				gameDone = true;
				loreDone = true;
			}
			if (game2.gameCompleted()) {
				whoWon = 2;
				gameDone = true;
				loreDone = true;
			}
		}
	}
	
	/**
	 * Updates the keypressed list
	 */
	public void keyPressed() {
		keys.add(keyCode);
		if (key == ESC)  // This prevents a processing program from closing on escape key
			key = 0;
	}

	/**
	 * Updates the keypressed list
	 */
	public void keyReleased() {
		while(keys.contains(keyCode))
			keys.remove(keys.indexOf(keyCode));
	}
	
	/**
	 * Detects if a key is pressed
	 * @param code Key number
	 * @return True if key is pressed, false otherwise
	 */
	public boolean isPressed(Integer code) {
		return keys.contains(code);
	}
	
}
