package it.costelli.manager.util;

import java.text.NumberFormat;
import java.util.Locale;

import static it.costelli.manager.util.StrUtils.strf;

/**
 * Created by f.barbano on 13/05/2018.
 */
public class StuffUtils {

	public static void display(String format, Object... params) {
		System.out.println(strf(format, params));
	}


	public static String viewDouble(double num) {
		return getNumberFormat().format(num);
	}
	private static NumberFormat getNumberFormat() {
		NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
		nf.setMinimumFractionDigits(2);
		nf.setMaximumFractionDigits(2);
		return nf;
	}
}
