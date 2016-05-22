package es.pcv.core.updater.elements;

import java.awt.Graphics;

import es.pcv.core.render.ObjectIcon;
import es.pcv.core.render.Point2D;
import es.pcv.game.configuration.Config;

public abstract class Walker extends LiveEntity{
	
	protected ObjectIcon icon = new ObjectIcon(Config.RESOURCES_PATH+"/icons/bad1.png", 4, 3);
	
	protected int movYImg = 0;
	protected int movXImg = 0;
	protected int mov = 0;
	protected int imgFija = 1;
	protected int img = 0;
	
	
	public Walker(Point2D p, Point2D v, Point2D s, int l, int d) {
		super(p, v, s, l, d);
		
	}
	
	public void draw(Graphics g) {
		if (!isVulnerable() && System.currentTimeMillis() % 8 < 5) {
			return;
		}

		if (movXImg == 0 && movYImg == 0) {
			img = imgFija;
		} else if (movXImg == -1) {
			img = 3 + mov;
			mov++;
			if (mov == 3) {
				mov = 0;
			}
		} else if (movXImg == 1) {
			img = 6 + mov;
			mov++;
			if (mov == 3) {
				mov = 0;
			}
		} else if (movYImg == -1) {
			img = 0 + mov;
			mov++;
			if (mov == 3) {
				mov = 0;
			}
		} else if (movYImg == 1) {
			img = 9 + mov;
			mov++;
			if (mov == 3) {
				mov = 0;
			}
		}		

		g.drawImage(icon.getImage(img), getX(),getY(),getSizeX(),getSizeY(),null);

	}
	
	
	public int getDir(){
		return img/3;
	}
}
