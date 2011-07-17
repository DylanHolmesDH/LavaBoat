package me.KillerSmurf.LavaBoat;

import org.bukkit.Location;
import org.bukkit.entity.Boat;
import org.bukkit.event.Event;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerListener;

public class LBPL extends PlayerListener
{
	LavaBoat plugin;
	public LBPL(LavaBoat plugin)
	{
		this.plugin=plugin;
	}
	public void onPlayerInteract(PlayerInteractEvent e)
	{
		if(e.hasItem()&&e.getAction()==Action.RIGHT_CLICK_BLOCK)
		{
			if(e.getItem().getTypeId()==333&&e.getItem().getDurability()==1)
			{
				e.setUseItemInHand(Event.Result.DENY);
				Location block=e.getClickedBlock().getLocation();
				Location loc=new Location(block.getWorld(),block.getX(),block.getY()+1,block.getZ());
				plugin.boats.add(loc.getWorld().spawn(loc, Boat.class));
				e.getItem().setAmount(0);
			}			
		}
	}
}
