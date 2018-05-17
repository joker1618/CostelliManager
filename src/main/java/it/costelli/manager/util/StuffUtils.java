package it.costelli.manager.util;

import static it.costelli.manager.util.StrUtils.strf;

/**
 * Created by f.barbano on 13/05/2018.
 */
public class StuffUtils {

	public static void display(String format, Object... params) {
		System.out.println(strf(format, params));
	}

}
