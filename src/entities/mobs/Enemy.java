package entities.mobs;

import java.util.ArrayList;

import entities.Entity;
import entities.projectiles.Bullet;
import processing.core.PImage;

/**
 * The Enemy class represents an enemy with all the properties of the Entity class
 * Enemies have the ability to shoot and move in their own specific patterns
 * @author nathangu
 */
public class Enemy extends Entity{

	private ArrayList<Entity> daList;
	
	/**
	 * Creates a new instance of Enemy
	 * @param x X-coordinate
	 * @param y Y-coordinate
	 * @param w Width of the hitbox
	 * @param h Height of the hitbox
	 * @param circle If the hitbox is a circle
	 */
	public Enemy(double x, double y, double w, double h, boolean circle) {
		super(x, y, w, h, circle);
	}
	
	/**
	 * Allows the Enemy to fire Bullet
	 */
	public void shoot(){
		
	}
	
	/**
	 * Sets the current Entity list
	 * @param e The new list of entities
	 */
	public void setEntityList(ArrayList<Entity> e) {
		daList = e;
	}
	
	/**
	 * @return The list with the entities
	 */
	public ArrayList<Entity> getDaList() {
		return daList;
	}
	/**
	 * Runs an enemy action
	 */
	public void act() {
		super.act();
	}
}
