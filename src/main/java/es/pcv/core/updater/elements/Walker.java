package es.pcv.core.updater.elements;

import java.awt.Graphics;

import es.pcv.core.render.ObjectIcon;
import es.pcv.core.render.Point2D;
import es.pcv.game.configuration.Config;
import es.pcv.game.elements.player.Player;

public abstract class Walker extends LiveEntity {

	protected ObjectIcon icon;
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
		if (velocity.value()!=0) {
			switch (getDir()) {
			case 0:
				movYImg = -1;
				movXImg = 0;
				imgFija = 1;
				break;
			case 1:
				movXImg = -1;
				movYImg = 0;
				imgFija = icon.w+1;
				break;
			case 2:
				movXImg = 1;
				movYImg = 0;
				imgFija = icon.w*2+1;
				break;
			case 3:
				movYImg = 1;
				movXImg = 0;
				imgFija = icon.w*3+1;
				break;
	
			default:
				break;
			}
		}else{
			movXImg = 0;
			movYImg = 0;
		}

		if (movXImg == 0 && movYImg == 0) {
			img = imgFija;
		} else if (movXImg == -1) {
			img = icon.w + mov;
			mov++;
			if (mov == icon.w) {
				mov = 0;
			}
		} else if (movXImg == 1) {
			img = icon.w*2 + mov;
			mov++;
			if (mov == icon.w) {
				mov = 0;
			}
		} else if (movYImg == -1) {
			img = 0 + mov;
			mov++;
			if (mov == icon.w) {
				mov = 0;
			}
		} else if (movYImg == 1) {
			img = icon.w*3 + mov;
			mov++;
			if (mov == icon.w) {
				mov = 0;
			}
		}

		g.drawImage(icon.getImage(img), getX(), getY(), getSizeX(), getSizeY(), null);

	}

	private int dir = 0;

	public int getDir() {
		if (Math.abs(velocity.getX()) > Math.abs(velocity.getY())) {
			if (velocity.getX() > 0) {
				dir = 2;
			} else {
				dir = 1;
			}
		} else if (Math.abs(velocity.getX()) < Math.abs(velocity.getY())) {
			if (velocity.getY() > 0) {
				dir = 0;
			} else {
				dir = 3;
			}
		}
		return dir;
	}
}
