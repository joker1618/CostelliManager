package it.costelli.manager.config;

import javafx.stage.FileChooser;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by f.barbano on 17/05/2018.
 */
public class Conf {

	// review 
	public static final Path BASE_FOLDER = Paths.get("resources");

	public static final Path FIELDS_POSITIONS = BASE_FOLDER.resolve("pdf/fieldsPositions.csv");
	public static final Path TEMPLATE_CERTIFICATE_ITA = BASE_FOLDER.resolve("pdf/templateCertificate_ita.pdf");
	public static final Path TEMPLATE_CERTIFICATE_ENG = BASE_FOLDER.resolve("pdf/templateCertificate_eng.pdf");

	public static final String CSV_FIELD_SEP = "|";
	public static final FileChooser.ExtensionFilter SAVE_FILE_EXT_FILTER = new FileChooser.ExtensionFilter ("CST", "*.cst");

	public static final String PATH_FLAG_ITA = "/images/flagITA.png";
	public static final String PATH_FLAG_UK = "/images/flagUK.png";

}
