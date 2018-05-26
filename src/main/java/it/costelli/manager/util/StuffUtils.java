package it.costelli.manager.util;

import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Window;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Optional;

import static xxx.joker.libs.javalibs.utils.JkStrings.strf;

/**
 * Created by f.barbano on 13/05/2018.
 */
public class StuffUtils {

	// JavaFX
	public static void showAlertInfo(String header, String format, Object... params) {
		showAlert(Alert.AlertType.INFORMATION, header, format, params);
	}
	public static boolean showAlertConfirmation(String header, String format, Object... params) {
		Optional<ButtonType> opt = showAlert(Alert.AlertType.CONFIRMATION, header, format, params);
		return opt.isPresent() && opt.get() == ButtonType.OK;
	}
	public static void showAlertError(String header, String format, Object... params) {
		showAlert(Alert.AlertType.ERROR, header, format, params);
	}
	public static Optional<ButtonType> showAlert(Alert.AlertType alertType, String header, String format, Object... params) {
		Alert alert = new Alert(alertType);
		alert.setTitle(null);
		alert.setHeaderText(header);
		alert.setContentText(format == null ? null : strf(format, params));
		return alert.showAndWait();
	}

	public static Window getWindow(Event e) {
		return ((Node)e.getSource()).getScene().getWindow();
	}


	// Mics
	public static String viewDouble(double num) {
		return getNumberFormat().format(num);
	}
	private static NumberFormat getNumberFormat() {
		NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
		nf.setMinimumFractionDigits(2);
		nf.setMaximumFractionDigits(2);
		return nf;
	}
}
