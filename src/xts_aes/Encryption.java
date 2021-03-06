/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xts_aes;

import java.awt.Dialog;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author proxc
 */
public class Encryption extends javax.swing.JFrame {

    /**
     * Creates new form Encryption
     */
    String sourceField, keyField, targetField;
    String sourceName, keyName, targetName;
    public Encryption() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        plainFileButton = new javax.swing.JButton();
        plainFileTF = new javax.swing.JTextField();
        keyFileTF = new javax.swing.JTextField();
        keyFileButton = new javax.swing.JButton();
        encryptButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(45, 118, 232));

        jLabel15.setFont(new java.awt.Font("Verdana", 1, 24)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/xts_aes/images/home_48px_1.png"))); // NOI18N
        jLabel15.setText("Ecryption");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(69, 69, 69)
                .addComponent(jLabel15)
                .addContainerGap(649, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jLabel15)
                .addContainerGap(50, Short.MAX_VALUE))
        );

        plainFileButton.setText("Browse");
        plainFileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                plainFileButtonActionPerformed(evt);
            }
        });

        plainFileTF.setForeground(new java.awt.Color(153, 153, 153));
        plainFileTF.setText("plain file path");
        plainFileTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                plainFileTFActionPerformed(evt);
            }
        });

        keyFileTF.setForeground(new java.awt.Color(153, 153, 153));
        keyFileTF.setText("key file path");
        keyFileTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                keyFileTFActionPerformed(evt);
            }
        });

        keyFileButton.setText("Browse");
        keyFileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                keyFileButtonActionPerformed(evt);
            }
        });

        encryptButton.setText("Encrypt");
        encryptButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                encryptButtonActionPerformed(evt);
            }
        });

        jLabel1.setText("Plain File");

        jLabel2.setText("Key File");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(127, 127, 127)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(keyFileTF, javax.swing.GroupLayout.PREFERRED_SIZE, 346, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(plainFileTF, javax.swing.GroupLayout.PREFERRED_SIZE, 346, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel2))
                        .addGap(32, 32, 32)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(plainFileButton)
                            .addComponent(keyFileButton)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(339, 339, 339)
                        .addComponent(encryptButton, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(53, 53, 53)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(plainFileTF, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(plainFileButton, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(44, 44, 44)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(keyFileButton, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(keyFileTF, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(62, 62, 62)
                .addComponent(encryptButton, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(108, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void plainFileButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_plainFileButtonActionPerformed
        // TODO add your handling code here:
        JFileChooser jfc = new JFileChooser(new File("D:\\"));

        int returnValue = jfc.showOpenDialog(null);
        // int returnValue = jfc.showSaveDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jfc.getSelectedFile();
            System.out.println(selectedFile.getAbsolutePath());
            this.plainFileTF.setText(selectedFile.getAbsolutePath());
            sourceField = selectedFile.getAbsolutePath();
        }
    }//GEN-LAST:event_plainFileButtonActionPerformed

    private void encryptButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_encryptButtonActionPerformed
        // TODO add your handling code here:
        
        if(plainFileTF.getText().equals("plain file path")){
            msgbox("plainFileTextField empty!");
            plainFileTF.requestFocusInWindow();
            return;
        }
        if(keyFileTF.getText().equals("key file path")){
            msgbox("keyFileTextField empty!");
            keyFileTF.requestFocusInWindow();
            return;
        }
        
        System.out.println("Encryption Start!");
        msgbox("Encryption Start!, press ok to start encrypt");
        
//        URL url = Encryption.class.getResource("/xts_aes/images/circle-loading-gif.gif");
//        Icon icon = new ImageIcon(url);
//        JLabel label = new JLabel(icon);
//
//        JFrame lc = new JFrame("Animation");
//        lc.getContentPane().add(label);
//        lc.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        lc.pack();
//        lc.setLocationRelativeTo(null);
//        lc.setVisible(true);

        //targetField = "D:/LMS/mik/cis/tugas/XTS_AES/src/xts_aes/cobaMatrixDecrypt.py";
        sourceName = sourceField;
        keyName = keyField;
        //targetName = targetField;
        
        try {
            //String basename = FilenameUtils.getBaseName(sourceField)+"_decrypted";
            
            targetName = sourceField.substring(0, sourceField.lastIndexOf("."));
            targetName = targetName.concat(".encrypted");
            System.out.println("Saved in "+targetName);
            targetField = targetName;
            // Use relative path for Unix systems
            File f = new File(targetField);

            f.getParentFile().mkdirs(); 
            f.createNewFile();
           
            XTS_AES aes = new XTS_AES(sourceName, keyName, targetName);
            aes.startEncryption(sourceName, keyName, targetName);
//            lc.setVisible(false);
            msgbox("Encryption Done!");
            
        }catch(Exception ex) {
            Logger.getLogger(Encrypt.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_encryptButtonActionPerformed

    private void plainFileTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_plainFileTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_plainFileTFActionPerformed

    private void keyFileButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_keyFileButtonActionPerformed
        // TODO add your handling code here:
        JFileChooser jfc = new JFileChooser(new File("D:\\"));

        int returnValue = jfc.showOpenDialog(null);
        // int returnValue = jfc.showSaveDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jfc.getSelectedFile();
            System.out.println(selectedFile.getAbsolutePath());
            this.keyFileTF.setText(selectedFile.getAbsolutePath());
            keyField = selectedFile.getAbsolutePath();
        }
    }//GEN-LAST:event_keyFileButtonActionPerformed

    private void keyFileTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_keyFileTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_keyFileTFActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Encryption.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Encryption.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Encryption.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Encryption.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Encryption().setVisible(true);
            }
        });
    }
    private void msgbox(String s){
        
        JOptionPane op = new JOptionPane(s, JOptionPane.INFORMATION_MESSAGE);
        JDialog dialog = op.createDialog("Information");
        dialog.setAlwaysOnTop(true);
        //dialog.setModalityType(Dialog.ModalityType.MODELESS);
        dialog.setModal(true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);      
        dialog.setVisible(true);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton encryptButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton keyFileButton;
    private javax.swing.JTextField keyFileTF;
    private javax.swing.JButton plainFileButton;
    private javax.swing.JTextField plainFileTF;
    // End of variables declaration//GEN-END:variables
}
