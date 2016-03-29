package es.pcv.core.updater.elements;

public interface hasLive {
	public int getLive();
	
	public int getMaxLive();
	
	public int addLive(int nl);
	
	public int addMaxLive(int nl);
	
	public int setMaxLive(int nl);
	
	public int doDamage(int d);
	
	public boolean isVulnerable();
}
