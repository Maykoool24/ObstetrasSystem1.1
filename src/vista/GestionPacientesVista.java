package vista;

import controlador.ControladorPaciente;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.Paciente;
import com.toedter.calendar.JDateChooser;
import java.time.ZoneId;
import java.util.Date;

public class GestionPacientesVista extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(GestionPacientesVista.class.getName());

    public GestionPacientesVista() {
        initComponents();
        cargarTablaPacientes();
        this.setLocationRelativeTo(null); // Centrar la ventana

        tableCrudPaciente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int fila = tableCrudPaciente.rowAtPoint(evt.getPoint());
                if (fila >= 0) {
                    idPacienteSeleccionado = Integer.parseInt(tableCrudPaciente.getValueAt(fila, 0).toString());
                    txtNombre.setText(tableCrudPaciente.getValueAt(fila, 1).toString());
                    txtDni.setText(tableCrudPaciente.getValueAt(fila, 2).toString());
                    txtTelefono.setText(tableCrudPaciente.getValueAt(fila, 4).toString());
                    txtDireccion.setText(tableCrudPaciente.getValueAt(fila, 5).toString());

                    for (Paciente p : controlador.listar()) {
                        if (p.getId() == idPacienteSeleccionado) {
                            fechaNacimientoChooser.setDate(java.util.Date.from(p.getFechaNac().atStartOfDay(ZoneId.systemDefault()).toInstant()));
                            break;
                        }
                    }
                }
            }
        });
    }

    // TABLA
    private void cargarTablaPacientes() {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.setColumnIdentifiers(new Object[]{"ID", "Nombre", "DNI", "Edad", "Teléfono", "Dirección"});

        for (Paciente p : controlador.listar()) {
            int edad = controlador.calcularEdad(p);  // Calcula la edad
            modelo.addRow(new Object[]{
                p.getId(), p.getNombreCompleto(), p.getDni(),
                edad, p.getTelefono(), p.getDireccion()
            });
        }

        tableCrudPaciente.setModel(modelo);
    }

    private ControladorPaciente controlador = new ControladorPaciente();
    private int idPacienteSeleccionado = -1;

    // LIMPIAR
    private void limpiarCampos() {
        txtNombre.setText("");
        txtDni.setText("");
        txtTelefono.setText("");
        txtDireccion.setText("");
        fechaNacimientoChooser.setDate(null);
        idPacienteSeleccionado = -1;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        btnGuardar = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtDni = new javax.swing.JTextField();
        txtTelefono = new javax.swing.JTextField();
        txtDireccion = new javax.swing.JTextField();
        btnModificar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        fechaNacimientoChooser = new com.toedter.calendar.JDateChooser();
        BtnLimpiar = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableCrudPaciente = new javax.swing.JTable();
        btnRegresar = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

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

        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("Nombre:");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 40, -1, -1));

        txtNombre.setBackground(new java.awt.Color(102, 102, 102));
        txtNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreActionPerformed(evt);
            }
        });
        jPanel1.add(txtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 30, 260, 30));

        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        jPanel1.add(btnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 50, 90, -1));

        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("DNI: ");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 80, -1, -1));

        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Fecha Nac:");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 120, -1, -1));

        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("Telefono: ");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 160, -1, -1));

        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("Dirección: ");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 200, -1, -1));

        txtDni.setBackground(new java.awt.Color(102, 102, 102));
        txtDni.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDniActionPerformed(evt);
            }
        });
        jPanel1.add(txtDni, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 70, 260, 30));

        txtTelefono.setBackground(new java.awt.Color(102, 102, 102));
        txtTelefono.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTelefonoActionPerformed(evt);
            }
        });
        jPanel1.add(txtTelefono, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 150, 260, 30));

        txtDireccion.setBackground(new java.awt.Color(102, 102, 102));
        txtDireccion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDireccionActionPerformed(evt);
            }
        });
        jPanel1.add(txtDireccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 190, 260, 30));

        btnModificar.setText("Modificar");
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });
        jPanel1.add(btnModificar, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 90, 90, -1));

        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });
        jPanel1.add(btnEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 130, 90, -1));

        fechaNacimientoChooser.setBackground(new java.awt.Color(102, 102, 102));
        jPanel1.add(fechaNacimientoChooser, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 110, 260, 30));

        BtnLimpiar.setText("Limpiar");
        BtnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnLimpiarActionPerformed(evt);
            }
        });
        jPanel1.add(BtnLimpiar, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 170, 90, -1));

        tableCrudPaciente.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "id", "Nombre", "Dni", "fechaNac", "Edad", "Telefono"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tableCrudPaciente);
        if (tableCrudPaciente.getColumnModel().getColumnCount() > 0) {
            tableCrudPaciente.getColumnModel().getColumn(0).setResizable(false);
            tableCrudPaciente.getColumnModel().getColumn(1).setResizable(false);
            tableCrudPaciente.getColumnModel().getColumn(2).setResizable(false);
            tableCrudPaciente.getColumnModel().getColumn(3).setResizable(false);
            tableCrudPaciente.getColumnModel().getColumn(4).setResizable(false);
            tableCrudPaciente.getColumnModel().getColumn(5).setResizable(false);
        }

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 260, 620, 230));

        btnRegresar.setText("REGRESAR");
        btnRegresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegresarActionPerformed(evt);
            }
        });
        jPanel1.add(btnRegresar, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 0, -1, -1));

        jLabel7.setFont(new java.awt.Font("SansSerif", 2, 24)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setText("PACIENTES");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 140, 20));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/login.jpg"))); // NOI18N
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 800, 500));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 501, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreActionPerformed

    private void btnRegresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegresarActionPerformed
        new MenuObstetraVista().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnRegresarActionPerformed

    // MODIFICAR 

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
       if (idPacienteSeleccionado != -1) {
        if (fechaNacimientoChooser.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Selecciona la fecha de nacimiento.");
            return;
        }

        Paciente paciente = new Paciente();
        paciente.setId(idPacienteSeleccionado);
        paciente.setNombreCompleto(txtNombre.getText().trim().toUpperCase());
        paciente.setDni(txtDni.getText());
        paciente.setFechaNac(fechaNacimientoChooser.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        paciente.setTelefono(txtTelefono.getText());
        paciente.setDireccion(txtDireccion.getText().trim().toUpperCase());

        if (controlador.modificar(paciente)) {
            JOptionPane.showMessageDialog(this, "Paciente modificado.");
            cargarTablaPacientes();
            limpiarCampos();
        } else {
            JOptionPane.showMessageDialog(this, "Error al modificar.");
        }
    }
    }//GEN-LAST:event_btnModificarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        if (idPacienteSeleccionado != -1) {
            if (controlador.eliminar(idPacienteSeleccionado)) {
                JOptionPane.showMessageDialog(this, "Paciente eliminado");
                cargarTablaPacientes();
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar");
            }
        }

    }//GEN-LAST:event_btnEliminarActionPerformed

    private void BtnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnLimpiarActionPerformed
        limpiarCampos();

    }//GEN-LAST:event_BtnLimpiarActionPerformed

    private void txtDniActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDniActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDniActionPerformed

    private void txtTelefonoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTelefonoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTelefonoActionPerformed

    private void txtDireccionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDireccionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDireccionActionPerformed

    //GUARDAR

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        if (txtNombre.getText().isEmpty() || txtDni.getText().isEmpty()
                || fechaNacimientoChooser.getDate() == null || txtTelefono.getText().isEmpty()
                || txtDireccion.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Completa todos los campos.");
            return;
        }

        Paciente paciente = new Paciente();
        paciente.setNombreCompleto(txtNombre.getText().trim().toUpperCase());
        paciente.setDni(txtDni.getText());
        paciente.setFechaNac(fechaNacimientoChooser.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        paciente.setTelefono(txtTelefono.getText());
        paciente.setDireccion(txtDireccion.getText().trim().toUpperCase());

        if (controlador.registrar(paciente)) {
            JOptionPane.showMessageDialog(this, "Paciente registrado exitosamente.");
            cargarTablaPacientes();
            limpiarCampos();
        } else {
            JOptionPane.showMessageDialog(this, "Error al registrar paciente.");
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

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
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new GestionPacientesVista().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnLimpiar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnRegresar;
    private com.toedter.calendar.JDateChooser fechaNacimientoChooser;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable tableCrudPaciente;
    private javax.swing.JTextField txtDireccion;
    private javax.swing.JTextField txtDni;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtTelefono;
    // End of variables declaration//GEN-END:variables
}
