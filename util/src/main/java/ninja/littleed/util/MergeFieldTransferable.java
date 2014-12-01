package ninja.littleed.util;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

public class MergeFieldTransferable implements Transferable {

	private DataFlavor flavour;
	private MergeField mergeField;

	public MergeFieldTransferable(MergeField mergeField) {
		//TODO Ensure that the mimetype string is best for our purposes.
		try {
			flavour = new DataFlavor ("text/html; class=java.lang.String;document=" +
			        "fragment" + ";charset=Unicode");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		this.mergeField = mergeField;
	}

	@Override
	public DataFlavor[] getTransferDataFlavors() {
		return new DataFlavor[] { flavour };
	}

	@Override
	public boolean isDataFlavorSupported(DataFlavor flavor) {
		return flavor == flavour;
	}

	@Override
	public Object getTransferData(DataFlavor flavor)
			throws UnsupportedFlavorException, IOException {
		return "<span style='mso-field-code:\" MERGEFIELD  " + mergeField.getEscapedFieldName()
				+ "  \\\\* MERGEFORMAT \"'>«" + mergeField.getFieldName() + "»</span>";
	}
	
}
