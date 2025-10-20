package game;

import processing.core.PApplet;

public class BossBar {
	private int x, y;
	private int maxhp;
	private double hp;
	private String name;
	private String subtext;
	
	public BossBar(int x, int y, double hp, String n, String s) {
		this.hp = hp;
		name = n;
		subtext = s;
		this.x = x;
		this.y = y;
	}
	
	public void draw(PApplet surface) {
		String str = "[";
		/*int percentage = (int) hp / maxhp;
		for (int i = 0; i < 10 * percentage; i++) {
			str += '|';
		}
		for (int i = 0; i < 100 - 10*percentage; i++) {
			str += ' ';
		}
		str += ']';*/
		surface.text(name, x, y, 300, 50);
		surface.text(str, x, y + 60, 300, 50);
		surface.text(subtext, x, y+120, 200, 40);
	}
	
	
	public void decreaseHp(int n) {
		hp -= n;
	}
	
	
	
}
