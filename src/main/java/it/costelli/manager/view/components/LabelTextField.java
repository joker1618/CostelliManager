package it.costelli.manager.view.components;

import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

/**
 * Created by f.barbano on 12/05/2018.
 */
public class LabelTextField extends BorderPane {

	private SimpleStringProperty labelField = new SimpleStringProperty("");

	private final Label label = new Label("");
	private final TextField textField = new TextField("");

	public LabelTextField() {
		super();

		setLeft(label);
		setCenter(textField);

		this.label.textProperty().bind(labelField);
		this.label.prefHeightProperty().bind(heightProperty());
		this.label.setAlignment(Pos.CENTER_LEFT);
		this.label.setPadding(new Insets(0, 5, 0, 0));
	}

	public String getLabelField() {
		return labelField.get();
	}

	public SimpleStringProperty labelFieldProperty() {
		return labelField;
	}

	public void setLabelField(String labelField) {
		this.labelField.set(labelField);
	}

	public TextField getTextField() {
		return textField;
	}
}
