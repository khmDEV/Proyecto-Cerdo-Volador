package es.pcv.game.elements.scene;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import es.pcv.core.render.Point2D;
import es.pcv.core.render.Render;
import es.pcv.core.updater.Updater;
import es.pcv.core.updater.elements.Element;
import es.pcv.game.elements.enemies.Boss;
import es.pcv.game.elements.enemies.EnemyAnnoying;
import es.pcv.game.elements.enemies.EnemyMelee;
import es.pcv.game.elements.enemies.EnemyShoter;
import es.pcv.game.elements.enemies.EnemyWall;
import es.pcv.game.elements.items.ItemAmmo;
import es.pcv.game.elements.items.ItemHeal;
import es.pcv.game.elements.items.ItemWeapon;
import es.pcv.game.elements.player.Player;
import es.pcv.game.elements.weapons.AllDirectionsWeapon;
import es.pcv.game.elements.weapons.GunDefault;
import es.pcv.game.elements.weapons.LaserBrimstone;
import es.pcv.game.elements.weapons.LaserGun;
import es.pcv.game.elements.weapons.RepeatGun;
import es.pcv.game.elements.weapons.ScabbardAreaSword;
import es.pcv.game.elements.weapons.ScabbardSword;
import es.pcv.game.elements.weapons.Shotgun;
import es.pcv.game.elements.weapons.Weapon;

public class MapsDebug extends Maps{
	
	
	public MapsDebug(Updater u, Render r,Player p){
		updater=u;
		render=r;
		player = p;
		currentMap=0;
		maps=new ArrayList<Map>();
		
		
		Weapon[] ALL_WEAPONS={new GunDefault(p),new LaserBrimstone(p),new LaserGun(p),new RepeatGun(p),new ScabbardSword(p),new ScabbardAreaSword(p),new Shotgun(p), new AllDirectionsWeapon(p)};
		
		p.setWeapons(ALL_WEAPONS);
			
		Map map0=map0(new Integer[]{1}, new Integer[]{0}, 0f, false);
		map0.addElement(new ItemWeapon(new Point2D(0.3f, 0.4f),new GunDefault(null)));
		map0.addElement(new ItemWeapon(new Point2D(0.5f, 0.4f),new Shotgun(null)));
		map0.addElement(new ItemWeapon(new Point2D(0.7f, 0.4f),new ScabbardSword(null)));

		map0.addElement(new ItemWeapon(new Point2D(0.3f, 0.6f),new ScabbardAreaSword(null)));
		map0.addElement(new ItemWeapon(new Point2D(0.5f, 0.6f),new AllDirectionsWeapon(null)));
		map0.addElement(new ItemWeapon(new Point2D(0.7f, 0.6f),new LaserBrimstone(null)));
		
		map0.addElement(new ItemWeapon(new Point2D(0.3f, 0.8f),new LaserGun(null)));
		map0.addElement(new ItemWeapon(new Point2D(0.5f, 0.8f),new RepeatGun(null)));
		EnemyMelee em=new EnemyMelee(new Point2D(0.7f, 0.8f), p,new Point2D(0, 0));em.doDamage(em.getMaxLive()-1);
		map0.addElement(em);

		map0.addElement(new ItemHeal(new Point2D(0.1f, 0.2f), 1));
		map0.addElement(new ItemHeal(new Point2D(0.1f, 0.3f), 10));
		map0.addElement(new ItemHeal(new Point2D(0.1f, 0.4f), 20));
		map0.addElement(new ItemHeal(new Point2D(0.1f, 0.5f), 200));
		
		map0.addElement(new ItemAmmo(new Point2D(0.1f, 0.6f), 1));
		map0.addElement(new ItemAmmo(new Point2D(0.1f, 0.7f), 10));
		map0.addElement(new ItemAmmo(new Point2D(0.1f, 0.8f), 20));
		map0.addElement(new ItemAmmo(new Point2D(0.1f, 0.9f), 200));

		maps.add(map0);
		
		int i=2;
		Map map;
		
		map=map1(new Integer[]{0,i}, new Integer[]{0,0}, 0f, false);
		map.addElement(new EnemyAnnoying(new Point2D(0.5, 0.5), p));
		maps.add(map);
		i++;
		
		map=map1(new Integer[]{0,i}, new Integer[]{0,0}, 0f, false);
		map.addElement(new EnemyMelee(new Point2D(0.5, 0.5), p));
		maps.add(map);
		i++;
		
		
		
		map=map1(new Integer[]{0,i}, new Integer[]{0,0}, 0f, false);
		map.addElement(new EnemyMelee(new Point2D(0.5, 0.5), p,new Point2D(0.2f, 0.2f)));
		maps.add(map);
		i++;
		
		map=map1(new Integer[]{0,i}, new Integer[]{0,0}, 0f, false);
		map.addElement(new EnemyShoter(new Point2D(0.5, 0.5), p,new Point2D(0, 0)));
		maps.add(map);
		i++;
		
		map=map1(new Integer[]{0,i}, new Integer[]{0,0}, 0f, false);
		map.addElement(new EnemyShoter(new Point2D(0.5, 0.5), p,new Point2D(0.12f, 0.12f)));
		maps.add(map);
		i++;
		
		map=map1(new Integer[]{0,i}, new Integer[]{0,0}, 0f, false);
		List<Element>els=new LinkedList<Element>();
		for (Element e:map.elements) {
			if (e instanceof Wall) {
				els.add(new EnemyWall((Wall) e, p));
			}
		}
		for (Element element : els) {
			map.addElement(element);
		}
		maps.add(map);
		i++;
	
		
		
		map=map1(new Integer[]{0,0}, new Integer[]{0,0}, 0f, false);
		map.addElement(new Boss(new Point2D(0.5, 0.5), p, new Point2D(0.2f, 0.2f)));
		maps.add(map);
		i++;
		
		
		map0.show();
		
	}
	
}
