package com.sanjay900.vvvvvv.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemDespawnEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.util.Vector;
import com.sanjay900.vvvvvv.Vvvvvv;
import com.sanjay900.vvvvvv.player.WonderlandPlayer;
import com.sanjay900.vvvvvv.utils.Cooldown;
import com.sanjay900.vvvvvv.utils.Utils;

public class PlayerListener implements Listener {
	private Vvvvvv plugin;

	public PlayerListener(Vvvvvv plugin) {
		this.plugin = plugin;

	}

	boolean checked = false;
	public boolean stop;
	 /*TODO: SOCKETIO
	@EventHandler
	public void onMessage(final ServerRecieveMessageEvent messageEvent) {
		if (messageEvent.getMessage().equals("vvvvvv.play")) {

			
			Bukkit.getScheduler().scheduleSyncDelayedTask(Vvvvvv.getPlugin(), new Runnable(){

				@Override
				public void run() {			
					//TODO create code to start game
					//String pl = msg.split("[|]")[0];
					//#String lvl = msg.split("[|]")[1];
					//final Player p = Bukkit.getPlayer(pl);
					//if (p != null) {
					//	Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run lvl"+lvl+":init "+pl);	
					//}
					
				}}, 20L);
		}


		
	}
	*/
	@EventHandler
	public void itemDrop(PlayerDropItemEvent evt) {
		if (Vvvvvv.getPlayer(evt.getPlayer()) != null) {
			evt.setCancelled(true);
		}
	}
	@EventHandler
	public void playerMoveEvent(PlayerMoveEvent evt) {
		if (Utils.compareLocation(evt.getFrom(), evt.getTo())&&(Vvvvvv.getPlayer(evt.getPlayer()) != null)){
			//Wonderland.getPlayer(evt.getPlayer()).changeDirection();
		}
	}
	@EventHandler
	public void itemGetEvent(PlayerPickupItemEvent event) {
		if (Vvvvvv.getPlayer(event.getPlayer()) == null) return;
		if (Utils.compareLocation(event.getItem().getLocation().getBlock().getLocation(), event.getPlayer().getLocation().getBlock().getLocation())) {
			if (event.getItem().getItemStack().hasItemMeta() && event.getItem().getItemStack().getItemMeta().hasDisplayName()) {
				Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt setstr "+event.getPlayer().getName()+" lastitem "+event.getItem().getItemStack().getItemMeta().getDisplayName().toLowerCase());
				Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run wlevent:itempickup "+event.getPlayer().getName());
			}
			else {
				event.setCancelled(true);
			}
			
		} else {
			
			event.setCancelled(true);
		}
	}
	@EventHandler
	public void despawn(ItemDespawnEvent evt ) {
		if (evt.getEntity().getItemStack().hasItemMeta() && evt.getEntity().getItemStack().getItemMeta().hasDisplayName()) {
			evt.setCancelled(true);
		}
	}
	@EventHandler
	public void leave(PlayerQuitEvent evt) {
		if (Vvvvvv.getPlayer(evt.getPlayer())!=null) {
    		  Vvvvvv.getPlayer(evt.getPlayer()).stopGame();
    		  Vvvvvv.players.remove(Vvvvvv.getPlayer(evt.getPlayer()));  
    	  }
	}
	
	@EventHandler
	public void interact(PlayerInteractEvent e) {
		if (Cooldown.tryCooldown(e.getPlayer(), "restart", 1L)&&Vvvvvv.getPlayer(e.getPlayer())!=null&&e.hasItem()&&e.getItem().hasItemMeta()&&e.getItem().getItemMeta().hasDisplayName()) {
			switch (ChatColor.stripColor(e.getItem().getItemMeta().getDisplayName()).toLowerCase()) {
		case "restart level": 
				Vvvvvv.getPlayer(e.getPlayer()).reSpawn();;
				e.setCancelled(true);
		break;	
			}
		}
		
		}
	

	public boolean playerMoveEvent(WonderlandPlayer p, Location from, Location to, Vector velocity) {
		boolean ret = false;
		 ret = to.getBlock().getType().isSolid();
		
		
		return ret;
	}

	

	
	
	
}