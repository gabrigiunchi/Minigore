package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import static view.MyJButton.createButton;
import static utilities.SystemInformation.PANELS_FOLDER;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import utilities.FunnyStrings;
import static view.CreateLabel.createLabel;
import controller.ViewObserver;

/**
 * Lo scopo di questa classe è di realizzare un panel da visualizzare alla fine di una partita.
 * Estende il BasePanel per il layout e di conseguenza l'ImagePanel per l'immagine di sfondo.
 * 
 * @author Mattia Ricci 
 *
 */

public class GameOverPanel extends BasePanel {

	private static final long serialVersionUID = 1L;
	private static final int STYLE = 5;
	private static final int BIG_DIM = 45;
	private static final int LITTLE_DIM = 20;
	private final JButton retry;
	private final JButton menu;
	private final JButton quit;
	private final JLabel score;
	private ViewObserver controller; 
	private final JPanel upPanel;
	private final JPanel downPanel;
	private final JLabel string;
	
	/**
	 * Questo panel imposta la propria immagine di sfondo, posiziona tre bottoni nel centro del panel
	 * e due label (uno per la visualizzazione del punteggio finale e uno per stampare stringhe passate
	 * da una classe a parte).
	 * 
	 * @param controller 
	 * 					  alla pressione dei bottoni il controllo viene passato al controller 
	 * @param score
	 * 					  il punteggio finale della partita
	 * 
	 * @param parentFrame
	 * 					   il panel da cui è stato chiamato questo panel
	 */
	
	public GameOverPanel(final ViewInterface parentFrame, final ViewObserver controller, final int score) {
		this.controller = controller;
		this.img = LoadImage.load(PANELS_FOLDER + "GameOver.jpg");
		this.upPanel = new JPanel(new FlowLayout());
		this.downPanel = new JPanel(new FlowLayout());
		this.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(final KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					retry.doClick();
				}
			}
		});
		this.setFocusable(true);
		this.requestFocusInWindow();
		
		this.retry = createButton("Retry");
		this.retry.addActionListener(e -> this.controller.newGame());
		this.centralPanel.add(this.retry);
		
		this.menu = createButton("Menu");
		this.menu.addActionListener(e -> parentFrame.showMenu());
		this.centralPanel.add(this.menu);
		
		this.quit = createButton("Quit");
		this.quit.addActionListener(e -> this.controller.quit());
		this.centralPanel.add(this.quit);
		
		this.score = createLabel("FINAL SCORE: " + score, new java.awt.Font("Georgia", STYLE, BIG_DIM));
		this.string = createLabel(FunnyStrings.random(), new java.awt.Font("Castellar", STYLE, LITTLE_DIM));
		
		this.centralPanel.add(this.retry, k);
		this.centralPanel.add(this.menu, k);
		this.centralPanel.add(this.quit, k);
		
		this.upPanel.add(this.score);
		this.upPanel.setOpaque(false);
		this.add(this.upPanel, BorderLayout.NORTH);
		this.downPanel.add(this.string);
		this.downPanel.setOpaque(false);
		this.add(this.downPanel, BorderLayout.SOUTH);
		this.b.setVisible(false);  //impostata la non visualizzazione della freccia per ritornare indietro
		this.validate();
		this.repaint();
	}
	
	@Override
	protected void paintComponent(final Graphics g) {
		super.paintComponent(g);
		this.requestFocusInWindow();
	}
}
