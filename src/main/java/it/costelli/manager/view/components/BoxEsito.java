package it.costelli.manager.view.components;

import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;

/**
 * Created by f.barbano on 15/05/2018.
 */
public class BoxEsito extends VBox {

	private CheckBox checkBox;

	public BoxEsito() {
		super();
		this.checkBox = new CheckBox("");
		getChildren().add(checkBox);
	}

	public CheckBox getCheckBox() {
		return checkBox;
	}
}
