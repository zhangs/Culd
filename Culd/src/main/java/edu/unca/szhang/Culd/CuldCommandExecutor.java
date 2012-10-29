package edu.unca.szhang.Culd;


import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
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
		// stores constantly used variables here
    	Player user = (Player) sender;
		PlayerInventory userinven = user.getInventory();	    				
		Player [] users = Bukkit.getOnlinePlayers();
		Player target = user; // Just to initialize
		PlayerInventory targetinven = user.getInventory(); // Just to initialize      				
		
		String order = args[0];
		if (!(order.equalsIgnoreCase("god"))) {
			if (args.length == 2) {
				if (sender.getServer().getPlayer(args[1]) == null) {
					user.sendMessage("Player does not exist");
					return false;
				}
				else {
					target = sender.getServer().getPlayer(args[1]);
					targetinven = target.getInventory();				
				}
			}
		}
		
    	if (args.length == 0 || (order.equalsIgnoreCase("god") && args.length < 2) || args.length > 2) {
			return false;
		}     	
		
		else if (!(sender instanceof Player)) {
			return false;
		} 
    	
				
		else if (order.equalsIgnoreCase("description")) {    		
    		user.sendMessage("sacrifice = Requires an item to be held, which will be lost");	    		
    		user.sendMessage("/culd find : Gain an item");
    		user.sendMessage("/culd ruin <player> : Target <player> loses all items - sacrifice");
    		user.sendMessage("/culd hope : Gain two items - sacrifice");
    		user.sendMessage("/culd upheaval <player> : Changes block of target to fire - sacrifice");	    		
    		user.sendMessage("/culd sink <player> : Changes block of target to water - sacrifice");	    		
    		user.sendMessage("/culd weathering <player> : Changes block of target to sand - sacrifice");	    		
    		user.sendMessage("/culd growth <player> : Changes block of target to grass - sacrifice");	    		
    		user.sendMessage("/culd subside <player> : Lowers block of target by 1 level - sacrifice");
    		user.sendMessage("/culd omnipotent : Reveals health and location of other players");	    		
    		user.sendMessage("/culd blast <player> : Deals 3 hearts of damage to player - sacrifice");	    		
    		user.sendMessage("/culd cure <player> : Fully heals target player");	
    		user.sendMessage("/culd god wrath : Sets world to Elemental Wrath (Chance of lightning when not on grass)");		    		
    		user.sendMessage("/culd god dead : Sets world to Raise Dead (Dead entities revive as zombies)");		    		
    		user.sendMessage("/culd god stone : Gives user the ability to petrify entities");		    		
    			    		
    		return true;
		}    		
		
		
		// Find - Draw a card, Find returns to hand
		// -> Minecraft ->
		// Gain an random item
		else if (order.equalsIgnoreCase("find")) {    		
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
		else if (order.equalsIgnoreCase("ruin")) {    	
	        if (args.length < 2) {
	            sender.sendMessage("Command for Ruin is culd ruin <player>");
	            return false;
	         }
	        else {        		
        		if (sacrifice(user) == false) {
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
		
		
		// Upheaval - Change target territory to fire
		// -> Minecraft ->
		// Change block of target player to fire    		
		else if (order.equalsIgnoreCase("upheaval")) {
	        if (args.length < 2) {
	            sender.sendMessage("Command for Upheaval is culd upheaval <player>");
	            return false;
	         }
	        else {    			
        		if (sacrifice(user) == false) {
        			user.sendMessage("You must hold something to sacrifice!");
        		}
        		else {
        			userinven.removeItem(user.getInventory().getItemInHand());
        			
            		if (target.isOnline() && !target.isDead()) {
    	    			Location area = target.getLocation();
    	    			World world = area.getWorld();
    	    			area.setY(area.getY() - 1);	    	    			
    	    			Block targetblock = world.getBlockAt(area);
    	    			targetblock.setType(Material.FIRE);
    	    			
    	    			target.sendMessage("Upheaval was casted on you by " + user.getName() + "!");
                		user.sendMessage("Upheaval");
            		}
            		else if (!(target.isOnline() && !target.isDead())) {
            			user.sendMessage("Target does not exist!");
            		}        			
        		}	    			
    		
    			return true;
	        }
		}  
		
		
		// Sink - Change target territory to water
		// -> Minecraft ->
		// Change block of target player to water   		
		else if (order.equalsIgnoreCase("sink")) {
	        if (args.length < 2) {
	            sender.sendMessage("Command for Sink is culd sink <player>");
	            return false;
	         }
	        else {    			
        		if (sacrifice(user) == false) {
        			user.sendMessage("You must hold something to sacrifice!");
        		}
        		else {
        			userinven.removeItem(user.getInventory().getItemInHand());
        			
            		if (target.isOnline() && !target.isDead()) {
    	    			Location area = target.getLocation();
    	    			World world = area.getWorld();
    	    			area.setY(area.getY() - 1);	    	    			
    	    			Block targetblock = world.getBlockAt(area);
    	    			targetblock.setType(Material.WATER);
    	    			
    	    			target.sendMessage("Sink was casted on you by " + user.getName() + "!");
                		user.sendMessage("Sink");
            		}
            		else if (!(target.isOnline() && !target.isDead())) {
            			user.sendMessage("Target does not exist!");
            		}        			
        		}	    			
    		
    			return true;
	        }
		}    		
		
		
		// Weathering - Change target territory to wind
		// -> Minecraft ->
		// Change block of target player to Sand  		
		else if (order.equalsIgnoreCase("weathering")) {
	        if (args.length < 2) {
	            sender.sendMessage("Command for Weathering is culd weathering <player>");
	            return false;
	         }
	        else {    			
        		if (sacrifice(user) == false) {
        			user.sendMessage("You must hold something to sacrifice!");
        		}
        		else {
        			userinven.removeItem(user.getInventory().getItemInHand());
        			
            		if (target.isOnline() && !target.isDead()) {
    	    			Location area = target.getLocation();
    	    			World world = area.getWorld();
    	    			area.setY(area.getY() - 1);	    	    			
    	    			Block targetblock = world.getBlockAt(area);
    	    			targetblock.setType(Material.SAND);
    	    			
    	    			target.sendMessage("Weathering was casted on you by " + user.getName() + "!");
                		user.sendMessage("Weathering");
            		}
            		else if (!(target.isOnline() && !target.isDead())) {
            			user.sendMessage("Target does not exist!");
            		}        			
        		}	    			
    		
    			return true;
	        }
		}    
		
		
		// Wild Growth - Change target territory to earth
		// -> Minecraft ->
		// Change block of target player to grass  		
		else if (order.equalsIgnoreCase("growth")) {
	        if (args.length < 2) {
	            sender.sendMessage("Command for Wild Growth is culd growth <player>");
	            return false;
	         }
	        else {    			
        		if (sacrifice(user) == false) {
        			user.sendMessage("You must hold something to sacrifice!");
        		}
        		else {
        			userinven.removeItem(user.getInventory().getItemInHand());
        			
            		if (target.isOnline() && !target.isDead()) {
    	    			Location area = target.getLocation();
    	    			World world = area.getWorld();
    	    			area.setY(area.getY() - 1);
    	    			Block targetblock = world.getBlockAt(area);
    	    			targetblock.setType(Material.GRASS);
    	    			
    	    			target.sendMessage("Wild Growth was casted on you by " + user.getName() + "!");
                		user.sendMessage("Wild Growth");
            		}
            		else if (!(target.isOnline() && !target.isDead())) {
            			user.sendMessage("Target does not exist!");
            		}        			
        		}	    			
    		
    			return true;
	        }
		}    		
		
		
		// subside - Lowers target's highest level territory by 1 level
		// -> Minecraft ->
		// Lowers the block stood on by target by 1 level 		
		else if (order.equalsIgnoreCase("subside")) {
	        if (args.length < 2) {
	            sender.sendMessage("Command for Subside is culd subside <player>");
	            return false;
	         }
	        else {    			
        		if (sacrifice(user) == false) {
        			user.sendMessage("You must hold something to sacrifice!");
        		}
        		else {
        			userinven.removeItem(user.getInventory().getItemInHand());
        			
            		if (target.isOnline() && !target.isDead()) {
    	    			Location area = target.getLocation();
    	    			World world = area.getWorld();
    	    			area.setY(area.getY() - 1);
    	    			Block targetblock = world.getBlockAt(area);
    	    			targetblock.setType(Material.AIR);
    	    			
    	    			target.sendMessage("Subsidence was casted on you by " + user.getName() + "!");
                		user.sendMessage("Subsidence");
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
		else if (order.equalsIgnoreCase("hope")) {    	
    		
    		if (sacrifice(user) == false) {
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
		
		
		// Evil Blast - Does 30 damage to target creature
		// -> Minecraft ->
		// Does 3 heart of damage to target user
   		else if (order.equalsIgnoreCase("blast")) {
	        if (args.length < 2) {
	            sender.sendMessage("Command for Evil Blast is culd blast <player>");
	            return false;
	         }
	        else {    			
        		if (sacrifice(user) == false) {
        			user.sendMessage("You must hold something to sacrifice!");
        		}
        		else {
        			userinven.removeItem(user.getInventory().getItemInHand());
        			
            		if (target.isOnline() && !target.isDead()) {
    	    			Location area = target.getLocation();
    	    			World world = area.getWorld();	   
    	    			
    	    			int targetcurlife = target.getHealth() - 6;	 
    	    			if (targetcurlife < 0) {
    	    				targetcurlife = 0;
    	    			}
            			world.createExplosion(area, 1);	            			
            			target.setHealth(targetcurlife);
            			
    	    			target.sendMessage("Evil Blast was casted on you by " + user.getName() + "!");
                		user.sendMessage("Evil Blast");	    	    			
            		}
            		else if (!(target.isOnline() && !target.isDead())) {
            			user.sendMessage("Target does not exist!");
            		}        			
        		}	    			
    		
    			return true;
	        }
		}      		
		
		
		// Cure - Fully heals target creature
		// -> Minecraft ->
		// Fully heals target user
   		else if (order.equalsIgnoreCase("cure")) {
	        if (args.length < 2) {
	            sender.sendMessage("Command for Cure is culd cure <player>");
	            return false;
	         }
	        else {        			
        		if (target.isOnline() && !target.isDead()) {	            			
        			target.setHealth(20);
        			
	    			target.sendMessage("Cure was casted on you by " + user.getName() + "!");
            		user.sendMessage("Cure");	    	    			
        		}
        		else if (!(target.isOnline() && !target.isDead())) {
        			user.sendMessage("Target does not exist!");
        		}        			
        			    			
    		
    			return true;
	        }
		}      		    		
		
		
		// Omnipotent - Choose 1 from 7 Shrine Effects
		// -> Minecraft ->
		// (Following definition of omnipotent) Reveals health and location of other players
		else if (order.equalsIgnoreCase("omnipotent")) {
			user.sendMessage("Omnipotent");
			plugin.log.info("Omnipotent used by: " + user.getName());    				
						
			for (int i = 0; i < users.length; i++) {
				if (!(users[i] == user)) {
					user.sendMessage(users[i].getName() + " is currently at X=" + users[i].getLocation().getX() + " Y=" + users[i].getLocation().getY() + " Z=" + users[i].getLocation().getZ());
    				user.sendMessage(users[i].getName() + "'s health is " + users[i].getHealth());

    				plugin.log.info(users[i].getName() + " is currently at X=" + users[i].getLocation().getX() + " Y=" + users[i].getLocation().getY() + " Z=" + users[i].getLocation().getZ());
    				plugin.log.info(users[i].getName() + "'s health is " + users[i].getHealth());        					
				}        	
			}
			        			
    		return true;        			    			
		}   
		
		
		// Elemental Wrath - All creatures on territory not of their element -20 HP
		// -> Minecraft ->
		// Players not on grass have a chance of being struck by lightning
		// Sets the key (Wrath)'s value to true if false and false if true
		else if (order.equalsIgnoreCase("god") && args[1].equalsIgnoreCase("wrath")) {			
			if (plugin.worldgod.get("Wrath")) {
    			plugin.worldgod.put("Wrath", false);
    			for (int i = 0; i < users.length; i++) {
					users[i].sendMessage("Elemental Wrath deactivated");
				}         			
			}
			else {
    			plugin.worldgod.put("Wrath", true);
		    	
    			for (int i = 0; i < users.length; i++) {
    					users[i].sendMessage("Elemental Wrath activated");
    			}       				
			} 		    	
	    	
	    	return true;    			        	       			    		
		}    	
		
		
		// Raise Dead - Places most recently destroyed creature into hand
		// -> Minecraft ->
		// Living entities revive as zombies when they die
		// Sets the key (Dead)'s value to true if false and false if true
		else if (order.equalsIgnoreCase("god") && args[1].equalsIgnoreCase("Dead")) {			
			if (plugin.worldgod.get("Dead")) {
    			plugin.worldgod.put("Dead", false);
    			for (int i = 0; i < users.length; i++) {
					users[i].sendMessage("Raise Dead deactivated");
				}         			
			}
			else {
    			plugin.worldgod.put("Dead", true);
		    	
    			for (int i = 0; i < users.length; i++) {
    					users[i].sendMessage("Raise Dead activated");
    			}       				
			} 		    	
	    	
	    	return true;    			        	       			    		
		}     	
		
		
		// Turn to the Wall - Transforms a creature to the wall-type creature of its element
		// -> Minecraft ->
		// Transforms entities to stone
		// Sets the key (Player's name)'s value to true if false and false if true
		else if (order.equalsIgnoreCase("god") && args[1].equalsIgnoreCase("Stone")) {						
			if (plugin.playerstone.get(user)) {
    			plugin.playerstone.put(user, false);
    			for (int i = 0; i < users.length; i++) {
					users[i].sendMessage(user.getName() + " deactivated Turn to the Wall");
				}         			
			}
			else {
    			plugin.playerstone.put(user, true);
		    	
    			for (int i = 0; i < users.length; i++) {
    					users[i].sendMessage(user.getName() + " activated Turn to the Wall");
    			}       				
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
    
    
    public static boolean sacrifice (Player user) {
		if (user.getItemInHand().getType() == Material.AIR || user.getItemInHand() == null) {
			return false;
		}
		else {
			return true;
		}
    }
    
}