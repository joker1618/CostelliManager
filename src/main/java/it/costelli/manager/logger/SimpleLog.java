package it.costelli.manager.logger;

import java.util.logging.Logger;

/**
 * Created by f.barbano on 21/08/2017.
 */
public class SimpleLog extends Logger {

	protected SimpleLog(String name) {
		super(name, null);
	}

	public void debug(String mex) {
		log(LogLevel.DEBUG, mex);
	}

	public void listener(String mex) {
		log(LogLevel.LISTENER, mex);
	}

}
