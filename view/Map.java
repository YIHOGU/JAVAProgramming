package view;
import java.awt.Dimension;
import javax.swing.*;
import action.*;

public class Map {
	public final int width = 64;
	public final int height = 64;
	public Tile[][] tiles; 
	// 	public static Tile[][] tiles; //for reset

	public static int gameData[][]=new int[64][64];
	public static int cost[][]=new int[64][64];
	public static int last_x=32, last_y=32;
	
	public int end_lx, end_rx, end_uy,end_dy;
	public Map() {
		int i, j;
		this.tiles = new Tile[this.height][this.width];
		
		for(i=0; i<this.height; i++) {
			for(j=0; j<this.width; j++) {
				this.tiles[i][j] = new Tile(j, i, this);
				this.tiles[i][j].status = 0;
				this.tiles[i][j].addMouseListener(new MouseAction());
				this.tiles[i][j].setIcon(new ImageIcon("./res/0.png"));
				this.tiles[i][j].setPreferredSize(new Dimension(44,44));
				this.tiles[i][j].setBorderPainted(false);
			}
		}

		if(TileJudge.first==1) {
			this.tiles[32][32].setIcon(new ImageIcon("./res/0_tmp.png"));
		}
	}
}
