


package entities;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import entities.mobs.Player;
import processing.core.PApplet;
import processing.core.PImage;

/**
 * The Entity class represents an entity with a movement speed, hp, damage, location, and hitbox
 * The entity is drawn using the Processing library
 * It detects collisions between other entities and allows the entity to act with respect to its own unique actions
 * @author nathangu
 */
public class Entity {
	
	private double width, height, x, y, vx, vy, hp, dmg;
	private boolean isCircleHitbox, isDead, isVisible;
	private PImage sprite;
	/**The bounds in which this entity is located (0, 1 are x, y and 2, 3, dim)
	 * 
	 */
	public double[] bounds; //{topLeftX, topLeftY, windowWidth, windowHeight}
	
	/**
	 * Creates a new instance of Entity
	 * @param x X-coordinate
	 * @param y Y-coordinate
	 * @param w Width of the hitbox
	 * @param h Height of the hitbox
	 * @param circle If the hitbox is a circle
	 */
	public Entity(double x, double y, double w, double h, boolean circle) {
		this.x = x; 
		this.y = y; 
		this.width = w; 
		this.height = h;
		isCircleHitbox = circle; 
		vx = 0;
		vy = 0;
		dmg = 0;
		isDead = false;
		isVisible = true;
		bounds = new double[4];
	}
	
	/**
	 * Sets up the sprite of an Entity with an image
	 * @param image Sprite to draw
	 */
	public void setSprite(PImage image) {
		sprite = image;
	}
	
	/**
	 * If the sprite exists
	 * @return True if the sprite exists, false otherwise
	 */
	public boolean spriteExists() {
		return sprite != null;
	}
	
	/**
	 * Returns whether or not the entity is touching another entity
	 * @param e The other Entity
	 * @return True if the two entities touch, false otherwise
	 */
	public boolean isTouching(Entity e) {
		//Rectangle-Rectangle
		//if(!e.getCircleHitbox() && !getCircleHitbox()) {
			if(isPointInside(e.getX(), e.getY()) 
			|| isPointInside(e.getX() + e.getWidth(), e.getY()) 
			|| isPointInside(e.getX() + e.getWidth(), e.getY() + e.getHeight())
			|| isPointInside(e.getX(), e.getY() + e.getHeight())) {
				return true;
			}
			else {
				return false;
			}
		}		
//		//Circle-Circle
//		else if(e.getCircleHitbox() && this.getCircleHitbox()){			
//			double centerDist = Math.sqrt(Math.pow(getX() - e.getX(), 2) + Math.sqrt(Math.pow(getY() - e.getY(), 2)));
//			double radiusDist = getWidth()+ e.getWidth();
//			
//			if(centerDist <= radiusDist) {
//				return true;
//			}
//			else {
//				return false;
//			}
//
//		}
//		//Rectangle-Circle
//		//This is when the pt is top left corner so i need to fix this
//		else if(!this.getCircleHitbox() && e.getCircleHitbox()){
//			double xDist = Math.abs(e.getX() + e.getWidth() / 2 - getX());
//		    double yDist = Math.abs(e.getY() + e.getHeight() / 2 - getY());
//
//		    if (xDist > (getWidth()/2 + e.getWidth())) {
//		    	return false; 
//		    }
//		    
//		    if (yDist > (getHeight()/2 + e.getHeight())){
//		    	return false; 
//		    }
//
//		    if (xDist <= (getWidth()/2)) {
//		    	return true; 
//		    } 
//		    if (yDist <= (getHeight()/2)) { 
//		    	return true; 
//		    }
//
//		    double corner = Math.pow(xDist - getWidth() / 2, 2) +
//		    		Math.pow(yDist - getHeight() / 2, 2);
//		    
//		    return (corner <= (Math.pow(e.getWidth(), 2)));
//		}
//		
//		//Circle-Rectangle
//		//This is when the pt is top left corner so i need to fix this
//		else {
//			double xDist = Math.abs(getX() + getWidth() / 2 - e.getX());
//		    double yDist = Math.abs(getY() + getHeight() / 2- e.getY());
//
//		    if (xDist > (e.getWidth()/2 + getWidth())) {
//		    	return false; 
//		    }
//		    
//		    if (yDist > (e.getHeight()/2 + getHeight())){
//		    	return false; 
//		    }
//
//		    if (xDist <= (getWidth()/2)) {
//		    	return true; 
//		    } 
//		    if (yDist <= (getHeight()/2)) { 
//		    	return true; 
//		    }
//
//		    double corner = Math.pow(xDist - getWidth() / 2, 2) +
//		    		Math.pow(yDist - getHeight() / 2, 2);
//		    
//		    return (corner <= (Math.pow(getWidth(), 2)));
//		}
//	}
	
	
	private boolean isPointInside(double x, double y) {
		
		if(x >= this.getX() && x <= this.getX() + this.getWidth() 
		&& y >= this.getY() && y <= this.getY() + this.getHeight()) {
			return true;
		}
		return false;
	}

