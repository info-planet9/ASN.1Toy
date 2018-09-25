/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.planet9.asn1toy.view;

import ch.planet9.asn1toy.control.Controller;
import ch.planet9.asn1toy.control.SelectedFileListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import javax.swing.JTextPane;

/**
 *
 * @author user
 */
public class SimpleHexView extends JTextPane implements SelectedFileListener {
    public static final long MAX_FILE_SIZE = 5*1024*1024;
    
    private Controller ctrl = Controller.getInstance();
    private StringBuffer buf = new StringBuffer();
    
    public SimpleHexView() {
        super();
        this.ctrl.registerSelectedFileListener(this);
    }

    @Override
    public void onSelectionChanged(File newSelection) {
        this.loadAndShowFile(newSelection);
    }
    
    protected void loadAndShowFile(File f) {
        this.buf = new StringBuffer();
        
        if(!f.exists() || !f.canRead() || f.length()> SimpleHexView.MAX_FILE_SIZE) {
            buf.append("File not readable or too long (>"+SimpleHexView.MAX_FILE_SIZE+" bytes).");
        } else if(f.isDirectory()) {
            buf.append("Cannot display directory.");
        } else {
             try (FileInputStream file = new FileInputStream(f)) {
                dumpFile(file, 0, (int)f.length(), 16);
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
    
    
    
    void dumpFile(InputStream file, int offset, int bytesToRead, int bytesPerLine) {

        // Buffer for storing bytes read from the file.
        byte[] buffer = new byte[bytesPerLine];

        // Maximum number of bytes to attempt to read per call to file.read().
        int maxBytes;

        // Number of bytes read by a call to file.read().
        int numBytes = 0;

        // If an offset has been specified, attempt to seek to it.
        if (offset != 0) {
            try {
                if (file.skip(offset) < offset) {
                    buf.append("Error while attempting to seek to the specified offset.");
                }
            }
            catch (IOException e) {
                buf.append("Error while attempting to seek to the specified offset.");
            }
        }

        // Read and dump one line of output per iteration.
        while (true) {

            // If bytesToRead < 0 (read all), try to read one full line.
            if (bytesToRead < 0) {
                maxBytes = bytesPerLine;
            }

            // Else if line length < bytesToRead, try to read one full line.
            else if (bytesPerLine < bytesToRead) {
                maxBytes = bytesPerLine;
            }

            // Otherwise, try to read all the remaining bytes in one go.
            else {
                maxBytes = bytesToRead;
            }

            // Attempt to read up to maxBytes from the file.
            try {
                numBytes = file.read(buffer, 0, maxBytes);
            }
            catch (IOException e) {
                buf.append(e);
            }

            // Write a line of output.
            if (numBytes > 0) {
                writeLine(buffer, numBytes, offset, bytesPerLine);
                offset += numBytes;
                bytesToRead -= numBytes;
            } else {
                break;
            }
        }
    }


    void writeLine(byte[] buffer, int numBytes, int offset, int bytesPerLine) {
        this.buf.append(String.format("%6X | ", offset));
        
        for (int i = 0; i < bytesPerLine; i++) {

            // Write an extra space in front of every fourth byte except the first.
            if (i > 0 && i % 4 == 0) {
                this.buf.append(" ");
            }

            // Write the byte in hex form, or a spacer if we're out of bytes.
            if (i < numBytes) {
                this.buf.append(String.format(" %02X", buffer[i]));
            } else {
                this.buf.append(String.format("   "));
            }
        }

        this.buf.append(" | ");

        // Write a character for each byte in the printable ascii range.
        for (int i = 0; i < numBytes; i++) {
            if (buffer[i] > 31 && buffer[i] < 127) {
                this.buf.append((char)buffer[i]);
            } else {
                this.buf.append(".");
            }
        }

        this.buf.append("\n");
    }
}
