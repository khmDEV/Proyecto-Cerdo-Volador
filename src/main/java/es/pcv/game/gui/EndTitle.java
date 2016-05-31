package es.pcv.game.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import es.pcv.core.render.figure.Drawable;

public class EndTitle implements Drawable {

	private Color color=new Color(255, 0, 0);
	private Font font=new Font("Monospaced", Font.BOLD, 36);

	public boolean isDead() {
		return false;
	}

	public boolean kill() {
		return false;
	}

	public void draw(Graphics g) {
		g.setColor(color);
		g.setFont(font);
		g.drawString("You're death", (int)Math.round(20),
				(int)Math.round(50));
		
	}

}
