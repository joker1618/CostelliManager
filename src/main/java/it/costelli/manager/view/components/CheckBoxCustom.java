package it.costelli.manager.view.components;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import org.apache.commons.lang3.StringUtils;

import static xxx.joker.libs.core.utils.JkStrings.strf;


/**
 * Created by f.barbano on 12/05/2018.
 */
public class CheckBoxCustom extends BorderPane {

	private SimpleStringProperty labelCheckBox = new SimpleStringProperty("");
	private SimpleBooleanProperty showTextField = new SimpleBooleanProperty();
	private SimpleStringProperty paddingTRBL = new SimpleStringProperty();

	private final CheckBox checkBox = new CheckBox("");
	private final TextField textField = new TextField("");

	public CheckBoxCustom() {
		super();

		VBox leftBox = new VBox(checkBox);
		setLeft(leftBox);

		showTextField.addListener((obs,old,nez) -> { if(nez) setCenter(textField); });
		paddingTRBL.addListener((obs, old, nez) -> this.setStyle(strf("-fx-padding: %s", getPaddingTRBL())) );

		Bindings.createBooleanBinding(() -> StringUtils.isNotBlank(getLabelCheckBox()) && isShowTextField(), labelCheckBox, showTextField)
			.addListener((obs,old,nez) -> leftBox.setPadding(new Insets(0, (nez ? 10 : 0), 0, 0)));

		this.checkBox.textProperty().bind(labelCheckBox);
		this.textField.disableProperty().bind(Bindings.createBooleanBinding(() -> !checkBox.isSelected(), checkBox.selectedProperty()));
		leftBox.setAlignment(Pos.CENTER);

		this.checkBox.selectedProperty().addListener((obs,o,n) -> {
			if(!n) {
				this.textField.setText(null);
			}
		});
	}

	public boolean isSelected() {
		return checkBox.isSelected();
	}

	public String getText() {
		return textField.getText();
	}

	public String getLabelCheckBox() {
		return labelCheckBox.get();
	}

	public SimpleStringProperty labelCheckBoxProperty() {
		return labelCheckBox;
	}

	public void setLabelCheckBox(String labelCheckBox) {
		this.labelCheckBox.set(labelCheckBox);
	}

	public boolean isShowTextField() {
		return showTextField.get();
	}

	public SimpleBooleanProperty showTextFieldProperty() {
		return showTextField;
	}

	public void setShowTextField(boolean showTextField) {
		this.showTextField.set(showTextField);
	}

	public String getPaddingTRBL() {
		return paddingTRBL.get();
	}

	public SimpleStringProperty paddingTRBLProperty() {
		return paddingTRBL;
	}

	public void setPaddingTRBL(String paddingTRBL) {
		this.paddingTRBL.set(paddingTRBL);
	}

	public CheckBox getCheckBox() {
		return checkBox;
	}

	public TextField getTextField() {
		return textField;
	}
}
