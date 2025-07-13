package vista;

import modelo.Usuario;
import utilidades.AppSesion;
import javax.swing.JOptionPane;
import utilidades.AppSesion;

public class MenuAdminVista extends javax.swing.JFrame {

    public MenuAdminVista() {
        initComponents();
        mostrarBienvenida();
        this.setLocationRelativeTo(null); // Centrar la ventana
    }

    private void mostrarBienvenida() {
        Usuario user = AppSesion.getInstance().getUsuarioLogueado();
        if (user != null) {
            lblBienvenida.setText("Bienvenido(a), " + user.getNombreCompleto() + " (Admin)");
            System.out.println("Admin logueado: " + user.getNombreCompleto()); // Para depuración
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        btnUsuarios = new javax.swing.JButton();
        lblBienvenida = new javax.swing.JLabel();
        btnObstetras = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnUsuarios.setFont(new java.awt.Font("Segoe Print", 0, 12)); // NOI18N
        btnUsuarios.setText("GESTIÓN DE USUARIOS");
        btnUsuarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUsuariosActionPerformed(evt);
            }
        });
        jPanel2.add(btnUsuarios, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 100, 200, 70));

        lblBienvenida.setFont(new java.awt.Font("Segoe Print", 1, 14)); // NOI18N
        jPanel2.add(lblBienvenida, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 50, 330, 30));

        btnObstetras.setFont(new java.awt.Font("Segoe Print", 0, 12)); // NOI18N
        btnObstetras.setText("REPORTE OBSTETRAS");
        btnObstetras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnObstetrasActionPerformed(evt);
            }
        });
        jPanel2.add(btnObstetras, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 100, 200, 70));

        jButton5.setFont(new java.awt.Font("Segoe Print", 0, 12)); // NOI18N
        jButton5.setText("ESTADÍSTICAS");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 190, 200, 70));

        jButton6.setFont(new java.awt.Font("Segoe Print", 0, 12)); // NOI18N
        jButton6.setText("Cerrar sesión");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 410, 120, 30));

        jLabel1.setBackground(new java.awt.Color(0, 0, 0));
        jLabel1.setFont(new java.awt.Font("Segoe Print", 0, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("SISTEMA DE GESTIÓN");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 10, -1, -1));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/login.jpg"))); // NOI18N
        jLabel2.setText("jLabel2");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 740, 440));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
       new EstadisticaVista().setVisible(true);

    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        int confirm = JOptionPane.showConfirmDialog(this, "¿Está seguro de cerrar sesión?", "Confirmar Cierre de Sesión", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            AppSesion.getInstance().cerrarSesion(); // Limpia el usuario de la sesión
            dispose(); // Cierra la ventana actual
            new LoginVista().setVisible(true); // Abre la ventana de login
        }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void btnObstetrasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnObstetrasActionPerformed

    }//GEN-LAST:event_btnObstetrasActionPerformed

    private void btnUsuariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUsuariosActionPerformed
        GestionUsuariosVista gv = new GestionUsuariosVista(); // Asume que GestionUsuariosVista también obtiene el usuario de AppSesion si lo necesita
        gv.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnUsuariosActionPerformed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new MenuAdminVista().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnObstetras;
    private javax.swing.JButton btnUsuarios;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lblBienvenida;
    // End of variables declaration//GEN-END:variables

}
