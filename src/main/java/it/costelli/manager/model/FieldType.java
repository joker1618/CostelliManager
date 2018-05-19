package it.costelli.manager.model;

import java.util.Arrays;
import java.util.Optional;

/**
 * Created by f.barbano on 13/05/2018.
 */
public enum FieldType {

	// Row 0
	FOGLIO_COLLAUDO_NUM(1),
	CLIENTE(2),
	IMPIANTO_TIPO(3),
	SCHEMA_NUM(4),
	COMMESSA(5),
	MATRICOLA(6),
	COD(7),

	// Row 2
	VERIFICA_CONFORMITA_ESITO(8),
	VERIFICA_CONFORMITA_OSSERV(9),

	// Row 3
	CONTROLLO_DIMENSIONALE_TEXT(154),
	CONTROLLO_DIMENSIONALE_ESITO(10),
	CONTROLLO_DIMENSIONALE_OSSERV(11),

	// Row 4
	GUARNIZIONI_STANDARD(42),
	GUARNIZIONI_VITON(90),
	GUARNIZIONI_CUSTOM(98),
	GUARNIZIONI_CUSTOM_TEXT(43),
	GUARNIZIONI_ESITO(12),
	GUARNIZIONI_OSSERV(13),

	// Row 5
	POMPE_VARIABILE(44),
	POMPE_VARIABILE_TEXT(45),
	POMPE_FISSA(46),
	POMPE_FISSA_TEXT(47),
	POMPE_INGRANAGGI(50),
	POMPE_INGRANAGGI_TEXT(51),
	POMPE_PALETTE(52),
	POMPE_PALETTE_TEXT(53),
	POMPE_PISTONI(54),
	POMPE_PISTONI_TEXT(55),
	POMPE_CUSTOM(56),
	POMPE_CUSTOM_TYPE(57),
	POMPE_CUSTOM_TYPE_TEXT(58),
	POMPE_PORTATA_MIN(48),
	POMPE_PORTATA_MIN_TEXT(49),
	POMPE_ESITO(14),
	POMPE_OSSERV(15),

	// Row 6
	REGOLATRICI_TARATURA_TEXT(59),
	REGOLATRICI_TARATURA_UNITY(60),
	REGOLATRICI_RIPETIBILITA_TEXT(61),
	REGOLATRICI_RIPETIBILITA_UNITY(62),
	REGOLATRICI_ESITO(16),
	REGOLATRICI_OSSERV(17),

	// Row 7
	PROVA_FUNZIONAMENTO_PRESSIONE_TEXT(63),
	PROVA_FUNZIONAMENTO_PRESSIONE_UNITY(64),
	PROVA_FUNZIONAMENTO_DURATA_TEXT(65),
	PROVA_FUNZIONAMENTO_DURATA_UNITY(66),
	PROVA_FUNZIONAMENTO_ESITO(18),
	PROVA_FUNZIONAMENTO_OSSERV(19),

	// Row 8
	PROVA_SOVRAPRESSIONE_PRESSIONE_TEXT(67),
	PROVA_SOVRAPRESSIONE_PRESSIONE_UNITY(68),
	PROVA_SOVRAPRESSIONE_DURATA_TEXT(69),
	PROVA_SOVRAPRESSIONE_DURATA_UNITY(70),
	PROVA_SOVRAPRESSIONE_ESITO(20),
	PROVA_SOVRAPRESSIONE_OSSERV(21),

	// Row 9
	VALVOLE_SEQUENZE(71),
	VALVOLE_STROZZATORI(72),
	VALVOLE_TENUTA_NON_RITORNO(73),
	VALVOLE_RIDUZ_PRESSIONE(74),
	VALVOLE_REGOL_PORTATA_COMPENS(75),
	VALVOLE_TENUTA_NON_RITORNO_PIL(76),
	VALVOLE_ESITO(22),
	VALVOLE_OSSERV(23),

	// Row 10
	ACCESSORI_ACCUMULATORE(77),
	ACCESSORI_P0_BAR(78),
	ACCESSORI_NORME(79),
	ACCESSORI_FILTRI_ASP(80),
	ACCESSORI_FILTRI_ASP_TEXT(86),
	ACCESSORI_FILTRI_MAN(82),
	ACCESSORI_FILTRI_MAN_TEXT(87),
	ACCESSORI_FILTRI_RIT(84),
	ACCESSORI_FILTRI_RIT_TEXT(88),
	ACCESSORI_SCAMBIO_CALORE_ARIA(81),
	ACCESSORI_SCAMBIO_CALORE_ACQUA(83),
	ACCESSORI_SCAMBIO_CALORE_CUSTOM(85),
	ACCESSORI_SCAMBIO_CALORE_CUSTOM_TEXT(89),
	ACCESSORI_ESITO(160),
	ACCESSORI_OSSERV(161),

