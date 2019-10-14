package it.costelli.manager.config;

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

}
