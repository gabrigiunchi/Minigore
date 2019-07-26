package controller;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import static utilities.SystemInformation.SAVES_FILENAME;
import controller.MyMusicPlayer.MyMusicFile;
import model.environment.MyEnvironment;
import model.environment.MyEnvironmentBlocks;
import model.game.Game;
import model.game.MyGameStrategies;
import model.game.Score;
import model.game.ScoreImpl;
import utilities.HighscoreDealer;
import utilities.GameLoader;
import view.ViewInterface;

/**
 * Implementazione del controller che aderisce al pattern singleton.
 * 
 * @author Lorenzo Mazzesi, Mattia Ricci (solo la parte del HighScore)
 */

public final class Controller implements ViewObserver {
	
	private static final int MAX = 9;
	private static Controller one;
	private ViewInterface view;
	private Game game;
	private GameController gameController;
	private MyGameStrategies selectedStrategy;
	private MyEnvironmentBlocks selectedEnvironment;
	private boolean deleteSave;
	
	private Controller() {
		this.selectedStrategy = MyGameStrategies.Basic;
		this.selectedEnvironment = MyEnvironmentBlocks.Default;
		this.deleteSave = false;
	}
	
	/**
	 * Singleton per la creazione del controller.
	 * @return l'istanza dell'oggetto controller
	 */
	public static synchronized Controller getInstance() {
		if (one == null) {
			one = new Controller();
		}
		return one;
	}
	
	private void checkInitializzation() {
		if (this.game == null || this.view == null) {
			throw new IllegalStateException();
		}
	}
	/**
	 * Collega il modello dell'aplicazione.
	 * @param game : modello dell'applicazione
	 */
	public void setModel(final Game game) {
		this.game = game;
	}

	/**
	 * Collega la view dell'applicazione.
	 * @param view : view dell'applicazione
	 */
	public void setView(final ViewInterface view) {
		this.view = view;
	}

	/**
	 * Imposta l'ecosistema dell'applicazione.
	 * @param env : l'ecosistema scelto
	 */
	public void setEnvironment(final MyEnvironmentBlocks env) {
		this.selectedEnvironment = env;
	}

	/**
	 * Imposta la strategia di gioco.
	 * @param strategy : la strategia scelta
	 */
	public void setStrategyGame(final MyGameStrategies strategy) {
		this.selectedStrategy = strategy;
	}

	@Override
	public Optional<GameViewObserver> getGameController() {
		return Optional.ofNullable(this.gameController);
	}
	@Override
	public void newGame() {
		this.checkInitializzation();
		
		MyEnvironment.get().setBlocks(this.selectedEnvironment.getBlocks());
		this.game.setEnvironment(MyEnvironment.get());
		this.game.setStrategy(this.selectedStrategy);
		
		if (this.gameController == null) {
			this.gameController = new GameController(this.game, this.view);
		}
		
		this.gameController.startGame();
		MyMusicPlayer.getInstance().reproduce(MyMusicFile.Background);
		this.deleteSave = false;
	}

	@Override
	public void quit() {
		System.exit(0);
	}

	@Override
	public void loadGame() {
		try {
			this.game = GameLoader.read(SAVES_FILENAME);
			
			if (this.gameController == null) {
				this.gameController = new GameController(this.game, this.view);
			} else {
				this.gameController.setModel(this.game);
				this.gameController.setView(this.view);
			}
			
			this.gameController.resumeGame();
			this.deleteSave = true;
			
			if (MyMusicPlayer.getInstance().isAudioOn()) {
				MyMusicPlayer.getInstance().reproduce(MyMusicFile.Background);
			}
		} catch (ClassNotFoundException | IOException e) {
			this.view.showError("There isn't a match saved!", "LOAD ERROR");
		}
	}

	@Override
	public void saveGame() {
		try {
			GameLoader.write(this.game, SAVES_FILENAME);
			this.deleteSave = true;
		} catch (IOException e) {
			System.out.println("errore nel salvataggio");
		}
	}

	@Override
	public void gameOver(final int score) {
		MyMusicPlayer.getInstance().stop();
		MyMusicPlayer.getInstance().reproduce(MyMusicFile.GameOver);
		
		if (this.deleteSave) {
			try {
				GameLoader.clear(SAVES_FILENAME);
			} catch (IOException e) {
				System.out.println(e);
			}
			
			this.deleteSave = false;
		}
		
		this.checkScore(score);
	}
	
	private void checkScore(final int score) {
		
		try {
			final String filename = this.game.getStrategy().get().getFilename();
			final List<Score> list = HighscoreDealer.readScores(filename);
			if (score != 0 && (list.size() < 10 || list.get(MAX).getScore() < score)) {
				this.view.askName();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void setNewHighscore(final String name) {
		try {
			final String filename = this.game.getStrategy().get().getFilename();
			final Score score = new ScoreImpl(name, this.game.getScore());
			final List<Score> list = new LinkedList<>(HighscoreDealer.readScores(filename));
			
			list.add(score);
			Collections.sort(list, (s1, s2) -> s2.getScore() - s1.getScore());
			HighscoreDealer.writeClassification(filename, list);
			
		} catch (IOException e) {
			System.out.println(e);
		} 
	}

	@Override
	public void clearHighScore(final String filename) {
		HighscoreDealer.clear(filename);
	}
}
