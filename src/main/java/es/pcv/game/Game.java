package es.pcv.game;

import java.awt.Dimension;
import java.awt.Toolkit;

import es.pcv.core.render.Point2D;
import es.pcv.core.render.Render3D;
import es.pcv.core.sound.SoundPlayer;
import es.pcv.core.updater.Updater;
import es.pcv.core.updater.UpdaterDefault;
import es.pcv.core.updater.elements.Element;
import es.pcv.game.configuration.Config;
import es.pcv.game.elements.player.Player;
import es.pcv.game.elements.scene.Maps;
import es.pcv.game.elements.scene.MapsDebug;
import es.pcv.game.gui.EndTitle;
import es.pcv.game.gui.GuiDefault;
import es.pcv.game.gui.Stats;

public class Game {
	private static Game game;
	public Updater updater;
	public Render3D render;
	private SoundPlayer player;
	private GuiDefault guirender;
	private boolean debug=true;
	
	public Game(){
		init();
	}
	private Maps maps;
	private void init(){
		game=this;
		updater=new UpdaterDefault();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	    Config.scale=new Point2D(screenSize.getHeight()*0.8, screenSize.getHeight()*0.8);
	    guirender=new GuiDefault();
		render=new Render3D(screenSize.width, screenSize.height,guirender);
	    player=new SoundPlayer();
	    player.start();
		updater.start();
		render.start();
	}
	public void startGame(){
		
		Player pl=new Player(new Point2D(0.8f, 0.5f));
		if (debug) {
			maps=new MapsDebug(updater,render,pl);
		}else{
			maps=new Maps(updater,render,pl);
		}
		addElement(pl);
		Stats st=new Stats(pl);
		guirender.add(st);		
	}
	
	public void addElement(Element e){
		if (!(e instanceof Player)) {
			maps.add(e);
		}
		updater.add(e);
		render.add(e);
	}
	
	
	public static Game getGame(){
		return game;
	}

	public void end() {
		updater.clear();
		//render.end();	
		render.clear();			
		guirender.clear();
		guirender.add(new EndTitle());
		render.addRestartButton();
		//render.dispose();	
		clearRoom();
		//Game g=new Game();
		//g.startGame();
	}
	
	public void clearRoom(){
		maps.clearRoom();
	}

}