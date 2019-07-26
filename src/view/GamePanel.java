package view;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.Set;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import model.entities.Enemy;
import model.entities.Hero;
import model.environment.Block;
import model.environment.Environment;
import model.environment.EnvironmentBounds;
import model.environment.IPosition2D;
import model.game.Game;
import controller.GameViewObserver;
import controller.GameKeyListener;
import static utilities.SystemInformation.IMAGES_FOLDER;
import static utilities.SystemInformation.PANELS_FOLDER;

/** Lo scopo di questa classe è quello di realizzare tutta la grafica che riguarda la partita. 
 *  In particolare viene creato il panel, al centro del quale è collocata un'arena, contenente
 *  tutti i personaggi. Il panel è stato realizzato in proporzione ed infatti l'arena si adatta 
 *  al frame. Estende il BasePanel per il layout e di conseguenza l'ImagePanel per l'immagine di 
 *  sfondo.
 *  
 * @author Mattia Ricci 
 *
 */

public class GamePanel extends ImagePanel {
	
	private static final long serialVersionUID = 1L;

	private int dimX;
	private int dimY;
	private static final int START_X = 5;
	private static final int START_Y = 5;
	
	private Image shotImage;
	private Image verticalBlock;
	private Image orizzontalBlock;
	private Image cornerSudWest;
	private Image cornerSudEst;
	private Image cornerNordWest;
	private Image cornerNordEst;
	private Image downHero;
	private Image upHero;
	private Image leftHero;
	private Image rightHero;
	private Image downGhost;
	private Image upGhost;
	private Image leftGhost;
	private Image rightGhost;
	private Image heart;
	
	private Game model;
	private final GameKeyListener keyListener;
	private final JLabel score;
	
	public GamePanel(final GameViewObserver controller) {
		this.img = LoadImage.load(PANELS_FOLDER + "game1.png");
		this.keyListener = new GameKeyListener(controller);
		this.addKeyListener(this.keyListener);
		this.setLayout(new FlowLayout());
		this.score = CreateLabel.createLabel("Score: ", new Font("Verdana", 1, 14));
		this.add(this.score);
		this.loadImages();
		this.setFocusable(true);
		this.requestFocusInWindow();
	}
	
	private void loadImages() {
		this.shotImage = new ImageIcon(GamePanel.class.getResource(IMAGES_FOLDER + "shot2.png")).getImage();
		this.cornerNordEst = new ImageIcon(GamePanel.class.getResource(IMAGES_FOLDER + "cornerNordEst.png")).getImage();
		this.cornerNordWest = new ImageIcon(GamePanel.class.getResource(IMAGES_FOLDER + "cornerNordWest.png")).getImage();
		this.cornerSudEst = new ImageIcon(GamePanel.class.getResource(IMAGES_FOLDER + "cornerSudEst.png")).getImage();
		this.cornerSudWest = new ImageIcon(GamePanel.class.getResource(IMAGES_FOLDER + "cornerSudOvest.png")).getImage();
		this.orizzontalBlock = new ImageIcon(GamePanel.class.getResource(IMAGES_FOLDER + "bordoOrizz.png")).getImage();
		this.verticalBlock = new ImageIcon(GamePanel.class.getResource(IMAGES_FOLDER + "bordoVert.png")).getImage();
		this.downHero = new ImageIcon(GamePanel.class.getResource(IMAGES_FOLDER + "FrontalKenny.png")).getImage();
		this.upHero = new ImageIcon(GamePanel.class.getResource(IMAGES_FOLDER + "KennyBehind.png")).getImage();
		this.leftHero = new ImageIcon(GamePanel.class.getResource(IMAGES_FOLDER + "KennyLeft.png")).getImage();
		this.rightHero = new ImageIcon(GamePanel.class.getResource(IMAGES_FOLDER + "KennyRight.png")).getImage();
		this.downGhost = new ImageIcon(GamePanel.class.getResource(IMAGES_FOLDER + "ghost.png")).getImage();
		this.upGhost = new ImageIcon(GamePanel.class.getResource(IMAGES_FOLDER + "ghostBehind.png")).getImage();
		this.leftGhost = new ImageIcon(GamePanel.class.getResource(IMAGES_FOLDER + "ghostLeft.png")).getImage();
		this.rightGhost = new ImageIcon(GamePanel.class.getResource(IMAGES_FOLDER + "ghostRight.png")).getImage();
		this.heart = new ImageIcon(GamePanel.class.getResource(IMAGES_FOLDER + "cuore.png")).getImage();
	}
	
