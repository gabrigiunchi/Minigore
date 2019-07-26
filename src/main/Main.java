package main;

import java.io.IOException;

import model.game.Game;
import model.game.GameImpl;
import utilities.SettingLoader;
import view.ViewImpl;
import view.ViewInterface;
import controller.Controller;
import controller.MyMusicPlayer;

/**
 * Classe che contiene il main.
 * 
 * @author Gabriele Giunchi, Mattia Ricci, Lorenzo Mazzesi
 */
public final class Main {

	private Main() { }
	
	public static void main(final String[] args) {
		if (SettingLoader.isFirstTime()) {
			playGame();
		} else {
			new InstructionFrame();
			
			try {
				SettingLoader.applicationOpened();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Fa partire l'applicazione del gioco.
	 */
	public static void playGame() {
		final Controller controller = Controller.getInstance();
		final Game game = new GameImpl();
		final ViewInterface view = new ViewImpl(controller);
		
		controller.setView(view);
		controller.setModel(game);
		
		MyMusicPlayer.getInstance().setAudio(SettingLoader.isAudioOn());
	}
}
