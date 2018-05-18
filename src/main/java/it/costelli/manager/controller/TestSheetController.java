package it.costelli.manager.controller;

import com.itextpdf.text.DocumentException;
import it.costelli.manager.logger.LogService;
import it.costelli.manager.logger.SimpleLog;
import it.costelli.manager.model.EnumUnity;
import it.costelli.manager.model.FieldType;
import it.costelli.manager.pdf.PDFFont;
import it.costelli.manager.pdf.PdfFacade;
import it.costelli.manager.util.FxUtils;
import it.costelli.manager.util.StrUtils;
import it.costelli.manager.view.components.*;
import javafx.beans.binding.Bindings;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextInputControl;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

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


	@FXML private VBox billContainer;

	// Row 0
	@FXML private LabelTextField fxFoglioCollaudoNum;
	@FXML private LabelTextField fxCliente;
	@FXML private LabelTextField fxImpiantoTipo;
	@FXML private LabelTextField fxSchedaNum;
	@FXML private LabelTextField fxCommessa;
	@FXML private LabelTextField fxMatricola;
	@FXML private LabelTextField fxCod;
	// Row 2
	@FXML private BoxEsito fxVerificaConformitaEsito;
	@FXML private BoxOsservazioni fxVerificaConformitaOsserv;
	// Row 3
	@FXML private LabelTextField fxControlloDimensionaleText;
	@FXML private BoxEsito fxControlloDimensionaleEsito;
	@FXML private BoxOsservazioni fxControlloDimensionaleOsserv;
	// Row 4
	@FXML private CheckBoxCustom fxGuarnizioniStandard;
	@FXML private CheckBoxCustom fxGuarnizioniViton;
	@FXML private CheckBoxCustom fxGuarnizioniCustom;
	@FXML private BoxEsito fxGuarnizioniEsito;
	@FXML private BoxOsservazioni fxGuarnizioniOsserv;
	// Row 5
	@FXML private CheckBoxCustom fxPompeVariabile;
	@FXML private CheckBoxCustom fxPompeFissa;
	@FXML private CheckBoxCustom fxPompeIngranaggi;
	@FXML private CheckBoxCustom fxPompePalette;
	@FXML private CheckBoxCustom fxPompePistoni;
	@FXML private CheckBoxCustom fxPompeCustom;
	@FXML private LabelTextField fxPompeCustomTypeText;
	@FXML private CheckBoxCustom fxPompePortataMin;
	@FXML private BoxEsito fxPompeEsito;
	@FXML private BoxOsservazioni fxPompeOsserv;
	// Row 6
	@FXML private LabelTextCombo fxRegolatriciTaratura;
	@FXML private LabelTextCombo fxRegolatriciRipetibilta;
	@FXML private BoxEsito fxRegolatriciEsito;
	@FXML private BoxOsservazioni fxRegolatriciOsserv;
	// Row 7
	@FXML private LabelTextCombo fxProvaFunzionamentoPressione;
	@FXML private LabelTextCombo fxProvaFunzionamentoDurata;
	@FXML private BoxEsito fxProvaFunzionamentoEsito;
	@FXML private BoxOsservazioni fxProvaFunzionamentoOsserv;
	// Row 8
	@FXML private LabelTextCombo fxProvaSovrapressionePressione;
	@FXML private LabelTextCombo fxProvaSovrapressioneDurata;
	@FXML private BoxEsito fxProvaSovrapressioneEsito;
	@FXML private BoxOsservazioni fxProvaSovrapressioneOsserv;
	// Row 9
	@FXML private CheckBoxCustom fxValvoleSequenze;
	@FXML private CheckBoxCustom fxValvoleStrozzatori;
	@FXML private CheckBoxCustom fxValvoleTenutaNonRitorno;
	@FXML private CheckBoxCustom fxValvoleRiduzPressione;
	@FXML private CheckBoxCustom fxValvoleRegolPortataCompens;
	@FXML private CheckBoxCustom fxValvoleTenutaNonRitornoPil;
	@FXML private BoxEsito fxValvoleEsito;
	@FXML private BoxOsservazioni fxValvoleOsserv;
	// Row 10
	@FXML private LabelTextField fxAccessoriAccumulatore;
	@FXML private LabelTextField fxAccessoriP0Bar;
	@FXML private LabelTextField fxAccessoriNorme;
	@FXML private CheckBoxCustom fxAccessoriFiltriAsp;
	@FXML private CheckBoxCustom fxAccessoriFiltriMan;
	@FXML private CheckBoxCustom fxAccessoriFiltriRit;
	@FXML private CheckBoxCustom fxAccessoriScambioCaloreAria;
	@FXML private CheckBoxCustom fxAccessoriScambioCaloreAcqua;
	@FXML private CheckBoxCustom fxAccessoriScambioCaloreCustom;
	@FXML private BoxEsito fxAccessoriEsito;
	@FXML private BoxOsservazioni fxAccessoriOsserv;
	// Row 11
	@FXML private CheckBoxCustom fxStrumentiManometro;
	@FXML private CheckBoxCustom fxStrumentiTermometro;
	@FXML private CheckBoxCustom fxStrumentiPressTrasd;
	@FXML private CheckBoxCustom fxStrumentiTermostato;
	@FXML private CheckBoxCustom fxStrumentiLivelloStato;
	@FXML private CheckBoxCustom fxStrumentiCustom;
	@FXML private BoxEsito fxStrumentiEsito;
	@FXML private BoxOsservazioni fxStrumentiOsserv;
	// Row 12
	@FXML private CheckBoxCustom fxTenuteTappi;
	@FXML private CheckBoxCustom fxTenuteBlocchi;
	@FXML private CheckBoxCustom fxTenuteOblo;
	@FXML private CheckBoxCustom fxTenuteValvole;
	@FXML private CheckBoxCustom fxTenuteSerbatoio;
	@FXML private CheckBoxCustom fxTenuteRaccordi;
	@FXML private BoxEsito fxTenuteEsito;
	@FXML private BoxOsservazioni fxTenuteOsserv;
	// Row 13
	@FXML private CheckBoxCustom fxFluidoOlioMinerale;
	@FXML private CheckBoxCustom fxFluidoCustom;
	@FXML private LabelTextField fxFluidoTempCollaudo;
	@FXML private LabelTextField fxFluidoTempOlio;
	@FXML private BoxEsito fxFluidoEsito;
	@FXML private BoxOsservazioni fxFluidoOsserv;
	// Row 14
	public CheckBoxCustom fxFinituraStandard;
	public CheckBoxCustom fxFinituraRal;
	public CheckBoxCustom fxFinituraCustom;
	public BoxEsito fxFinituraEsito;
	public BoxOsservazioni fxFinituraOsserv;
	

	private final Map<FieldType,EditableField> fieldsMap = new HashMap<>();


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		TestSheetResizer.resizeBillView(billContainer, 950);
		manageSpecificResize();
		manageFieldBindings();
	}

	private void manageSpecificResize() {
		// POMPE - row 5
		Pane row5 = (Pane) fxPompeCustom.getParent();
		fxPompeCustom.prefWidthProperty().bind(Bindings.createDoubleBinding(() -> row5.getWidth() * 0.4, row5.widthProperty()));
//		((VBox) fxPompeCustom.getLeft()).setPadding(new Insets(0));
		
		// TENUTE - row 12
		fxTenuteTappi.prefHeightProperty().bind(fxTenuteSerbatoio.heightProperty());
		fxTenuteBlocchi.prefHeightProperty().bind(fxTenuteRaccordi.heightProperty());
		fxTenuteOblo.prefHeightProperty().bind(fxTenuteSerbatoio.heightProperty());
		fxTenuteValvole.prefHeightProperty().bind(fxTenuteRaccordi.heightProperty());
	}

	private void manageFieldBindings() {
		// Row 0
		addEditableText(FOGLIO_COLLAUDO_NUM, fxFoglioCollaudoNum.getTextField());
		addEditableText(CLIENTE, fxCliente.getTextField());
		addEditableText(IMPIANTO_TIPO, fxImpiantoTipo.getTextField());
		addEditableText(SCHEMA_NUM, fxSchedaNum.getTextField());
		addEditableText(COMMESSA, fxCommessa.getTextField());
		addEditableText(MATRICOLA, fxMatricola.getTextField());
		addEditableText(COD, fxCod.getTextField());

		// Row 2
		addEditableCheckBox(VERIFICA_CONFORMITA_ESITO, fxVerificaConformitaEsito.getCheckBox(), fxVerificaConformitaOsserv.getTextArea());
		addEditableText(VERIFICA_CONFORMITA_OSSERV, fxVerificaConformitaOsserv.getTextArea());

		// Row 3
		addEditableText(CONTROLLO_DIMENSIONALE_TEXT, fxControlloDimensionaleText.getTextField());
		addEditableCheckBox(CONTROLLO_DIMENSIONALE_ESITO, fxControlloDimensionaleEsito.getCheckBox(), fxControlloDimensionaleOsserv.getTextArea());
		addEditableText(CONTROLLO_DIMENSIONALE_OSSERV, fxControlloDimensionaleOsserv.getTextArea());

		// Row 4
		addEditableCheckBox(GUARNIZIONI_STANDARD, fxGuarnizioniStandard.getCheckBox());
		addEditableCheckBox(GUARNIZIONI_VITON, fxGuarnizioniViton.getCheckBox());
		addEditableCheckBox(GUARNIZIONI_CUSTOM, fxGuarnizioniCustom.getCheckBox());
		addEditableText(GUARNIZIONI_CUSTOM_TEXT, fxGuarnizioniCustom.getTextField());
		addEditableCheckBox(GUARNIZIONI_ESITO, fxGuarnizioniEsito.getCheckBox(), fxGuarnizioniOsserv.getTextArea());
		addEditableText(GUARNIZIONI_OSSERV, fxGuarnizioniOsserv.getTextArea());

		// Row 5
		addEditableCheckBox(POMPE_VARIABILE, fxPompeVariabile.getCheckBox());
		addEditableText(POMPE_VARIABILE_TEXT, fxPompeVariabile.getTextField());
		addEditableCheckBox(POMPE_FISSA, fxPompeFissa.getCheckBox());
		addEditableText(POMPE_FISSA_TEXT, fxPompeFissa.getTextField());
		addEditableCheckBox(POMPE_INGRANAGGI, fxPompeIngranaggi.getCheckBox());
		addEditableText(POMPE_INGRANAGGI_TEXT, fxPompeIngranaggi.getTextField());
		addEditableCheckBox(POMPE_PALETTE, fxPompePalette.getCheckBox());
		addEditableText(POMPE_PALETTE_TEXT, fxPompePalette.getTextField());
		addEditableCheckBox(POMPE_PISTONI, fxPompePistoni.getCheckBox());
		addEditableText(POMPE_PISTONI_TEXT, fxPompePistoni.getTextField());
		addEditableCheckBox(POMPE_CUSTOM, fxPompeCustom.getCheckBox(), fxPompeCustomTypeText.getTextField());
		addEditableText(POMPE_CUSTOM_TYPE, fxPompeCustom.getTextField());
		addEditableText(POMPE_CUSTOM_TYPE_TEXT, fxPompeCustomTypeText.getTextField());
		addEditableCheckBox(POMPE_PORTATA_MIN, fxPompePortataMin.getCheckBox());
		addEditableText(POMPE_PORTATA_MIN_TEXT, fxPompePortataMin.getTextField());
		addEditableCheckBox(POMPE_ESITO, fxPompeEsito.getCheckBox(), fxPompeOsserv.getTextArea());
		addEditableText(POMPE_OSSERV, fxPompeOsserv.getTextArea());

		// Row 6
		addEditableText(REGOLATRICI_TARATURA_TEXT, fxRegolatriciTaratura.getTextField());
		addEditableComboUnity(REGOLATRICI_TARATURA_UNITY, fxRegolatriciTaratura.getComboBox());
		addEditableText(REGOLATRICI_RIPETIBILITA_TEXT, fxRegolatriciRipetibilta.getTextField());
		addEditableComboUnity(REGOLATRICI_RIPETIBILITA_UNITY, fxRegolatriciRipetibilta.getComboBox());
		addEditableCheckBox(REGOLATRICI_ESITO, fxRegolatriciEsito.getCheckBox(), fxRegolatriciOsserv.getTextArea());
		addEditableText(REGOLATRICI_OSSERV, fxRegolatriciOsserv.getTextArea());

		// Row 7
		addEditableText(PROVA_FUNZIONAMENTO_PRESSIONE_TEXT, fxProvaFunzionamentoPressione.getTextField());
		addEditableComboUnity(PROVA_FUNZIONAMENTO_PRESSIONE_UNITY, fxProvaFunzionamentoPressione.getComboBox());
		addEditableText(PROVA_FUNZIONAMENTO_DURATA_TEXT, fxProvaFunzionamentoDurata.getTextField());
		addEditableComboUnity(PROVA_FUNZIONAMENTO_DURATA_UNITY, fxProvaFunzionamentoDurata.getComboBox());
		addEditableCheckBox(PROVA_FUNZIONAMENTO_ESITO, fxProvaFunzionamentoEsito.getCheckBox(), fxProvaFunzionamentoOsserv.getTextArea());
		addEditableText(PROVA_FUNZIONAMENTO_OSSERV, fxProvaFunzionamentoOsserv.getTextArea());

		// Row 8
		addEditableText(PROVA_SOVRAPRESSIONE_PRESSIONE_TEXT, fxProvaSovrapressionePressione.getTextField());
		addEditableComboUnity(PROVA_SOVRAPRESSIONE_PRESSIONE_UNITY, fxProvaSovrapressionePressione.getComboBox());
		addEditableText(PROVA_SOVRAPRESSIONE_DURATA_TEXT, fxProvaSovrapressioneDurata.getTextField());
		addEditableComboUnity(PROVA_SOVRAPRESSIONE_DURATA_UNITY, fxProvaSovrapressioneDurata.getComboBox());
		addEditableCheckBox(PROVA_SOVRAPRESSIONE_ESITO, fxProvaSovrapressioneEsito.getCheckBox(), fxProvaSovrapressioneOsserv.getTextArea());
		addEditableText(PROVA_SOVRAPRESSIONE_OSSERV, fxProvaSovrapressioneOsserv.getTextArea());

		// Row 9
		addEditableCheckBox(VALVOLE_SEQUENZE, fxValvoleSequenze.getCheckBox());
		addEditableCheckBox(VALVOLE_STROZZATORI, fxValvoleStrozzatori.getCheckBox());
		addEditableCheckBox(VALVOLE_TENUTA_NON_RITORNO, fxValvoleTenutaNonRitorno.getCheckBox());
		addEditableCheckBox(VALVOLE_RIDUZ_PRESSIONE, fxValvoleRiduzPressione.getCheckBox());
		addEditableCheckBox(VALVOLE_REGOL_PORTATA_COMPENS, fxValvoleRegolPortataCompens.getCheckBox());
		addEditableCheckBox(VALVOLE_TENUTA_NON_RITORNO_PIL, fxValvoleTenutaNonRitornoPil.getCheckBox());
		addEditableCheckBox(VALVOLE_ESITO, fxValvoleEsito.getCheckBox(), fxValvoleOsserv.getTextArea());
		addEditableText(VALVOLE_OSSERV, fxValvoleOsserv.getTextArea());

		// Row 10
		addEditableText(ACCESSORI_ACCUMULATORE, fxAccessoriAccumulatore.getTextField());
		addEditableText(ACCESSORI_P0_BAR, fxAccessoriP0Bar.getTextField());
		addEditableText(ACCESSORI_NORME, fxAccessoriNorme.getTextField());
		addEditableCheckBox(ACCESSORI_FILTRI_ASP, fxAccessoriFiltriAsp.getCheckBox(), fxAccessoriFiltriAsp.getTextField());
		addEditableText(ACCESSORI_FILTRI_ASP_TEXT, fxAccessoriFiltriAsp.getTextField());
		addEditableCheckBox(ACCESSORI_FILTRI_MAN, fxAccessoriFiltriMan.getCheckBox(), fxAccessoriFiltriMan.getTextField());
		addEditableText(ACCESSORI_FILTRI_MAN_TEXT, fxAccessoriFiltriMan.getTextField());
		addEditableCheckBox(ACCESSORI_FILTRI_RIT, fxAccessoriFiltriRit.getCheckBox(), fxAccessoriFiltriRit.getTextField());
		addEditableText(ACCESSORI_FILTRI_RIT_TEXT, fxAccessoriFiltriRit.getTextField());
		addEditableCheckBox(ACCESSORI_SCAMBIO_CALORE_ARIA, fxAccessoriScambioCaloreAria.getCheckBox());
		addEditableCheckBox(ACCESSORI_SCAMBIO_CALORE_ACQUA, fxAccessoriScambioCaloreAcqua.getCheckBox());
		addEditableCheckBox(ACCESSORI_SCAMBIO_CALORE_CUSTOM, fxAccessoriScambioCaloreCustom.getCheckBox(), fxAccessoriScambioCaloreCustom.getTextField());
		addEditableText(ACCESSORI_SCAMBIO_CALORE_CUSTOM_TEXT, fxAccessoriScambioCaloreCustom.getTextField());
		addEditableCheckBox(ACCESSORI_ESITO, fxAccessoriEsito.getCheckBox(), fxAccessoriOsserv.getTextArea());
		addEditableText(ACCESSORI_OSSERV, fxAccessoriOsserv.getTextArea());

		// Row 11
		addEditableCheckBox(STRUMENTI_MANOMETRO, fxStrumentiManometro.getCheckBox());
		addEditableCheckBox(STRUMENTI_TERMOMETRO, fxStrumentiTermometro.getCheckBox());
		addEditableCheckBox(STRUMENTI_PRESS_TRASD, fxStrumentiPressTrasd.getCheckBox());
		addEditableCheckBox(STRUMENTI_TERMOSTATO, fxStrumentiTermostato.getCheckBox());
		addEditableCheckBox(STRUMENTI_LIVELLOSTATO, fxStrumentiLivelloStato.getCheckBox());
		addEditableCheckBox(STRUMENTI_CUSTUM, fxStrumentiCustom.getCheckBox(), fxStrumentiCustom.getTextField());
		addEditableText(STRUMENTI_CUSTOM_TEXT, fxStrumentiCustom.getTextField());
		addEditableCheckBox(STRUMENTI_ESITO, fxStrumentiEsito.getCheckBox(), fxStrumentiOsserv.getTextArea());
		addEditableText(STRUMENTI_OSSERV, fxStrumentiOsserv.getTextArea());

		// Row 12
		addEditableCheckBox(TENUTE_TAPPI, fxTenuteTappi.getCheckBox());
		addEditableCheckBox(TENUTE_BLOCCHI, fxTenuteBlocchi.getCheckBox());
		addEditableCheckBox(TENUTE_OBLO, fxTenuteOblo.getCheckBox());
		addEditableCheckBox(TENUTE_VALVOLE, fxTenuteValvole.getCheckBox());
		addEditableCheckBox(TENUTE_SERBATOIO, fxTenuteSerbatoio.getCheckBox(), fxTenuteSerbatoio.getTextField());
		addEditableText(TENUTE_SERBATOIO_TEXT, fxTenuteSerbatoio.getTextField());
		addEditableCheckBox(TENUTE_RACCORDI, fxTenuteRaccordi.getCheckBox(), fxTenuteRaccordi.getTextField());
		addEditableText(TENUTE_RACCORDI_TEXT, fxTenuteRaccordi.getTextField());
		addEditableCheckBox(TENUTE_ESITO, fxTenuteEsito.getCheckBox(), fxTenuteOsserv.getTextArea());
		addEditableText(TENUTE_OSSERV, fxTenuteOsserv.getTextArea());

		// Row 13
		addEditableCheckBox(FLUIDO_OLIO_MINERALE, fxFluidoOlioMinerale.getCheckBox());
		addEditableCheckBox(FLUIDO_CUSTOM, fxFluidoCustom.getCheckBox(), fxFluidoCustom.getTextField());
		addEditableText(FLUIDO_CUSTOM_TEXT, fxFluidoCustom.getTextField());
		addEditableText(FLUIDO_TEMP_AMBIENTE_COLLAUDO_TEXT, fxFluidoTempCollaudo.getTextField());
		addEditableText(FLUIDO_TEMP_OLIO_TEXT, fxFluidoTempOlio.getTextField());
		addEditableCheckBox(FLUIDO_ESITO, fxFluidoEsito.getCheckBox(), fxFluidoOsserv.getTextArea());
		addEditableText(FLUIDO_OSSERV, fxFluidoOsserv.getTextArea());

		// Row 14
		addEditableCheckBox(FINITURA_STANDARD, fxFinituraStandard.getCheckBox());
		addEditableCheckBox(FINITURA_RAL, fxFinituraRal.getCheckBox(), fxFinituraRal.getTextField());
		addEditableText(FINITURA_RAL_TEXT, fxFinituraRal.getTextField());
		addEditableCheckBox(FINITURA_CUSTOM, fxFinituraCustom.getCheckBox(), fxFinituraCustom.getTextField());
		addEditableText(FINITURA_CUSTOM_TEXT, fxFinituraCustom.getTextField());
		addEditableCheckBox(FINITURA_ESITO, fxFinituraEsito.getCheckBox(), fxFinituraOsserv.getTextArea());
		addEditableText(FINITURA_OSSERV, fxFinituraOsserv.getTextArea());
	}
	private void addEditableText(FieldType fieldType, TextInputControl textInput) {
		fieldsMap.put(fieldType, new EditableText(textInput));
	}
	private void addEditableCheckBox(FieldType fieldType, CheckBox checkBox, Node... nodesToDisable) {
		fieldsMap.put(fieldType, new EditableCheckBox(checkBox, nodesToDisable));
	}
	private void addEditableComboUnity(FieldType fieldType, ComboBox<EnumUnity> comboUnity) {
		fieldsMap.put(fieldType, new EditableComboUnity(comboUnity));
	}

	@FXML
	private void actionCreatePDF(ActionEvent event) throws IOException, DocumentException {
//		FileChooser fc = new FileChooser();
//		fc.setTitle("Select output path");
//		fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF", "*.pdf"));
//		File outFile = fc.showSaveDialog(FxUtils.getWindow(event));
		File outFile = new File("C:\\Users\\f.barbano\\Desktop\\ZZZZZ.pdf");//review to delete
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

			//review to remove
			String defval = "fede";
			getNode().setText(defval);
			getProperty().setValue(defval);
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
			// review to delete
			getNode().setSelected(true);
		}

		@Override
		public String toStringField() {
			return getNode().isSelected() ? "X" : "";
		}
	}
	private class EditableComboUnity extends EditableField<ComboBox<EnumUnity>,SimpleObjectProperty<EnumUnity>> {
		EditableComboUnity(ComboBox<EnumUnity> node) {
			super(node, new SimpleObjectProperty<>());
			getProperty().bind(getNode().getSelectionModel().selectedItemProperty());
		}

		@Override
		public String toStringField() {
			return getNode().getSelectionModel().getSelectedItem().getLabel();
		}
	}
}
