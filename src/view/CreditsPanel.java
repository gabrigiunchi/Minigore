package view;

import java.awt.Font;
import java.util.stream.IntStream;
import static view.CreateLabel.createLabel;
import javax.swing.JLabel;
import static utilities.SystemInformation.PANELS_FOLDER;;

/**
 * Lo scopo di questa classe è semplicemente di creare un panel contenente dei label.
 * Estende il BasePanel per il layout e di conseguenza l'ImagePanel per l'immagine di sfondo.
 * 
 * @author Mattia Ricci 
 *
 */

public class CreditsPanel extends BasePanel {
	
	private static final long serialVersionUID = 1L;
	private static final int SIZE = 5;
	private static final int STYLE = 5;
	private static final int DIM = 25;
	private final JLabel[] label;
	private final ViewImpl parent;
	
	/**
	 * Questo panel imposta la propria immagine di sfondo e stampa dei label. 
	 * 
	 * @param parentFrame
	 * 						il panel da cui è stato invocato
	 */
	
	public CreditsPanel(final ViewImpl parentFrame) {
		this.parent = parentFrame;
		this.img = LoadImage.load(PANELS_FOLDER + "OtherPanel.jpg");
		
		this.label = new JLabel[SIZE];
		
		this.label[0] = createLabel("This game was realized by:", new Font("Monotype Corsiva", STYLE, DIM));
		this.label[1] = createLabel("- Gabriele Giunchi",  new Font("Monotype Corsiva", STYLE, DIM));
		this.label[2] = createLabel("- Mattia Ricci",  new Font("Monotype Corsiva", STYLE, DIM));
		this.label[3] = createLabel("- Lorenzo Mazzesi",  new Font("Monotype Corsiva", STYLE, DIM));
		this.label[4] = createLabel("© MINIGORE 1.0  -  2015",  new Font("Monotype Corsiva", STYLE, DIM));
		
		IntStream.rangeClosed(0, 4).forEach(i-> { 
			this.centralPanel.add(this.label[i], k);
			k.gridy++;
		});
		
		this.b.addActionListener(e-> parent.showMenu()); //se si preme la freccia si torna al MenuPanel
		
	}
}
