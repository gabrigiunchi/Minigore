package controller;

import static utilities.SystemInformation.SOUNDS_FOLDER;

import java.util.EnumMap;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.Mixer;

/**
 *	Implementazione della musica dell'applicazione che aderisce al pattern singleton.
 *  @author Lorenzo Mazzesi
 */
public final class MyMusicPlayer implements MusicPlayer {
	
	/**
	 * Enum per i file musicali da riprodurre.
	 * @author Lorenzo Mazzesi
	 *
	 */
	public enum MyMusicFile {
		/**
		 * File per la musica di sottofondo.
		 */
		Background("background.wav"),
		/**
		 * File per la musica di fine partita.
		 */
		GameOver("gameOverSound.wav");
		
		private final String filename;
		
		private MyMusicFile(final String path) {
			this.filename = path;
		}
		/**
		 * Getter del nome del file audio.
		 * @return stringa contenente il nome del file
		 */
		public String getFilename() {
			return this.filename;
		}
	}
	
	private static final boolean DEFAULT_AUDIO_ENABLED = false;
	private static MusicPlayer one = new MyMusicPlayer();
	private boolean audioEnabled;
	private final EnumMap<MyMusicFile, Clip> map;
	
	private MyMusicPlayer() {
		this.audioEnabled = DEFAULT_AUDIO_ENABLED;
		this.map = new EnumMap<>(MyMusicFile.class);
	}
	/**
	 * Singleton per la realizzazione del music player.
	 * @return l'istanza del music player
	 */
	public static MusicPlayer getInstance() {
		return one;
	}
	
	private Clip loadClip(final String filename) {
		Clip clip = null;
		final Mixer.Info[] mixInfos = AudioSystem.getMixerInfo();
		final Mixer mixer = AudioSystem.getMixer(mixInfos[0]);
		final DataLine.Info dataInfo = new DataLine.Info(Clip.class, null);
		
		try {
			clip = (Clip) mixer.getLine(dataInfo);
			final AudioInputStream ais = AudioSystem.getAudioInputStream(MyMusicFile.class.getResource(SOUNDS_FOLDER + filename));
			clip.open(ais);
			clip.setFramePosition(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return clip;
	}

	@Override
	public void reproduce(final MyMusicFile file) {
		if (!this.audioEnabled) {
			return;
		}
		
		if (!this.map.containsKey(file)) {
			this.map.put(file, this.loadClip(file.getFilename()));
		}
		
		if (file.equals(MyMusicFile.Background)) {
			this.map.get(file).loop(Clip.LOOP_CONTINUOUSLY);
		} else {
			this.map.get(file).setFramePosition(0);
			this.map.get(file).start();
		}
	}
	
	@Override
	public void stop() {
		this.map.keySet().forEach(c -> map.get(c).stop());
	}

	@Override
	public void setAudio(final boolean on) {
		this.audioEnabled = on;
		
		if (!this.audioEnabled) {
			this.stop();
		}
	}

	@Override
	public boolean isAudioOn() {
		return this.audioEnabled;
	}
}
