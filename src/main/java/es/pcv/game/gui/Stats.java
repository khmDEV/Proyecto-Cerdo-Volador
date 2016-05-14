package es.pcv.game.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import es.pcv.core.render.ObjectIcon;
import es.pcv.core.render.Point2D;
import es.pcv.core.render.auxiliar.PolygonHelper;
import es.pcv.core.render.figure.Drawable;
import es.pcv.game.Game;
import es.pcv.game.configuration.Config;
import es.pcv.game.elements.player.Player;

public class Stats implements Drawable {

	private Player pl;
	private Font font=new Font(Font.SANS_SERIF, Font.PLAIN, 20);
	
	private Color color=new Color(255,0,0);
	private Color black=new Color(0,0,0);
	
	
	private BufferedImage[] weapon;
	
	public Stats(Player pl) {
		this.pl = pl;
		
		weapon=new BufferedImage[3];
		File f = new File("pistola.jpg");
		try {
			weapon[0] = ImageIO.read(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		f = new File("espada.jpg");
		try {
			weapon[1] = ImageIO.read(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		f = new File("escopeta.png");
		try {
			weapon[2] = ImageIO.read(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean isDead() {
		return false;
	}

	public boolean kill() {
		return false;
	}

	
	
	public void draw(Graphics g) {
		g.setColor(color);
		g.fillRect(5, 5, 50*pl.getLive() , 20);
		g.setColor(black);
		g.drawRect(5, 5, 500, 20);
		g.drawRect(4, 4, 502, 22);
		
		g.drawImage(weapon[pl.getCurrentWeapon()], 10, 40 , 100 ,100,null);
		for (int i=0;i<3;i++){
			g.setColor(black);
			g.drawImage(weapon[i], 120 + (i*40), 40 , 30 ,30,null);
			Integer num=i;
			char[] number=num.toString().toCharArray();
			g.drawChars(number, 0, 1, 130 + (i*40), 85);
			if(i==pl.getCurrentWeapon()){
				g.setColor(color);
				g.drawRect(120 + (i*40), 40 , 30 ,30);
			}
		}
		//String s="Live:" + pl.getLive() + "/" + pl.getMaxLive();
		//g.drawString(s, 0,20);
	}

}
