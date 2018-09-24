/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.planet9.asn1;

import javax.swing.SwingUtilities;

/**
 *
 * @author user
 */
public class ASN1ToyMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                MainWindow mainWindow = new MainWindow();
                mainWindow.setVisible(true); }
        });
    }
    
}
