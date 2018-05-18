package it.costelli.manager.view.components;

import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.Background;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 * Created by f.barbano on 15/05/2018.
 */
public class BoxEsito extends VBox {

	private CheckBox checkBox;

	public BoxEsito() {
		super();
		this.checkBox = new CheckBox("");
		getChildren().add(checkBox);
		setAlignment(Pos.CENTER);
	}

	public CheckBox getCheckBox() {
		return checkBox;
	}
}
