package es.pcv.core.updater;


import es.pcv.core.updater.elements.Element;
import es.pcv.game.configuration.Config;

public abstract class Updater extends Thread{
	
	public abstract void add(Element u);
	
	public abstract void remove(Element u);
	
	public abstract void update();
	
	@Override
	public void run(){
		long start=System.currentTimeMillis();
		while (true) {
			if (System.currentTimeMillis()-start>Config.UPDATE_TICK) {
				update();
				start = System.currentTimeMillis();
			}
			
		}
	}
	
}
