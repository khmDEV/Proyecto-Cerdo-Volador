package es.pcv.core.updater;


import java.util.concurrent.Semaphore;

import es.pcv.core.updater.elements.Element;
import es.pcv.game.configuration.Config;
import javazoom.jl.player.Player;

public abstract class Updater extends Thread{
	
	private boolean seguir = true;
	private Semaphore s = new Semaphore(1);
	
	public abstract void add(Element u);
	
	public abstract void remove(Element u);
	
	public abstract void update();
	
	@Override
	public void run(){
		long start=System.currentTimeMillis();
		while (seguir) {
			s.release();
			if (System.currentTimeMillis()-start>Config.UPDATE_TICK) {
				update();
				start = System.currentTimeMillis();
			}
			try {
				s.acquire();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public abstract void clear();
	
	public void end(){
		try {
			s.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		seguir=false;
		s.release();
	}
	
}
