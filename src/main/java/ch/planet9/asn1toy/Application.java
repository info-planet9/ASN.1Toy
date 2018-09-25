package ch.planet9.asn1toy;

import ch.planet9.asn1toy.control.Controller;
import ch.planet9.asn1toy.view.SimpleHexView;
import ch.planet9.asn1toy.view.bouncycastle.ASN1HexView;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import javax.swing.*;
import javax.swing.tree.TreeModel;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.io.File;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

@SpringBootApplication
public class Application extends JFrame {

    private Controller ctrl = null;

    private javax.swing.JButton jButton1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTabbedPane jTabbedPane3;
    private javax.swing.JTextPane jTextPane1;
    private javax.swing.JTextPane jTextPane2;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JTree navigationTree;

	public Application() {
        this.ctrl = Controller.getInstance();
        initComponents();
	}

    private void initComponents() {

        jSplitPane1 = new javax.swing.JSplitPane();
        jPanel1 = new javax.swing.JPanel();
        jTabbedPane3 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTextPane1 = new SimpleHexView();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextPane2 = new ASN1HexView();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        navigationTree = new javax.swing.JTree();
        jToolBar1 = new javax.swing.JToolBar();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel3.setLayout(new java.awt.BorderLayout());

        jTextPane1.setEditable(false);
        jTextPane1.setBackground(new java.awt.Color(40, 40, 40));
        jTextPane1.setFont(new java.awt.Font("Bitstream Vera Sans Mono", 0, 18)); // NOI18N
        jTextPane1.setForeground(new java.awt.Color(64, 255, 0));
        jTextPane1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextPane1MouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(jTextPane1);

        jPanel3.add(jScrollPane4, java.awt.BorderLayout.CENTER);

        jTabbedPane3.addTab("Hex", jPanel3);

        jPanel5.setLayout(new java.awt.BorderLayout());

        jTextPane2.setEditable(false);
        jTextPane2.setBackground(new java.awt.Color(40, 40, 40));
        jTextPane2.setFont(new java.awt.Font("Bitstream Vera Sans Mono", 0, 12)); // NOI18N
        jTextPane2.setForeground(new java.awt.Color(64, 255, 0));
        jTextPane2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextPane2MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTextPane2);

        jPanel5.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        jTabbedPane3.addTab("ASN.1 Hex", jPanel5);

        jPanel1.add(jTabbedPane3, java.awt.BorderLayout.CENTER);
        jTabbedPane3.getAccessibleContext().setAccessibleName("treeTab");

        jSplitPane1.setRightComponent(jPanel1);

        jPanel2.setLayout(new java.awt.BorderLayout());

        navigationTree.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
                navigationTreeValueChanged(evt);
            }
        });
        jScrollPane3.setViewportView(navigationTree);
        TreeModel m = ctrl.setNewFileRoot(new File(System.getProperty("user.home")));
        this.navigationTree.setModel(m);
        this.navigationTree.updateUI();
        //this.navigationTree.addMouseListener(this.ml_tree);

        jPanel2.add(jScrollPane3, java.awt.BorderLayout.CENTER);

        jSplitPane1.setLeftComponent(jPanel2);

        getContentPane().add(jSplitPane1, java.awt.BorderLayout.CENTER);

        jToolBar1.setRollover(true);

        //jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons/22x22/status/folder-open.png"))); // NOI18N
        jButton1.setToolTipText("Open a Folder");
        jButton1.setFocusable(false);
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton1);

        getContentPane().add(jToolBar1, java.awt.BorderLayout.PAGE_START);

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int returnVal = fc.showOpenDialog(this);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            TreeModel m = this.ctrl.setNewFileRoot(fc.getSelectedFile());
            this.navigationTree.setModel(m);
            this.navigationTree.updateUI();
        }
    }

    private void navigationTreeValueChanged(javax.swing.event.TreeSelectionEvent evt) {
        File f = (File)evt.getPath().getLastPathComponent();
        this.ctrl.setSelectedFile(f);
    }

    private void jTextPane1MouseClicked(java.awt.event.MouseEvent evt) {
        if(evt.getClickCount() == 2) {
            StringSelection stringSelection = new StringSelection(jTextPane1.getText());
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(stringSelection, null);
        }
    }

    private void jTextPane2MouseClicked(java.awt.event.MouseEvent evt) {
        if(evt.getClickCount() == 2) {
            StringSelection stringSelection = new StringSelection(jTextPane2.getText());
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(stringSelection, null);
        }
    }


	public static void main(String[] args) {
        ConfigurableApplicationContext ctx = new SpringApplicationBuilder(Application.class)
                .headless(false).run(args);

        EventQueue.invokeLater(() -> {
            Application ex = ctx.getBean(Application.class);
            ex.setVisible(true);
        });





        //SpringApplication.run(Application.class, args);
	}
}
