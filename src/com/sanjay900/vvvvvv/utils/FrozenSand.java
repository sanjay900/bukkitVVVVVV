package com.sanjay900.vvvvvv.utils;

import java.lang.reflect.InvocationTargetException;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.reflect.StructureModifier;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import com.sanjay900.vvvvvv.Vvvvvv;


public class FrozenSand {
	private Player attachPlayer;
	public String id = "";
	public int entityId = 0;
	double x = 0;
	double y = 0;
	double z = 0;
	String worldName = "";
	ProtocolManager pm;
	private Vector motion;
	public BukkitTask velocitytask = null;

	protected FrozenSand(final Vvvvvv plugin, String worldName, double x, double y, double z, Player attachPlayer, String id) {
		this.attachPlayer = attachPlayer;
		entityId = plugin.tagIdGenerator.nextId(1);
		this.x = x;
		this.y = y;
		this.z = z;
		this.worldName = worldName;
		this.pm = ProtocolLibrary.getProtocolManager();
		this.id = id;
	}

	protected void generate(Player observer, String message, double diffY, double x, double y, double z) {

		this.id = message;
		x = Math.floor(x)+0.5;
		y = Math.floor(y);
		z = Math.floor(z)+0.5;
		if (((CraftPlayer)observer).getHandle().playerConnection.networkManager.getVersion() > 5) {
			
			gen18(observer, message, diffY, x, y, z);
		} else {
			genPre18(observer, message, diffY, x, y, z);
		}


	}
	public void gen18(Player observer, String message, double diffY, double x, double y, double z) {
		try {
			PacketContainer attach2 = pm.createPacket(PacketType.Play.Server.ATTACH_ENTITY);
			PacketContainer horse = pm.createPacket(PacketType.Play.Server.SPAWN_ENTITY_LIVING);
			StructureModifier<Integer> modifier = horse.getIntegers();
			modifier.write(0,this.getHorseIndex());
			modifier.write(1, 30);
			modifier.write(2, (int) Math.floor(x* 32.0D));
			modifier.write(3, (int)Math.floor(y * 32.0D));
			modifier.write(4, (int)Math.floor(z* 32.0D));

			WrappedDataWatcher dw = new WrappedDataWatcher();
			dw.setObject(0, Byte.valueOf(Byte.valueOf((byte) 32)));
			horse.getDataWatcherModifier().write(0,dw);
			PacketContainer item = pm.createPacket(PacketType.Play.Server.SPAWN_ENTITY);

			if (!message.equals("none")) {
				modifier = item.getIntegers();
				modifier.write(0, this.getTouchSlimeIndex());
				modifier.write(1, (int) Math.floor(x * 32.0D));
				modifier.write(2, (int) Math.floor(y * 32.0D));
				modifier.write(3, (int) Math.floor(z * 32.0D));
				modifier.write(9, 70);

				String[] datas = message.split(":");
				int blockID = Integer.parseInt(datas[0]);
				byte data = datas.length > 1 ? Byte.valueOf(datas[1]) : 0;
				modifier.write(10, blockID | (data << 0x10));
				modifier = attach2.getIntegers();
				modifier.write(0, 0);
				modifier.write(1, item.getIntegers().read(0));
				attach2.getIntegers().write(2, horse.getIntegers().read(0));

			}
			pm.sendServerPacket(observer, horse);
			if (!message.equals("none"))  {
				pm.sendServerPacket(observer, item);
			}
			if (!message.equals("none")) {
				pm.sendServerPacket(observer, attach2);
			}
			if (attachPlayer != null) {
				PacketContainer attach3 = pm.createPacket(PacketType.Play.Server.ATTACH_ENTITY);
				modifier = attach3.getIntegers();
				modifier.write(0, 0);
				modifier.write(1, attachPlayer.getEntityId());

				modifier.write(2, message.equals("none") ? horse.getIntegers().read(0) : item.getIntegers().read(0));

				pm.sendServerPacket(observer, attach3);

			}
		} 
		catch (Exception ex) {

		
	}
	}
	public void genPre18(Player observer, String message, double diffY, double x, double y, double z) {
		try {
			PacketContainer attach = pm.createPacket(PacketType.Play.Server.ATTACH_ENTITY);
			PacketContainer attach2 = pm.createPacket(PacketType.Play.Server.ATTACH_ENTITY);
			PacketContainer horse = pm.createPacket(PacketType.Play.Server.SPAWN_ENTITY_LIVING);
			StructureModifier<Integer> modifier = horse.getIntegers();
			modifier.write(0,this.getHorseIndex());
			modifier.write(1,(int)EntityType.HORSE.getTypeId());
			modifier.write(2, (int) Math.floor(x* 32.0D));
			modifier.write(3, (int)Math.floor((y + diffY+ 41)* 32.0D));
			modifier.write(4, (int)Math.floor(z* 32.0D));

			WrappedDataWatcher dw = new WrappedDataWatcher();
			dw.setObject(0, Byte.valueOf((byte) 0x20));

			dw.setObject(10, "");
			dw.setObject(11, Byte.valueOf((byte) 0));

			dw.setObject(12, Integer.valueOf(-1696975));

			horse.getDataWatcherModifier().write(0,dw);

			PacketContainer skull = pm.createPacket(PacketType.Play.Server.SPAWN_ENTITY);
			modifier = skull.getIntegers();

			modifier.write(0, this.getSkullIndex());
			modifier.write(1, (int) Math.floor(x * 32.0D));
			modifier.write(2, (int) Math.floor((y +diffY + 41) * 32.0D));
			modifier.write(3, (int) Math.floor(z * 32.0D));
			modifier.write(9, 66);


			PacketContainer item = pm.createPacket(PacketType.Play.Server.SPAWN_ENTITY);

			if (!message.equals("none")) {
				modifier = item.getIntegers();
				modifier.write(0, this.getTouchSlimeIndex());
				modifier.write(1, (int) Math.floor(x * 32.0D));
				modifier.write(2, (int) Math.floor((y +diffY + 41) * 32.0D));
				modifier.write(3, (int) Math.floor(z * 32.0D));
				modifier.write(9, 70);

				String[] datas = message.split(":");
				int blockID = Integer.parseInt(datas[0]);
				byte data = datas.length > 1 ? Byte.valueOf(datas[1]) : 0;
				modifier.write(10, blockID | (data << 0x10));
				modifier = attach2.getIntegers();
				modifier.write(0, 0);
				modifier.write(1, item.getIntegers().read(0));
				attach2.getIntegers().write(2, horse.getIntegers().read(0));

			}

			modifier = attach.getIntegers();
			modifier.write(0, 0);
			modifier.write(1, horse.getIntegers().read(0));
			modifier.write(2, skull.getIntegers().read(0));

			pm.sendServerPacket(observer, horse);
			pm.sendServerPacket(observer, skull);
			pm.sendServerPacket(observer, attach);
			if (!message.equals("none"))  {
				pm.sendServerPacket(observer, item);
			}
			if (!message.equals("none")) {
				pm.sendServerPacket(observer, attach2);
			}
			if (attachPlayer != null) {
				PacketContainer attach3 = pm.createPacket(PacketType.Play.Server.ATTACH_ENTITY);
				modifier = attach3.getIntegers();
				modifier.write(0, 0);
				modifier.write(1, attachPlayer.getEntityId());

				modifier.write(2, message.equals("none") ? horse.getIntegers().read(0) : item.getIntegers().read(0));

				pm.sendServerPacket(observer, attach3);

			}
			pm.sendServerPacket(observer, attach);
		} 
		catch (Exception ex) {

		
	}
}
public void clearTags(Player observer, int... entityIds) {
	if (entityIds.length > 0) {
		PacketContainer packet = pm.createPacket(PacketType.Play.Server.ENTITY_DESTROY);
		packet.getIntegerArrays().write(0, entityIds);
		try {
			pm.sendServerPacket(observer, packet);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(
					"Cannot send packet " + packet, e);
		}

	}
}
public int[] getAllEntityIds() {
	int[] entityIdList = new int[2];
	for (int i = 0; i < 2; i++) {
		entityIdList[i] = this.getHorseIndex() + i;
	}

	return entityIdList;
}
protected void updateNametag(Player observer, String message) {
	y = Math.floor(y);
	this.id = message;
	clearTags(observer,this.getTouchSlimeIndex());
	PacketContainer attach2 = pm.createPacket(PacketType.Play.Server.ATTACH_ENTITY);
	PacketContainer item = pm.createPacket(PacketType.Play.Server.SPAWN_ENTITY);

	if (!message.equals("none")) {
		StructureModifier<Integer> modifier = item.getIntegers();
		modifier.write(0, this.getTouchSlimeIndex());
		modifier.write(1, (int) Math.floor(x * 32.0D));
		modifier.write(2, (int) (Math.floor(y + 41) * 32.0D));
		modifier.write(3, (int) Math.floor(z * 32.0D));
		modifier.write(9, 70);

		String[] datas = message.split(":");
		modifier.write(10, Integer.parseInt(datas[0])| (Byte.valueOf(datas[1]) << 0x10));
		modifier = attach2.getIntegers();
		modifier.write(0, 0);
		modifier.write(1, item.getIntegers().read(0));
		modifier.write(2, this.getHorseIndex());
		try {
			pm.sendServerPacket(observer, item);
			pm.sendServerPacket(observer, attach2);
		} catch (InvocationTargetException e) {
			// 
			e.printStackTrace();
		}

	}
}
public void moveTag(Location l) {
	this.x = l.getX();
	this.y = l.getY();
	this.z = l.getZ();
	for (Player p: Bukkit.getOnlinePlayers()) {
		moveTag(p);
	}
}
protected void moveTag(Player observer) {
	PacketContainer teleportSkull = pm.createPacket(PacketType.Play.Server.ENTITY_TELEPORT);
	StructureModifier<Integer> modifier = teleportSkull.getIntegers();
	if (((CraftPlayer)observer).getHandle().playerConnection.networkManager.getVersion() > 5) {
		
		modifier.write(0, getHorseIndex());
		modifier.write(1, (int) Math.floor( x* 32.0D));
		modifier.write(2, (int) Math.floor(y* 32.0D));
		modifier.write(3,(int) Math.floor( z * 32.0D));
	} else {
		
		modifier.write(0, getSkullIndex());
		modifier.write(1, (int) Math.floor( x* 32.0D));
		modifier.write(2, (int) Math.floor((y+41)* 32.0D));
		modifier.write(3,(int) Math.floor( z * 32.0D));
	}
	


	PacketContainer metadata = pm.createPacket(PacketType.Play.Server.ENTITY_METADATA);
	WrappedDataWatcher dw = new WrappedDataWatcher();
	dw.setObject(12, Integer.valueOf(-1716550));	

	metadata.getIntegers().write(0, getHorseIndex());
	metadata.getWatchableCollectionModifier().write(0, dw.getWatchableObjects());
	try {
		pm.sendServerPacket(observer, teleportSkull);
		if (((CraftPlayer)observer).getHandle().playerConnection.networkManager.getVersion() < 5) {
		pm.sendServerPacket(observer, metadata);
		}
	} catch (InvocationTargetException e) {
		// 
		e.printStackTrace();
	} 

}

public int getHorseIndex() {
	return entityId;
}
public int getSkullIndex() {
	return entityId+1;
}
public int getTouchSlimeIndex() {
	return entityId+2;
}
public void setVelocity(final Vector velocity) {			
	motion = velocity;
}

public void show(Player observer, Location location) {
	this.show(observer, location.getBlockX(), location.getBlockY(), location.getBlockZ());
}



public void show(Player observer, double x, double y, double z) {
	this.generate(observer, id, 0, x, y, z);
}
public Location getLocation() {
	return new Location(Bukkit.getWorld(worldName),x,y,z);
}
public Vector getVelocity() {
	return motion;
}
public Material getMaterial() {
	return Material.getMaterial(Integer.parseInt(id.split(":")[0]));
}
public byte getData() {
	String[] split = id.split(":");
	if (split.length < 2)
		return 0;
	return Byte.parseByte(split[1]);
}

}