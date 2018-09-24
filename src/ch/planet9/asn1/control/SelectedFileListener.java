/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.planet9.asn1.control;

import java.io.File;

/**
 *
 * @author user
 */
public interface SelectedFileListener {
    public void onSelectionChanged(File newSelection);
    
}
