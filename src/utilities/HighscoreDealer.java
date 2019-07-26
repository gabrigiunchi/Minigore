package utilities;

import java.io.File;

import static utilities.SystemInformation.HIGHSCORE_FOLDER;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import model.game.Score;

/**
 * Scrive e legge su file liste di Score.
 * 
 * @author Gabriele Giunchi, Mattia Ricci, Lorenzo Mazzesi
 */
public final class HighscoreDealer {
	
	private HighscoreDealer() { }
	
	/**
	 * Legge da file una lista di Score. Il file si deve trovare nella cartella predefinita HIGHSCORE_FOLDER
	 * @param filename : il file da cui leggere
	 * @return la lista di Score
	 */
	@SuppressWarnings("unchecked")
	public static List<Score> readScores(final String filename) {
		try {
			final ObjectInputStream input = new ObjectInputStream(new FileInputStream(new File(HIGHSCORE_FOLDER + filename)));
			final List<Score> score = (List<Score>) input.readObject();
			input.close();
			return score;
			
		} catch (IOException | ClassNotFoundException e) {
			System.out.println(e);
			return new LinkedList<>();
		}
	}
	
	/**
	 * Scrive su file una lista di Score.
	 * @param filename : il file su cui scrivere
	 * @param scores : la lista
	 * @throws IOException
	 */
	public static void writeClassification(final String filename, final List<Score> scores) throws IOException {
		new File(HIGHSCORE_FOLDER).mkdirs();
		final ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(new File(HIGHSCORE_FOLDER + filename)));
		output.writeObject(scores);
		output.close();
	}
	
	/**
	 * Pulisce un file, ossia cancella la lista di Score che era precedentemente scritta sostituendola con una lista vuota.
	 * @param filename : il nome del file da pulire
	 */
	public static void clear(final String filename) {
		try {
			final ObjectOutputStream file = new ObjectOutputStream(new FileOutputStream(new File(HIGHSCORE_FOLDER + filename)));
			file.writeObject(Collections.emptyList());
			file.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
