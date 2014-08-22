package ninja.littleed.swing.dragspinner;

import javax.swing.JComponent;

/**
 * The renderer is an optional component for the DragSpinner that allows the
 * user to directly enter new values diractly into the text field without having
 * the drag it. It also decorates the component when a set value is invalid.
 * 
 * @author LittleEd
 * 
 * @param <E>
 */
public interface DragSpinnerRenderer<E> {

	/**
	 * Attempts to convert the string into a valid value. If this fails, then
	 * null may be returned.
	 * 
	 * @param str
	 * @return
	 */
	public E getValueForString(String str);

	/**
	 * Converts the value into a string.
	 * 
	 * @param value
	 * @return
	 */
	public String getStringForValue(E value);

	/**
	 * Decorates the component as it sees fit to show that the value is
	 * currently valid or not. Please ensure that it odoes the formatting when
	 * the value is valid too.
	 * 
	 * @param component
	 * @param isValid
	 */
	public void showValidation(JComponent component, boolean isValid);
}
