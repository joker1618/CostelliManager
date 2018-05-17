package it.costelli.manager.util;

import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Window;
import javafx.util.StringConverter;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.function.Function;

import static it.costelli.manager.util.StrUtils.strf;


/**
 * Created by f.barbano on 18/06/2017.
 */
public class FxUtils {

	/* TableView utilities */
	public static void setTableCellFactoryString(TableColumn<?, String> column, String bindVarName) {
		setTableCellFactory(column, bindVarName, s -> s, s -> s);
	}

	public static void setTableCellFactoryInteger(TableColumn<?, Integer> column, String bindVarName) {
		setTableCellFactory(column, bindVarName, String::valueOf, Integer::valueOf);
	}

	public static void setTableCellFactoryLocalDate(TableColumn<?, LocalDate> column, String bindVarName, DateTimeFormatter formatter) {
		setTableCellFactory(column, bindVarName, (LocalDate ldt) -> ldt.format(formatter), (s) -> (LocalDate)formatter.parse(s));
	}

	public static void setTableCellFactoryLocalDateTime(TableColumn<?, LocalDateTime> column, String bindVarName, DateTimeFormatter formatter) {
		setTableCellFactory(column, bindVarName, (LocalDateTime ldt) -> ldt.format(formatter), (s) -> (LocalDateTime)formatter.parse(s));
	}

	public static <V> void setTableCellFactory(TableColumn<?, V> column, String bindVarName, Function<V, String> toStringFunc, Function<String, V> fromStringFunc) {
		if(StringUtils.isNotBlank(bindVarName)) {
			setTableCellValueBinding(column, bindVarName);
		}

		column.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<V>() {
			@Override
			public String toString(V object) {
				return toStringFunc.apply(object);
			}

			@Override
			public V fromString(String string) {
				return fromStringFunc.apply(string);
			}
		}));
	}

	public static <V> void setTableCellValueBinding(TableColumn<?, V> column, String bindVarName) {
		column.setCellValueFactory(new PropertyValueFactory<>(bindVarName));
	}


	// Alerts
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


	// Miscellanea
	public static Window getWindow(Event e) {
		return ((Node)e.getSource()).getScene().getWindow();
	}
}
