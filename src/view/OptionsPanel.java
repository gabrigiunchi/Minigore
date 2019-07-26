package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.stream.IntStream;

import static view.MyJButton.createButton;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import utilities.SettingLoader;
import controller.MyMusicPlayer;
import static utilities.SystemInformation.W_SCREEN;
import static utilities.SystemInformation.H_SCREEN;
import static utilities.SystemInformation.IMAGES_FOLDER;
import static utilities.SystemInformation.PANELS_FOLDER;

/** Lo scopo di questa classe è di realizzare un panel per il settaggio della musica e degli effetti
 *  sonori. Inoltre vengono spiegate le istruzioni del gioco. 
 *  Estende il BasePanel per il layout e di conseguenza l'ImagePanel per l'immagine di sfondo.
 * 
 *  @author Mattia Ricci 
 *
 */

public class OptionsPanel extends BasePanel {
	
	private static final long serialVersionUID = 1L;
	private static final int DIM = 14;
	private static final int SIZE = 2;
	private static final String MUSIC_ON = "music on.png";
	private static final String MUSIC_OFF = "music off.png";
	private static final int W_DIALOG = W_SCREEN / 5; //il campo W_SCREEN è pubblico, indica la larghezza dello schermo.
	private static final int H_DIALOG = H_SCREEN / 4; //il campo H_SCREEN è pubblico, indica l'altezza dello schermo.
	private JLabel[] label;
	private JButton[] audio;
	private JButton instruction;
	
	//le immagini per il settaggio della musica e degli effetti.
	private final ImageIcon musicOn = new ImageIcon(OptionsPanel.class.getResource(IMAGES_FOLDER + MUSIC_ON));
	private final ImageIcon musicOff = new ImageIcon(OptionsPanel.class.getResource(IMAGES_FOLDER + MUSIC_OFF));
	
	
	/** In questo panel sono presenti due label, associati a due bottoni (immagini) che settano l'audio.
	 *  E' poi presente un bottone per la spiegazione delle istruzioni.
	 *  
	 *  @param parentFrame
	 *  					viene passato il panel da cui è stato chiamato
	 * 
	 */
	
	public OptionsPanel(final ViewInterface parentFrame) {
		this.img = LoadImage.load(PANELS_FOLDER + "OtherPanel.jpg");
		
		this.label = new JLabel[SIZE];
		this.audio = new JButton[SIZE];
		
		this.label[0] = new JLabel("Music: ");
		this.label[1] = new JLabel("Sound effects: ");
		
		IntStream.rangeClosed(0, 1).forEach(i-> { 
			this.audio[i] = new JButton();
			this.setAudioButtonIcon(audio[i]);
			this.audio[i].addActionListener(new AudioActionListener(i)); 
			this.audio[i].setContentAreaFilled(false);
			this.audio[i].setBorder(null);
			this.label[i].setFont(new java.awt.Font("Verdana", 2, 15));
			this.label[i].setForeground(Color.WHITE);
			this.centralPanel.add(this.label[i], k);
			this.centralPanel.add(this.audio[i], k);
			k.gridy++;
		});
		
		this.instruction = createButton("Instructions");
		this.centralPanel.add(this.instruction, k);
		/**
		 *  Se premuto il tasto istruzioni, si apre un dialog in cui vengono spiegate le istruzioni
		 */
		this.instruction.addActionListener(e -> {
			final JDialog prova = new JDialog();
			prova.setTitle("Instructions");
			JPanel panel = new JPanel();
			prova.add(panel);
			panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
			panel.add(instLabel("W:  hero move up"));
			panel.add(instLabel("S:  hero move down"));
			panel.add(instLabel("A:  hero move left"));
			panel.add(instLabel("D:  hero move right"));
			panel.add(instLabel("Up arrow:  shoot up"));
			panel.add(instLabel("Down arrow:  shoot down"));
			panel.add(instLabel("Left arrow:  shoot left"));
			panel.add(instLabel("Right arrow:  shoot right"));
			panel.add(instLabel("ESC:  pause"));
			prova.setSize(W_DIALOG, H_DIALOG);
			prova.setResizable(false);
			prova.setLocationRelativeTo(null);
			prova.setVisible(true);
		});
		
		this.b.addActionListener(e-> parentFrame.showMenu());
		
		this.validate();
	}
	
	//metodo per la creazioni di label di colore nero
	private JLabel instLabel(final String s) {
		final JLabel l = new JLabel(s);
		l.setFont(new Font("Verdana", 1, DIM));
		l.setAlignmentX(CENTER_ALIGNMENT);
		l.setForeground(Color.BLACK);
		
		return l;
	}
	
	/** Questo metodo prende in ingresso un bottone e lo riempie con un'immagine in base all'audio 
	 *  (se disattivato vi sarà una barra sull'immagine).
	 * 
	 * @param button
	 *             
	 */
	private void setAudioButtonIcon(final JButton button) {
		
		if (MyMusicPlayer.getInstance().isAudioOn()) {
			button.setIcon(musicOn);
		} else {
			button.setIcon(musicOff);
		}
	}
	
	/** ActionListener innestato nella stessa classe per modificare l'audio (e quindi le immagini).
	 * 
	 * @author Mattia Ricci
	 *
	 */
	private class AudioActionListener implements ActionListener {
		
		private final int indice;
		
		public AudioActionListener(final int i) {
			this.indice = i;
		}
		
		/**
		 * Premendo il bottone si decide di cambiare l'audio, quindi si controlla se fosse attivo 
		 * e in tal caso si spegne, e viceversa. Di conseguenza si cambia anche l'immagine per rendere
		 * anche visibilmente il cambiamento. 
		 */
		
		@Override
		public void actionPerformed(final ActionEvent e) {
			if (MyMusicPlayer.getInstance().isAudioOn()) {
				MyMusicPlayer.getInstance().setAudio(false);
				try {
					SettingLoader.setPreferredAudio(false);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				audio[indice].setIcon(musicOff);
			} else {
				MyMusicPlayer.getInstance().setAudio(true);
				try {
					SettingLoader.setPreferredAudio(true);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				audio[indice].setIcon(musicOn);
			}
		}
		
	}
}