package it.costelli.manager.pdf;

import com.itextpdf.text.Element;

import java.util.Arrays;

/**
 * Created by f.barbano on 21/05/2018.
 */
public enum PDFAlignment {
	LEFT(Element.ALIGN_LEFT),
	CENTER(Element.ALIGN_CENTER),
	RIGHT(Element.ALIGN_RIGHT),
	;

	private int num;

	PDFAlignment(int num) {
		this.num = num;
	}

	public int getNum() {
		return num;
	}

	public static PDFAlignment getByNum(int num) {
		return Arrays.stream(values()).filter(el -> el.num == num).findAny().orElse(null);
	}
}