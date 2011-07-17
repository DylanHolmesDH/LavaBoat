package me.KillerSmurf.LavaBoat;

import org.bukkit.entity.Boat;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityListener;

public class LBEL extends EntityListener {
	LavaBoat lb;
	
	public LBEL(LavaBoat instance) {
		lb = instance;
	}
	
	public void onEntityCombust(EntityCombustEvent event) {
		if (event.getEntity() instanceof Boat)
		{
			event.setCancelled(true);
		}	
		if(event.getEntity() instanceof Player)
		{
			Player player=(Player) event.getEntity();
			if(player.getVehicle() instanceof Boat&&lb.boats.contains(player.getVehicle()))
			{			
				event.setCancelled(true);
				player.setFireTicks(0);
			}
		}
	}
	
	public void onEntityDamage(EntityDamageEvent event) {
		if (!(event.getEntity() instanceof Player)) return;
		Player player=(Player)event.getEntity();
		if (player.getVehicle() instanceof Boat&&lb.boats.contains(player.getVehicle())) {
			event.setCancelled(true);
		}
	}
}