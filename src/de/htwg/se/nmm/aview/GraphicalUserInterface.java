package de.htwg.se.nmm.aview;

import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import de.htwg.se.nmm.controller.INmmController;
import de.htwg.se.nmm.util.observer.Event;
import de.htwg.se.nmm.util.observer.IObserver;

public class GraphicalUserInterface extends JFrame implements IObserver, MouseListener {
	
	private static final long serialVersionUID = -2199919969331826902L;
	
	private INmmController controller;
	private JLabel lastClickedField = null;
	JLabel[][] fields;
	
	public GraphicalUserInterface(INmmController controller) {
		this.controller = controller;
		this.controller.addObserver(this);
		this.initGui();
	}
	
	private void initGui() {
		
		// window preferences
		final int gameLen = 800;		
		final int fieldLen = gameLen / 15;		
		setTitle("Nine Men's Morris");
		setSize(gameLen, gameLen);	
		setResizable(false);
		//setUndecorated(true);
		
		try {
			
			// gameboard
			JLabel gamefieldLabel = new JLabel();
			add(gamefieldLabel);
			gamefieldLabel.setBounds(getBounds());							
			BufferedImage pic = ImageIO.read(new File("src/resource/gameboard.png"));
			Image image = pic.getScaledInstance(gameLen, gameLen, Image.SCALE_SMOOTH);
			ImageIcon icon = new ImageIcon(image);
			gamefieldLabel.setIcon(icon);
						
			fields = new JLabel[controller.grids()][controller.index()];			
			for (int g = 0; g < controller.grids(); g++) {				
				for (int i = 0; i < controller.index(); i++) {										
					JLabel field = new JLabel();									
					field.setName(String.valueOf(g) + String.valueOf(i));					
					//field.setText(field.getName());
					field.addMouseListener(this);
					fields[g][i] = field;					
					gamefieldLabel.add(field);						
					field.setSize(fieldLen, fieldLen);					
				}					
			}													
						
			double factor = 0.125;
			int x = (int)((gameLen * factor));
			int y = x;						
			int deltaY = 45;
			int deltaX = 5;
			fields[0][0].setLocation(x*1-deltaX, y-deltaY);
			fields[0][1].setLocation(x*4-deltaX, y-deltaY);
			fields[0][2].setLocation(x*7-deltaX, y-deltaY);
			fields[0][3].setLocation(x*7-deltaX, y*4-deltaY);
			fields[0][4].setLocation(x*7-deltaX, y*7-deltaY);
			fields[0][5].setLocation(x*4-deltaX, y*7-deltaY);
			fields[0][6].setLocation(x*1-deltaX, y*7-deltaY);
			fields[0][7].setLocation(x*1-deltaX, y*4-deltaY);
			
			fields[1][0].setLocation(x*2-deltaX, y*2-deltaY);
			fields[1][1].setLocation(x*4-deltaX, y*2-deltaY);
			fields[1][2].setLocation(x*6-deltaX, y*2-deltaY);
			fields[1][3].setLocation(x*6-deltaX, y*4-deltaY);
			fields[1][4].setLocation(x*6-deltaX, y*6-deltaY);
			fields[1][5].setLocation(x*4-deltaX, y*6-deltaY);
			fields[1][6].setLocation(x*2-deltaX, y*6-deltaY);
			fields[1][7].setLocation(x*2-deltaX, y*4-deltaY);
			
			fields[2][0].setLocation(x*3-deltaX, y*3-deltaY);
			fields[2][1].setLocation(x*4-deltaX, y*3-deltaY);
			fields[2][2].setLocation(x*5-deltaX, y*3-deltaY);
			fields[2][3].setLocation(x*5-deltaX, y*4-deltaY);
			fields[2][4].setLocation(x*5-deltaX, y*5-deltaY);
			fields[2][5].setLocation(x*4-deltaX, y*5-deltaY);
			fields[2][6].setLocation(x*3-deltaX, y*5-deltaY);
			fields[2][7].setLocation(x*3-deltaX, y*4-deltaY);
														
		} catch (IOException e) {			
			e.printStackTrace();
		}		
	}

	@Override
	public void update(Event e) {		
		BufferedImage blackToken;
		BufferedImage whiteToken;
		try {
			blackToken = ImageIO.read(new File("src/resource/blackToken.png"));
			whiteToken = ImageIO.read(new File("src/resource/whiteToken.png"));
		} catch (IOException e1) {			
			e1.printStackTrace();
			return;
		}
				
		for (int g = 0; g < controller.grids(); g++) {
			for (int i = 0; i < controller.index(); i++) {
				JLabel field = fields[g][i];				
				Image scaled = null;								
				if (controller.hasToken(g, i)) {
					BufferedImage image = null;
					if (controller.dumpColor(g, i) == "BLACK") {
						image = blackToken;
					} else {
						image = whiteToken;
					}					
					scaled = image.getScaledInstance(field.getWidth(), field.getHeight(), Image.SCALE_SMOOTH);
					field.setIcon(new ImageIcon(scaled));					
				} else {
					field.setIcon(null);
				}
																																													
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		assert(arg0.getSource() instanceof JLabel);		
		JLabel field = (JLabel)arg0.getSource();
		
		int grid = getGrid(field);
		int index = getIndex(field);
		
		if (controller.hasToken(grid, index)) {			
			if (controller.pickToken(grid, index)) {
				lastClickedField = null;
			} else {
				lastClickedField = field;
			}				
		} else {
			/* no token */
			if (lastClickedField == null) {
				controller.setToken(grid, index);
				lastClickedField = null;
			} else {
				int oldGrid = getGrid(lastClickedField);
				int oldIndex = getIndex(lastClickedField);
				controller.moveToken(oldGrid, oldIndex, grid, index);
				lastClickedField = null;
			}
		}							
	}
	
	private int getGrid(JLabel l) {
		return Character.getNumericValue(l.getName().charAt(0));
	}
	
	private int getIndex(JLabel l) {
		return Character.getNumericValue(l.getName().charAt(1));		
	}		

	@Override
	public void mouseEntered(MouseEvent arg0) { }
	@Override
	public void mouseExited(MouseEvent arg0) { }
	@Override
	public void mousePressed(MouseEvent arg0) { }
	@Override
	public void mouseReleased(MouseEvent arg0) { }	
}
