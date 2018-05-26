package it.costelli.manager.model;

import it.costelli.manager.pdf.PDFAlignment;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleObjectProperty;
import xxx.joker.libs.javalibs.utils.JkConverter;

/**
 * Created by f.barbano on 17/05/2018.
 */
public class PdfField {

	private SimpleFloatProperty x;
	private SimpleFloatProperty y;
	private SimpleFloatProperty endX;
	private SimpleFloatProperty endY;
	private SimpleObjectProperty<PDFAlignment> alignment;

	public PdfField(String x, String y, String endX, String endY, String alignment) {
		this(x, y, endX, endY, PDFAlignment.getByNum(JkConverter.stringToInteger(alignment)));
	}
	public PdfField(String x, String y, String endX, String endY, PDFAlignment alignment) {
		this.x = new SimpleFloatProperty(JkConverter.stringToFloat(x));
		this.y = new SimpleFloatProperty(JkConverter.stringToFloat(y));
		this.endX = new SimpleFloatProperty(JkConverter.stringToFloat(endX));
		this.endY = new SimpleFloatProperty(JkConverter.stringToFloat(endY));
		this.alignment = new SimpleObjectProperty<>(alignment);
	}
	private PdfField(double x, double y, double endX, double endY, PDFAlignment alignment) {
		this.x = new SimpleFloatProperty((float)x);
		this.y = new SimpleFloatProperty((float)y);
		this.endX = new SimpleFloatProperty((float)endX);
		this.endY = new SimpleFloatProperty((float)endY);
		this.alignment = new SimpleObjectProperty<>(alignment);
	}

	public PdfField clone() {
		return new PdfField(getX(), getY(), getEndX(), getEndY(), getAlignment());
	}

	public void update(PdfField pdfField) {
		setX(pdfField.getX());
		setY(pdfField.getY());
		setEndX(pdfField.getEndX());
		setEndY(pdfField.getEndY());
		setAlignment(pdfField.getAlignment());
	}

	public float getX() {
		return x.get();
	}

	public SimpleFloatProperty xProperty() {
		return x;
	}

	public void setX(float x) {
		this.x.set(x);
	}

	public float getY() {
		return y.get();
	}

	public SimpleFloatProperty yProperty() {
		return y;
	}

	public void setY(float y) {
		this.y.set(y);
	}

	public float getEndX() {
		return endX.get();
	}

	public SimpleFloatProperty endXProperty() {
		return endX;
	}

	public void setEndX(float endX) {
		this.endX.set(endX);
	}

	public float getEndY() {
		return endY.get();
	}

	public SimpleFloatProperty endYProperty() {
		return endY;
	}

	public void setEndY(float endY) {
		this.endY.set(endY);
	}

	public PDFAlignment getAlignment() {
		return alignment.get();
	}

	public SimpleObjectProperty<PDFAlignment> alignmentProperty() {
		return alignment;
	}

	public void setAlignment(PDFAlignment alignment) {
		this.alignment.set(alignment);
	}

	public float getWidth() {
		return getEndX() - getX();
	}
	public float getHeight() {
		return getEndY() - getY();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof PdfField)) return false;

		PdfField pdfField = (PdfField) o;

		if (getX() != pdfField.getX()) return false;
		if (getY() != pdfField.getY()) return false;
		if (getEndX() != pdfField.getEndX()) return false;
		if (getEndY() != pdfField.getEndY()) return false;
		return getAlignment().equals(pdfField.getAlignment());
	}

	@Override
	public int hashCode() {
		float result = getX();
		result = 31 * result + getY();
		result = 31 * result + getEndX();
		result = 31 * result + getEndY();
		result = 31 * result + getAlignment().hashCode();
		return (int) result;
	}
}
