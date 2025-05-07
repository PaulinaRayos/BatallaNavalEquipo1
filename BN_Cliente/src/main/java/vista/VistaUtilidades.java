/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import vistaModelo.Juego;

/**
 *
 * @author pauli
 */
public class VistaUtilidades {

    // Imagenes
    public static final String PORTADA = "/recursos/batallaNaval.png";
    public static final String FONDO_TABLERO = "/recursos/FondoTablero.png";
    public static final String BOTON_INICIO = "/recursos/botonInicio.png";
    public static final String BOTON_UNIRSE = "/recursos/botonUnirse.png";
    public static final String BOTON_CREAR = "/recursos/botonCrear.png";
    public static final String BOTON_INSTRUCCIONES = "/recursos/botonInstrucciones.png";
    public static final String BOTON_CONTINUAR = "/recursos/botonConfirmar.png";
    public static final String BOTON_VOLVER = "/recursos/botonVolver.png";
    public static final String BOTON_REGRESAR = "/recursos/botonRegresar.png";
    public static final String BOTON_JUGAR = "/recursos/botonJugar.png";
    public static final String BOTON_RENDIRSE = "/recursos/botonRendirse.png";
    public static final String BOTON_ESTADISTICAS= "/recursos/botonEstadisticas.png";
    

    // Colores componentes
    public static final Color COLOR_FONDO = new Color(139, 137, 126);
    public static final Color COLOR_TEXTO_AZUL_OSCURO = new Color(0, 30, 61);
    public static final Color COLOR_TEXTO_BLANCO = new Color(255, 255, 255);
    public static final Color COLOR_BOTON_FONDO = new Color(14, 47, 67);
    //public static final Color COLOR_BOTON_INICIO = new Color(0, 210, 255);
    public static final Color COLOR_BOTON_TEXTO = new Color(255, 255, 255);
    //public static final Color COLOR_CAMPO_TEXTO = new Color(218, 218, 211);
    public static final Color COLOR_CAMPO_TEXTO_FONDO = new Color(24, 25, 37);

    // Colores estados naves
    public static final Color COLOR_UNIDAD_SIN_DANO = new Color(19, 222, 33);
    public static final Color COLOR_UNIDAD_DANADA = new Color(237, 227, 21);
    public static final Color COLOR_UNIDAD_DESTRUIDA = new Color(252, 24, 24);

    // Colores Tablero
    public static final Color COLOR_CELDAS_INVALIDAS = new Color(255, 125, 125, 128);
    public static final Color COLOR_VISTA_PREVIEW = new Color(255, 255, 0, 128);
    public static final String[] LISTA_COLORES_BARCO = {"Rojo", "Azul", "Blanco", "Verde"};
    public static final Color BARCO_ROJO = new Color(226, 28, 8);
    public static final Color BARCO_AZUL = new Color(13, 126, 255);
    public static final Color BARCO_BLANCO = new Color(250, 250, 250);
    public static final Color BARCO_VERDE = new Color(41, 167, 15);

    // Fuentes comunes
    public static final Font FUENTE_TITULO = new Font("Arial Black", Font.PLAIN, 40);//Antes Stencil
    public static final Font FUENTE_SUBTITULO = new Font("SansSerif", Font.BOLD, 20);
    public static final Font FUENTE_BOTON = new Font("SansSerif", Font.BOLD, 18);
    public static final Font FUENTE_CAMPO_TEXTO = new Font("SansSerif", Font.PLAIN, 16);
    public static final Font FUENTE_RESULTADO = new Font("Stencil", Font.PLAIN, 70);

    /**
     * Método para dibujar texto centrado horizontalmente.
     *
     * @param g El objeto Graphics para dibujar
     * @param texto El texto a dibujar
     * @param y La posición vertical (y) en la que dibujar el texto
     * @param fuente La fuente a usar para el texto
     */
    public static void dibujarTextoCentrado(Graphics g, String texto, int y, Font fuente) {
        g.setFont(fuente);
        FontMetrics metrics = g.getFontMetrics(fuente);
        int textWidth = metrics.stringWidth(texto);
        int x = (Juego.GAME_ANCHO - textWidth) / 2;
        g.drawString(texto, x, y);
    }

    /**
     * Crea un botón personalizado con estilo consistente.
     *
     * @param texto Texto del botón
     * @return JButton con el estilo definido
     */
    public static JButton crearBoton(String texto) {
        JButton boton = new JButton(texto);
        boton.setFont(FUENTE_BOTON);
        boton.setForeground(COLOR_BOTON_TEXTO);
        boton.setOpaque(true);
        boton.setBackground(COLOR_BOTON_FONDO);
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        return boton;
    }

    public static JButton crearBotones(String tipoBoton) {
        // Cargar la imagen usando el método correcto para recursos
        BufferedImage img = cargarImagen(tipoBoton);

        // Crear un ImageIcon a partir de la BufferedImage
        ImageIcon icono = (img != null) ? new ImageIcon(img) : null;

        // Crear el botón con la imagen
        JButton boton = new JButton(icono);

        // Eliminar todos los elementos decorativos
        boton.setBorderPainted(false);
        boton.setContentAreaFilled(false);
        boton.setFocusPainted(false);
        boton.setOpaque(false);
        boton.setMargin(new Insets(0, 0, 0, 0));

        // IMPORTANTE: Establecer tamaño exacto basado en la imagen
        if (img != null) {
            int anchoImagen = img.getWidth();
            int altoImagen = img.getHeight();
            boton.setPreferredSize(new Dimension(anchoImagen, altoImagen));
            boton.setMinimumSize(new Dimension(anchoImagen, altoImagen));
            boton.setMaximumSize(new Dimension(anchoImagen, altoImagen));
            boton.setSize(new Dimension(anchoImagen, altoImagen));
        } else {
            boton.setPreferredSize(new Dimension(200, 40));
        }

        // Cambiar el cursor cuando se pasa por encima
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        return boton;
    }
    
