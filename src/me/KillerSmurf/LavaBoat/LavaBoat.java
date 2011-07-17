package me.KillerSmurf.LavaBoat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;

public class LavaBoat extends JavaPlugin {
	Logger log = Logger.getLogger("Minecraft");
	public LBVL vl = new LBVL(this);
	public LBEL el = new LBEL(this);
	public LBPL pl = new LBPL(this);
	public Server server;
	
	public ArrayList boats=new ArrayList();
	
	public boolean iConomy=false;
	public boolean permissions=false;
	public int price=150;
	
	public PermissionHandler permissionHandler;
	
	public void onDisable() {
		log.info("[LavaBoat] Disabled!");
		
	}

	public void onEnable() {
		server=getServer();		
	    PluginManager pm = getServer().getPluginManager();
		pm.registerEvent(Event.Type.VEHICLE_DAMAGE, vl, Event.Priority.Normal, this);
		pm.registerEvent(Event.Type.VEHICLE_MOVE, vl, Event.Priority.Normal, this);
		pm.registerEvent(Event.Type.ENTITY_DAMAGE, el, Event.Priority.Normal, this);
		pm.registerEvent(Event.Type.ENTITY_COMBUST, el, Event.Priority.Normal, this);
		pm.registerEvent(Event.Type.PLAYER_INTERACT, pl, Event.Priority.Normal, this);
		setupConfig();
		setupPermissions();
		setupIconomy();
		log.info("[LavaBoat] Enabled!");
	}
	public boolean onCommand(CommandSender sender,Command cmd,String commandLabel,String[] args)
	{
		Player player;
		try
		{
			player=(Player)sender;
		}
		catch(Exception e)
		{
			sender.sendMessage(ChatColor.DARK_RED+"You need to be in-game to use that!");
			return true;
		}
		if(player.getItemInHand().getTypeId()!=333)
		{
			sender.sendMessage(ChatColor.DARK_RED+"That's not a boat, you moron!");
			return true;
		}
		if(permissions)
		{
			if(!permissionHandler.has(player,"LavaBoat"))
			{
				player.sendMessage(ChatColor.DARK_RED+"You don't have permission to use that!");
				return true;
			}
		}
		if(iConomy)
		{
			if(!com.iConomy.iConomy.hasAccount(player.getName()))
			{
				player.sendMessage(ChatColor.DARK_RED+"You don't have an account!");
				return true;
			}
			else if(!com.iConomy.iConomy.getAccount(player.getName()).getHoldings().hasEnough(price))
			{
				player.sendMessage(ChatColor.DARK_RED+"You don't have enough money! You need "+price);
				return true;
			}
			else
			{
				player.sendMessage(ChatColor.GREEN+"LavaBoat bought!");
				player.sendMessage(ChatColor.GREEN+""+price+" subtracted from your account");
				com.iConomy.iConomy.getAccount(player.getName()).getHoldings().subtract(price);
				player.getItemInHand().setDurability((short)1);
				return true;
			}
		}
		else
		{
			player.sendMessage(ChatColor.GREEN+"Your boat is now lava-proof!");
			player.getItemInHand().setDurability((short)1);
			return true;
		}
	}
	private void setupPermissions() 
	{
		Plugin permissionsPlugin = server.getPluginManager().getPlugin("Permissions");
	    if (this.permissionHandler == null) {
	    	if (permissionsPlugin != null) {
	    		this.permissionHandler = ((Permissions) permissionsPlugin).getHandler();
	            permissions=true;
	        } else {
	        	log.info("[LavaBoat] Permission system not detected, defaulting to everyone");	               
	        }
	    }
	}
	private void setupIconomy()
	{
		Plugin iconomy=server.getPluginManager().getPlugin("iConomy");
		if(iconomy!=null)
		{
			iConomy=true;
		}
		else
		{
			log.info("[LavaBoat] iConomy not detected, LavaBoats free");
		}
	}
	
	public static String mainDir="plugins/LavaBoat";
	public static File config=new File(mainDir+File.separator+"config.txt");
	public static Properties prop=new Properties();
	
	private void setupConfig()
	{
		new File(mainDir).mkdir();
		if(!config.exists())
		{
			try
			{
				log.info("[LavaBoat] Creating config file...");
				config.createNewFile();
				FileOutputStream out=new FileOutputStream(config);
				prop.put("Price", "150");
				prop.store(out,"Edit to change the price of boats in-game, only if you have iConomy");
				log.info("[LavaBoat] Config created!");
			}
			catch(IOException e)
			{
				e.printStackTrace();
				log.info("[LavaBoat] Could not create config, using defaults");
				return;
			}
		}
		try
		{
			FileInputStream in=new FileInputStream(config);
			prop.load(in);
			price=Integer.parseInt(prop.getProperty("Price"));
		}
		catch(IOException e)
		{
			e.printStackTrace();
			log.info("[NeoMeteors] Could not load config file. Try reloading");	
		}
	}
}
