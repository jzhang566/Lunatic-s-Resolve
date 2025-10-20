package entities.projectiles;

import entities.Entity;
import entities.mobs.Player;
import processing.core.PApplet;
import processing.core.PImage;

/**
 * Coin represents an Entity that can be picked up by the Player
 * The Coin has a value and can be added or deducted from a Player
 * @author Johnny Zhang
 */
public class Coin extends Entity {
	private int value;
	/**
	 * Creates a new instance of Coin with the properties of Entity
	 * @param x X-Coordinate
	 * @param y Y-Coordinate
	 * @param w Width of hitbox
	 * @param h Height of hitbox
	 * @param circle If the hitbox is a circle
	 */
	public Coin(double x, double y, double w, double h, boolean circle) {
		super(x, y, w, h, circle);
		System.out.println("dfdfsf");
		value = 5;
		setHp(1);
	}
	
	/**
	 * Adds the coins to the Player
	 * @param e The Player
	 */
	public void interact(Player e) {
		e.addCoins(value);
		die();
	}
	
	/**
	 * Sets up the image for the coin
	 * @param surface The drawing surface
	 */
	public void setup(PApplet surface) {
		PImage temp;
		System.out.println("loaded image");
		temp = (surface.loadImage("sprites/entity-coin.png"));
		temp.resize((int)getWidth(), (int)getHeight());
		setSprite(temp);
	}
	
}
