package es.pcv.game;

import javax.swing.JFrame;

import es.pcv.core.render.Render;
import es.pcv.core.render.RenderDefault;
import es.pcv.core.updater.Updater;
import es.pcv.core.updater.UpdaterDefault;
import es.pcv.game.elements.Player;
import es.pcv.game.elements.TestElement;

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

	    frame.setSize(300, 300);
	    frame.setVisible(true);
	    frame.add(render);
	}
	
	public void startGame(){
		TestElement tel=new TestElement();
		Player pl=new Player(frame);
		updater.add(tel);
		render.add(tel);
		
		updater.add(pl);
		render.add(pl);
		updater.start();
		new Thread(new Runnable() {
			public void run() {
				render.render();
			}
		}).start();
		
	}
	
	public static Game getGame(){
		return game;
	}

}
