package it.costelli.manager.config;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by f.barbano on 17/05/2018.
 */
public class Conf {

	public static final Path BASE_FOLDER = Paths.get("C:\\Users\\f.barbano\\IdeaProjects\\app\\CostelliManager\\src\\main\\resources");

	public static final Path RESOURCE_FIELDS_POS = BASE_FOLDER.resolve("pdf\\fieldsPositions.csv");
	public static final Path RESOURCE_TEMPLATE_TEST_SHEET = BASE_FOLDER.resolve("pdf\\testSheetTemplate.pdf");

}
