package ninja.littleed.swing;

import java.util.ArrayList;
import java.util.List;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class DefaultSpinnerModel implements DragSpinnerModel {

	private Integer current = 0;
	private Integer step = 1;
	private Integer min = 0;
	private Integer max = Integer.MAX_VALUE;
	private List<ChangeListener> changeListeners;

	public Integer getPreferredNumberOfColumns() {
		return Math.max(String.valueOf(min).length(), String.valueOf(max)
				.length());
	}

	public Object getValue() {
		return current;
	}

	public boolean isStringValid(String str) {
		if (str == null) {
			return false;
		} else {
			Integer num = Integer.valueOf(str);
			return min <= num && num <= max;
		}
	}

	public void setValue(Object value) {
		current = (Integer) value;
		fireChangeListeners(new ChangeEvent(this));
	}

	public Object getNextValue() {
		return Math.min(current + step, max);
	}

	public Object getPreviousValue() {
		return Math.max(current - step, min);
	}

	public void setStep(Integer step) {
		this.step = step;
	}

	public void setMin(Integer min) {
		this.min = min;
	}

	public void setMax(Integer max) {
		this.max = max;
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

	private void fireChangeListeners(ChangeEvent event) {
		if (changeListeners != null) {
			for (ChangeListener listener : changeListeners) {
				listener.stateChanged(event);
			}
		}
	}

}
