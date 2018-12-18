package action;

import java.util.Random;

import javax.swing.ImageIcon;

import defs.tColor;
import defs.tDirection;
import view.*;

public class AI {
	private static Map map;
	public static int tmp_x, tmp_y, tmp_d=2;
	public static int cost_x, cost_y, cost_cnt;
	public static int location;
	public static int pre_x,pre_y,pre_status;
	
	static final tColor[][] colors = new tColor[][]{
		{tColor.NONE, tColor.NONE, tColor.NONE, tColor.NONE},
		{tColor.BLACK, tColor.WHITE, tColor.WHITE, tColor.BLACK},
		{tColor.BLACK, tColor.BLACK, tColor.WHITE, tColor.WHITE},
		{tColor.WHITE, tColor.BLACK, tColor.BLACK, tColor.WHITE},
		{tColor.WHITE, tColor.WHITE, tColor.BLACK, tColor.BLACK},
		{tColor.WHITE, tColor.BLACK, tColor.WHITE, tColor.BLACK},
		{tColor.BLACK, tColor.WHITE, tColor.BLACK, tColor.WHITE}
	};	
	
	public static void findLocation(int x, int y, int turn, Map map) { //가장 높거나 낮은 가중치중 첫번째 위치
		int i,j;
		int maxCost=-999,minCost=999;
		int max_x=-1,max_y=-1,min_x=-1,min_y=-1;
		int myTurn;; //0 : white, 1 :black
		int up,down,left,right;
		
		myTurn=turn%2;
		for(i=0;i<63;i++) {
			for(j=0;j<63;j++) {
				up = map.tiles[i][j].getAdjTile(tDirection.UP);
				down = map.tiles[i][j].getAdjTile(tDirection.DOWN);
				left = map.tiles[i][j].getAdjTile(tDirection.LEFT);
				right = map.tiles[i][j].getAdjTile(tDirection.RIGHT);
				if(((up==0||up==-1)&&(down==0||down==-1)&&(left==0||left==-1)&&(right==0||right==-1)))
					continue; //타일 착수조건:상하좌우에만 놓게 하기
				if(myTurn==0) {
					if((Map.gameData[i][j]==0)&&(Map.cost[i][j]>maxCost)) {
						System.out.println("white");
						maxCost=Map.cost[i][j];
						max_x=j;
						max_y=i;
					}
				}//if화이트
				else if(myTurn==1) {
					if((Map.gameData[i][j]==0)&&(Map.cost[i][j]<minCost)) {
						System.out.println("black");
						minCost=Map.cost[i][j];
						min_x=j;
						min_y=i;
					}
				}
			}//if 블랙
		}//for
		if(myTurn==0) {
			if(minCost==-2||maxCost==2) location=1;
			else location=2;
			tmp_x=max_x;
			tmp_y=max_y;
		}
		else if(myTurn==1){
			if(minCost==-2||maxCost==2) location=1;
			else location=2;
			tmp_x=min_x;
			tmp_y=min_y;
		}
	}//void findLocation
	
