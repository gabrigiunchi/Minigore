package model.environment;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import model.entities.Enemy;
import model.entities.Entity;
import model.entities.Hero;
import model.entities.Shot;

/**
 * Implementazione di {@link Environment}. Aderisce al pattern singleton.
 * 
 * @author Gabriele Giunchi
 */
public final class MyEnvironment implements Environment, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * I bordi predefiniti dell'ecosistema. Se non ne vengono settati esplicitamente altri verranno usati questi
	 */
	public static final EnvironmentBounds DEFAULT_BOUNDS = new EnvironmentBoundsImpl.Builder().defaultBounds().create();
	
	/**
	 * Il set di ostacoli predefinito.
	 */
	public static final Set<Block> DEFAULT_BLOCKS = Collections.emptySet();
	
	private static MyEnvironment singleton;
	
	private EnvironmentBounds bounds;
	private final List<Enemy> enemies;
	private final List<Shot> shots;
	private Hero hero;
	private Set<Block> blocks;
	
	private MyEnvironment() {
		this.bounds = DEFAULT_BOUNDS;
		this.blocks = DEFAULT_BLOCKS;
		
		this.enemies = new CopyOnWriteArrayList<>();
		this.shots = new CopyOnWriteArrayList<>();
	}
	
	/**
	 * Ritona l'unica istanza della classe MyEnvironment.
	 * @return : l'oggetto singleton
	 */
	public static synchronized Environment get() {
		if (singleton == null) {
			singleton = new MyEnvironment();
		}
		
		return singleton;
	}

	@Override
	public void canMoveTo(final IPosition2D pos) throws IllegalPositionException {
		if (this.bounds != null && !this.bounds.isWithinBounds(pos)) {
			throw new IllegalPositionException(pos);
		}
		
		if (this.blocks.stream().anyMatch(e -> e.isHere(pos))) {
			throw new IllegalPositionException(pos);
		}
	}

	@Override
	public void addShot(final Shot shot) {
		this.shots.add(shot);
	}
	
	@Override
	public void setHero(final Hero hero) {
		this.hero = hero;
	}

	@Override
	public void addEnemy(final Enemy enemy) {
		this.enemies.add(enemy);
	}
	
	@Override
	public boolean remove(final Entity entity) {
		if (this.shots != null && this.shots.contains(entity)) {
			return this.shots.remove(entity);
		}
		
		if (this.enemies != null && this.enemies.contains(entity)) {
			return this.enemies.remove(entity);
		}
		
		if (this.hero.equals(entity)) {
			this.hero = null;
		}
		
		return false;
	}
	
	@Override
	public void clear() {
		this.enemies.clear();
		this.shots.clear();
		this.hero = null;
	}
	
	@Override
	public void setBlocks(final Set<Block> blocks) {
		this.blocks = blocks;
	}

	@Override
	public void setBounds(final EnvironmentBounds bounds) {
		this.bounds = bounds;
	}
	
	@Override
	public List<Enemy> getEnemies() {
		if (this.enemies != null) {
			return Collections.unmodifiableList(this.enemies);
		}
		return Collections.emptyList();
	}

	@Override
	public List<Shot> getShots() {
		if (this.shots != null) {
			return Collections.unmodifiableList(this.shots);
		}
		
		return Collections.emptyList();
	}
	
	@Override
	public Set<Block> getBlocks() {
		if (this.blocks != null) {
			return this.blocks;
		}
		
		return Collections.emptySet();
	}
	
	@Override
	public Optional<Hero> getHero() {
		return Optional.ofNullable(this.hero);
	}
	
	@Override
	public Optional<EnvironmentBounds> getBounds() {
		return Optional.ofNullable(this.bounds);
	}
	
	@Override
	public String toString() {
		final StringBuilder string = new StringBuilder("Has bounds:");
		
		string.append(this.bounds == null ? "false" : "true");
		string.append(", has blocks:");
		string.append(this.blocks.isEmpty() ? "false" : "true");
		
		return string.toString();
	}
}
