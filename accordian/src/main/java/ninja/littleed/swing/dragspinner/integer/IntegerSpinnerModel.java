package ninja.littleed.swing.dragspinner.integer;

import java.util.ArrayList;
import java.util.List;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import ninja.littleed.swing.dragspinner.DragSpinnerModel;

/**
 * A spinner model for integer values within a set range. The initial value is
 * set to 0, but this can be set byu setValue(). The min/max values can also be
 * set.
 * 
 * @author LittleEd
 * 
 */
public class IntegerSpinnerModel implements DragSpinnerModel<Integer> {

	private Integer value = 0;
	private Integer min = Integer.MIN_VALUE;
	private Integer max = Integer.MAX_VALUE;
	private List<ChangeListener> changeListeners;
	

	/**
	 * The minimum allowed value.
	 * 
	 * @return
	 */
	public Integer getMin() {
		return min;
	}

	/**
	 * The minimum allowed value.
	 * 
	 * @param min
	 */
	public void setMin(Integer min) {
		this.min = min;
	}

	/**
	 * The maximum allowed value.
	 * 
	 * @return
	 */
	public Integer getMax() {
		return max;
	}

	/**
	 * The maximum allowed value.
	 * 
	 * @param max
	 */
	public void setMax(Integer max) {
		this.max = max;
	}

	/**
	 * The current value.
	 */
	public Integer getValue() {
		return value;
	}

	/**
	 * The current value. Setting this will update the spinner.
	 */
	public void setValue(Integer value) {
		this.value = value;
		fireChangeListeners(new ChangeEvent(this));
	}

	/**
	 * Returns the next value based on the original value and the number of
	 * steps to increase/decrease (direction derived from the sign (+/-)).
	 */
	public Integer getNextValue(Integer originalValue, int steps) {
		if (originalValue == null) {
			return null;
		}
		return getValidValue(originalValue + steps);
	}

	/**
	 * Returns true if the value is valid, as determined by the allowable range.
	 */
	public boolean isValueValid() {
		if(value==null){
			return false;
		}else{
			return value.equals(getValidValue(value));
		}
	}

	/**
	 * Returns the closest valid value to the passed value.
	 * 
	 * @param value
	 * @return
	 */
	private Integer getValidValue(Integer value) {
		return Math.min(max, Math.max(min, value));
	}

	public void addChangeListener(ChangeListener l) {
		if (changeListeners == null) {
			changeListeners = new ArrayList<ChangeListener>();
		}
		changeListeners.add(l);
	}

	public void removeChangeListener(ChangeListener l) {
		if (changeListeners != null) {
			changeListeners.remove(l);
			if (changeListeners.size() < 1) {
				changeListeners = null;
			}
		}
	}

	/**
	 * Fired when the value is set.
	 * 
	 * @param event
	 */
	private void fireChangeListeners(ChangeEvent event) {
		if (changeListeners != null) {
			for (ChangeListener listener : changeListeners) {
				listener.stateChanged(event);
			}
		}
	}

}
