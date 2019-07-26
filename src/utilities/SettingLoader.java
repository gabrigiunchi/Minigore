package utilities;

import static utilities.SystemInformation.MY_FOLDER;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import controller.MyMusicPlayer;

/**
 * Classe che controlla se il gioco è stato aperto almeno una volta.
 * 
 * @author Gabriele Giunchi
 *
 */
public final class SettingLoader {
	/**
	 * Nome del file dove vengono salvate le settings.
	 */
	public static final String FILENAME = "setting.bin";
	
	private SettingLoader() { }
	
	/**
	 * Legge dal file se è la prima volta o meno che l'utente apre l'applicazione.
	 * 
	 * @return true se non è la prima volta
	 */
	@SuppressWarnings("unchecked")
	private static Pair<Boolean, Boolean> read() {
		try {
			final ObjectInputStream input = new ObjectInputStream(new FileInputStream(new File(MY_FOLDER + FILENAME)));
			final Pair<Boolean, Boolean> pair = (Pair<Boolean, Boolean>) input.readObject();
			input.close();
			return pair;
		} catch (IOException | ClassNotFoundException e) {
			return new Pair<Boolean, Boolean>(false, false);
		}
	}
	
	/**
	 * Verifica se è la prima volta che l'utente apre l'applicazione.
	 * 
	 * @return true se è la prima volta
	 */
	public static boolean isFirstTime() {
		return read().getFirst();
	}
	
	/**
	 * Aggiorna le impostazioni dicendo che l'applicazione è stata aperta.
	 * 
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static void applicationOpened() throws FileNotFoundException, IOException {
		new File(MY_FOLDER).mkdirs();
		final ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(new File(MY_FOLDER + FILENAME)));
		output.writeObject(new Pair<Boolean, Boolean>(true, MyMusicPlayer.getInstance().isAudioOn()));
		output.close();
	}
	
	/**
	 * Memorizza la scelta dell'utente sull'audio che può essere on o off all'avvio dell'applicazione.
	 * 
	 * @param on : true se l'audio deve essere attivo di default, falso altrimenti
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static void setPreferredAudio(final boolean on) throws FileNotFoundException, IOException {
		new File(MY_FOLDER).mkdirs();
		final boolean bool = isFirstTime();
		final ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(new File(MY_FOLDER + FILENAME)));
		output.writeObject(new Pair<Boolean, Boolean>(bool, on));
		output.close();
	}
	
	/**
	 * Indica se la scelta delle preferenze dell'utente sono quelle di attivare o meno l'audio all'avvio dell'applicazione.
	 * 
	 * @return true se l'audio deve essere attivato
	 */
	public static boolean isAudioOn() {
		return read().getSecond();
	}

}