	// Row 11
	STRUMENTI_MANOMETRO(91),
	STRUMENTI_PRESS_TRASD(93),
	STRUMENTI_LIVELLOSTATO(95),
	STRUMENTI_TERMOMETRO(92),
	STRUMENTI_TERMOSTATO(94),
	STRUMENTI_CUSTUM(96),
	STRUMENTI_CUSTOM_TEXT(97),
	STRUMENTI_ESITO(26),
	STRUMENTI_OSSERV(27),

	// Row 12
	TENUTE_TAPPI(99),
	TENUTE_OBLO(101),
	TENUTE_SERBATOIO(103),
	TENUTE_SERBATOIO_TEXT(105),
	TENUTE_BLOCCHI(100),
	TENUTE_VALVOLE(102),
	TENUTE_RACCORDI(104),
	TENUTE_RACCORDI_TEXT(106),
	TENUTE_ESITO(28),
	TENUTE_OSSERV(29),

	// Row 13
	FLUIDO_OLIO_MINERALE(107),
	FLUIDO_CUSTOM(108),
	FLUIDO_CUSTOM_TEXT(109),
	FLUIDO_TEMP_AMBIENTE_COLLAUDO_TEXT(111),
	FLUIDO_TEMP_OLIO_TEXT(112),
	FLUIDO_ESITO(30),
	FLUIDO_OSSERV(31),

	// Row 14
	FINITURA_STANDARD(113),
	FINITURA_RAL(114),
	FINITURA_RAL_TEXT(116),
	FINITURA_CUSTOM(115),
	FINITURA_CUSTOM_TEXT(117),
	FINITURA_ESITO(32),
	FINITURA_OSSERV(33),

	// Row 15
	PULIZIA_SERBATOIO_ESITO(34),
	PULIZIA_SERBATOIO_OSSERV(35),

	// Row 16
	BOBINE_V_TEXT(119),
	BOBINE_HZ_TEXT(120),
	BOBINE_VDC_TEXT(121),
	BOBINE_ESITO(36),
	BOBINE_OSSERV(37),

	// Row 17
	MOTORI_CV_TEXT(125),
	MOTORI_220_380V(122),
	MOTORI_KW(126),
	MOTORI_CUSTOM(123),
	MOTORI_CUSTOM_TEXT(124),
	MOTORI_2POLI(127),
	MOTORI_4POLI(128),
	MOTORI_6POLI(129),
	MOTORI_ESITO(38),
	MOTORI_OSSERV(39),

	// Row 18
	CERTIFICATI_MOTORI_ELETTRICI(130),
	CERTIFICATI_MOTORI_ELETTRICI_TEXT(134),
	CERTIFICATI_POMPE(131),
	CERTIFICATI_POMPE_TEXT(135),
	CERTIFICATI_ACCUMULATORI(132),
	CERTIFICATI_ACCUMULATORI_TEXT(136),
	CERTIFICATI_CUSTOM(133),
	CERTIFICATI_CUSTOM_TYPE(137),
	CERTIFICATI_CUSTOM_TYPE_TEXT(138),
	CERTIFICATI_ESITO(40),
	CERTIFICATI_OSSERV(41),

	// Row 19
	RUMORE_FONOMETRO_MOD(139),
	RUMORE_PMIN_A(140),
	RUMORE_PMIN_B(142),
	RUMORE_PMIN_C(144),
	RUMORE_PMIN_D(146),
	RUMORE_PMAX_A(141),
	RUMORE_PMAX_B(143),
	RUMORE_PMAX_C(145),
	RUMORE_PMAX_D(147),
	RUMORE_FONDO(148),
	RUMORE_NOTE(155),

	// Row 21
	LAST_MONTATO_DA(149),
	LAST_COLLAUDATORE(150),
	LAST_RESPONSABILE(152),
	LAST_DATA(153)
	
	;

	private int grossoNum;

	FieldType(int grossoNum) {
		this.grossoNum = grossoNum;
	}

	public int grossoNum() {
		return grossoNum;
	}

	public static FieldType getByGrossoNum(int num) {
		Optional<FieldType> opt = Arrays.stream(values()).filter(e -> e.grossoNum == num).findAny();
		return opt.orElse(null);
	}
}

