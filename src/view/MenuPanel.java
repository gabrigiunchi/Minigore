package view;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JPanel;
import controller.ViewObserver;
import static view.MyJButton.createButton;

/** Lo scopo di questa classe è quello di realizzare il panel principale, il menù iniziale.
 *  Estende l'ImagePanel per l'immagine di sfondo.
 * 
 * @author Mattia Ricci 
 *
 */

public class MenuPanel extends ImagePanel {

	private static final long serialVersionUID = 1L;
	private static final int INSETS = 10;
	private final JPanel downPanel;
	private final ViewObserver controller;
	private final JButton newGame;
	private final JButton loadGame;
	private final JButton options;
	private final JButton highScore;
	private final JButton credits;
	private final JButton quit;
	private final ViewInterface parentFrame;
	
	/**Questo panel ha diversi bottoni, collegati ognuno ad un ad un listener, per indirizzare il 
	 * programma nei vari panel. 
	 * 
	 * @param parent
	 * 					il panel da cui è stato chiamato
	 * @param ctrl	
	 * 					viene passato il controller per potergli passare il controllo del programma
	 */
	
	public MenuPanel(final ViewInterface parent, final ViewObserver ctrl) {
		this.controller = ctrl;
		this.parentFrame = parent;
		
	    this.setLayout(new BorderLayout());
	    this.downPanel = new JPanel(new GridBagLayout());
	    
	    this.newGame = createButton("New Game");
	    this.newGame.addActionListener(e -> this.parentFrame.showModes()); //invoca un metodo del controller
	    
	    this.loadGame = createButton("Load match");
	    this.loadGame.addActionListener(e -> this.controller.loadGame()); //invoca un metodo del controller
	    
	    this.highScore = createButton("High Score");
	    this.highScore.addActionListener(e -> this.parentFrame.showHighScore()); //invoca un metodo del frame che l'ha invocato
	    
	    this.options = createButton("Options");
	    this.options.addActionListener(e -> this.parentFrame.showOptions()); //invoca un metodo del controller
	    
	    this.credits = createButton("Credits");
	    this.credits.addActionListener(e -> this.parentFrame.showCredits()); //invoca un metodo del frame che l'ha invocato
	    
	    this.quit = createButton("Quit");
	    this.quit.addActionListener(e -> this.controller.quit()); //invoca un metodo del controller
	    
	    /**
	     * posizionamento dei bottoni
	     */
	    
	    GridBagConstraints k = new GridBagConstraints();
		k.gridy = 0;
		k.insets = new Insets(INSETS, INSETS, INSETS, INSETS);
		k.fill = GridBagConstraints.VERTICAL;
		this.downPanel.add(this.newGame, k);
		k.gridy++;
		this.downPanel.add(this.loadGame, k);
		k.gridy++;
		this.downPanel.add(this.highScore, k);
		k.gridy++;
		this.downPanel.add(this.options, k);
		k.gridy++;
		this.downPanel.add(this.credits, k);
		k.gridy++;
		this.downPanel.add(this.quit, k);
		
		this.downPanel.setOpaque(false);
		this.add(downPanel, BorderLayout.SOUTH);
	}
	
}

