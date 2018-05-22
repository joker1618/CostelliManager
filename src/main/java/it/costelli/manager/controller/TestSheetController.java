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
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static it.costelli.manager.model.FieldType.*;

/**
 * Created by f.barbano on 18/05/2018.
 */
public class TestSheetController implements Initializable {

	private static final SimpleLog logger = LogService.getLogger(TestSheetController.class);

	@FXML private ScrollPane scrollPaneContainer;
	@FXML private VBox sheetBox;

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
	@FXML private CheckBoxCustom fxFinituraStandard;
	@FXML private CheckBoxCustom fxFinituraRal;
	@FXML private CheckBoxCustom fxFinituraCustom;
	@FXML private BoxEsito fxFinituraEsito;
	@FXML private BoxOsservazioni fxFinituraOsserv;
	// Row 15
	@FXML private BoxEsito fxPuliziaSerbatoioEsito;
	@FXML private BoxOsservazioni fxPuliziaSerbatoioOsserv;
	// Row 16
	@FXML private LabelTextField fxBobineV;
	@FXML private LabelTextField fxBobineHz;
	@FXML private LabelTextField fxBobineVdc;
	@FXML private BoxEsito fxBobineEsito;
	@FXML private BoxOsservazioni fxBobineOsserv;
	// Row 17
	@FXML private CheckBoxCustom fxMotoriHz;
	@FXML private CheckBoxCustom fxMotoriCustom;
	@FXML private LabelTextField fxMotoriKwText;
	@FXML private LabelTextField fxMotoriCvText;
	@FXML private CheckBoxCustom fxMotori2Poli;
	@FXML private CheckBoxCustom fxMotori4Poli;
	@FXML private CheckBoxCustom fxMotori6Poli;
	@FXML private BoxEsito fxMotoriEsito;
	@FXML private BoxOsservazioni fxMotoriOsserv;
	// Row 18
	@FXML private CheckBoxCustom fxCertificatiMotoriElettrici;
	@FXML private CheckBoxCustom fxCertificatiPompe;
	@FXML private CheckBoxCustom fxCertificatiAccumulatori;
	@FXML private CheckBoxCustom fxCertificatiCustom;
	@FXML private LabelTextField fxCertificatiCustomTypeText;
	@FXML private BoxEsito fxCertificatiEsito;
	@FXML private BoxOsservazioni fxCertificatiOsserv;
	// Row 19
	@FXML private LabelTextField fxRumoreFonometroMod;
	@FXML private LabelTextField fxRumoreMinA;
	@FXML private LabelTextField fxRumoreMinB;
	@FXML private LabelTextField fxRumoreMinC;
	@FXML private LabelTextField fxRumoreMinD;
	@FXML private LabelTextField fxRumoreMaxA;
	@FXML private LabelTextField fxRumoreMaxB;
	@FXML private LabelTextField fxRumoreMaxC;
	@FXML private LabelTextField fxRumoreMaxD;
	@FXML private LabelTextField fxRumoreFondo;
	@FXML private BoxOsservazioni fxRumoreNote;
	// Row 21
	@FXML private LabelTextField fxLastMontatoDa;
	@FXML private LabelTextField fxLastCollaudatore;
	@FXML private LabelTextField fxLastResponsabile;
	@FXML private DatePickerCustom fxLastData;


	private final Map<FieldType,EditableField> fieldsMap = new HashMap<>();


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		int sheetWidth = 950;

		manageSpecificBindings();

		TestSheetResizer.resizeBillView(sheetBox, sheetWidth);

		scrollPaneContainer.setPadding(new Insets(2.0));
		scrollPaneContainer.setMaxWidth(sheetWidth + 20.0);

