package utilities;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collections;
import java.util.Set;
import model.environment.Block;

/**
 * Classe di utilità che legge e scrive set di ostacoli.
 * 
 * @author Gabriele Giunchi
 */
public final class BlockLoader {
	
	private BlockLoader() { }
	
	/**
	 * Legge un set di ostacoli da file.
	 * 
	 * @param filename : il file da cui leggere
	 * @return il set di ostacoli inmodificabile (read-only)
	 * @throws ClassNotFoundException
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public static Set<Block> readFromFile(final String filename) throws ClassNotFoundException, FileNotFoundException, IOException {
		final ObjectInputStream input = new ObjectInputStream(BlockLoader.class.getResourceAsStream(filename));
		final Set<Block> toReturn = (Set<Block>) input.readObject();
		input.close();
		return Collections.unmodifiableSet(toReturn);
	}
	
	/**
	 * Scrive su un file un set di ostacoli.
	 * 
	 * @param filename : il nome del file su cui scrivere
	 * @param blocks : il set di ostacoli
	 * @throws IOException
	 */
	public static void write(final String filename, final Set<Block> blocks) throws IOException {
		final ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(new File(filename)));
		output.writeObject(blocks);
		output.close();
		
	}
}