	/**
	 * Moves the Entity given its current velocities
	 */
	public void act() {
		x += vx;
		y += vy;
				
		if(bounds[0] <= getX() || getX() >= bounds[0] + bounds[2]) {
			vx = - vx;
			x += vx;
		}		
		if(bounds[1] <= getY() || getY() >= bounds[1] + bounds[3]) {
			vy = - vy;
			y += vy;
		}
	}
	
	/**
	 * Sets up the image for the Entity
	 * @param surface The drawing surface
	 */
	public void setup(PApplet surface) {
		setSprite(surface.loadImage("player1.png"));
	}
	
	
	/**
	 * Draws out the Entity
	 * @param surface The surface to be drawn on
	 * @pre setSprite must be called first
	 */
	public void draw(PApplet surface) {
		surface.image(sprite, (float)x, (float)y);
	}
	
	/**
	 * @return Current X-Coordinate
	 */
	public double getX() {
		return x;
	}
	/**
	 * Sets the X-Coordinate
	 * @param coord New X-Coordinate
	 */
	public void setX(double coord) {
		x = coord;
	}
	/**
	 * @return Current Y-Coordinate
	 */
	public double getY() {
		return y;
	}
	/**
	 * Sets the Y-Coordinate
	 * @param coord New Y-Coordinate
	 */
	public void setY(double coord) {
		y = coord;
	}
	/**
	 * @return Current X-velocity
	 */
	public double getvx() {
		return vx;
	}
	/**
	 * Sets the X-velocity
	 * @param velo New X-velocity
	 */
	public void setvx(double velo) {
		vx = velo;
	}
	/**
	 * @return Current Y-velocity
	 */
	public double getvy() {
		return vy;
	}
	/**
	 * Sets the Y-velocity
	 * @param velo New Y-velocity
	 */
	public void setvy(double velo) {
		vy = velo;
	}
	/**
	 * @return Width of the entity
	 */
	public double getWidth() {
		return width;
	}
	/**
	 * Sets the width
	 * @param width New width
	 */
	public void setWidth(double width) {
		this.width = width;
	}
	/**
	 * @return Height of the Entity
	 */
	public double getHeight() {
		return height;
	}
	/**
	 * Sets the height
	 * @param height New height
	 */
	public void setHeight(double height) {
		this.height = height;
	}
	/**
	 * @return True if hitbox is a circle, false otherwise
	 */
	public boolean getCircleHitbox() {
		return isCircleHitbox;
	}
	/**
	 * @return HP of the Entity
	 */
	public double getHp() {
		return hp;
	}
	/**
	 * Sets the HP of the Entity
	 * @param h New HP value
	 */
	public void setHp(double h) {
		hp = h;
	}
	/**
	 * Sets the damage per bullet of the Entity
	 * @param d New damage value
	 */
	public void setDmg(double d) {
		dmg = d;
	}
	
	/**
	 * @return Current damage per bullet
	 */
	public double getDmg() {
		return dmg;
	}
	
	/**
	 * Allows the entity to act
	 * @param e The Entity
	 */
	public void act(Entity e) {
		
	}
	
	/**
	 * @return If the player is dead
	 */
	public boolean isDead() {
		if(hp <= 0) {
			return true;
		}
		return isDead;
	}
	
	/**
	 * @return If the entity is visible
	 */
	public boolean isVisble() {
		return isVisible;
	}

	/**
	 * Sets the x,y and dimensions of the window boundary
	 * @param topLeft Point on the top left corner
	 * @param dimensions Point on the bottom right corner
	 */
	public void giveBounds(Point2D topLeft, Point2D dimensions) {
		bounds[0] = topLeft.getX();
		bounds[1] = topLeft.getY();
		bounds[2] = dimensions.getX();
		bounds[3] = dimensions.getY();
		
				
	}
	
	/**
	 * Makes the Entity die
	 */
	public void die() {
		isDead = true;
	}

	
	/**
	 * Gets the invincibility frames of the Entity
	 * @return invincibility frames of the Entity
	 */
	public int getInv() {
		return 0;
	}
	
	/**
	 * Sets the invincibility frames of the Entity
	 * @param n Number of frames
	 */
	public void setInv(int n) {
		
	}
}
