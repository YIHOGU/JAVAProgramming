package action;

import javax.swing.JOptionPane;

import defs.*;
import view.*;

public class WinGame {
	public static Tile u,p;
	public static tColor color=null;
	private static int hMin,hMax,sMin,sMax; 
	private static int hMin_x,hMax_x,sMin_y,sMax_y; 
	private static int curve;
	private static tDirection high1,side1,high2,side2;
	
	public static boolean GameEnd(Tile t) {
		int up = t.getAdjTile(tDirection.UP);
		int down = t.getAdjTile(tDirection.DOWN);
		int left = t.getAdjTile(tDirection.LEFT);
		int right = t.getAdjTile(tDirection.RIGHT);
		boolean x=false;
		
		
		u=t;
		hMin=t.y;
		hMax=t.y;
		sMin=t.x;
		sMax=t.x;
		
		hMin_x=t.x;
		hMax_x=t.x;
		sMin_y=t.y;
		sMax_y=t.y; 
		
		high1=null;
		side1=null;
		high2=null;
		side2=null;
		//system.out.println(""+t.x+","+t.y+"");
		if(TileJudge.colors[t.status][0] == TileJudge.colors[up][2]) {
			//system.out.println("up");
			p=t;
			x|=trace(t.map.tiles[t.y-1][t.x],TileJudge.colors[t.status][0]);
		}
		if(TileJudge.colors[t.status][2] == TileJudge.colors[down][0]) {
			//system.out.println("down");
			p=t;
			x|=trace(t.map.tiles[t.y+1][t.x],TileJudge.colors[t.status][2]);
		}
		if(TileJudge.colors[t.status][3] == TileJudge.colors[left][1]) {
			//system.out.println("left");
			p=t;
			x|=trace(t.map.tiles[t.y][t.x-1],TileJudge.colors[t.status][3]);
		}
		if(TileJudge.colors[t.status][1] == TileJudge.colors[right][3]) {
			//system.out.println("right");
			p=t;
			x|=trace(t.map.tiles[t.y][t.x+1],TileJudge.colors[t.status][1]);
		}
		//system.out.println("sMin="+sMin+" sMin_y="+sMin_y+","+" sMax="+sMax+" sMax_y="+sMax_y);
		//system.out.println("hMin="+hMin+" hMin_x="+hMin_x+","+" hMax="+hMax+" hMax_x="+hMax_x);
		//system.out.println("---------");
		
		return x;
	}


