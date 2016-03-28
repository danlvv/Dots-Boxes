package controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import view.GridSheet;

public class LabelListener implements MouseListener {
	
	private int rows;
	private int cols;
	
	
	public void setDimensions(int r, int c){
		rows = r;
		cols = c;
	}	
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		JLabel clickedLabel = (JLabel) e.getSource();
		
		for(int i=0; i<rows; i++){
			for(int j=0; j<cols; j++){
				if(clickedLabel.getName().equals(i + ", " + j)){
					if(i%2 == 0 || j%2 == 0){
						ImageIcon temp = null;
						if(i%2 != 0){
							temp = new ImageIcon(GridSheet.class.getResource("v_line.png"));
						}else if(i%2 == 0){
							temp = new ImageIcon(GridSheet.class.getResource("h_line.png"));
						}
						clickedLabel.setIcon(temp);
						clickedLabel.setName("taken: " + i + ", " + j);
						clickedLabel.setHorizontalAlignment(JLabel.CENTER);
					}
				}
			}
		}
		
		checkScored(clickedLabel);
	}
	
	public void checkScored(JLabel tempLabel){
		for(int i=0; i<rows; i++){
			for(int j=0; j<cols; j++){
				if(tempLabel.getName().equals("taken: " + i + ", " + j)){
					if(i%2 == 0){
						System.out.println("hor");
					}else if(j%2 == 0){
						System.out.println("ver");
					}
				}
			}
		}
	}
	
	
	/*
	 * Completely unused listeners...  
	 */
	
	
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
