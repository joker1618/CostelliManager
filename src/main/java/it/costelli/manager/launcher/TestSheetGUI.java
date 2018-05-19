package it.costelli.manager.launcher;

import com.itextpdf.text.DocumentException;
import it.costelli.manager.controller.TestSheetController;
import it.costelli.manager.controller.TestSheetController.EditableField;
import it.costelli.manager.logger.LogService;
import it.costelli.manager.logger.SimpleLog;
import it.costelli.manager.model.FieldType;
import it.costelli.manager.pdf.PDFFont;
import it.costelli.manager.pdf.PdfFacade;
import it.costelli.manager.util.FxUtils;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.Effect;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.lang3.tuple.Pair;
import org.scenicview.ScenicView;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static it.costelli.manager.util.StuffUtils.display;

/**
 * Created by f.barbano on 17/05/2018.
 */
public class TestSheetGUI extends Application {

	private static final SimpleLog logger = LogService.getLogger(TestSheetGUI.class);

	private static boolean scenicView; // review remove

	private TestSheetController testSheetCtrl;

	public static void main(String[] args) throws IOException {
		scenicView = args.length > 0 && "-scenicView".equals(args[0]);
		LogService.init("it.costelli", Level.ALL);
		launch(args);
	}

	@Override
	public void start(Stage stage) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/testSheetView.fxml"));
		VBox testSheetPane = fxmlLoader.load();
		testSheetCtrl = fxmlLoader.getController();
		ScrollPane scrollPane = new ScrollPane(testSheetPane);
		scrollPane.setPadding(new Insets(2.0));
		scrollPane.setPrefWidth(970.0);

		VBox rightBox = createRightBox();

		HBox hBox = new HBox(scrollPane, rightBox);

		Scene scene = new Scene(hBox);
		stage.setScene(scene);
		stage.show();

		stage.setMaximized(true);

		if(scenicView) {
			ScenicView.show(scene);
		}
	}

	@Override
	public void stop() throws Exception {
		logger.info("end");
	}

	// review move in a controller all below
	private VBox createRightBox() {
		Button btnCreatePDF = new Button("CREA FOGLIO DI COLLAUDO");
		btnCreatePDF.setPrefWidth(150.0);
		btnCreatePDF.setPrefHeight(100.0);
		btnCreatePDF.setWrapText(true);
		btnCreatePDF.setStyle("-fx-font-size:16px;-fx-font-weight:bold;-fx-text-alignment:CENTER");
		btnCreatePDF.setOnAction(this::actionCreatePDF);
		VBox vbox = new VBox(btnCreatePDF);
		vbox.setPadding(new Insets(50.0));
		return vbox;
	}

	private void actionCreatePDF(ActionEvent event) {
		FileChooser fc = new FileChooser();
		fc.setTitle("Select output path");
		fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF", "*.pdf"));
		File outFile = fc.showSaveDialog(FxUtils.getWindow(event));
//		File outFile = new File("C:\\Users\\f.barbano\\Desktop\\ZZZZZ.pdf");//review to delete
		if(outFile == null) {
			return;
		}

		GridPane gridPane = new GridPane();

		// Combo font type
		ComboBox<PDFFont> comboFont = new ComboBox<>(FXCollections.observableArrayList(PDFFont.values()));
		comboFont.getSelectionModel().select(PDFFont.HELVETICA);
		gridPane.add(new Label("Tipo font:"), 0, 0);
		gridPane.add(comboFont, 1, 0);

		// Combo font size
		List<Integer> sizes = Stream.iterate(4, integer -> integer + 1).limit(40).collect(Collectors.toList());
		ComboBox<Integer> comboSize = new ComboBox<>(FXCollections.observableArrayList(sizes));
		comboSize.getSelectionModel().select((Integer)10);
		gridPane.add(new Label("Font size:"), 0, 1);
		gridPane.add(comboSize, 1, 1);

		// Create dialod
		Dialog<Pair<PDFFont,Integer>> dlg = new Dialog<>();
		dlg.getDialogPane().setContent(gridPane);
		dlg.getDialogPane().getButtonTypes().add(new ButtonType("CREATE PDF", ButtonBar.ButtonData.OK_DONE));
		dlg.setResultConverter(param -> param != null && param.getButtonData() == ButtonBar.ButtonData.OK_DONE ? Pair.of(comboFont.getSelectionModel().getSelectedItem(), comboSize.getSelectionModel().getSelectedItem()) : null);
		Optional<Pair<PDFFont, Integer>> resp = dlg.showAndWait();

		try {
			if (resp.isPresent()) {
				Map<FieldType, String> pdfData = new HashMap<>();
				Map<FieldType, EditableField> fieldsMap = testSheetCtrl.getFieldsMap();
				fieldsMap.forEach((ftype, efield) -> pdfData.put(ftype, efield.toStringField()));
				PdfFacade.fillPDFFields(outFile.toPath(), resp.get().getKey(), resp.get().getValue(), pdfData);
				FxUtils.showAlertInfo("Nuovo foglio di collaudo creato", "PDF path: %s", outFile);
			}

		} catch(Exception ex) {
			FxUtils.showAlertError("ERRORE NELLA CREAZIONE DEL PDF", ex.getMessage());
			throw new RuntimeException(ex);
		}
	}
}
