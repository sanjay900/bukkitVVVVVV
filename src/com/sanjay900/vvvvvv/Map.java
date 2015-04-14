package com.sanjay900.vvvvvv;

import java.util.Arrays;

import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

public class Map {
	public String Title;
	public String Desc1;
	public String Desc2;
	public String Desc3;
	public String website;
	public int width;
	public int height;
	public int musicId;
	public int[] levelContents;
	public int[] levelTileSets;
	public int[][][][] lvlSContent;
	public Map(String Title, String Desc1, String Desc2, String Desc3,
			String website, int width, int height, int musicId, int[] levelContents, int[] levelTileSets) {
		this.Title = Title;
		this.Desc1 = Desc1;
		this.Desc2 = Desc2;
		this.Desc3 = Desc3;
		this.width = width;
		this.height = height;
		this.musicId = musicId;
		this.levelContents = levelContents;
		this.lvlSContent = new int[width][height][40][30];
		this.levelTileSets = levelTileSets;
		divide();
	}
	public void divide() {
		int maxwidth = width*40;
		for (int i = 0; i < levelContents.length; i++) {
			int cy = Math.round(i/maxwidth);
			int cx = i > (maxwidth-1)?i-(maxwidth*cy):i;
			int lvly = cy/30;
			int lvlx = cx/40;
			int x = cx-(lvlx*40);
			int y = cy-(lvly*30);
			
			lvlSContent[lvlx][lvly][x][y] = levelContents[i];
		}
	}
	public void debug() {
		System.out.println("Title: "+Title);
		System.out.println("Desc1: "+Desc1);
		System.out.println("Desc2: "+Desc2);
		System.out.println("Desc3: "+Desc3);
		System.out.println("width: "+String.valueOf(height));
		System.out.println("height: "+String.valueOf(width));
		System.out.println("song: "+getSongName());
		System.out.println(Arrays.asList(levelContents).toString());
	}
	public String getSongName(){
		return String.valueOf(musicId);
	}
	//loc = bottom left corner
	public void draw(Location loc) {
			for (int lvlx = 0; lvlx < lvlSContent.length; lvlx++) {
				for (int lvly = 0; lvly < lvlSContent[lvlx].length; lvly++) {
					draw(loc.clone(), lvlx, lvly);
				}
			}
		
	}
	@SuppressWarnings("deprecation")
	public void draw(Location loc, int lvlx, int lvly) {
		//get top left corner
		loc = loc.add(0, height*30, 0);

			Location loc3 = loc.clone().add(lvlx*40, -(lvly*30), 0);
			for (int x = 0; x < 40; x++) {
				for (int y = 0; y < 30; y++) {
					int foreground = getColor(lvlSContent[lvlx][lvly][x][y]);
					int background = getBack(lvlSContent[lvlx][lvly][x][y]);
					if (levelTileSets[lvlx+lvly*20]==1) {
						foreground = getColor2(lvlSContent[lvlx][lvly][x][y]);
						background = getBack2(lvlSContent[lvlx][lvly][x][y]);
					} 
					Block b = loc3.clone().add(x,-y,0).getBlock();
					b.setType(getMat(foreground));
					b.setData((byte) getmtColor(foreground));
					Block b2 = b.getRelative(BlockFace.NORTH);
					b2.setType(getMat(background));
					b2.setData((byte) getmtColor(background));
				}
			}
			
			
		
	}

