package entities.projectiles;

import entities.Entity;
import entities.mobs.Player;
import processing.core.PApplet;
import processing.core.PImage;

/**
 * PowerUp represents an Entity that the Player can interact with to receive benefits
 * PowerUp provides the Player that interacts with it various buffs that increase damage or survivablity
 * @author Johnny Zhang
 *
 */
public class PowerUp extends Entity {
	//1 = heal, 2 = multi, 3 = pierce, 4 = rate, 5 = dmg, 6 = hp
	private int time;
	private int type;
	/**
	 * Creates a new instance of PowerUp
	 * @param x X-Coordinate
	 * @param y Y-Coordinate
	 * @param w Width of hitbox
	 * @param h Height of hitbox
	 * @param type The type of PowerUp
	 * @param circle If the hitbox is a circle
	 */
	public PowerUp(double x, double y, double w, double h, int type, boolean circle) {
		super(x, y, w, h, circle);
		this.type = type;
		if (type == 1) time = 1;
		time = 1;
		setHp(1);
	}
	
	/**
	 * Gives the PowerUp to the Player
	 * @param e The Player receiving the PowerUp
	 */
	public void interact(Player e) {
		e.setPowerUpDuration(time);
		e.setPowerUpType(type);
		die();
	}
	
	/**
	 * Sets up the image for the PowerUp
	 * @param surface The drawing surface
	 */
	public void setup(PApplet surface) {
		PImage temp;
		//if (thisSprite == null) {
		System.out.println("loaded power up image");
		String str = "sprites/entity-powerup-";
		str += type;
		str += ".png";
		temp = (surface.loadImage(str));
		temp.resize((int)getWidth(), (int)getHeight());
		
		setSprite(temp);
	}

}
