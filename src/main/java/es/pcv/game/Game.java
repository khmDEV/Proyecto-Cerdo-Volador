package es.pcv.game;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import es.pcv.core.render.Point2D;
import es.pcv.core.render.Render;
import es.pcv.core.render.Render3D;
import es.pcv.core.render.RenderDefault;
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
import es.pcv.game.gui.WinTitle;

public class Game{
	private static Game game;
	public Updater updater;
	public Render render;
	private SoundPlayer player;
	private Render guirender;
	private boolean debug=false;
	private Maps maps;
	private JFrame frame;
	private JPanel menu;
	private Dimension screenSize;
	private JButton restart;
	//private boolean showRestart = false;
	
	
	public Game(){
		init();
	}
	
	private void init(){
		game = this;
		updater = new UpdaterDefault();
		player=new SoundPlayer();
		frame = new JFrame("Juego");
		
		
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Config.scale=new Point2D(screenSize.getHeight()*0.8, screenSize.getHeight()*0.8);
		//frame.setBounds(0,0,screenSize.width, screenSize.height);
	    frame.setVisible(true);
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
	    
	    menu = new JPanel();
	    
		//Boton 2D
		JButton boton2D = new JButton("Jugar en 2D");
		menu.add(boton2D);

		// Accion del boton de Entrar
		boton2D.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				init2D();
			}
		});
		
		
		//Boton 3D
		JButton boton3D = new JButton("Jugar en 3D");
		menu.add(boton3D);

		// Accion del boton de Entrar
		boton3D.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				init3D();
			}
		});
		
		//Boton salir
		JButton botonSalir = new JButton("Salir");
		menu.add(botonSalir);

		// Accion del boton de Entrar
		botonSalir.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				System.exit(1);
			}
		});
		
		
		restart = new JButton();
		//restart.setPreferredSize(new Dimension(width, 100));
		restart.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				restart();
				frame.setVisible(true);
			}
		});
		restart.setIcon(new ImageIcon(Config.RESOURCES_PATH + "icons/play_again.png"));
		
		//System.out.println("hola");
		
	}
	
	
	public void startMenu(){
		frame.add(menu);
		frame.setVisible(true);
	}
	
	
	public void init2D(){
		frame.remove(menu);
		render=new RenderDefault();
		new Thread(new Runnable() {
			public void run() {
				render.render();
			}
		}).start();

	    player.start();
		updater.start();
		
		frame.add(render,BorderLayout.CENTER);
		render.setFocusable(true);
		render.requestFocusInWindow();
		
		frame.setVisible(true);
		startGame2D();
	}
	
	public void init3D(){
		frame.remove(menu);
	    guirender=new RenderDefault();
		render=new Render3D(screenSize.width, screenSize.height,guirender);
	    player.start();
		updater.start();
		
		frame.add(render,BorderLayout.CENTER);
		
		render.setFocusable(true);
		render.requestFocusInWindow();
		//frame.add(restart,BorderLayout.PAGE_END);
		frame.setVisible(true);
		startGame3D();
	}
	
	
	public void startGame2D(){
		
		Player pl=new Player(new Point2D(0.8f, 0.5f),render);
		maps=new Maps(updater,render,pl);
		addElement(pl);
		
		Stats st=new Stats(pl);
		render.add(st);		
	}
	
	public void startGame3D(){
		
		
		Player pl=new Player(new Point2D(0.8f, 0.5f),render);
		//if (debug) {
			//maps=new MapsDebug(updater,render,pl);
		//}else{
			maps=new Maps(updater,render,pl);
		//}
		addElement(pl);
		Stats st=new Stats(pl);
		guirender.add(st);		
	}
	
	public void restart(){
		System.out.println("reiniciando");
		frame.remove(restart);
		//frame.setVisible(true);
		if(render.is3D()){
			render.clear();
			updater.clear();
			startGame3D();
		}else{
			render.clear();
			startGame2D();
		}
	}
	
	public void addElement(Element e){
		if (!(e instanceof Player)) {
			maps.add(e);
		}
		updater.add(e);
		render.add(e);
	}
	/**
	public void addRestartButton() {
		// esto
		//this.getContentPane().add(butt, BorderLayout.PAGE_END);
		//this.add(butt, BorderLayout.PAGE_END);
		//validate();
		//setVisible(true);
	}*/
	
	
	public static Game getGame(){
		return game;
	}

	public void end(boolean win) {
		updater.clear();
		render.clear();
		if(render.is3D()){
			guirender.clear();
			if(win){
				guirender.add(new WinTitle());
			}else{
				guirender.add(new EndTitle());
			}
		}else{
			if(win){
				render.add(new WinTitle());
			}else{
				render.add(new EndTitle());
			}
		}
		
		//render.end();	
					
		
		frame.add(restart,BorderLayout.PAGE_END);
		frame.setVisible(true);
		//render.addRestartButton();
		//render.dispose();	
		clearRoom();
		//Game g=new Game();
		//g.startGame();
	}
	
	public void clearRoom(){
		maps.clearRoom();
	}

}