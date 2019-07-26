package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import utilities.HighscoreDealer;
import model.game.MyGameStrategies;
import model.game.Score;
import controller.ViewObserver;
import static utilities.SystemInformation.PANELS_FOLDER;

/**
 * Lo scopo di questa classe è di realizzare un panel per la visualizzazione dei migliori 10 punteggi
 * per ogni modalità. Vi è inoltre la possibilità tramite un buttone di azzerrare i punteggi di una 
 * specifica modalità.
 * Estende il BasePanel per il layout e di conseguenza l'ImagePanel per l'immagine di sfondo.
 * 
 * @author Mattia Ricci 
 *
 */

public class HighScorePanel extends BasePanel {
	
	private static final long serialVersionUID = 1L;
	private static final int LIMIT = 10;
	private static final int DIM = 55;
	private List<Score> scores;
	private final List<JLabel> numbers;
	private final List<JLabel> names;
	private final JLabel topScores;
	private final JPanel upPanel;
	private final ViewImpl parent;
	private final JComboBox<MyGameStrategies> mod;
	private final ViewObserver controller;
	private final JButton clear;
	
	/**
	 * Questo panel imposta la propria immagine di sfondo, rende possibile la selezione della modalità
	 * con un ComboBox, rende possibile l'azzeramento dei punteggi della modalità che si sta visualizzando.
	 * 
	 * @param parentFrame
	 *                    il panel da cui è stato chiamato questo panel
	 * @param controller
	 * 					  viene passato il controller per potergli passare il controllo del programma
	 */
	
	public HighScorePanel(final ViewImpl parentFrame, final ViewObserver controller) {
		this.parent = parentFrame;
		this.img = LoadImage.load(PANELS_FOLDER + "HighScorePanel.jpg");
		this.upPanel = new JPanel(new FlowLayout());
		this.controller = controller;
		
		this.numbers = new LinkedList<JLabel>();
		this.names = new LinkedList<JLabel>();
		
		for (int i = 0; i < 10; i++) {
			this.names.add(CreateLabel.createLabel("", new Font("Verdana", 1, 16)));
			this.numbers.add(CreateLabel.createLabel("", new Font("Verdana", 1, 16)));
			this.centralPanel.add(this.numbers.get(i), k);
			this.centralPanel.add(this.names.get(i), k);
			k.gridy++;
		}
		
		this.clear = new JButton("Clear");
		/**
		 *  alla pressione del bottone viene invocato un metodo del controller che azzera i punteggi,
		 *  a tale metodo viene passata la modalità. Infine viene invocato il metodo load per 
		 *  ridisegnare il panel in seguito alla cancellazione.
		 */
		this.clear.addActionListener(e -> {
			this.controller.clearHighScore(((MyGameStrategies) this.mod.getSelectedItem()).getFilename());
			load(); 
		});
		
		this.topScores = new JLabel(" TOP 10 SCORES ");
		this.topScores.setForeground(Color.WHITE);
		this.topScores.setFont(new Font("Andalus", 1, DIM));
		
		this.mod = new JComboBox<>(MyGameStrategies.values());
		this.mod.addActionListener(e -> load()); //selezionata una modalità viene chiamato il metodo load
		this.mod.setSelectedIndex(-1);  //inizialmente il combobox non seleziona nulla
		
		this.upPanel.add(this.topScores);
		this.upPanel.add(this.mod);
		this.upPanel.add(this.clear);
		this.upPanel.setOpaque(false);
		this.add(this.upPanel, BorderLayout.NORTH);
		
		this.b.addActionListener(e -> parent.showMenu());
		this.validate();
		this.repaint();
	}
	
	/**
	 * Questo metodo modifica i label in base ai punteggi, leggendoli dalla lista modificata dal 
	 * metodo load. 
	 */
	
	private void setLabel() {
		int i = 0;
	
		this.names.forEach(e -> e.setText(""));
		this.numbers.forEach(e -> e.setText(""));
		
		if (this.scores.size() > 0) {
			for (final Score s : this.scores) {
				this.names.get(i).setText(s.getName());
				this.numbers.get(i).setText(s.getScore() + "");
				i++;
				if (i == LIMIT) {
					break;
				}
			}
		}
	}
	
	/**
	 * Questo metodo cerca il file di memorizzazione dei risultati (in base alla stringa del combo box),
	 * salva i risultati in una lista di Score, dopodichè chiama il metodo setLabel per modificare i 
	 * label (in corrispondenza ai risultati).Infine ridisegna il panel.
	 */
	
	private void load() {
		if (this.mod.getSelectedIndex() != -1) {
			final String selected = ((MyGameStrategies) this.mod.getSelectedItem()).getFilename();
			this.scores = HighscoreDealer.readScores(selected);
			this.setLabel();
			this.revalidate();
			this.repaint();
		}
	}
}
