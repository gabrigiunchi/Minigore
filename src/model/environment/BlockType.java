package model.environment;

import java.io.Serializable;

/**
 * Forma di un ostacolo. 
 * Questa caratteristica può essere utile per disegnare l'ostacolo più fedelmente.
 * 
 * @author Gabriele Giunchi
 */
public enum BlockType implements Serializable {
	
	CornerNordWest("Corner nord-ovest"),
	CornerNordEast("Corner nord-east"),
	CornerSudWest("Corner sud-ovest"),
	CornerSudEast("Corner sud-east"),
	Orizzontal("Orizzontal block"),
	Vertical("Vertical block");

	private final String blockName;
	
	private BlockType(final String name) {
		this.blockName = name;
	}
	
	@Override
	public String toString() {
		return this.blockName;
	}
}
