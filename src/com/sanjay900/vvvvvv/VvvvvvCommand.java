package com.sanjay900.vvvvvv;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;
import org.bukkit.util.Vector;
import com.sanjay900.vvvvvv.player.WonderlandPlayer;

/**
 * This class is responsible for handling the /ne command.
 *
 * @author Levi Webb
 *
 */
class VvvvvvCommand implements CommandExecutor {
	
    

    /**
     * onCommand method for the plugin.
     *
     * @param sender the command sender
     * @param cmd the executed command
     * @param label the command label
     * @param args an array of {@link String} objects for the command arguments
     * @see {@link org.bukkit.command.CommandExecutor}
     */
    @SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player senderPlayer = null;
        if (sender instanceof Player) {
            senderPlayer = (Player) sender;
        

        if (cmd.getName().equalsIgnoreCase("vvvvvv")) {
          if (args[0].equals("start")) {
        	  if (Vvvvvv.getPlayer(senderPlayer)==null) {
        	  Vvvvvv.players.add(new WonderlandPlayer(senderPlayer));
        	  }
          }
          if (args[0].equals("test")) {
          LevelParser.parseDoc(senderPlayer.getLocation());
          }
          if (args[0].equals("stop")) {
        	  if (Vvvvvv.getPlayer(senderPlayer)!=null) {
        		  Vvvvvv.getPlayer(senderPlayer).stopGame();
        		  Vvvvvv.players.remove(Vvvvvv.getPlayer(senderPlayer));  
        	  }
        	  
          }
            
        }
        } else {
        	senderPlayer = Bukkit.getPlayer(args[1]);
        	if (cmd.getName().equalsIgnoreCase("vvvvvv")) {
                if (args[0].equals("start")) {
              	  if (Vvvvvv.getPlayer(senderPlayer)==null) {
              	  Vvvvvv.players.add(new WonderlandPlayer(senderPlayer));
              	  }
                }
                
                if (args[0].equals("stop")) {
              	  if (Vvvvvv.getPlayer(senderPlayer)!=null) {
              		  Vvvvvv.getPlayer(senderPlayer).stopGame();
              		  Vvvvvv.players.remove(Vvvvvv.getPlayer(senderPlayer));  
              	  }
              	  
                }
                  
              }
        	if (args[0].equals("drop")) {
        		//./wl drop[0] item[1] loc[2] pitch[3] yaw[4] itemname[5]
        		Location l = new Location(Bukkit.getWorld("wonderland"),Double.parseDouble(args[2].split(",")[0]),Double.parseDouble(args[2].split(",")[1]),Double.parseDouble(args[2].split(",")[2]));
        		ItemStack item = new ItemStack(Material.getMaterial(Integer.parseInt(args[1].split(":")[0])));
        		MaterialData data = item.getData();
        		data.setData((byte)Integer.parseInt(args[1].split(":")[1]));
        		item.setData(data);
        		ItemMeta m = item.getItemMeta();
        		m.setDisplayName(args[5]);
        		item.setItemMeta(m);
        		l.getWorld().dropItem(l.getBlock().getLocation().add(0.5, 0.5, 0.5), item).setVelocity(new Vector(0, 0, 0));
        	}
        	if (args[0].equals("stopb")) {
        		//./wl chomper[0] playername[1] loc[2] pitch[3] yaw[4] chompername[5]
          	 Vvvvvv.getPlugin().pl.stop = true;
        	}
        	if (args[0].equals("startb")) {
        		//./wl chomper[0] playername[1] loc[2] pitch[3] yaw[4] chompername[5]
          	 Vvvvvv.getPlugin().pl.stop = false;
          	 
          	
          	  
        	}
        	
        	
        }
        return true;
    }

    /**
     * Combines the given array of {@link String} objects into a single (@link
     * String}
     *
     * @param args the (@link String} array to combine
     * @return the combined string
     */
    private String getText(String[] args) {
        String rv = "";
        for (int t = 2; t < args.length; t++) {
            if (t == args.length - 1) {
                rv += args[t];
            } else {
                rv += args[t] + " ";
            }
        }
        return rv;
    }

    

}
