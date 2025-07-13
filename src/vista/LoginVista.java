package vista;

import controlador.ControladorUsuario;
import javax.swing.JOptionPane;
import modelo.Usuario;
import utilidades.AppSesion;
import utilidades.HashUtil;

public class LoginVista extends javax.swing.JFrame {

    public LoginVista() {
        initComponents();
        this.setLocationRelativeTo(null); // Centrar la ventana
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        lblTitulo = new javax.swing.JLabel();
        lblIntentos = new javax.swing.JLabel();
        lblUsuario = new javax.swing.JLabel();
        txtUsuario = new javax.swing.JTextField();
        lblContrasena = new javax.swing.JLabel();
        txtContrasena = new javax.swing.JPasswordField();
        btnLogin = new javax.swing.JButton();
        btnRecuperar = new javax.swing.JButton();
        fondo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setLocation(new java.awt.Point(0, 0));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(204, 255, 255));
        jPanel1.setOpaque(false);
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblTitulo.setFont(new java.awt.Font("Segoe Print", 0, 18)); // NOI18N
        lblTitulo.setForeground(new java.awt.Color(0, 0, 0));
        lblTitulo.setText("Sistema Obstétrico - Iniciar Sesión");
        jPanel1.add(lblTitulo, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 0, -1, -1));

        lblIntentos.setFont(new java.awt.Font("Segoe Print", 0, 14)); // NOI18N
        jPanel1.add(lblIntentos, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 50, 330, 30));

        lblUsuario.setFont(new java.awt.Font("Segoe Print", 0, 16)); // NOI18N
        lblUsuario.setForeground(new java.awt.Color(0, 0, 0));
        lblUsuario.setText("Usuario: ");
        jPanel1.add(lblUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 120, -1, -1));

        txtUsuario.setFont(new java.awt.Font("Segoe Print", 0, 16)); // NOI18N
        jPanel1.add(txtUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 110, 210, 40));

        lblContrasena.setFont(new java.awt.Font("Segoe Print", 0, 16)); // NOI18N
        lblContrasena.setForeground(new java.awt.Color(0, 0, 0));
        lblContrasena.setText("Contraseña: ");
        jPanel1.add(lblContrasena, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 190, -1, -1));

        txtContrasena.setFont(new java.awt.Font("Segoe Print", 0, 16)); // NOI18N
        jPanel1.add(txtContrasena, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 180, 210, -1));

        btnLogin.setBackground(new java.awt.Color(0, 204, 0));
        btnLogin.setFont(new java.awt.Font("Segoe Print", 1, 16)); // NOI18N
        btnLogin.setForeground(new java.awt.Color(0, 0, 0));
        btnLogin.setText("Iniciar Sesión");
        btnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginActionPerformed(evt);
            }
        });
        jPanel1.add(btnLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 310, -1, -1));

        btnRecuperar.setBackground(new java.awt.Color(0, 204, 204));
        btnRecuperar.setFont(new java.awt.Font("Segoe Print", 0, 10)); // NOI18N
        btnRecuperar.setForeground(new java.awt.Color(0, 0, 0));
        btnRecuperar.setText("¿Olvidaste tu contraseña?");
        btnRecuperar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRecuperarActionPerformed(evt);
            }
        });
        jPanel1.add(btnRecuperar, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 230, -1, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 40, 410, 420));

        fondo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/login.jpg"))); // NOI18N
        getContentPane().add(fondo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoginActionPerformed
    String usuarioTexto = txtUsuario.getText().trim();
    String claveTexto = new String(txtContrasena.getPassword());

    // Validar campos vacíos antes de llamar al controlador
    if (usuarioTexto.isEmpty() || claveTexto.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Por favor, ingresa tu usuario y contraseña.", "Campos Vacíos", JOptionPane.WARNING_MESSAGE);
        return;
    }

    ControladorUsuario ctrl = new ControladorUsuario();
    // El método login ya devuelve el Usuario o null
    Usuario usuarioAutenticado = ctrl.login(usuarioTexto, claveTexto); 

    if (usuarioAutenticado != null) {
        // Login exitoso
        JOptionPane.showMessageDialog(this, "Bienvenido(a) " + usuarioAutenticado.getNombreCompleto() + "\nRol: " + usuarioAutenticado.getRol());

        // --- ¡AQUÍ ES DONDE ALMACENAS EL USUARIO EN LA SESIÓN GLOBAL! ---
        AppSesion.getInstance().setUsuarioLogueado(usuarioAutenticado);
        // ------------------------------------------------------------------

        // Limpiar los campos después del login exitoso
        txtUsuario.setText("");
        txtContrasena.setText("");
        lblIntentos.setText(""); // Limpiar cualquier mensaje de intentos

        // Redireccionar según el rol
        // Ahora las vistas se inicializan sin pasar el usuario, ya que lo obtienen de AppSesion
        if (usuarioAutenticado.getRol().equalsIgnoreCase("ADMIN")) {
            MenuAdminVista menuAdmin = new MenuAdminVista(); // SIN PARÁMETRO
            menuAdmin.setVisible(true);
        } else if (usuarioAutenticado.getRol().equalsIgnoreCase("OBSTETRA")) {
            MenuObstetraVista menuObstetra = new MenuObstetraVista(); // SIN PARÁMETRO
            menuObstetra.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Rol de usuario no reconocido.", "Error de Rol", JOptionPane.ERROR_MESSAGE);
            // Si el rol es desconocido, quizás también quieras cerrar sesión en AppSesion
            AppSesion.getInstance().cerrarSesion();
            return; // No cierres la ventana de login, o redirige a otro lugar
        }
        this.dispose(); // Cerrar la ventana de login
    } else {
        // Manejo de login fallido (usuario no existe, contraseña incorrecta, cuenta bloqueada)
        Usuario u = ctrl.obtenerUsuario(usuarioTexto); // Vuelves a obtener el usuario para verificar el estado

        if (u == null) {
            // El usuario no existe en la DB
            JOptionPane.showMessageDialog(this, "Usuario incorrecto.", "Error de Autenticación", JOptionPane.ERROR_MESSAGE);
            lblIntentos.setText("Usuario incorrecto.");
        } else if (u.getEstado() == ControladorUsuario.ESTADO_BLOQUEADO) {
            // El usuario existe pero está bloqueado
            // Tu controlador de login (ctrl.login) ya debería manejar la verificación de tiempo de bloqueo.
            // Si llega aquí con estado BLOQUEADO, es porque el bloqueo sigue activo.
            JOptionPane.showMessageDialog(this, "Tu cuenta está bloqueada. Intenta nuevamente en 5 minutos.", "Cuenta Bloqueada", JOptionPane.WARNING_MESSAGE);
            lblIntentos.setText("Cuenta bloqueada. Intenta más tarde.");
        } else {
            // Contraseña incorrecta (y el usuario no está bloqueado ni es nulo)
            JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos.", "Error de Autenticación", JOptionPane.ERROR_MESSAGE);
            // Mostrar intentos restantes, asumiendo que u.getIntentosRestantes() está actualizado
            lblIntentos.setText("Intentos restantes: " + u.getIntentosRestantes());
        }
        txtContrasena.setText(""); // Limpiar solo la contraseña en caso de error
    }
    }//GEN-LAST:event_btnLoginActionPerformed

    private void btnRecuperarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRecuperarActionPerformed
        RecuperarContrasenaVista recuperar = new RecuperarContrasenaVista();
        recuperar.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnRecuperarActionPerformed

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
            java.util.logging.Logger.getLogger(LoginVista.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LoginVista.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LoginVista.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LoginVista.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LoginVista().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLogin;
    private javax.swing.JButton btnRecuperar;
    private javax.swing.JLabel fondo;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblContrasena;
    private javax.swing.JLabel lblIntentos;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JLabel lblUsuario;
    private javax.swing.JPasswordField txtContrasena;
    private javax.swing.JTextField txtUsuario;
    // End of variables declaration//GEN-END:variables
}
