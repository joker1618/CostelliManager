package it.costelli.manager.logger;

import java.io.IOException;
import java.util.Arrays;
import java.util.logging.*;

import static it.costelli.manager.util.StrUtils.strf;


/**
 * Created by f.barbano on 30/03/2018.
 */
public class LogService {

	public static SimpleLog getLogger(Class<?> loggerClass) {
		return new SimpleLog(loggerClass.getName());
	}

	public static void init(String rootLoggerName, Level level) throws IOException {
		// Turn off global logger
		Logger global = Logger.getLogger("");
//		global.setLevel(Level.OFF);
//		Arrays.stream(global.getHandlers()).forEach(global::removeHandler);

		// Config root logger
		Logger rootLogger = Logger.getLogger(rootLoggerName);
//		rootLogger.setUseParentHandlers(false);
		rootLogger.setLevel(Level.ALL);

		// Console handler
		ConsoleHandler consoleHandler = new ConsoleHandler();
		consoleHandler.setLevel(level);
//		consoleHandler.setFormatter(getSimpleFormatter());
		rootLogger.addHandler(consoleHandler);
	}

	private static Formatter getSimpleFormatter() {
		return new Formatter() {
			@Override
			public String format(LogRecord record) {
				String mex = super.formatMessage(record);
				return strf("%-7s - %s\n", record.getLevel(), mex);
			}
		};
	}
}
