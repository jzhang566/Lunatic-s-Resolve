package game;
import java.awt.Point;
import java.util.ArrayList;

import entities.Entity;
import entities.mobs.Boss;
import entities.mobs.Enemy;
import entities.mobs.Goon;
import entities.mobs.Player;
import entities.projectiles.Bullet;
import entities.projectiles.Coin;
import entities.projectiles.PowerUp;
import entities.projectiles.Projectile;
import entities.projectiles.Trap;
import processing.core.PApplet;
import processing.core.PImage;

/**
 * The Stage class represents the stages that contains entities and makes them "act"
 * @author ayu663
 */
public class Stage {
	private int stageNum;
	private ArrayList<Entity> entityList;
	private Player curPlayer;
	private Statistics playerStats;
	private Point topLeft, dimensions;
	private boolean gameOver, stageComplete;
	private Background back;
	private int curWave;
	private double walkingMulti;
	private int walkingSwapCD;
	private Sounds sound;
	private boolean soundOn;
	private Boss b;
	
	/**Constructs an instance of Stage 
	 * @param stageNum the stage to construct (1-4)
	 * @param x topLeft x of the stage
	 * @param y topLeft y of the stage
	 * @param width width of the stage
	 * @param height height of the stage
	 * @param p player passed into the stage and to interact there
	 */
	public Stage(int stageNum, int x, int y, int width, int height, Player p) {
		//soundOn = false;
		curWave = 1;
		gameOver = false;
		stageComplete = false;
		topLeft = new Point(x,y);
		//System.out.println(width);
		dimensions = new Point(width, height);
		this.stageNum = stageNum;
		entityList = new ArrayList<Entity>();
		entityList.add(p);
		curPlayer = p;
		b = null;
		walkingSwapCD = 0;
		walkingMulti = 1;
		//Johnny requested below
		p.setEntityList(entityList);
		entityList.add(new Goon(topLeft.x+50, topLeft.y+50, 100, 100, false, stageNum, 1));
		entityList.add(new Goon(topLeft.x+350, topLeft.y+50, 100, 100, false, stageNum, 1));
		entityList.add(new Goon(topLeft.x+200, topLeft.y+200, 100, 100, false, stageNum, 2));
		for (int i = 0; i < 4; i++) {
		//	entityList.add(new Goon(stageNum)); we need different stageNum for
			//different enemy movement
			//entityList.add(new Goon(0, 0, 0, 0, false, null));
		}
		
		//entityList.add(new Boss(stageNum));
		//this is accounted in different waives
		//entityList.add(new Boss(0, 0, 0, 0, false, null));
		
		for (Entity e : entityList) {
			//System.out.println(topLeft.x + " " + topLeft.y + " " + dimensions.x + " " + dimensions.y);
			e.giveBounds(topLeft, dimensions);
		}
		playerStats = new Statistics(curPlayer, x, y+height, width, 200);
	}
	
	/**
	 * Sets up the stage with an image using Processing
	 * @param surface PApplet to setup with
	 */
	public void setup(PApplet surface) {
		for (Entity e : entityList) {
			e.setup(surface);
			e.giveBounds(topLeft, dimensions);
		}
		//System.out.println("setup run");
		PImage cloud = surface.createImage(dimensions.x, dimensions.y, surface.RGB);
		cloud = surface.loadImage("sprites/cloud_tile.jpg");
		//System.out.println(topLeft.x);
		back = new Background(cloud, topLeft.x, topLeft.y, dimensions.x, cloud.height, stageNum);
	}
	
