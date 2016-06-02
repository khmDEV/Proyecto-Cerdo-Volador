package es.pcv.game.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;

import es.pcv.core.render.figure.Drawable;
import es.pcv.game.configuration.Config;
import es.pcv.game.elements.player.Player;
import es.pcv.game.elements.weapons.Weapon;

public class Stats implements Drawable {

	private Player pl;
	private Font font=new Font(Font.SANS_SERIF, Font.PLAIN, 18);
	
	private Color color=new Color(255,0,0);
	private Color black=new Color(0,0,0);

	private BufferedImage[] weapon;
	
	public Stats(Player pl) {
		this.pl = pl;
		
		weapon=new BufferedImage[3];
		File f = new File(Config.RESOURCES_PATH+"/icons/pistola.jpg");
		try {
			weapon[0] = ImageIO.read(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		f = new File(Config.RESOURCES_PATH+"/icons/espada.jpg");
		try {
			weapon[1] = ImageIO.read(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		f = new File(Config.RESOURCES_PATH+"/icons/escopeta.png");
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
		g.setFont(font);
		g.fillRect(5, 5, (int) (2.5*pl.getLive()) , 20);
		g.setColor(black);
		g.drawRect(5, 5, 500, 20);
		g.drawRect(4, 4, 502, 22);
		
		String s="";
		Weapon wp=pl.getWeapon();
		if (wp!=null){
			if( wp.getMaxDurability()!=0) {
				s=wp.getDurability()+"/"+wp.getMaxDurability();
				g.drawChars(s.toCharArray(), 0, s.length(), 20, 65);
			}else{
				g.setFont(new Font(font.getFontName(), font.getStyle(), 44));
				s='\u221e'+"";
				g.drawChars(s.toCharArray(), 0, s.length(), 30, 70);
				g.setFont(font);
			}
		}
			
		
		for (int i=0;i<pl.getWeaponCapacity();i++){
			wp=pl.getWeapon(i);
			g.setColor(black);
			if (wp!=null) {
				g.drawImage(Weapon.ICONS.getImage(wp.getId()), 120 + (i*40), 40 , 30 ,30,null);
				
			}else{
				g.drawRect(120 + (i*40), 40 , 30 ,30);
			}
			if(i==pl.getCurrentWeapon()){
				g.setColor(color);
				g.drawRect(120 + (i*40), 40 , 30 ,30);
			}
			
			Integer num=i+1;
			char[] number=num.toString().toCharArray();
			g.drawChars(number, 0, 1, 130 + (i*40), 85);
			
		}
		//String s="Live:" + pl.getLive() + "/" + pl.getMaxLive();
		//g.drawString(s, 0,20);
	}

	public void draw3d(GL2 gl, GLU glu, GLUquadric quadric) {
		// TODO Auto-generated method stub
		
	}

}
