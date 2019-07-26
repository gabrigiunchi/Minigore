package view;

import java.awt.FlowLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import static utilities.SystemInformation.H_SCREEN;
import static utilities.SystemInformation.W_SCREEN;
import static utilities.SystemInformation.IMAGES_FOLDER;
import controller.ViewObserver;
import model.game.Game;

/** Lo scopo di questa classe è quello di creare il Frame su cui tutti i panel si alterneranno e
 *  di implementare diversi metodi pubblici per il coordinamento di tutta la view.
 *  {@link ViewInterface}
 * 
 * @author Mattia Ricci 
 *
 */

public class ViewImpl extends JFrame implements ViewInterface {

	private static final long serialVersionUID = 1L;
	/**
	 * La misura del frame e della dialog è proporzionale allo schermo che proietta il programma.
	 */
	public static final int W_FRAME = (W_SCREEN * 3) / 5 - ((W_SCREEN * 3) / 5) % 100;
	public static final int H_FRAME = (H_SCREEN * 4) / 5;
	private static final int W_DIALOG = W_SCREEN / 5;
	private static final int H_DIALOG = H_SCREEN / 6;
	private ViewObserver controller;
	private GamePanel gamePanel;
	
	
	/** Creato il Frame, con le dimensioni proporzionali.
	 * 
	 * @param ctrl
	 *               il ViewObserver
	 */
	
	public ViewImpl(final ViewObserver ctrl) {
		super("MINIGORE");
		this.setIconImage(new ImageIcon(ViewImpl.class.getResource(IMAGES_FOLDER + "icon.png")).getImage());
		this.controller = ctrl;
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(W_FRAME, H_FRAME);
		this.setLocationRelativeTo(null);
		this.getContentPane().add(new MenuPanel(this, this.controller));
		this.validate();
		this.setVisible(true);
	}
	
	/**
	 * Metodo che permette di passare da un panel all'altro. Viene rimosso ciò che il frame contiene e 
	 * viene aggiunto il panel che questo metodo prende in ingresso. Infine viene ridisegnato tutto.
	 * 
	 * @param panel
	 * 					panel da visualizzare
	 */
	private void setPanel(final JPanel panel) {
		this.getContentPane().removeAll();
		this.getContentPane().add(panel);
		this.revalidate();
		this.repaint();
	}
	
	@Override
	public void setController(final ViewObserver controller) {
		this.controller = controller;
	}
	
	@Override
	public void drawModel(final Game game) {
		this.gamePanel.drawModel(game);
		this.repaint();
	}
	
	@Override
	public void showPause() {
		if (this.controller.getGameController().isPresent()) {
			this.setPanel(new PausePanel(this.controller.getGameController().get()));
		}
	}
	
	@Override
	public void showgameOver(final int score) {
		this.setPanel(new GameOverPanel(this, this.controller, score));
	}
    
	@Override
	public void showMenu() {
		this.setPanel(new MenuPanel(this, this.controller));
	}
	
	@Override
	public void showModes() {
		this.setPanel(new ModesPanel(this, this.controller));
	}
	
	@Override
	public void showCredits() {
		this.setPanel(new CreditsPanel(this));
	}
	
	@Override
	public void showGame() {
		if (this.controller.getGameController().isPresent()) {
			this.gamePanel = new GamePanel(this.controller.getGameController().get());
			this.setPanel(this.gamePanel);
		}
	}
	
	@Override
	public void showOptions() {
		this.setPanel(new OptionsPanel(this));
	}

	@Override
	public void showHighScore() {
		this.setPanel(new HighScorePanel(this, this.controller));
	}
	
	@Override
	public void showError(final String error, final String title) {
		JOptionPane.showMessageDialog(this, error, title, JOptionPane.ERROR_MESSAGE);
	}
	
	@Override
	public void askName() {
		final JDialog prova = new JDialog();
		prova.setTitle("SAVE SCORE");
		prova.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		final JPanel panel = new JPanel(new FlowLayout());
		prova.add(panel);
		final JLabel label = new JLabel("Name: ");
		panel.add(label);
		final JTextField name = new JTextField(14);
		panel.add(name);
		final JButton save = new JButton("Save");
		save.addActionListener(e -> {
			try {
				this.controller.setNewHighscore(name.getText());
				prova.dispose();
			} catch (IllegalArgumentException e1) {
				this.showError("From 3 to 15 characters!", "WRONG NAME");
			}
		});
		name.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(final KeyEvent e) { }
			@Override
			public void keyReleased(final KeyEvent e) { }
			@Override
			public void keyPressed(final KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					save.doClick();
				}
			}
		});
		panel.add(save);
		prova.setSize(W_DIALOG, H_DIALOG);
		prova.setResizable(false);
		prova.setLocationRelativeTo(null);
		prova.setVisible(true);
			
	}
}

