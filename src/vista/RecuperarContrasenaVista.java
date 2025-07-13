package vista;

import controlador.ControladorUsuario;
import javax.swing.JOptionPane;
import java.sql.*;

public class RecuperarContrasenaVista extends javax.swing.JFrame {

    private String usuarioActual = ""; // Para almacenar el usuario una vez que se busca

    public RecuperarContrasenaVista() {
        initComponents();
        txtPregunta.setEditable(false);
        txtNuevaClave.setEnabled(false);
        btnGuardarNueva.setEnabled(false);
        this.setLocationRelativeTo(null); // Centrar la ventana
        txtRespuesta.setEnabled(false); // Desactivar respuesta inicialmente
        btnValidarRespuesta.setEnabled(false); // Desactivar botón validar inicialmente
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        lblTitulo = new javax.swing.JLabel();
        lblUsuario = new javax.swing.JLabel();
        txtUsuario = new javax.swing.JTextField();
        btnBuscarPregunta = new javax.swing.JButton();
        lblPregunta = new javax.swing.JLabel();
        txtPregunta = new javax.swing.JTextField();
        lblRespuesta = new javax.swing.JLabel();
        txtRespuesta = new javax.swing.JTextField();
        btnValidarRespuesta = new javax.swing.JButton();
        lblNuevaClave = new javax.swing.JLabel();
        txtNuevaClave = new javax.swing.JPasswordField();
        btnGuardarNueva = new javax.swing.JButton();
        btnVolverLogin = new javax.swing.JButton();
        fondo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setOpaque(false);
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblTitulo.setFont(new java.awt.Font("Segoe Print", 0, 18)); // NOI18N
        lblTitulo.setForeground(new java.awt.Color(0, 0, 0));
        lblTitulo.setText("Recuperar Contraseña");
        jPanel1.add(lblTitulo, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 0, -1, -1));

