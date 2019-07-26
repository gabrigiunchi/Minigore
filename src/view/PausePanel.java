package view;

import java.util.stream.IntStream;
import static view.MyJButton.createButton;
import javax.swing.JButton;
import controller.Controller;
import controller.GameViewObserver;
import static utilities.SystemInformation.PANELS_FOLDER;

/** Lo scopo di questa classe è di realizzare il panel da visualizzare quando si mette il gioco in pausa.
 *  Estende il BasePanel per il layout e di conseguenza l'ImagePanel per l'immagine di sfondo.
 * 
 * @author Mattia Ricci 
 *
 */

public class PausePanel extends BasePanel {
	
	private static final long serialVersionUID = 1L;
	private static final int SIZE = 4;
	private GameViewObserver controller;
	private final JButton[] buttons;
	
	/**
	 * Questo panel imposta la propria immagine di sfondo e posiziona al centro 4 bottoni.
	 * 
	 * @param controller
	 * 						il controller del gioco
	 */
	
	public PausePanel(final GameViewObserver controller) {
		
		this.controller = controller;
		this.img = LoadImage.load(PANELS_FOLDER + "OtherPanel.jpg");
		this.buttons = new JButton[SIZE];
		
		this.buttons[0] = createButton("Resume");
		this.buttons[0].addActionListener(e -> this.controller.resumeGame());
		
		this.buttons[1] = createButton("Save");
		this.buttons[1].addActionListener(e -> Controller.getInstance().saveGame());
		
		this.buttons[2] = createButton("Menu");
		this.buttons[2].addActionListener(e -> this.controller.menuButton());
		
		this.buttons[3] = createButton("Exit");
		this.buttons[3].addActionListener(e-> System.exit(0));
		
		IntStream.rangeClosed(0, 3).forEach(i-> { 
			this.centralPanel.add(this.buttons[i], k);
			k.gridy++;
		});
		
		this.b.setVisible(false);  //rendo invisibile il bottone per tornare indietro
		
		this.validate();
		this.repaint();
	}
	

}
