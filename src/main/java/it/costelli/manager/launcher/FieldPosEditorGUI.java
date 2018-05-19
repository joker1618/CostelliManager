package it.costelli.manager.launcher;

import it.costelli.manager.logger.LogService;
import it.costelli.manager.logger.SimpleLog;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.scenicview.ScenicView;

import java.io.IOException;
import java.util.logging.Level;

/**
 * Created by f.barbano on 17/05/2018.
 */
public class FieldPosEditorGUI extends Application {

	private static final SimpleLog logger = LogService.getLogger(FieldPosEditorGUI.class);

	private static boolean scenicView; // review remove

	public static void main(String[] args) throws IOException {
		scenicView = args.length > 0 && "-scenicView".equals(args[0]);
		LogService.init("it.costelli", Level.ALL);
		launch(args);
	}

	@Override
	public void start(Stage stage) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/fieldPosEditorView.fxml"));
		VBox editorPosPane = fxmlLoader.load();

		ScrollPane imagePane = createImagePane();

		BorderPane borderPane = new BorderPane(imagePane, null, editorPosPane, null, null);

		Scene scene = new Scene(borderPane);
//		Scene scene = new Scene(editorPosPane);
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

	private ScrollPane createImagePane() {
		Image image = new Image(getClass().getResourceAsStream("/images/TestSheetTemplate.png"));
		ImageView imageView = new ImageView(image);

		double rel = image.getHeight() / image.getWidth();
		double fitWidth = 1000.0;
		imageView.setFitWidth(fitWidth);
		imageView.setFitHeight(fitWidth * rel);

		ScrollPane scrollPane = new ScrollPane(imageView);
		scrollPane.setPrefWidth(fitWidth + 16.0);

		return scrollPane;
	}

}
