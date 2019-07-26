package main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import static utilities.SystemInformation.SCREEN_DIMENSION;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import utilities.Pair;

/**
 * Finestra che viene visualizzata se è la prima volta che viene aperto il gioco e mostra le istruzioni per giocare.
 * 
 * @author Gabriele Giunchi
 *
 */
public class InstructionFrame extends JFrame {
	
	private static final long serialVersionUID = 982692881301674802L;
	
	private static final String LABEL1_STRING = "Per muoversi usare i stati W,A,S,D";
	private static final String LABEL2_STRING = "Per sparare usare le frecce";
	private static final String LABEL3_STRING = "Premere ESC per mettere in pausa";
	
	private static final Pair<Integer, Integer> PROPORTION_X = new Pair<Integer, Integer>(5, 16);
	private static final Pair<Integer, Integer> PROPORTION_Y = new Pair<Integer, Integer>(3, 10);
	
	public InstructionFrame() {
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setLocationByPlatform(true);
		this.setSize(this.calculateDimension());
		this.setResizable(false);
		this.setLayout(new BorderLayout());
		
		final JButton ok = new JButton("Ho Capito");
		ok.addActionListener(e -> {
			Main.playGame();
			dispose();
		});
		final JPanel sud = new JPanel(new FlowLayout());
		sud.add(ok);
		
		this.getContentPane().add(sud, BorderLayout.SOUTH);
		
		final JLabel label1 = new JLabel(LABEL1_STRING);
		final JLabel label2 = new JLabel(LABEL2_STRING);
		final JLabel label3 = new JLabel(LABEL3_STRING);
		
		final JPanel nord = new JPanel(new GridLayout(0, 1));
		nord.add(this.create(label1));
		nord.add(this.create(label2));
		nord.add(this.create(label3));
		
		this.getContentPane().add(nord, BorderLayout.NORTH);
		
		
		this.validate();
		this.setVisible(true);
	}
	
	private Dimension calculateDimension() {
		final int x = Math.round((float) SCREEN_DIMENSION.getWidth() * PROPORTION_X.getFirst() / (float) PROPORTION_X.getSecond());
		final int y = Math.round((float) SCREEN_DIMENSION.getHeight() * PROPORTION_Y.getFirst() / (float) PROPORTION_Y.getSecond());
		
		return new Dimension(x, y);
		
	}
	
	private JPanel create(final JComponent component) {
		final JPanel panel = new JPanel(new FlowLayout());
		panel.add(component);
		return panel;
	}
}
