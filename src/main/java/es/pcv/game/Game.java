package es.pcv.game;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

import es.pcv.core.render.Point2D;
import es.pcv.core.render.Render;
import es.pcv.core.render.RenderDefault;
import es.pcv.core.updater.Updater;
import es.pcv.core.updater.UpdaterDefault;
import es.pcv.core.updater.elements.Element;
import es.pcv.game.configuration.Config;
import es.pcv.game.elements.player.Player;
import es.pcv.game.elements.scene.Maps;
import es.pcv.game.gui.EndTitle;
import es.pcv.game.gui.Stats;

public class Game {
	private static Game game;
	public Updater updater;
	public Render render;
	private JFrame frame;
	
	public Game(){
		game=this;
		updater=new UpdaterDefault();
		render=new RenderDefault();
	    frame = new JFrame("DrawPanel");
	    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	    Config.scale=new Point2D(screenSize.getHeight()*0.8, screenSize.getHeight()*0.8);
	    frame.setBounds(0,0,screenSize.width, screenSize.height);
	    frame.setVisible(true);
	    frame.add(render);

	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void startGame(){
		
		Player pl=new Player(new Point2D(0.8f, 0.5f),frame);
		new Maps(updater,render,pl);
		//Sword sword=new Sword(pl,pl.getPos().clone(), 1, 70, 2);
		addElement(pl);
		
		//addElement(sword);
		Stats st=new Stats(pl);
		render.add(st);
		
		
		updater.start();
		new Thread(new Runnable() {
			public void run() {
				render.render();
			}
		}).start();
		
	}
	
	public void addElement(Element e){
		updater.add(e);
		render.add(e);
	}
	
	
	public static Game getGame(){
		return game;
	}

	public void end() {
		updater.clear();
		render.clear();
		
		render.add(new EndTitle());
	}

}
