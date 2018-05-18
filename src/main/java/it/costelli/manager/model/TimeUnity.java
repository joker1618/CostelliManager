package it.costelli.manager.model;

/**
 * Created by f.barbano on 16/05/2018.
 */
public enum TimeUnity implements EnumUnity {

	HOUR("h"),
	MINUTE("min"),
	SEC("sec")
	;

	private String label;

	TimeUnity(String label) {
		this.label = label;
	}

	@Override
	public String getLabel() {
		return label;
	}
}
