package view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import model.State;

public class GridSheet extends JFrame implements MouseListener{
	private JLabel[][] gridLabels;
	private int rows;
	private int cols;
	private boolean[][] taken;
	private State current;
	
	private JPanel gameBoard;
	private JPanel infoBoard;
	
	private JLabel p1_Score;
	private JLabel p2_Score;
	
	private JLabel player1;
	private JLabel player2;
	
	
	public GridSheet(int rows, int cols, String player1, String player2){
		setTitle("Dots && Boxes");
		
		gameBoard = new JPanel(new GridLayout(cols,rows));
		infoBoard = new JPanel(new GridLayout(2,1));
		
		setLayout(new GridLayout(1, 2));
		gameBoard.setPreferredSize(new Dimension(cols*30, rows*30));
//		setPreferredSize(new Dimension(cols*30, rows*30));
		
		this.rows = rows;
		this.cols = cols;
		gridLabels = new JLabel[cols][rows];
		taken = new boolean[(cols+1)/2][(rows+1)/2];
		current = new State();
		
		for(int i=0; i<rows; i++){
			for(int j=0; j<cols; j++){
				taken[i/2][j/2] = false;
				if(i%2 == 0 && j%2 == 0){
					gridLabels[i][j] = new JLabel("*", JLabel.CENTER);	
					gridLabels[i][j].setName("dot");
				}else{
					gridLabels[i][j] = new JLabel("");
					gridLabels[i][j].setName(i + ", " + j);
//					LabelListener temp = new LabelListener();
//					temp.setDimensions(rows, cols);
					gridLabels[i][j].addMouseListener(this);
				}
				gameBoard.add(gridLabels[i][j]);
			}
		}
		
		/*
		 *  Info board stuff.
		 */
		
		JPanel p1 = new JPanel(new GridLayout(1,2));
		JPanel p2 = new JPanel(new GridLayout(1,2));
		
		this.player1 = new JLabel(player1 + " :           ", JLabel.RIGHT);
		this.player2 = new JLabel(player2 + " :           ", JLabel.RIGHT);
		
		p1.add(this.player1);
		p2.add(this.player2);
		
		this.p1_Score = new JLabel(0 + "", JLabel.LEFT);
		this.p2_Score = new JLabel(0 + "", JLabel.LEFT);
		
		p1.add(this.p1_Score);
		p2.add(this.p2_Score);
		
		infoBoard.add(p1);
		infoBoard.add(p2);
		
		
		this.add(gameBoard);
		this.add(infoBoard);
		
		current.setDimensions(rows, cols, player1, player2);
		current.update(gridLabels, taken);
//		current.setTaken(taken);	
		
		setVisible(true);
		pack();
	}

	// AI move must go after the score check otherwise score checker breaks;
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		JLabel clickedLabel = (JLabel) e.getSource();
		
		for(int i=0; i<rows; i++){
			for(int j=0; j<cols; j++){
				if(clickedLabel.getName().equals(i + ", " + j) ||
						clickedLabel.getName().equals("hover: " + i + ", " + j)) {
					if(i%2 == 0 || j%2 == 0){
						ImageIcon temp = null;
						if(i%2 != 0){
							temp = new ImageIcon(GridSheet.class.getResource("v_line.png"));
						}else{
							temp = new ImageIcon(GridSheet.class.getResource("h_line.png"));
						}
						clickedLabel.setIcon(temp);
						clickedLabel.setName("taken");
						clickedLabel.setHorizontalAlignment(JLabel.CENTER);
					}
				}
			}
		}
		
		current.checkScored("D");
		updateScore();
		if(checkEndgame()){
			JOptionPane.showConfirmDialog(new JFrame(), "Game is over.");
		}
			
		
		if(!current.playerScored("D")){
			// AI move must go here.
			Random rand = new Random();
			
			int x = rand.nextInt(cols);
			int y = rand.nextInt(rows);
			
			while(!current.canMove(x,y)){
				x = rand.nextInt(cols);
				y = rand.nextInt(rows);
	//			System.out.println("invalid");
			}
			current.move(x,y);
			updateScore();
			
			while(current.playerScored("C")){
				x = rand.nextInt(cols);
				y = rand.nextInt(rows);
				
				while(!current.canMove(x,y)){
					x = rand.nextInt(cols);
					y = rand.nextInt(rows);
		//			System.out.println("invalid");
				}
				current.move(x,y);
				updateScore();
			}
		}
			
	}
	
	public void updateScore(){
		p1_Score.setText(current.getScore("D") + "");
		p2_Score.setText(current.getScore("C") + "");
	}
	
	public boolean checkEndgame(){
		int x = cols/2;
		int y = rows/2;
		
		for(int i=0; i<x; i++){
			for(int j=0; j<y; j++){
				if(taken[i][j] == false)
					return false;
			}
		}
		
		return true;
	}
	

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		JLabel hoverLabel = (JLabel) e.getSource();
		
		for(int i=0; i<rows; i++){
			for(int j=0; j<cols; j++){
				if(hoverLabel.getName().equals(i + ", " + j)){
					if(i%2 == 0 || j%2 == 0){
						ImageIcon temp = null;
						if(i%2 != 0){
							temp = new ImageIcon(GridSheet.class.getResource("v_line.png"));
						}else{
							temp = new ImageIcon(GridSheet.class.getResource("h_line.png"));
						}
						hoverLabel.setIcon(temp);
						hoverLabel.setName("hover: " + i + ", " + j);
						hoverLabel.setHorizontalAlignment(JLabel.CENTER);
					}
				}
			}
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		JLabel hoverLabel = (JLabel) e.getSource();
		
		for(int i=0; i<rows; i++){
			for(int j=0; j<cols; j++){
				if(gridLabels[i][j].getName().equals("hover: " + i + ", " + j)){
//					hoverLabel.setIcon(null);
//					hoverLabel.setName(i + ", " + j);
					gridLabels[i][j].setIcon(null);
					gridLabels[i][j].setName(i + ", " + j);
				}
			}
		}
		
	}
	
	/*
	 *  The last two don't matter. They are arbitrarily useless.
	 */

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
