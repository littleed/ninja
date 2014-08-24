package ninja.littleed.swing.accordian;

import info.clearthought.layout.TableLayout;

import java.awt.Component;

import javax.swing.JComponent;

/**
 * Accordion component in Swing Orientation!<br>
 * <h3>Enahncements</h3>
 * <ul>
 * <li>TODO Allow single exapanded bellow mode.
 * <li>TODO Allow vertically expanding mode.
 * </ul>
 * 
 */
@SuppressWarnings("serial")
public class JAccordian extends JComponent {

	private TableLayout tableLayout;

	/**
	 * Creates a default JAccordian.
	 */
	public JAccordian() {
		tableLayout = new TableLayout(
				new double[][] { {}, { TableLayout.FILL } });
		setLayout(tableLayout);
	}

	/**
	 * Adds a new component to the JAccordian encased in a bellow.
	 * 
	 * @param title
	 * @param component
	 */
	public void addComponent(JComponent title, JComponent component) {
		tableLayout.insertColumn(tableLayout.getNumColumn(),
				TableLayout.PREFERRED);
		add(new JAccordianBellow(title, component),
				(tableLayout.getNumColumn() - 1) + ",0");
		tableLayout.layoutContainer(this);
	}

	/**
	 * Expand all the bellows
	 */
	public void expandAll() {
		for (Component c : getComponents()) {
			if (c instanceof JAccordianBellow) {
				((JAccordianBellow) c).expand();
			}
		}
	}

	/**
	 * True if all bellows are expanded.
	 * 
	 * @return
	 */
	public boolean isAllExpanded() {
		for (Component c : getComponents()) {
			if (c instanceof JAccordianBellow) {
				if (((JAccordianBellow) c).isCollapsed()) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Collapses all the bellows.
	 */
	public void collapseAll() {
		for (Component c : getComponents()) {
			if (c instanceof JAccordianBellow) {
				((JAccordianBellow) c).collapse();
			}
		}
	}

	/**
	 * True if all the bellows are collapsed.
	 * 
	 * @return
	 */
	public boolean isAllCollapsed() {
		for (Component c : getComponents()) {
			if (c instanceof JAccordianBellow) {
				if (!((JAccordianBellow) c).isCollapsed()) {
					return false;
				}
			}
		}
		return true;
	}
}
