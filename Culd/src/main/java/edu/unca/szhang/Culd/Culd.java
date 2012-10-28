package edu.unca.szhang.Culd;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.java.JavaPlugin;

/*
 * This is the main class of the sample plug-in
 */
public class Culd extends JavaPlugin {		
	
	CuldLogger log;
	
	// Map (to be Hashmap) that uses String and Boolean, when listener activates, 
	// it checks if a certain word=setting is true
	public Map <String, Boolean> worldgod;	
	
	// Map that stores keys of each player, when true, they have the ability to petrify entities
	// It is declared false when player log-ins
	public Map <Player, Boolean> playerstone;
	
    /*
     * This is called when your plug-in is enabled
     */
    @Override
    public void onEnable() {
    	log = new CuldLogger(this);
    	log.info("Plugin has been enabled");    	
    	
        // save the configuration file
        saveDefaultConfig();
        
        worldgod = new HashMap<String, Boolean>();
        playerstone = new HashMap<Player, Boolean>();
        
        // Create the SampleListener
        new CuldListener(this);
        
        // set the command executor for sample
        this.getCommand("culd").setExecutor(new CuldCommandExecutor(this));
    }
    
    /*
     * This is called when your plug-in shuts down
     */
    @Override
    public void onDisable() {
    	log.info("Plugin has been disabled");        
    }

	public void setMetadata(Player player, String key, Object value, Culd plugin) {
		player.setMetadata(key, new FixedMetadataValue(plugin, value));
	}
		
	public Object getMetadata(Player player, String key, Culd plugin) {
		List<MetadataValue> values = player.getMetadata(key);
		for (MetadataValue value : values) {
			if (value.getOwningPlugin().getDescription().getName()
			.equals(plugin.getDescription().getName())) {
			return (value.asBoolean());
			}
		}
		return null;
	}
}