        lblUsuario.setFont(new java.awt.Font("Segoe Print", 0, 14)); // NOI18N
        lblUsuario.setForeground(new java.awt.Color(0, 0, 0));
        lblUsuario.setText("Usuario:");
        jPanel1.add(lblUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 70, -1, -1));

        txtUsuario.setFont(new java.awt.Font("Segoe Print", 0, 14)); // NOI18N
        jPanel1.add(txtUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 60, 200, -1));

        btnBuscarPregunta.setFont(new java.awt.Font("Segoe Print", 0, 14)); // NOI18N
        btnBuscarPregunta.setText("Ver pregunta");
        btnBuscarPregunta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarPreguntaActionPerformed(evt);
            }
        });
        jPanel1.add(btnBuscarPregunta, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 60, 130, 40));

        lblPregunta.setFont(new java.awt.Font("Segoe Print", 0, 14)); // NOI18N
        lblPregunta.setForeground(new java.awt.Color(0, 0, 0));
        lblPregunta.setText("Pregunta:");
        jPanel1.add(lblPregunta, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 140, -1, -1));

        txtPregunta.setEditable(false);
        txtPregunta.setFont(new java.awt.Font("Segoe Print", 0, 14)); // NOI18N
        txtPregunta.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jPanel1.add(txtPregunta, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 130, 350, -1));

        lblRespuesta.setFont(new java.awt.Font("Segoe Print", 0, 14)); // NOI18N
        lblRespuesta.setForeground(new java.awt.Color(0, 0, 0));
        lblRespuesta.setText("Respuesta: ");
        jPanel1.add(lblRespuesta, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 210, -1, -1));

        txtRespuesta.setFont(new java.awt.Font("Segoe Print", 0, 14)); // NOI18N
        jPanel1.add(txtRespuesta, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 200, 350, -1));

        btnValidarRespuesta.setFont(new java.awt.Font("Segoe Print", 0, 14)); // NOI18N
        btnValidarRespuesta.setForeground(new java.awt.Color(255, 255, 255));
        btnValidarRespuesta.setText("Validar respuesta");
        btnValidarRespuesta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnValidarRespuestaActionPerformed(evt);
            }
        });
        jPanel1.add(btnValidarRespuesta, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 250, -1, -1));

        lblNuevaClave.setFont(new java.awt.Font("Segoe Print", 0, 14)); // NOI18N
        lblNuevaClave.setForeground(new java.awt.Color(0, 0, 0));
        lblNuevaClave.setText("Nueva Contraseña:");
        jPanel1.add(lblNuevaClave, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 330, -1, -1));

        txtNuevaClave.setFont(new java.awt.Font("Segoe Print", 0, 14)); // NOI18N
        jPanel1.add(txtNuevaClave, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 320, 290, -1));

        btnGuardarNueva.setFont(new java.awt.Font("Segoe Print", 0, 14)); // NOI18N
        btnGuardarNueva.setText("Guardar nueva contraseña");
        btnGuardarNueva.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarNuevaActionPerformed(evt);
            }
        });
        jPanel1.add(btnGuardarNueva, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 390, -1, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 20, 510, 460));

        btnVolverLogin.setText("VOLVER");
        btnVolverLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVolverLoginActionPerformed(evt);
            }
        });
        getContentPane().add(btnVolverLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 110, 40));

        fondo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/login.jpg"))); // NOI18N
        getContentPane().add(fondo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 750, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnBuscarPreguntaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarPreguntaActionPerformed
        String usuario = txtUsuario.getText().trim();

        if (usuario.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese el usuario", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        ControladorUsuario ctrl = new ControladorUsuario();
        String[] datosSeguridad = ctrl.obtenerPreguntaYRespuestaSeguridad(usuario);

        if (datosSeguridad != null && datosSeguridad[0] != null && !datosSeguridad[0].isEmpty()) {
            txtPregunta.setText(datosSeguridad[0]);
            this.usuarioActual = usuario; // Guardar el usuario para pasos posteriores

            // Habilitar campos y botones para el siguiente paso
            txtRespuesta.setEnabled(true);
            btnValidarRespuesta.setEnabled(true);

            // --- CAMBIO AQUÍ: Desactivar el botón "Ver pregunta" y el campo de usuario ---
            btnBuscarPregunta.setEnabled(false);
            txtUsuario.setEditable(false);

            JOptionPane.showMessageDialog(this, "Pregunta de seguridad encontrada. Responda para continuar.", "Información", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Usuario no encontrado o no tiene pregunta de seguridad configurada.", "Error", JOptionPane.ERROR_MESSAGE);
            txtPregunta.setText(""); // Limpiar campo de pregunta

            // Desactivar todo si el usuario no se encuentra o no tiene pregunta
            txtRespuesta.setEnabled(false);
            btnValidarRespuesta.setEnabled(false);
            txtNuevaClave.setEnabled(false);
            btnGuardarNueva.setEnabled(false);

            // Asegurarse de que los campos de inicio estén habilitados si el usuario no fue encontrado
            btnBuscarPregunta.setEnabled(true);
            txtUsuario.setEditable(true);

            this.usuarioActual = "";
        }
    }//GEN-LAST:event_btnBuscarPreguntaActionPerformed

    private void btnValidarRespuestaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnValidarRespuestaActionPerformed
        String usuario = this.usuarioActual; // Usar usuarioActual para asegurar que estamos validando para el usuario que se buscó
        String respuestaIngresada = txtRespuesta.getText().trim().toLowerCase();

        if (usuario.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Primero debe buscar un usuario.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (respuestaIngresada.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese su respuesta.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        ControladorUsuario ctrl = new ControladorUsuario();
        String[] datosSeguridad = ctrl.obtenerPreguntaYRespuestaSeguridad(usuario); // Re-obtener para validar la respuesta

        if (datosSeguridad != null && datosSeguridad[1] != null) {
            String respuestaCorrecta = datosSeguridad[1].toLowerCase();
            if (respuestaIngresada.equals(respuestaCorrecta)) {
                txtNuevaClave.setEnabled(true);
                btnGuardarNueva.setEnabled(true);

                // --- CAMBIO AQUÍ: Desactivar el botón "Validar respuesta" y el campo de respuesta ---
                btnValidarRespuesta.setEnabled(false);
                txtRespuesta.setEditable(false); // Puedes hacerlo no editable, o simplemente deshabilitar el botón

                JOptionPane.showMessageDialog(this, "Respuesta correcta. Ahora puede ingresar su nueva contraseña.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Respuesta incorrecta. Inténtelo de nuevo.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
                txtNuevaClave.setEnabled(false);
                btnGuardarNueva.setEnabled(false);
            }
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo verificar la respuesta. Vuelva a buscar el usuario.", "Error Interno", JOptionPane.ERROR_MESSAGE);
            txtNuevaClave.setEnabled(false);
            btnGuardarNueva.setEnabled(false);
        }
    }//GEN-LAST:event_btnValidarRespuestaActionPerformed

    private boolean validarContrasena(String clave) {
        if (clave.length() < 6) {
            JOptionPane.showMessageDialog(this, "La contraseña debe tener al menos 6 caracteres.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        boolean tieneLetra = clave.matches(".*[a-zA-Z].*");
        boolean tieneNumero = clave.matches(".*\\d.*");
        if (!tieneLetra || !tieneNumero) {
            JOptionPane.showMessageDialog(this, "La contraseña debe contener letras y números.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    private void btnGuardarNuevaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarNuevaActionPerformed
        String usuario = this.usuarioActual; // Usamos el usuario guardado
        String nuevaClave = new String(txtNuevaClave.getPassword()).trim();

        if (usuario.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Primero debe buscar y validar un usuario.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!validarContrasena(nuevaClave)) {
            return; // El método validarContrasena ya muestra el mensaje
        }

        ControladorUsuario ctrl = new ControladorUsuario();
        if (ctrl.actualizarContrasenaYRestauraEstado(usuario, nuevaClave)) {
            JOptionPane.showMessageDialog(this, "Contraseña actualizada correctamente y cuenta restablecida.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            this.dispose(); // Cerrar ventana de recuperación
            new LoginVista().setVisible(true); // Abrir la vista de Login
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo actualizar la contraseña. Inténtelo de nuevo.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnGuardarNuevaActionPerformed

    private void btnVolverLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolverLoginActionPerformed
        new LoginVista().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnVolverLoginActionPerformed

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
            java.util.logging.Logger.getLogger(RecuperarContrasenaVista.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RecuperarContrasenaVista.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RecuperarContrasenaVista.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RecuperarContrasenaVista.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new RecuperarContrasenaVista().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscarPregunta;
    private javax.swing.JButton btnGuardarNueva;
    private javax.swing.JButton btnValidarRespuesta;
    private javax.swing.JButton btnVolverLogin;
    private javax.swing.JLabel fondo;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblNuevaClave;
    private javax.swing.JLabel lblPregunta;
    private javax.swing.JLabel lblRespuesta;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JLabel lblUsuario;
    private javax.swing.JPasswordField txtNuevaClave;
    private javax.swing.JTextField txtPregunta;
    private javax.swing.JTextField txtRespuesta;
    private javax.swing.JTextField txtUsuario;
    // End of variables declaration//GEN-END:variables
}
