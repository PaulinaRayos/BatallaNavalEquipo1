/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bo;

import Modelo.ModeloCasilla;
import Modelo.ModeloCoordenada;
import Modelo.ModeloDisparo;
import Modelo.ModeloJugador;
import Modelo.ModeloPartida;
import Modelo.ModeloTablero;
import Modelo.ModeloTipoUnidad;
import Modelo.ModeloUbicacionUnidad;
import Modelo.ModeloUnidad;
import comunicacion.ClientManager;
import convertidor.toJSON;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import comunicacion.MessageUtil;
import enums.AccionesJugador;
import enums.ControlPartida;
import enums.EstadoCasilla;
import enums.ResultadosDisparos;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.Random;

/**
 *
 * @author pauli
 */
public class PartidaBO {

    private ModeloPartida partida;
    private UnidadBO unidadBO;

    public PartidaBO() {
        this.partida = ModeloPartida.getInstance();
        this.unidadBO = new UnidadBO();
    }

    // Método para crear una partida
    public Map<String, Object> crearPartida(Map<String, Object> data, String clientId) {
        // Verifica si el cliente ya está registrado
        if (ClientManager.getClientSocket(clientId) == null) {
            throw new IllegalStateException("El cliente no está conectado.");
        }

        String codigoAcceso = UUID.randomUUID().toString().replace("-", "").substring(0, 5);
        partida.setCodigoAcceso(codigoAcceso);
        System.out.println("El codigo de acceso es: " + codigoAcceso);
        ModeloJugador host = crearJugador(clientId, (String) data.get("nombre"));
        partida.addJugador(host);
        // se habla al metodo para iniciar su tablero y unidades
        iniciarTableroyUnidades(clientId);
        ClientManager.addClient(ClientManager.getClientSocket(clientId), clientId, host);
        return toJSON.dataToJSON("accion", "CREAR_PARTIDA", "id", host.getId(), "codigo_acceso", codigoAcceso);
    }

    // Método para que un jugador se una a una partida
    public Map<String, Object> unirsePartida(Map<String, Object> data, String clientId) {
        String codigo_acceso = (String) data.get("codigo_acceso");
        System.out.println(codigo_acceso);
        if (!partida.getCodigoAcceso().equalsIgnoreCase(codigo_acceso)) {
            return toJSON.dataToJSON("accion", "UNIRSE_PARTIDA", "error", "El código de acceso no coincide");
        }
        ModeloJugador jugador = crearJugador(clientId, (String) data.get("nombre"));
        partida.addJugador(jugador);
        // se habla al metodo para iniciar su tablero y unidades
        iniciarTableroyUnidades(clientId);
        ClientManager.addClient(ClientManager.getClientSocket(clientId), clientId, jugador);
        partida.getJugadores().stream().forEach(p -> System.out.println("Jugador en la partida " + p.getId()));
        List<String> nombresJugadores = partida.getJugadores().stream()
                .map(ModeloJugador::getNombre)
                .collect(Collectors.toList());

        // Notificar a todos los jugadores que un nuevo jugador se ha unido
        notificarNuevoJugador(jugador);

        return toJSON.dataToJSON(
                "accion", "UNIRSE_PARTIDA",
                "id", jugador.getId(),
                "codigo_acceso", codigo_acceso,
                "nombres_jugadores", nombresJugadores);
    }

    private void notificarNuevoJugador(ModeloJugador nuevoJugador) {
        for (ModeloJugador jugadorExistente : partida.getJugadores()) {
            if (!jugadorExistente.getId().equals(nuevoJugador.getId())) {
                String clientIdExistente = jugadorExistente.getId();
                Socket socketJugador = ClientManager.getClientSocket(clientIdExistente);

                Map<String, Object> mensajeNotificacion = toJSON.dataToJSON(
                        "accion", "NUEVO_JUGADOR",
                        "nombre_jugador", nuevoJugador.getNombre());

                MessageUtil.enviarMensaje(socketJugador, mensajeNotificacion);
            }
        }
    }

