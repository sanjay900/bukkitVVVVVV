package com.sanjay900.vvvvvv.utils;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitRunnable;
public class SetBlockLater extends BukkitRunnable {
	private Block blk;
	private Material mt;
	private byte data;
	public SetBlockLater(Block blk, Material mt, byte data) {
		this.blk = blk;
		this.mt = mt;
		this.data = data;
	}

	public void run() {
		// What you want to schedule goes here
		this.blk.setType(mt);
		this.blk.setData(data);


	}
}
