/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.planet9.asn1.view.bouncycastle;

import ch.planet9.asn1.control.Controller;
import ch.planet9.asn1.control.SelectedFileListener;
import java.io.File;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeModel;

/**
 *
 * @author user
 */
public class BCJTree extends JTree implements SelectedFileListener {
    private Controller ctrl = Controller.getInstance();
    
    public BCJTree() {
        super();
        this.ctrl.registerSelectedFileListener(this);
        //this.setModel(new DefaultTreeModel());
    }

    @Override
    public void onSelectionChanged(File newSelection) {
        this.loadAndShowFile(newSelection);
    }

    private void loadAndShowFile(File newSelection) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }    
}