		manageFieldBindings();
	}

	private void manageSpecificBindings() {
		// POMPE - row 5
		Pane row5 = (Pane) fxPompeCustom.getParent();
		fxPompeCustom.prefWidthProperty().bind(Bindings.createDoubleBinding(() -> row5.getWidth() * 0.4, row5.widthProperty()));
		fxPompeCustomTypeText.disableProperty().bind(Bindings.createBooleanBinding(
			() -> !fxPompeCustom.isSelected() || StringUtils.isBlank(fxPompeCustom.getText()),
			fxPompeCustom.getCheckBox().selectedProperty(),
			fxPompeCustom.getTextField().textProperty()
		));

		// TENUTE - row 12
		fxTenuteTappi.prefHeightProperty().bind(fxTenuteSerbatoio.heightProperty());
		fxTenuteBlocchi.prefHeightProperty().bind(fxTenuteRaccordi.heightProperty());
		fxTenuteOblo.prefHeightProperty().bind(fxTenuteSerbatoio.heightProperty());
		fxTenuteValvole.prefHeightProperty().bind(fxTenuteRaccordi.heightProperty());

		// BOBINE 16
		DoubleBinding halfSizeBind = Bindings.createDoubleBinding(() -> fxBobineVdc.getWidth() / 2.0, fxBobineVdc.widthProperty());
		fxBobineV.prefWidthProperty().bind(halfSizeBind);
		fxBobineHz.prefWidthProperty().bind(halfSizeBind);

		// CERTIFICATI 18
		fxCertificatiCustomTypeText.disableProperty().bind(Bindings.createBooleanBinding(
			() -> !fxCertificatiCustom.isSelected() || StringUtils.isBlank(fxCertificatiCustom.getText()),
			fxCertificatiCustom.getCheckBox().selectedProperty(),
			fxCertificatiCustom.getTextField().textProperty()
		));
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
		addEditableCheckBox(POMPE_CUSTOM, fxPompeCustom.getCheckBox());
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
		addEditableCheckBox(TENUTE_SERBATOIO, fxTenuteSerbatoio.getCheckBox());
//		addEditableText(TENUTE_SERBATOIO_TEXT, fxTenuteSerbatoio.getTextField());
		addEditableCheckBox(TENUTE_RACCORDI, fxTenuteRaccordi.getCheckBox());
//		addEditableText(TENUTE_RACCORDI_TEXT, fxTenuteRaccordi.getTextField());
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

		// Row 15
		addEditableCheckBox(PULIZIA_SERBATOIO_ESITO, fxPuliziaSerbatoioEsito.getCheckBox(), fxPuliziaSerbatoioOsserv.getTextArea());
		addEditableText(PULIZIA_SERBATOIO_OSSERV, fxPuliziaSerbatoioOsserv.getTextArea());

		// Row 16
		addEditableText(BOBINE_V_TEXT, fxBobineV.getTextField());
		addEditableText(BOBINE_HZ_TEXT, fxBobineHz.getTextField());
		addEditableText(BOBINE_VDC_TEXT, fxBobineVdc.getTextField());
		addEditableCheckBox(BOBINE_ESITO, fxBobineEsito.getCheckBox(), fxBobineOsserv.getTextArea());
		addEditableText(BOBINE_OSSERV, fxBobineOsserv.getTextArea());

		// Row 17
		addEditableCheckBox(MOTORI_HZ, fxMotoriHz.getCheckBox());
		addEditableCheckBox(MOTORI_CUSTOM, fxMotoriCustom.getCheckBox());
		addEditableText(MOTORI_CUSTOM_TEXT, fxMotoriCustom.getTextField());
		addEditableText(MOTORI_KW_TEXT, fxMotoriKwText.getTextField());
		addEditableText(MOTORI_CV_TEXT, fxMotoriCvText.getTextField());
		addEditableCheckBox(MOTORI_2POLI, fxMotori2Poli.getCheckBox());
		addEditableCheckBox(MOTORI_4POLI, fxMotori4Poli.getCheckBox());
		addEditableCheckBox(MOTORI_6POLI, fxMotori6Poli.getCheckBox());
		addEditableCheckBox(MOTORI_ESITO, fxMotoriEsito.getCheckBox(), fxMotoriOsserv.getTextArea());
		addEditableText(MOTORI_OSSERV, fxMotoriOsserv.getTextArea());

		// Row 18
		addEditableCheckBox(CERTIFICATI_MOTORI_ELETTRICI, fxCertificatiMotoriElettrici.getCheckBox(), fxCertificatiMotoriElettrici.getTextField());
		addEditableText(CERTIFICATI_MOTORI_ELETTRICI_TEXT, fxCertificatiMotoriElettrici.getTextField());
		addEditableCheckBox(CERTIFICATI_POMPE, fxCertificatiPompe.getCheckBox(), fxCertificatiPompe.getTextField());
		addEditableText(CERTIFICATI_POMPE_TEXT, fxCertificatiPompe.getTextField());
		addEditableCheckBox(CERTIFICATI_ACCUMULATORI, fxCertificatiAccumulatori.getCheckBox(), fxCertificatiAccumulatori.getTextField());
		addEditableText(CERTIFICATI_ACCUMULATORI_TEXT, fxCertificatiAccumulatori.getTextField());
		addEditableCheckBox(CERTIFICATI_CUSTOM, fxCertificatiCustom.getCheckBox(), fxCertificatiCustom.getTextField());
		addEditableText(CERTIFICATI_CUSTOM_TYPE, fxCertificatiCustom.getTextField());
		addEditableText(CERTIFICATI_CUSTOM_TYPE_TEXT, fxCertificatiCustomTypeText.getTextField());  // disabling managed in specific bindings
		addEditableCheckBox(CERTIFICATI_ESITO, fxCertificatiEsito.getCheckBox(), fxCertificatiOsserv.getTextArea());
		addEditableText(CERTIFICATI_OSSERV, fxCertificatiOsserv.getTextArea());

		// Row 19
		addEditableText(RUMORE_FONOMETRO_MOD, fxRumoreFonometroMod.getTextField());
		addEditableText(RUMORE_MIN_A, fxRumoreMinA.getTextField());
		addEditableText(RUMORE_MIN_B, fxRumoreMinB.getTextField());
		addEditableText(RUMORE_MIN_C, fxRumoreMinC.getTextField());
		addEditableText(RUMORE_MIN_D, fxRumoreMinD.getTextField());
		addEditableText(RUMORE_MAX_A, fxRumoreMaxA.getTextField());
		addEditableText(RUMORE_MAX_B, fxRumoreMaxB.getTextField());
		addEditableText(RUMORE_MAX_C, fxRumoreMaxC.getTextField());
		addEditableText(RUMORE_MAX_D, fxRumoreMaxD.getTextField());
		addEditableText(RUMORE_FONDO, fxRumoreFondo.getTextField());
		addEditableText(RUMORE_NOTE, fxRumoreNote.getTextArea());

		// Row 21
		addEditableText(LAST_MONTATO_DA, fxLastMontatoDa.getTextField());
		addEditableText(LAST_COLLAUDATORE, fxLastCollaudatore.getTextField());
		addEditableText(LAST_RESPONSABILE, fxLastResponsabile.getTextField());
		addEditableDatePicker(LAST_DATA, fxLastData);
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
	private void addEditableDatePicker(FieldType fieldType, DatePicker datePicker) {
		fieldsMap.put(fieldType, new EditableDatePicker(datePicker));
	}

	@FXML
	private void actionCreatePDF(ActionEvent event) throws IOException, DocumentException {
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

		// Create dialog
		Dialog<Pair<PDFFont,Integer>> dlg = new Dialog<>();
		dlg.getDialogPane().setContent(gridPane);
		dlg.getDialogPane().getButtonTypes().add(new ButtonType("CREATE PDF", ButtonBar.ButtonData.OK_DONE));
		dlg.setResultConverter(param -> param != null && param.getButtonData() == ButtonBar.ButtonData.OK_DONE ? Pair.of(comboFont.getSelectionModel().getSelectedItem(), comboSize.getSelectionModel().getSelectedItem()) : null);
		Optional<Pair<PDFFont, Integer>> resp = dlg.showAndWait();

		if (resp.isPresent()) {
			Map<FieldType, String> pdfData = new HashMap<>();
			fieldsMap.forEach((ftype, efield) -> pdfData.put(ftype, efield.toStringField()));
			PdfFacade.fillPDFFields(outFile.toPath(), resp.get().getKey(), resp.get().getValue(), pdfData);
			FxUtils.showAlertInfo("Nuovo foglio di collaudo creato", "PDF path: %s", outFile);
		}
	}

	public abstract class EditableField<N extends Node, P extends Property> {
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
			return getProperty().getValue() ? "X" : "";
		}
	}
	private class EditableComboUnity extends EditableField<ComboBox<EnumUnity>,SimpleObjectProperty<EnumUnity>> {
		EditableComboUnity(ComboBox<EnumUnity> node) {
			super(node, new SimpleObjectProperty<>());
			getProperty().bind(getNode().getSelectionModel().selectedItemProperty());
		}

		@Override
		public String toStringField() {
			return getProperty().getValue().getLabel();
		}
	}
	private class EditableDatePicker extends EditableField<DatePicker,SimpleObjectProperty<LocalDate>> {
		EditableDatePicker(DatePicker node) {
			super(node, new SimpleObjectProperty<>());
			getNode().valueProperty().addListener((obs,old,nez) -> getProperty().setValue(nez));
			getProperty().setValue(getNode().getValue());
		}

		@Override
		public String toStringField() {
			return getProperty().getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		}
	}
}
