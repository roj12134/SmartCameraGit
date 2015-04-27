/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smartcamera.View;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 *
 * @author Gio
 */
public class MainView extends javax.swing.JFrame {

    

    /**
     * Creates new form MainView
     */
    public MainView() {
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
        jLabel1 = new javax.swing.JLabel();
        panelsContainer = new javax.swing.JTabbedPane();
        ViewLivePanel = new javax.swing.JPanel();
        joystick = new javax.swing.JLabel();
        exitButton = new javax.swing.JLabel();
        takePhotoButton = new javax.swing.JLabel();
        livePanel = new javax.swing.JPanel();
        joystickButton = new javax.swing.JLabel();
        fourButton = new javax.swing.JLabel();
        threeButton = new javax.swing.JLabel();
        editButton = new javax.swing.JLabel();
        galleryButton = new javax.swing.JLabel();
        editViewPanel = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        exitButton2 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        photoView = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        goToCameraButton = new javax.swing.JLabel();
        galleryButton2 = new javax.swing.JLabel();
        galleryPanel = new javax.swing.JPanel();
        next = new javax.swing.JLabel();
        back = new javax.swing.JLabel();
        gallery = new javax.swing.JPanel();
        photo = new javax.swing.JLabel();
        goToCameraButton2 = new javax.swing.JLabel();
        exitButton3 = new javax.swing.JLabel();
        trash = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Swis721 LtCn BT", 1, 36)); // NOI18N
        jLabel1.setText("Smart Camera");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 355, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(24, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addContainerGap())
        );

        panelsContainer.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        panelsContainer.setTabPlacement(javax.swing.JTabbedPane.BOTTOM);

        joystick.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        joystick.setIcon(new javax.swing.ImageIcon(getClass().getResource("/smartcamera/Images/GUI/1430019112_Nintendo_SNES.png"))); // NOI18N

        exitButton.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        exitButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/smartcamera/Images/GUI/1430019599_Log Out.png"))); // NOI18N

        takePhotoButton.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        takePhotoButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/smartcamera/Images/GUI/1430022678_Camera-2.png"))); // NOI18N

        livePanel.setBackground(new java.awt.Color(255, 204, 51));

        javax.swing.GroupLayout livePanelLayout = new javax.swing.GroupLayout(livePanel);
        livePanel.setLayout(livePanelLayout);
        livePanelLayout.setHorizontalGroup(
            livePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        livePanelLayout.setVerticalGroup(
            livePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 472, Short.MAX_VALUE)
        );

        joystickButton.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        joystickButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/smartcamera/Images/GUI/1430019941_Games.png"))); // NOI18N

        fourButton.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        fourButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/smartcamera/Images/GUI/1430023233_ic_looks_4_48px-128.png"))); // NOI18N

        threeButton.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        threeButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/smartcamera/Images/GUI/1430023225_ic_looks_3_48px-128.png"))); // NOI18N

        editButton.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        editButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/smartcamera/Images/GUI/1430027579_pencil-128.png"))); // NOI18N

        galleryButton.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        galleryButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/smartcamera/Images/GUI/1430028915_gallery.png"))); // NOI18N

