package it.costelli.manager.view.components;

import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

/**
 * Created by f.barbano on 15/05/2018.
 */
public class BoxOsservazioni extends VBox {

	private TextArea textArea;

	public BoxOsservazioni() {
		super();
		this.textArea = new TextArea("");
		getChildren().add(textArea);
	}

	public TextArea getTextArea() {
		return textArea;
	}
}
