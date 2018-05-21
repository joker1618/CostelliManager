package it.costelli.manager.controller;

import com.itextpdf.text.DocumentException;
import it.costelli.manager.logger.LogService;
import it.costelli.manager.logger.SimpleLog;
import it.costelli.manager.model.FieldType;
import it.costelli.manager.model.PdfField;
import it.costelli.manager.pdf.PDFFont;
import it.costelli.manager.pdf.PdfFacade;
import it.costelli.manager.util.*;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static it.costelli.manager.util.StrUtils.strf;

/**
 * Created by f.barbano on 17/05/2018.
 */
public class FieldPosEditorController implements Initializable {

	private static final SimpleLog logger = LogService.getLogger(FieldPosEditorController.class);

	@FXML private BorderPane container;

	@FXML private ImageView fxImagePDF;

	@FXML private VBox editorBox;

	@FXML private TextField fxText;
	@FXML private ComboBox<PDFFont> fxComboFontType;
	@FXML private ComboBox<Integer> fxComboFontSize;
	@FXML private TextField fxPosX;
	@FXML private TextField fxPosY;
	@FXML private TextField fxPosEndX;
	@FXML private TextField fxPosEndY;
	@FXML private Button btnPrintPDF;

	@FXML private GridPane panePosList;
	@FXML private Button btnSaveAllFieldPos;

