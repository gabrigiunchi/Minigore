package model.environment;

import java.io.Serializable;

/**
 * Implementazione dell'interfaccia {@link EnvironmentBounds}.
 *
 * @author Gabriele Giunchi
 */
public final class EnvironmentBoundsImpl implements EnvironmentBounds, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private final int xUpperLimit;
	private final int yUpperLimit;
	private final int yLowerLimit;
	private final int xLowerLimit;
	
	private EnvironmentBoundsImpl(final int xUpper, final int xLower, final int yUpper, final int yLower) {
		this.xUpperLimit = xUpper;
		this.yUpperLimit = yUpper;
		this.xLowerLimit = xLower;
		this.yLowerLimit = yLower;
	}

	@Override
	public boolean isWithinBounds(final IPosition2D pos) {
		final int x = pos.getX();
		final int y = pos.getY();
		
		return x >= this.xLowerLimit && x <= xUpperLimit && y >= yLowerLimit && y <= yUpperLimit;
	}
	
	@Override
	public int getXUpper() {
		return this.xUpperLimit;
	}
	
	@Override
	public int getXLower() {
		return this.xLowerLimit;
	}
	
	@Override
	public int getYUpper() {
		return this.yUpperLimit;
	}
	
	@Override
	public int getYLower() {
		return this.yLowerLimit;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + xLowerLimit;
		result = prime * result + xUpperLimit;
		result = prime * result + yLowerLimit;
		result = prime * result + yUpperLimit;
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final EnvironmentBoundsImpl other = (EnvironmentBoundsImpl) obj;
		if (xLowerLimit != other.xLowerLimit) {
			return false;
		}
		if (xUpperLimit != other.xUpperLimit) {
			return false;
		}
		if (yLowerLimit != other.yLowerLimit) {
			return false;
		}
		if (yUpperLimit != other.yUpperLimit) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return this.xUpperLimit + " " + this.yUpperLimit;
	}
	
	/** 
	 * Static factory di EnvironmentBound.
	 * @author Gabriele Giunchi
	 *
	 */
	public static class Builder {
		
		
		private static final int DEFAULT_X_UPPER = 30;
		private static final int DEFAULT_Y_UPPER = 20;
		
		private int xUpperLimit;
		private int yUpperLimit;
		private int yLowerLimit;
		private int xLowerLimit;
		
		/**
		 * Di default vengono messi i valori predefiniti.
		 */
		public Builder() {
			this.setDefaultBounds();
		}
		
		/**
		 * Setta i bordi di default.
		 * @return Builder
		 */
		public Builder defaultBounds() {
			this.setDefaultBounds();
			
			return this;
		}
		
		private void setDefaultBounds() {
			this.xUpperLimit = DEFAULT_X_UPPER;
			this.yUpperLimit = DEFAULT_Y_UPPER;
			this.xLowerLimit = 0;
			this.yLowerLimit = 0;
		}
		
		/**
		 * Setta il valore del limite superiore di x.
		 * @param x : il valore da settare
		 * @return Builder
		 */
		public Builder xUpper(final int x) {
			this.xUpperLimit = x;
			return this;
		}
		
		/**
		 * Setta il valore del limite inferiore di x.
		 * @param x : il valore da settare
		 * @return Builder
		 */	
		public Builder xLower(final int x) {
			this.xLowerLimit = x;
			return this;
		}
		
		/**
		 * Setta il valore del limite superiore di y.
		 * @param y : il valore da settare
		 * @return Builder
		 */
		public Builder yUpper(final int y) {
			this.yUpperLimit = y;
			return this;
		}
		
		/**
		 * Setta il valore del limite inferiore di y.
		 * @param y : il valore da settare
		 * @return Builder
		 */
		public Builder yLower(final int y) {
			this.yLowerLimit = y;
			return this;
		}
		
		/**
		 * @return l'oggetto EnvironmentBounds
		 * @throws IllegalArgumentException se i valori non sono adeguati 
		 */
		public EnvironmentBoundsImpl create() throws IllegalArgumentException {
			if (this.yLowerLimit < this.yUpperLimit && this.xLowerLimit < this.xUpperLimit) {
				return new EnvironmentBoundsImpl(this.xUpperLimit, this.xLowerLimit, this.yUpperLimit, this.yLowerLimit);
			}
			
			throw new IllegalArgumentException("I bordi inferiori devono essere minori di quelli superiori");
		}
	}
}
