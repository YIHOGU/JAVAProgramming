package view;

import javax.swing.*;
import defs.*;

public class Tile extends JButton{
	
	public int status; // 0 = Empty, 1~6 = tile01~06
	public int visit;
	public final int x, y;
	public final Map map;
	
	public Tile(int x, int y, Map map) {
		super();
		
		this.x = x;
		this.y = y;
		this.map = map;
	}
	
	public int getAdjTile(tDirection d) {
		switch(d) {
		case UP:
			if(y <= 0)	return -1;
			return this.map.tiles[y-1][x].status;
		case RIGHT:
			if(x >= map.width)	return -1;
			return this.map.tiles[y][x+1].status;
		case DOWN:
			if(y >= map.height)	return -1;
			return this.map.tiles[y+1][x].status;
		case LEFT:
			if(x <= 0)	return -1;
			return this.map.tiles[y][x-1].status;
		default:
			return -1;
		}
	}
	
	public boolean setStatus(int status) {
		if(status < 0 || status > 6)	return false;
		
		int i,j;
		this.status = status;
		this.visit=1;
		this.setIcon(new ImageIcon("./res/" + status +".png"));
		Map.gameData[y][x]=status;
		Map.last_x=x;
		Map.last_y=y;
		
		if(status==1) {
			Map.cost[y-1][x-1]++;
			Map.cost[y-1][x]++;
			Map.cost[y][x-1]++;
			
			Map.cost[y+1][x+1]--;
			Map.cost[y+1][x]--;
			Map.cost[y][x+1]--;
		}
		else if(status==2) {
			Map.cost[y-1][x+1]++;
			Map.cost[y][x+1]++;
			Map.cost[y-1][x]++;
			
			Map.cost[y+1][x-1]--;
			Map.cost[y][x-1]--;
			Map.cost[y+1][x]--;
		}
		else if(status==3) {
			Map.cost[y+1][x+1]++;
			Map.cost[y+1][x]++;
			Map.cost[y][x+1]++;
			
			Map.cost[y-1][x-1]--;
			Map.cost[y-1][x]--;
			Map.cost[y][x-1]--;
		}
		else if(status==4) {
			Map.cost[y+1][x]++;
			Map.cost[y+1][x-1]++;
			Map.cost[y][x-1]++;
			
			Map.cost[y-1][x+1]--;
			Map.cost[y-1][x]--;
			Map.cost[y][x+1]--;
		}
		else if(status==5) {
			Map.cost[y][x-1]++;
			Map.cost[y][x+1]++;
			
			Map.cost[y+1][x]--;
			Map.cost[y-1][x]--;
		}
		else if(status==6) {
			Map.cost[y+1][x]++;
			Map.cost[y-1][x]++;
			
			Map.cost[y][x-1]--;
			Map.cost[y][x+1]--;
		}
		
		return true;
	}
	
}
