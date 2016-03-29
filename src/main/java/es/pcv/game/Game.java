package es.pcv.game;

import javax.swing.JFrame;

import es.pcv.core.render.Render;
import es.pcv.core.render.RenderDefault;
import es.pcv.core.updater.Updater;
import es.pcv.core.updater.UpdaterDefault;
import es.pcv.game.configuration.Config;
import es.pcv.game.elements.enemies.EnemyMelee;
import es.pcv.game.elements.player.Player;
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
	    
	    frame.setSize(Math.round(Config.size.getX()), Math.round(Config.size.getY()));
	    frame.setVisible(true);
	    frame.add(render);
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void startGame(){
		EnemyMelee tel=new EnemyMelee();
		Player pl=new Player(frame);
		updater.add(tel);
		render.add(tel);
		
		updater.add(pl);
		render.add(pl);
		updater.start();
		Stats st=new Stats(pl);
		render.add(st);
		new Thread(new Runnable() {
			public void run() {
				render.render();
			}
		}).start();
		
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
