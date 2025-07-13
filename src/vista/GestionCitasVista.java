package vista;

import controlador.ControladorCita;
import modelo.Cita; // Para las constantes de estado
import modelo.Usuario; // Necesitas el objeto Usuario loggeado
import java.awt.Color; // Necesitas esto para cambiar el color del texto
import java.awt.event.FocusAdapter; // Para los listeners de foco
import java.awt.event.FocusEvent; // Para los listeners de foco
import java.awt.event.KeyAdapter; // Para los listeners de teclado
import java.awt.event.KeyEvent; // Para los listeners de teclado
import java.sql.Timestamp;
import java.util.LinkedHashMap;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap; // Para el mapa de programas
import java.util.List;
import java.util.Map; // Para el mapa de programas
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JTextField; // Importar JTextField
import javax.swing.table.DefaultTableModel;
import com.toedter.calendar.JDateChooser;
import utilidades.AppSesion;

public class GestionCitasVista extends javax.swing.JFrame {

    private final ControladorCita controladorCita = new ControladorCita();
    // Ya NO NECESITAS la variable de instancia 'usuarioLogueado' si siempre la obtienes de AppSesion
    // private Usuario usuarioLogueado; 
    private Map<Integer, String> programasMap;
    private Map<String, Integer> estadosFiltroMap;

