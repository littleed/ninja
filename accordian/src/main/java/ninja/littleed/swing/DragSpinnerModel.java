package ninja.littleed.swing;

import javax.swing.SpinnerModel;

public interface DragSpinnerModel extends SpinnerModel{

	public boolean isStringValid(String str);
	
	/**
	 * The number of columns to set the text field preferred size
	 * @return
	 */
	public Integer getPreferredNumberOfColumns();
}
