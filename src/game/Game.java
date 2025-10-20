package game;
import java.awt.Point;
import java.util.ArrayList;

import entities.mobs.Player;
import entities.projectiles.Trap;
import processing.core.PApplet;

/**
 * Game represents the framework, with menus, stages, and other important elements in a game
 * The Game class draws out the Game using the Processing library
 * @author ayu663
 */
public class Game {
	private int gameNum;
	private ArrayList<Stage> stages;
	private Stage curStage;
	private int curStageInd;
	private Statistics playerStats;
	private Point dimensions, topLeft;
	private Player p, otherPlayer;
	private int trapCD, stageSkipCD;
	
	/**
	 * Constructs a new Game window
	 * @param gameNum Denoting the game for player 1 or 2 
	 * @param width The width of the processing window
	 * @param height The height of the processing window
	 */
	public Game(int gameNum, int width, int height) {
		//System.out.println(gameNum + " width: " + width);
		dimensions = new Point(width/2,height);
		p = null;
		
		if (gameNum == 1) p = new Player(width/4-50, height/4*3-50, 40, 50, false, gameNum);
		if (gameNum == 2) p = new Player(width/4*3-50, height/4*3-50, 40, 50, false, gameNum);

		this.gameNum = gameNum;
		int x = 0;
		stages = new ArrayList<Stage>();
		if (gameNum == 2) {
			x = width/2;
			//System.out.println("game2x is " + x);
		} else {
			//System.out.println("game1x is " + x);
		}
		for (int i = 1; i <= 4; i++) {
			stages.add(new Stage(i, x+50, 0+50, width/2-50-50, height-200-50,p)); //100 on the bottom saved for statistics
		}
		curStage = stages.get(0);
		topLeft = new Point(x, 0);
		trapCD = 0;
		
		//delete this when done testing
		stageSkipCD = 0;
		
	}
	
	/**
	 *  Returns the current Stage number this game is on
	 * @return int The current Stage (1-4)*/
	public int getStageNum() {
		return curStageInd+1;
	}
	//switches stage
	private boolean switchStage(int i) {
		if (i <= stages.size() && i > 0) {
			//System.out.println(i);
			curStage = stages.get(i-1);
			curStageInd++;
			return true;
		}
		curStageInd++;
		return false;
	}
	private boolean nextStage() {
		curStage.stopSound();
		return switchStage(stages.indexOf(curStage)+2);
	}
	
	/**
	 * Gets the current Player
	 * @return The Player
	 */
	public Player getCurPlayer() {
		return p;
	}
	
	/**
	 * Sets the other Player
	 * @param p The other player
	 */
	public void setOtherPlayer(Player p) {
		otherPlayer = p;
	}
	/**
	 * Sets up the stages
	 * @param surface PApplet to setup things with
	 */
	public void setup(PApplet surface) {
		for (Stage s : stages) s.setup(surface);
	}
	
	
	/** 
	 * Draws the current Game on the PApplet
	 * @param surface Surface to draw things on
	 */
	public void draw(PApplet surface) {
		if (trapCD > 0) trapCD--;
		if (stageSkipCD > 0) stageSkipCD--;
		if (curStage.isCompleted()) {
			System.out.println(nextStage());
			return;
		}
		playerStats = curStage.getStats();
		curStage.draw(surface);
		surface.noStroke();
		if (gameNum == 1) {
			surface.fill(130, 7, 0);
		}
		if (gameNum == 2) {
			surface.fill(0, 95, 143);
		}
		//System.out.println("Drawing game:" + gameNum);
		surface.rect(topLeft.x, topLeft.y, dimensions.x, 50);
		surface.rect(topLeft.x, topLeft.y+dimensions.y-200, dimensions.x, 200);

		surface.rect(topLeft.x, topLeft.y, 50, dimensions.y);
		surface.rect(topLeft.x+dimensions.x-50, topLeft.y, 50, dimensions.y);
		playerStats.draw(surface);
		
	}
	
