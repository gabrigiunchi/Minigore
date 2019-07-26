package model.game;

import model.game.Game.GameStrategy;

/**
 * Modalità di gioco uguale a quella base ma il protagonista ha 3 vite anzichè una.
 * 
 * @author Gabriele Giunchi
 */
public class MultipleLifesMod extends BasicGameStrategy implements GameStrategy {

	private static final long serialVersionUID = -4987860858376985272L;
	private static final int HERO_LIVES = 3;
	
	@Override
	public void initGame(final Game game) {
		if (game.getEnvironment().isPresent()) {
			super.initGame(game);
			game.getEnvironment().get().getHero().get().setLives(HERO_LIVES);
		}
	}

}
