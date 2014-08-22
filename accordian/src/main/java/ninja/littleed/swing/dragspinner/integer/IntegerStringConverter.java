package ninja.littleed.swing.dragspinner.integer;

import java.awt.Color;

import javax.swing.JComponent;

import ninja.littleed.swing.dragspinner.DragSpinnerRenderer;

/**
 * Allows the user to directly enter values into the text field, and formats
 * invalid values as red text.
 * 
 * @author LittleEd
 * 
 */
public class IntegerStringConverter implements DragSpinnerRenderer<Integer> {

	/**
	 * Returns the Integer based on the string provided, or null if it cannot be
	 * converted.
	 */
	public Integer getValueForString(String str) {
		try {
			return Integer.valueOf(str);
		} catch (NumberFormatException nfe) {
			return null;
		}
	}

	/**
	 * Returns the string value of the integer, or an empty string if the value
	 * is null.
	 */
	public String getStringForValue(Integer value) {
		return value == null ? "" : value.toString();
	}

	/**
	 * Formats the component with a red foreground if invalid.
	 */
	public void showValidation(JComponent component, boolean isValid) {
		if (isValid) {
			component.setForeground(Color.black);
		} else {
			component.setForeground(Color.red);
		}
	}

}
