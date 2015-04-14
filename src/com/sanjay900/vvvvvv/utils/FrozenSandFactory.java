package com.sanjay900.vvvvvv.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.sanjay900.vvvvvv.Vvvvvv;


public class FrozenSandFactory {
        private final Vvvvvv plugin;
	    private String worldName;
	    private double locX;
	    private double locY;
	    private double locZ;
	    private String saveId;
	    private Player attachPlayer = null;
		private String tag;
		
		public FrozenSandFactory(Vvvvvv plugin) {
		    this.plugin = plugin;
		}
		
	    private FrozenSandFactory withCoords(double x, double y, double z) {
	        this.locX = x;
	        this.locY = y;
	        this.locZ = z;
	        return this;
	    }
	    public FrozenSandFactory withPlayer(Player p) {
	        this.attachPlayer = p;
	        return this;
	    }
	    private FrozenSandFactory withWorld(String worldName) {
	        this.worldName = worldName;
	        return this;
	    }
	    public FrozenSandFactory withLocation(Location location) {
	        this.withCoords(location.getX(), location.getY(), location.getZ());
	        this.withWorld(location.getWorld().getName());
	        return this;
	    }
	    public FrozenSandFactory withLocation(Vector vectorLocation, String worldName) {
	        this.withCoords(vectorLocation.getX(), vectorLocation.getY(), vectorLocation.getZ());
	        this.withWorld(worldName);
	        return this;
	    }
	    public FrozenSandFactory withText(String text) {
	        this.tag = text;
	        return this;
	    }
	    public FrozenSand build() {
	        World world = Bukkit.getWorld(this.worldName);
	        if (world == null) {
	                plugin.getLogger().warning("Could not find valid world (" + this.worldName + ") for Hologram of ID " + this.saveId + ". Maybe the world isn't loaded yet?");
	            return null;
	        }
	        FrozenSand hologram = new FrozenSand(plugin, this.worldName, this.locX, this.locY, this.locZ, this.attachPlayer, tag);
	        
	            hologram.show(this.attachPlayer, locX, locY, locZ);
	       
	        return hologram;
	    }
	}

