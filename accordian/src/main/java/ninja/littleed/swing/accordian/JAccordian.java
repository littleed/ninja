package ninja.littleed.swing.accordian;

import info.clearthought.layout.TableLayout;

import java.awt.Color;
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
	private Color spineColour = new Color(51,51,255);

	/**
	 * Creates a default JAccordian.
	 */
	public JAccordian() {
		tableLayout = new TableLayout(
				new double[][] { {}, { TableLayout.FILL } });
		setLayout(tableLayout);
	}

	public Color getSpineColour() {
		return spineColour;
	}

	public void setSpineColour(Color spineColour) {
		this.spineColour = spineColour;
	}
	
	public void setSpacing(int spacing){
		tableLayout.setHGap(spacing);
		tableLayout.setVGap(spacing);
	}
	
	public int getSpacing(){
		return tableLayout.getHGap();
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
		add(new JAccordianBellow(title, component, spineColour),
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