	private static boolean trace(Tile t,tColor c) {
		
		int up = t.getAdjTile(tDirection.UP);
		int down = t.getAdjTile(tDirection.DOWN);
		int left = t.getAdjTile(tDirection.LEFT);
		int right = t.getAdjTile(tDirection.RIGHT);
		int y_zeroCnt=0,x_zeroCnt=0;
		boolean x=false;
		tDirection direc;
		
		direc=dSeach(t,c);
			
		if(t==u) {
			color=c;
			return true;
		}
		
		if(t.x>=sMax) {
			sMax=t.x;
			sMax_y=t.y;
			side1=direc;
		}
		if(t.x<=sMin) {
			sMin=t.x;
			sMin_y=t.y;
			side2=direc;
		}
		if(t.y>=hMax) {
			hMax=t.y;
			hMax_x=t.x;
			high1=direc;
		}
		if(t.y<=hMin) {
			hMin=t.y;
			hMin_x=t.x;
			high2=direc;
		}
		
		if(TileJudge.colors[t.status][0]==TileJudge.colors[up][2]&&direc==tDirection.UP) {
			//system.out.println("up");
			p=t;
			x=trace(t.map.tiles[t.y-1][t.x],TileJudge.colors[t.status][0]);
		}
		else if( TileJudge.colors[t.status][2]==TileJudge.colors[down][0]&&direc==tDirection.DOWN) {
			//system.out.println("down");
			p=t;
			x=trace(t.map.tiles[t.y+1][t.x],TileJudge.colors[t.status][2]);
		}
		else if(TileJudge.colors[t.status][3]==TileJudge.colors[left][1]&&direc==tDirection.LEFT) {
			//system.out.println("left");
			p=t;
			x=trace(t.map.tiles[t.y][t.x-1],TileJudge.colors[t.status][3]);
		}
		else if( TileJudge.colors[t.status][1]==TileJudge.colors[right][3]&&direc==tDirection.RIGHT) {
			//system.out.println("right");
			p=t;
			x=trace(t.map.tiles[t.y][t.x+1],TileJudge.colors[t.status][1]);
		}
		
		if(sMax-sMin>=7) {
			if(Map.last_x==sMin) {
				sMin_y=Map.last_y;
			}
			else if(Map.last_x==sMax) {
				sMax_y=Map.last_y;
			}
			//system.out.printf("gamdData[%d][%d]=%d gamdData[%d][%d]=%d\n",sMin_y,sMin,Map.gameData[sMin_y][sMin],sMax_y,sMax,Map.gameData[sMax_y][sMax]);
			
			for(int i=0;i<64;i++) {
				if(sMin!=0||sMax!=63) {
					if((Map.gameData[i][sMin-1]==0)&&(Map.gameData[i][sMax+1]==0)) {
						y_zeroCnt++;
					}
				}
				else {
					if(sMin==0) {
						if((Map.gameData[i][sMax+1]==0)) {
							x_zeroCnt++;
						}
					}
					else if(sMax==63) {
						if((Map.gameData[i][sMin-1]==0)) {
							x_zeroCnt++;
						}
					}
				}
			}
			if((c==tColor.BLACK)
					&&(y_zeroCnt==64)
					&&(Map.gameData[sMin_y][sMin]==1||Map.gameData[sMin_y][sMin]==4||Map.gameData[sMin_y][sMin]==5)
					&&(Map.gameData[sMax_y][sMax]==2||Map.gameData[sMax_y][sMax]==3||Map.gameData[sMax_y][sMax]==5)) {
				//system.out.printf("gamdData[%d][%d]=%d gamdData[%d][%d]=%d\n",sMin_y,sMin,Map.gameData[sMin_y][sMin],sMax_y,sMax,Map.gameData[sMax_y][sMax]);
				color=c;
				//system.out.println(c);
				return true;
			}
			else if((c==tColor.WHITE)
					&&(y_zeroCnt==64)
					&&(Map.gameData[sMin_y][sMin]==2||Map.gameData[sMin_y][sMin]==3||Map.gameData[sMin_y][sMin]==6)
					&&(Map.gameData[sMax_y][sMax]==1||Map.gameData[sMax_y][sMax]==4||Map.gameData[sMax_y][sMax]==6)) {
				//system.out.printf("gamdData[%d][%d]=%d gamdData[%d][%d]=%d\n",sMin_y,sMin,Map.gameData[sMin_y][sMin],sMax_y,sMax,Map.gameData[sMax_y][sMax]);
				color=c;
				//system.out.println(c);
				return true;
			}
		}
		else if(hMax-hMin>=7) {
			if(Map.last_y==hMin) {
				hMin_x=Map.last_x;
			}
			else if(Map.last_y==hMax) {
				hMax_x=Map.last_x;
			}
			//system.out.printf("gamdData[%d][%d]=%d gamdData[%d][%d]=%d\n",hMin,hMin_x,Map.gameData[hMin][hMin_x],hMax,hMax_x,Map.gameData[hMax][hMax_x]);
			for(int i=0;i<64;i++) {
				if(hMin!=0||hMax!=63) {
					if((Map.gameData[hMin-1][i]==0)&&(Map.gameData[hMax+1][i]==0)) {
						x_zeroCnt++;
					}
				}
				else {
					if(hMin==0) {
						if((Map.gameData[hMax+1][i]==0)) {
							x_zeroCnt++;
						}
					}
					else if(hMax==63) {
						if((Map.gameData[hMin-1][i]==0)) {
							x_zeroCnt++;
						}
					}
				}
			}
			if((c==tColor.BLACK)
					&&(x_zeroCnt==64)
					&&(Map.gameData[hMin][hMin_x]==1||Map.gameData[hMin][hMin_x]==2||Map.gameData[hMin][hMin_x]==6)
					&&(Map.gameData[hMax][hMax_x]==3||Map.gameData[hMax][hMax_x]==4||Map.gameData[hMax][hMax_x]==6)) {
			//system.out.printf("gamdData[%d][%d]=%d gamdData[%d][%d]=%d\n",sMin_y,sMin,Map.gameData[sMin_y][sMin],sMax_y,sMax,Map.gameData[sMax_y][sMax]);
			color=c;
			
			//system.out.println(c);
			return true;
			}
			else if((c==tColor.WHITE)
					&&(x_zeroCnt==64)
					&&(Map.gameData[hMin][hMin_x]==3||Map.gameData[hMin][hMin_x]==4||Map.gameData[hMin][hMin_x]==5)
					&&(Map.gameData[hMax][hMax_x]==1||Map.gameData[hMax][hMax_x]==2||Map.gameData[hMax][hMax_x]==5)) {
				//system.out.printf("gamdData[%d][%d]=%d gamdData[%d][%d]=%d\n",sMin_y,sMin,Map.gameData[sMin_y][sMin],sMax_y,sMax,Map.gameData[sMax_y][sMax]);
				color=c;
				//system.out.println(c);
				return true;
			}
		}
		return x;
	}
	
	private static tDirection dSeach(Tile t,tColor c) {
		int up = t.getAdjTile(tDirection.UP);
		int down = t.getAdjTile(tDirection.DOWN);
		int left = t.getAdjTile(tDirection.LEFT);
		int right = t.getAdjTile(tDirection.RIGHT);
		
		if(TileJudge.colors[t.status][0] == c &&t.map.tiles[t.y-1][t.x]!=p){
			return tDirection.UP;
		}
		else if(TileJudge.colors[t.status][2]==c&&t.map.tiles[t.y+1][t.x]!=p) {
			return tDirection.DOWN;
		}
		else if(TileJudge.colors[t.status][3] ==c&&t.map.tiles[t.y][t.x-1]!=p) {
			return tDirection.LEFT;
		}
		else if(TileJudge.colors[t.status][1] ==c&&t.map.tiles[t.y][t.x+1]!=p) {
			return tDirection.RIGHT;
		}
		return null;
	}
	
	public static void winner(Tile t) {
		
		if(GameEnd(t)) {
			if(color==tColor.WHITE) {
				JOptionPane.showMessageDialog(null, "White Win");
			}
			else if (color==tColor.BLACK) {
				JOptionPane.showMessageDialog(null, "Black Win");
			}
		}
	}
	public static int predict(Tile t) {
		if(!GameEnd(t)) {
			if(color==tColor.WHITE) {
				return 0;
			}
			else if (color==tColor.BLACK) {
				return 1;
			}
		}
		return 2;
	}
}
