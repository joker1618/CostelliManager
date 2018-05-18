package it.costelli.manager.launcher;

import it.costelli.manager.logger.LogService;
import it.costelli.manager.logger.SimpleLog;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.scenicview.ScenicView;

import java.io.IOException;
import java.util.logging.Level;

/**
 * Created by f.barbano on 17/05/2018.
 */
public class TestSheetGUI extends Application {

	private static final SimpleLog logger = LogService.getLogger(TestSheetGUI.class);

	private static boolean scenicView; // review remove

	public static void main(String[] args) throws IOException {
		scenicView = args.length > 0 && "-scenicView".equals(args[0]);
		LogService.init("it.costelli", Level.ALL);
		launch(args);
	}

	@Override
	public void start(Stage stage) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/testSheetView.fxml"));
		ScrollPane scrollPane = new ScrollPane(fxmlLoader.load());

		Scene scene = new Scene(scrollPane);
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

}
