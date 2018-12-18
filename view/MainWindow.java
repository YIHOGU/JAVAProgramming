package view;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.*;
import action.*;
public class MainWindow {
	private static JFrame mainFrame;
	private static JMenuBar mainMenuBar;
	private static JScrollPane mapPanel;
	private static Map map;
	public static JTextArea textArea = new JTextArea();
	public static JScrollPane record = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
		         JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
	public static JPanel logPanel=new JPanel();
	public static JButton btn = new JButton("AI");
	//public static JButton reset = new JButton("reset");
	public static int turn=0;
	private static JFrame initWindow() {
		JFrame frame = new JFrame();
		
		frame.setSize(new Dimension(1100,800));
		frame.setTitle("Trax Game");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		mainMenuBar = initMenu();
		mapPanel = initMap();
		logPanel=initLog();
		logPanel.setPreferredSize(new Dimension(300, 800));

		
		frame.getContentPane().add(mapPanel);
		frame.setJMenuBar(mainMenuBar);
		frame.add(logPanel, BorderLayout.EAST);
		frame.setVisible(true);
		
		return frame;
	}
	
	private static JMenuBar initMenu() {
		JMenuBar mb = new JMenuBar();
		JMenu m;
		JMenuItem mi;
		
		m = new JMenu("File");
		m.setMnemonic(KeyEvent.VK_F);
		mb.add(m);
		mi = new JMenuItem("Load Game");
		m.add(mi);
		mi = new JMenuItem("Save Game");
		m.add(mi);
		mi = new JMenuItem("Exit");
		m.addSeparator();
		m.add(mi);
		
		m = new JMenu("Game");
		m.setMnemonic(KeyEvent.VK_G);
		mb.add(m);
		mi=new JMenuItem("Reset Game");
		m.add(mi);
		
		m = new JMenu("Utility");
		m.setMnemonic(KeyEvent.VK_U);
		mb.add(m);
		
		m = new JMenu("Help");
		m.setMnemonic(KeyEvent.VK_H);
		mb.add(m);
		
		return mb;
	}
	private static JScrollPane initMap() {
		JScrollPane sp = new JScrollPane();
		JPanel pn = new JPanel();
		int x, y;
		
		map = new Map();
		pn.setLayout(new GridLayout(map.height, map.width));
		for(y=0; y<map.height; y++) {
			for(x=0; x<map.width; x++) {
				pn.add(map.tiles[y][x]);
			}
		}
		
		sp.setViewportView(pn);
		
		return sp;
	}

	private static JPanel initLog(){
		
		JPanel logPanel=new JPanel();
		textArea.setEditable(false);
		record.setPreferredSize(new Dimension(300, 600));
		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AI.setLocation(map, turn);
				
				turn++;
				TileJudge.first=0;
								
				if(turn!=1) {
					WinGame.winner(map.tiles[AI.tmp_y][AI.tmp_x]);
				}
				
				
				try {
					TileJudge.autoJudge(map.tiles[AI.tmp_y][AI.tmp_x]);
					
				}catch(Exception ex) {
					System.out.println("<!> AutoJudge Error...");
					return;
				}
				

				for(int i=0;i<63;i++) {
					for(int j=0;j<63;j++) {
						if(Map.gameData[i][j]!=0) {
							Map.cost[i][j]=0;
						}
					//	System.out.printf("%2d", Map.cost[i][j]);
					}
				//	  System.out.printf("\n");

				}
			}
			
		});
		/*
		reset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Map.gameData=new int [64][64];
				TileJudge.first=1;
				Map.last_x=Map.last_y=0;
				textArea.append("reset\n");
				int i, j;
				for(i=0; i<64; i++) {
					for(j=0; j<64; j++) {
						Map.tiles[i][j].status = 0;
						Map.tiles[i][j].setIcon(new ImageIcon("./res/0.png"));
						Map.tiles[i][j].setBorderPainted(false);
					}
				}
			}
			
		});
		*/ //reset 
		logPanel.add(record);
		logPanel.add(btn);
		//logPanel.add(reset);
	
		return logPanel;
		
		
	}
	
	public static void main (String[] args) {
		
		mainFrame = initWindow();
		mapPanel.getVerticalScrollBar().setValue(1030);
		mapPanel.getHorizontalScrollBar().setValue(1005);
	}
	
}