	public Material getMat(int i) {
		if (i == -1) {
			return Material.AIR;
		}
		if (i < 16) {
			return Material.WOOL;
		}
		if (i < 32) {
			return Material.STAINED_CLAY;
		}
		return Material.STAINED_GLASS;
	}
	public int getmtColor(int i) {
		if (i == -1) {
			return 0;
		}
		if (i < 16) {
			return i;
		}
		if (i < 32) {
			return i-16;
		}
		return i-32;
	}
	@SuppressWarnings({ "deprecation"})
	public int getColor(int vColor) {
		int color = -1;
		int y = vColor > 39?Math.round(vColor/40):0;
		int x = vColor > 39?vColor-(40*y):vColor;
		int val = x/3;
		if (y > 1 && y < 6) {
			switch (val+1) {
			case 1:
				color = DyeColor.SILVER.getWoolData();
				break;
			case 2:
				color= DyeColor.BLUE.getWoolData();
				break;
			case 3:
				color = DyeColor.MAGENTA.getWoolData();
				break;
			case 4:
				color = DyeColor.RED.getWoolData();
				break;
			case 5:
				color = DyeColor.YELLOW.getWoolData();
				break;
			case 6:
				color = DyeColor.LIME.getWoolData();
				break;
			case 7:
				color = DyeColor.BLUE.getWoolData();
				break;
			case 8:
				color = DyeColor.RED.getWoolData();
				break;
			case 9:
				color = DyeColor.BLUE.getWoolData();
				break;
			case 10:
				color = DyeColor.LIME.getWoolData();
				break;
			case 11:
				color = DyeColor.CYAN.getWoolData();
				break;
			case 12:
				color = DyeColor.MAGENTA.getWoolData();
				break;
			case 13:
				color = 32+ DyeColor.BLUE.getWoolData();
			case 14:
				switch (y) {
				case 2:
				case 6:
					color = DyeColor.MAGENTA.getWoolData();
					break;
				case 3:
					color = DyeColor.RED.getWoolData();
					break;
				case 4:
				case 5:
					color = DyeColor.YELLOW.getWoolData();
					break;
				
					
				}
			}
		}
		if (y > 5 && y < 12) {
			switch (val+1) {
			case 1:
				color = DyeColor.GRAY.getWoolData();
				break;
			case 2:
				color= DyeColor.RED.getWoolData();
				break;
			case 3:
				color = DyeColor.GREEN.getWoolData();
				break;
			case 4:
				color = DyeColor.LIGHT_BLUE.getWoolData();
				break;
			case 5:
				color = DyeColor.PURPLE.getWoolData();
				break;
			case 6:
				color = DyeColor.MAGENTA.getWoolData();
				break;
			case 7:
				color = DyeColor.YELLOW.getWoolData();
				break;
			case 8:
				color = DyeColor.CYAN.getWoolData();
				break;
			case 9:
				color = DyeColor.PURPLE.getWoolData();
				break;
			case 10:
				color = DyeColor.RED.getWoolData();
				break;
			case 11:
				color = DyeColor.ORANGE.getWoolData();
				break;
			case 12:
				color = DyeColor.LIME.getWoolData();
				break;
			case 13:
				color = 32+ DyeColor.RED.getWoolData();
			case 14:
				switch (y) {
				case 7:
					color = DyeColor.YELLOW.getWoolData();
					break;
				case 8:
					color = DyeColor.CYAN.getWoolData();
					break;
				
					
				}
			}
		}
		if (y > 11 && y < 18) {
			switch (val+1) {
			case 1:
				color = DyeColor.MAGENTA.getWoolData();
				break;
			case 2:
				color= DyeColor.RED.getWoolData();
				break;
			case 3:
				color = DyeColor.YELLOW.getWoolData();
				break;
			case 4:
				color = DyeColor.LIME.getWoolData();
				break;
			case 5:
				color = DyeColor.LIGHT_BLUE.getWoolData();
				break;
			case 6:
				color = DyeColor.PURPLE.getWoolData();
				break;
			case 7:
				color = DyeColor.RED.getWoolData();
				break;
			case 8:
				color = DyeColor.LIME.getWoolData();
				break;
			case 9:
				color = DyeColor.ORANGE.getWoolData();
				break;
			case 10:
				color = DyeColor.GREEN.getWoolData();
				break;
			case 11:
				color = DyeColor.MAGENTA.getWoolData();
				break;
			case 12:
				color = 32+DyeColor.CYAN.getWoolData();
				break;
			case 13:
				color = 32+ DyeColor.MAGENTA.getWoolData();
			}
		}
		
		
		
		return color;
	}
	

