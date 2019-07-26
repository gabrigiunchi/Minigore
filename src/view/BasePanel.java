package view;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import static utilities.SystemInformation.IMAGES_FOLDER;

/**
 * Lo scopo di questa classe è quello organizzare il panel con un preciso layout, 
 * estendibile da alcuni panel che richiedevano tutti la stessa tecnica. A suo volta
 * questo panel estende l'ImagePanel per poter applicare uno sfondo al panel.
 * 
 * @author Mattia Ricci 
 *
 */

class BasePanel extends ImagePanel {
    
	/**
	 * Il bottone b è stato impostato protected poichè è presente in quasi tutti i panel,
	 * tranne due, nei quali verrà reso non visibile. Il campo k è protected per rendere
	 * possibile il posizionamento dei componenti (incrementandolo quando necessario). 
	 */
	private static final long serialVersionUID = 1L;
	private static final int INSETS = 10;
	protected JButton b;
	protected JPanel centralPanel;
	protected GridBagConstraints k;

	public BasePanel() {
		
	    this.setLayout(new BorderLayout());
	    this.centralPanel = new JPanel(new GridBagLayout());
	    final JPanel downPanel = new JPanel(new BorderLayout());
	    
	    final ImageIcon icon = new ImageIcon(BasePanel.class.getResource(IMAGES_FOLDER 
	    		+ "arrow_left.png")); // utilizzo questo immagine come bottone per tornare al panel precedente
	    this.b = new JButton(icon);
		this.b.setContentAreaFilled(false);
		this.b.setBorder(null);
		downPanel.add(this.b, BorderLayout.WEST);
		downPanel.setOpaque(false);
		this.add(downPanel, BorderLayout.SOUTH);
		
		/**
		 * Questa tecnica di sistemazione dei Component è stata presa da un progetto 
		 * del Ing. Pianini, come specificato nella relazione del progetto.
		 */
		this.k = new GridBagConstraints();
		this.k.gridy = 0;
		this.k.insets = new Insets(INSETS, INSETS, INSETS, INSETS);
		this.k.fill = GridBagConstraints.VERTICAL;
		this.centralPanel.setOpaque(false);
		this.add(centralPanel, BorderLayout.CENTER);
	}
	  
	  
}