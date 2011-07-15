package me.KillerSmurf.LavaBoat;

import java.util.logging.Logger;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.Event;

public class LavaBoat extends JavaPlugin {
	Logger log = Logger.getLogger("Minecraft");
	public static LBVL vl;
	public static LBEL el;
	
	public Boolean BOATISINVINCIBLE = false;
	public Boolean ISINBOAT = false;
	
	public void onDisable() {
		log.info("[LavaBoat] Disabled!");
		
	}

	public void onEnable() {
		el = new LBEL(this);
		vl = new LBVL(this);
	    PluginManager pm = getServer().getPluginManager();
		pm.registerEvent(Event.Type.VEHICLE_DAMAGE, vl, Event.Priority.Normal, this);
		pm.registerEvent(Event.Type.VEHICLE_EXIT, vl, Event.Priority.Normal, this);
		pm.registerEvent(Event.Type.VEHICLE_ENTER, vl, Event.Priority.Normal, this);
		pm.registerEvent(Event.Type.ENTITY_DAMAGE, el, Event.Priority.Normal, this);
		pm.registerEvent(Event.Type.ENTITY_COMBUST, el, Event.Priority.Normal, this);
		log.info("[LavaBoat] Enabled!");
	}
}