	private List<PosRow> posRowList = new ArrayList<>();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initImageView(900);
		initCombos();
		initGridPaneXY();
		initBindings();
	}
	private void initImageView(double imageWidth) {
		Image image = fxImagePDF.getImage();
		double rel = image.getHeight() / image.getWidth();
		fxImagePDF.setFitWidth(imageWidth);
		fxImagePDF.setFitHeight(imageWidth*rel);
	}
	private void initCombos() {
		// Combo font type
		fxComboFontType.setItems(FXCollections.observableArrayList(PDFFont.values()));
		fxComboFontType.getSelectionModel().select(PDFFont.HELVETICA);
		// Combo font size
		AtomicInteger aint = new AtomicInteger(4);
		List<Integer> sizeList = Stream.generate(aint::getAndIncrement).limit(25).collect(Collectors.toList());
		fxComboFontSize.setItems(FXCollections.observableArrayList(sizeList));
		fxComboFontSize.getSelectionModel().select((Integer)10);
	}
	private void initGridPaneXY() {
		try {
			int rowNum = 0;
			for (FieldType ft : FieldType.values()) {
				PdfField pdfField = PdfFacade.getFieldPosition(ft);
				PosRow posRow = new PosRow(ft, pdfField);
				posRowList.add(posRow);

				panePosList.add(new Label(ft.grossoNum() + ""), 0, rowNum);
				panePosList.add(posRow.btnUpdate, 1, rowNum);
				panePosList.add(posRow.xpos, 2, rowNum);
				panePosList.add(posRow.ypos, 3, rowNum);
				panePosList.add(posRow.endXpos, 4, rowNum);
				panePosList.add(posRow.endYpos, 5, rowNum);
				panePosList.add(posRow.btnUse, 6, rowNum);

				Label nameLabel = new Label(ft.name());
				nameLabel.getStyleClass().add("labelLittle");
				panePosList.add(nameLabel, 7, rowNum);

				rowNum++;
			}
		} catch(IOException ex) {
			logger.severe(() -> strf("Unable to init grid pane fields positions: %s", ex.getMessage()));
		}
	}
	private void initBindings() {
		// Text fields 'focus released' bindings
		fxText.focusedProperty().addListener((obs,old,nez) -> {
			if(!nez) {
				String s = StrUtils.safeTrim(fxText.getText());
				fxText.setText(s);
			}
		});
		Arrays.asList(fxPosX, fxPosY, fxPosEndX, fxPosEndY).forEach(
			tf -> tf.focusedProperty().addListener((obs,old,nez) -> {
				if(!nez) {
					String s = StrUtils.safeTrim(tf.getText());
					Double dnum = Converter.stringToDouble(s);
					tf.setText(dnum == null ? "" : StuffUtils.viewDouble(dnum));
				}
			})
		);

		// Button 'btnPrintPDF' disable binding
		btnPrintPDF.disableProperty().bind(Bindings.createBooleanBinding(
			() -> StringUtils.isAnyBlank(fxText.getText(), fxPosX.getText(), fxPosY.getText(), fxPosEndX.getText(), fxPosEndY.getText()),
			fxText.textProperty(), fxPosX.textProperty(), fxPosY.textProperty(), fxPosEndX.textProperty(), fxPosEndY.textProperty()
		));
		// Size binding
		editorBox.prefWidthProperty().bind(Bindings.createDoubleBinding(
			() -> container.getWidth() - fxImagePDF.getFitWidth(),
			container.widthProperty(), fxImagePDF.fitWidthProperty()
		));
	}

	@FXML private void actionPrintOnPDF(ActionEvent event) throws IOException, DocumentException {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Select output path");
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF", "*.pdf"));
		File outFile = fileChooser.showSaveDialog(FxUtils.getWindow(event));
		if(outFile != null) {
			PDFFont fontType = fxComboFontType.getSelectionModel().getSelectedItem();
			Integer fontSize = fxComboFontSize.getSelectionModel().getSelectedItem();
			PdfField pdfField = new PdfField(fxPosX.getText(), fxPosY.getText(), fxPosEndX.getText(), fxPosEndY.getText());
			PdfFacade.writOnPDF(outFile.toPath(), fontType, fontSize, Pair.of(pdfField, fxText.getText()));
			FxUtils.showAlertInfo("Nuovo PDF creato", "Path: %s", outFile.toPath().toAbsolutePath());
		}
	}

	@FXML private void actionSaveAllFieldPos(ActionEvent event) throws IOException {
		if(StreamUtils.filter(posRowList, PosRow::isPosChanged).isEmpty()) {
			FxUtils.showAlertInfo("Nessuna modifica da salvare", "");
			return;
		}

		Map<FieldType,PdfField> fmap = new HashMap<>();
		posRowList.forEach(pr -> {
			fmap.put(pr.fieldType, pr.getNewPos());
			pr.updatePos();
		});

		PdfFacade.updatePositions(fmap);
		FxUtils.showAlertInfo("Posizioni salvate", "");
	}


	private class PosRow {
		FieldType fieldType;
		Label ftNum, ftName;
		TextField xpos, ypos, endXpos, endYpos;
		Button btnUse, btnUpdate;
		Pair<PdfField, PdfField> pair;

		PosRow(FieldType fieldType, PdfField pdfField) {
			this.fieldType = fieldType;

			pair = Pair.of(pdfField, pdfField.clone());

			ftNum = new Label(fieldType.grossoNum()+"");

			ftName = new Label(fieldType.name());
			ftName.getStyleClass().add("labelLittle");

			xpos = new TextField();
			xpos.getStyleClass().add("numField");
			xpos.setEditable(false);
			xpos.textProperty().bind(Bindings.createStringBinding(() -> StuffUtils.viewDouble(pair.getValue().getX()), pair.getValue().xProperty()));

			ypos = new TextField();
			ypos.getStyleClass().add("numField");
			ypos.setEditable(false);
			ypos.textProperty().bind(Bindings.createStringBinding(() -> StuffUtils.viewDouble(pair.getValue().getY()), pair.getValue().yProperty()));

			endXpos = new TextField();
			endXpos.getStyleClass().add("numField");
			endXpos.setEditable(false);
			endXpos.textProperty().bind(Bindings.createStringBinding(() -> StuffUtils.viewDouble(pair.getValue().getEndX()), pair.getValue().endXProperty()));

			endYpos = new TextField();
			endYpos.getStyleClass().add("numField");
			endYpos.setEditable(false);
			endYpos.textProperty().bind(Bindings.createStringBinding(() -> StuffUtils.viewDouble(pair.getValue().getEndY()), pair.getValue().endYProperty()));

			btnUse = new Button("USA");
			btnUse.setOnAction(e -> {
				fxPosX.setText(pair.getValue().getX()+"");
				fxPosY.setText(pair.getValue().getY()+"");
				fxPosEndX.setText(pair.getValue().getEndX()+"");
				fxPosEndY.setText(pair.getValue().getEndY()+"");
			});

			btnUpdate = new Button("AGGIORNA");
			btnUpdate.disableProperty().bind(Bindings.createBooleanBinding(
				() -> StringUtils.isAnyBlank(fxPosX.getText(), fxPosY.getText(), fxPosEndX.getText(), fxPosEndY.getText()),
				fxPosX.textProperty(), fxPosY.textProperty(), fxPosEndX.textProperty(), fxPosEndY.textProperty()
			));
			btnUpdate.setOnAction(e -> {
				pair.getValue().setX(Converter.stringToFloat(fxPosX.getText()));
				pair.getValue().setY(Converter.stringToFloat(fxPosY.getText()));
				pair.getValue().setEndX(Converter.stringToFloat(fxPosEndX.getText()));
				pair.getValue().setEndY(Converter.stringToFloat(fxPosEndY.getText()));
			});
		}

		boolean isPosChanged() {
			return !pair.getKey().equals(pair.getValue());
		}

		PdfField getNewPos() {
			return pair.getValue();
		}

		void updatePos() {
			if(isPosChanged()) {
				pair.getKey().update(pair.getValue());
			}
		}
	}
}