    public ModeloJugador crearJugador(String id, String nombre) {
        return new ModeloJugador(id, nombre);
    }

    public Map<String, Object> jugadorListo(Map<String, Object> request, String clientId) {
        ModeloJugador jugador = ClientManager.getJugadorByClientId(clientId);
        if (jugador != null) {
            jugador.setListo(true);

            // Notificar a todos los jugadores que este jugador está listo
            notificarEstadoListo(jugador);

            // Si todos los jugadores están listos, notificar para avanzar
            if (partida.todosLosJugadoresListos()) {
                // Reiniciar el estado listo de los jugadores para la siguiente fase
                reiniciarEstadoListo();

                // Notificar a los jugadores para iniciar la organización
                notificarIniciarOrganizar();
            }
        }
        return null;
    }

    private void notificarEstadoListo(ModeloJugador jugadorListo) {
        for (ModeloJugador jugador : partida.getJugadores()) {
            Socket socketJugador = ClientManager.getClientSocket(jugador.getId());
            Map<String, Object> mensaje = toJSON.dataToJSON(
                    "accion", "ACTUALIZAR_ESTADO_LISTO",
                    "id_jugador", jugadorListo.getId(),
                    "nombre_jugador", jugadorListo.getNombre(),
                    "listo", jugadorListo.isListo());
            MessageUtil.enviarMensaje(socketJugador, mensaje);
        }
    }

    private void notificarTodosListos() {
        for (ModeloJugador jugador : partida.getJugadores()) {
            Socket socketJugador = ClientManager.getClientSocket(jugador.getId());
            Map<String, Object> mensaje = toJSON.dataToJSON(
                    "accion", "TODOS_LISTOS");
            MessageUtil.enviarMensaje(socketJugador, mensaje);

        }
    }

    private void notificarIniciarOrganizar() {
        for (ModeloJugador jugador : partida.getJugadores()) {
            Socket socketJugador = ClientManager.getClientSocket(jugador.getId());
            Map<String, Object> mensaje = toJSON.dataToJSON(
                    "accion", "INICIAR_ORGANIZAR");
            MessageUtil.enviarMensaje(socketJugador, mensaje);
        }
    }

    public void reiniciarEstadoListo() {
        for (ModeloJugador jugador : partida.getJugadores()) {
            jugador.setListo(false);
        }
    }

    private void determinarJugadorInicial() {
        List<ModeloJugador> jugadores = partida.getJugadores();
        Random random = new Random();
        int indice = random.nextInt(jugadores.size());
        ModeloJugador jugadorInicial = jugadores.get(indice);
        partida.setJugadorTurno(jugadorInicial);
    }

    private void notificarJugadorEsperando(ModeloJugador jugadorListo) {
        for (ModeloJugador jugador : partida.getJugadores()) {
            if (!jugador.getId().equals(jugadorListo.getId())) {
                Socket socketJugador = ClientManager.getClientSocket(jugador.getId());
                Map<String, Object> mensaje = toJSON.dataToJSON(
                        "accion", "JUGADOR_ESPERANDO",
                        "nombre_jugador", jugadorListo.getNombre());
                MessageUtil.enviarMensaje(socketJugador, mensaje);
            }
        }
    }

    public void reiniciarPartida() {
        partida.ReiniciarPartida();
    }

    public void finalizarPartida() {
        ModeloPartida.instance = null;
    }

    private String formatoDuracion(long duracionMilisegundos) {
        long segundos = duracionMilisegundos / 1000;
        long minutos = segundos / 60;
        segundos = segundos % 60;
        return String.format("%02d:%02d", minutos, segundos);
    }

