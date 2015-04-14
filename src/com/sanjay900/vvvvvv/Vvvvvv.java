package com.sanjay900.vvvvvv;

import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.sanjay900.vvvvvv.listeners.PlayerListener;
import com.sanjay900.vvvvvv.player.WonderlandPlayer;
import com.sanjay900.vvvvvv.utils.Cooldown;
import com.sanjay900.vvvvvv.utils.TagIdGenerator;

public class Vvvvvv extends JavaPlugin {

    private static Vvvvvv plugin = null;
	public PlayerListener pl;
	public static ArrayList<WonderlandPlayer> players = new ArrayList<>();
	public TagIdGenerator tagIdGenerator;
    @Override
    public void onEnable() {
    	tagIdGenerator = new TagIdGenerator();
    	 ProtocolLibrary.getProtocolManager().addPacketListener(
    	          new PacketAdapter(this, ListenerPriority.NORMAL, PacketType.Play.Server.WORLD_EVENT) {
    	            @Override
    	            public void onPacketSending(PacketEvent event) {
    	                PacketContainer packet = event.getPacket();
    	                int effectID = packet.getIntegers().read(0);
    	                
    	                // Sound: random.click
    	                if (effectID == 1000) {
    	                    int x = packet.getIntegers().read(2);
    	                    int y = packet.getIntegers().read(3);
    	                    int z = packet.getIntegers().read(4);
    	                    Block block = event.getPlayer().getWorld().getBlockAt(x, y, z);
    	 
    	                    // Cancel all dispenser clicks
    	                    if (block.getType() == Material.DISPENSER) {
    	                        event.setCancelled(true);
    	                    }
    	                }
    	                if (effectID == 1008) {
    	                    int x = packet.getIntegers().read(2);
    	                    int y = packet.getIntegers().read(3);
    	                    int z = packet.getIntegers().read(4);
    	                    Block block = event.getPlayer().getWorld().getBlockAt(x, y, z);
    	 
    	                    // Cancel all dispenser clicks
    	                    if (Cooldown.tryCooldown(event.getPlayer(), "sound", 100)) {
    	                        event.setCancelled(true);
    	                    }
    	                }
    	            }
    	        });
    	 ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(this,
 		        ListenerPriority.NORMAL, 
 		        PacketType.Play.Client.STEER_VEHICLE) {
 		    @Override
 		    public void onPacketReceiving(PacketEvent event) {
 		            PacketContainer packet = event.getPacket();
 		            Player p = event.getPlayer();	
 		            if (getPlayer(p) != null) {
 		            	float sideMot = packet.getFloat().read(0);
						//float forMot = packet.getFloat().read(1);
						getPlayer(p).movement(sideMot);
 		            }
 							
 					
 		    }
 		    
 		});
    	 ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(this,
  		        ListenerPriority.NORMAL, 
  		        PacketType.Play.Client.ENTITY_ACTION) {
  		    @Override
  		    public void onPacketReceiving(PacketEvent event) {
  		            PacketContainer packet = event.getPacket();
  		            Player p = event.getPlayer();
  		            if (getPlayer(p) != null) {
  		            	if (packet.getIntegers().read(1) == 6) {
  		            		getPlayer(p).flip();
  		            	}
  		            }
  							
  					
  		    }
  		    
  		});
        PluginManager pm = this.getServer().getPluginManager();
        setPlugin((Vvvvvv) pm.getPlugin("vvvvvvPlugin"));
        getCommand("vvvvvv").setExecutor(new VvvvvvCommand());
        pl = new PlayerListener(this);
        
        
        	     Bukkit.getPluginManager().registerEvents(pl, this);
        	     
    }
    
    public static WonderlandPlayer getPlayer(Player player) {
        for (WonderlandPlayer p : players) {
        	
            if (p.getPlayer().getName().equalsIgnoreCase(player.getName())) {
                return p;
            }
        }
        return null;
    }
    @Override
    public void onDisable() {
    	for (WonderlandPlayer p : players) 
    	{
    		p.stopGame();
    	}
    	
    	ProtocolLibrary.getProtocolManager().removePacketListeners(getPlugin());

    }
   
	public static Vvvvvv getPlugin() {
		return plugin;
	}
	public static void setPlugin(Vvvvvv plugin) {
		Vvvvvv.plugin = plugin;
	}
}

