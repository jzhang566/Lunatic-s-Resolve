package entities.projectiles;

import entities.mobs.Player;
import processing.core.PApplet;
import processing.core.PImage;


/**
 * Wind represents a Projectile that Players interact with
 * Wind contains the same properties as Projectile
 * @author Johnny Zhang
 *
 */
public class Wind extends Projectile {
	/**
	 * Creates a new instance of Wind with the properties of Entity
	 * @param x X-Coordinate
	 * @param y Y-Coordinate
	 * @param w Width of hitbox
	 * @param h Height of hitbox
	 * @param vx X velocity
	 * @param vy Y velocity
	 * @param circle If the hitbox is a circle
	 * @param dmg Damage of the Wind
	 */
	public Wind(double x, double y, double w, double h, double vx, double vy, boolean circle, double dmg) {
		super(x, y, w, h, vx, vy, circle, false, dmg);
	}
	
	/**
	 * Pushes the Player back 
	 * @param player The Player
	 */
	public void interact(Player player) {
		player.setvy(player.getvy() - 10);
	}
	
	/**
	 * Sets up the image
	 * @param surface The drawing surface
	 */
	public void setup(PApplet surface) {
		PImage temp;
		System.out.println("loaded image");
		temp = (surface.loadImage("sprites/projectile-wind.png"));
		temp.resize((int)getWidth(), (int)getHeight());
		setSprite(temp);
	}
	
}
