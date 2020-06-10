package it.costelli.manager.model;

import java.util.function.Predicate;

/**
 * Created by f.barbano on 18/05/2018.
 */
public interface EnumUnity {

	String getLabel();

	static EnumUnity fromLabel(String label) {
		EnumUnity value = TimeUnity.fromLabel(label);
		if(value == null) {
			value = PressureUnity.fromLabel(label);
		}
		return value;
	}

}
