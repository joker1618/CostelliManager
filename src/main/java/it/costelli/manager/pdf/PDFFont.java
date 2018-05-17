package it.costelli.manager.pdf;

/**
 * Created by f.barbano on 17/05/2018.
 */
public enum PDFFont {

	COURIER("Courier"),
	COURIER_BOLD("Courier-Bold"),
	COURIER_OBLIQUE("Courier-Oblique"),
	COURIER_BOLD_OBLIQUE("Courier-BoldOblique"),
	HELVETICA("Helvetica"),
	HELVETICA_BOLD("Helvetica-Bold"),
	HELVETICA_OBLIQUE("Helvetica-Oblique"),
	HELVETICA_BLOD_OBLIQUE("Helvetica-BoldOblique"),
	SYMBOL("Symbol"),
	TIMES_ROMAN("Times-Roman"),
	TIMES_BOLD("Times-Bold"),
	TIMES_ITALIC("Times-Italic"),
	TIMES_BOLD_ITALIC("Times-BoldItalic"),
	ZAP_DING_BATS("ZapfDingbats"),

	;

	private String label;

	PDFFont(String label) {
		this.label = label;
	}

	public String label() {
		return label;
	}

}
