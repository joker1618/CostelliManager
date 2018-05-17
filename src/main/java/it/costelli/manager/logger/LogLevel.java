package it.costelli.manager.logger;

import java.util.logging.Level;

/**
 * Created by f.barbano on 10/05/2018.
 */
public class LogLevel extends Level {
	/**
	 * Duplicate of SEVERE level (same value = 1000)
	 */
	public static final Level ERROR = new LogLevel("ERROR", 1000);

	/**
	 * DEBUG level value between INFO and CONFIG
	 */
	public static final Level DEBUG = new LogLevel("DEBUG", 750);

	public static final Level LISTENER = new LogLevel("LISTENER", 730);

	protected LogLevel(String name, int value) {
		super(name, value);
	}
}