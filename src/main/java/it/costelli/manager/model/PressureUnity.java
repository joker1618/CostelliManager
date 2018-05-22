package it.costelli.manager.model;

/**
 * Created by f.barbano on 16/05/2018.
 */
public enum PressureUnity implements EnumUnity {

	BAR("bar"),
	MPA("MPa"),
	PA("Pa"),
	PSI("psi"),
	ATM("atm"),
	;

	private String label;

	PressureUnity(String label) {
		this.label = label;
	}

	@Override
	public String getLabel() {
		return label;
	}


}
