
package entities.projectiles;

import processing.core.PApplet;
import processing.core.PImage;

/**
 * This class represents a Bullet with the properties of Projectile
 * @author Johnny Zhang
 *
 */
public class Bullet extends Projectile {
	private static PImage thisSprite;
	private static PImage thisSpriteP;
	/**
	 * Creates a new instance of Bullet with the properties of Entity
	 * @param x X-Coordinate
	 * @param y Y-Coordinate
	 * @param w Width of hitbox
	 * @param h Height of hitbox
	 * @param vx X velocity
	 * @param vy Y velocity
	 * @param circle If hitbox a circle
	 * @param p If the Bullet is from the Player
	 * @param dmg Damage of the Bullet
	 */
	public Bullet(double x, double y, double w, double h, double vx, double vy, boolean circle, boolean p, double dmg) {
		super(x, y, w, h, vx, vy, circle, p, dmg);
		this.setHp(1);
	}
	
	/**
	 * Sets up the image for the Bullet
	 * @param surface The drawing surface
	 */
	public void setup(PApplet surface) {
		if (thisSprite == null) {
			System.out.println("loaded image");
			thisSprite = (surface.loadImage("sprites/projectile.png"));
			thisSpriteP = (surface.loadImage("sprites/projectile-bullet.png"));

			thisSprite.resize((int)getWidth(), (int)getHeight());
			thisSpriteP.resize((int)getWidth()/2, (int)(getHeight()*1.5));
		}
		if (getFromPlayer()) {
			setSprite(thisSpriteP);
		} else {
			setSprite(thisSprite);
		}
	}


	
	
}