	@SuppressWarnings("deprecation")
	private int getBack(int vColor) {
		int color = 15;
		int y = vColor > 39?Math.round(vColor/40):0;
		int x = vColor > 39?vColor-(40*y):vColor;
		int val = x/3;
		if (y > 17 &&  y < 24) {
			
			switch (val+1) {
			case 1:
				color = 16+DyeColor.BLUE.getWoolData();
				break;
			case 2:
				color= 16+DyeColor.YELLOW.getWoolData();
				break;
			case 3:
				color = 16+DyeColor.CYAN.getWoolData();
				break;
			case 4:
				color = 16+DyeColor.LIME.getWoolData();
				break;
			case 5:
				color = 16+DyeColor.ORANGE.getWoolData();
				break;
			case 6:
				color = 16+DyeColor.RED.getWoolData();
				break;
			case 7:
				color = 16+DyeColor.MAGENTA.getWoolData();
				break;
			case 8:
				color = 16+DyeColor.PURPLE.getWoolData();
				break;
			case 9:
				color = 16+DyeColor.CYAN.getWoolData();
				break;
			}
			
		
	
		
		}
		
		return color;
	}
	@SuppressWarnings("deprecation")
	public int getColor2(int vColor) {
		int color = -1;
		int y = vColor > 39?Math.round(vColor/40):0;
		int x = vColor > 39?vColor-(40*y):vColor;
		int val = x/3;
		if (y > 1 && y < 6) {
			switch (val+1) {
			case 1:
				color = DyeColor.CYAN.getWoolData();
				break;
			case 2:
				color = DyeColor.RED.getWoolData();
				break;
			case 3:
				color = DyeColor.MAGENTA.getWoolData();
				break;
			case 4:
				color = DyeColor.PURPLE.getWoolData();
				break;
			case 5:
				color = DyeColor.YELLOW.getWoolData();
				break;
			case 6:
				color = DyeColor.GREEN.getWoolData();
				break;
			case 7:
				color = DyeColor.SILVER.getWoolData();
				break;
			case 8: 
				color = DyeColor.LIME.getWoolData();
				break;
			case 9:
				color = DyeColor.LIGHT_BLUE.getWoolData();
				break;
			case 10:
				color = DyeColor.YELLOW.getWoolData();
				break;
			case 11:
				color = DyeColor.MAGENTA.getWoolData();
				break;
			case 12:
				color = DyeColor.BLUE.getWoolData();
				break;
			case 13:
				color = DyeColor.RED.getWoolData();
				break;
				
			}
		}
		if (y > 11 && y < 18) {
			switch (val+1) {
			case 1:
				color = DyeColor.LIGHT_BLUE.getWoolData()+32;
				break;
			case 2:
				color = DyeColor.RED.getWoolData()+32;
				break;
			case 3:
				color = DyeColor.CYAN.getWoolData()+32;
				break;
			case 4:
				color = DyeColor.MAGENTA.getWoolData()+32;
				break;
			case 5:
				color = DyeColor.YELLOW.getWoolData()+32;
				break;
			case 6:
				color = DyeColor.LIME.getWoolData()+32;
				break;
			case 7:
				color = DyeColor.PINK.getWoolData()+32;
				break;
			case 8:
				color = DyeColor.ORANGE.getWoolData()+32;
				break;
			}
		}
		if (color == -1 && x !=0 && y!=0) {
			System.out.print(x);
			System.out.print(y);
		}
		return color;
	}
	private int getBack2(int vColor) {
		int color = 15;
		int y = vColor > 39?Math.round(vColor/40):0;
		int x = vColor > 39?vColor-(40*y):vColor;
		
		return color;
	}
	
}