    private void notificarIniciarJuego() {
        ModeloJugador jugadorInicial = partida.getJugadorTurno();
        List<ModeloJugador> jugadores = partida.getJugadores();

        for (ModeloJugador jugador : jugadores) {
            Socket socketJugador = ClientManager.getClientSocket(jugador.getId());
            boolean esSuTurno = jugador.getId().equals(jugadorInicial.getId());

            // Get opponent's name
            ModeloJugador oponente = jugadores.stream()
                    .filter(j -> !j.getId().equals(jugador.getId()))
                    .findFirst()
                    .orElse(null);
            String nombreOponente = oponente != null ? oponente.getNombre() : "Oponente";

            Map<String, Object> mensaje = toJSON.dataToJSON(
                    "accion", "INICIAR_JUEGO",
                    "jugador_inicial_id", jugadorInicial.getId(),
                    "tu_turno", esSuTurno,
                    "nombre_oponente", nombreOponente);
            MessageUtil.enviarMensaje(socketJugador, mensaje);
        }
    }

    public void colocarUnidadTablero(Map<String, Object> request, String clientId) {
        ModeloTablero tableroUsuario = this.partida.getTableros().get(clientId);
        ModeloJugador jugador = ClientManager.getJugadorByClientId(clientId);

        List<Map<String, Object>> unidadesData = (List<Map<String, Object>>) request.get("unidades");

        for (Map<String, Object> unidadData : unidadesData) {
            int numNave = (Integer) unidadData.get("numNave");
            ModeloUnidad unidad = unidadBO.obtenerUnidadPorNumNave(jugador, numNave);

            if (unidad == null) {
                System.out.println("No se encontro la unidad");
                break;
            }

            Map<ModeloCasilla, Boolean> casillas = unidadBO.obtenerCoordenadas(unidadData);

            ModeloUbicacionUnidad ubicacionUnidad = new ModeloUbicacionUnidad(unidad, casillas);

            tableroUsuario.agregarUbicacion(ubicacionUnidad);
        }
        // Marcar al jugador como listo
        jugador.setListo(true);
        notificarEstadoListo(jugador);

        System.out.println("Jugador: " + clientId);
        System.out.println(tableroUsuario.getUnidades());

        // Si todos los jugadores están listos, notificar para iniciar el juego
        if (partida.todosLosJugadoresListos()) {
            // iniciar el temporizador
            partida.iniciarPartida();

            // Notificar para iniciar el juego
            determinarJugadorInicial();
            notificarIniciarJuego();
        } else {
            notificarJugadorEsperando(jugador);
        }

        // return toJSON.dataToJSON("accion", AccionesJugador.ORDENAR.name(), "id",
        // clientId);
    }

