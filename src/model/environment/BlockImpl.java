package model.environment;

import java.io.Serializable;

import model.entities.AbstractEntity;

/**
 * Implementazione di {@link Block}.
 * 
 * @author Gabriele Giunchi
 */
public class BlockImpl extends AbstractEntity implements Block, Serializable {

	private static final long serialVersionUID = 1607923180910141467L;
	
	private final BlockType type;

	/**
	 * Costruzione di un blocco a partire dalla posizione e dalla forma.
	 * @param pos : la posizione del blocco
	 * @param blockType : il tipo di blocco, la sua forma
	 */
	public BlockImpl(final IPosition2D pos, final BlockType blockType) {
		super(pos);
		this.type = blockType;
	}
	
	/**
	 * @return il tipo dell'ostacolo, la sua forma
	 */
	public BlockType getType() {
		return this.type;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		
		if (getClass() != obj.getClass()) {
			return false;
		}
		final BlockImpl other = (BlockImpl) obj;
		if (type != other.type || !position.equals(other.position)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return super.getPosition().toString() + "," + this.type;
	}
}
