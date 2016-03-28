package model;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import view.GridSheet;

public class State {
	private boolean taken[][];
	private JLabel moves[][];
	private int rows;
	private int cols;
	private String lastScored;
	
	private String player1;
	private String player2;
	
	private int p1_score;
	private int p2_score;
	
	/*
	 *  Setup of the state itself.
	 */
	
	public void setDimensions(int r, int c, String p1, String p2){
		this.rows = r;
		this.cols = c;
		lastScored = "";
		
		player1 = p1;
		player2 = p2;
		
		p1_score = 0;
		p2_score = 0;
	}
	
	public void setLabels(JLabel[][] curLabels){
		moves = new JLabel[cols][rows];
		for(int i=0; i<cols; i++){
			for(int j=0; j<rows; j++){
				moves[i][j] = curLabels[i][j];
			}
		}
	}
	
	public boolean playerScored(String player){
		if(player.equals(lastScored))
			return true;
		return false;
	}
	
	public void update(JLabel[][] curLabels, boolean[][] takenSpots){
		setLabels(curLabels);
		setTaken(takenSpots);
	}
	
	public void setTaken(boolean[][] takenSpots){
		taken = new boolean[(cols+1)/2][(rows+1)/2];
		for(int i=0; i<(cols/2); i++){
			for(int j=0; j<(rows/2); j++){
				taken[i][j] = takenSpots[i][j];
			}
		}
	}
	
	public JLabel getLabel(int x, int y){
		return moves[x][y];
	}
	
	public boolean isTaken(int x, int y){
		if(!taken[x][y])
			return true;
		
		return false;
	}
	
	public void setLabelName(int x, int y, String name){
		moves[x][y].setName(name);
	}
	
	/*
	 *  State actions for the AI portion.
	 */
	
	public void move(int x, int y){
		ImageIcon temp = null;
		
		if(y%2 != 0){
			temp = new ImageIcon(GridSheet.class.getResource("h_line.png"));
		}else{
			temp = new ImageIcon(GridSheet.class.getResource("v_line.png"));
		}
		
		moves[x][y].setIcon(temp);
		moves[x][y].setName("taken");
		moves[x][y].setHorizontalAlignment(JLabel.CENTER);
		
		checkScored("C");
	}
	
	public boolean canMove(int x, int y){
		if(x%2 != 0 && y%2 != 0){
			return false;
		}
		if(moves[x][y].getName().equals("taken") || moves[x][y].getName().equals("dot")){
			return false;
		}				
		
		return true;
	}
	
	public int getScore(String player){
		if(player.equals(player1)){
			return p1_score;
		}
		return p2_score;
	}
	
	public void checkScored(String player) {
		lastScored = "";
		for(int i=0; i<rows; i++){
			for(int j=0; j<cols; j++){
				if(i%2 != 0 || j%2 != 0){
					if(moves[i][j].getName().equals("taken")){
						if(i%2 == 0){
							if(i < rows - 2 && moves[i+2][j].getName().equals("taken")){
								if(j < cols - 1 && moves[i+1][j-1].getName().equals("taken")){
									if(moves[i+1][j+1].getName().equals("taken")){
										if(!moves[i+1][j].getName().equals("taken")){
											moves[i+1][j].setText(player);
											moves[i+1][j].setName("taken");
											moves[i+1][j].setHorizontalAlignment(JLabel.CENTER);
											taken[j/2][i/2] = true;
											lastScored = player;
											
											if(player.equals(player1)){
												p1_score++;
											}else if(player.equals(player2)){
												p2_score++;
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}
}

