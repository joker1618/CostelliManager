package it.costelli.manager.model;

import it.costelli.manager.util.Converter;
import javafx.beans.property.SimpleFloatProperty;
import javafx.scene.control.TextField;

/**
 * Created by f.barbano on 17/05/2018.
 */
public class PdfField {

	private SimpleFloatProperty x;
	private SimpleFloatProperty y;
	private SimpleFloatProperty endX;
	private SimpleFloatProperty endY;

	public PdfField(String x, String y, String endX, String endY) {
		this.x = new SimpleFloatProperty(Converter.stringToFloat(x));
		this.y = new SimpleFloatProperty(Converter.stringToFloat(y));
		this.endX = new SimpleFloatProperty(Converter.stringToFloat(endX));
		this.endY = new SimpleFloatProperty(Converter.stringToFloat(endY));
	}
	public PdfField(double x, double y, double endX, double endY) {
		this.x = new SimpleFloatProperty((float)x);
		this.y = new SimpleFloatProperty((float)y);
		this.endX = new SimpleFloatProperty((float)endX);
		this.endY = new SimpleFloatProperty((float)endY);
	}

	public PdfField clone() {
		return new PdfField(getX(), getY(), getEndX(), getEndY());
	}

	public void update(PdfField pdfField) {
		setX(pdfField.getX());
		setY(pdfField.getY());
		setEndX(pdfField.getEndX());
		setEndY(pdfField.getEndY());
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
		return getEndY() == pdfField.getEndY();
	}

	@Override
	public int hashCode() {
		int result = (int) getX();
		result = 31 * result + (int) getY();
		result = 31 * result + (int) getEndX();
		result = 31 * result + (int) getEndY();
		return result;
	}
}
