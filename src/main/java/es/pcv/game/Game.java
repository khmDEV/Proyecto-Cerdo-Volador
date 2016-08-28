package es.pcv.game;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
	private Player pl;
	private Render guirender;
	private boolean debug=true;
	private Maps maps;
	private JFrame frame;
	private JPanel menu;
	private JPanel subMenu;
	private JPanel endMenu;
	private Dimension screenSize;
	private JButton restart;
	private JButton exit2;
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
	    frame.setLayout(null);
		frame.getContentPane().setBackground(new Color(0, 0, 0));
		frame.setBackground(new Color(0, 0, 0));

	    GridLayout layout = new GridLayout(3,1);
	    layout.setVgap(10);
	    menu = new JPanel(layout);
	    menu.setBackground(new Color(0, 0, 0));

	    menu.setBounds((int) (0), (int) (0), 
	    		(int)(screenSize.getWidth()), (int)(screenSize.getHeight()));

	    subMenu = new JPanel(layout);
	    subMenu.setBackground(new Color(0, 0, 0));

	    subMenu.setBounds((int) (screenSize.getWidth()*0.4), (int) (screenSize.getHeight()*0.4), 
	    		(int)(screenSize.getWidth()*0.2), (int)(screenSize.getHeight()*0.2));
	    
	    BufferedImage myPicture = null;
		try {
			myPicture = ImageIO.read(new File(Config.RESOURCES_PATH + "/icons/logo.png"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    JLabel picLabel = new JLabel(new ImageIcon(myPicture));
	    menu.add(picLabel);
	    
	    menu.add(subMenu);
	    
	    endMenu = new JPanel();
	    
		//Boton 2D
		JButton boton2D = new JButton("Jugar en 2D");
		subMenu.add(boton2D);

		// Accion del boton de Entrar
		boton2D.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				init2D();
			}
		});
		
		
		//Boton 3D
		JButton boton3D = new JButton("Jugar en 3D");
		subMenu.add(boton3D);

		// Accion del boton de Entrar
		boton3D.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				init3D();
			}
		});
		
		//Boton salir
		JButton botonSalir = new JButton("Salir");
		subMenu.add(botonSalir);

		// Accion del boton de Entrar
		botonSalir.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				System.exit(1);
			}
		});
		
		
		restart = new JButton();
		endMenu.add(restart);
		restart.setPreferredSize(new Dimension(200, 100));
		restart.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				restart();
				frame.setVisible(true);
			}
		});
		restart.setIcon(new ImageIcon(Config.RESOURCES_PATH + "icons/play_again.png"));

		
		
		
		exit2 = new JButton();
		endMenu.add(exit2);
		exit2.setPreferredSize(new Dimension(200, 100));
		exit2.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				
				frame.remove(endMenu);
				frame.remove(render);
				//endMenu.setVisible(false);
				frame.setVisible(false);
				
				
				stop();
				frame.setLayout(null);
				frame.add(menu);
				frame.setVisible(true);
				
				updater = new UpdaterDefault();
				player=new SoundPlayer();
			}
		});
		ImageIcon exit = new ImageIcon(Config.RESOURCES_PATH + "icons/exit.png");
		Image img = exit.getImage();
		Image newimg = img.getScaledInstance(200, 70,  java.awt.Image.SCALE_SMOOTH);
		ImageIcon newIcon = new ImageIcon(newimg);
		exit2.setIcon(newIcon);
		
	}
	
	
	public void startMenu(){
		frame.add(menu);
		//frame.add(new JPanel());
		frame.setVisible(true);
	}
	
	
	public void init2D(){
		frame.setLayout(new BorderLayout());
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
		frame.setLayout(new BorderLayout());
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
		
		pl=new Player(new Point2D(0.8f, 0.5f),render,false);
		if (debug) {
			maps=new MapsDebug(updater,render,pl);
		} else{
			maps=new Maps(updater,render,pl);
		}
		addElement(pl);
		
		Stats st=new Stats(pl);
		render.add(st);		
	}
	
	public void startGame3D(){
		
		
		pl=new Player(new Point2D(0.8f, 0.5f),render,true);
		if (debug) {
			maps=new MapsDebug(updater,render,pl);
		} else{
			maps=new Maps(updater,render,pl);
		}
		addElement(pl);
		Stats st=new Stats(pl);
		guirender.add(st);		
	}
	
	public void restart(){
		System.out.println("reiniciando");
		frame.remove(endMenu);
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
	public void stop(){
		player.end();
		updater.end();
		render.end();
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
	
	public Player getPlayer(){
		return pl;
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
					
		
		
		frame.add(endMenu,BorderLayout.PAGE_END);
		frame.setVisible(true);
		//render.addRestartButton();
		//render.dispose();	
		//clearRoom();
		//Game g=new Game();
		//g.startGame();
	}
	
	public void clearRoom(){
		maps.clearRoom();
	}

}