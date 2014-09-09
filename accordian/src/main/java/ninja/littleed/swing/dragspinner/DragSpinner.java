package ninja.littleed.swing.dragspinner;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;
import javax.swing.text.PlainDocument;

import ninja.littleed.swing.dragspinner.integer.Orientation;

/**
 * Much like a spinner component where the incrementing and decrementing of
 * values are controlled by dragging the mouse. If a renderer is provided, then
 * the user can also type values directly into the text field. The generic T is
 * the type of value the spinner is dealing with and the model and renderer must
 * be compatible with this.
 * 
 * @author LittleEd
 * 
 */
public class DragSpinner<T> extends JComponent {

	private static final long serialVersionUID = -7020677235459510600L;
	// Keeps track on the current enablement.
	private boolean enabled = true;
	// Prevents cyclic calls that lead to OutOfMem exceptions.
	private boolean suppressUpdate = false;
	private int resolution = 5;
	private DragSpinnerControl line;
	private DragSpinnerModel<T> model;
	private DragSpinnerRenderer<T> renderer;
	private ChangeListener changeListener;
	private JTextField textField;
	private MouseAdapter textFieldListener;
	private PlainDocument document;

	/**
	 * Creates a DragSpinner without any model. A model must be supplied before
	 * this can be used.
	 */
	public DragSpinner() {
		changeListener = new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				try {
					updateTextField();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		};
		textField = new JTextField();
		textFieldListener = new SpinnerMouseListener();

		document = new PlainDocument();
		document.addDocumentListener(new DocumentListener() {

			public void removeUpdate(DocumentEvent e) {
				changedUpdate(e);
			}

			public void insertUpdate(DocumentEvent e) {
				changedUpdate(e);
			}

			public void changedUpdate(DocumentEvent e) {
				Document doc = e.getDocument();
				try {
					String text = doc.getText(0, doc.getLength());
					updateModel(text);
				} catch (BadLocationException ex) {
					ex.printStackTrace();
				}
			}
		});
		textField.setDocument(document);
		textField.setEnabled(false);

		line = new DragSpinnerControl();
		line.setOrientation(Orientation.VERTICAL);
		line.addMouseMotionListener(textFieldListener);
		line.addMouseListener(textFieldListener);

		setLayout(new BorderLayout());
		add(textField);
		add(line, BorderLayout.SOUTH);
		refreshEnablement();
	}

	/**
	 * Creates a new DragSpinner with the supplied model.
	 * 
	 * @param spinnerModel
	 */
	public DragSpinner(DragSpinnerModel<T> spinnerModel) {
		this();
		setModel(spinnerModel);
		setControlLocation(BorderLayout.SOUTH);
	}

	/**
	 * Replaces any exciting model with a new model type.
	 * 
	 * @param spinnerModel
	 */
	public void setModel(DragSpinnerModel<T> spinnerModel) {
		// Remove old model
		if (this.model != null) {
			this.model.removeChangeListener(changeListener);
		}
		// Set the new one.
		this.model = spinnerModel;
		this.model.addChangeListener(changeListener);
		updateTextField();
	}

	/**
	 * Returns the current model. Values can be set via the model.
	 * 
	 * @return
	 */
	public DragSpinnerModel<T> getModel() {
		return model;
	}

	/**
	 * An optional component that will allow users to directly enter new values
	 * into the text field. It allows 2-way conversion between the text field's
	 * string and the model's value, as well as how the text field should show
	 * an invalid value.
	 * 
	 * @param converter
	 */
	public void setRenderer(DragSpinnerRenderer<T> converter) {
		this.renderer = converter;
		refreshEnablement();
		updateModel(textField.getText());
	}

	/**
	 * An optional component that will allow users to directly enter new values
	 * into the text field. It allows 2-way conversion between the text field's
	 * string and the model's value, as well as how the text field should show
	 * an invalid value.
	 * 
	 * @return
	 */
	public DragSpinnerRenderer<T> getRenderer() {
		return renderer;
	}

	/**
	 * Sets the document filter onto the text field as an additional level of
	 * input validation.
	 * 
	 * @param filter
	 */
	public void setDocumentFilter(DocumentFilter filter) {
		document.setDocumentFilter(filter);
	}

	/**
	 * Returns the current document filter set on the text field.
	 * 
	 * @return
	 */
	public DocumentFilter getDocumentFilter() {
		return document.getDocumentFilter();
	}

