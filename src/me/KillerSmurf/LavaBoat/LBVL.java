package me.KillerSmurf.LavaBoat;

import org.bukkit.Material;
import org.bukkit.craftbukkit.entity.CraftBoat;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Player;
import org.bukkit.event.vehicle.VehicleDamageEvent;
import org.bukkit.event.vehicle.VehicleEnterEvent;
import org.bukkit.event.vehicle.VehicleExitEvent;
import org.bukkit.event.vehicle.VehicleListener;
import org.bukkit.event.vehicle.VehicleMoveEvent;
import org.bukkit.util.Vector;

public class LBVL extends VehicleListener {
	LavaBoat lb;
	
	public LBVL(LavaBoat instance) {
		lb = instance;
	}
	
	public void onVehicleDamage(VehicleDamageEvent event)
	{
		if(event.getVehicle() instanceof Boat&&lb.boats.contains(event.getVehicle())&&event.getAttacker()==null)
		{
			event.setCancelled(true);
			event.getVehicle().setFireTicks(0);
		}
	}

	public void onVehicleMove(VehicleMoveEvent e)
	{
		
		if(e.getVehicle() instanceof Boat)
		{
			Vector vect=e.getVehicle().getVelocity();
			int y=0;
			Material mat=e.getVehicle().getLocation().getWorld().getBlockAt(e.getVehicle().getLocation()).getType();
			if(mat==Material.LAVA||mat==Material.STATIONARY_LAVA)
			{
				y=1;
			}
			e.getVehicle().setVelocity(new Vector(vect.getX(),y,vect.getZ()));
		}
	}
}
