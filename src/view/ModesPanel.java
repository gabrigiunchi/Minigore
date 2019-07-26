package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import model.environment.MyEnvironmentBlocks;
import model.game.MyGameStrategies;
import controller.ViewObserver;
import static view.MyJButton.createButton;
import static utilities.SystemInformation.PANELS_FOLDER;
import static utilities.SystemInformation.W_SCREEN;
import static utilities.SystemInformation.H_SCREEN;
import static view.CreateLabel.createLabel;

/** Lo scopo di questa classe è di realizzare il panel che precede l'inizio del gioco, nel quale 
 *  vengono scelte la mappa e la modalità di gioco. 
 *  Estende il BasePanel per il layout e di conseguenza l'ImagePanel per l'immagine di sfondo.
 * 
 * @author Mattia Ricci 
 *
 */

public class ModesPanel extends BasePanel {

	private static final long serialVersionUID = 1L;
	private static final int DIM = 14;
	private final JLabel envir;
	private final JLabel strat;
	private final JComboBox<MyEnvironmentBlocks> env;
	private final JComboBox<MyGameStrategies> strategies;
	private final JButton play;
	private final JButton ask;
	private static final int W_DIALOG = W_SCREEN / 3; // il campo W_SCREEN è pubblico, indica la larghezza dello schermo. 
	private static final int H_DIALOG = H_SCREEN / 5; // il campo H_SCREEN è pubblico, indica l'altezza dello schermo.
	private final ViewObserver controller;
	
	/** In questo panel sono presenti due label, associati a due combo box per la scelta delle modalità.
	 *  E' poi presente un bottone per la spiegazione delle modalità e uno per avviare il gioco.
	 * 
	 * @param controller 
	 * 						viene passato il controller per indicargli le modalità di gioco
	 * @param parentFrame
	 *                      viene passato il panel da cui è stato chiamato
	 */
	
	public ModesPanel(final ViewInterface parentFrame, final ViewObserver controller) {
		this.controller = controller;
		this.img = LoadImage.load(PANELS_FOLDER + "OtherPanel.jpg");
		
		this.env = new JComboBox<>(MyEnvironmentBlocks.values());
		this.env.setSelectedIndex(0); // se non viene modificare usa la mappa default
		this.envir = new JLabel("Select environment: ");
		this.envir.setForeground(Color.RED);
		this.envir.setFont(new Font("Georgia", 1, DIM));
		
		this.strategies = new JComboBox<>(MyGameStrategies.values());
		this.strategies.setSelectedIndex(0); // se non viene modifiato usa la modalità base
		this.strat = new JLabel("Select modality: ");
		this.strat.setForeground(Color.RED);
		this.strat.setFont(new Font("Georgia", 1, DIM));
		
		this.ask = createButton("?");
		/**
		 *  Se premuto il tasto '?', si apre un dialog in cui vengono spiegate le modalità
		 */
		this.ask.addActionListener(e -> {
			final JDialog mod = new JDialog();
			mod.setTitle("Games strategies");
			final JPanel panel = new JPanel(new BorderLayout());
			panel.setBackground(Color.BLACK);
			mod.add(panel);
			final JPanel upPanel = new JPanel();
			upPanel.setLayout(new BoxLayout(upPanel, BoxLayout.Y_AXIS));
			upPanel.add(createLabel("In basic strategy the points increase with kills. ", new Font("Verdana", 1, DIM)));
			upPanel.add(createLabel("In TimeChallenge the points increase with the life time. ", new Font("Verdana", 1, DIM)));
			upPanel.add(createLabel("Multiplies Lifes is similar to basic, but you have 5 lives.", new Font("Verdana", 1, DIM)));
			final JPanel downPanel = new JPanel(new FlowLayout());
			final JButton button = createButton("Let's start!");
			button.addActionListener(i -> mod.dispose());  //metodo per chiudere il dialog.
			downPanel.add(button);
			panel.add(upPanel, BorderLayout.NORTH);
			panel.add(downPanel, BorderLayout.SOUTH);
			upPanel.setOpaque(false);
			downPanel.setOpaque(false);
			mod.setSize(W_DIALOG, H_DIALOG);
			mod.setResizable(false);
			mod.setLocationRelativeTo(null);
			mod.setVisible(true);
		});
		
		/**
		 *  Avvia la partita, passando al controller le modalità scelte. 
		 */
		this.play = createButton("Play");
		 this.play.addActionListener(e -> {
			this.controller.setEnvironment((MyEnvironmentBlocks) env.getSelectedItem());
			this.controller.setStrategyGame((MyGameStrategies) strategies.getSelectedItem());
			this.controller.newGame();
		});
		
		this.centralPanel.add(this.envir, k);
		this.centralPanel.add(this.env, k);
		k.gridy++;
		this.centralPanel.add(this.strat, k);
		this.centralPanel.add(this.strategies, k);
		this.centralPanel.add(this.ask, k);
		k.gridy++;
		this.centralPanel.add(this.play, k);
		
		this.b.addActionListener(e-> parentFrame.showMenu());
		
		this.validate();
	}

	

}
