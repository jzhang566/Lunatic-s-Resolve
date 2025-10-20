package entities.projectiles;
import entities.Entity;
import entities.mobs.Enemy;
import entities.mobs.Player;
import processing.core.PApplet;
import processing.core.PImage;

/**
 * Projectile represents an Entity that can be interacted with
 * It also contians the properties of Entity as well
 * @author Johnny Zhang
 *
 */
public class Projectile extends Entity {
	private static PImage thisSprite;
	private boolean fromPlayer;
	private boolean penetrate;
	private int inv;
	/**
	 * Creates a new instance of Projectile
	 * @param x X-coordinate
	 * @param y Y-coordinate
	 * @param w Width of the hitbox
	 * @param h Height of the hitbox
	 * @param vx X velocity
	 * @param vy Y velocity
	 * @param circle If the hitbox is a circle
	 * @param p If the Projectile is from the Player
	 * @param dmg Damage of the Projectile
	 */
	public Projectile(double x, double y, double w, double h, double vx, double vy, boolean circle, boolean p, double dmg) {
		super(x, y, w, h, circle);
		setvx(vx);
		setvy(vy);
		setDmg(dmg);
		this.setHp(1);
		fromPlayer = p;
		if (fromPlayer == true) System.out.println("+++++++++++++++++++++++");
		inv = 0;
	}
	
	
	/**
	 * Causes the Entity hit by the Bullet to lose HP
	 * @param e Entity that is interacted with
	 */
	public void interact(Entity e) {
		if (inv <= 1) {
			if (fromPlayer == false && e instanceof Player && e.getInv() <= 0) {
				if (!((Player)(e)).shieldOn()) e.setHp(e.getHp() - getDmg());
				if (penetrate == false) {
					setHp(0);
				}
				e.setInv(10);
			}
			if (fromPlayer == true && e instanceof Enemy) {
				e.setHp(e.getHp() - getDmg());
				if (penetrate == false) {
					setHp(0);
				}
			}
		}
		inv = 10;
	}
	
	/**
	 * Checks if the Projectile is out of bounds
	 * @return boolean If the Projectile is out of bounds, return true, false otherwise
	 */
	public boolean outOfBounds() {
		if (bounds == null) {
			System.out.println("NOBOUDSN");
			return true;
		
		}
		if (bounds[0] < getX() && getX() < bounds[0]+bounds[2] && getY() > bounds[1] && getY() < bounds[1]+bounds[3]) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Allows the Projectile to move
	 */
	public void act() {
		inv--;
		setX(getX()+getvx());
		setY(getY()+getvy());
		if (outOfBounds()) {
			die();
		}
	}
	
	/**
	 * Sets up the image for the trap
	 * @param surface The drawing surface
	 */
	public void setup(PApplet surface) {
		//PImage temp;
		if (thisSprite == null) {
			System.out.println("loaded image");
			if (fromPlayer) {
				thisSprite = (surface.loadImage("sprites/projectile-bullet.png"));
				setWidth(10);
			} else {
				thisSprite = (surface.loadImage("sprites/projectile.png"));
			}
			thisSprite.resize((int)getWidth(), (int)getHeight());
		}
		setSprite(thisSprite);
	}
	
	/**
	 * Sets if Bullet can penetrate
	 * @param b If the Bullet can penetrate
	 */
	public void setPenetrate(boolean b) {
		penetrate = b;
	}
	
	/**
	 * returns whether the bullet is from a player or not. 
	 * @return fromPlayer
	 */
	public boolean getFromPlayer() {
		return fromPlayer;
	}

}
