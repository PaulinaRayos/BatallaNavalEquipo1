/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vistaModelo;

import estados.EstadoMenu;
import interfaz.IVistaBienvenida;
import modelo.ModeloJugador;

/**
 * ViewModel para la vista de bienvenida. Gestiona la lógica de inicio del
 * juego, validando el nombre del jugador y navegando hacia el menú principal.
 *
 * @author ivanochoa
 * @author paulvazquez
 * @author paulinarodriguez
 * @author cuauhtemocvazquez
 */
public class VistaModeloBienvenida {

    /**
     * Vista de bienvenida.
     */
    private IVistaBienvenida vista;

    /**
     * Modelo del jugador.
     */
    private ModeloJugador modeloJugador;

    /**
     * Referencia al juego principal.
     */
    private Juego juego;

    /**
     * Constructor que inicializa el ViewModel con la vista y el juego.
     *
     * @param vista la vista de bienvenida
     * @param juego la instancia principal del juego
     */
    public VistaModeloBienvenida(IVistaBienvenida vista, Juego juego) {
        this.vista = vista;
        this.modeloJugador = ModeloJugador.getInstance();
        this.juego = juego;
    }

    /**
     * Inicia el juego validando el nombre del jugador. Si el nombre es válido,
     * asigna el nombre al modelo y avanza al menú principal. Si no es válido,
     * muestra un mensaje de error.
     */
    public void iniciarJuego() {
        String nombre = vista.obtenerNombreJugador();
        if (nombre.isBlank()) {
            vista.mostrarMensajeError("El nombre no puede estar vacío");
            return;
        }
        if (!validarNombre(nombre)) {
            vista.mostrarMensajeError("El nombre no tiene el formato adecuado");
            return;
        }
        modeloJugador.setNombre(nombre);
        avanzarAMenu();
    }

    /**
     * Cambia el estado del juego al menú principal.
     */
    public void avanzarAMenu() {
        juego.cambiarEstado(new EstadoMenu(juego));
    }

    /**
     * Valida que el nombre del jugador solo contenga letras y números y tenga
     * una longitud entre 1 y 15 caracteres.
     *
     * @param nombre el nombre del jugador
     * @return true si el nombre es válido, false en caso contrario
     */
    private boolean validarNombre(String nombre) {
        return nombre.matches("^[a-zA-Z0-9]{1,15}$");
    }

}