    private void iniciarTableroyUnidades(String clientId) {
        // se agrega el tablero del jugador
        partida.addTablero(clientId, new ModeloTablero());

        // se crea la lista de unidades del jugador
        List<ModeloUnidad> unidades = new ArrayList<>();
        // 2 portaaviones
        int numNave = 1;
        // se crean los portaaviones
        while (numNave <= 2) {
            ModeloUnidad nave = UnidadFactory.crearUnidad(ModeloTipoUnidad.PORTAAVIONES.NOMBRE);
            nave.setNumNave(numNave);
            unidades.add(nave);
            numNave++;
        }
        // se crean los cruceros
        while (numNave <= 4) {
            ModeloUnidad nave = UnidadFactory.crearUnidad(ModeloTipoUnidad.CRUCERO.NOMBRE);
            nave.setNumNave(numNave);
            unidades.add(nave);
            numNave++;
        }
        // se crean los submarinos
        while (numNave <= 8) {
            ModeloUnidad nave = UnidadFactory.crearUnidad(ModeloTipoUnidad.SUBMARINO.NOMBRE);
            nave.setNumNave(numNave);
            unidades.add(nave);
            numNave++;
        }
        // se crean los barcos
        while (numNave <= 11) {
            ModeloUnidad nave = UnidadFactory.crearUnidad(ModeloTipoUnidad.BARCO.NOMBRE);
            nave.setNumNave(numNave);
            unidades.add(nave);
            numNave++;
        }

        // se guardan las unidades del jugador
        for (ModeloJugador jugador : partida.getJugadores()) {
            if (jugador.getId() == clientId) {
                jugador.setUnidades(unidades);
            }
        }
    }
    private Map<String, Object> generarRespuestaAtaque(boolean impacto, String mensaje, int vidaNave, int numeroNave,
            int x, int y, String clientId, ModeloJugador jugadorAtacado) {
        ModeloJugador jugadorTurno = impacto ? ClientManager.getJugadorByClientId(clientId)
                : ClientManager.getJugadorByClientId(jugadorAtacado.getId());
        this.partida.setJugadorTurno(jugadorTurno);

        Map<String, Object> jugador1Response = new HashMap<>();
        jugador1Response.put("accion", AccionesJugador.ATACAR.name());
        jugador1Response.put("resultado", impacto ? ResultadosDisparos.RESULTADO_ATAQUE_REALIZADO_IMPACTO.name()
                : ResultadosDisparos.RESULTADO_ATAQUE_REALIZADO_FALLO.name());
        jugador1Response.put("mensaje", mensaje);
        jugador1Response.put("vida_nave", vidaNave);
        jugador1Response.put("numero_nave", numeroNave);
        jugador1Response.put("x", x);
        jugador1Response.put("y", y);
        jugador1Response.put(ControlPartida.DETERMINAR_TURNO.name(), jugadorTurno.getNombre());

        Map<String, Object> jugador2Response = new HashMap<>();
        jugador2Response.put("accion", AccionesJugador.ATACAR.name());
        jugador2Response.put("resultado", impacto ? ResultadosDisparos.RESULTADO_ATAQUE_RECIBIDO_IMPACTO.name()
                : ResultadosDisparos.RESULTADO_ATAQUE_RECIBIDO_FALLO.name());
        jugador2Response.put("mensaje", impacto ? "Tu nave fue impactada" : "El impacto falló");
        jugador2Response.put("vida_nave", vidaNave);
        jugador2Response.put("numero_nave", numeroNave);
        jugador2Response.put("x", x);
        jugador2Response.put("y", y);
        jugador2Response.put(ControlPartida.DETERMINAR_TURNO.name(), jugadorTurno.getNombre());

        Map<String, Object> response = new HashMap<>();
        response.put(clientId, jugador1Response);
        response.put(jugadorAtacado.getId(), jugador2Response);

        return response;
    }
    
    public boolean verificarEstadoPartida(ModeloTablero tablero) {
        // Aqui abria que tener en alguna parte el numero definido de naves para no
        // hardcodearlo
        return tablero.getNumNavesDestruidas() == 11;
    }

     
    private Map<String, Object> generarMensajeVictoria(ModeloJugador jugadorGanador) {
        Map<String, Object> mensajeVictoria = new HashMap<>();

        mensajeVictoria.put("resultado", ControlPartida.PARTIDA_FINALIZADA.name());
        mensajeVictoria.put("mensaje", "¡Felicidades! " + jugadorGanador.getNombre() + " ha ganado la partida.");
        mensajeVictoria.put("accion", AccionesJugador.ATACAR.name());
        mensajeVictoria.put("ganador", jugadorGanador.getNombre());
        mensajeVictoria.put(ControlPartida.DETERMINAR_TURNO.name(), null);
        
        
        Map<String, Object> estadisticas = obtenerEstadisticasJugador(jugadorGanador.getId());
        mensajeVictoria.put("estadisticas", estadisticas);
        
        // Agregar el tiempo de la partida
        String tiempoPartida = formatoDuracion(partida.getDuracion());
        mensajeVictoria.put("tiempo_partida", tiempoPartida);

        // mensajeVictoria.put("turnos_jugados", turnosJugados);
        // mensajeVictoria.put("naves_restantes", navesRestantes);
        return mensajeVictoria;
    }
    