	public static void findWinLocation(int x, int y, int turn, Map map) { 
	
		int i,j;
		int maxCost=-999,minCost=999,kingCost=0;
		int max_x=-1,max_y=-1,min_x=-1,min_y=-1;
		int myTurn;; //0 : white, 1 :black
		int up,down,left,right;
		myTurn=turn%2;
		for(i=0;i<63;i++) {
			for(j=0;j<63;j++) {
				up = map.tiles[i][j].getAdjTile(tDirection.UP);
				down = map.tiles[i][j].getAdjTile(tDirection.DOWN);
				left = map.tiles[i][j].getAdjTile(tDirection.LEFT);
				right = map.tiles[i][j].getAdjTile(tDirection.RIGHT);
				if(((up==0||up==-1)&&(down==0||down==-1)&&(left==0||left==-1)&&(right==0||right==-1)))
					continue; 
				if(Map.gameData[i][j]==0)
				System.out.println(j+" , "+i+"  cost : "+Map.cost[i][j]);
				
				if(Map.gameData[i][j]==0 && Math.abs(Map.cost[i][j])>Math.abs(kingCost)) {
					kingCost = Map.cost[i][j];
					cost_x=j;
					cost_y=i;
				}
					
				if(myTurn==1) {
					if((Map.gameData[i][j]==0)&&(Map.cost[i][j]>maxCost)) {
						//System.out.println("white");
						maxCost=Map.cost[i][j];
						max_x=j;
						max_y=i;						
					}
				}//if�솕�씠�듃
				else if(myTurn==0) {
					if((Map.gameData[i][j]==0)&&(Map.cost[i][j]<minCost)) {
						//System.out.println("black");
						minCost=Map.cost[i][j];
						min_x=j;
						min_y=i;
					}
				}
			}//if
		}//for
		
		if(myTurn==0) {
			System.out.println(minCost+"  cost : "+kingCost);
			if(minCost+kingCost<=0) {
			tmp_x=min_x;
			tmp_y=min_y;
			}
			else {
				tmp_x=cost_x;
				tmp_y=cost_y;
				cost_cnt=1;
			}
		}
		else if(myTurn==1){
			System.out.println(maxCost+"  cost : "+kingCost);
			if(maxCost+kingCost>=0) {
				tmp_x=max_x;
				tmp_y=max_y;
			}
			else {
				tmp_x=cost_x;
				tmp_y=cost_y;
				cost_cnt=1;
			}
		}
		
	}//void findWinLocation
	