	/**
	 * Returns if game is completed(last stage)
	 * @return True if all stages are completed, false otherwise
	 */
	public boolean gameCompleted() {
		if (curStageInd >= stages.size()) {
			curStage.stopSound();
		}
		return curStageInd >= stages.size();
	}
	
	/**
	 * Returns if game is over (game over or player is dead)
	 * @return True if the game is over, false otherwise
	 */
	public boolean gameOver() {
		if (curStage.gameOver()) {
			curStage.stopSound();
		}
		return curStage.gameOver();
	}
	
	/**
	 * Forwards player inputs into Player
	 * @param surface DrawingSurface class that has keypresses
	 */
	public void giveInputs(DrawingSurface surface) {
		if ((surface.isPressed(66) || surface.isPressed(98)) && stageSkipCD == 0) {
			//hard code stage skip
			//System.out.println("WHY");
			nextStage();
			stageSkipCD = 30;
		}
		
		if (gameNum == 1) {
			//traps
			if (surface.isPressed((int)'n') || surface.isPressed((int)'N')) {
				System.out.println("n");
				//activate trap on p1 side
				if (otherPlayer.numCoins() >= 10 && trapCD == 0) {
					Trap t = new Trap(p.getX()+p.getWidth()/2-100-20, topLeft.y+50, 40, 40, -1, 4, 1, false, 20);
					t.setup(surface);
					t.giveBounds(topLeft, dimensions);
					curStage.getEntityList().add(t);
	
					Trap t1 = new Trap(p.getX()+p.getWidth()/2-20, topLeft.y+100, 40, 40, 0, 5, 1, false, 20);
					t1.setup(surface);
					t1.giveBounds(topLeft, dimensions);
					curStage.getEntityList().add(t1);
	
					Trap t2 = new Trap(p.getX()+p.getWidth()/2+100-20, topLeft.y+50, 40, 40, 1, 4, 1, false, 20);
					t2.setup(surface);
					t2.giveBounds(topLeft, dimensions);
					curStage.getEntityList().add(t2);
					otherPlayer.addCoins(-10);
					
					trapCD = 30;
				}
				
			}
			if (surface.isPressed((int)'m') || surface.isPressed((int)'M')) {
				System.out.println("m");
				//activate trap on p1 side
				if (otherPlayer.numCoins() >= 10 && trapCD == 0) {
					Trap t = new Trap(p.getX()+p.getWidth()/2-100-20, topLeft.y+50, 40, 40, -1, 4, 2, false, 20);
					t.setup(surface);
					t.giveBounds(topLeft, dimensions);
					curStage.getEntityList().add(t);
	
					Trap t1 = new Trap(p.getX()+p.getWidth()/2-20, topLeft.y+100, 40, 40, 0, 5, 2, false, 20);
					t1.setup(surface);
					t1.giveBounds(topLeft, dimensions);
					curStage.getEntityList().add(t1);
	
					Trap t2 = new Trap(p.getX()+p.getWidth()/2+100-20, topLeft.y+50, 40, 40, 1, 4, 2, false, 20);
					t2.setup(surface);
					t2.giveBounds(topLeft, dimensions);
					curStage.getEntityList().add(t2);
					otherPlayer.addCoins(-10);
					
					trapCD = 30;
				}
			}
			if (surface.isPressed((int)',') || surface.isPressed((int)'<')) {
				System.out.println(",");
				//activate trap on p1 side

				if (otherPlayer.numCoins() >= 10 && trapCD == 0) {
					Trap t = new Trap(p.getX()+p.getWidth()/2-100-20, topLeft.y+50, 40, 40, -1, 4, 3, false, 20);
					t.setup(surface);
					t.giveBounds(topLeft, dimensions);
					curStage.getEntityList().add(t);
	
					Trap t1 = new Trap(p.getX()+p.getWidth()/2-20, topLeft.y+100, 40, 40, 0, 5, 3, false, 20);
					t1.setup(surface);
					t1.giveBounds(topLeft, dimensions);
					curStage.getEntityList().add(t1);
	
					Trap t2 = new Trap(p.getX()+p.getWidth()/2+100-20, topLeft.y+50, 40, 40, 1, 4, 3, false, 20);
					t2.setup(surface);
					t2.giveBounds(topLeft, dimensions);
					curStage.getEntityList().add(t2);
					otherPlayer.addCoins(-10);
					
					trapCD = 30;
				}
			}
		}
		if (gameNum == 2) {
			//traps
			if (surface.isPressed((int)'z') || surface.isPressed((int)'Z')) {
				System.out.println("z");
				//activate trap on p1 side
				if (otherPlayer.numCoins() >= 10 && trapCD == 0) {
					Trap t = new Trap(p.getX()+p.getWidth()/2-100-20, topLeft.y+50, 40, 40, -1, 4, 1, false, 20);
					t.setup(surface);
					t.giveBounds(topLeft, dimensions);
					curStage.getEntityList().add(t);
	
					Trap t1 = new Trap(p.getX()+p.getWidth()/2-20, topLeft.y+100, 40, 40, 0, 5, 1, false, 20);
					t1.setup(surface);
					t1.giveBounds(topLeft, dimensions);
					curStage.getEntityList().add(t1);
	
					Trap t2 = new Trap(p.getX()+p.getWidth()/2+100-20, topLeft.y+50, 40, 40, 1, 4, 1, false, 20);
					t2.setup(surface);
					t2.giveBounds(topLeft, dimensions);
					curStage.getEntityList().add(t2);
					otherPlayer.addCoins(-10);
					
					trapCD = 30;
				}
				
			}
			if (surface.isPressed((int)'x') || surface.isPressed((int)'X')) {
				System.out.println("x");
				//activate trap on p1 side
				if (otherPlayer.numCoins() >= 10 && trapCD == 0) {
					Trap t = new Trap(p.getX()+p.getWidth()/2-100-20, topLeft.y+50, 40, 40, -1, 4, 2, false, 20);
					t.setup(surface);
					t.giveBounds(topLeft, dimensions);
					curStage.getEntityList().add(t);
	
					Trap t1 = new Trap(p.getX()+p.getWidth()/2-20, topLeft.y+100, 40, 40, 0, 5, 2, false, 20);
					t1.setup(surface);
					t1.giveBounds(topLeft, dimensions);
					curStage.getEntityList().add(t1);
	
					Trap t2 = new Trap(p.getX()+p.getWidth()/2+100-20, topLeft.y+50, 40, 40, 1, 4, 2, false, 20);
					t2.setup(surface);
					t2.giveBounds(topLeft, dimensions);
					curStage.getEntityList().add(t2);
					otherPlayer.addCoins(-10);
					
					trapCD = 30;
				}
			}
			if (surface.isPressed((int)'c') || surface.isPressed((int)'C')) {
				System.out.println("c");
				//activate trap on p1 side

				if (otherPlayer.numCoins() >= 10 && trapCD == 0) {
					Trap t = new Trap(p.getX()+p.getWidth()/2-100-20, topLeft.y+50, 40, 40, -1, 4, 3, false, 20);
					t.setup(surface);
					t.giveBounds(topLeft, dimensions);
					curStage.getEntityList().add(t);
	
					Trap t1 = new Trap(p.getX()+p.getWidth()/2-20, topLeft.y+100, 40, 40, 0, 5, 3, false, 20);
					t1.setup(surface);
					t1.giveBounds(topLeft, dimensions);
					curStage.getEntityList().add(t1);
	
					Trap t2 = new Trap(p.getX()+p.getWidth()/2+100-20, topLeft.y+50, 40, 40, 1, 4, 3, false, 20);
					t2.setup(surface);
					t2.giveBounds(topLeft, dimensions);
					curStage.getEntityList().add(t2);
					otherPlayer.addCoins(-10);
					
					trapCD = 30;
				}
			}
		}
		
		curStage.giveInputs(surface, gameNum);
	}
}