    public void rendirse(Map<String, Object> request, String clientId) {
        ModeloJugador jugadorQueSeRinde = ClientManager.getJugadorByClientId(clientId);
        if (jugadorQueSeRinde == null) {
            // Manejar error si el jugador no existe
            return;
        }

        ModeloJugador jugadorGanador = ClientManager.getOtherPlayer(clientId);

        if (jugadorGanador == null) {
            // Manejar error si el otro jugador no existe
            return;
        }

        // Finalizar la partida
        partida.finalizarPartida();
        partida.setGanador(jugadorGanador);

        // Generar el mensaje de victoria con las estadísticas
        Map<String, Object> mensajeVictoria = generarMensajeVictoria(jugadorGanador);

        // Enviar el mensaje a ambos jugadores
        Socket jugadorSocket = ClientManager.getClientSocket(jugadorGanador.getId());
        MessageUtil.enviarMensaje(jugadorSocket, mensajeVictoria);
        
        Socket otroJugadorSocket = ClientManager.getClientSocket(jugadorQueSeRinde.getId());
        MessageUtil.enviarMensaje(otroJugadorSocket, mensajeVictoria);
    }

    
    public Map<String, Object> ubicarAtaque(Map<String, Object> data, String clientId) {

        ModeloJugador jugadorAtacado = ClientManager.getOtherPlayer(clientId);
        ModeloTablero tableroAtacado = this.partida.getTableroJugador(jugadorAtacado.getId());
        List<ModeloUbicacionUnidad> ubicacionUnidades = tableroAtacado.getUnidades();
        String mensaje = null;
        int vidaNave = 0;
        int numeroNave = 0;

        boolean estadoPartida = false;
        int x = (int) data.get("x");
        int y = (int) data.get("y");

        Optional<ModeloUbicacionUnidad> ubicacionUnidadImpactada = ubicacionUnidades.stream()
                .filter(ubicacionUnidad -> ubicacionUnidad.getCasillas().entrySet().stream()
                .filter(entry -> entry.getValue())
                .anyMatch(entry -> entry.getKey().getCoordenada().getX() == x
                && entry.getKey().getCoordenada().getY() == y))
                .findFirst();

        boolean impacto = ubicacionUnidadImpactada.isPresent();

        if (impacto) {
            ubicacionUnidadImpactada.get().getUnidad()
                    .setVida(ubicacionUnidadImpactada.get().getUnidad().getVida() - 1);

            mensaje = impacto ? "Haz impactado en una nave enemiga" : "Haz fallado el ataque";

            if (ubicacionUnidadImpactada.get().getUnidad().getVida() == 0) {
                tableroAtacado.addNaveDestruida();
                mensaje = "La nave ha sido destruida";
                estadoPartida = verificarEstadoPartida(tableroAtacado);
            }
            numeroNave = impacto ? ubicacionUnidadImpactada.get().getUnidad().getNumNave() : 0;
            vidaNave = impacto ? ubicacionUnidadImpactada.get().getUnidad().getVida() : 0;

            ModeloUbicacionUnidad ubicacionUnidad = ubicacionUnidadImpactada.get();

            ubicacionUnidad.getCasillas().entrySet().stream()
                    .filter(entry -> entry.getValue()) // Casillas activas
                    .filter(entry -> entry.getKey().getCoordenada().getX() == x
                    && entry.getKey().getCoordenada().getY() == y)
                    .findFirst()
                    .ifPresent(entry -> {
                        entry.setValue(false);
                        EstadoCasilla nuevoEstado = EstadoCasilla.IMPACTADA;

                        ModeloDisparo disparo = new ModeloDisparo(entry.getKey(), nuevoEstado);
                        tableroAtacado.addDisparoRecibido(disparo);
                    });
        } else {
            ModeloDisparo disparo = new ModeloDisparo(new ModeloCasilla(new ModeloCoordenada(x, y)), EstadoCasilla.VACIA);
            tableroAtacado.addDisparoRecibido(disparo);
        }
        // falta guardar los disparos que fallan
        if (estadoPartida) {
            // para el tiempo
            partida.finalizarPartida();
            return generarMensajeVictoria(ClientManager.getJugadorByClientId(clientId));
        }

        Map<String, Object> respuesta = generarRespuestaAtaque(impacto, mensaje, vidaNave, numeroNave, x, y, clientId,
                jugadorAtacado);

        return respuesta;
    }


