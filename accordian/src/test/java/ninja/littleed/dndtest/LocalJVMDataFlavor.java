package ninja.littleed.dndtest;

import java.awt.datatransfer.DataFlavor;

/**
 * Data flavor for local objects.
 * 
 * @author LittleEd
 * 
 */
public class LocalJVMDataFlavor extends DataFlavor {

	public LocalJVMDataFlavor(Class<?> classType) {
		super(javaJVMLocalObjectMimeType + "; class=\"" + classType.getName() + "\"", classType.getName());
	}
}
