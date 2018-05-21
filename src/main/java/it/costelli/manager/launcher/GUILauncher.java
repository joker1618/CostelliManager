package it.costelli.manager.launcher;

import it.costelli.manager.logger.LogService;
import it.costelli.manager.logger.SimpleLog;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
//import org.scenicview.ScenicView;

import java.io.IOException;
import java.util.logging.Level;

/**
 * Created by f.barbano on 17/05/2018.
 */
public class GUILauncher extends Application {

	private static final SimpleLog logger = LogService.getLogger(GUILauncher.class);

	private static boolean scenicView; // review remove

	private BorderPane mainPane;
	private Pane testSheetPane, editorPosPane;

	public static void main(String[] args) throws IOException {
		scenicView = args.length > 0 && "-scenicView".equals(args[0]);
		LogService.init("it.costelli", Level.ALL);
		launch(args);
	}

	@Override
	public void start(Stage stage) throws IOException {
		FXMLLoader sheetLoader = new FXMLLoader(getClass().getResource("/fxml/testSheetView.fxml"));
		testSheetPane = sheetLoader.load();

		FXMLLoader editorLoader = new FXMLLoader(getClass().getResource("/fxml/fieldPosEditorView.fxml"));
		editorPosPane = editorLoader.load();

		mainPane = new BorderPane(testSheetPane, createMenuBar(), null, null, null);

		Scene scene = new Scene(mainPane);
		stage.setScene(scene);
		stage.show();

		stage.setMaximized(true);

//		if(scenicView) {
//			ScenicView.show(scene);
//		}

	}

	@Override
	public void stop() throws Exception {
		logger.info("end");
	}

	private MenuBar createMenuBar() {
		MenuItem sheetItem = new MenuItem("Foglio di collaudo");
		sheetItem.setOnAction(e -> mainPane.setCenter(testSheetPane));

		MenuItem editorItem = new MenuItem("Editor posizione campi PDF");
		editorItem.setOnAction(e -> mainPane.setCenter(editorPosPane));

		Menu menu = new Menu("Menu");
		menu.getItems().addAll(sheetItem, editorItem);

		return new MenuBar(menu);
	}
}
