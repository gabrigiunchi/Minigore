package controller;

import controller.MyMusicPlayer.MyMusicFile;
/**
 * Interfaccia per la realizzazione dell'audio.
 * @author Lorenzo Mazzesi
 *
 */
public interface MusicPlayer {

	/**
	 * Imposta l'audio on/off.
	 * @param on : true se acceso, false se spento
	 */
	void setAudio(final boolean on);
	/**
	 * Riproduce il file audio.
	 * @param file : file da riprodurre
	 */
	void reproduce(final MyMusicFile file);
	/**
	 * Termina la risproduzione audio.
	 */
	void stop();
	/**
	 * Getter per sapere sa l'audio è on/off.
	 * @return true se acceso, false se spento
	 */
	boolean isAudioOn();
}
