package vista;

import controlador.ControladorUsuario;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.Usuario;
import utilidades.HashUtil;
import utilidades.AppSesion;

public class GestionUsuariosVista extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(GestionUsuariosVista.class.getName());

    private final ControladorUsuario controlador = new ControladorUsuario();
    private int idUsuarioSeleccionado = -1;

    public GestionUsuariosVista() {
        initComponents();
        cargarTablaUsuarios();
        limpiarCampos();
        this.setLocationRelativeTo(null); // Centrar la ventana

        tableObstetras.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int fila = tableObstetras.getSelectedRow();
                if (fila >= 0) {
                    // Cuidado con los índices! Deben coincidir con cargarTablaUsuarios() y la vista.
                    // Según image_fb423b.png y los títulos de tus columnas:

                    // Columna 0: ID
                    idUsuarioSeleccionado = Integer.parseInt(tableObstetras.getValueAt(fila, 0).toString());

// Columna 1: Usuario
                    Object userObj = tableObstetras.getValueAt(fila, 1);
                    txtUsuario.setText(userObj != null ? userObj.toString() : "");

// Columna 2: Contraseña (Hash) - No se carga al JPasswordField
                    txtcontrasena.setText(""); // Siempre limpiar al seleccionar

// Columna 3: Nombre Completo
                    Object nombreObj = tableObstetras.getValueAt(fila, 3);
                    txtNombreCompleto.setText(nombreObj != null ? nombreObj.toString() : "");

// Columna 4: DNI
                    Object dniObj = tableObstetras.getValueAt(fila, 4);
                    txtDNI.setText(dniObj != null ? dniObj.toString() : "");

// Columna 5: ROL (Correcto)
                    Object rolObj = tableObstetras.getValueAt(fila, 5);
                    if (rolObj != null) {
                        cmbRol.setSelectedItem(rolObj.toString());
                    } else {
                        cmbRol.setSelectedIndex(-1); // O selecciona un valor por defecto si lo prefieres
                    }

// Columna 6: Pregunta Seguridad (ANTES ERA 5, AHORA ES 6)
                    Object pregSegObj = tableObstetras.getValueAt(fila, 6); // <-- ¡CORREGIDO EL ÍNDICE!
                    if (pregSegObj != null) {
                        cmbPreguntaSeguridad.setSelectedItem(pregSegObj.toString());
                    } else {
                        cmbPreguntaSeguridad.setSelectedIndex(-1);
                    }

// Columna 7: Respuesta Seguridad (ANTES ERA 6, AHORA ES 7)
                    txtRespuestaSeguridad.setText(""); // El valor real es el de la columna 7, pero lo vaciamos por seguridad
// Si alguna vez necesitaras leerlo para validación, sería:
// Object respSegObj = tableObstetras.getValueAt(fila, 7);
// String respuestaGuardada = respSegObj != null ? respSegObj.toString() : "";
                }
            }
        });
    }

    private void cargarTablaUsuarios() {
        DefaultTableModel modelo = new DefaultTableModel();
    // Índices:                    0       1              2                 3              4           5       6                      7
    modelo.setColumnIdentifiers(new Object[]{"ID", "Usuario", "Contraseña (Hash)", "Nombre Completo", "DNI", "Rol", "Pregunta Seguridad", "Respuesta Seguridad"});

    for (Usuario u : controlador.listar()) {
        modelo.addRow(new Object[]{
            u.getId(),
            u.getUsuario(),
            u.getContrasena(), // O "********"
            u.getNombreCompleto(),
            u.getDni(),
            u.getRol(), // <-- Asegúrate de que u.getRol() devuelve el String correcto
            u.getPreguntaSeguridad(),
            "********" // Ocultar la respuesta de seguridad
        });
    }
    tableObstetras.setModel(modelo);
    }

    private void limpiarCampos() {
        txtUsuario.setText("");
        txtcontrasena.setText("");
        txtNombreCompleto.setText("");
        txtDNI.setText(""); // LIMPIAR CAMPO DNI
        cmbRol.setSelectedIndex(0);
        cmbPreguntaSeguridad.setSelectedIndex(0); // Selecciona el primer elemento de la lista (o -1 para ninguno)
        txtRespuestaSeguridad.setText("");
        idUsuarioSeleccionado = -1; // Renombrado
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtUsuario = new javax.swing.JTextField();
        btnGuardar = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtNombreCompleto = new javax.swing.JTextField();
        btnModificar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        BtnLimpiar = new javax.swing.JButton();
        txtRespuestaSeguridad = new javax.swing.JTextField();
        txtcontrasena = new javax.swing.JPasswordField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableObstetras = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtDNI = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        cmbPreguntaSeguridad = new javax.swing.JComboBox<>();
        btnRegresar = new javax.swing.JButton();
        cmbRol = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setText("Usuario");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 40, -1, -1));

        txtUsuario.setBackground(new java.awt.Color(102, 102, 102));
        txtUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUsuarioActionPerformed(evt);
            }
        });
        jPanel1.add(txtUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 30, 220, 30));

        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        jPanel1.add(btnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 50, 90, -1));

        jLabel3.setText("Contrasena");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 80, -1, -1));

        jLabel5.setText("Nombre Completo");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 110, 110, 30));

        txtNombreCompleto.setBackground(new java.awt.Color(102, 102, 102));
        txtNombreCompleto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreCompletoActionPerformed(evt);
            }
        });
        jPanel1.add(txtNombreCompleto, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 110, 220, 30));

        btnModificar.setText("Actualizar");
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });
        jPanel1.add(btnModificar, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 80, 90, -1));

        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });
        jPanel1.add(btnEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 110, 90, -1));

        BtnLimpiar.setText("Limpiar");
        BtnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnLimpiarActionPerformed(evt);
            }
        });
        jPanel1.add(BtnLimpiar, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 140, 90, -1));

        txtRespuestaSeguridad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtRespuestaSeguridadActionPerformed(evt);
            }
        });
        jPanel1.add(txtRespuestaSeguridad, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 190, 220, -1));
        jPanel1.add(txtcontrasena, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 70, 220, 30));

        tableObstetras.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "id", "Usuario", "Contraseña", "DNI", "Nombre completo"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, true, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tableObstetras);
        if (tableObstetras.getColumnModel().getColumnCount() > 0) {
            tableObstetras.getColumnModel().getColumn(0).setResizable(false);
            tableObstetras.getColumnModel().getColumn(1).setResizable(false);
            tableObstetras.getColumnModel().getColumn(2).setResizable(false);
            tableObstetras.getColumnModel().getColumn(4).setResizable(false);
        }

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 280, 660, 210));

        jLabel6.setText("Respuesta de segurida:");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 200, -1, -1));

        jLabel4.setText("Pregunta de seguridad: ");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 160, -1, -1));

        jLabel8.setText("DNI:");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 40, 30, -1));
        jPanel1.add(txtDNI, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 30, 170, 30));

        jLabel9.setText("ROL:");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 80, -1, -1));

        cmbPreguntaSeguridad.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "¿Cuál es el nombre de tu mascota?", "¿Cuál era tu asignatura favorita en la escuela?", "¿Cuál es el segundo nombre de tu madre?", "¿En qué ciudad naciste?", "¿Cual es tu color favorito?" }));
        jPanel1.add(cmbPreguntaSeguridad, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 150, 320, 30));

        btnRegresar.setText("REGRESAR");
        btnRegresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegresarActionPerformed(evt);
            }
        });
        jPanel1.add(btnRegresar, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 10, -1, -1));

        cmbRol.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ADMIN", "OBSTETRA" }));
        jPanel1.add(cmbRol, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 70, 130, 30));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/login.jpg"))); // NOI18N
        jLabel1.setText("Eliminar");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 800, 500));

        jLabel7.setFont(new java.awt.Font("SansSerif", 2, 24)); // NOI18N
        jLabel7.setText("OBSTETRA");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 160, 20));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUsuarioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUsuarioActionPerformed

    private void btnRegresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegresarActionPerformed
        new MenuAdminVista().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnRegresarActionPerformed

    // MODIFICAR 

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        if (idUsuarioSeleccionado == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un usuario para modificar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String usuario = txtUsuario.getText().trim();
        String contrasena = new String(txtcontrasena.getPassword()); // Puede estar vacío si no se cambia
        String nombreCompleto = txtNombreCompleto.getText().trim().toUpperCase();
        String dni = txtDNI.getText().trim(); // Nuevo campo
        String rol = (String) cmbRol.getSelectedItem();
        String preguntaSeguridad = (String) cmbPreguntaSeguridad.getSelectedItem(); // Obtener del JComboBox
        String respuestaSeguridad = txtRespuestaSeguridad.getText().trim();

        // Validaciones de campos
        if (usuario.isEmpty() || nombreCompleto.isEmpty() || dni.isEmpty() || rol.isEmpty() || preguntaSeguridad.isEmpty() || respuestaSeguridad.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos (excepto la contraseña si no se cambia) son obligatorios.", "Campos vacíos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Validación de formato de DNI (Ejemplo simple, puedes mejorar con expresiones regulares)
        if (!dni.matches("\\d{8}")) { // Asumiendo DNI de 8 dígitos numéricos
            JOptionPane.showMessageDialog(this, "El DNI debe contener 8 dígitos numéricos.", "Error de formato", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validar unicidad de DNI y Usuario (excluyendo el propio usuario)
        if (controlador.existeUsuarioExcluyendoId(usuario, idUsuarioSeleccionado)) {
            JOptionPane.showMessageDialog(this, "El nombre de usuario '" + usuario + "' ya existe para otro usuario.", "Error de Duplicidad", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (controlador.existeDNIExcluyendoId(dni, idUsuarioSeleccionado)) {
            JOptionPane.showMessageDialog(this, "El DNI '" + dni + "' ya está registrado para otro usuario.", "Error de Duplicidad", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Usuario u = new Usuario();
        u.setId(idUsuarioSeleccionado);
        u.setUsuario(usuario);
        // Solo establece la contraseña si no está vacía (indica que el usuario desea cambiarla)
        if (!contrasena.isEmpty()) {
            u.setContrasena(contrasena);
        } else {
            u.setContrasena(null); // O deja la propiedad sin modificar para que el controlador lo ignore
        }
        u.setNombreCompleto(nombreCompleto); // Se convertirá a mayúsculas
        u.setDni(dni); // Establecer el DNI
        u.setRol(rol);
        u.setPreguntaSeguridad(preguntaSeguridad);
        // Si la respuesta de seguridad se hashea en el controlador, aquí pasas el texto plano
        // Si se hashea aquí, necesitarías HashUtil.sha256(respuestaSeguridad)
        u.setRespuestaSeguridad(respuestaSeguridad);
        u.setEstado(1); // Mantener estado activo si no hay un caso para cambiarlo aquí

        if (controlador.modificar(u)) {
            JOptionPane.showMessageDialog(this, "Usuario modificado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            cargarTablaUsuarios();
            limpiarCampos();
        } else {
            JOptionPane.showMessageDialog(this, "Error al modificar usuario. Verifique los datos o el log.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnModificarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        if (idUsuarioSeleccionado == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un usuario para eliminar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "¿Está seguro de que desea eliminar este usuario (desactivar)?", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (controlador.eliminar(idUsuarioSeleccionado)) {
                JOptionPane.showMessageDialog(this, "Usuario eliminado (desactivado) correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                cargarTablaUsuarios();
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar usuario.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void BtnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnLimpiarActionPerformed
        limpiarCampos();

    }//GEN-LAST:event_BtnLimpiarActionPerformed

    //GUARDAR

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        String usuario = txtUsuario.getText().trim();
        String contrasena = new String(txtcontrasena.getPassword()).trim();
        String nombreCompleto = txtNombreCompleto.getText().trim().toUpperCase();
        String dni = txtDNI.getText().trim(); // Nuevo campo
        String rol = (String) cmbRol.getSelectedItem();
        String preguntaSeguridad = (String) cmbPreguntaSeguridad.getSelectedItem(); // Obtener del JComboBox
        String respuestaSeguridad = txtRespuestaSeguridad.getText().trim();

        // Validaciones de campos obligatorios
        if (usuario.isEmpty() || contrasena.isEmpty() || nombreCompleto.isEmpty() || dni.isEmpty() || rol.isEmpty() || preguntaSeguridad.isEmpty() || respuestaSeguridad.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.", "Campos Vacíos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Validación de formato de DNI (Ejemplo simple, puedes mejorar con expresiones regulares)
        if (!dni.matches("\\d{8}")) { // Asumiendo DNI de 8 dígitos numéricos
            JOptionPane.showMessageDialog(this, "El DNI debe contener 8 dígitos numéricos.", "Error de formato", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validar unicidad de DNI y Usuario antes de intentar registrar
        if (controlador.existeUsuario(usuario)) {
            JOptionPane.showMessageDialog(this, "El nombre de usuario '" + usuario + "' ya existe.", "Error de Duplicidad", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (controlador.existeDNI(dni)) {
            JOptionPane.showMessageDialog(this, "El DNI '" + dni + "' ya está registrado.", "Error de Duplicidad", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validar longitud de contraseña (ejemplo)
        if (contrasena.length() < 6) { // Ejemplo de longitud mínima
            JOptionPane.showMessageDialog(this, "La contraseña debe tener al menos 6 caracteres.", "Contraseña débil", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Usuario nuevoUsuario = new Usuario(); // Renombrado de nuevoObstetra a nuevoUsuario
        nuevoUsuario.setUsuario(usuario);
        nuevoUsuario.setContrasena(contrasena); // El controlador lo hasheará
        nuevoUsuario.setNombreCompleto(nombreCompleto);
        nuevoUsuario.setDni(dni); // Establecer el DNI
        nuevoUsuario.setRol(rol);
        nuevoUsuario.setPreguntaSeguridad(preguntaSeguridad);
        // Si la respuesta de seguridad se hashea en el controlador, aquí pasas el texto plano
        // Si se hashea aquí, necesitarías HashUtil.sha256(respuestaSeguridad)
        nuevoUsuario.setRespuestaSeguridad(respuestaSeguridad);
        nuevoUsuario.setEstado(1); // Por defecto activo

        if (controlador.registrar(nuevoUsuario)) {
            JOptionPane.showMessageDialog(this, "Usuario registrado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            cargarTablaUsuarios();
            limpiarCampos();
        } else {
            JOptionPane.showMessageDialog(this, "Error al registrar usuario. Verifique los datos o el log.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void txtNombreCompletoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreCompletoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreCompletoActionPerformed

    private void txtRespuestaSeguridadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtRespuestaSeguridadActionPerformed

    }//GEN-LAST:event_txtRespuestaSeguridadActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnLimpiar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnRegresar;
    private javax.swing.JComboBox<String> cmbPreguntaSeguridad;
    private javax.swing.JComboBox<String> cmbRol;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable tableObstetras;
    private javax.swing.JTextField txtDNI;
    private javax.swing.JTextField txtNombreCompleto;
    private javax.swing.JTextField txtRespuestaSeguridad;
    private javax.swing.JTextField txtUsuario;
    private javax.swing.JPasswordField txtcontrasena;
    // End of variables declaration//GEN-END:variables
}
