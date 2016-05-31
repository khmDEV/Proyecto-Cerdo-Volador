package es.pcv.game.elements.weapons.melee;

import es.pcv.core.render.Point2D;
import es.pcv.core.sound.SoundPlayer;
import es.pcv.core.updater.elements.Collisionable;
import es.pcv.core.updater.elements.Walker;
import es.pcv.game.elements.enemies.Enemy;
import es.pcv.game.elements.player.Player;

public class Sword extends Melee {

	public Sword(Walker w, int dur, int damage) {
		super(w,new Point2D(0.01f,0.05), dur, damage);
	}
	
	private static final String sound="fx/Knife_SL-Derka_De-8768_hifi.mp3";
	private static final long CD_SOUND=400;
	private long last=0;
	public void collision(Collisionable col) {
		super.collision(col);
		if (whoAttack != col && (col instanceof Player && whoAttack instanceof Enemy)
				|| (col instanceof Enemy && whoAttack instanceof Player)) {
			if (System.currentTimeMillis()-last>CD_SOUND) {
				SoundPlayer.playInThreath(sound);
				last=System.currentTimeMillis();
			}
		}
	}
}