        javax.swing.GroupLayout ViewLivePanelLayout = new javax.swing.GroupLayout(ViewLivePanel);
        ViewLivePanel.setLayout(ViewLivePanelLayout);
        ViewLivePanelLayout.setHorizontalGroup(
            ViewLivePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ViewLivePanelLayout.createSequentialGroup()
                .addGroup(ViewLivePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(editButton, javax.swing.GroupLayout.PREFERRED_SIZE, 312, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(joystick, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(galleryButton, javax.swing.GroupLayout.PREFERRED_SIZE, 312, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ViewLivePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ViewLivePanelLayout.createSequentialGroup()
                        .addComponent(joystickButton, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(takePhotoButton, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(threeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(fourButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(exitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(livePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        ViewLivePanelLayout.setVerticalGroup(
            ViewLivePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ViewLivePanelLayout.createSequentialGroup()
                .addGap(0, 19, Short.MAX_VALUE)
                .addComponent(galleryButton)
                .addGap(18, 18, 18)
                .addComponent(editButton)
                .addGap(28, 28, 28)
                .addComponent(joystick, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(ViewLivePanelLayout.createSequentialGroup()
                .addComponent(livePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(ViewLivePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(ViewLivePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(exitButton)
                        .addComponent(takePhotoButton, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(joystickButton, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(threeButton, javax.swing.GroupLayout.Alignment.TRAILING))
                    .addComponent(fourButton)))
        );

        panelsContainer.addTab("CamView", ViewLivePanel);

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/smartcamera/Images/GUI/1430019112_Nintendo_SNES.png"))); // NOI18N

        exitButton2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        exitButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/smartcamera/Images/GUI/1430019599_Log Out.png"))); // NOI18N

        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/smartcamera/Images/GUI/1430022678_Camera-2.png"))); // NOI18N

        photoView.setBackground(new java.awt.Color(255, 204, 51));

        javax.swing.GroupLayout photoViewLayout = new javax.swing.GroupLayout(photoView);
        photoView.setLayout(photoViewLayout);
        photoViewLayout.setHorizontalGroup(
            photoViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        photoViewLayout.setVerticalGroup(
            photoViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/smartcamera/Images/GUI/1430019941_Games.png"))); // NOI18N

        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/smartcamera/Images/GUI/1430023233_ic_looks_4_48px-128.png"))); // NOI18N

        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/smartcamera/Images/GUI/1430023225_ic_looks_3_48px-128.png"))); // NOI18N

        goToCameraButton.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        goToCameraButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/smartcamera/Images/GUI/1430030566_Windows_Live_Gallery.png"))); // NOI18N

        galleryButton2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        galleryButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/smartcamera/Images/GUI/1430028915_gallery.png"))); // NOI18N

        javax.swing.GroupLayout editViewPanelLayout = new javax.swing.GroupLayout(editViewPanel);
        editViewPanel.setLayout(editViewPanelLayout);
        editViewPanelLayout.setHorizontalGroup(
            editViewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, editViewPanelLayout.createSequentialGroup()
                .addGroup(editViewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(goToCameraButton, javax.swing.GroupLayout.PREFERRED_SIZE, 312, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(galleryButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 312, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(editViewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(editViewPanelLayout.createSequentialGroup()
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(exitButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(photoView, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        editViewPanelLayout.setVerticalGroup(
            editViewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(editViewPanelLayout.createSequentialGroup()
                .addContainerGap(19, Short.MAX_VALUE)
                .addComponent(galleryButton2)
                .addGap(18, 18, 18)
                .addComponent(goToCameraButton)
                .addGap(28, 28, 28)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(editViewPanelLayout.createSequentialGroup()
                .addComponent(photoView, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(editViewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(editViewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(exitButton2)
                        .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.TRAILING))
                    .addComponent(jLabel13)))
        );

        panelsContainer.addTab("EditView", editViewPanel);

        next.setIcon(new javax.swing.ImageIcon(getClass().getResource("/smartcamera/Images/GUI/1430135734_circle_next_arrow_disclosure_-128.png"))); // NOI18N

        back.setIcon(new javax.swing.ImageIcon(getClass().getResource("/smartcamera/Images/GUI/1430135689_circle_back_arrow_-128.png"))); // NOI18N

        gallery.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout galleryLayout = new javax.swing.GroupLayout(gallery);
        gallery.setLayout(galleryLayout);
        galleryLayout.setHorizontalGroup(
            galleryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(galleryLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(photo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        galleryLayout.setVerticalGroup(
            galleryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, galleryLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(photo, javax.swing.GroupLayout.DEFAULT_SIZE, 426, Short.MAX_VALUE)
                .addContainerGap())
        );

        goToCameraButton2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        goToCameraButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/smartcamera/Images/GUI/1430030566_Windows_Live_Gallery.png"))); // NOI18N

        exitButton3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        exitButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/smartcamera/Images/GUI/1430019599_Log Out.png"))); // NOI18N

        trash.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        trash.setIcon(new javax.swing.ImageIcon(getClass().getResource("/smartcamera/Images/GUI/1430138205_24-Empty Trash.png"))); // NOI18N

        javax.swing.GroupLayout galleryPanelLayout = new javax.swing.GroupLayout(galleryPanel);
        galleryPanel.setLayout(galleryPanelLayout);
        galleryPanelLayout.setHorizontalGroup(
            galleryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, galleryPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(galleryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(back)
                    .addComponent(goToCameraButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(galleryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(gallery, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(galleryPanelLayout.createSequentialGroup()
                        .addComponent(trash, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 572, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(galleryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(next)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, galleryPanelLayout.createSequentialGroup()
                        .addComponent(exitButton3)
                        .addContainerGap())))
        );
        galleryPanelLayout.setVerticalGroup(
            galleryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, galleryPanelLayout.createSequentialGroup()
                .addGroup(galleryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(galleryPanelLayout.createSequentialGroup()
                        .addGap(194, 194, 194)
                        .addGroup(galleryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(back, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(next)))
                    .addGroup(galleryPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(gallery, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                .addGroup(galleryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(exitButton3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, galleryPanelLayout.createSequentialGroup()
                        .addGroup(galleryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(goToCameraButton2, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(trash, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())))
        );

        panelsContainer.addTab("GalleryView", galleryPanel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panelsContainer)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelsContainer))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public JPanel getViewLivePanel() {
        return ViewLivePanel;
    }

    public JLabel getEditButton() {
        return editButton;
    }

    public JPanel getEditViewPanel() {
        return editViewPanel;
    }

    public JLabel getExitButton() {
        return exitButton;
    }

    public JLabel getExitButton2() {
        return exitButton2;
    }
    
    public JLabel getExitButton3() {
        return exitButton3;
    }

    public JLabel getFourButton() {
        return fourButton;
    }

    public JLabel getGalleryButton() {
        return galleryButton;
    }

    public JLabel getGalleryButton2() {
        return galleryButton2;
    }

    public JLabel getGoToCameraButton() {
        return goToCameraButton;
    }

    public JLabel getGoToCameraButton2() {
        return goToCameraButton2;
    }
    
    public JLabel getJoystick() {
        return joystick;
    }

    public JLabel getJoystickButton() {
        return joystickButton;
    }

    public JPanel getLivePanel() {
        return livePanel;
    }

    public JPanel getGalleryPanel() {
        return galleryPanel;
    }
    
    public JTabbedPane getPanelsContainer() {
        return panelsContainer;
    }

    public JPanel getPhotoView() {
        return photoView;
    }

    public JLabel getTakePhotoButton() {
        return takePhotoButton;
    }

    public JLabel getThreeButton() {
        return threeButton;
    }
    
    public JLabel getPhoto() {
        return photo;
    }
    
    public void setPhoto(Icon image) {
        photo.setIcon(image);
    }
    
    public JLabel getNext() {
        return next;
    }
    
    public JLabel getBack() {
        return back;
    }
    
    public JLabel getTrash() {
        return trash;
    }
    
    
    
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
            java.util.logging.Logger.getLogger(MainView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainView().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel ViewLivePanel;
    private javax.swing.JLabel back;
    private javax.swing.JLabel editButton;
    private javax.swing.JPanel editViewPanel;
    private javax.swing.JLabel exitButton;
    private javax.swing.JLabel exitButton2;
    private javax.swing.JLabel exitButton3;
    private javax.swing.JLabel fourButton;
    private javax.swing.JPanel gallery;
    private javax.swing.JLabel galleryButton;
    private javax.swing.JLabel galleryButton2;
    private javax.swing.JPanel galleryPanel;
    private javax.swing.JLabel goToCameraButton;
    private javax.swing.JLabel goToCameraButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel joystick;
    private javax.swing.JLabel joystickButton;
    private javax.swing.JPanel livePanel;
    private javax.swing.JLabel next;
    private javax.swing.JTabbedPane panelsContainer;
    private javax.swing.JLabel photo;
    private javax.swing.JPanel photoView;
    private javax.swing.JLabel takePhotoButton;
    private javax.swing.JLabel threeButton;
    private javax.swing.JLabel trash;
    // End of variables declaration//GEN-END:variables
}