    private Map<String, Object> generarEstadisticas(ModeloJugador jugador, ModeloTablero tableroPropio, ModeloTablero tableroEnemigo, long duracionPartida) {
        Map<String, Object> estadisticas = new HashMap<>();

        estadisticas.put("nombre", jugador.getNombre());
        estadisticas.put("naves_destruidas", tableroPropio.getNumNavesDestruidas());
        estadisticas.put("naves_restantes", 11 - tableroPropio.getNumNavesDestruidas());
        estadisticas.put("tiempo_partida", duracionPartida);

        int disparosAcertados = 0;
        int disparosFallados = 0;

        for (var disparo : tableroEnemigo.getDisparosRecibidos()) {
            if (disparo.getEstado() == EstadoCasilla.IMPACTADA) {
                disparosAcertados++;
            } else {
                disparosFallados++;
            }
        }

        estadisticas.put("disparos_acertados", disparosAcertados);
        estadisticas.put("disparos_fallados", disparosFallados);
        estadisticas.put("disparos_totales", tableroEnemigo.getDisparosRecibidos().size());

        return estadisticas;
    }
    
    public Map<String, Object> obtenerEstadisticasJugador(String clientId) {
        ModeloJugador jugadorPrincipal = ClientManager.getJugadorByClientId(clientId);
        ModeloJugador jugadorEnemigo = ClientManager.getOtherPlayer(clientId);

        ModeloTablero tableroPrincipal = partida.getTableroJugador(jugadorPrincipal.getId());
        ModeloTablero tableroEnemigo = partida.getTableroJugador(jugadorEnemigo.getId());

        Map<String, Object> jugadoresRespuestas = new HashMap<>();
        jugadoresRespuestas.put(clientId, generarEstadisticas(jugadorPrincipal, tableroPrincipal, tableroEnemigo, partida.getDuracion()));
        jugadoresRespuestas.put(jugadorEnemigo.getId(), generarEstadisticas(jugadorEnemigo, tableroEnemigo, tableroPrincipal, partida.getDuracion()));

        return jugadoresRespuestas;
    }

    private Map<String, Object> generarMensajeVictoriaPorRendicion(ModeloJugador jugadorGanador) {
        Map<String, Object> mensajeVictoria = new HashMap<>();

        mensajeVictoria.put("resultado", ControlPartida.PARTIDA_FINALIZADA.name());
        mensajeVictoria.put("mensaje", "El jugador oponente se ha rendido. " + jugadorGanador.getNombre() + " ha ganado la partida.");
        mensajeVictoria.put("accion", AccionesJugador.RENDIRSE.name());
        mensajeVictoria.put("ganador", jugadorGanador.getNombre());
        mensajeVictoria.put(ControlPartida.DETERMINAR_TURNO.name(), null);

        // Obtener las estadísticas
        Map<String, Object> estadisticas = obtenerEstadisticasJugador(jugadorGanador.getId());
        mensajeVictoria.put("estadisticas", estadisticas); // Agregar las estadísticas bajo la clave "estadisticas"

        // Agregar el tiempo de la partida
        String tiempoPartida = formatoDuracion(partida.getDuracion());
        mensajeVictoria.put("tiempo_partida", tiempoPartida);

        return mensajeVictoria;
    }

    private void iniciarNuevaPartida() {
        // Reiniciar la partida
        partida.ReiniciarPartida();
        partida.limpiarTableros();
        for (ModeloJugador jugador : partida.getJugadores()) {
            for (ModeloUnidad unidad : jugador.getUnidades()) {
                unidad.reiniciarVida();
            }
        }
    }

