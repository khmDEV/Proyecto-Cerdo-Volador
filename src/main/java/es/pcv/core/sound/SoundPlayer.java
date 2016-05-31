package es.pcv.core.sound;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;


import es.pcv.game.configuration.Config;
import javazoom.jl.player.Player;

public class SoundPlayer extends Thread {
	
	public static void playInThreath(String sound){
		final String s=sound;
		new Thread(new Runnable() {
			public void run() {
				play(s);
			}
		}).start();
	}

	public static void play(String sound){
		FileInputStream fis=null;
		try {
			fis = new FileInputStream(Config.RESOURCES_PATH+"/sound/"+sound);
		    Player playMP3 = new Player(fis);
		    
		    playMP3.play();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}finally {
			if (fis!=null) {
				try {
					fis.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	@Override
	public void run() {
		File f=new File(Config.RESOURCES_PATH+"/sound/bso");
		String[]fls=f.list();
		while(true){
			int v=(int) Math.round(Math.random()*fls.length-0.5);
			if (v<fls.length) {
				play("bso/"+fls[v]);
				
			}
		}
	}

}