	public static void chooseType(int turn,Map map) {

		System.out.println("win");
		int up = map.tiles[tmp_y][tmp_x].getAdjTile(tDirection.UP);
		int down = map.tiles[tmp_y][tmp_x].getAdjTile(tDirection.DOWN);
		int left = map.tiles[tmp_y][tmp_x].getAdjTile(tDirection.LEFT);
		int right = map.tiles[tmp_y][tmp_x].getAdjTile(tDirection.RIGHT);
	
		int upCost=map.cost[tmp_y-1][tmp_x];
		int downCost=map.cost[tmp_y+1][tmp_x];
		int leftCost=map.cost[tmp_y][tmp_x-1];
		int rightCost=map.cost[tmp_y][tmp_x+1];
		
		int myTurn=turn%2; //0 : white 1: black
		int status=0;
		final tColor[] tmp_colors = new tColor[] {tColor.NONE,tColor.NONE,tColor.NONE,tColor.NONE};
		int [] types =new int[3];
		int direction_cnt=0, type_cnt=0, checking=0;;
		
		if(up!=0) {
			tmp_colors[0] = colors[up][2];
			direction_cnt++;
		}
		if(down!=0) {
			tmp_colors[2] = colors[down][0];
			direction_cnt++;
		}
		if(right!=0) {
			tmp_colors[1] = colors[right][3];
			direction_cnt++;
		}
		if(left!=0) {
			tmp_colors[3] = colors[left][1];
			direction_cnt++;
		}
		
		
		for(int i=1; i<=6; i++) {
			int cnt=0;
			for(int j=0; j<4; j++) {
				if( (tmp_colors[j]!=tColor.NONE) && (colors[i][j]==tmp_colors[j]) ) {
					cnt++;					
				}
			}
			
			if(cnt==direction_cnt) {
				types[type_cnt]=i;
				type_cnt++;
				
			}
		}
		
		int direction[] = new int[] {-1,-1,-1,-1};
		int max1=-1, max2=-1,max1_index=-1,max2_index=-1;
		
		if(myTurn==0) {
			
			if(up!=0) {
				for(int i=0; i<4; i++) {
					if(colors[up][i]==tColor.WHITE && i!=2) {
						direction[i]++;
					}
				}
			} 
			
			if(right!=0) {
				for(int i=0; i<4; i++) {
					if(colors[right][i]==tColor.WHITE && i!=3) {
						direction[i]++;
					}
				}
			} 
			
			if(down!=0) {
				for(int i=0; i<4; i++) {
					if(colors[down][i]==tColor.WHITE && i!=0) {
						direction[i]++;
					}
				}
			} 
			
			if(left!=0) {
				for(int i=0; i<4; i++) {
					if(colors[left][i]==tColor.WHITE && i!=1) {
						direction[i]++;
					}
				}
			} 
			
			if(map.gameData[tmp_y-1][tmp_x-1]!=0) {direction[0]++; direction[3]++;}
			
			if(map.gameData[tmp_y-1][tmp_x+1]!=0) {direction[0]++; direction[1]++;}

			if(map.gameData[tmp_y+1][tmp_x-1]!=0) {direction[2]++; direction[3]++;}
			
			if(map.gameData[tmp_y+1][tmp_x+1]!=0) {direction[2]++; direction[1]++;}
			
			for(int i=0; i<4; i++) {
				if(direction[i]>max1) {
					int temp = max1;
					max1 = direction[i];					
					max2= temp;
					
					int temp_index=max1_index;
					max1_index=i;
					max2_index=temp_index;
				}
				else if(direction[i]>max2) {
					max2 = direction[i];
					max2_index = i;
				}
			}
			
			if(max2_index>-1) {
				for(int i=0; i<type_cnt; i++) {				
					if( (colors[types[i]][max1_index]==tColor.WHITE) && (colors[types[i]][max2_index]==tColor.WHITE)) {
						tmp_d=types[i];
						checking++;
						return;
					}
				}		
			}
	
		}
		
		else if(myTurn==1){ //black
			if(up!=0) {
				for(int i=0; i<4; i++) {
					if(colors[up][i]==tColor.BLACK && i!=2) {
						direction[i]++;
					}
				}
			} 
			
			if(right!=0) {
				
				for(int i=0; i<4; i++) {
					if(colors[right][i]==tColor.BLACK && i!=3) {
						direction[i]++;
					}
				}
			} 
			
			if(down!=0) {
				for(int i=0; i<4; i++) {
					if(colors[down][i]==tColor.BLACK && i!=0) {
						direction[i]++;
					}
				}
			} 
			
			if(left!=0) {
				for(int i=0; i<4; i++) {
					if(colors[left][i]==tColor.BLACK && i!=1) {
						direction[i]++;
					}
				}
			} 
			
			if(map.gameData[tmp_y-1][tmp_x-1]!=0) {direction[0]++; direction[3]++;}
			
			if(map.gameData[tmp_y-1][tmp_x+1]!=0) {direction[0]++; direction[1]++;}

			if(map.gameData[tmp_y+1][tmp_x-1]!=0) {direction[2]++; direction[3]++;}
			
			if(map.gameData[tmp_y+1][tmp_x+1]!=0) {direction[2]++; direction[1]++;}
			
			for(int i=0; i<4; i++) {
				if(direction[i]>max1) {
					int temp = max1;
					max1 = direction[i];					
					max2= temp;
					
					int temp_index=max1_index;
					max1_index=i;
					max2_index=temp_index;
				}				
				
				else if(direction[i]>max2) {
					max2 = direction[i];
					max2_index = i;
				}

			}

			
			if(max2_index>-1) {
				for(int i=0; i<type_cnt; i++) {
					if( (colors[types[i]][max1_index]==tColor.BLACK) && (colors[types[i]][max2_index]==tColor.BLACK)) {
						tmp_d=types[i];
						checking++;
						return;
					}
				}
			}
			

		}
		
		if(checking==0) {
			for(int i=0; i<type_cnt; i++) {
				if(myTurn==0) {
					if(colors[types[i]][max1_index]==tColor.WHITE) {
						tmp_d=types[i];
						checking++;
						return;
					}
				}
				
				else {
					if(colors[types[i]][max1_index]==tColor.BLACK) {
						tmp_d=types[i];
						checking++;
						return;
					}
				}

			}
		}
		
		if(checking==0) {
			for(int i=0; i<type_cnt; i++) {
				if(myTurn==0) {
					if ((colors[types[i]][max1_index]==tColor.WHITE) || (colors[types[i]][max2_index]==tColor.WHITE && max2_index!=-1)) {
						tmp_d=types[i];
						checking++;
						return;
					}
				}
				
				else {
					if((colors[types[i]][max1_index]==tColor.BLACK) || (colors[types[i]][max2_index]==tColor.BLACK && max2_index!=-1)) {
						tmp_d=types[i];
						checking++;
						return;
					}
				}

			}
		}
		if(checking==0) {
			System.out.println("ERROR ERROR ERROR ERROR ERROR ERROR ERROR");
		}
	}
	
