package ch.planet9.asn1.view.bouncycastle;


import ch.planet9.asn1.control.Controller;
import ch.planet9.asn1.control.SelectedFileListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.JTextPane;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.util.ASN1Dump;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author user
 */
public class ASN1HexView extends JTextPane implements SelectedFileListener {
    public static final long MAX_FILE_SIZE = 5*1024*1024;
    
    private Controller ctrl = Controller.getInstance();
    private StringBuffer buf = new StringBuffer();
    
    public ASN1HexView() {
        super();
        this.ctrl.registerSelectedFileListener(this);
    }

    @Override
    public void onSelectionChanged(File newSelection) {
        this.loadAndShowFile(newSelection);
    }
    
    protected void loadAndShowFile(File f) {
        this.buf = new StringBuffer();
        
        if(!f.exists() || !f.canRead() || f.length()> ASN1HexView.MAX_FILE_SIZE) {
            buf.append("File not readable or too long (>"+ASN1HexView.MAX_FILE_SIZE+" bytes).");
        } else if(f.isDirectory()) {
            buf.append("Cannot display directory.");
        } else {
             try (FileInputStream file = new FileInputStream(f)) {
                ASN1InputStream aIn = new ASN1InputStream(file);
                Object o = null;
                while((o=aIn.readObject()) != null) {
                    buf.append(ASN1Dump.dumpAsString(o, true));
                }
            }
            catch (FileNotFoundException e) {
                buf.append("Error: file not found.\n");
                buf.append(e.toString());
            }
            catch (IOException e) {
                buf.append("General IO Exception: \n");
                buf.append(e);
            }
        }
        
        this.setText(buf.toString());
    }
}
