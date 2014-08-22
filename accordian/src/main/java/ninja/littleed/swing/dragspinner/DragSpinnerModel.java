package ninja.littleed.swing.dragspinner;

import javax.swing.event.ChangeListener;

/**
 * All DragSpinners need to be set a valid model that will define how to get/set
 * values and find the next/previous value in the sequence. It must also fire
 * change listeners when a new value is set on the model.
 * 
 * @author LittleEd
 * 
 * @param <E>
 */
public interface DragSpinnerModel<E> {

	/**
	 * Returns the current value for the model.
	 * 
	 * @return
	 */
	public E getValue();

	/**
	 * Returns true if the current value is valid.
	 * 
	 * @return
	 */
	public boolean isValueValid();

	/**
	 * Returns the next value that is the provided number of steps. The number
	 * of steps may be either positive or negative and represents the direction
	 * of the action.
	 * 
	 * @param originalValue
	 * @param steps
	 * @return
	 */
	public E getNextValue(E originalValue, int steps);

	/**
	 * Sets the current value.
	 * 
	 * @param value
	 */
	public void setValue(E value);

	/**
	 * Adds a change listener that will be fired when a new value is set on the
	 * model.
	 * 
	 * @param changeListener
	 */
	public void addChangeListener(ChangeListener changeListener);

	/**
	 * Removes a change listener that would be fired if a new value was set on
	 * the model, had it not been removed.
	 * 
	 * @param changeListener
	 */
	public void removeChangeListener(ChangeListener changeListener);
}
