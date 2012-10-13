package edu.unca.szhang.Culd;

import org.bukkit.plugin.java.JavaPlugin;

/*
 * This is the main class of the sample plug-in
 */
public class Culd extends JavaPlugin {		
	
	CuldLogger log;
	
    /*
     * This is called when your plug-in is enabled
     */
    @Override
    public void onEnable() {
    	log = new CuldLogger(this);
    	log.info("Plugin has been enabled");    	
    	
        // save the configuration file
        saveDefaultConfig();
        
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

}