package ninja.littleed.swing.dragspinner;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;

import javax.swing.JComponent;

import ninja.littleed.swing.dragspinner.integer.Orientation;

/**
 * A dashed line used for dragging the values in the drag spinner.
 * 
 * @author LittleEd
 * 
 */
@SuppressWarnings("serial")
class DragSpinnerControl extends JComponent {

	private int controlSize = 5;
	private Orientation orientation;
	private Stroke lineStroke;

	public DragSpinnerControl() {
		setForeground(Color.blue);
		this.orientation = Orientation.HORIZONTAL;
		lineStroke = new BasicStroke(1f, BasicStroke.CAP_SQUARE,
				BasicStroke.JOIN_MITER, 10f, new float[] { 5f, 2f }, 0f);
		refreshComponent();
	}

	/**
	 * Can be a vertical or horizontal line.
	 * 
	 * @param orientation
	 */
	public void setOrientation(Orientation orientation) {
		this.orientation = orientation;
		refreshComponent();
	}

	/**
	 * Can be a vertical or horizontal line.
	 * 
	 * @return
	 */
	public Orientation getOrientation() {
		return orientation;
	}

	/**
	 * The size of the component in the constrained dimension (vertical if
	 * orientation is horizontal and horizontal if the orientation is vertical).
	 */
	public int getControlSize() {
		return controlSize;
	}

	/**
	 * The size of the component in the constrained dimension (vertical if
	 * orientation is horizontal and horizontal if the orientation is vertical).
	 * 
	 * @param size
	 */
	public void setControlSize(int size) {
		this.controlSize = size;
	}

	/**
	 * The type of stroke of the line. Defaults to a thin dashed one.
	 * 
	 * @return
	 */
	public Stroke getLineStroke() {
		return lineStroke;
	}

	/**
	 * The type of stroke of the line. Defaults to a thin dashed one.
	 * 
	 * @param lineStroke
	 */
	public void setLineStroke(Stroke lineStroke) {
		this.lineStroke = lineStroke;
	}

	/**
	 * Called at initialisation, and when the orientation has been changed. Will
	 * set the new dimensions on the component.
	 */
	private void refreshComponent() {
		if (orientation == Orientation.VERTICAL) {
			setPreferredSize(new Dimension(controlSize+1, -1));
			setMinimumSize(new Dimension(controlSize+1, -1));
		} else {
			setPreferredSize(new Dimension(-1, controlSize+1));
			setMinimumSize(new Dimension(-1, controlSize+1));
		}
		invalidate();
	}

	/**
	 * Draws a vertical or horizontal dashed-line based on the orientation and
	 * the foreground colour.
	 */
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D gg = (Graphics2D) g;
		gg.setPaint(getForeground());
		gg.setStroke(lineStroke);
		int end = controlSize;
		int midpoint = end / 2;
		int w = getWidth();
		int h = getHeight();
		if (orientation == Orientation.HORIZONTAL) {
			gg.drawLine(0, midpoint, w, midpoint);
			gg.drawLine(0, 0, 0, end);
			gg.drawLine(w - 1, 0, w - 1, end);
		} else {
			gg.drawLine(midpoint, 0, midpoint, h);
			gg.drawLine(0, 0, end, 0);
			gg.drawLine(0, h - 1, end, h - 1);
		}

		super.paintComponent(g);
	}

}