	/**
	 * True if the spinner is enabled.
	 */
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * True if the spinner is enabled.
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
		refreshEnablement();
	}

	/**
	 * The width of the text field in columns.
	 * 
	 * @param columns
	 */
	public void setColumns(int columns) {
		textField.setColumns(columns);
	}

	/**
	 * The width of the text field in columns.
	 * 
	 * @return
	 */
	public int getColumns() {
		return textField.getColumns();
	}

	/**
	 * The number of pixels in a step. A step is an increment or decrement value
	 * supplied to the model in order to determine the next or previous value.
	 * 
	 * @return
	 */
	public int getResolution() {
		return resolution;
	}

	/**
	 * The number of pixels in a step. A step is an increment or decrement value
	 * supplied to the model in order to determine the next or previous value.
	 * 
	 * @param resolution
	 */
	public void setResolution(int resolution) {
		this.resolution = resolution;
	}

	public void setControlLocation(String controlLocation) {
		remove(line);
		if (controlLocation == BorderLayout.WEST
				|| controlLocation == BorderLayout.EAST) {
			line.setOrientation(Orientation.VERTICAL);
		} else if (controlLocation == BorderLayout.NORTH
				|| controlLocation == BorderLayout.SOUTH) {
			line.setOrientation(Orientation.HORIZONTAL);
		} else {
			throw new IllegalArgumentException(
					"control location needs to be one of BorderLayout's compass directions.");
		}
		add(line, controlLocation);
		invalidate();
		revalidate();
		refreshEnablement();
	}

	/**
	 * Vertical or horizontal, depending on which way the control line is set
	 * up.
	 * 
	 * @return
	 */
	public Orientation getOrientation() {
		return line.getOrientation();
	}

	/**
	 * The text field will only be editable if a renderer has been supplied.
	 * This ensures that this is checked before the text field is enabled.
	 */
	private void refreshEnablement() {
		textField.setEnabled(enabled && renderer != null);
		if (enabled) {
			if (getOrientation().equals(Orientation.HORIZONTAL)) {
				line.setCursor(Cursor
						.getPredefinedCursor(Cursor.W_RESIZE_CURSOR));
			} else {
				line.setCursor(Cursor
						.getPredefinedCursor(Cursor.N_RESIZE_CURSOR));
			}
		} else {
			line.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}
	}

	/**
	 * Updates the spinner model with the value in the text field, provided this
	 * isn't called because the model has updated the text field.
	 * 
	 * @param value
	 */
	private void updateModel(String value) {
		if (renderer != null) {
			if (!suppressUpdate) {
				suppressUpdate = true;
				model.setValue(renderer.getValueForString(value));
				validateValue();
				suppressUpdate = false;
			}
		}
	}

	/**
	 * Updates the text field with the value in the model, provided this isn't
	 * called because the text field has updated the model.
	 */
	private void updateTextField() {
		if (!suppressUpdate) {
			suppressUpdate = true;
			if (renderer != null) {
				textField.setText(renderer.getStringForValue(model.getValue()));
			} else {
				textField.setText(model.getValue().toString());
			}
			validateValue();
			suppressUpdate = false;
		}
	}

	/**
	 * If the value is seen as invalid by the model, then this will decorate the
	 * text field to show this. If there is the renderer, then the decoration
	 * responsiblity is passed on to it. Although the user cannot enter values
	 * manually without a renderer, invalid values can still be set on it
	 * programtically through the model. That is why some default rendering
	 * rules are required.
	 */
	private void validateValue() {
		boolean valid = model.isValueValid();
		if (renderer != null) {
			renderer.showValidation(textField, valid);
		} else {
			if (valid) {
				textField.setForeground(Color.red);
			} else {
				textField.setForeground(Color.black);
			}
		}
	}

	/**
	 * This listener is responsible for listening to drag events and
	 * incrementing/decrementing the model accordingly.
	 * 
	 * @author LittleEd
	 * 
	 */
	private class SpinnerMouseListener extends MouseAdapter {
		private Integer origin;
		private T tempValue;

		/**
		 * If enabled, this will increment/decrement the value based on the
		 * value it started at and the number of steps in a direction from the
		 * origin derived from the spinner's resolution.
		 */
		public void mouseDragged(MouseEvent e) {
			if (enabled) {
				if (origin == null) {
					if (getOrientation() == Orientation.HORIZONTAL) {
						origin = e.getX();
					} else {
						origin = e.getY();
					}
					tempValue = model.getValue();
				} else {
					int distance;
					if (getOrientation() == Orientation.HORIZONTAL) {
						distance = e.getX() - origin;
					} else {
						distance = origin - e.getY();
					}

					int steps = distance / resolution;
					model.setValue(model.getNextValue(tempValue, steps));
					validateValue();
				}
			}
		}

		/**
		 * Reset the origin point so it can be reset by the next drag event.
		 */
		@Override
		public void mouseReleased(MouseEvent e) {
			origin = null;
		}
	}
}