	/**
	 * Visually draws and updates the current frame of the Stage elements, and the getStatistics
	 * @param surface PApplet surface to draw on
	 */
	public void draw(PApplet surface) {
		if (sound == null) {
			sound = new Sounds(stageNum);
			sound.nextTrack();
		}
		
		//surface.clear();
		back.draw(surface);
		back.scroll(5);
		act(surface);
		///updateStats();
		//System.out.println("saygiydfiyfiyg: "+ entityList.size());
		for (Entity e : entityList) {
			//if (e.isVisble()) {
//			if (e instanceof Goon) System.out.println("goon drawn");
			//e.setup(surface);
			e.draw(surface);
			//}
		}

		if (stageNum == 1) {
			surface.fill(0, 132, 209);
		}
		if (stageNum == 2) {
			surface.fill(181, 255, 233);
		}
		if (stageNum == 3) {
			surface.fill(255, 0, 242);
		}
		if (stageNum == 4) {
			surface.fill(255, 0, 0);
		}
		if (b!=null) {
			surface.fill(150);
			surface.rect(topLeft.x+5, topLeft.y+5, dimensions.x-10, 30);

			if (stageNum == 1) {
				surface.fill(0, 132, 209);
			}
			if (stageNum == 2) {
				surface.fill(181, 255, 233);
			}
			if (stageNum == 3) {
				surface.fill(255, 0, 242);
			}
			if (stageNum == 4) {
				surface.fill(255, 0, 0);
			}
			if (b.getHp()>b.getMaxHP()) {
				surface.rect(topLeft.x+10, topLeft.y+10, (float)(dimensions.x-20), 20);
			} else {
				surface.rect(topLeft.x+10, topLeft.y+10, (float)((dimensions.x-20)*b.getHp()/b.getMaxHP()), 20);
			}
			surface.text("BOSS HP: " + (int)b.getHp() + "/" + (int)b.getMaxHP(), topLeft.x+10, topLeft.y+35, dimensions.x, dimensions.y);
		} else {
			surface.text("Current Wave: " + curWave + "/3", topLeft.x+10, topLeft.y, dimensions.x, dimensions.y);
		}
	}
	
	/**
	 * Returns a Statistics object used for drawing outside of the class
	 * @return Statistics The statistics for this Player
	 */
	public Statistics getStats() {
		return playerStats;
	}
	
