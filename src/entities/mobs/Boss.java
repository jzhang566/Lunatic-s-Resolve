package entities.mobs;

import java.util.ArrayList;

import entities.Entity;
import entities.projectiles.Bullet;
import entities.projectiles.PatternBullet;
import processing.core.PApplet;
import processing.core.PImage;

/**
 * The Boss class represent a Boss enemy with the properties of Enemy
 * @author nathangu
 */
public class Boss extends Enemy{

	private int stageNum;
	private PApplet surface;
	private double[][] movement;
	private int curInd;
	private int frames;
	private int counter;
	private int ogHP;
	/**
	 * Creates a new instance of Boss
	 * @param x X-coordinate
	 * @param y Y-coordinate
	 * @param w Width of the hitbox
	 * @param h Height of the hitbox
	 * @param circle If the hitbox is a circle
	 * @param stageNum the stage number of the Boss
	 */
	public Boss(double x, double y, double w, double h, boolean circle, int stageNum) {
		super(x, y, w, h, circle);
		this.stageNum = stageNum;
		ogHP = 40000;
		setHp(40000);
		frames = 0;
		movement = new double[4][2];
		if (stageNum == 1) {
			ogHP = 40000;
			movement = new double[][] {{5, -5},{6, 0},{6, 0},{5, 5}
			};
		}
		if (stageNum == 2) {
			ogHP = 60000;
			movement = new double[][] {{-5, 7},{5, 7},{5, -7},{-5, -7}
			};
		}
		if (stageNum == 3) {
			ogHP = 100000;
			movement = new double[][] {{-4, -5},{4, -5},{4, 5},{-4, 5}
			};
		}
		if(stageNum == 4) {
			movement = new double[][] {{-10, -4},{-10, -4},{10, 5},{10, 4},{-10, 4},{-10, 5}, {10, -4},
			{10, -4},{-10, 5}, {-10, 4},{10, 4},{10, 5},{-10, -4}, {-10, -4},{10, -5}, {10, -4}, {-10, 4},
			{-10, 5}, {10, -4}, {10, -4}
			};
		}
		
		setHp(ogHP);
	}
	
	/**
	 * Makes the boss shoot a pattern of bullets
	 */
	public void shootPatternBullet() {
		PatternBullet p = new PatternBullet((int)(getX() + getWidth()/2), (int)(getY()+ getHeight()/2), stageNum, stageNum*2);
		p.giveBounds(bounds.clone());
		p.setup(surface);
		ArrayList<Entity> list = getDaList();
		for (Bullet b : p.getBullets()) {
			if (!list.contains(b)) list.add(b);
		}
	}
	
	
	/**
	 * Draws out the Boss
	 * @param surface Surface to draw on
	 */
	public void draw(PApplet surface) {
		surface.tint(255, (float)(1.0*(getHp())/ogHP*125)+100);
		surface.tint((int)(1.0*(getHp())/ogHP*100)+125,(int)(1.0*(getHp())/ogHP*100)+125,(int)(1.0*(getHp())/ogHP*100)+125);
		super.draw(surface);
		surface.noTint();
	}
	
	/**
	 * Allows the Boss to move and shoot
	 */
	public void act() {
		frames++;	
		if (frames % 15 == 0 && stageNum == 1) {
			counter++;
			curInd++;
			if (curInd >= movement.length) {
				curInd = 0;
			}
			
			double vx = movement[curInd][0];
			double vy = movement[curInd][1];
			double chance = Math.random();
			if(counter == 1 || (chance < 0.5 && stageNum == 3)) {
				movement[curInd][1] = -vy;
				movement[curInd][0] = -vx;
				counter = 0;
			}
			setvx(vx);
			setvy(vy);
			shootPatternBullet();
			frames = 0;
		}
		
		if (frames % 23 == 0 && stageNum == 2) {
			counter++;
			curInd++;
			if (curInd >= movement.length) {
				curInd = 0;
			}
			
			double vx = movement[curInd][0];
			double vy = movement[curInd][1];
			double chance = Math.random();
			if(counter == 1 || (chance < 0.5 && stageNum == 3)) {
				movement[curInd][1] = -vy;
				movement[curInd][0] = -vx;
				counter = 0;
			}
			setvx(vx);
			setvy(vy);
			shootPatternBullet();
			frames = 0;
		}
		
		if (frames % 30 == 0 && stageNum == 3) {
			counter++;
			curInd++;
			if (curInd >= movement.length) {
				curInd = 0;
			}
			
			double vx = movement[curInd][0];
			double vy = movement[curInd][1];
			double chance = Math.random();
			if(counter == 1 || (chance < 0.5 && stageNum == 3)) {
				movement[curInd][1] = -vy;
				movement[curInd][0] = -vx;
				counter = 0;
			}
			setvx(vx);
			setvy(vy);
			shootPatternBullet();
			frames = 0;
		}
		
		if (frames % 10 == 0 && stageNum == 4) {
			counter++;
			curInd++;
			if (curInd >= movement.length) {
				curInd = 0;
			}
			double vx = movement[curInd][0];
			double vy = movement[curInd][1];
			double chance = Math.random();
			if(counter == 1 || (chance < 0.5 && stageNum == 3)) {
				movement[curInd][1] = -vy;
				movement[curInd][0] = -vx;
				counter = 0;
			}
			if(curInd % 3 == 1) {
				shootPatternBullet();
			}
			setvx(vx);
			setvy(vy);
			frames = 0;
		}
		setX(getvx() + getX());
		setY(getvy() + getY());
	}
	
	
	/**
	 * @return The original max HP of the Boss
	 */
	public int getMaxHP() {
		return ogHP;
	}
	
	
	/**
	 * Sets up the Boss with an image
	 * @param surface PApplet to setup with
	 */
	public void setup(PApplet surface) {
		this.surface = surface;
		PImage temp;
		System.out.println("loaded image");
		temp = (surface.loadImage("sprites/stage" + stageNum + "-3.png"));
		temp.resize((int)getWidth(), (int)getHeight());
		setSprite(temp);
	}
}
