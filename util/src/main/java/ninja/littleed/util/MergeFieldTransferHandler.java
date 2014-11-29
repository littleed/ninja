package ninja.littleed.util;

import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;

import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.TransferHandler;

public class MergeFieldTransferHandler extends TransferHandler {

	private static final long serialVersionUID = 3490792545037077984L;

    @Override
    public int getSourceActions(JComponent c) {
        return DnDConstants.ACTION_COPY_OR_MOVE;
    }

    @Override
    protected Transferable createTransferable(JComponent c) {
        Transferable t = null;
        if (c instanceof JList) {
            JList list = (JList) c;
            Object value = list.getSelectedValue();
            if (value instanceof MergeField) {
            	MergeField li = (MergeField) value;
                t = new MergeFieldTransferable(li);
            }
        }
        return t;
    }

    @Override
    protected void exportDone(JComponent source, Transferable data, int action) {
        System.out.println("ExportDone");
        // Here you need to decide how to handle the completion of the transfer,
        // should you remove the item from the list or not...
    }

}
