package proyectoed.pantallas;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import proyectoed.VentanaPrincipal;
import proyectoed.VentanaPrincipal.DunabCRUD;
import proyectoed.VentanaPrincipal.Transaccion;

/**
 * @authors Tomás Reyes, Juan Mateus, Santiago Rey, David Barbosa
 */
public class VentanaLogin extends JFrame {

    private static ArrayList<Usuario> usuariosRegistrados = new ArrayList<>();

    private JTextField txtUsuario;
    private JPasswordField txtContrasena;
    
    private final Color AZUL_UNAB_TEC = new Color(0, 91, 171);
    private final Color BLANCO = new Color(255, 255, 255);
    private final Color NEGRO = new Color(0, 0, 0);

    public VentanaLogin() {
        setTitle("Sistema de Gestión de Puntos DUNAB - Login");
        setSize(450, 540); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        if (usuariosRegistrados.isEmpty()) {
            String fechaAyer = java.time.LocalDate.now().minusDays(1).toString();
            usuariosRegistrados.add(new Usuario("Estudiante UNAB", "unab", "Ingeniería de Sistemas", "123", 19, fechaAyer));
        }

        try {
            java.net.URL urlIcono = getClass().getResource("/proyectoed/logo_unab.png");
            if (urlIcono != null) setIconImage(new ImageIcon(urlIcono).getImage());
        } catch (Exception e) { System.out.println(e.getMessage()); }

        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(BLANCO);

        JPanel panelCabecera = new JPanel(new GridBagLayout());
        panelCabecera.setBackground(BLANCO);
        panelCabecera.setPreferredSize(new Dimension(450, 120));
        
        try {
            java.net.URL urlLogo = getClass().getResource("/proyectoed/imágenes/Logo UNAB.png");
            if (urlLogo != null) {
                ImageIcon iconoOriginal = new ImageIcon(urlLogo);
                Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(180, 60, Image.SCALE_SMOOTH);
                JLabel lblLogo = new JLabel(new ImageIcon(imagenEscalada));
                panelCabecera.add(lblLogo);
            }
        } catch (Exception e) {
            JLabel lblFallback = new JLabel("UNIVERSIDAD UNAB", JLabel.CENTER);
            lblFallback.setForeground(AZUL_UNAB_TEC);
            lblFallback.setFont(new Font("Arial", Font.BOLD, 20));
            panelCabecera.add(lblFallback);
        }
        panelPrincipal.add(panelCabecera, BorderLayout.NORTH);

        JPanel panelCuerpo = new JPanel(new GridLayout(6, 1, 5, 5));
        panelCuerpo.setBackground(BLANCO);
        panelCuerpo.setBorder(new EmptyBorder(20, 40, 10, 40));

        JLabel lblUser = new JLabel("Usuario o correo UNAB:");
        lblUser.setFont(new Font("Arial", Font.BOLD, 14));
        txtUsuario = new JTextField();
        
        JLabel lblPass = new JLabel("Contraseña:");
        lblPass.setFont(new Font("Arial", Font.BOLD, 14));
        txtContrasena = new JPasswordField();

        JButton btnIngresar = new JButton("Únete a DUNAB / Ingresar");
        btnIngresar.setFont(new Font("Arial", Font.BOLD, 14));
        btnIngresar.setBackground(AZUL_UNAB_TEC);
        btnIngresar.setForeground(BLANCO); 

        btnIngresar.setOpaque(true);
        btnIngresar.setContentAreaFilled(true);
        btnIngresar.setBorderPainted(true);
        btnIngresar.setFocusPainted(false);
        btnIngresar.setBorder(BorderFactory.createLineBorder(AZUL_UNAB_TEC, 8));

        btnIngresar.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnIngresar.setBackground(new Color(0, 115, 215)); 
                btnIngresar.setBorder(BorderFactory.createLineBorder(new Color(0, 115, 215), 8));
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnIngresar.setBackground(AZUL_UNAB_TEC); 
                btnIngresar.setBorder(BorderFactory.createLineBorder(AZUL_UNAB_TEC, 8));
            }
        });

        panelCuerpo.add(lblUser);
        panelCuerpo.add(txtUsuario);
        panelCuerpo.add(lblPass);
        panelCuerpo.add(txtContrasena);
        panelCuerpo.add(new JLabel("")); 
        panelCuerpo.add(btnIngresar);
        panelPrincipal.add(panelCuerpo, BorderLayout.CENTER);

        JPanel panelInferior = new JPanel(new GridLayout(2, 1, 2, 2));
        panelInferior.setBackground(BLANCO);
        panelInferior.setBorder(new EmptyBorder(0, 40, 15, 40));

        JButton btnCrearCuenta = new JButton("¿No tienes cuenta? Regístrate aquí");
        btnCrearCuenta.setContentAreaFilled(false);
        btnCrearCuenta.setBorderPainted(false);
        btnCrearCuenta.setForeground(AZUL_UNAB_TEC); 
        btnCrearCuenta.setFont(new Font("Arial", Font.BOLD, 13)); 
        btnCrearCuenta.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btnCrearCuenta.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) { btnCrearCuenta.setForeground(new Color(0, 150, 255)); }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) { btnCrearCuenta.setForeground(AZUL_UNAB_TEC); }
        });

        JButton btnOlvidarPass = new JButton("¿Olvidaste tu contraseña?");
        btnOlvidarPass.setContentAreaFilled(false);
        btnOlvidarPass.setBorderPainted(false);
        btnOlvidarPass.setForeground(AZUL_UNAB_TEC);
        btnOlvidarPass.setFont(new Font("Arial", Font.BOLD, 12));
        btnOlvidarPass.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnOlvidarPass.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) { btnOlvidarPass.setForeground(new Color(0, 150, 255)); }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) { btnOlvidarPass.setForeground(AZUL_UNAB_TEC); }
        });

        panelInferior.add(btnCrearCuenta);
        panelInferior.add(btnOlvidarPass);
        panelPrincipal.add(panelInferior, BorderLayout.SOUTH);

        btnIngresar.addActionListener(e -> procesarLogin());
        btnCrearCuenta.addActionListener(e -> mostrarVentanaRegistro());
        btnOlvidarPass.addActionListener(e -> mostrarVentanaRecuperacion()); 

        add(panelPrincipal);
    }

    private void procesarLogin() {
        String correoInput = txtUsuario.getText().trim();
        String passInput = new String(txtContrasena.getPassword());

        if (correoInput.isEmpty() || passInput.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, llene todos los campos.", "Campos vacíos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        boolean accesoConcedido = false;
        Usuario usuarioLogueado = null;
        
        for (Usuario u : usuariosRegistrados) {
            if (u.getCorreoUnab().equalsIgnoreCase(correoInput) && u.getContrasena().equals(passInput)) {
                accesoConcedido = true;
                usuarioLogueado = u;
                break;
            }
        }

        if (accesoConcedido) {
            verificarYPremiarRacha(usuarioLogueado);

            JOptionPane.showMessageDialog(this, "¡Bienvenido al Sistema DUNAB!");
            this.dispose(); 
            
            // Convertimos el Usuario local de la VentanaLogin al Usuario esperado por la VentanaPrincipal
            final VentanaPrincipal.Usuario usrParaVentana = new VentanaPrincipal.Usuario(
                usuarioLogueado.getNombres(),
                usuarioLogueado.getCorreoUnab(),
                usuarioLogueado.getCarrera(),
                usuarioLogueado.getContrasena(),
                usuarioLogueado.getEdad()
            );
            
            java.awt.EventQueue.invokeLater(() -> {
                new VentanaPrincipal(usrParaVentana).setVisible(true);
            });
        } else {
            JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos.\n(Prueba con usuario: 'unab' y clave: '123')", "Error de autenticación", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void verificarYPremiarRacha(Usuario u) {
        try {
            java.time.LocalDate hoy = java.time.LocalDate.now();
            java.time.LocalDate ultimaConexion = java.time.LocalDate.parse(u.getUltimaFechaIngreso());
            
            long diferenciaDias = java.time.temporal.ChronoUnit.DAYS.between(ultimaConexion, hoy);

            if (diferenciaDias == 1) {
                u.setRachaDias(u.getRachaDias() + 1);
                u.setUltimaFechaIngreso(hoy.toString());
                
                int recompensa = 100 * u.getRachaDias();
                DunabCRUD.saldoDunab += recompensa;
                DunabCRUD.historialTransacciones.add(new Transaccion("Bono Racha Activa 🔥 (Día " + u.getRachaDias() + ")", recompensa, hoy.toString(), "INGRESO"));
                
                JOptionPane.showMessageDialog(this, "¡Sigues conectado! Tu racha subió a " + u.getRachaDias() + " días.\n🎁 Recompensa: +" + recompensa + " DUNAB acreditados.", "Racha Activa 🔥", JOptionPane.INFORMATION_MESSAGE);
            } 
            else if (diferenciaDias > 1) {
                u.setRachaDias(1);
                u.setUltimaFechaIngreso(hoy.toString());
                JOptionPane.showMessageDialog(this, "No ingresaste el día de ayer. Tu racha se ha restablecido a 1 día.", "Racha Reiniciada 💔", JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception ex) {
            System.out.println("Error procesando cálculo de rachas: " + ex.getMessage());
        }
    }
    
    private void mostrarVentanaRecuperacion() {
        JDialog diagRecuperar = new JDialog(this, "Recuperar Contraseña DUNAB", true);
        diagRecuperar.setSize(400, 300);
        diagRecuperar.setLocationRelativeTo(this);
        diagRecuperar.setLayout(new BorderLayout());

        JPanel panelForm = new JPanel(new GridLayout(4, 1, 10, 10));
        panelForm.setBorder(new EmptyBorder(20, 30, 20, 30));
        panelForm.setBackground(BLANCO);

        JTextField txtCorreoBuscar = new JTextField();
        JPasswordField txtNuevaPass = new JPasswordField();

        panelForm.add(new JLabel("Ingresa tu Correo Institucional UNAB:"));
        panelForm.add(txtCorreoBuscar);
        panelForm.add(new JLabel("Ingresa tu NUEVA Contraseña:"));
        panelForm.add(txtNuevaPass);

        JButton btnCambiarClave = new JButton("Actualizar Contraseña");
        btnCambiarClave.setBackground(AZUL_UNAB_TEC);
        btnCambiarClave.setForeground(BLANCO);
        btnCambiarClave.setFont(new Font("Arial", Font.BOLD, 14));

        btnCambiarClave.addActionListener(ev -> {
            try {
                String correoBuscado = txtCorreoBuscar.getText().trim();
                String nuevaClave = new String(txtNuevaPass.getPassword());

                if (correoBuscado.isEmpty() || nuevaClave.isEmpty()) {
                    throw new IllegalArgumentException("Todos los campos son obligatorios.");
                }

                Usuario usuarioEncontrado = null;
                for (Usuario u : usuariosRegistrados) {
                    if (u.getCorreoUnab().equalsIgnoreCase(correoBuscado)) {
                        usuarioEncontrado = u;
                        break;
                    }
                }

                if (usuarioEncontrado == null) {
                    throw new NullPointerException("El correo ingresado no coincide con ningún estudiante registrado.");
                }

                usuarioEncontrado.setContrasena(nuevaClave);
                JOptionPane.showMessageDialog(diagRecuperar, "¡Contraseña actualizada con éxito!\nYa puedes iniciar sesión con tu nueva contraseña.");
                diagRecuperar.dispose();

            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(diagRecuperar, ex.getMessage(), "Campos vacíos", JOptionPane.WARNING_MESSAGE);
            } catch (NullPointerException ex) {
                JOptionPane.showMessageDialog(diagRecuperar, ex.getMessage(), "Usuario no encontrado", JOptionPane.ERROR_MESSAGE);
            }
        });

        diagRecuperar.add(panelForm, BorderLayout.CENTER);
        diagRecuperar.add(btnCambiarClave, BorderLayout.SOUTH);
        diagRecuperar.setVisible(true);
    }

    private void mostrarVentanaRegistro() {
        JDialog diagRegistro = new JDialog(this, "Sistema de Registro DUNAB", true);
        diagRegistro.setSize(400, 500);
        diagRegistro.setLocationRelativeTo(this);
        diagRegistro.setLayout(new BorderLayout());

        JPanel panelForm = new JPanel(new GridLayout(6, 2, 10, 15));
        panelForm.setBorder(new EmptyBorder(20, 20, 20, 20));
        panelForm.setBackground(BLANCO);

        JTextField regNombres = new JTextField();
        JTextField regCorreo = new JTextField();
        JTextField regCarrera = new JTextField();
        JTextField regEdad = new JTextField();
        JPasswordField regPass = new JPasswordField();

        panelForm.add(new JLabel("Nombres Completos:")); panelForm.add(regNombres);
        panelForm.add(new JLabel("Correo Institucional:")); panelForm.add(regCorreo);
        panelForm.add(new JLabel("Carrera / Programa:")); panelForm.add(regCarrera);
        panelForm.add(new JLabel("Edad:")); panelForm.add(regEdad);
        panelForm.add(new JLabel("Contraseña:")); panelForm.add(regPass);

        JButton btnRegistrar = new JButton("Crear Cuenta");
        btnRegistrar.setBackground(AZUL_UNAB_TEC);
        btnRegistrar.setForeground(BLANCO); 
        
        btnRegistrar.addActionListener(ev -> {
            try {
                String nombres = regNombres.getText().trim();
                String correo = regCorreo.getText().trim();
                String carrera = regCarrera.getText().trim();
                String pass = new String(regPass.getPassword());
                
                if (nombres.isEmpty() || correo.isEmpty() || carrera.isEmpty() || pass.isEmpty()) {
                    throw new IllegalArgumentException("Todos los campos obligatorios deben estar diligenciados.");
                }

                int edad = Integer.parseInt(regEdad.getText().trim());
                if (edad < 0 || edad > 100) {
                    throw new NumberFormatException("La edad debe ser un número coherente.");
                }

                String fechaHoy = java.time.LocalDate.now().toString();
                usuariosRegistrados.add(new Usuario(nombres, correo, carrera, pass, edad, fechaHoy));
                JOptionPane.showMessageDialog(diagRegistro, "¡Cuenta institucional creada con éxito!\nYa puedes iniciar sesión.");
                diagRegistro.dispose();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(diagRegistro, "Error en el campo Edad: Por favor introduce solo números válidos.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(diagRegistro, ex.getMessage(), "Campos Incompletos", JOptionPane.WARNING_MESSAGE);
            }
        });

        diagRegistro.add(panelForm, BorderLayout.CENTER);
        diagRegistro.add(btnRegistrar, BorderLayout.SOUTH);
        diagRegistro.setVisible(true);
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            new VentanaLogin().setVisible(true);
        });
    }

    public static class Usuario {
        private String nombres, correoUnab, carrera, contrasena, ultimaFechaIngreso;
        private int edad, rachaDias;

        public Usuario(String nombres, String correoUnab, String carrera, String contrasena, int edad, String ultimaFechaIngreso) {
            this.nombres = nombres;
            this.correoUnab = correoUnab;
            this.carrera = carrera;
            this.contrasena = contrasena;
            this.edad = edad;
            this.ultimaFechaIngreso = ultimaConexionValida(ultimaFechaIngreso);
            this.rachaDias = 1;
        }

        private String ultimaConexionValida(String fecha) {
            return (fecha == null || fecha.isEmpty()) ? java.time.LocalDate.now().toString() : fecha;
        }

        public String getNombres() { return nombres; }
        public String getCorreoUnab() { return correoUnab; }
        public String getCarrera() { return carrera; }
        public String getContrasena() { return contrasena; }
        public void setContrasena(String contrasena) { this.contrasena = contrasena; }
        public int getEdad() { return edad; }
        public int getRachaDias() { return rachaDias; }
        public void setRachaDias(int rachaDias) { this.rachaDias = rachaDias; }
        public String getUltimaFechaIngreso() { return ultimaFechaIngreso; }
        public void setUltimaFechaIngreso(String ultimaFechaIngreso) { this.ultimaFechaIngreso = ultimaFechaIngreso; }
    }
} 