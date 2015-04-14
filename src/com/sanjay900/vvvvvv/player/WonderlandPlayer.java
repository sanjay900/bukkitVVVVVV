package com.sanjay900.vvvvvv.player;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;
import com.sanjay900.vvvvvv.Vvvvvv;
import com.sanjay900.vvvvvv.utils.FrozenSand;
import com.sanjay900.vvvvvv.utils.FrozenSandFactory;

public class WonderlandPlayer{
	
	
	private String player;
	private FrozenSand hologram;
	public Location location;
	public Location hlocation;
	public boolean tp = true;
	private NPC npc;
	public boolean stop = false;
	Vector dir;
	public boolean hasFlipped = false;
	public BukkitTask runnable;
	public WonderlandPlayer(Player p) {
		this.setPlayer(p);
		dir = p.getLocation().getDirection();
			this.hologram = new FrozenSandFactory(Vvvvvv.getPlugin()).withLocation(p.getLocation()).withText("none").withPlayer(p).build();
		this.location = p.getLocation();
		hologram.moveTag(location.clone().add(0, 2, 5));
		p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE,1, true));
		for (Player p2 : Bukkit.getOnlinePlayers()) {
			p2.hidePlayer(p);
		}
		NPCRegistry registry = CitizensAPI.getNPCRegistry();
		this.npc = registry.createNPC(EntityType.PLAYER, "§0§kDinnorbone");
		npc.data().setPersistent(NPC.PLAYER_SKIN_UUID_METADATA, "t");
		npc.spawn(p.getLocation());
		npc.getNavigator().getDefaultParameters().speedModifier(3);
		runnable = Bukkit.getScheduler().runTaskTimer(Vvvvvv.getPlugin(), new Runnable(){

			@Override
			public void run() {
				Player npcp = (Player)npc.getEntity();
				Vector velocity = npc.getEntity().getVelocity();
				
				if (hasFlipped) {
				velocity.setY(1.5);
				if (!npc.getEntity().getLocation().add(velocity).getBlock().getType().isSolid()) {
				npcp.setVelocity(velocity);
				} else {
					velocity.setY(1.2);
					npcp.setVelocity(velocity);
				}
				} else {
					velocity.setY(-1.5);
					if (!npc.getEntity().getLocation().add(velocity).getBlock().getType().isSolid()) {
					
					npcp.setVelocity(velocity);
					}
				}
				 
				final Location hl = npc.getEntity().getLocation().clone().add(0,0,5);  
				   hologram.moveTag(hl.clone());
				   ((CraftPlayer)getPlayer()).getHandle().locX = npc.getEntity().getLocation().clone().getX();
					  ((CraftPlayer)getPlayer()).getHandle().locY = npc.getEntity().getLocation().clone().getY();
					  	 ((CraftPlayer)getPlayer()).getHandle().locZ = npc.getEntity().getLocation().clone().getZ();
			}}, 1l, 1l);
	
	}
	public boolean movement(Location l, Vector v) {
		if (stop) return false;
			boolean ret = Vvvvvv.getPlugin().pl.playerMoveEvent(this,this.location,l.clone(),v);
			if (!ret) {
				movePlayerAndHologram(l);
				return true;
			}
			return false;
			
		
		
	}

	public void teleportNoEvt(Location l) {
		if (stop) return;

				movePlayerAndHologram(l);
			
			
		
		
	}
	public void teleportNoChk(Location l, Vector v) {
		if (stop) return;
		boolean ret = Vvvvvv.getPlugin().pl.playerMoveEvent(this,this.location,l.clone(),v);
			movePlayerAndHologram(l);
	
	}
	public void movement(float sideMot) {
		if (stop) return;
		if (!tp) return;
		/*
		Vector vector = new Vector(sideMot*-1, 0, 0);
		vector = FaceUtil.faceToVector(FaceUtil.getDirection(vector));
		vector.multiply(0.5);
		if (sideMot != 0) {
			Location l3 = npc.getEntity().getLocation().clone().add(vector);
				MoveEvent event = new MoveEvent(this,l3.clone(),vector);
				Bukkit.getServer().getPluginManager().callEvent(event);
				if (!event.isCancelled()) {
					walkPlayerAndHologram(vector);
					tp = false;
					Bukkit.getScheduler().runTaskLater(Vvvvvv.getPlugin(), new Runnable(){

						@Override
						public void run() {
							tp = true;
							
						}}, 1L);
			
				
			}
			
		}
		*/
		npc.getEntity().setVelocity(npc.getEntity().getVelocity().setX(sideMot*-1));
				
		
		
	}
	public void stopGame() {
		runnable.cancel();
		stop = true;
		for (Player p2 : Bukkit.getOnlinePlayers()) {
			p2.showPlayer(getPlayer());
			hologram.clearTags(p2, hologram.getAllEntityIds());
		}
		
		npc.destroy();
		for (PotionEffect effect : getPlayer().getActivePotionEffects())
	        getPlayer().removePotionEffect(effect.getType());
		 
	}

	private void movePlayerAndHologram(Location l) {
		if (stop) return;
		   Vector v = l.toVector().subtract(location.toVector());
		   location = l;
		   location.setDirection(v);
		   l.setDirection(v);
		   final Location hl = location.clone().add(0,5,0);
			   hologram.moveTag(hl.clone());
		   npc.getNavigator().cancelNavigation();
		   npc.faceLocation(l.clone().add(v));
		   npc.getEntity().teleport(location);
		   npc.faceLocation(l.clone().add(v));
		   ((CraftPlayer)getPlayer()).getHandle().locX = location.clone().getX();
		  ((CraftPlayer)getPlayer()).getHandle().locY = location.clone().getY();
		  ((CraftPlayer)getPlayer()).getHandle().locZ = location.clone().getZ();
		   
		   
			
		 
		}
	private void walkPlayerAndHologram(final Vector vector) {
		if (stop) return;
		if (npc.getEntity().getLocation().add(vector).getBlock().getType().isSolid()&&npc.getEntity().getLocation().add(vector).getBlock().getRelative(BlockFace.UP).getType().isSolid()) return;
	   location = npc.getEntity().getLocation().add(vector);
	   

	   npc.faceLocation(location.clone().add(vector));
	   npc.getNavigator().cancelNavigation();
	   npc.getNavigator().setTarget(npc.getEntity().getLocation().add(vector));
       npc.getEntity().teleport(npc.getEntity().getLocation().add(vector));
	   ((CraftPlayer)getPlayer()).getHandle().locX = location.clone().getX();
	  ((CraftPlayer)getPlayer()).getHandle().locY = location.clone().getY();
	  	 ((CraftPlayer)getPlayer()).getHandle().locZ = location.clone().getZ();
	
	}

	public Player getPlayer() {
		return Bukkit.getPlayer(player);
	}
	public void reSpawn() {
		//TODO handle respawn
		//Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run wlevent:death "+getPlayer().getName());
	}
	public void setPlayer(Player player) {
		this.player = player.getName();
	}
	public void flip() {
		Bukkit.getScheduler().runTask(Vvvvvv.getPlugin(), new Runnable(){

			@Override
			public void run() {
				if (!hasFlipped) {
					hasFlipped = !hasFlipped;
					
					npc.setName("§0§kDinnerbone");
					npc.spawn(location);
					location = npc.getEntity().getLocation();
					   npc.faceLocation(location.clone());
					   npc.getNavigator().cancelNavigation();
					   npc.getNavigator().setTarget(location);
				       npc.getEntity().teleport(location);
					   ((CraftPlayer)getPlayer()).getHandle().locX = location.clone().getX();
					  ((CraftPlayer)getPlayer()).getHandle().locY = location.clone().getY();
					  	 ((CraftPlayer)getPlayer()).getHandle().locZ = location.clone().getZ();
					
				} else {
					hasFlipped = !hasFlipped;
					npc.setName("§0§kDinnorbone");
					npc.spawn(location);
					location = npc.getEntity().getLocation();
					   npc.faceLocation(location.clone());
					   npc.getNavigator().cancelNavigation();
					   npc.getNavigator().setTarget(location);
				       npc.getEntity().teleport(location);
					   ((CraftPlayer)getPlayer()).getHandle().locX = location.clone().getX();
					  ((CraftPlayer)getPlayer()).getHandle().locY = location.clone().getY();
					  	 ((CraftPlayer)getPlayer()).getHandle().locZ = location.clone().getZ();
				}
			}});
		
		
	}

}