	/**
	 * Forward inputs into the player for movement and skill activation
	 * @param surface Surface to draw on
	 * @param gameNum The game number
	 */
	public void giveInputs(DrawingSurface surface, int gameNum) {
//		if (surface.isPressed(66) || surface.isPressed(98)) {
//			//hard code stage skip
//			curWave +=1;
//		}
		
		
		
		if (gameNum == 1) {
			if (surface.isPressed((int)'e') || surface.isPressed((int)'E')) {
				System.out.println("e");
				if (walkingMulti == 0.5) walkingMulti = 1;
				else walkingMulti = 0.5;
				walkingSwapCD = 30;
			}
			if (surface.isPressed(65) || surface.isPressed(97)) {
				System.out.println("a");
				curPlayer.move(-5 * walkingMulti, 0);
			}

			if (surface.isPressed(68) || surface.isPressed(100)) {
				System.out.println("d");
				curPlayer.move(5 * walkingMulti, 0);
			}

			if (surface.isPressed(87) || surface.isPressed(119)) {
				System.out.println("w");
				curPlayer.move(0, -5 * walkingMulti);
			}

			if (surface.isPressed(83) || surface.isPressed(115)) {

				System.out.println("s");
				curPlayer.move(0, 5 * walkingMulti);
			}
			if (surface.isPressed(102) || surface.isPressed(70)) {

				System.out.println("f");
				if(curPlayer.shoot(surface)) {
					//if (sound != null) sound.playEffect();
				}
			}
//			//traps (handled in game now)
//			if (surface.isPressed((int)'n') || surface.isPressed((int)'N')) {
//				System.out.println("n");
//				//activate trap on p1 side
//				if (trapCD == 0) {
//					Trap t = new Trap(topLeft.x+100, topLeft.y+50, 40, 40, 0, 5, 1, false, 20);
//					t.setup(surface);
//					t.giveBounds(topLeft, dimensions);
//					entityList.add(t);
//	
//					Trap t1 = new Trap(topLeft.x+200, topLeft.y+100, 40, 40, 0, 5, 1, false, 20);
//					t1.setup(surface);
//					t1.giveBounds(topLeft, dimensions);
//					entityList.add(t1);
//	
//					Trap t2 = new Trap(topLeft.x+300, topLeft.y+50, 40, 40, 0, 5, 1, false, 20);
//					t2.setup(surface);
//					t2.giveBounds(topLeft, dimensions);
//					entityList.add(t2);
//					trapCD = 900;
//				}
//				
//			}
//			if (surface.isPressed((int)'m') || surface.isPressed((int)'M')) {
//				System.out.println("m");
//				//activate trap on p1 side
//				if (trapCD == 0) {
//					Trap t = new Trap(topLeft.x+100, topLeft.y+50, 40, 40, 0, 5, 2, false, 0);
//					t.setup(surface);
//					t.giveBounds(topLeft, dimensions);
//					entityList.add(t);
//	
//					Trap t1 = new Trap(topLeft.x+200, topLeft.y+100, 40, 40, 0, 5, 2, false, 0);
//					t1.setup(surface);
//					t1.giveBounds(topLeft, dimensions);
//					entityList.add(t1);
//	
//					Trap t2 = new Trap(topLeft.x+300, topLeft.y+50, 40, 40, 0, 5, 2, false, 0);
//					t2.setup(surface);
//					t2.giveBounds(topLeft, dimensions);
//					entityList.add(t2);
//					trapCD = 900;
//				}
//			}
//			if (surface.isPressed((int)',') || surface.isPressed((int)'<')) {
//				System.out.println(",");
//				//activate trap on p1 side
//
//				if (trapCD == 0) {
//					Trap t = new Trap(topLeft.x+100, topLeft.y+50, 40, 40, 0, 5, 3, false, 0);
//					t.setup(surface);
//					t.giveBounds(topLeft, dimensions);
//					entityList.add(t);
//	
//					Trap t1 = new Trap(topLeft.x+200, topLeft.y+100, 40, 40, 0, 5, 3, false, 0);
//					t1.setup(surface);
//					t1.giveBounds(topLeft, dimensions);
//					entityList.add(t1);
//	
//					Trap t2 = new Trap(topLeft.x+300, topLeft.y+50, 40, 40, 0, 5, 3, false, 0);
//					t2.setup(surface);
//					t2.giveBounds(topLeft, dimensions);
//					entityList.add(t2);
//					trapCD = 900;
//				}
//			}
			
			
			//curPlayer.shift(surface.isPressed(61) && !surface.isPressed(64) , surface.isPressed(77) && !surface.isPressed(73));
//			if (surface.isPressed(77)) {
//				curPlayer.shift(surface.isPressed(61) && !surface.isPressed(64) , surface.isPressed(77) && !surface.isPressed(73))
//				//bunch of ifs and moves/skill use methods in player
//			}
		}
		if (gameNum == 2) {
			if (surface.isPressed((int)'o') || surface.isPressed((int)'O')) {
				System.out.println("o");
				if (walkingMulti == 2) walkingMulti = 1;
				else walkingMulti = 2;
				walkingSwapCD = 30;
			}
			if (surface.isPressed(74) || surface.isPressed(106)) {
				System.out.println("j");
				curPlayer.move(-5 * walkingMulti, 0);
			}
			if (surface.isPressed(74) || surface.isPressed(106)) {
				System.out.println("j");
				curPlayer.move(-5 * walkingMulti, 0);
			}

			if (surface.isPressed(76) || surface.isPressed(108)) {
				System.out.println("l");
				curPlayer.move(5 * walkingMulti, 0);
			}

			if (surface.isPressed(73) || surface.isPressed(105)) {
				System.out.println("i");
				curPlayer.move(0, -5 * walkingMulti);
			}

			if (surface.isPressed(75) || surface.isPressed(107)) {

				System.out.println("k");
				curPlayer.move(0, 5 * walkingMulti);
			}
			if (surface.isPressed(58) || surface.isPressed(59)) {
				System.out.println(";");
				if(curPlayer.shoot(surface)) {
					//if (sound != null) sound.playEffect();
				}
			}
			
			
			//traps (handled in game now)
//
//			if (surface.isPressed((int)'z') || surface.isPressed((int)'Z')) {
//				System.out.println("z");
//				//activate trap on p2 side
//
//				if (trapCD == 0) {
//					Trap t = new Trap(topLeft.x+100, topLeft.y+50, 40, 40, 0, 5, 1, false, 20);
//					t.setup(surface);
//					t.giveBounds(topLeft, dimensions);
//					entityList.add(t);
//	
//					Trap t1 = new Trap(topLeft.x+200, topLeft.y+100, 40, 40, 0, 5, 1, false, 20);
//					t1.setup(surface);
//					t1.giveBounds(topLeft, dimensions);
//					entityList.add(t1);
//	
//					Trap t2 = new Trap(topLeft.x+300, topLeft.y+50, 40, 40, 0, 5, 1, false, 20);
//					t2.setup(surface);
//					t2.giveBounds(topLeft, dimensions);
//					entityList.add(t2);
//					trapCD = 900;
//				}
//			}
//			if (surface.isPressed((int)'x') || surface.isPressed((int)'X')) {
//				System.out.println("x");
//				//activate trap on p2 side
//
//				if (trapCD == 0) {
//					Trap t = new Trap(topLeft.x+100, topLeft.y+50, 40, 40, 0, 5, 2, false, 0);
//					t.setup(surface);
//					t.giveBounds(topLeft, dimensions);
//					entityList.add(t);
//	
//					Trap t1 = new Trap(topLeft.x+200, topLeft.y+100, 40, 40, 0, 5, 2, false, 0);
//					t1.setup(surface);
//					t1.giveBounds(topLeft, dimensions);
//					entityList.add(t1);
//	
//					Trap t2 = new Trap(topLeft.x+300, topLeft.y+50, 40, 40, 0, 5, 2, false, 0);
//					t2.setup(surface);
//					t2.giveBounds(topLeft, dimensions);
//					entityList.add(t2);
//					trapCD = 900;
//				}
//			}
//			if (surface.isPressed((int)'c') || surface.isPressed((int)'C')) {
//				System.out.println("c");
//				//activate trap on p2 side
//
//				if (trapCD == 0) {
//					Trap t = new Trap(topLeft.x+100, topLeft.y+50, 40, 40, 0, 5, 3, false, 0);
//					t.setup(surface);
//					t.giveBounds(topLeft, dimensions);
//					entityList.add(t);
//	
//					Trap t1 = new Trap(topLeft.x+200, topLeft.y+100, 40, 40, 0, 5, 3, false, 0);
//					t1.setup(surface);
//					t1.giveBounds(topLeft, dimensions);
//					entityList.add(t1);
//	
//					Trap t2 = new Trap(topLeft.x+300, topLeft.y+50, 40, 40, 0, 5, 3, false, 0);
//					t2.setup(surface);
//					t2.giveBounds(topLeft, dimensions);
//					entityList.add(t2);
//					trapCD = 900;
//				}
//			}
		}
	}
	
