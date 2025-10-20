package game;
import java.util.ArrayList;

import entities.mobs.Player;
import processing.core.PApplet;
import processing.core.PImage;

/**
 * Statistics represents the Player's statistics 
 * @author ayu663
 */
public class Statistics {
	private double hp;
	private boolean[] skillsUsable;
	private boolean[] trapsUsable;
	private double progress, coins;
	private int x,y,width,height;
	private Player p;
	private PImage[] powerups, traps;
	
	/**
	 * Constructs an instance of Statistics  
	 * @param p The Player
	 * @param x X-coordinate Statistics box
	 * @param y Y-coordinate Statistics box
	 * @param width Width of the Statistics box
	 * @param height Height of the Statistics box
	 */
	public Statistics(Player p, int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.p = p;
		this.width = width;
		this.height = height;
		powerups = new PImage[6];
		traps = new PImage[3];
		//note: put info in player so below methods dont have to be used
	}
	
	/**
	 * Draws out the Statistics
	 * @param surface The surface to draw on
	 */
	public void draw(PApplet surface) {
		if (powerups[0] == null) {
			for (int i = 1; i < 7; i++) {
				powerups[i-1] = surface.loadImage("sprites/entity-powerup-"+ i + ".png");
				powerups[i-1].resize(30, 30);
			}
		}
		if (traps[0] == null) {
			for (int i = 1; i < 4; i++) {
				traps[i-1] = surface.loadImage("sprites/trap-"+ i + ".png");
				traps[i-1].resize(30, 30);
			}
		}
		surface.fill(100);
		surface.rect(x, y+5, width-10, 30);
		surface.fill(0,255,0);
		if (p.getHp()>150) {
			surface.rect(x+5, y+10, (float)(width-20), 20);
		} else {
			surface.rect(x+5, y+10, (float)((width-20)*p.getHp()/150), 20);
		}
			
		surface.fill(255);
		String s = "HP: " + (int)p.getHp() + "/150";
		s = s + "\nTrap Coins: " + p.numCoins() + "/10";
		s = s + "\nPowerups: \n";
		s = s.substring(0, s.length()-2);
		surface.text(s, x, y+30, width, height);
		
		if (p.numCoins() >= 10) {
			int x = this.x+width-155;
			surface.image(traps[0], x, y+70);
			x+=50;
			surface.image(traps[1], x, y+70);
			x+=50;
			surface.image(traps[2], x, y+70);
			x+=50;
		}
		
		
		int x = 180;
		if (p.multishotActive()) {
			surface.image(powerups[1], x, y+110);
			x+=50;
			//s+="Multishot, ";
		}
		if (p.penetrateActive()) {
			surface.image(powerups[2], x, y+110);
			x+=50;
			//s+="Penetrate, ";
		}
		if (p.rateIncreaseActive()) {
			surface.image(powerups[3], x, y+110);
			x+=50;
			//s+="Increased Rate, ";
		}
		if (p.dmgIncreaseActive()) {
			surface.image(powerups[4], x, y+110);
			x+=50;
			//s+="Increased Damage, ";
		}
		if (p.shieldOn()) {
			surface.image(powerups[5], x, y+110);
			x+=50;
			//s+="Increased Damage, ";
		}
//		surface.text("HP: " + p.getHp(), x, y, width, height);
//		surface.text("HP: " + p.getHp(), x, y, width, height);
	}
//	/**
//	 * Updates the hp by num amount
//	 * @param num The amount the hp increases of decrease by
//	 */
//	public void updateHP(double num) {
//		hp += num;
//	}
//	
//	/**
//	 * Updates the Statistics class to show that a skill is usable
//	 * @param i Number of the skill
//	 * @pre i must be 0 less than the number of trap
//	 */
//	public void makeSkillUsable(int i) {
//		skillsUsable[i] = true;
//	}
//	
//	/**
//	 * Updates the Statistics class to show that a trap is usable
//	 * @param i number of the Trap
//	 * @pre i must be 0 less than the number of trap
//	 */
//	public void makeTrapUsable(int i) {
//		trapsUsable[i] = true;
//	}
//	
//	/**
//	 * Updates the Statistics class to show that a trap is usable
//	 * @param i number of the Trap
//	 * @pre i must be 0 less than the number of trap
//	 */
//	public void useSkill(int i) {
//		skillsUsable[i] = false;
//	}
//	
//	/**
//	 * Updates the Statistics class to show that a trap is usable
//	 * @param i number of the Trap
//	 * @pre i must be 0 less than the number of trap
//	 */
//	public void useTrap(int i) {
//		trapsUsable[i] = true;
//	}


}
