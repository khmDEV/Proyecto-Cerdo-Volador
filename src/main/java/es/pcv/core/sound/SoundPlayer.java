package es.pcv.core.sound;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

import es.pcv.game.configuration.Config;
import javazoom.jl.player.Player;

public class SoundPlayer extends Thread {
	
	private boolean seguir = true;
	private Semaphore s = new Semaphore(1);
	public static Player mainPlayMP3;
	public static void playInThreath(String sound){
		final String s=sound;
		new Thread(new Runnable() {
			public void run() {
				play(s,false);
			}
		}).start();
		
	}

	public static void play(String sound,boolean main){
		FileInputStream fis=null;
		try {
			fis = new FileInputStream(Config.RESOURCES_PATH+"/sound/"+sound);
		    Player playMP3 = new Player(fis);
		    if(main){
		    	mainPlayMP3 = playMP3;
		    }
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
		//hilos = new ArrayList<Thread>();
		
		
		File f=new File(Config.RESOURCES_PATH+"/sound/bso");
		String[]fls=f.list();
		while(seguir){
			s.release();
			int v=(int) Math.round(Math.random()*fls.length-0.5);
			if (v<fls.length) {
				play("bso/"+fls[v],true);
					
			}

			try {
				s.acquire();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public void end(){
		try {
			s.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		seguir=false;
		s.release();
		SoundPlayer.mainPlayMP3.close();
	}

}
