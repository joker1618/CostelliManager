package it.costelli.manager.view.components;

import javafx.scene.control.DatePicker;

import java.time.LocalDate;

/**
 * Created by f.barbano on 19/05/2018.
 */
public class DatePickerCustom extends DatePicker {

	public DatePickerCustom() {
		super();
		setEditable(false);
		setShowWeekNumbers(false);
		setValue(LocalDate.now());
	}

}
