package test;

import java.io.IOException;
import java.util.Collections;

import model.entities.BasicEnemy;
import model.entities.BasicWeapon;
import model.entities.Enemy;
import model.entities.Hero;
import model.entities.HeroImpl;
import model.entities.Shot;
import model.entities.ShotsOverException;
import model.environment.Environment;
import model.environment.EnvironmentBoundsImpl;
import model.environment.MyEnvironment;
import model.environment.IllegalPositionException;
import model.environment.MyEnvironmentBlocks;
import model.environment.Position2D;
import model.game.Game;
import model.game.GameImpl;
import model.game.MyGameStrategies;

import org.junit.Test;

import utilities.GameLoader;
import utilities.SystemInformation;
import static model.environment.Direction.DOWN;
import static model.environment.Direction.LEFT;
import static model.environment.Direction.RIGHT;
import static model.environment.Direction.UP;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Test automatici sul modello dell'applicazione.
 * 
 * @author Gabriele Giunchi
 */
public class MyTest {
	
	/**
	 * Test sui movimenti.
	 */
	@Test
	public void movimento() {
		// Creo un ecosistema di grandezza 30x20 senza ostacoli
		final Environment env = MyEnvironment.get();
		env.setBounds(new EnvironmentBoundsImpl.Builder().xUpper(30).yUpper(20).create());
		env.setBlocks(Collections.emptySet());
		env.clear();
		
		// Creo un nemico e un eroe
		final Enemy enemy = new BasicEnemy(env, new Position2D(1, 1));
		final Hero hero = new HeroImpl(env, new Position2D(10, 5));
		
		// muovo enemy verso destra per 10
		for (int i = 0; i < 10; i++) {
			try {
				enemy.move(RIGHT);
			} catch (IllegalPositionException e) {
				System.out.println(e);
				fail();
			}
		}
		
		assertTrue(enemy.isHere(new Position2D(11, 1)));
		assertFalse(enemy.isHere(hero.getPosition()));
		
		// Muovo hero fino al bordo inferiore
		for (int i = 0; i < 15; i++) {
			try {
				hero.move(DOWN);
			} catch (IllegalPositionException e) {
				System.out.println(e);
				fail();
			}
		}
		assertTrue(hero.isHere(new Position2D(10, 20)));
		
		// Se hero prova a muoversi verso sud si scontra contro il bordo e viene lanciata un'eccezione
		try {
			hero.move(DOWN);
			fail();
		} catch (IllegalPositionException e) { 
			System.out.println("OK");
		}
		
		// Muovo hero vero il bordo superiore
		for (int i = 0; i < 20; i++) {
			try {
				hero.move(UP);
			} catch (IllegalPositionException e) {
				fail();
			}
		}
		assertTrue(hero.isHere(new Position2D(10, 0)));
		hero.changeDirection(UP);
		assertTrue(hero.getDirection().equals(UP));
		try {
			hero.move(hero.getDirection());
			fail();
		} catch (IllegalPositionException e) { 
			System.out.println("OK");
		}
		
		// Controllo simile su bordo destro e sinistro
		for (int i = 0; i < 20; i++) {
			try {
				hero.move(RIGHT);
			} catch (IllegalPositionException e) {
				fail();
			}
		}
		assertTrue(hero.isHere(new Position2D(30, 0)));
		hero.changeDirection(RIGHT);
		try {
			hero.move(hero.getDirection());
			fail();
		} catch (IllegalPositionException e) { 
			System.out.println("OK");
		}
		
		for (int i = 0; i < 30; i++) {
			try {
				hero.move(LEFT);
			} catch (IllegalPositionException e) {
				fail();
			}
		}
		assertTrue(hero.isHere(new Position2D(0, 0)));
		hero.changeDirection(LEFT);
		try {
			hero.move(hero.getDirection());
			fail();
		} catch (IllegalPositionException e) {
			System.out.println("OK");
		}
	}
	
