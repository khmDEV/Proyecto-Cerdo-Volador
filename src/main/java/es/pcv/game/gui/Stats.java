package es.pcv.game.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import es.pcv.core.render.figure.Drawable;
import es.pcv.game.elements.player.Player;

public class Stats implements Drawable {

	private Player pl;
	private Font font=new Font(Font.SANS_SERIF, Font.PLAIN, 20);
	private Color color=new Color(0,0,0);
	public Stats(Player pl) {
		this.pl = pl;
	}

	public boolean isDead() {
		return false;
	}

	public boolean kill() {
		return false;
	}

	public void draw(Graphics g) {
		g.setColor(color);
		g.setFont(font);
		String s="Live:" + pl.getLive() + "/" + pl.getMaxLive();
		g.drawString(s, 0,
				20);
	}

}
