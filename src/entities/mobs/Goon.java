package entities.mobs;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import entities.Entity;
import entities.projectiles.Bullet;
import processing.core.PApplet;
import processing.core.PImage;

/**
 * The class Goon class represents an Enemy
 *  * @author nathangu
 */
public class Goon extends Enemy{
	private int stageNum;
	private int goonNum;
	private PApplet surface;
	private long frames, shots;
	private double[][] movement;
	private int curInd;
	private int counter;
	/**
	 * Creates a new instance of Goon
	 * @param x X-coordinate
	 * @param y Y-coordinate
	 * @param w Width of the hitbox
	 * @param h Height of the hitbox
	 * @param circle If the hitbox is a circle
	 * @param stageNum The stage number
	 * @param goonNum The Goon number
	 */
	public Goon(double x, double y, double w, double h, boolean circle, int stageNum, int goonNum) {
		super(x, y, w, h, circle);
		this.goonNum = goonNum;
		this.stageNum = stageNum;
		setHp(2000);	
		frames = 0;
		counter = 0;
		movement = new double[6][2];
		if (stageNum == 1) {
			if(goonNum == 1) {
				movement = new double[][]{{-6, 0}, {6, 0}
				};
			}
			if(goonNum == 2) {
				movement = new double[][]{{-10, 6}, {10, 6}
				};	
			}
		}
		if (stageNum == 2) {
			setHp(2500);
			if(goonNum == 1) {
				movement = new double[][] {
					{-3, 0}, {-3, 0}, {-3, 0}, {-3,0}, {3,0}, {3,0}, {3,0}
				};
			}
			if(goonNum == 2) {
				movement = new double[][] {
					{-3, 8}, {4, 8}, {-4, -8}, {-3, -8}
				};
			}
		}
		if (stageNum == 3) {
			setHp(3000);
			if(goonNum == 1) {
				movement = new double[][] {
					{-3, 0}, {-3, 0}, {-3, 0}, {-3,0}, {3,0}, {3,0}, {3,0}
				};
			}
			if(goonNum == 2) {
				movement = new double[][] {
					{2, -10}, {-7, -10}, {-2, 10}, {12,10}
				};
			}
		}
		if (stageNum == 4) {
			setHp(3500);
			if(goonNum == 1) {
				movement = new double[][] {
					{-4, 6}, {4, 6}, {4, -6}, {-4,-6}
				};
			}
			if(goonNum == 2) {
				movement = new double[][] {
					{-7, 5}, {7, 5}
				};
			}
		}
		curInd = 0;
	}	
	
	
	/**
	 * Shoots Bullets
	 */
	public void shoot() {
		if (shots % 6 == 0) {
			Bullet b = new Bullet(this.getX()+getWidth()/2-15, this.getY()+this.getHeight(), 20, 20, 0.5, 4, true, false, 1);
			b.setup(surface);
			b.bounds = bounds.clone();
			getDaList().add(b);
		}
		if (shots % 5 == 1) {
			Bullet b = new Bullet(this.getX()+getWidth()/2-15, this.getY()+this.getHeight(), 20, 20, 0, 5, true, false, 1);
			b.setup(surface);
			b.bounds = bounds.clone();
			getDaList().add(b);
		}
		if (shots % 4 == 1) {
			Bullet b = new Bullet(this.getX()+getWidth()/2-15, this.getY()+this.getHeight(), 20, 20, -0.5, 4, true, false, 1);
			b.setup(surface);
			b.bounds = bounds.clone();
			getDaList().add(b);
		}
		shots++;

	}
	
	
	/**
	 * Allows the Goon to move and shoot
	 */
	public void act() {
		frames++;	
		if (frames % 15 == 0) {
			counter++;
			curInd++;
			if (curInd >= movement.length) {
				curInd = 0;
			}
			
			double vx = movement[curInd][0];
			double vy = movement[curInd][1];
			if(counter == 1) {
				movement[curInd][1] = -vy;
				movement[curInd][0] = -vx;
				counter = 0;
			}
			setvx(vx);
			setvy(vy);
			shoot();
			frames = 0;
		}
		setX(getvx() + getX());
		setY(getvy() + getY());
	}
	
	/**
	 * Draws out the Goon
	 * @param surface The drawing surface
	 */
	public void draw(PApplet surface) {
		surface.tint(255, (float)(1.0*(getHp())/2500*125)+100);
		surface.tint((int)(1.0*(getHp())/2500*100)+125,(int)(1.0*(getHp())/2500*100)+125,(int)(1.0*(getHp())/2500*100)+125);
		super.draw(surface);
		surface.noTint();
	}
		

	
	/**
	 * Sets the X-coordinate velocity
	 * @param d The new x-velocity
	 */
	public void setXMovement(double d) {
		setvx(d);
	}
	
	/**
	 * Sets the X-coordinate velocity
	 * @param d The new y-velocity
	 */
	public void setYMovement(double d) {
		setvy(d);
	}
	
	
	/**
	 * Sets up the image for the Goon
	 * @param surface The drawing surface
	*/
	public void setup(PApplet surface) {
		this.surface = surface;
		PImage temp;
		System.out.println("loaded image");
		temp = (surface.loadImage("sprites/stage" + stageNum + "-" + goonNum+".png"));
		temp.resize((int)getWidth(), (int)getHeight());
		setSprite(temp);
	}

	
}
