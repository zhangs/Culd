package edu.unca.szhang.Culd;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.google.common.base.Joiner;

/*
 * This is a sample CommandExectuor
 */
public class CuldCommandExecutor implements CommandExecutor {
    private final Culd plugin;
    
    /*
     * This command executor needs to know about its plugin from which it came from
     */
    public CuldCommandExecutor(Culd plugin) {
        this.plugin = plugin;
    }

    /*
     * On command set the sample message
     */
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    		if (args.length == 0) {
    			return false;
    		} 
    		else if (!(sender instanceof Player)) {
    			return false;
    		} 
    		
    		// Explanation of all available commands
    		else if (args[0].equalsIgnoreCase("description")) {
	    		Player user = (Player) sender;
	    		
	    		user.sendMessage("sacrifice = Requires an item to be held, which will be lost");	    		
	    		user.sendMessage("/culd find : Gain an item");
	    		user.sendMessage("/culd ruin <player> : Target <player> loses all items - sacrifice");
	    		user.sendMessage("/culd hope : Gain two items - sacrifice");
	    			    		
	    		return true;
    		}    		
    		
    		// Find - Draw a card, Find returns to hand
    		// -> Minecraft ->
    		// Gain an random item
    		else if (args[0].equalsIgnoreCase("find")) {
	    		Player user = (Player) sender;
	    		PlayerInventory userinven = user.getInventory();	    		
	    		
	    		if (userinven.firstEmpty() == -1) {
	    			user.sendMessage("Your inventory is full!");
	    		}
	    		else {
				    ItemStack tempStack = new ItemStack(itemgeneration(),1);		    
				    userinven.addItem(tempStack);
	    			user.sendMessage("Find");				    
	    		}
	    		
	    		return true;
    		} 
    		
    		// Ruin - Destroy all spell cards in target's hand
    		// -> Minecraft ->
    		// Destroy all items in target's hand
    		else if (args[0].equalsIgnoreCase("ruin")) {    	
    	        if (args.length < 2) {
    	            sender.sendMessage("Command for Ruin is culd ruin <player>");
    	            return false;
    	         }
    	        else {
		    		Player user = (Player) sender;    				    			    		    		
		    		PlayerInventory userinven = user.getInventory();	    		
	    			Player target = sender.getServer().getPlayer(args[1]);    			
	        		PlayerInventory targetinven = target.getInventory(); 	        		
	        		
	        		if (user.getItemInHand().getType() == Material.AIR || user.getItemInHand() == null) {
	        			user.sendMessage("You must hold something to sacrifice!");
	        		}
	        		else {
	        			userinven.removeItem(user.getInventory().getItemInHand());
	        			
	            		if (target.isOnline() && !target.isDead()) {
	                		targetinven.clear();    			
	                		target.sendMessage("Ruin was casted on you by " + user.getName() + "!");
	                		user.sendMessage("Ruin");
	            		}
	            		else if (!(target.isOnline() && !target.isDead())) {
	            			user.sendMessage("Target does not exist!");
	            		}        			
	        		}        		
	        		
	        		return true;
    	        }
    		}
    		
    		// Wind of Hope - Draw two cards
    		// -> Minecraft ->
    		// Remove current item held, gain two random items
    		else if (args[0].equalsIgnoreCase("hope")) {    	
	    		Player user = (Player) sender;    				    			    		    		
	    		PlayerInventory userinven = user.getInventory();	    		
        		
        		if (user.getItemInHand().getType() == Material.AIR || user.getItemInHand() == null) {
        			user.sendMessage("You must hold something to sacrifice!");
        		}
        		else {
        			userinven.removeItem(user.getInventory().getItemInHand());

        			for (int i = 0; i < 2; i++) {
        	    		if (userinven.firstEmpty() == -1) {
        	    			user.sendMessage("Your inventory is full!");
        	    		}
        	    		else {
        				    ItemStack tempStack = new ItemStack(itemgeneration(),1);		    
        				    userinven.addItem(tempStack);        	    							   
        	    		}        				
        			}
        			user.sendMessage("Wind of Hope");
        		}        		
        		
        		return true;
	        }
    		

    		else {
    			return false;
    		}    	
    }
    
    public static int itemgeneration () {
        Random randomitem = new Random();        	
		int tempitem = 0;
    	
		int selectsection = randomitem.nextInt(2);
		if (selectsection == 0) {
    		tempitem = randomitem.nextInt(96) + 1;    			
		}
		if (selectsection == 1) {
    		tempitem = randomitem.nextInt(103) + 256;    			
		}    	
		
		return tempitem;
    }
    
}