package entities.projectiles;

import entities.Entity;
import entities.mobs.Player;
import processing.core.PApplet;
import processing.core.PImage;

/**
 * Trap represents a Bullet that Players can summon on the other player's field of play
 * Trap contains the properties of Bullet as well 
 * @author Johnny Zhang
 *
 */
public class Trap extends Bullet {
	//1 = hurt, 2 = slow, 3 = subtractCoins
	private boolean available, cost;
	private int type;
	private static PImage thisSprite;
	
	/**
	 * Creates a new instance of Trap
	 * @param x X-Coordinate
	 * @param y Y-Coordinate
	 * @param w Width of hitbox
	 * @param h Height of hitbox
	 * @param vx X velocity
	 * @param vy Y velocity
	 * @param type The type of trap
	 * @param circle If the hitbox is a circle
	 * @param dmg Damage of the Trap
	 */
	public Trap(double x, double y, double w, double h, double vx, double vy, int type, boolean circle, double dmg) {
		super(x, y, w, h, vx, vy, circle, false, dmg);
		this.type = type;
	}
	
	/**
	 * Determines the type of Trap and applies the effects on the specified Entity
	 * @param e The Entity that receives the trap effects
	 */
	public void interact(Entity e) {
		if (!(e instanceof Player)) return;
		Player player = (Player)e;
		if (type == 1) {
			player.setHp(player.getHp()-getDmg());
		}
		if (type == 2) {
			player.setMovementSpeed(0.5);
		}
		if (type == 3) {
			player.addCoins(-10);
		}
		die();
	}
	
	/**
	 * Deploys a trap
	 * @param t The type of Trap
	 */
	public void use(int t) {
		
	}
	
	/**
	 * Sets up the image for the Trap
	 * @param surface The drawing surface
	 */
	public void setup(PApplet surface) {
		if (thisSprite == null)
			System.out.println("loaded image");
			String str = "sprites/trap-";
			str += type;
			str += ".png";
			thisSprite = (surface.loadImage(str));
			thisSprite.resize((int)getWidth(), (int)getHeight());
		setSprite(thisSprite);
	}
}
