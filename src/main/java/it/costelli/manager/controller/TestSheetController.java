package it.costelli.manager.controller;

import com.itextpdf.text.DocumentException;
import it.costelli.manager.logger.LogService;
import it.costelli.manager.logger.SimpleLog;
import it.costelli.manager.model.FieldType;
import it.costelli.manager.model.PdfField;
import it.costelli.manager.pdf.PDFFont;
import it.costelli.manager.pdf.PdfFacade;
import it.costelli.manager.util.FxUtils;
import it.costelli.manager.util.StrUtils;
import it.costelli.manager.view.components.BoxEsito;
import it.costelli.manager.view.components.BoxOsservazioni;
import it.costelli.manager.view.components.LabelTextField;
import javafx.beans.binding.Bindings;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextInputControl;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import static it.costelli.manager.model.FieldType.*;

/**
 * Created by f.barbano on 18/05/2018.
 */
public class TestSheetController implements Initializable {

	private static final SimpleLog logger = LogService.getLogger(TestSheetController.class);

	@FXML
	private VBox billContainer;

	// Row 1
	@FXML private LabelTextField fxFoglioCollaudoNum;
	@FXML private LabelTextField fxCliente;
	@FXML private LabelTextField fxImpiantoTipo;
	@FXML private LabelTextField fxSchedaNum;
	@FXML private LabelTextField fxCommessa;
	@FXML private LabelTextField fxMatricola;
	@FXML private LabelTextField fxCod;
	// Row 3
	@FXML private BoxEsito fxVerificaConformitaEsito;
	@FXML private BoxOsservazioni fxVerificaConformitaOsserv;
	// Row 4
	@FXML private LabelTextField fxControlloDimensionaleText;
	@FXML private BoxEsito fxControlloDimensionaleEsito;
	@FXML private BoxOsservazioni fxControlloDimensionaleOsserv;
	// Row 5
//	@FXML private CheckBoxCustom fxGuarnizioniStandard;
//	@FXML private CheckBoxCustom fxGuarnizioniViton;
//	@FXML private CheckBoxCustom fxGuarnizioniCustom;
//	@FXML private BoxEsito fxGuarnizioniEsito;
//	@FXML private BoxOsservazioni fxGuarnizioniOsserv;

	private final Map<FieldType,EditableField> fieldsMap = new HashMap<>();


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		TestSheetResizer.resizeBillView(billContainer, 900);
		manageFieldBindings();
	}

	private void manageFieldBindings() {
		// Row 1
		addEditableText(FOGLIO_COLLAUDO_NUM, fxFoglioCollaudoNum.getTextField());
		addEditableText(CLIENTE, fxCliente.getTextField());
		addEditableText(IMPIANTO_TIPO, fxImpiantoTipo.getTextField());
		addEditableText(SCHEMA_NUM, fxSchedaNum.getTextField());
		addEditableText(COMMESSA, fxCommessa.getTextField());
		addEditableText(MATRICOLA, fxMatricola.getTextField());
		addEditableText(COD, fxCod.getTextField());

		// Row 3
		addEditableCheckBox(VERIFICA_CONFORMITA_ESITO, fxVerificaConformitaEsito.getCheckBox(), fxVerificaConformitaOsserv.getTextArea());
		addEditableText(VERIFICA_CONFORMITA_OSSERV, fxVerificaConformitaOsserv.getTextArea());

		// Row 4
		addEditableText(CONTROLLO_DIMENSIONALE_TEXT, fxControlloDimensionaleText.getTextField());
		addEditableCheckBox(CONTROLLO_DIMENSIONALE_ESITO, fxControlloDimensionaleEsito.getCheckBox(), fxControlloDimensionaleOsserv.getTextArea());
		addEditableText(CONTROLLO_DIMENSIONALE_OSSERV, fxControlloDimensionaleOsserv.getTextArea());

		// Row 5
//		addEditableCheckBox(GUARNIZIONI_STANDARD, fxGuarnizioniStandard.getCheckBox());
//		addEditableCheckBox(GUARNIZIONI_VITON, fxGuarnizioniViton.getCheckBox());
//		addEditableCheckBox(GUARNIZIONI_CUSTOM, fxGuarnizioniCustom.getCheckBox());
//		addEditableText(GUARNIZIONI_CUSTOM_TEXT, fxGuarnizioniCustom.getTextField());
//		addEditableCheckBox(GUARNIZIONI_ESITO, fxGuarnizioniEsito.getCheckBox(), fxGuarnizioniOsserv.getTextArea());
//		addEditableText(GUARNIZIONI_OSSERV, fxGuarnizioniOsserv.getTextArea());

	}
	private void addEditableText(FieldType fieldType, TextInputControl textInput) {
		fieldsMap.put(fieldType, new EditableText(textInput));
	}
	private void addEditableCheckBox(FieldType fieldType, CheckBox checkBox, Node... nodesToDisable) {
		fieldsMap.put(fieldType, new EditableCheckBox(checkBox, nodesToDisable));
	}

	@FXML
	private void createPDF(ActionEvent event) throws IOException, DocumentException {
		FileChooser fc = new FileChooser();
		fc.setTitle("Select output path");
		fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF", "*.pdf"));
		File outFile = fc.showSaveDialog(FxUtils.getWindow(event));
		if(outFile != null) {
			Map<FieldType, String> pdfData = new HashMap<>();
			fieldsMap.forEach((ftype, efield) -> pdfData.put(ftype, efield.toStringField()));
			PdfFacade.fillPDFFields(outFile.toPath(), PDFFont.HELVETICA, 10, pdfData);
			FxUtils.showAlertInfo("Nuovo foglio di collaudo creato", "PDF path: %s", outFile);
		}
	}

	private abstract class EditableField<N extends Node, P extends Property> {
		private N node;
		private P property;

		protected EditableField(N node, P property) {
			this.node = node;
			this.property = property;
		}

		protected N getNode() {
			return node;
		}

		protected P getProperty() {
			return property;
		}

		public abstract String toStringField();
	}
	private class EditableText extends EditableField<TextInputControl,SimpleStringProperty> {
		EditableText(TextInputControl node) {
			super(node, new SimpleStringProperty(""));
			getNode().focusedProperty().addListener((obs,old,nez) -> {
				if(!nez) {
					String fval = StrUtils.safeTrim(getNode().getText());
					String pval = StrUtils.safeTrim(getProperty().getValue());
					getNode().setText(fval);
					if(!fval.equals(pval)) {
						getProperty().setValue(fval);
					}
				}
			});
		}

		@Override
		public String toStringField() {
			return getProperty().getValue();
		}
	}
	private class EditableCheckBox extends EditableField<CheckBox,SimpleBooleanProperty> {
		EditableCheckBox(CheckBox node, Node... disableNodes) {
			super(node, new SimpleBooleanProperty(false));
			getProperty().bind(getNode().selectedProperty());
			Arrays.asList(disableNodes).forEach(n -> n.disableProperty().bind(Bindings.createBooleanBinding(() -> !getProperty().getValue(), getProperty())));
		}

		@Override
		public String toStringField() {
			return getNode().isSelected() ? "X" : "";
		}
	}
}
