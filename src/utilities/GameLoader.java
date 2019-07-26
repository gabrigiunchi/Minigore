package utilities;

import static utilities.SystemInformation.SAVES_FOLDER;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import model.game.Game;

/**
 * Legge e scrive su file il gioco per poter essere ripreso successivamente.
 * 
 * @author Gabriele Giunchi
 */
public final class GameLoader {
	
	private GameLoader() { }

	/**
	 * Carica da un file un gioco salvato. Il file si trova nella cartella dei salvataggi dell'applicazione.
	 * 
	 * @param filename : il nome del file su cui è salvato il gioco
	 * @return il gioco salvato
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public static Game read(final String filename) throws ClassNotFoundException, IOException {
		final ObjectInputStream input = new ObjectInputStream(new FileInputStream(new File(SAVES_FOLDER + filename)));
		final Game game = (Game) input.readObject();
		input.close();
		return game;
	}
	
	/**
	 * Scrive su file che si trova nella cartella dei salvataggi un gioco.
	 *
	 * @param game il gioco da salvare su file
	 * @param filename : il nome del file su cui salvare
	 * @throws IOException
	 */
	public static void write(final Game game, final String filename) throws IOException {
		new File(SAVES_FOLDER).mkdirs();
		final ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(new File(SAVES_FOLDER + filename)));
		output.writeObject(game);
		output.close();
	}
	
	/**
	 * Cancella un file di salvataggio dal file system. Il file si deve trovare nella cartella dei salvataggi dell'applicazione.
	 * 
	 * @param filename : il nome del file
	 * @throws IOException
	 */
	public static void clear(final String filename) throws IOException {
		if (!new File(SAVES_FOLDER + filename).delete()) {
			throw new IOException();
		}
	}
}
