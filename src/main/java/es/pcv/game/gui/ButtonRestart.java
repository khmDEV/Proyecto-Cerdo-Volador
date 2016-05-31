package es.pcv.game.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import es.pcv.core.render.Point2D;
import es.pcv.core.render.figure.Drawable;
import es.pcv.game.Game;
import es.pcv.game.configuration.Config;

public class ButtonRestart implements Drawable{

	private JFrame j;
	private Point2D pos;
	private Point2D size;
	private Game g;
	BufferedImage b;
	boolean on = false;
	
	MouseListener ml = new MouseListener() {

		public void mouseReleased(MouseEvent e) {
			Point m = j.getMousePosition();
			if(m.getX() > pos.getX() && m.getX() < (pos.getX() + size.getX()) 
					&& m.getY() > pos.getY() && m.getY() < (pos.getY() + size.getY())){
				restart();
			}
		}

		public void mousePressed(MouseEvent e) {}

		public void mouseExited(MouseEvent e) {}

		public void mouseEntered(MouseEvent e) {}

		public void mouseClicked(MouseEvent e) {}
	};
	
	public ButtonRestart(Game g,JFrame j){
		this.j=j;
		this.pos= new Point2D(500,500);
		this.size= new Point2D(200,50);
		this.g=g;
		File f = new File(Config.RESOURCES_PATH+"/icons/play_again.png");
		try {
			b = ImageIO.read(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		on = true;
		j.addMouseListener(ml);	
	}
	
	
	
	public boolean isDead() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean kill() {
		// TODO Auto-generated method stub
		return false;
	}

	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		
		g.drawImage(b, (int)pos.getX(), (int)pos.getY() , (int)size.getX() ,(int)size.getY(),null);
		
	}
	public void restart(){
		on = false;
		g.render.clear();
		g.updater.clear();
		g.startGame();
	}

}
