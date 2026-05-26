package proyectoed;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

/**
 * @authors Tomás Reyes, Juan Mateus, Santiago Rey, David Barbosa
 */
public class VentanaPrincipal extends JFrame {

    private Usuario usuarioActual; 
    private JLabel lblSaldoBilletera;
    private JLabel lblRachaVisual;
    private DefaultTableModel modeloHistorial;
    private DefaultTableModel modeloEncuentros;
    private DefaultTableModel modeloInscritos;
    private DefaultTableModel modeloTienda;

    private JTextField txtActividad, txtDescripcion, txtFecha, txtHora;
    private JTextField txtConceptoBono, txtMontoBono;

    private final Color AZUL_UNAB_TEC = new Color(0, 91, 171);
    private final Color BLANCO = new Color(255, 255, 255);
    private final Color NEGRO = new Color(0, 0, 0);
    private final Color GRIS_FONDO = new Color(245, 247, 250);

    public VentanaPrincipal(Usuario usuario) {
        this.usuarioActual = usuario;
        DunabCRUD.inicializarDatos();
        
        if (EncuentroCRUD.encuentros.isEmpty()) {
            EncuentroCRUD.encuentros = EncuentroCRUD.cargarEncuentros();
        }
        if (EncuentroCRUD.encuentros.isEmpty()) {
            EncuentroCRUD.encuentros.add(new Encuentro("Seminario de B-Trees", "Estructuras avanzadas", "2026-05-28", "14:00"));
            EncuentroCRUD.encuentros.add(new Encuentro("Taller de Electromagnetismo", "Simulación física", "2026-05-29", "10:00"));
        }
        
        setTitle("Sistema de Gestión de Puntos DUNAB - Plataforma Principal");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane pestañas = new JTabbedPane();
        pestañas.setFont(new Font("Arial", Font.BOLD, 13));

        pestañas.addTab("Perfil", crearPanelPerfil());
        pestañas.addTab("Billetera & Historial", crearPanelBilletera());
        pestañas.addTab("Encuentros (Inscripción)", crearPanelEncuentros());
        pestañas.addTab("Administrar Encuentros (CRUD)", crearPanelAdminEncuentros());
        pestañas.addTab("Tienda", crearPanelTienda());
        pestañas.addTab("Clan & Comunidad", crearPanelClan());
        pestañas.addTab("Ranking", crearPanelRanking());
        pestañas.addTab("Minijuegos", crearPanelMinijuegos());

        add(pestañas);
    }

    public VentanaPrincipal() {
        this(new Usuario("Estudiante de Prueba", "prueba@unab.edu.co", "Ingeniería de Sistemas", "123", 20));
    }

