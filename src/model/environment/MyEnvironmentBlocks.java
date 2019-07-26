package model.environment;

import java.util.Collections;
import java.util.Set;
import static utilities.SystemInformation.BLOCK_FOLDER;
import utilities.BlockLoader;

/**
 * Tipi di ecosistemi con ostacoli creati per l'applicazione.
 * 
 * @author Gabriele Giunchi
 */
public enum MyEnvironmentBlocks {
	
	/**
	 * Solamente i contorni dell'ecosistema.
	 */
	Default("defaultBlock.bin"),
	
	/**
	 * Qualche ostacolo in mezzo.
	 */
	Type1("Type1.bin");
	
	private Set<Block> blocks;
	private final String filename;
	
	private MyEnvironmentBlocks(final String file) {
		this.filename = file;
	}
	
	/**
	 * Ritona il set di ostacoli associati al valore dell'enumerazione.
	 * @return il set di ostacoli
	 */
	public Set<Block> getBlocks() {
		if (this.blocks == null) {
			try {
				this.blocks = BlockLoader.readFromFile(BLOCK_FOLDER + this.filename);
			} catch (Exception e) {
				System.out.println("(MyEnvironmentBlocks) Caricamento dei blocchi fallito : " + e);
				this.blocks = Collections.emptySet();
			}
		}
		
		return this.blocks;
	}
}
