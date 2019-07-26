package utilities;

import java.util.Random;

/**
 * Frasi divertenti da mostrare quando il giocatore perde.
 * 
 * @author Mattia Ricci 
 */
public enum FunnyStrings {
	
	Really("Really? Again?"),
	OhMyGod("Oh my god....."),
	Seriously("Seriously, change game"),
	StopTrying("Stop trying, you're too noob"),
	Why("Outside is a sunny day, leave this game alone"),
	Better("I think you can't do better than that"),
	Drunk("Are you drunk?"),
	Child("Maybe a newborn child could do better.."),
	Times("Time rolls on and you're still here..."),
	God("Where is your god now?"),
	Keys("You seem to be in trouble"),
	Facebook("Go back to Facebook"),
	NeverHappened("Let's pretend that never happened"),
	Expected("I don't know what I was expecting"),
	ShotNotFound("404 - shot not found"),
	CandyCrush("Have you tried Candy Crush?"),
	FirsTime("Is this your first time?"),
	Focus("Focus is everything"),
	TryAgain("You know you have to try again");
	
	private final String message;
	
	private FunnyStrings(final String msg) {
		this.message = msg;
	}
	
	@Override
	public String toString() {
		return this.message;
	}

	/**
	 * @return una stringa casuale tra quelle disponibili
	 */
	public static String random() {
		final Random r = new Random();
		final FunnyStrings[] strings = FunnyStrings.values();
		return strings[r.nextInt(strings.length)].toString();
	}
}