    /**
     * Crea un campo de texto personalizado con estilo consistente.
     *
     * @param anchoTexto Cantidad de caracteres de ancho
     * @return JTextField con el estilo definido
     */
    public static JTextField crearCampoTexto(int anchoTexto) {
        JTextField campoTexto = new JTextField(anchoTexto) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15); // Bordes redondeados
                super.paintComponent(g);
            }

            @Override
            protected void paintBorder(Graphics g) {
                // Dibuja un borde redondeado personalizado
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.GRAY); // Color del borde
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
                g2.dispose();
            }
        };
        campoTexto.setFont(FUENTE_CAMPO_TEXTO);
        campoTexto.setForeground(COLOR_CAMPO_TEXTO_FONDO);
        campoTexto.setBackground(COLOR_TEXTO_BLANCO);
        campoTexto.setHorizontalAlignment(SwingConstants.CENTER);
        // Importante para que se vea el fondo redondeado
        campoTexto.setOpaque(false);
        // Margen interno
        campoTexto.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        return campoTexto;
    }

    /**
     * Carga una imagen desde un directorio especificado.
     *
     * @param directorio La ruta del directorio donde se encuentra la imagen
     * @return BufferedImage con la imagen cargada
     */
    public static BufferedImage cargarImagen(String directorio) {
        BufferedImage imagen = null;
        InputStream is = VistaUtilidades.class.getResourceAsStream(directorio);

        try {
            // Lee la imagen desde el flujo de entrada
            imagen = ImageIO.read(is);

        } catch (IOException ex) {
            // Manejo de excepciones en caso de error al cargar la imagen
            ex.getMessage();
        } finally {
            try {
                // Cierra el flujo de entrada una vez que se ha terminado de cargar la imagen
                is.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        // Devuelve la imagen cargada
        return imagen;
    }

    /**
     * Crea una tabla personalizada con estilo consistente.
     *
     * @param datos Los datos de la tabla
     * @param columnas Los nombres de las columnas de la tabla
     * @param ancho El ancho de la tabla
     * @param alto El alto de la tabla
     * @return JTable con el estilo definido
     */
    public static JTable crearTabla(Object[][] datos, String[] columnas, int ancho, int alto) {
        // Modelo de la tabla con los datos y las columnas especificadas
        DefaultTableModel modelo = new DefaultTableModel(datos, columnas);
        JTable tabla = new JTable(modelo);

        // Tamaño preferido de la tabla
        tabla.setPreferredScrollableViewportSize(new Dimension(ancho, alto));
        tabla.setFillsViewportHeight(true);

        // Configuración adicional si deseas personalizar la apariencia
        tabla.setRowHeight(30);  // Altura de las filas
        tabla.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 20)); // Fuente del encabezado
        tabla.setFont(new Font("SansSerif", Font.PLAIN, 18));  // Fuente del contenido

        // Retornar el JTable creado
        return tabla;
    }

    /**
     * Crea un combo box (lista desplegable) personalizado con estilo
     * consistente.
     *
     * @param elementos Los elementos a incluir en el combo box
     * @param ancho El ancho del combo box
     * @param alto El alto del combo box
     * @return JComboBox con el estilo definido
     */
    public static JComboBox<String> crearComboBox(String[] elementos, int ancho, int alto) {
        JComboBox<String> comboBox = new JComboBox(elementos);

        // Configuración de tamaño
        comboBox.setPreferredSize(new Dimension(ancho, alto));

        // Configuración de fuente 
        comboBox.setFont(VistaUtilidades.FUENTE_CAMPO_TEXTO);

        // Configuración de colores
        comboBox.setBackground(COLOR_BOTON_TEXTO);
        comboBox.setForeground(COLOR_CAMPO_TEXTO_FONDO);

        return comboBox;
    }

    /**
     * Crea un panel personalizado para representar un barco con un color
     * específico.
     *
     * @param ancho Ancho del panel
     * @param alto Alto del panel
     * @param colorFondo Color de fondo del panel
     * @return JPanel con el estilo definido
     */
    public static JPanel crearBarcoVista(int ancho, int alto, Color colorFondo) {
        JPanel panel = new JPanel();

        // Establecer el tamaño preferido
        panel.setPreferredSize(new Dimension(ancho, alto));

        // Aplicar el color de fondo
        if (colorFondo != null) {
            panel.setBackground(colorFondo);
        }

        // Desactivar el diseño si quieres un panel de tamaño fijo sin variaciones
        panel.setLayout(null);

        return panel;
    }

    /**
     * Obtiene el color correspondiente a un barco basado en su nombre.
     *
     * @param nombreColor Nombre del color
     * @return Color correspondiente al nombre dado
     */
    public static Color obtenerColorBarco(String nombreColor) {
        switch (nombreColor) {
            case "Rojo":
                return BARCO_ROJO;
            case "Azul":
                return BARCO_AZUL;
            case "Blanco":
                return BARCO_BLANCO;
            case "Verde":
                return BARCO_VERDE;
            default:
                return BARCO_AZUL; // Color por defecto
        }
    }

}
