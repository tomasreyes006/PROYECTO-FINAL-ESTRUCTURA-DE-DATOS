package proyectoed.pantallas;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import proyectoed.tda.PilaEnlazada;
import proyectoed.utilidades.GestorPersistencia;

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
    private DefaultTableModel modeloTrueques;

    private JTextField txtActividad, txtDescripcion, txtFecha, txtHora;
    private JTextField txtConceptoBono, txtMontoBono;
    private JTextField txtOfrecidoTrueque, txtSolicitadoTrueque;

    private final Color AZUL_UNAB_TEC = new Color(0, 91, 171);
    private final Color BLANCO = new Color(255, 255, 255);
    private final Color NEGRO = new Color(0, 0, 0);

    private List<Trueque> listaTrueques = new ArrayList<>();
    private boolean modoOscuroActivado = false;
    
    private List<JPanel> panelesConmutables = new ArrayList<>();
    private List<JLabel> etiquetasTextoPerfil = new ArrayList<>();
    
    private JPanel panelCuerpoPerfil;
    private JLabel lblNombre, lblCorreo, lblCarrera, lblEdad;
    private JComponent tarjeta1, tarjeta2, tarjeta3;

    private JToggleButton btnModoOscuro;
    private JPanel panelCabecera; 

    public VentanaPrincipal(Usuario usuario) {
        this.usuarioActual = usuario;
        GestorPersistencia.cargarTodo();
        
        setTitle("Sistema de Gestión de Puntos DUNAB - Plataforma Principal");
        setSize(1000, 720);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                GestorPersistencia.guardarTodo();
                System.exit(0);
            }
        });

        JTabbedPane pestañas = new JTabbedPane();
        pestañas.setFont(new Font("Arial", Font.BOLD, 13));

        pestañas.addTab("Perfil", crearPanelPerfil());
        pestañas.addTab("Billetera & Historial", crearPanelBilletera());
        pestañas.addTab("Encuentros (Inscripción)", crearPanelEncuentros());
        pestañas.addTab("Administrar Encuentros (CRUD)", crearPanelAdminEncuentros());
        pestañas.addTab("Tienda", crearPanelTienda());
        pestañas.addTab("Trueque DUNAB", crearPanelTrueques());
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
        panelPerfil.setBackground(Color.WHITE);
        panelesConmutables.add(panelPerfil);

        panelCabecera = new JPanel(new BorderLayout());
        panelCabecera.setBackground(AZUL_UNAB_TEC);
        panelCabecera.setBorder(new EmptyBorder(15, 25, 15, 25));

        JPanel panelIzquierdoCabecera = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 5));
        panelIzquierdoCabecera.setOpaque(false);

        JLabel lblTitulo = new JLabel("Tu Perfil DUNAB");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitulo.setForeground(BLANCO);
        panelIzquierdoCabecera.add(lblTitulo);
        
        btnModoOscuro = new JToggleButton("Modo Oscuro 🌙");
        btnModoOscuro.setFont(new Font("Arial", Font.BOLD, 12));
        btnModoOscuro.addActionListener(e -> btnModoOscuroActionPerformed(e));
        panelIzquierdoCabecera.add(btnModoOscuro);
        
        panelCabecera.add(panelIzquierdoCabecera, BorderLayout.WEST);

        JPanel panelDerechoCabecera = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 5));
        panelDerechoCabecera.setOpaque(false);

        lblRachaVisual = new JLabel("🔥 Racha: " + usuarioActual.getRachaDias() + " días ");
        lblRachaVisual.setFont(new Font("Arial", Font.BOLD, 16));
        lblRachaVisual.setForeground(new Color(255, 204, 0)); 
        panelDerechoCabecera.add(lblRachaVisual);

        JLabel lblLogoUnab = new JLabel();
        try {
            java.net.URL urlLogo = getClass().getResource("/proyectoed/imágenes/Logo UNAB.png");
            if (urlLogo != null) {
                ImageIcon imgLogo = new ImageIcon(urlLogo);
                Image escaladaLogo = imgLogo.getImage().getScaledInstance(140, 50, Image.SCALE_SMOOTH);
                lblLogoUnab.setIcon(new ImageIcon(escaladaLogo));
            }
        } catch(Exception e) {}
        panelDerechoCabecera.add(lblLogoUnab);
        
        panelCabecera.add(panelDerechoCabecera, BorderLayout.EAST);

        panelPerfil.add(panelCabecera, BorderLayout.NORTH);

        panelCuerpoPerfil = new JPanel(new GridBagLayout());
        panelCuerpoPerfil.setBackground(Color.WHITE);
        panelCuerpoPerfil.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 224, 230), 1, true),
                new EmptyBorder(30, 40, 30, 40)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 15, 10, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        gbc.gridheight = 4; 
        
        JLabel lblFotoPerfil = new JLabel();
        lblFotoPerfil.setPreferredSize(new Dimension(150, 150));
        lblFotoPerfil.setBorder(BorderFactory.createLineBorder(AZUL_UNAB_TEC, 3, true));
        lblFotoPerfil.setHorizontalAlignment(JLabel.CENTER);
        try {
            java.net.URL urlSonic = getClass().getResource("/proyectoed/imágenes/sonic.jpg");
            if (urlSonic != null) {
                ImageIcon imgSonic = new ImageIcon(urlSonic);
                Image escalada = imgSonic.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
                lblFotoPerfil.setIcon(new ImageIcon(escalada));
            } else {
                lblFotoPerfil.setText("🦔 Sonic.jpg");
            }
        } catch(Exception e) {
            lblFotoPerfil.setText("FOTO PERFIL");
        }
        panelCuerpoPerfil.add(lblFotoPerfil, gbc);

        gbc.gridheight = 1; 
        gbc.weightx = 1.0;

        gbc.gridx = 1; gbc.gridy = 0;
        lblNombre = new JLabel(usuarioActual.getNombres().toUpperCase());
        lblNombre.setFont(new Font("Arial", Font.BOLD, 24));
        lblNombre.setForeground(AZUL_UNAB_TEC);
        panelCuerpoPerfil.add(lblNombre, gbc);

        gbc.gridx = 1; gbc.gridy = 1;
        lblCorreo = new JLabel("📧 Correo: " + usuarioActual.getCorreoUnab());
        lblCorreo.setFont(new Font("Arial", Font.BOLD, 15));
        lblCorreo.setForeground(NEGRO);
        etiquetasTextoPerfil.add(lblCorreo);
        panelCuerpoPerfil.add(lblCorreo, gbc);

        gbc.gridx = 1; gbc.gridy = 2;
        lblCarrera = new JLabel("🎓 Programa: " + usuarioActual.getCarrera());
        lblCarrera.setFont(new Font("Arial", Font.BOLD, 15));
        lblCarrera.setForeground(NEGRO);
        etiquetasTextoPerfil.add(lblCarrera);
        panelCuerpoPerfil.add(lblCarrera, gbc);

        gbc.gridx = 1; gbc.gridy = 3;
        lblEdad = new JLabel("🎂 Edad: " + usuarioActual.getEdad() + " años");
        lblEdad.setFont(new Font("Arial", Font.BOLD, 15));
        lblEdad.setForeground(NEGRO);
        etiquetasTextoPerfil.add(lblEdad);
        panelCuerpoPerfil.add(lblEdad, gbc);

        panelPerfil.add(panelCuerpoPerfil, BorderLayout.CENTER);

        JPanel panelEstadisticas = new JPanel(new GridLayout(1, 3, 15, 0));
        panelEstadisticas.setBackground(Color.WHITE);
        panelesConmutables.add(panelEstadisticas);
        panelEstadisticas.setBorder(new EmptyBorder(10, 40, 30, 40));

        tarjeta1 = crearTarjetaMetrica("Comunidad", "67 Amigos Activos", "👥");
        tarjeta2 = crearTarjetaMetrica("Rango Académico", "Estudiante Regular", "🛡️");
        tarjeta3 = crearTarjetaMetrica("Clan Oficial", "Ing. de la Recocha", "⚔️");

        panelEstadisticas.add(tarjeta1);
        panelEstadisticas.add(tarjeta2);
        panelEstadisticas.add(tarjeta3);

        panelPerfil.add(panelEstadisticas, BorderLayout.SOUTH);

        return panelPerfil;
    }

    private JPanel crearPanelBilletera() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panelesConmutables.add(panel);

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
        panelCrudDunab.setBackground(Color.WHITE);
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
                DunabCRUD.historialAcciones.apilar("Inyección de puntos: " + monto + " DUNAB");
                
                lblSaldoBilletera.setText(DunabCRUD.saldoDunab + " DUNAB");
                actualizarTablaHistorial();
                txtConceptoBono.setText("");
                txtMontoBono.setText("");
                GestorPersistencia.guardarTodo();
                JOptionPane.showMessageDialog(this, "Saldo modificado correctamente en la base de datos DUNAB.");
            } catch(Exception ex) {
                JOptionPane.showMessageDialog(this, "Campos inválidos.", "Error CRUD", JOptionPane.ERROR_MESSAGE);
            }
        });
        panelTop.add(panelCrudDunab);
        
        panel.add(panelTop, BorderLayout.NORTH);

        JPanel panelTabla = new JPanel(new BorderLayout());
        panelTabla.setBorder(new EmptyBorder(15, 20, 20, 20));
        panelTabla.setBackground(Color.WHITE);
        panelesConmutables.add(panelTabla);

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
        panel.setBackground(Color.WHITE);
        panelesConmutables.add(panel);
        panel.setBorder(new EmptyBorder(15, 20, 15, 20));

        JPanel panelDisp = new JPanel(new BorderLayout());
        panelDisp.setOpaque(false);
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
                    DunabCRUD.historialAcciones.apilar("Inscripción a: " + enc.getActividad());
                    
                    lblSaldoBilletera.setText(DunabCRUD.saldoDunab + " DUNAB");
                    actualizarTablaInscritos();
                    actualizarTablaHistorial();
                    GestorPersistencia.guardarTodo();
                    JOptionPane.showMessageDialog(this, "¡Inscripción confirmada! Se han abonado +250 DUNAB.");
                } else {
                    JOptionPane.showMessageDialog(this, "Ya te encuentras registrado en esta actividad.", "Registro Duplicado", JOptionPane.WARNING_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Por favor, seleccione una actividad de la tabla.");
            }
        });
        panelDisp.add(btnInscribir, BorderLayout.SOUTH);

        JPanel panelInsc = new JPanel(new BorderLayout());
        panelInsc.setOpaque(false);
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
        panel.setBackground(Color.WHITE);
        panelesConmutables.add(panel);
        panel.setBorder(new EmptyBorder(15, 20, 15, 20));

        JPanel panelForm = new JPanel(new GridLayout(5, 2, 8, 8));
        panelForm.setBackground(Color.WHITE);
        panelForm.setBorder(BorderFactory.createTitledBorder("Registrar / Modificar Encuentros"));

        txtActividad = new JTextField();
        txtDescripcion = new JTextField();
        txtFecha = new JTextField();
        txtHora = new JTextField();

        panelForm.add(new JLabel("  Nombre de Actividad:")); panelForm.add(txtActividad);
        panelForm.add(new JLabel("  Descripción:")); panelForm.add(txtDescripcion);
        panelForm.add(new JLabel("  Fecha (AAAA-MM-DD):")); panelForm.add(txtFecha);
        panelForm.add(new JLabel("  Hora (HH:MM):")); panelForm.add(txtHora);

        JPanel panelBotones = new JPanel(new GridLayout(1, 2, 10, 0));
        panelBotones.setOpaque(false);
        
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
                DunabCRUD.historialAcciones.apilar("Modificó encuentro: " + act);
                JOptionPane.showMessageDialog(this, "Encuentro modificado con éxito.");
            } else {
                EncuentroCRUD.encuentros.add(new Encuentro(act, desc, fec, hor));
                DunabCRUD.historialAcciones.apilar("Creó encuentro: " + act);
                JOptionPane.showMessageDialog(this, "Nuevo encuentro registrado.");
            }
            
            EncuentroCRUD.guardarEncuentros();
            GestorPersistencia.guardarTodo();
            refrescarAdminTabla.run();
            
            txtActividad.setText(""); txtDescripcion.setText(""); txtFecha.setText(""); txtHora.setText("");
            tablaAdmin.clearSelection();
        });

        btnEliminar.addActionListener(e -> {
            int fila = tablaAdmin.getSelectedRow();
            if (fila >= 0) {
                Encuentro removed = EncuentroCRUD.encuentros.remove(fila);
                DunabCRUD.historialAcciones.apilar("Eliminó encuentro: " + removed.getActividad());
                EncuentroCRUD.guardarEncuentros();
                GestorPersistencia.guardarTodo();
                refrescarAdminTabla.run();
                txtActividad.setText(""); txtDescripcion.setText(""); txtFecha.setText(""); txtHora.setText("");
                JOptionPane.showMessageDialog(this, "Encuentro eliminado.");
            } else {
                JOptionPane.showMessageDialog(this, "Seleccione un registro.");
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
        panel.setBackground(Color.WHITE);
        panelesConmutables.add(panel);
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
                        DunabCRUD.historialAcciones.apilar("Canjeó: " + prod.getNombre());
                        
                        lblSaldoBilletera.setText(DunabCRUD.saldoDunab + " DUNAB");
                        actualizarTablaTienda();
                        actualizarTablaHistorial();
                        GestorPersistencia.guardarTodo();
                        JOptionPane.showMessageDialog(this, "¡Canje Procesado!");
                    } else {
                        JOptionPane.showMessageDialog(this, "Artículo agotado.", "Sin Stock", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Saldo insuficiente.", "Saldo Insuficiente", JOptionPane.WARNING_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Seleccione un producto.");
            }
        });
        panel.add(btnComprar, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel crearPanelTrueques() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.WHITE);
        panelesConmutables.add(panel);
        panel.setBorder(new EmptyBorder(15, 20, 15, 20));

        JPanel panelForm = new JPanel(new GridLayout(3, 2, 8, 8));
        panelForm.setBackground(Color.WHITE);
        panelForm.setBorder(BorderFactory.createTitledBorder("Proponer un nuevo Intercambio / Trueque"));

        txtOfrecidoTrueque = new JTextField();
        txtSolicitadoTrueque = new JTextField();
        JButton btnPublicarTrueque = new JButton("Publicar Oferta");
        btnPublicarTrueque.setBackground(AZUL_UNAB_TEC);
        btnPublicarTrueque.setForeground(BLANCO);

        panelForm.add(new JLabel("  Qué ofreces (Artículo/Servicio):")); panelForm.add(txtOfrecidoTrueque);
        panelForm.add(new JLabel("  Qué buscas a cambio:")); panelForm.add(txtSolicitadoTrueque);
        panelForm.add(new JLabel("")); panelForm.add(btnPublicarTrueque);

        JPanel panelTabla = new JPanel(new BorderLayout(5, 5));
        panelTabla.setOpaque(false);
        
        JLabel lblT = new JLabel("Tablón de Trueques de la Comunidad Universitaria DUNAB", JLabel.LEFT);
        lblT.setFont(new Font("Arial", Font.BOLD, 14));
        panelTabla.add(lblT, BorderLayout.NORTH);

        String[] columnas = {"ID", "Estudiante Origen", "Ofrece", "Busca", "Estado", "Estudiante Destino"};
        modeloTrueques = new DefaultTableModel(columnas, 0);
        JTable tablaTrueques = new JTable(modeloTrueques);
        panelTabla.add(new JScrollPane(tablaTrueques), BorderLayout.CENTER);

        JButton btnAceptarTrueque = new JButton("Aceptar Intercambio Seleccionado");
        btnAceptarTrueque.setBackground(new Color(40, 167, 69));
        btnAceptarTrueque.setForeground(BLANCO);
        panelTabla.add(btnAceptarTrueque, BorderLayout.SOUTH);

        panel.add(panelForm, BorderLayout.NORTH);
        panel.add(panelTabla, BorderLayout.CENTER);

        if(listaTrueques.isEmpty()) {
            listaTrueques.add(new Trueque("TRQ1", "Carlos Mendoza", "Libro Cálculo Stewart", "Puntos DUNAB"));
            listaTrueques.add(new Trueque("TRQ2", "Laura Gómez", "Calculadora Casio", "Saco Universitario"));
        }
        actualizarTablaTrueques();

        btnPublicarTrueque.addActionListener(e -> {
            String ofr = txtOfrecidoTrueque.getText().trim();
            String sol = txtSolicitadoTrueque.getText().trim();
            if (ofr.isEmpty() || sol.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor rellena ambos campos.", "Campos vacíos", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String id = "TRQ" + (listaTrueques.size() + 1);
            listaTrueques.add(new Trueque(id, usuarioActual.getNombres(), ofr, sol));
            DunabCRUD.historialAcciones.apilar("Publicó trueque de: " + ofr);
            actualizarTablaTrueques();
            txtOfrecidoTrueque.setText("");
            txtSolicitadoTrueque.setText("");
            JOptionPane.showMessageDialog(this, "Tu oferta ha sido publicada.");
        });

        btnAceptarTrueque.addActionListener(e -> {
            int fila = tablaTrueques.getSelectedRow();
            if (fila >= 0) {
                String id = (String) modeloTrueques.getValueAt(fila, 0);
                String origen = (String) modeloTrueques.getValueAt(fila, 1);
                String estado = (String) modeloTrueques.getValueAt(fila, 4);

                if (origen.equals(usuarioActual.getNombres())) {
                    JOptionPane.showMessageDialog(this, "No puedes aceptar tu propio trueque.", "Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if (!estado.equals("Pendiente")) {
                    JOptionPane.showMessageDialog(this, "Este trueque ya fue procesado.", "No disponible", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                for (Trueque t : listaTrueques) {
                    if (t.getIdTrueque().equals(id)) {
                        t.setEstudianteDestino(usuarioActual.getNombres());
                        t.setEstado("Aceptado");
                        DunabCRUD.historialAcciones.apilar("Aceptó trueque ID: " + id);
                        break;
                    }
                }
                actualizarTablaTrueques();
                JOptionPane.showMessageDialog(this, "¡Intercambio aceptado!");
            } else {
                JOptionPane.showMessageDialog(this, "Selecciona una oferta de trueque.");
            }
        });

        return panel;
    }

    private JPanel crearPanelClan() {
        JPanel panel = new JPanel(new GridLayout(1, 2, 15, 0));
        panel.setBackground(Color.WHITE);
        panelesConmutables.add(panel);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel panelIzq = new JPanel(new BorderLayout());
        panelIzq.setBackground(Color.WHITE);
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
        panelDer.setBackground(Color.WHITE);
        panelDer.setBorder(BorderFactory.createLineBorder(new Color(220, 224, 230), 1));
        JLabel lblA = new JLabel("📜 Acciones Recientes en Sesión (TDA Stack)", JLabel.CENTER);
        lblA.setFont(new Font("Arial", Font.BOLD, 14));
        panelDer.add(lblA, BorderLayout.NORTH);

        DefaultListModel<String> modeloAcciones = new DefaultListModel<>();
        for (String accion : DunabCRUD.historialAcciones.toList()) {
            modeloAcciones.addElement(accion);
        }
        JList<String> listaAcciones = new JList<>(modeloAcciones);
        panelDer.add(new JScrollPane(listaAcciones), BorderLayout.CENTER);

        panel.add(panelIzq);
        panel.add(panelDer);
        return panel;
    }

    private JPanel crearPanelRanking() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panelesConmutables.add(panel);
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
        panel.setBackground(Color.WHITE);
        panelesConmutables.add(panel);
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
                DunabCRUD.historialAcciones.apilar("Ganó trivia de algoritmos");
                lblSaldoBilletera.setText(DunabCRUD.saldoDunab + " DUNAB");
                actualizarTablaHistorial();
                GestorPersistencia.guardarTodo();
                JOptionPane.showMessageDialog(this, "¡Correcto! Has sumado 150 DUNAB.");
            } else if (resp != null) {
                JOptionPane.showMessageDialog(this, "Incorrecto.", "Fallo", JOptionPane.ERROR_MESSAGE);
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
                DunabCRUD.historialAcciones.apilar("Encontró a Banú");
                lblSaldoBilletera.setText(DunabCRUD.saldoDunab + " DUNAB");
                actualizarTablaHistorial();
                GestorPersistencia.guardarTodo();
                JOptionPane.showMessageDialog(this, "¡Lo encontraste! Ganaste 100 DUNAB.");
            } else if (opcion >= 0) {
                JOptionPane.showMessageDialog(this, "El aula está vacía.");
            }
        });
        panel.add(btnBanu, gbc);

        return panel;
    }

    private void btnModoOscuroActionPerformed(java.awt.event.ActionEvent evt) {
        modoOscuroActivado = !modoOscuroActivado;
        aplicarColoresSelectivos(modoOscuroActivado);
        
        if (modoOscuroActivado) {
            btnModoOscuro.setText("Modo Claro ☀️");
        } else {
            btnModoOscuro.setText("Modo Oscuro 🌙");
        }
    }

    private void aplicarColoresSelectivos(boolean oscuro) {
        Color colorFondo = oscuro ? new Color(33, 33, 33) : Color.WHITE;
        Color colorParches = oscuro ? new Color(50, 50, 50) : Color.WHITE;
        Color colorTexto = oscuro ? new Color(240, 240, 240) : Color.BLACK;

        for (JPanel panel : panelesConmutables) {
            panel.setBackground(colorFondo);
        }

        panelCuerpoPerfil.setBackground(colorFondo);
        for (JLabel etiqueta : etiquetasTextoPerfil) {
            etiqueta.setForeground(colorTexto);
        }
        
        tarjeta1.setBackground(colorParches);
        tarjeta2.setBackground(colorParches);
        tarjeta3.setBackground(colorParches);
        
        this.repaint();
    }

    private void actualizarTablaHistorial() {
        if(modeloHistorial != null) {
            modeloHistorial.setRowCount(0);
            for (Transaccion t : DunabCRUD.historialTransacciones) {
                modeloHistorial.addRow(new Object[]{t.getConcepto(), t.getCantidad() + " DUNAB", t.getFecha(), t.getTipo()});
            }
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
        if(modeloInscritos != null) {
            modeloInscritos.setRowCount(0);
            for (Encuentro e : DunabCRUD.encuentrosInscritos) {
                modeloInscritos.addRow(new Object[]{e.getActividad(), e.getFecha(), e.getHora()});
            }
        }
    }

    private void actualizarTablaTienda() {
        if(modeloTienda != null) {
            modeloTienda.setRowCount(0);
            for (Producto p : DunabCRUD.inventarioTienda) {
                modeloTienda.addRow(new Object[]{p.getNombre(), p.getCosto() + " DUNAB", p.getCategoria(), p.getStock()});
            }
        }
    }

    private void actualizarTablaTrueques() {
        if (modeloTrueques != null) {
            modeloTrueques.setRowCount(0);
            for (Trueque t : listaTrueques) {
                modeloTrueques.addRow(new Object[]{t.getIdTrueque(), t.getEstudianteOrigen(), t.getArticuloOfrecido(), t.getArticuloSolicitado(), t.getEstado(), t.getEstudianteDestino() != null ? t.getEstudianteDestino() : "---"});
            }
        }
    }

    private JComponent crearTarjetaMetrica(String titulo, String valor, String icono) {
        JPanel tarjeta = new JPanel(new BorderLayout(5, 5));
        tarjeta.setBackground(Color.WHITE);
        tarjeta.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(215, 219, 225), 1),
                new EmptyBorder(12, 15, 12, 15)
        ));
        JLabel lblIcono = new JLabel(icono);
        lblIcono.setFont(new Font("Arial", Font.PLAIN, 24));
        tarjeta.add(lblIcono, BorderLayout.WEST);
        
        JPanel panelTextos = new JPanel(new GridLayout(2, 1));
        panelTextos.setOpaque(false);
        
        JLabel lblT = new JLabel(titulo);
        lblT.setFont(new Font("Arial", Font.BOLD, 12));
        lblT.setForeground(Color.GRAY);
        
        JLabel lblV = new JLabel(valor);
        lblV.setFont(new Font("Arial", Font.BOLD, 14));
        lblV.setForeground(NEGRO);
        
        panelTextos.add(lblT);
        panelTextos.add(lblV);
        tarjeta.add(panelTextos, BorderLayout.CENTER);
        
        etiquetasTextoPerfil.add(lblV);
        
        return tarjeta;
    }

    public static class Usuario implements Serializable {
        private static final long serialVersionUID = 1L;
        private String nombres, correoUnab, carrera, contraseña;
        private int edad, rachaDias = 3;
        public Usuario(String n, String co, String ca, String con, int e) {
            this.nombres = n; this.correoUnab = co; this.carrera = ca; this.contraseña = con; this.edad = e;
        }
        public String getNombres() { return nombres; }
        public String getCorreoUnab() { return correoUnab; }
        public String getCarrera() { return carrera; }
        public int getEdad() { return edad; }
        public int getRachaDias() { return rachaDias; }
    }

    public static class Transaccion implements Serializable {
        private static final long serialVersionUID = 1L;
        private String concepto, fecha, tipo; int cantidad;
        public Transaccion(String c, int can, String f, String t) {
            this.concepto = c; this.cantidad = can; this.fecha = f; this.tipo = t;
        }
        public String getConcepto() { return concepto; }
        public int getCantidad() { return cantidad; }
        public String getFecha() { return fecha; }
        public String getTipo() { return tipo; }
    }

    public static class Encuentro implements Serializable {
        private static final long serialVersionUID = 1L;
        private String actividad, descripcion, fecha, hora;
        public Encuentro(String a, String d, String f, String h) {
            this.actividad = a; this.descripcion = d; this.fecha = f; this.hora = h;
        }
        public String getActividad() { return actividad; }
        public void setActividad(String a) { this.actividad = a; }
        public String getDescripcion() { return descripcion; }
        public String getFecha() { return fecha; }
        public void setFecha(String f) { this.fecha = f; }
        public String getHora() { return hora; }
        public void setHora(String h) { this.hora = h; }
        public void setDescripcion(String desc) { this.descripcion = desc; }
    }

    public static class Producto implements Serializable {
        private static final long serialVersionUID = 1L;
        private String nombre, categoria; int costo, stock;
        public Producto(String n, int c, String cat, int s) {
            this.nombre = n; this.costo = c; this.categoria = cat; this.stock = s;
        }
        public String getNombre() { return nombre; }
        public int getCosto() { return costo; }
        public String getCategoria() { return categoria; }
        public int getStock() { return stock; }
        public void setStock(int s) { this.stock = s; }
    }

    public static class Trueque implements Serializable {
        private static final long serialVersionUID = 1L;
        private String idTrueque, estudianteOrigen, articuloOfrecido, articuloSolicitado, estado, estudianteDestino;
        public Trueque(String id, String ori, String ofr, String sol) {
            this.idTrueque = id; this.estudianteOrigen = ori; this.articuloOfrecido = ofr; this.articuloSolicitado = sol;
            this.estado = "Pendiente"; this.estudianteDestino = null;
        }
        public String getIdTrueque() { return idTrueque; }
        public String getEstudianteOrigen() { return estudianteOrigen; }
        public String getArticuloOfrecido() { return articuloOfrecido; }
        public String getArticuloSolicitado() { return articuloSolicitado; }
        public String getEstado() { return estado; }
        public void setEstado(String e) { this.estado = e; }
        public String getEstudianteDestino() { return estudianteDestino; }
        public void setEstudianteDestino(String ed) { this.estudianteDestino = ed; }
    }

    public static class DunabCRUD {
        public static int saldoDunab = 1250;
        public static List<Transaccion> historialTransacciones = new ArrayList<>();
        public static List<Encuentro> encuentrosInscritos = new ArrayList<>();
        public static List<Producto> inventarioTienda = new ArrayList<>();
        public static PilaEnlazada<String> historialAcciones = new PilaEnlazada<>();
        
        public static void inicializarDatos() {
            if(historialTransacciones.isEmpty()) {
                historialTransacciones.add(new Transaccion("Bono Bienvenida", 500, "2026-05-20", "INGRESO"));
                historialTransacciones.add(new Transaccion("Taller de Git/GitHub", 500, "2026-05-22", "INGRESO"));
                historialTransacciones.add(new Transaccion("Fotocopias Biblioteca", 250, "2026-05-24", "GASTO"));
            }
            if(inventarioTienda.isEmpty()) {
                inventarioTienda.add(new Producto("Almuerzo Completo Cafetería", 600, "Alimentación", 15));
                inventarioTienda.add(new Producto("Termo Oficial UNAB", 800, "Accesorios", 8));
                inventarioTienda.add(new Producto("Gorra Plana DUNAB", 1000, "Moda", 4));
            }
            if(historialAcciones.estaVacia()){
                historialAcciones.apilar("Sesión Inicializada Correctamente");
            }
        }
    }

    public static class EncuentroCRUD {
        public static List<Encuentro> encuentros = new ArrayList<>();
        public static List<Encuentro> cargarEncuentros() { 
            List<Encuentro> listaDefecto = new ArrayList<>();
            listaDefecto.add(new Encuentro("Seminario de B-Trees", "Estructuras avanzadas", "2026-05-28", "14:00"));
            listaDefecto.add(new Encuentro("Taller de Electromagnetismo", "Simulación física", "2026-05-29", "10:00"));
            return listaDefecto;
        }
        public static void guardarEncuentros() { }
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            new VentanaPrincipal().setVisible(true);
        });
    }
}