	/** 
	 * @return True if all enemies died, false otherwise
	 */
	public boolean isCompleted() {
		return curWave > 3;
	}
	/** 
	 * @return True if the game is over, false otherwise
	 */
	public boolean gameOver() {
		return gameOver;
	}
	
	/**
	 * Stops the music or sound
	 */
	public void stopSound() {
		if (sound != null) sound.stopTrack();
	}
	
	private void act(PApplet surface) {
		walkingSwapCD--;
		boolean playerExists = false;
		boolean entitiesExist = false;
		
		//entitiesExist = true;
		
		for (int i = 0; i < entityList.size(); i++) {
			Entity e = entityList.get(i);
			if (e.isDead()) {
				if (e instanceof Enemy) {
					Coin c = new Coin(e.getX()+e.getWidth()/2, curPlayer.getY()/*e.getY()+e.getHeight()/2*/, 40, 40, false);
					c.setup(surface);
					entityList.add(c);
				}
				//if (e instanceof Projectile) System.out.println("didfdsfjdsuifyhdsue");
				entityList.remove(entityList.indexOf(e));
				continue;
			}
			if (e instanceof Player) {
				playerExists = true;
				curPlayer = (Player)e;
				curPlayer.setEntityList(entityList);
				//if (curPlayer != null)curPlayer.act();
			}
			if (e instanceof Enemy) {
				entitiesExist = true;
				((Enemy) e).setEntityList(entityList);
			}
			e.act();
			
		}

		//entityList removes all projectiles???
		
		//System.out.println(entityList.size());
		//for (Entity e : entityList) if (e instanceof Projectile) System.out.println("proj exists");
		for (int i = 0; i < entityList.size(); i++) {
			Entity e = entityList.get(i);
			if (e instanceof Player || e instanceof Enemy) {
				//System.out.println("eva");
				for (int j = 0; j < entityList.size(); j++) {
					//System.out.println("eva2");
					Entity e2 = entityList.get(j);
					if (e2 instanceof Projectile) {
						//System.out.println("neva");
						if (e.isTouching(e2)) {
							//System.out.println("say neva");
							//if (sound != null) sound.hitEffect();
							((Projectile)e2).interact(e);
						}
					}
					else if (e2 instanceof PowerUp && e instanceof Player) {
						//System.out.println("neva");
						if (e.isTouching(e2)) {
							//System.out.println("say neva2");
							//if (sound != null) sound.pickUpEffect();
							((PowerUp)e2).interact((Player)e);
						}
					}
					else if (e2 instanceof Coin && e instanceof Player) {
						if (e.isTouching(e2)) {
							//if (sound != null) sound.pickUpEffect();
							((Coin)e2).interact((Player)e);
						}
					}
				}
			}
		}
		for (int i = 0; i < entityList.size(); i++) {
			Entity e = entityList.get(i);
			if (e instanceof Enemy) {
				entitiesExist = true;
				//e.act();
			} else {
				//e.act();
			}
		}
		if (!playerExists) gameOver = true;
		
		if (!entitiesExist) {
			curWave++;
			//you can add below another set of if conditions for each stage that
			//can change up wave number, enemy number, and other properties
			if (curWave == 3) {
				if (sound != null && soundOn) sound.nextTrack();
				if(stageNum == 1) {
					Boss b = new Boss(topLeft.x+75, topLeft.y+125, 100, 100, false, stageNum);
					b.setup(surface);
					b.giveBounds(topLeft, dimensions);
					this.b = b;
				}
				if(stageNum == 2) {
					Boss b = new Boss(topLeft.x+175, topLeft.y+125, 100, 100, false, stageNum);
					b.setup(surface);
					b.giveBounds(topLeft, dimensions);
					this.b = b;
				}
				if(stageNum == 3) {
					Boss b = new Boss(topLeft.x+190, topLeft.y+125, 100, 100, false, stageNum);
					b.setup(surface);
					b.giveBounds(topLeft, dimensions);
					this.b = b;
				}
				if(stageNum == 4) {
					Boss b = new Boss(topLeft.x+140, topLeft.y+125, 200, 200, false, stageNum);
					b.setup(surface);
					b.giveBounds(topLeft, dimensions);
					this.b = b;
				}
				
				entityList.add(b);
				if (stageNum == 1) {
					PowerUp p = new PowerUp(topLeft.x+dimensions.x*Math.random(), topLeft.y+500, 50, 50, ((int)(Math.random()*6.0))+1, true);
					p.setup(surface);
					entityList.add(p);
				}
				if (stageNum == 2) {
					PowerUp p = new PowerUp(topLeft.x+dimensions.x*Math.random(), topLeft.y+200, 50, 50, ((int)(Math.random()*6.0))+1, true);
					p.setup(surface);
					entityList.add(p);
				}
				if (stageNum == 3) {
					PowerUp p = new PowerUp(topLeft.x+dimensions.x*Math.random(), topLeft.y+100, 50, 50, ((int)(Math.random()*6.0))+1, true);
					p.setup(surface);
					entityList.add(p);
				}
			} else if (curWave < 3) {
				
				Goon g = new Goon(topLeft.x+75, topLeft.y+50, 75, 75, false, stageNum, 1);
				g.setup(surface);
				g.giveBounds(topLeft, dimensions);
				entityList.add(g);
				
				g = new Goon(topLeft.x+275, topLeft.y+50, 75, 75, false, stageNum, 1);
				g.setup(surface);
				g.giveBounds(topLeft, dimensions);
				entityList.add(g);
				
				g = new Goon(topLeft.x+75, topLeft.y+200, 75, 75, false, stageNum, 2);
				g.setup(surface);
				g.giveBounds(topLeft, dimensions);
				entityList.add(g);
				
				g = new Goon(topLeft.x+275, topLeft.y+200, 75, 75, false, stageNum, 2);
				g.setup(surface);
				g.giveBounds(topLeft, dimensions);
				entityList.add(g);
			}
		}
	}
	
	/**
	 * Returns the current Player referenced in Stage
	 * @return Player current Player character
	 */
	public Player getPlayer() {
		return curPlayer;
	}

	/**
	 * @return The entity list.
	 */
	public ArrayList<Entity> getEntityList() {
		return entityList;
	}
	private void updateStats() {
		//will update Statistics with data about the Player curPlayer
	}
	
}
