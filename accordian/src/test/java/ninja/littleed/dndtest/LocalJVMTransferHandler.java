package ninja.littleed.dndtest;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import javax.swing.JComponent;
import javax.swing.TransferHandler;

public class LocalJVMTransferHandler<T> extends TransferHandler {

	private static final long serialVersionUID = -256927098640615621L;
	private Class<T> classType;
	private LocalJVMDataFlavor dataFlavor;
	private DnDObjectHandler<T> objectHandler;
	private boolean giver = true;
	private boolean receiver = true;

	public LocalJVMTransferHandler(Class<T> classType, 
			DnDObjectHandler<T> handler) {
		this.classType = classType;
		dataFlavor = new LocalJVMDataFlavor(classType);
		objectHandler = handler;
	}

	public boolean isGiver() {
		return giver;
	}

	public void setGiver(boolean giver) {
		this.giver = giver;
	}

	public boolean isReceiver() {
		return receiver;
	}

	public void setReceiver(boolean reveiver) {
		this.receiver = reveiver;
	}

	/*******
	 * Giving methods
	 *******/
	@Override
	public int getSourceActions(JComponent c) {
		if (giver) {
			System.out.println("Wecanlink");
			return LINK;
		} else {
			return NONE;
		}
	}

	@Override
	protected Transferable createTransferable(JComponent c) {
		if (giver) {
			Transferable transferable = new Transferable() {

				public boolean isDataFlavorSupported(DataFlavor flavor) {
					return dataFlavor.equals(flavor);
				}

				public DataFlavor[] getTransferDataFlavors() {
					return new DataFlavor[] { dataFlavor };
				}

				public Object getTransferData(DataFlavor flavor)
						throws UnsupportedFlavorException, IOException {
					return objectHandler.getObject();
				}
			};
			System.out.println("Returning transferrable.");
			return transferable;
		} else {
			return null;
		}
	}

	@Override
	protected void exportDone(JComponent source, Transferable data, int action) {
		System.out.println("Export done?");
		super.exportDone(source, data, action);
	}

	/******
	 * Receiving methods
	 ******/
	@Override
	public boolean importData(TransferSupport support) {
		if (receiver) {
			try {
				T object = classType.cast(support.getTransferable()
						.getTransferData(dataFlavor));
				objectHandler.addObject(object);
				System.out.println("Object added");
				return true;
			} catch (IOException ioe) {
				ioe.printStackTrace();
				return false;
			} catch (UnsupportedFlavorException e) {
				e.printStackTrace();
				return false;
			}
		} else {
			return false;
		}
	}

	@Override
	public boolean canImport(TransferSupport support) {
		if (receiver) {
			for (DataFlavor flavor : support.getDataFlavors()) {
				if (flavor.equals(dataFlavor)) {
					System.out.println("Can import");
					return true;
				}
			}
		}
		return false;
	}

}
