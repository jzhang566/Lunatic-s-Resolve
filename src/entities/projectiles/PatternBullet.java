package entities.projectiles;

import java.awt.Point;
import java.util.ArrayList;

import processing.core.PApplet;

/**
 * PatternBullet represents a collection of bullets that follow a certain pattern
 * @author Johnny Zhang
 *
 */
public class PatternBullet {
	private ArrayList<Bullet> bullets;
	private int type;
	public double[] bounds;

	/**
	 * Creates a new instance of PatternBullet
	 * @param x X-Coordinate
	 * @param y Y-Coordinate
	 * @param type The stage number
	 * @param dmg The damage of the bullet
	 */
	public PatternBullet(int x, int y, int type, int dmg) {
		this.type = type;
		bullets = new ArrayList<Bullet>();
		if (type == 1) {
			bullets.add(new Bullet(x, y, 30, 30, -5, -5, true, false, dmg));
			bullets.add(new Bullet(x, y, 30, 30, -5, 0, true, false, dmg));
			bullets.add(new Bullet(x, y, 30, 30, -5, 5, true, false, dmg));
			bullets.add(new Bullet(x, y, 30, 30, 0, -5, true, false, dmg));
			bullets.add(new Bullet(x, y, 30, 30, 0, 5, true, false, dmg));
			bullets.add(new Bullet(x, y, 30, 30, 5, -5, true, false, dmg));
			bullets.add(new Bullet(x, y, 30, 30, 5, 0, true, false, dmg));
			bullets.add(new Bullet(x, y, 30, 30, 5, 5, true, false, dmg));
		}
		if (type == 2) {
			bullets.add(new Bullet(x, y, 30, 30, -3.5, -3.5, true, false, dmg));
			bullets.add(new Bullet(x, y, 30, 30, -5, 0, true, false, dmg));
			bullets.add(new Bullet(x, y, 30, 30, -3.5, 3.5, true, false, dmg));
			bullets.add(new Bullet(x, y, 30, 30, 0, -5, true, false, dmg));
			bullets.add(new Bullet(x, y, 30, 30, 0, 5, true, false, dmg));
			bullets.add(new Bullet(x, y, 30, 30, 3.5, -3.5, true, false, dmg));
			bullets.add(new Bullet(x, y, 30, 30, 5, 0, true, false, dmg));
			bullets.add(new Bullet(x, y, 30, 30, 3.5, 3.5, true, false, dmg));
		}
		if (type == 3) {
			for (int i = -5; i < 5; i++) {
				for (int j = -5; j < 5; j++) {
					if (i == j)
					bullets.add(new Bullet(x + (i * 10), y + (i * 10), 30, 30, 0, 5, true, false, dmg));
				}
			}
		}
		if(type == 4) {
			for (int i = -5; i < 5; i++) {
				for (int j = -5; j < 5; j++) {
					if (i == j)
					bullets.add(new Bullet(x + (i * 10), y + (i * 10), 30, 30, 0, 5, true, false, dmg));
				}
			}
			
			
			bullets.add(new Bullet(x, y, 30, 30, -3.5, -3.5, true, false, dmg));
			bullets.add(new Bullet(x, y, 30, 30, -5, 0, true, false, dmg));
			bullets.add(new Bullet(x, y, 30, 30, -3.5, 3.5, true, false, dmg));
			bullets.add(new Bullet(x, y, 30, 30, 0, -5, true, false, dmg));
			bullets.add(new Bullet(x, y, 30, 30, 0, 5, true, false, dmg));
			bullets.add(new Bullet(x, y, 30, 30, 3.5, -3.5, true, false, dmg));
			bullets.add(new Bullet(x, y, 30, 30, 5, 0, true, false, dmg));
			bullets.add(new Bullet(x, y, 30, 30, 3.5, 3.5, true, false, dmg));
			
		}
	}
	
	/**
	 * Gives the boundaries of the window
	 * @param bounds The array of boundaries
	 */
	public void giveBounds(double[] bounds) {
		System.out.println("gave bounds" + bounds[0] + " " + bounds[1] + " " + bounds[2] + " " + bounds[3]);
		this.bounds = bounds.clone();
	}
	
	/**
	 * Allows the Bullets to move
	 */
	public void act() {
		if (type == 2) {
			for (Bullet b : bullets) {
				b.act();
				double temp = b.getvx();
				b.setvx(b.getvy() / 2);
				b.setvy(temp / 2);
			}
		}
	}
	
	/**
	 * @return The bullets
	 */
	public ArrayList<Bullet> getBullets() {
		return bullets;
	}
	
	
	/**
	 * Sets up the PatterBullet
	 * @param surface The drawing surface
	 */
	public void setup(PApplet surface) {
		//PImage temp;
		for (Bullet b : bullets) {
			b.setup(surface);
		}
		for (Bullet b : bullets) {
			b.bounds = bounds.clone();
		}
	}
}