	public static void chooseType2(int turn,Map map) {
		int up = map.tiles[tmp_y][tmp_x].getAdjTile(tDirection.UP);
		int down = map.tiles[tmp_y][tmp_x].getAdjTile(tDirection.DOWN);
		int left = map.tiles[tmp_y][tmp_x].getAdjTile(tDirection.LEFT);
		int right = map.tiles[tmp_y][tmp_x].getAdjTile(tDirection.RIGHT);
	
		int upCost=map.cost[tmp_y-1][tmp_x];
		int downCost=map.cost[tmp_y+1][tmp_x];
		int leftCost=map.cost[tmp_y][tmp_x-1];
		int rightCost=map.cost[tmp_y][tmp_x+1];
		
		int myTurn=turn%2; //0 : white 1: black
		int status=0;
		if(myTurn==0) {//white
			if(up!=0) {
				if(left==0&&leftCost==0&&rightCost!=0) {
					tmp_d=1;
					return;
				}
				else if(right==0&&rightCost==0&&leftCost!=0) {
					tmp_d=2;
					return;
				}
			}
			else if(down!=0){
				if(left==0&&leftCost==0&&rightCost!=0) {
					tmp_d=4;
					return;
				}
				else if(right==0&&rightCost==0&&leftCost!=0) {
					tmp_d=3;
					return;
				}
			}
			else if(right!=0){
				if(up==0&&upCost==0&&downCost!=0) {
					tmp_d=2;
					return;
				}
				else if(down==0&&downCost==0&&upCost!=0) {
					tmp_d=3;
					return;
				}
			}
			else if(left!=0){
				if(up==0&&upCost==0&&downCost!=0) {
					tmp_d=1;
					return;
				}
				else if(down==0&&downCost==0&&upCost!=0) {
					tmp_d=4;
					return;
				}
			}
		}
		else if(myTurn==1){ //black
			if(up!=0) {
				if(left==0&&leftCost>rightCost) {
					tmp_d=3;
					return;
				}
				else if(right==0&&rightCost>leftCost) {
					tmp_d=4;
					return;
				}
			}
			else if(down!=0){
				if(left==0&&leftCost>rightCost) {
					tmp_d=2;
					return;
				}
				else if(right==0&&rightCost>leftCost) {
					tmp_d=1;
					return;
				}
			}
			else if(right!=0){
				if(up==0&&upCost>downCost) {
					tmp_d=4;
					return;
				}
				else if(down==0&&downCost>upCost) {
					tmp_d=1;
					return;
				}
			}
			else if(left!=0){
				if(up==0&&upCost>downCost) {
					tmp_d=3;
					return;
				}
				else if(down==0&&downCost>upCost) {
					tmp_d=2;
					return;
				}
			}
		}
	}
	
	public static void setLocation(Map map, int turn) {
		for(int i=0;i<64;i++) {
			for(int j=0;j<64;j++) {
				System.out.printf("%2d", Map.cost[i][j]);
			}
			System.out.println();
		}
		cost_cnt=0;
		int myTurn=turn%2;
		if(turn==0) {
			map.tiles[map.last_y][map.last_x].setStatus(5);
		}
		else {
			
				findWinLocation(map.last_x, map.last_y, turn, map);
			System.out.println(cost_cnt);
			
			if(cost_cnt==0) {

				chooseType(turn,map);
			}
			else {
				findLocation(map.last_x, map.last_y, turn, map);
				chooseType2(turn,map);
			}
			
			map.tiles[tmp_y][tmp_x].setStatus(tmp_d);
			
		}
		
		if(myTurn==0) {
			MainWindow.textArea.append("---------------------------------------------------------------\n");
			MainWindow.textArea.append("AI(WHITE) set location : "+"("+Map.last_x+","+Map.last_y+")"+"  Value = "+Map.gameData[Map.last_y][Map.last_x]+"\n");
			MainWindow.textArea.append("---------------------------------------------------------------\n");
		}
		else if(myTurn==1) {
			MainWindow.textArea.append("---------------------------------------------------------------\n");
			MainWindow.textArea.append("AI(BLACK) set location : "+"("+Map.last_x+","+Map.last_y+")"+"  Value = "+Map.gameData[Map.last_y][Map.last_x]+"\n");
			MainWindow.textArea.append("---------------------------------------------------------------\n");
		}
	}
}