	@Override
	protected void paintComponent(final Graphics g) {
		super.paintComponent(g);

		if (this.model == null || !this.model.getEnvironment().isPresent()) {
			return;
		}
		
		this.dimX = Math.round((float) this.getWidth() / (float) 40);
		this.dimY = Math.round((float) this.getHeight() / (float) 30);
		
		final Environment env = this.model.getEnvironment().get();
		
		this.drawBlocks(g, env.getBlocks());
		this.drawEnemies(g, env.getEnemies());
		env.getShots().forEach(e -> this.drawGameComponent(g, shotImage, e.getPosition()));
		if (env.getHero().isPresent()) {
			this.drawHero(g, env.getHero().get());
			this.drawLives(g, env.getHero().get().getLife());
		}
		
		this.score.setText("Score: " + this.model.getScore());
	}
	
	/**
	 * Disegna i dati del model, ossia il gioco che sta procedendo.
	 * Viene anche dato un segnale al keyListener che deve gestire il movimento del protagonista in base ai tasti attualmente premuti
	 * @param game : il model dell'applicazione
	 */
	public void drawModel(final Game game) {
		this.model = game;
		this.requestFocusInWindow();
		super.repaint();
		this.keyListener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "Aggiorna il protagonista"));
	}
	
	/**
	 * Disegna le componenti del gioco in modo che vengano centrate rispetto alla finestra e proporzionate.
	 * @param g : la grafica del panel
	 * @param image : l'immagine da disegnare
	 * @param pos : la posizione relativa
	 */
	private void drawGameComponent(final Graphics g, final Image image, final IPosition2D pos) {
		final int x = pos.getX();
		final int y = pos.getY();
		
		g.drawImage(image, (x + START_X) * dimX, (y + START_Y) * this.dimY, this.dimX, this.dimY, this);
	}
	
	/**
	 * Disegna la lista di nemici del gioco in base alla direzione cui sono rivolti
	 * @param g : la grafica del gioco
	 * @param enemy : la lista di nemici
	 */
	private void drawEnemies(final Graphics g, final List<Enemy> enemy) {
		Image image = this.downGhost;

		for (final Enemy temp : enemy) {
			final int x = temp.getPosition().getX();
			final int y = temp.getPosition().getY();
			final EnvironmentBounds bounds = temp.getEnvironment().get().getBounds().get();
			
			// Per non disegnare i nemici sovrapposti agli ostacoli
			if (x != bounds.getXLower() && x != bounds.getXUpper() && y != bounds.getYUpper() && y != bounds.getYLower()) {
				switch(temp.getDirection()) {
					case DOWN: image = downGhost; break;
					case UP: image = upGhost; break;
					case LEFT: image = leftGhost; break;
					case RIGHT: image = rightGhost; break;
				}
			
				this.drawGameComponent(g, image, temp.getPosition());
			}
		}
	}
	
	/**
	 * Disegna il set di blocchi in base alla loro forma
	 * @param g : la grafica del gioco
	 * @param blocks : il set di blocchi
	 */
	private void drawBlocks(final Graphics g, final Set<Block> blocks) {
		for (final Block temp : blocks) {
			Image image = orizzontalBlock;
			
			switch(temp.getType()) {
				case CornerSudWest: image = cornerSudWest; break;
				case CornerSudEast: image = cornerSudEst; break;
				case CornerNordEast: image = cornerNordEst; break;
				case CornerNordWest:image = cornerNordWest; break;
				case Orizzontal: image = orizzontalBlock; break;
				case Vertical: image = verticalBlock; break;
			}
			
			this.drawGameComponent(g, image, temp.getPosition());
		}
	}
	
	/**
	 * Disegna le vite che possiede il protagonista
	 * @param g : la grafica del panel
	 * @param lives : il numero di vite che possiede l'erore
	 */
	private void drawLives(final Graphics g, final int lives) {
		for (int i = 0; i < lives; i++) {
			g.drawImage(this.heart, i * dimX, 0, dimX, dimY, this);
		}
	}
	
	/**
	 * Disegna il protagonista in base alla direzione in cui è rivolto
	 * @param g : la grafica del panel
	 * @param hero : l'eroe del gioco
	 */
	private void drawHero(final Graphics g, final Hero hero) {
		Image image = this.leftHero;
		
		switch (hero.getDirection()) {
			case DOWN: image = downHero; break;
			case UP: image = upHero; break;
			case LEFT: image = leftHero; break;
			case RIGHT: image = rightHero; break;
		}
		
		this.drawGameComponent(g, image, hero.getPosition());
	}
}