    private JPanel crearPanelPerfil() {
        JPanel panelPerfil = new JPanel(new BorderLayout());
        panelPerfil.setBackground(GRIS_FONDO);

        JPanel panelCabecera = new JPanel(new BorderLayout());
        panelCabecera.setBackground(AZUL_UNAB_TEC);
        panelCabecera.setBorder(new EmptyBorder(10, 25, 10, 25));

        JPanel panelIzquierdoCabecera = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 5));
        panelIzquierdoCabecera.setOpaque(false);

        try {
            java.net.URL urlLogo = getClass().getResource("/proyectoed/imágenes/Logo UNAB.png");
            if (urlLogo != null) {
                ImageIcon logoOriginal = new ImageIcon(urlLogo);
                Image logoEscalado = logoOriginal.getImage().getScaledInstance(110, 38, Image.SCALE_SMOOTH);
                JLabel lblLogoUnab = new JLabel(new ImageIcon(logoEscalado));
                panelIzquierdoCabecera.add(lblLogoUnab);
            }
        } catch (Exception e) {
            System.out.println("No se pudo cargar el logo en la cabecera: " + e.getMessage());
        }

        JLabel lblTitulo = new JLabel("Tu Perfil DUNAB");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitulo.setForeground(BLANCO);
        panelIzquierdoCabecera.add(lblTitulo);
        
        panelCabecera.add(panelIzquierdoCabecera, BorderLayout.WEST);

        lblRachaVisual = new JLabel("🔥 Racha: " + usuarioActual.getRachaDias() + " días ", JLabel.RIGHT);
        lblRachaVisual.setFont(new Font("Arial", Font.BOLD, 16));
        lblRachaVisual.setForeground(new Color(255, 204, 0)); 
        panelCabecera.add(lblRachaVisual, BorderLayout.EAST);

        panelPerfil.add(panelCabecera, BorderLayout.NORTH);

        JPanel panelCuerpo = new JPanel(new GridBagLayout());
        panelCuerpo.setBackground(BLANCO);
        panelCuerpo.setBorder(BorderFactory.createCompoundBorder(
                new EmptyBorder(30, 40, 30, 40),
                BorderFactory.createLineBorder(new Color(220, 224, 230), 1, true)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 15, 10, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        gbc.gridheight = 4; 
        gbc.anchor = GridBagConstraints.CENTER;
        
        JLabel lblFotoPerfil = new JLabel();
        lblFotoPerfil.setPreferredSize(new Dimension(150, 150));
        lblFotoPerfil.setBorder(BorderFactory.createLineBorder(AZUL_UNAB_TEC, 3, true));
        
        try {
            java.net.URL urlFoto = getClass().getResource("/proyectoed/imágenes/sonic.jpg");
            if (urlFoto != null) {
                ImageIcon fotoOriginal = new ImageIcon(urlFoto);
                Image imgEscalada = fotoOriginal.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
                lblFotoPerfil.setIcon(new ImageIcon(imgEscalada));
            } else {
                lblFotoPerfil.setText("sonic.jpg");
                lblFotoPerfil.setFont(new Font("Arial", Font.BOLD, 14));
                lblFotoPerfil.setHorizontalAlignment(JLabel.CENTER);
                lblFotoPerfil.setBackground(new Color(230, 240, 255));
                lblFotoPerfil.setOpaque(true);
            }
        } catch (Exception e) {
            System.out.println("Error al cargar sonic.jpg: " + e.getMessage());
        }
        panelCuerpo.add(lblFotoPerfil, gbc);

        gbc.gridheight = 1; 
        gbc.weightx = 1.0;

        gbc.gridx = 1; gbc.gridy = 0;
        JLabel lblNombre = new JLabel(usuarioActual.getNombres().toUpperCase());
        lblNombre.setFont(new Font("Arial", Font.BOLD, 24));
        lblNombre.setForeground(AZUL_UNAB_TEC);
        panelCuerpo.add(lblNombre, gbc);

        gbc.gridx = 1; gbc.gridy = 1;
        JLabel lblCorreo = new JLabel("📧 Correo: " + usuarioActual.getCorreoUnab());
        lblCorreo.setFont(new Font("Arial", Font.PLAIN, 15));
        panelCuerpo.add(lblCorreo, gbc);

        gbc.gridx = 1; gbc.gridy = 2;
        JLabel lblCarrera = new JLabel("🎓 Programa: " + usuarioActual.getCarrera());
        lblCarrera.setFont(new Font("Arial", Font.PLAIN, 15));
        panelCuerpo.add(lblCarrera, gbc);

        gbc.gridx = 1; gbc.gridy = 3;
        JLabel lblEdad = new JLabel("🎂 Edad: " + usuarioActual.getEdad() + " años");
        lblEdad.setFont(new Font("Arial", Font.PLAIN, 15));
        panelCuerpo.add(lblEdad, gbc);

        panelPerfil.add(panelCuerpo, BorderLayout.CENTER);

        JPanel panelEstadisticas = new JPanel(new GridLayout(1, 3, 15, 0));
        panelEstadisticas.setBackground(GRIS_FONDO);
        panelEstadisticas.setBorder(new EmptyBorder(10, 40, 30, 40));

        panelEstadisticas.add(crearTarjetaMetrica("Comunidad", "67 Amigos Activos", "👥"));
        panelEstadisticas.add(crearTarjetaMetrica("Rango Académico", "Estudiante Regular", "🛡️"));
        panelEstadisticas.add(crearTarjetaMetrica("Clan Oficial", "Ing. de la Recocha", "⚔️"));

        panelPerfil.add(panelEstadisticas, BorderLayout.SOUTH);

        return panelPerfil;
    }

    private JPanel crearPanelBilletera() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(GRIS_FONDO);

        JPanel panelTop = new JPanel(new GridLayout(1, 2));
        
        JPanel panelSaldo = new JPanel(new GridLayout(2, 1));
        panelSaldo.setBackground(AZUL_UNAB_TEC);
        panelSaldo.setBorder(new EmptyBorder(20, 30, 20, 30));

        JLabel lblT = new JLabel("SALDO DISPONIBLE", JLabel.CENTER);
        lblT.setFont(new Font("Arial", Font.PLAIN, 14));
        lblT.setForeground(BLANCO);

        lblSaldoBilletera = new JLabel(DunabCRUD.saldoDunab + " DUNAB", JLabel.CENTER);
        lblSaldoBilletera.setFont(new Font("Arial", Font.BOLD, 36));
        lblSaldoBilletera.setForeground(new Color(255, 204, 0));

        panelSaldo.add(lblT);
        panelSaldo.add(lblSaldoBilletera);
        panelTop.add(panelSaldo);

        JPanel panelCrudDunab = new JPanel(new GridLayout(3, 2, 8, 8));
        panelCrudDunab.setBackground(BLANCO);
        panelCrudDunab.setBorder(BorderFactory.createTitledBorder("Módulo Administrativo DUNAB (CRUD)"));
        
        txtConceptoBono = new JTextField();
        txtMontoBono = new JTextField();
        JButton btnInyectarPuntos = new JButton("Inyectar Puntos");
        btnInyectarPuntos.setBackground(new Color(40, 167, 69));
        btnInyectarPuntos.setForeground(BLANCO);
        
        panelCrudDunab.add(new JLabel("  Concepto / Motivo:"));
        panelCrudDunab.add(txtConceptoBono);
        panelCrudDunab.add(new JLabel("  Cantidad DUNAB:"));
        panelCrudDunab.add(txtMontoBono);
        panelCrudDunab.add(new JLabel(""));
        panelCrudDunab.add(btnInyectarPuntos);
        
        btnInyectarPuntos.addActionListener(e -> {
            try {
                String concepto = txtConceptoBono.getText().trim();
                int monto = Integer.parseInt(txtMontoBono.getText().trim());
                if(concepto.isEmpty()) throw new IllegalArgumentException();
                
                DunabCRUD.saldoDunab += monto;
                DunabCRUD.historialTransacciones.add(new Transaccion(concepto, monto, "2026-05-26", "INGRESO"));
                
                lblSaldoBilletera.setText(DunabCRUD.saldoDunab + " DUNAB");
                actualizarTablaHistorial();
                txtConceptoBono.setText("");
                txtMontoBono.setText("");
                JOptionPane.showMessageDialog(this, "Saldo modificado correctamente en la base de datos DUNAB.");
            } catch(Exception ex) {
                JOptionPane.showMessageDialog(this, "Campos inválidos. Introduce un concepto válido y un monto numérico.", "Error CRUD", JOptionPane.ERROR_MESSAGE);
            }
        });
        panelTop.add(panelCrudDunab);
        
        panel.add(panelTop, BorderLayout.NORTH);

        JPanel panelTabla = new JPanel(new BorderLayout());
        panelTabla.setBorder(new EmptyBorder(15, 20, 20, 20));
        panelTabla.setBackground(GRIS_FONDO);

        JLabel lblHist = new JLabel("Historial del Estado de Cuenta (Transacciones)", JLabel.LEFT);
        lblHist.setFont(new Font("Arial", Font.BOLD, 15));
        lblHist.setBorder(new EmptyBorder(0, 0, 10, 0));
        panelTabla.add(lblHist, BorderLayout.NORTH);

        String[] columnas = {"Concepto / Motivo", "Monto", "Fecha", "Tipo de Movimiento"};
        modeloHistorial = new DefaultTableModel(columnas, 0);
        JTable tablaHistorial = new JTable(modeloHistorial);
        actualizarTablaHistorial();

        panelTabla.add(new JScrollPane(tablaHistorial), BorderLayout.CENTER);
        panel.add(panelTabla, BorderLayout.CENTER);

        return panel;
    }

    private JPanel crearPanelEncuentros() {
        JPanel panel = new JPanel(new GridLayout(2, 1, 10, 15));
        panel.setBackground(GRIS_FONDO);
        panel.setBorder(new EmptyBorder(15, 20, 15, 20));

        JPanel panelDisp = new JPanel(new BorderLayout());
        panelDisp.setBackground(GRIS_FONDO);
        JLabel lblD = new JLabel("Encuentros Disponibles en el Ecosistema UNAB", JLabel.LEFT);
        lblD.setFont(new Font("Arial", Font.BOLD, 14));
        panelDisp.add(lblD, BorderLayout.NORTH);

        String[] colDisp = {"#", "Actividad Académica", "Descripción / Detalles", "Fecha Evento", "Hora Programada"};
        modeloEncuentros = new DefaultTableModel(colDisp, 0);
        JTable tablaDisp = new JTable(modeloEncuentros);
        actualizarTablaEncuentros();
        panelDisp.add(new JScrollPane(tablaDisp), BorderLayout.CENTER);

        JButton btnInscribir = new JButton("Inscribirse y Agendar Encuentro Seleccionado");
        btnInscribir.setBackground(AZUL_UNAB_TEC);
        btnInscribir.setForeground(BLANCO);
        btnInscribir.addActionListener(e -> {
            int fila = tablaDisp.getSelectedRow();
            if (fila >= 0) {
                Encuentro enc = EncuentroCRUD.encuentros.get(fila);
                
                boolean yaInscrito = false;
                for (Encuentro inscrito : DunabCRUD.encuentrosInscritos) {
                    if (inscrito.getActividad().equals(enc.getActividad()) && inscrito.getFecha().equals(enc.getFecha())) {
                        yaInscrito = true;
                        break;
                    }
                }

                if (!yaInscrito) {
                    DunabCRUD.encuentrosInscritos.add(enc);
                    DunabCRUD.saldoDunab += 250;
                    DunabCRUD.historialTransacciones.add(new Transaccion("Asistencia: " + enc.getActividad(), 250, enc.getFecha(), "INGRESO"));
                    
                    lblSaldoBilletera.setText(DunabCRUD.saldoDunab + " DUNAB");
                    actualizarTablaInscritos();
                    actualizarTablaHistorial();
                    JOptionPane.showMessageDialog(this, "¡Inscripción confirmada! Se han abonado +250 DUNAB a tu billetera.");
                } else {
                    JOptionPane.showMessageDialog(this, "Ya te encuentras registrado en esta actividad.", "Registro Duplicado", JOptionPane.WARNING_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Por favor, seleccione una actividad de la tabla.");
            }
        });
        panelDisp.add(btnInscribir, BorderLayout.SOUTH);

        JPanel panelInsc = new JPanel(new BorderLayout());
        panelInsc.setBackground(GRIS_FONDO);
        JLabel lblI = new JLabel("Mis Encuentros Confirmados (Mi Agenda Personal)", JLabel.LEFT);
        lblI.setFont(new Font("Arial", Font.BOLD, 14));
        panelInsc.add(lblI, BorderLayout.NORTH);

        String[] colInsc = {"Actividad", "Fecha", "Hora"};
        modeloInscritos = new DefaultTableModel(colInsc, 0);
        JTable tablaInsc = new JTable(modeloInscritos);
        panelInsc.add(new JScrollPane(tablaInsc), BorderLayout.CENTER);

        panel.add(panelDisp);
        panel.add(panelInsc);
        return panel;
    }

    private JPanel crearPanelAdminEncuentros() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(GRIS_FONDO);
        panel.setBorder(new EmptyBorder(15, 20, 15, 20));

        JPanel panelForm = new JPanel(new GridLayout(5, 2, 8, 8));
        panelForm.setBackground(BLANCO);
        panelForm.setBorder(BorderFactory.createTitledBorder("Registrar / Modificar Encuentros"));

        txtActividad = new JTextField();
        txtDescripcion = new JTextField();
        txtFecha = new JTextField();
        txtHora = new JTextField();

        panelForm.add(new JLabel("  Nombre de Actividad:")); panelForm.add(txtActividad);
        panelForm.add(new JLabel("  Descripción:")); panelForm.add(txtDescripcion);
        panelForm.add(new JLabel("  Fecha (AAAA-MM-DD):")); panelForm.add(txtFecha);
        panelForm.add(new JLabel("  Hora (HH:MM):")); panelForm.add(txtHora);

        JPanel panelBotones = new JPanel(new GridLayout(1, 3, 10, 0));
        panelBotones.setBackground(GRIS_FONDO);
        
        JButton btnGuardar = new JButton("Crear/Guardar");
        btnGuardar.setBackground(AZUL_UNAB_TEC); btnGuardar.setForeground(BLANCO);
        
        JButton btnEliminar = new JButton("Eliminar Seleccionado");
        btnEliminar.setBackground(new Color(220, 53, 69)); btnEliminar.setForeground(BLANCO);

        panelBotones.add(btnGuardar);
        panelBotones.add(btnEliminar);

        panelForm.add(new JLabel("")); panelForm.add(panelBotones);

        String[] colDisp = {"#", "Actividad Académica", "Descripción / Detalles", "Fecha Evento", "Hora Programada"};
        DefaultTableModel modeloAdmin = new DefaultTableModel(colDisp, 0);
        JTable tablaAdmin = new JTable(modeloAdmin);
        
        tablaAdmin.getSelectionModel().addListSelectionListener(e -> {
            int fila = tablaAdmin.getSelectedRow();
            if (fila >= 0 && fila < EncuentroCRUD.encuentros.size()) {
                Encuentro enc = EncuentroCRUD.encuentros.get(fila);
                txtActividad.setText(enc.getActividad());
                txtDescripcion.setText(enc.getDescripcion());
                txtFecha.setText(enc.getFecha());
                txtHora.setText(enc.getHora());
            }
        });

        Runnable refrescarAdminTabla = () -> {
            modeloAdmin.setRowCount(0);
            int cont = 1;
            for(Encuentro e : EncuentroCRUD.encuentros) {
                modeloAdmin.addRow(new Object[]{cont++, e.getActividad(), e.getDescripcion(), e.getFecha(), e.getHora()});
            }
            actualizarTablaEncuentros(); 
        };

        btnGuardar.addActionListener(e -> {
            String act = txtActividad.getText().trim();
            String desc = txtDescripcion.getText().trim();
            String fec = txtFecha.getText().trim();
            String hor = txtHora.getText().trim();

            if(act.isEmpty() || desc.isEmpty() || fec.isEmpty() || hor.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Diligencie todos los campos.", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int fila = tablaAdmin.getSelectedRow();
            if (fila >= 0) {
                Encuentro enc = EncuentroCRUD.encuentros.get(fila);
                enc.setActividad(act); enc.setDescripcion(desc); enc.setFecha(fec); enc.setHora(hor);
                JOptionPane.showMessageDialog(this, "Encuentro modificado con éxito.");
            } else {
                EncuentroCRUD.encuentros.add(new EncounterAdapter(act, desc, fec, hor));
                JOptionPane.showMessageDialog(this, "Nuevo encuentro registrado.");
            }
            
            EncuentroCRUD.guardarEncuentros();
            refrescarAdminTabla.run();
            
            txtActividad.setText(""); txtDescripcion.setText(""); txtFecha.setText(""); txtHora.setText("");
            tablaAdmin.clearSelection();
        });

        btnEliminar.addActionListener(e -> {
            int fila = tablaAdmin.getSelectedRow();
            if (fila >= 0) {
                EncuentroCRUD.encuentros.remove(fila);
                EncuentroCRUD.guardarEncuentros();
                refrescarAdminTabla.run();
                txtActividad.setText(""); txtDescripcion.setText(""); txtFecha.setText(""); txtHora.setText("");
                JOptionPane.showMessageDialog(this, "Encuentro eliminado del archivo serializado.");
            } else {
                JOptionPane.showMessageDialog(this, "Seleccione un registro de la tabla.");
            }
        });

        panel.add(panelForm, BorderLayout.NORTH);
        panel.add(new JScrollPane(tablaAdmin), BorderLayout.CENTER);
        
        int cont = 1;
        for(Encuentro e : EncuentroCRUD.encuentros) {
            modeloAdmin.addRow(new Object[]{cont++, e.getActividad(), e.getDescripcion(), e.getFecha(), e.getHora()});
        }

        return panel;
    }

    private JPanel crearPanelTienda() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(GRIS_FONDO);
        panel.setBorder(new EmptyBorder(15, 20, 15, 20));

        JLabel lblT = new JLabel("Tienda de Beneficios y Canjes Universitarios", JLabel.LEFT);
        lblT.setFont(new Font("Arial", Font.BOLD, 15));
        lblT.setBorder(new EmptyBorder(0, 0, 10, 0));
        panel.add(lblT, BorderLayout.NORTH);

        String[] columnas = {"Artículo Recompensa", "Costo (DUNAB)", "Categoría", "Stock Disponible"};
        modeloTienda = new DefaultTableModel(columnas, 0);
        JTable tablaTienda = new JTable(modeloTienda);
        actualizarTablaTienda();
        panel.add(new JScrollPane(tablaTienda), BorderLayout.CENTER);

        JButton btnComprar = new JButton("Redimir / Comprar Artículo");
        btnComprar.setBackground(new Color(40, 167, 69));
        btnComprar.setForeground(BLANCO);
        btnComprar.setFont(new Font("Arial", Font.BOLD, 14));
        btnComprar.addActionListener(e -> {
            int fila = tablaTienda.getSelectedRow();
            if (fila >= 0) {
                Producto prod = DunabCRUD.inventarioTienda.get(fila);
                if (DunabCRUD.saldoDunab >= prod.getCosto()) {
                    if (prod.getStock() > 0) {
                        prod.setStock(prod.getStock() - 1);
                        DunabCRUD.saldoDunab -= prod.getCosto();
                        DunabCRUD.historialTransacciones.add(new Transaccion("Canje: " + prod.getNombre(), prod.getCosto(), "2026-05-26", "GASTO"));
                        
                        lblSaldoBilletera.setText(DunabCRUD.saldoDunab + " DUNAB");
                        actualizarTablaTienda();
                        actualizarTablaHistorial();
                        JOptionPane.showMessageDialog(this, "¡Canje Procesado! Descarga tu cupón digital en la oficina de Bienestar.");
                    } else {
                        JOptionPane.showMessageDialog(this, "Este artículo se encuentra agotado temporalmente.", "Sin Stock", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "No posees suficientes puntos DUNAB para este artículo.", "Saldo Insuficiente", JOptionPane.WARNING_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Seleccione un producto del catálogo para redimir.");
            }
        });
        panel.add(btnComprar, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel crearPanelClan() {
        JPanel panel = new JPanel(new GridLayout(1, 2, 15, 0));
        panel.setBackground(GRIS_FONDO);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel panelIzq = new JPanel(new BorderLayout());
        panelIzq.setBackground(BLANCO);
        panelIzq.setBorder(BorderFactory.createLineBorder(new Color(220, 224, 230), 1));
        JLabel lblC = new JLabel("🛡️ Clan de Estudio Oficial: Ing. de la Recocha", JLabel.CENTER);
        lblC.setFont(new Font("Arial", Font.BOLD, 14));
        lblC.setForeground(AZUL_UNAB_TEC);
        panelIzq.add(lblC, BorderLayout.NORTH);
        
        DefaultListModel<String> modeloClan = new DefaultListModel<>();
        modeloClan.addElement("Tomás Reyes (Líder) - 4500 pts");
        modeloClan.addElement("Juan Mateus - 3200 pts");
        modeloClan.addElement("Santiago Rey - 2900 pts");
        modeloClan.addElement("David Barbosa - 2750 pts");
        modeloClan.addElement(usuarioActual.getNombres() + " (Tú) - " + DunabCRUD.saldoDunab + " pts");
        JList<String> listaClan = new JList<>(modeloClan);
        panelIzq.add(new JScrollPane(listaClan), BorderLayout.CENTER);

        JPanel panelDer = new JPanel(new BorderLayout());
        panelDer.setBackground(BLANCO);
        panelDer.setBorder(BorderFactory.createLineBorder(new Color(220, 224, 230), 1));
        JLabel lblA = new JLabel("👥 Mis Amigos de la Facultad (Comunidad)", JLabel.CENTER);
        lblA.setFont(new Font("Arial", Font.BOLD, 14));
        panelDer.add(lblA, BorderLayout.NORTH);

        DefaultListModel<String> modeloAmigos = new DefaultListModel<>();
        for(int i = 1; i <= 8; i++) {
            modeloAmigos.addElement("Estudiante de Ingeniería Metropolitano #" + i + " - Conectado hace poco");
        }
        JList<String> listaAmigos = new JList<>(modeloAmigos);
        panelDer.add(new JScrollPane(listaAmigos), BorderLayout.CENTER);

        panel.add(panelIzq);
        panel.add(panelDer);
        return panel;
    }

    private JPanel crearPanelRanking() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(GRIS_FONDO);
        panel.setBorder(new EmptyBorder(15, 20, 15, 20));

        JLabel lblT = new JLabel("🏆 Top Clasificación General de la Facultad de Ingeniería", JLabel.LEFT);
        lblT.setFont(new Font("Arial", Font.BOLD, 15));
        lblT.setBorder(new EmptyBorder(0, 0, 10, 0));
        panel.add(lblT, BorderLayout.NORTH);

        String[] columnas = {"Posición", "Nombre Completo", "Programa Académico", "Puntos DUNAB Totales"};
        DefaultTableModel modeloRank = new DefaultTableModel(columnas, 0);
        
        modeloRank.addRow(new Object[]{"1", "Tomás Reyes", "Ingeniería de Sistemas", "4500"});
        modeloRank.addRow(new Object[]{"2", "Juan Mateus", "Ingeniería de Sistemas", "3200"});
        modeloRank.addRow(new Object[]{"3", "Santiago Rey", "Ingeniería de Sistemas", "2900"});
        modeloRank.addRow(new Object[]{"4", "David Barbosa", "Ingeniería de Sistemas", "2750"});
        modeloRank.addRow(new Object[]{"5", usuarioActual.getNombres(), usuarioActual.getCarrera(), String.valueOf(DunabCRUD.saldoDunab)});

        JTable tablaRank = new JTable(modeloRank);
        panel.add(new JScrollPane(tablaRank), BorderLayout.CENTER);
        return panel;
    }

    private JPanel crearPanelMinijuegos() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(BLANCO);
        panel.setBorder(new EmptyBorder(30, 40, 30, 40));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.BOTH;

        JLabel lblT = new JLabel("🎮 Zona de Retos Rápidos: Genera DUNAB de Emergencia", JLabel.CENTER);
        lblT.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(lblT, gbc);

        JButton btnTrivia = new JButton("Trivia Diaria de Algoritmos (+150 DUNAB)");
        btnTrivia.setBackground(AZUL_UNAB_TEC);
        btnTrivia.setForeground(BLANCO);
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 1; gbc.weightx = 0.5; gbc.weighty = 0.3;
        btnTrivia.addActionListener(e -> {
            String resp = JOptionPane.showInputDialog(this, "¿Cuál es la complejidad temporal en el peor de los casos de una búsqueda binaria?\nA) O(1)\nB) O(n)\nC) O(log n)", "Trivia DUNAB", JOptionPane.QUESTION_MESSAGE);
            if (resp != null && resp.equalsIgnoreCase("C")) {
                DunabCRUD.saldoDunab += 150;
                DunabCRUD.historialTransacciones.add(new Transaccion("Trivia Algoritmos Ganada", 150, "2026-05-26", "INGRESO"));
                lblSaldoBilletera.setText(DunabCRUD.saldoDunab + " DUNAB");
                actualizarTablaHistorial();
                JOptionPane.showMessageDialog(this, "¡Correcto! Has sumado 150 DUNAB.");
            } else if (resp != null) {
                JOptionPane.showMessageDialog(this, "Incorrecto. Repasa tus apuntes de árboles de decisión.", "Fallo", JOptionPane.ERROR_MESSAGE);
            }
        });
        panel.add(btnTrivia, gbc);

        JButton btnBanu = new JButton("Encuentra a Banú (Suerte) (+100 DUNAB)");
        btnBanu.setBackground(AZUL_UNAB_TEC);
        btnBanu.setForeground(BLANCO);
        gbc.gridx = 1; gbc.gridy = 1;
        btnBanu.addActionListener(e -> {
            int opcion = JOptionPane.showOptionDialog(this, "¿En qué aula de Bienestar se esconde la mascota Banú?", "Encuentra a Banú", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, new String[]{"Aula 101", "Aula 202", "Coliseo"}, "Aula 101");
            if (opcion == 2) {
                DunabCRUD.saldoDunab += 100;
                DunabCRUD.historialTransacciones.add(new Transaccion("Encontraste a Banú 🐾", 100, "2026-05-26", "INGRESO"));
                lblSaldoBilletera.setText(DunabCRUD.saldoDunab + " DUNAB");
                actualizarTablaHistorial();
                JOptionPane.showMessageDialog(this, "¡Lo encontraste jugando baloncesto! Ganaste 100 DUNAB.");
            } else if (opcion >= 0) {
                JOptionPane.showMessageDialog(this, "El aula está vacía. ¡Sigue buscando!");
            }
        });
        panel.add(btnBanu, gbc);

        return panel;
    }

    private void actualizarTablaHistorial() {
        modeloHistorial.setRowCount(0);
        for (Transaccion t : DunabCRUD.historialTransacciones) {
            modeloHistorial.addRow(new Object[]{t.getConcepto(), t.getCantidad() + " DUNAB", t.getFecha(), t.getTipo()});
        }
    }

    private void actualizarTablaEncuentros() {
        if(modeloEncuentros != null) {
            modeloEncuentros.setRowCount(0);
            int contador = 1;
            for (Encuentro e : EncuentroCRUD.encuentros) {
                modeloEncuentros.addRow(new Object[]{contador++, e.getActividad(), e.getDescripcion(), e.getFecha(), e.getHora()});
            }
        }
    }

    private void actualizarTablaInscritos() {
        modeloInscritos.setRowCount(0);
        for (Encuentro e : DunabCRUD.encuentrosInscritos) {
            modeloInscritos.addRow(new Object[]{e.getActividad(), e.getFecha(), e.getHora()});
        }
    }

    private void actualizarTablaTienda() {
        modeloTienda.setRowCount(0);
        for (Producto p : DunabCRUD.inventarioTienda) {
            modeloTienda.addRow(new Object[]{p.getNombre(), p.getCosto() + " DUNAB", p.getCategoria(), p.getStock()});
        }
    }

    private JComponent crearTarjetaMetrica(String titulo, String valor, String icono) {
        JPanel tarjeta = new JPanel(new BorderLayout(5, 5));
        tarjeta.setBackground(BLANCO);
        tarjeta.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(225, 229, 235), 1),
                new EmptyBorder(12, 15, 12, 15)
        ));
        JLabel lblIcono = new JLabel(icono);
        lblIcono.setFont(new Font("Arial", Font.PLAIN, 24));
        tarjeta.add(lblIcono, BorderLayout.WEST);
        JPanel panelTextos = new JPanel(new GridLayout(2, 1));
        panelTextos.setBackground(BLANCO);
        JLabel lblT = new JLabel(titulo);
        lblT.setFont(new Font("Arial", Font.BOLD, 12));
        lblT.setForeground(Color.GRAY);
        JLabel lblV = new JLabel(valor);
        lblV.setFont(new Font("Arial", Font.BOLD, 14));
        lblV.setForeground(NEGRO);
        panelTextos.add(lblT);
        panelTextos.add(lblV);
        tarjeta.add(panelTextos, BorderLayout.CENTER);
        return tarjeta;
    }

    private static class EncounterAdapter extends Encuentro {
        public EncounterAdapter(String a, String d, String f, String h) {
            super(a, d, f, h);
        }
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            new VentanaPrincipal().setVisible(true);
        });
    }
}