	/**
	 * Test sulle collisioni.
	 */
	@Test
	public void collisioni() {
		final Environment env = MyEnvironment.get();
		env.setBlocks(Collections.emptySet());
		env.clear();
		
		final Hero hero = new HeroImpl(env, new Position2D(10, 10));
		final Enemy enemy1 = new BasicEnemy(env, new Position2D(5, 10));
		final Enemy enemy2 = new BasicEnemy(env, new Position2D(0, 0));
		
		env.addEnemy(enemy1);
		env.addEnemy(enemy2);
		env.setHero(hero);
		
		// Muovo enemy verso hero, senza farli scontrare
		for (int i = 0; i < 4; i++) {
			try {
				enemy1.move(RIGHT);
			} catch (IllegalPositionException e) {
				fail();
			}
		}
		assertTrue(enemy1.isHere(new Position2D(9, 10)));
		// Muovo enemy1 nella stessa posizione di hero
		try {
			enemy1.move(RIGHT);
		} catch (IllegalPositionException e) {
			fail();
		}
		assertTrue(enemy1.isHere(hero.getPosition()));
		// enemy1 colpisce hero e lo uccide
		enemy1.checkCollision();
		assertTrue(hero.isDead());
		
		// Muovo enemy1 verso enemy2, non dovrebbe andare nella sua stessa posizione
		for (int i = 0; i < 9; i++) {
			try {
				enemy1.move(UP);
				enemy1.move(LEFT);
			} catch (Exception e) {
				fail();
			}
		}
		assertTrue(enemy1.isHere(new Position2D(1, 1)));
		try {
			enemy2.move(DOWN);
		} catch (Exception e) {
			fail();
		}
		// enemy2 non può muoversi nella stessa posizione di enemy1
		try {
			enemy2.move(RIGHT);
			fail();
		} catch (IllegalPositionException e) { 
			System.out.println(e);
		}
	}
	
	 /**
	  * Collisioni contro i blocchi.
	  */
	@Test
	public void collisioniBlocchi() {
		final Environment env = MyEnvironment.get();
		env.setBlocks(MyEnvironmentBlocks.Type1.getBlocks());
		env.clear();
		
		assertTrue(env.getBlocks().size() > 0);
		
		final Enemy enemy = new BasicEnemy(env, new Position2D(1, 1));
		try {
			enemy.move(LEFT);
			fail();
		} catch (IllegalPositionException e) {
			System.out.println(e);
		}
		assertTrue(env.getBlocks().stream().anyMatch(e -> e.getPosition().equals(new Position2D(0, 0))));
	}
	
	/**
	 * Test sulle armi.
	 */
	@Test
	public void armi() {
		final Environment env = MyEnvironment.get();
		env.clear();
		
		final Hero hero = new HeroImpl(env, new Position2D(1, 1));
		hero.equip(new BasicWeapon(env, hero.getPosition()));
		final Enemy enemy = new BasicEnemy(env, new Position2D(10, 1));
		
		env.setHero(hero);
		env.addEnemy(enemy);
		hero.changeDirection(RIGHT);
		
		try {
			hero.useWeapon();
		} catch (ShotsOverException e2) {
			fail();
		}
		assertEquals(env.getShots().size(), 1);
		assertTrue(env.getShots().get(0).isHere(hero.getPosition()));
		
		for (int i = 0; i < 9; i++) {
			for (final Shot temp : env.getShots()) {
				try {
					temp.move();
				} catch (IllegalPositionException e1) {
					fail();
				}
			}
		}
		// Il proiettile è nella stessa posizione di enemy, quindi dovrebbe ucciderlo e il suo riferimento da env dovrebbe essere tolto
		assertTrue(env.getShots().get(0).isHere(enemy.getPosition()));
		env.getShots().forEach(e -> e.checkCollision());
		assertTrue(enemy.isDead());
		assertEquals(env.getShots().size(), 0);
	}
	
	/**
	 * Test sul salvataggio e caricamento del gioco su file.
	 */
	@Test
	public void gameLoaderTest() {
		final Environment env = MyEnvironment.get();
		final Game game = new GameImpl(env, MyGameStrategies.Basic);
		game.startGame();
		game.pause();
		assertTrue(env.getEnemies().size() > 0);
		assertTrue(env.getHero().isPresent());
		
		game.incrementScore(10);
		
		try {
			GameLoader.write(game, SystemInformation.SAVES_FILENAME);
		} catch (IOException e) {
			fail();
		}
		
		Game game2 = null;
		try {
			game2 = GameLoader.read(SystemInformation.SAVES_FILENAME);
		} catch (ClassNotFoundException | IOException e) {
			fail();
		}
		
		assertEquals(game.getScore(), game2.getScore());
		assertEquals(game.getEnvironment().get().getEnemies().size(), game2.getEnvironment().get().getEnemies().size());
		assertEquals(game.getEnvironment().get().getBounds().get(), game2.getEnvironment().get().getBounds().get());
		assertEquals(game.getEnvironment().get().getBlocks(), game2.getEnvironment().get().getBlocks());
	}
}