    public void volverAJugar(String clientId) {
        ModeloJugador jugador = ClientManager.getJugadorByClientId(clientId);
        ModeloJugador oponente = ClientManager.getOtherPlayer(clientId);

        if (jugador == null || oponente == null) {
            // Manejar error
            return;
        }

        jugador.setQuiereRevancha(true);

        if (oponente.isQuiereRevancha()) {
            // Ambos quieren jugar de nuevo
            iniciarNuevaPartida();
        } else {
            // Notificar al oponente que el jugador quiere volver a jugar
            Map<String, Object> mensajeOponente = new HashMap<>();
            mensajeOponente.put("accion", "OPONENTE_QUIERE_VOLVER_A_JUGAR");
            mensajeOponente.put("mensaje", jugador.getNombre() + " quiere volver a jugar");
            mensajeOponente.put("clientId", oponente.getId());

            MessageUtil.enviarMensaje(ClientManager.getClientSocket(oponente.getId()), mensajeOponente);
        }
    }

    public void respuestaVolverAJugar(String clientId, boolean acepta) {
        ModeloJugador jugador = ClientManager.getJugadorByClientId(clientId);
        ModeloJugador oponente = ClientManager.getOtherPlayer(clientId);

        if (jugador == null || oponente == null) {
            // Manejar error
            return;
        }

        if (acepta) {
            jugador.setQuiereRevancha(true);

            if (oponente.isQuiereRevancha()) {
                // Ambos quieren jugar de nuevo
                iniciarNuevaPartida();
            } else {
                // Esperar a que el oponente decida
                // Opcionalmente, puedes notificar al oponente que este jugador ha aceptado
            }
        } else {
            // Jugador rechazó volver a jugar
            jugador.setQuiereRevancha(false);

            // Notificar al oponente que el jugador no quiere volver a jugar
            Map<String, Object> mensajeOponente = new HashMap<>();
            mensajeOponente.put("accion", "OPONENTE_NO_QUIERE_VOLVER_A_JUGAR");
            mensajeOponente.put("mensaje", jugador.getNombre() + " no quiere volver a jugar.");
            mensajeOponente.put("clientId", oponente.getId());

            MessageUtil.enviarMensaje(ClientManager.getClientSocket(oponente.getId()), mensajeOponente);

            // Reiniciar los estados de revancha
            oponente.setQuiereRevancha(false);
        }
    }

    public Map<String, Object> salir(String clientId) {
        Map<String, Object> response = new HashMap<>();
        ModeloJugador jugador = ClientManager.getJugadorByClientId(clientId);
        ModeloJugador oponente = ClientManager.getOtherPlayer(clientId);

        // Resetear el estado de revancha de ambos jugadores
        jugador.setListo(false);
        oponente.setListo(false);
        jugador.setQuiereRevancha(false);
        oponente.setQuiereRevancha(false);
        for (ModeloJugador j : partida.getJugadores()) {
            for (ModeloUnidad unidad : j.getUnidades()) {
                unidad.reiniciarVida();
            }
        }

        if (jugador == null) {
            response.put("accion", "SALIR");
            response.put("error", "No se pudo encontrar al jugador.");
            return response;
        }

        // Notificar al oponente que el jugador ha salido
        if (oponente != null) {
            Map<String, Object> mensajeOponente = new HashMap<>();
            mensajeOponente.put("accion", "OPONENTE_SALIO");

            MessageUtil.enviarMensaje(ClientManager.getClientSocket(oponente.getId()), mensajeOponente);
        }

        // Remover al jugador de la partida
        partida.removerJugador(jugador);

        // Opcionalmente, limpiar la partida si no hay jugadores
        if (partida.getJugadores().isEmpty()) {
            partida.ReiniciarPartida();
            partida.limpiarTableros();
        }

        response.put("accion", "SALIR");
        response.put("mensaje", "Has salido de la partida.");

        return response;
    }
}
