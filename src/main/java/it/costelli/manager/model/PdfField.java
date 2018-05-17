package it.costelli.manager.model;

import javafx.beans.property.SimpleFloatProperty;

/**
 * Created by f.barbano on 17/05/2018.
 */
public class PdfField {

	private SimpleFloatProperty x;
	private SimpleFloatProperty y;
	private SimpleFloatProperty endX;
	private SimpleFloatProperty endY;

	public PdfField(double x, double y, double endX, double endY) {
		this.x = new SimpleFloatProperty((float)x);
		this.y = new SimpleFloatProperty((float)y);
		this.endX = new SimpleFloatProperty((float)endX);
		this.endY = new SimpleFloatProperty((float)endY);
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
}
