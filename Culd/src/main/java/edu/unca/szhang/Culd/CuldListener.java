package edu.unca.szhang.Culd;

import java.text.MessageFormat;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.server.PluginEnableEvent;

/*
 * This is a sample event listener
 */
public class CuldListener implements Listener {
    private final Culd plugin;

    /*
     * This listener needs to know about the plugin which it came from
     */
    public CuldListener(Culd plugin) {
        // Register the listener
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        
        this.plugin = plugin;
    }
    
    // sets worldly events to false upon start of plugin
    @EventHandler
    public void worldset(PluginEnableEvent event) {
    	plugin.worldgod.put("Wrath", false);
    	plugin.worldgod.put("Dead", false);    	
    }

    /*
     * Send the sample message to all players that join
     */
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
    	plugin.playerstone.put(event.getPlayer(), false);
        event.getPlayer().sendMessage(this.plugin.getConfig().getString("sample.message"));
    }
    
    /*
     * Another example of a event handler. This one will give you the name of
     * the entity you interact with, if it is a Creature it will give you the
     * creature Id.
     */
    @EventHandler
    public void onPlayerInteract(PlayerInteractEntityEvent event) {
        final EntityType entityType = event.getRightClicked().getType();

        event.getPlayer().sendMessage(MessageFormat.format(
                "You interacted with a {0} it has an id of {1}",
                entityType.getName(),
                entityType.getTypeId()));
    }
    
    // Turns object you touch to stone
	@EventHandler(priority = EventPriority.HIGH)
	public void wall (PlayerInteractEntityEvent event) {
		if (plugin.playerstone.get(event.getPlayer())) {
			Entity animal = event.getRightClicked();
			Location loc= animal.getLocation();		
			
			loc.getWorld().strikeLightningEffect(loc);
			loc.getWorld().getBlockAt(loc).setType(Material.STONE);	
			
			Location newloc = loc;
			newloc.setY(loc.getY() - 12);
			animal.teleport(newloc);
	
			event.getPlayer().sendMessage("Turn to the Wall");
		}
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void raisedead (EntityDeathEvent event) {
		if (plugin.worldgod.get("Dead")) {
			if (!(event.getEntity().getType() == EntityType.ZOMBIE)) {
				Location loc = event.getEntity().getLocation();
				loc.getWorld().spawnEntity(loc, EntityType.ZOMBIE);
		
				Player [] users = Bukkit.getOnlinePlayers();
				for (int i = 0; i < users.length; i++) {
					users[i].sendMessage("Raise Dead occurred");
				}
			}
		}
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void wrath (PlayerMoveEvent event) {
		if (plugin.worldgod.get("Wrath")) {			
			Location playerloc = event.getTo();
			Location checkloc = playerloc;
			checkloc.setY(playerloc.getY() - 1);
			
	        Random randomnum = new Random();        	
			
			
			if (!(checkloc.getBlock().getType() == Material.GRASS)) {
				if (randomnum.nextInt(100) == 7) {
					playerloc.getWorld().strikeLightning(playerloc);
				}
			}
		}
	}	
}    

