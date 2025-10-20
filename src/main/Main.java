package main;

import java.awt.Dimension;
import game.DrawingSurface;
import javax.swing.JFrame;

import processing.awt.PSurfaceAWT;
import processing.core.PApplet;

public class Main {

	public static void main(String[] args) {
		DrawingSurface drawing = new DrawingSurface(1200,900);
		PApplet.runSketch(new String[]{""}, drawing);
		PSurfaceAWT surf = (PSurfaceAWT) drawing.getSurface();
		PSurfaceAWT.SmoothCanvas canvas = (PSurfaceAWT.SmoothCanvas) surf.getNative();
		JFrame window = (JFrame)canvas.getFrame();

		window.setSize(1200, 900);
		window.setLocationRelativeTo(null);
		window.setMinimumSize(new Dimension(1200,900));
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(true);
		window.setVisible(true);
		
		
		canvas.requestFocus();
	}

}
