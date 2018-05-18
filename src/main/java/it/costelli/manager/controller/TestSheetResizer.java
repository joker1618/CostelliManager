package it.costelli.manager.controller;

import it.costelli.manager.logger.LogService;
import it.costelli.manager.logger.SimpleLog;
import it.costelli.manager.view.components.BoxOsservazioni;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ObservableDoubleValue;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by f.barbano on 18/05/2018.
 */
class TestSheetResizer {

	private static final SimpleLog logger = LogService.getLogger(TestSheetResizer.class);

	private static final double CM_TO_PIXEL_MULTIPLIER = 28.3464567d;

	public static void resizeBillView(Pane container, int billWidth) {
		List<List<BoxDim>> dimsRows = computeDims(billWidth);

		Pane headerPane = getPane(container, 0);

		for(int i = 1; i < container.getChildren().size(); i++) {
			bindRowsGrid(headerPane, getPane(container, i));
		}
		resizeRowHeader(headerPane, billWidth);

//		for(int i = 1; i < container.getChildren().size(); i++) {
//			List<BoxDim> dims = dimsRows.get(i);
//			int numElems = ((Pane) container.getChildren().get(i)).getChildren().size();
//			for(int j = 0; j < numElems; j++) {
//				BoxDim boxDim = dims.get(j);
//				Pane p = (Pane) ((Pane) container.getChildren().get(i)).getChildren().get(j);
//				if(p.getStyleClass().contains("boxElem")) {
//					p.setPrefWidth(boxDim.pixelWidth);
//					p.setPrefHeight(boxDim.pixelHeight);
//					updateSpecificRowDims(p, boxDim, i, j);
//				}
//			}
//		}
	}

	private static void resizeRowHeader(Pane headerPane, int billWidth) {
		Pane logoBox = getPane(headerPane, 0);
		Pane centerBox = getPane(headerPane, 1);
		Pane rightBox = getPane(headerPane, 2);

		rightBox.prefWidthProperty().bind(Bindings.createDoubleBinding(() -> billWidth - (logoBox.getWidth() + centerBox.getWidth()), logoBox.widthProperty(), centerBox.widthProperty()));

		ImageView imageView = (ImageView) logoBox.getChildren().get(0);
		imageView.fitHeightProperty().bind(logoBox.heightProperty());
		imageView.fitWidthProperty().bind(Bindings.createDoubleBinding(() -> 175.0 * imageView.getFitHeight() / 102.0, imageView.fitHeightProperty()));

		centerBox.setPrefWidth(billWidth*0.5);
	}
	private static void bindRowsGrid(Pane headerPane, Pane rowPane) {
		Pane logoBox = getPane(headerPane, 0);
		Pane centerBox = getPane(headerPane, 1);
		Pane rightBox = getPane(headerPane, 2);

		ReadOnlyDoubleProperty bright = rightBox.widthProperty();
		SimpleDoubleProperty bcenter = new SimpleDoubleProperty(60.0);
		DoubleBinding bleft = Bindings.createDoubleBinding(() -> logoBox.getWidth() + centerBox.getWidth() - bcenter.getValue(), logoBox.widthProperty(), centerBox.widthProperty(), bcenter);

		getPane(rowPane, 0).prefWidthProperty().bind(bleft);
		getPane(rowPane, 1).prefWidthProperty().bind(bcenter);
		getPane(rowPane, 2).prefWidthProperty().bind(bright);

		Pane ta = getPane(rowPane, 2);
		if(ta instanceof BoxOsservazioni) {
			BoxOsservazioni boxOss = (BoxOsservazioni) ta;
			boxOss.getTextArea().setPrefHeight(0.0);
			boxOss.getTextArea().prefHeightProperty().bind(boxOss.heightProperty());
			boxOss.setPrefHeight(10.0);
		}
	}
	private static Pane getPane(Node node, int numChild) {
		return (Pane) ((Pane)node).getChildren().get(numChild);
	}

	private static List<List<BoxDim>> computeDims(int billWidth) {
		List<List<BoxDim>> dims = new ArrayList<>();

		// Header row
		int h = 70;
		int logoWidth = (int) Math.ceil(175.0 * (h / 102.0));
		BoxDim hbox1 = new BoxDim(logoWidth, h);
		BoxDim hbox2 = new BoxDim((int)Math.round(billWidth * 0.50), h);
		BoxDim hbox3 = new BoxDim(billWidth-hbox1.pixelWidth-hbox2.pixelWidth, h);
		dims.add(Arrays.asList(hbox1, hbox2, hbox3));

		// Grid rows
		h = 20;
		BoxDim gbox3 = new BoxDim(hbox3.pixelWidth, h);
		BoxDim gbox2 = new BoxDim(80, h);
		BoxDim gbox1 = new BoxDim(billWidth - gbox2.pixelWidth - gbox3.pixelWidth, h);
		List<BoxDim> gridRowDims = Arrays.asList(gbox1, gbox2, gbox3);
		for(int i = 0; i < 18; i++) 	dims.add(new ArrayList<>(gridRowDims));

		return dims;
	}


	private static class BoxDim {
		private int pixelWidth;
		private int pixelHeight;

		BoxDim(int pixelWidth, int pixelHeight) {
			this.pixelWidth = pixelWidth;
			this.pixelHeight = pixelHeight;
		}
	}
}