    public GestionCitasVista() { // <-- Constructor sin parámetros
        // Ya no se asigna this.usuarioLogueado aquí.
        // Se obtendrá de AppSesion cuando se necesite.
        initComponents();
        this.setLocationRelativeTo(null);

        setPlaceholder(txtHoraCita, "HH:MM");

        // Cargar datos del obstetra directamente desde AppSesion
        cargarDatosObstetra();

        cargarProgramasPreventivos();
        inicializarEstadosFiltro();
        cargarFiltroEstados();
        cargarTablaCitas(-1);
        limpiarCampos();

        txtDniPaciente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtDniPacienteKeyReleased(evt);
            }
        });

        cmbFiltroEstadoCita.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbFiltroEstadoCitaActionPerformed(evt);
            }
        });

        // Listener para el JComboBox de filtro
        cmbFiltroEstadoCita.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbFiltroEstadoCitaActionPerformed(evt);
            }
        });
    }

    // NUEVO: Método para inicializar el mapa de estados de filtro
    private void inicializarEstadosFiltro() {
        estadosFiltroMap = new LinkedHashMap<>(); // Mantiene el orden de inserción
        estadosFiltroMap.put("Todas", -1); // Un valor especial para indicar "todas"
        estadosFiltroMap.put("Pendientes", Cita.ESTADO_PENDIENTE);
        estadosFiltroMap.put("Atendidas", Cita.ESTADO_ATENDIDO);
        estadosFiltroMap.put("Canceladas", Cita.ESTADO_CANCELADO);
    }

    // NUEVO: Método para cargar el JComboBox de filtro de estados
    private void cargarFiltroEstados() {
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        for (String estadoNombre : estadosFiltroMap.keySet()) {
            model.addElement(estadoNombre);
        }
        cmbFiltroEstadoCita.setModel(model);
        cmbFiltroEstadoCita.setSelectedItem("Todas");
    }

    // NUEVO MÉTODO para manejar el placeholder (añádelo dentro de la clase GestionCitasVista)
    private void setPlaceholder(final javax.swing.JTextField textField, final String placeholder) {
        // Establecer el texto inicial del placeholder
        textField.setText(placeholder);
        // Cambiar color para que parezca placeholder
        textField.setForeground(Color.GRAY); // Usa java.awt.Color

        // Añadir FocusListener para borrar el placeholder al ganar foco
        textField.addFocusListener(new FocusAdapter() { // Usa FocusAdapter
            @Override
            public void focusGained(FocusEvent evt) {
                if (textField.getText().equals(placeholder)) {
                    textField.setText("");
                    textField.setForeground(Color.BLACK); // Restaurar color de texto normal
                }
            }

            @Override
            public void focusLost(FocusEvent evt) {
                if (textField.getText().isEmpty()) {
                    textField.setText(placeholder);
                    textField.setForeground(Color.GRAY); // Restaurar color de placeholder
                }
            }
        });

        // Opcional: Para borrar al empezar a escribir incluso si no se ha perdido el foco antes
        textField.addKeyListener(new KeyAdapter() { // Usa KeyAdapter
            @Override
            public void keyTyped(KeyEvent evt) {
                if (textField.getText().equals(placeholder)) {
                    textField.setText("");
                    textField.setForeground(Color.BLACK);
                }
            }
        });
    }

    private void cargarDatosObstetra() {
        Usuario user = AppSesion.getInstance().getUsuarioLogueado(); // Obtiene el usuario de la sesión
        if (user != null) {
            lblObstetraLogeada.setText("Obstetra: " + user.getNombreCompleto());
        } else {
            lblObstetraLogeada.setText("Obstetra: No logueada");
            // Manejar error: quizás volver a login o mostrar un mensaje
        }
    }

    private void cargarProgramasPreventivos() {
        // Obtener programas del controlador
        programasMap = controladorCita.obtenerProgramasPreventivos();

        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        model.addElement("Seleccione un programa"); // Opción por defecto

        // Es importante que el programasMap contenga los nombres como claves
        // y los IDs como valores, o viceversa, según tu implementación.
        // Si tu programasMap es Map<Integer, String> (ID -> Nombre), debes iterar sobre los valores.
        // Si es Map<String, Integer> (Nombre -> ID), debes iterar sobre las claves.
        // Tu código actual asume Map<Integer, String> y usa .values() correctamente.
        for (String nombre : programasMap.values()) {
            model.addElement(nombre);
        }
        cmbProgramaPreventivo.setModel(model);
    }

    // MODIFICADO: Ahora este método acepta un parámetro de estado para filtrar
    private void cargarTablaCitas(int estado) {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.setColumnIdentifiers(new Object[]{"ID Cita", "Fecha Cita", "DNI Paciente", "Nombre Paciente", "Programa", "Estado", "Obstetra"});

        // Usar el nuevo método del controlador que trae los detalles por estado
        List<Map<String, Object>> citasConDetalles = controladorCita.listarCitasConDetalles(estado); // Pasa el estado aquí

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        for (Map<String, Object> citaDetalle : citasConDetalles) {
            String fechaFormateada = sdf.format((Timestamp) citaDetalle.get("fecha_cita"));
            String estadoString = "";
            int estadoInt = (int) citaDetalle.get("estado_cita");
            switch (estadoInt) {
                case Cita.ESTADO_PENDIENTE:
                    estadoString = "PENDIENTE";
                    break;
                case Cita.ESTADO_ATENDIDO:
                    estadoString = "ATENDIDO";
                    break;
                case Cita.ESTADO_CANCELADO:
                    estadoString = "CANCELADO";
                    break;
                default:
                    estadoString = "DESCONOCIDO";
                    break;
            }

            modelo.addRow(new Object[]{
                citaDetalle.get("id_cita"),
                fechaFormateada,
                citaDetalle.get("dni_paciente"),
                citaDetalle.get("nombre_paciente"),
                citaDetalle.get("nombre_programa_preventivo"),
                estadoString,
                citaDetalle.get("nombre_obstetra")
            });
        }
        tableCitas.setModel(modelo);
    }

    // NUEVO: Acción para el JComboBox de filtro
    private void cmbFiltroEstadoCitaActionPerformed(java.awt.event.ActionEvent evt) {
        String estadoSeleccionadoNombre = (String) cmbFiltroEstadoCita.getSelectedItem();
        Integer estadoFiltro = estadosFiltroMap.get(estadoSeleccionadoNombre);

        if (estadoFiltro != null) {
            cargarTablaCitas(estadoFiltro);
        } else {
            JOptionPane.showMessageDialog(this, "Error al aplicar filtro de estado.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarCampos() {
        txtDniPaciente.setText("");
        txtNombrePaciente.setText("");
        dateChooserFechaCita.setDate(new Date()); // Fecha actual

        // Restaurar el placeholder y el color para txtHoraCita
        txtHoraCita.setText("HH:MM");
        txtHoraCita.setForeground(Color.GRAY);

        cmbProgramaPreventivo.setSelectedIndex(0); // Seleccionar el primer programa ("Seleccione un programa")
        txtObservaciones.setText("");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblObstetraLogeada = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtDniPaciente = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtNombrePaciente = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        dateChooserFechaCita = new com.toedter.calendar.JDateChooser();
        jLabel5 = new javax.swing.JLabel();
        lblProgramaPrevent = new javax.swing.JLabel();
        cmbProgramaPreventivo = new javax.swing.JComboBox<>();
        lblObservaciones = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtObservaciones = new javax.swing.JTextArea();
        btnRegistrarCita = new javax.swing.JButton();
        btnMarcarAtendido = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        btnRegresar = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableCitas = new javax.swing.JTable();
        txtHoraCita = new javax.swing.JTextField();
        cmbFiltroEstadoCita = new javax.swing.JComboBox<>();
        btnCancelarCita = new javax.swing.JButton();
        fondo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        getContentPane().add(lblObstetraLogeada, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 220, 30));

        jLabel2.setBackground(new java.awt.Color(0, 0, 0));
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("DNI Paciente:");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 60, -1, -1));
        getContentPane().add(txtDniPaciente, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 50, 180, 30));

        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Nombre Paciente:");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 100, -1, -1));
        getContentPane().add(txtNombrePaciente, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 90, 180, 30));

        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Fecha de la cita:");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 140, -1, -1));
        getContentPane().add(dateChooserFechaCita, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 130, 180, -1));

        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("Hora de la cita:");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 180, -1, -1));

        lblProgramaPrevent.setForeground(new java.awt.Color(0, 0, 0));
        lblProgramaPrevent.setText("Programa preventivo:");
        getContentPane().add(lblProgramaPrevent, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 60, -1, -1));

        cmbProgramaPreventivo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        getContentPane().add(cmbProgramaPreventivo, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 50, 180, 30));

        lblObservaciones.setForeground(new java.awt.Color(0, 0, 0));
        lblObservaciones.setText("Observaciones:");
        getContentPane().add(lblObservaciones, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 100, -1, -1));

        txtObservaciones.setColumns(20);
        txtObservaciones.setRows(5);
        jScrollPane1.setViewportView(txtObservaciones);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 90, -1, 110));

        btnRegistrarCita.setText("Registrar Cita");
        btnRegistrarCita.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarCitaActionPerformed(evt);
            }
        });
        getContentPane().add(btnRegistrarCita, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 40, -1, -1));

        btnMarcarAtendido.setText("Marcar atendido");
        btnMarcarAtendido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMarcarAtendidoActionPerformed(evt);
            }
        });
        getContentPane().add(btnMarcarAtendido, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 70, -1, -1));

        btnLimpiar.setText("Limpiar");
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });
        getContentPane().add(btnLimpiar, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 110, -1, -1));

        btnRegresar.setText("Volver");
        btnRegresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegresarActionPerformed(evt);
            }
        });
        getContentPane().add(btnRegresar, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 140, -1, -1));

        tableCitas.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(tableCitas);

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 220, 640, 270));
        getContentPane().add(txtHoraCita, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 170, 180, 30));

        cmbFiltroEstadoCita.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        getContentPane().add(cmbFiltroEstadoCita, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 310, 90, -1));

        btnCancelarCita.setText("Cancelar cita");
        btnCancelarCita.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarCitaActionPerformed(evt);
            }
        });
        getContentPane().add(btnCancelarCita, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 260, -1, -1));

        fondo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/login.jpg"))); // NOI18N
        getContentPane().add(fondo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnRegistrarCitaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarCitaActionPerformed
        String dniPaciente = txtDniPaciente.getText().trim();
        Date fechaSeleccionada = dateChooserFechaCita.getDate();
        String horaMinutoStr = txtHoraCita.getText().trim(); // Obtener el texto completo "HH:MM"
        String programaNombre = (String) cmbProgramaPreventivo.getSelectedItem();
        String observaciones = txtObservaciones.getText().trim();

        // Validaciones iniciales de campos vacíos
        if (dniPaciente.isEmpty() || fechaSeleccionada == null || horaMinutoStr.isEmpty()
                || programaNombre == null || programaNombre.equals("Seleccione un programa") || observaciones.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe completar todos los campos obligatorios.", "Campos obligatorios", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Validar si el texto de la hora es el placeholder y no se ha modificado
        if (horaMinutoStr.equals("HH:MM")) {
            JOptionPane.showMessageDialog(this, "Debe ingresar la hora y los minutos de la cita.", "Campos obligatorios", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Validar y parsear la hora y el minuto del String "HH:MM"
        int hora;
        int minuto;

        // Regex para validar el formato HH:MM
        if (!horaMinutoStr.matches("^([01]?\\d|2[0-3]):([0-5]?\\d)$")) {
            JOptionPane.showMessageDialog(this, "El formato de la hora debe ser HH:MM (ej. 14:30 o 09:05).", "Formato de Hora Inválido", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            String[] partesHora = horaMinutoStr.split(":");
            hora = Integer.parseInt(partesHora[0]);
            minuto = Integer.parseInt(partesHora[1]);

            // Validación de rangos (aunque la regex ya ayuda mucho)
            if (hora < 0 || hora > 23 || minuto < 0 || minuto > 59) {
                JOptionPane.showMessageDialog(this, "La hora debe estar entre 0 y 23, y el minuto entre 0 y 59.", "Formato de Hora Inválido", JOptionPane.WARNING_MESSAGE);
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Error al parsear la hora y el minuto. Asegúrese de usar números.", "Formato de Hora Inválido", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int idPaciente = controladorCita.obtenerIdPacientePorDNI(dniPaciente);
        if (idPaciente == -1) {
            JOptionPane.showMessageDialog(this, "El DNI del paciente no está registrado.", "Paciente no encontrado", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Integer idProgramaInteger = null;
        for (Map.Entry<Integer, String> entry : programasMap.entrySet()) {
            if (entry.getValue().equals(programaNombre)) {
                idProgramaInteger = entry.getKey();
                break;
            }
        }

        if (idProgramaInteger == null) {
            JOptionPane.showMessageDialog(this, "El programa seleccionado no es válido o no se encontró su ID.", "Error de Programa", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int idPrograma = idProgramaInteger.intValue();

        // Combinar fecha, hora y minuto en un solo Timestamp
        Calendar cal = Calendar.getInstance();
        cal.setTime(fechaSeleccionada);

        cal.set(Calendar.HOUR_OF_DAY, hora);
        cal.set(Calendar.MINUTE, minuto);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        Timestamp fechaHoraCita = new Timestamp(cal.getTimeInMillis());

        // Obtener el ID del obstetra directamente de AppSesion
        Usuario obstetraLogueado = AppSesion.getInstance().getUsuarioLogueado();
        if (obstetraLogueado == null) {
            JOptionPane.showMessageDialog(this, "No hay un obstetra logueado. Inicie sesión nuevamente.", "Error de Sesión", JOptionPane.ERROR_MESSAGE);
            // new LoginVista().setVisible(true); // O redirigir a login
            // this.dispose();
            return;
        }

        // Crear objeto Cita
        Cita nuevaCita = new Cita();
        nuevaCita.setIdObstetra(obstetraLogueado.getId()); // Usar el ID del obstetra loggeado
        nuevaCita.setIdPaciente(idPaciente);
        nuevaCita.setFechaCita(fechaHoraCita);
        nuevaCita.setIdPrograma(idPrograma); // Usar ID del programa
        nuevaCita.setEstadoCita(Cita.ESTADO_PENDIENTE); // Estado por defecto (1)
        nuevaCita.setObservaciones(observaciones);

        if (controladorCita.registrarCita(nuevaCita)) {
            JOptionPane.showMessageDialog(this, "Cita registrada con éxito.", "Registro Exitoso", JOptionPane.INFORMATION_MESSAGE);
            cargarTablaCitas(Cita.ESTADO_PENDIENTE);
            limpiarCampos();
        } else {
            // Verificar si fue por cita duplicada
            if (controladorCita.yaTieneCitaEnMismoProgramaEsteAnio(idPaciente, idPrograma, fechaHoraCita)) {
                JOptionPane.showMessageDialog(this,
                        "⚠ El paciente ya tiene registrada una cita para este programa en el mismo año.",
                        "Cita duplicada",
                        JOptionPane.WARNING_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Error al registrar la cita.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnRegistrarCitaActionPerformed


    private void btnMarcarAtendidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMarcarAtendidoActionPerformed
        int filaSeleccionada = tableCitas.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una cita de la tabla para marcar como atendida.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idCita = (int) tableCitas.getValueAt(filaSeleccionada, 0); // Asume que la ID de la cita está en la primera columna
        int estadoActual = (int) controladorCita.obtenerEstadoCita(idCita); // Necesitarás crear este método en el controlador

        if (estadoActual == Cita.ESTADO_ATENDIDO) {
            JOptionPane.showMessageDialog(this, "Esta cita ya está marcada como ATENDIDA.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "¿Está seguro de marcar esta cita como ATENDIDA?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (controladorCita.modificarEstadoCita(idCita, Cita.ESTADO_ATENDIDO)) {
                JOptionPane.showMessageDialog(this, "Cita marcada como ATENDIDA.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                // Después de la acción, recargar la tabla con el filtro actual
                String estadoSeleccionadoNombre = (String) cmbFiltroEstadoCita.getSelectedItem();
                Integer estadoFiltro = estadosFiltroMap.get(estadoSeleccionadoNombre);
                cargarTablaCitas(estadoFiltro);
            } else {
                JOptionPane.showMessageDialog(this, "Error al marcar la cita como ATENDIDA.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnMarcarAtendidoActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        limpiarCampos();
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void btnRegresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegresarActionPerformed
        new MenuObstetraVista().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnRegresarActionPerformed

    private void btnCancelarCitaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarCitaActionPerformed
        int filaSeleccionada = tableCitas.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una cita de la tabla para cancelar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idCita = (int) tableCitas.getValueAt(filaSeleccionada, 0);
        int estadoActual = (int) controladorCita.obtenerEstadoCita(idCita); // Necesitarás crear este método en el controlador

        if (estadoActual == Cita.ESTADO_CANCELADO) {
            JOptionPane.showMessageDialog(this, "Esta cita ya está marcada como CANCELADA.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "¿Está seguro de CANCELAR esta cita?", "Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (confirm == JOptionPane.YES_OPTION) {
            if (controladorCita.modificarEstadoCita(idCita, Cita.ESTADO_CANCELADO)) { // Usar constante de estado (0)
                JOptionPane.showMessageDialog(this, "Cita CANCELADA con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                // Después de la acción, recargar la tabla con el filtro actual
                String estadoSeleccionadoNombre = (String) cmbFiltroEstadoCita.getSelectedItem();
                Integer estadoFiltro = estadosFiltroMap.get(estadoSeleccionadoNombre);
                cargarTablaCitas(estadoFiltro);
            } else {
                JOptionPane.showMessageDialog(this, "Error al cancelar la cita.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnCancelarCitaActionPerformed

    private void txtDniPacienteKeyReleased(java.awt.event.KeyEvent evt) {
        String dni = txtDniPaciente.getText().trim();
        // Opcional: Convertir DNI a mayúsculas si tu BD lo espera así
        // dni = dni.toUpperCase();

        if (dni.length() >= 8) { // Autocompletar cuando el DNI tiene una longitud razonable (ej. 8 para Perú)
            int idPaciente = controladorCita.obtenerIdPacientePorDNI(dni);
            if (idPaciente != -1) {
                txtNombrePaciente.setText(controladorCita.obtenerNombrePaciente(idPaciente));
            } else {
                txtNombrePaciente.setText("Paciente no encontrado");
            }
        } else if (dni.isEmpty()) {
            txtNombrePaciente.setText(""); // Limpiar si el DNI está vacío
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelarCita;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnMarcarAtendido;
    private javax.swing.JButton btnRegistrarCita;
    private javax.swing.JButton btnRegresar;
    private javax.swing.JComboBox<String> cmbFiltroEstadoCita;
    private javax.swing.JComboBox<String> cmbProgramaPreventivo;
    private com.toedter.calendar.JDateChooser dateChooserFechaCita;
    private javax.swing.JLabel fondo;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblObservaciones;
    private javax.swing.JLabel lblObstetraLogeada;
    private javax.swing.JLabel lblProgramaPrevent;
    private javax.swing.JTable tableCitas;
    private javax.swing.JTextField txtDniPaciente;
    private javax.swing.JTextField txtHoraCita;
    private javax.swing.JTextField txtNombrePaciente;
    private javax.swing.JTextArea txtObservaciones;
    // End of variables declaration//GEN-END:variables
}
