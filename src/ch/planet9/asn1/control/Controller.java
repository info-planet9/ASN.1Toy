package ch.planet9.asn1.control;

import ch.planet9.asn1.FileSystemTreeModel;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.tree.TreeModel;

/**
 *
 * @author user
 */
public class Controller {

    /**
     * @return the selectedFile
     */
    public File getSelectedFile() {
        return selectedFile;
    }

    /**
     * @param selectedFile the selectedFile to set
     */
    public void setSelectedFile(File selectedFile) {
        this.selectedFile = selectedFile;
        this.notifySelectedFileListeners(selectedFile);
    }
    private static Controller instance = null;
    
    private File selectedFile = null;
    private List<SelectedFileListener> selectedFileListeners = new ArrayList<SelectedFileListener>();
    
    public void registerSelectedFileListener(SelectedFileListener listener) {
        this.selectedFileListeners.add(listener);
    }
    
    public void unregisterSelectedFileListener(SelectedFileListener listener) {
        this.selectedFileListeners.remove(listener);
    }
    
    protected void notifySelectedFileListeners(File newSelection) {
        this.selectedFileListeners.forEach(listener -> listener.onSelectionChanged(newSelection));
    }
    
    public static Controller getInstance() {
        if(Controller.instance == null) {
            Controller.instance = new Controller();
        }
        
        return Controller.instance;
    }
    
    public TreeModel setNewFileRoot(File root) {
        System.out.println("New Root chosen: "+root.getAbsolutePath());
        return new FileSystemTreeModel(root);
    }
    
    private Controller() {} 
    
    public String getHex() {
        return "This should be a hex representation like in hexdump -C.\n\n....";
    }
    
}
