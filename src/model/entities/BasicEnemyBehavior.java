package model.entities;

import static model.environment.Direction.DOWN;
import static model.environment.Direction.LEFT;
import static model.environment.Direction.RIGHT;
import static model.environment.Direction.UP;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import model.entities.Enemy.EnemyBehavior;
import model.environment.Direction;
import model.environment.IPosition2D;
import model.environment.IllegalPositionException;

/**
 * Intelligenza artificiale dei nemici del gioco che inseguono l'eroe per colpirlo.
 *
 * @author Gabriele Giunchi
 */
public class BasicEnemyBehavior implements EnemyBehavior, Serializable {

	private static final long serialVersionUID = 562618693500371976L;
	
	private final List<Direction> noAvailableDirections;
	
	public BasicEnemyBehavior() {
		this.noAvailableDirections = new ArrayList<>();
	}
	
	/**
	 * Enemy cerca di muoversi verso il personaggio, se incontra un ostacolo memorizza quella direzione come "bad" e ne prova un'altra
	 * finchè non riesce a muoversi.
	 */
	@Override
	public void apply(final Enemy enemy) {
		if (enemy.getEnvironment() == null) {
			return;
		}
		
		if (enemy.getEnvironment().get().getHero().isPresent()) {
			final IPosition2D heroPos = enemy.getEnvironment().get().getHero().get().getPosition();
			final IPosition2D myPos = enemy.getPosition();
			
			final Direction dir = this.calculate(heroPos, myPos);
			enemy.changeDirection(dir);
			
			try {
				enemy.move(dir);
				this.noAvailableDirections.clear();
					
			} catch (IllegalPositionException e) {
				this.noAvailableDirections.add(dir);
			}
		} else {
			try {
				enemy.move(enemy.getDirection());
				this.noAvailableDirections.clear();
			} catch (IllegalPositionException e) {
				this.noAvailableDirections.add(enemy.getDirection());
				enemy.changeDirection(this.randomPosition());
			}
		}
	}
	
	private Direction calculate(final IPosition2D heroPos, final IPosition2D myPos) {
		Direction dir;
		final int xHero = heroPos.getX();
		final int yHero = heroPos.getY();
		final int myX = myPos.getX();
		final int myY = myPos.getY();
		
		/* Se la distanza nella direzione y è maggiore di quella nella direzione x 
		 * oppure se sono sulla stessa linea mi muovo nella direzione x, 
		 * altrimenti mi muovo nella direzione y
		 */
		if (Math.abs(myY - yHero) > Math.abs(myX - xHero) || yHero == myY) {
				dir = yHero > myY ? DOWN : UP;
		} else {
			dir = xHero > myX ? RIGHT : LEFT;
		}
	
		if (this.noAvailableDirections.contains(dir)) {
			return this.randomPosition();
		}
		
		return dir;
	}
	
	private Direction randomPosition() {
		final Random r = new Random();
		Direction dir = DOWN;

		do {
			final int x = r.nextInt(4);
			
			switch(x) {
				case 0: dir = UP; break;
				case 1: dir = DOWN; break;
				case 2: dir = LEFT; break;
				case 3: dir = RIGHT; break;
				default: break;
			}
		} while(this.noAvailableDirections.contains(dir) && this.noAvailableDirections.size() < 4);
		
		return dir;
	}
}
