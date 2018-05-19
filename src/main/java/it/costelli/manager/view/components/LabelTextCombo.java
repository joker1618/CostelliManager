package it.costelli.manager.view.components;

import it.costelli.manager.model.EnumUnity;
import it.costelli.manager.model.PressureUnity;
import it.costelli.manager.model.TimeUnity;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;

/**
 * Created by f.barbano on 16/05/2018.
 */
public class LabelTextCombo extends BorderPane {

	private SimpleStringProperty labelField = new SimpleStringProperty("");
	private SimpleStringProperty itemsType = new SimpleStringProperty(""); // time, unity

	private final Label label = new Label("");
	private final TextField textField = new TextField("");
	private final ComboBox<EnumUnity> comboBox = new ComboBox<>();

	public LabelTextCombo() {
		super();

		VBox centerBox = new VBox(textField);
		centerBox.setPadding(new Insets(0, 5, 0, 5));

		setLeft(label);
		setCenter(centerBox);
		setRight(comboBox);

		// Label binding
		this.label.textProperty().bind(labelField);
		this.label.prefHeightProperty().bind(heightProperty());

		// ComboBox bindings
		SingleSelectionModel<EnumUnity> comboSelModel = this.comboBox.getSelectionModel();
		this.comboBox.setConverter(new StringConverter<EnumUnity>() {
			@Override
			public String toString(EnumUnity eu) {
				return eu.getLabel();
			}
			@Override
			public EnumUnity fromString(String string) {
				return null;
			}
		});
		this.itemsType.addListener((observable, oldValue, newValue) -> {
			EnumUnity[] values = "time".equalsIgnoreCase(getItemsType()) ? TimeUnity.values() : PressureUnity.values();
			this.comboBox.setItems(FXCollections.observableArrayList(values));
			comboSelModel.select(0);
		});
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

	public String getItemsType() {
		return itemsType.get();
	}

	public SimpleStringProperty itemsTypeProperty() {
		return itemsType;
	}

	public void setItemsType(String itemsType) {
		this.itemsType.set(itemsType);
	}

	public TextField getTextField() {
		return textField;
	}

	public ComboBox<EnumUnity> getComboBox() {
		return comboBox;
	}
}
