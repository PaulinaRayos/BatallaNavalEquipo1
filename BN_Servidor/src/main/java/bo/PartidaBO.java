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
 * Clase que gestiona la lógica principal de una partida de Batalla Naval.
 * Controla la creación de partidas, unión de jugadores, turnos, ataques y
 * finalización del juego.
 *
 * @author ivanochoa
 * @author paulvazquez
 * @author paulinarodriguez
 * @author cuauhtemocvazquez
 */
public class PartidaBO {

    private ModeloPartida partida;
    private UnidadBO unidadBO;

    /**
     * Constructor que inicializa la instancia de la partida y el gestor de
     * unidades.
     */
    public PartidaBO() {
        this.partida = ModeloPartida.getInstance();
        this.unidadBO = new UnidadBO();
    }

    /**
     * Crea una nueva partida con un código de acceso único.
     *
     * @param data Mapa con datos del jugador (nombre)
     * @param clientId Identificador único del cliente
     * @return Mapa con información de la partida creada
     * @throws IllegalStateException Si el cliente no está conectado
     */
    public Map<String, Object> crearPartida(Map<String, Object> data, String clientId) {
        // Verifica si se encuentra un cliente previamente registrado
        if (ClientManager.getClientSocket(clientId) == null) {
            throw new IllegalStateException("El cliente no está conectado.");
        }

        String codigoAcceso = UUID.randomUUID().toString().replace("-", "").substring(0, 5);
        partida.setCodigoAcceso(codigoAcceso);
        System.out.println("El codigo de acceso es: " + codigoAcceso);
        ModeloJugador host = crearJugador(clientId, (String) data.get("nombre"));
        partida.addJugador(host);
        // Se realiza una llamada al metodo para iniciar sus correspondientes:
        // tablero y unidades
        iniciarTableroyUnidades(clientId);
        ClientManager.addClient(ClientManager.getClientSocket(clientId), clientId, host);
        return toJSON.dataToJSON("accion", "CREAR_PARTIDA", "id", host.getId(), "codigo_acceso", codigoAcceso);
    }

    /**
     * Permite a un jugador unirse a una partida existente.
     *
     * @param data Mapa con código de acceso y nombre del jugador
     * @param clientId Identificador único del cliente
     * @return Mapa con resultado de la operación
     */
    public Map<String, Object> unirsePartida(Map<String, Object> data, String clientId) {
        String codigo_acceso = (String) data.get("codigo_acceso");
        System.out.println(codigo_acceso);
        if (!partida.getCodigoAcceso().equalsIgnoreCase(codigo_acceso)) {
            return toJSON.dataToJSON("accion", "UNIRSE_PARTIDA", "error", "El código de acceso no coincide");
        }
        ModeloJugador jugador = crearJugador(clientId, (String) data.get("nombre"));
        partida.addJugador(jugador);
        // Se realiza una llamada al metodo para iniciar sus correspondientes:
        // tablero y unidades
        iniciarTableroyUnidades(clientId);
        ClientManager.addClient(ClientManager.getClientSocket(clientId), clientId, jugador);
        partida.getJugadores().stream().forEach(p -> System.out.println("Jugador en la partida " + p.getId()));
        List<String> nombresJugadores = partida.getJugadores().stream()
                .map(ModeloJugador::getNombre)
                .collect(Collectors.toList());

        // Notifica a los jugadores activos que se ha unido un nuevo jugador
        notificarNuevoJugador(jugador);

        return toJSON.dataToJSON(
                "accion", "UNIRSE_PARTIDA",
                "id", jugador.getId(),
                "codigo_acceso", codigo_acceso,
                "nombres_jugadores", nombresJugadores);
    }

    /**
     * Notifica a todos los jugadores sobre un nuevo participante.
     *
     * @param nuevoJugador Jugador que se ha unido
     */
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

    /**
     * Crea una nueva instancia de jugador.
     *
     * @param id Identificador único
     * @param nombre Nombre del jugador
     * @return Instancia de ModeloJugador
     */
    public ModeloJugador crearJugador(String id, String nombre) {
        return new ModeloJugador(id, nombre);
    }

    public Map<String, Object> jugadorListo(Map<String, Object> request, String clientId) {
        ModeloJugador jugador = ClientManager.getJugadorByClientId(clientId);
        if (jugador != null) {
            jugador.setListo(true);

            // Notificar a los jugadores activos si el jugador está listo
            notificarEstadoListo(jugador);

            // Notificar si todos estan listos para jugar
            if (partida.todosLosJugadoresListos()) {
                // Reiniciar el estado listo
                reiniciarEstadoListo();

                // Notificar a los jugadores para cambiar al modo organización
                notificarIniciarOrganizar();
            }
        }
        return null;
    }

    /**
     * Notifica a todos los jugadores sobre el estado "listo" de un jugador
     * específico.
     *
     * @param jugadorListo Jugador cuyo estado ha cambiado a "listo"
     */
    private void notificarEstadoListo(ModeloJugador jugadorListo) {
        // Itera sobre todos los jugadores en la partida
        for (ModeloJugador jugador : partida.getJugadores()) {
            // Obtiene el socket de conexión del jugador
            Socket socketJugador = ClientManager.getClientSocket(jugador.getId());
            // Crea mensaje JSON con los datos del jugador listo
            Map<String, Object> mensaje = toJSON.dataToJSON(
                    "accion", "ACTUALIZAR_ESTADO_LISTO",
                    "id_jugador", jugadorListo.getId(),
                    "nombre_jugador", jugadorListo.getNombre(),
                    "listo", jugadorListo.isListo());
            // Envía la notificación al jugador actual
            MessageUtil.enviarMensaje(socketJugador, mensaje);
        }
    }

    /**
     * Notifica a todos los jugadores que todos los participantes están listos.
     */
    private void notificarTodosListos() {
        for (ModeloJugador jugador : partida.getJugadores()) {
            Socket socketJugador = ClientManager.getClientSocket(jugador.getId());
            // Mensaje simple indicando que todos están listos
            Map<String, Object> mensaje = toJSON.dataToJSON(
                    "accion", "TODOS_LISTOS");
            MessageUtil.enviarMensaje(socketJugador, mensaje);

        }
    }

    /**
     * Envía notificación para iniciar la fase de organización de barcos.
     */
    private void notificarIniciarOrganizar() {
        for (ModeloJugador jugador : partida.getJugadores()) {
            Socket socketJugador = ClientManager.getClientSocket(jugador.getId());
            Map<String, Object> mensaje = toJSON.dataToJSON(
                    "accion", "INICIAR_ORGANIZAR");
            MessageUtil.enviarMensaje(socketJugador, mensaje);
        }
    }

    /**
     * Reinicia el estado "listo" de todos los jugadores a falso. Útil para
     * preparar una nueva fase del juego.
     */
    public void reiniciarEstadoListo() {
        // Recorre todos los jugadores y los marca como no listos
        for (ModeloJugador jugador : partida.getJugadores()) {
            jugador.setListo(false);
        }
    }

    /**
     * Selecciona aleatoriamente al jugador que iniciará la partida. Usa un
     * generador de números aleatorios para mayor imparcialidad.
     */
    private void determinarJugadorInicial() {
        List<ModeloJugador> jugadores = partida.getJugadores();
        Random random = new Random();
        int indice = random.nextInt(jugadores.size());
        ModeloJugador jugadorInicial = jugadores.get(indice);
        partida.setJugadorTurno(jugadorInicial);
    }

    /**
     * Notifica a los demás jugadores que un jugador está esperando.
     *
     * @param jugadorListo Jugador que está listo y esperando a los demás
     */
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

    /**
     * Reinicia completamente el estado de la partida. Prepara el juego para una
     * nueva ronda.
     */
    public void reiniciarPartida() {
        partida.ReiniciarPartida();
    }

    /**
     * Finaliza la partida actual y limpia la instancia singleton.
     */
    public void finalizarPartida() {
        ModeloPartida.instance = null;
    }

    /**
     * Convierte milisegundos a formato MM:SS para mostrar duración.
     *
     * @param duracionMilisegundos Tiempo en milisegundos
     * @return String formateado como minutos:segundos
     */
    private String formatoDuracion(long duracionMilisegundos) {
        long segundos = duracionMilisegundos / 1000;
        long minutos = segundos / 60;
        segundos = segundos % 60;
        return String.format("%02d:%02d", minutos, segundos);
    }

    /**
     * Notifica el inicio oficial del juego a todos los jugadores. Incluye
     * información sobre quién comienza y el nombre del oponente.
     */
    private void notificarIniciarJuego() {
        ModeloJugador jugadorInicial = partida.getJugadorTurno();
        List<ModeloJugador> jugadores = partida.getJugadores();

        for (ModeloJugador jugador : jugadores) {
            Socket socketJugador = ClientManager.getClientSocket(jugador.getId());
            boolean esSuTurno = jugador.getId().equals(jugadorInicial.getId());

            // Obtiene el nombre del jugador enemigo
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

    /**
     * Procesa la colocación de unidades en el tablero por un jugador.
     *
     * @param request Datos de la solicitud con información de unidades
     * @param clientId ID del jugador que coloca las unidades
     */
    public void colocarUnidadTablero(Map<String, Object> request, String clientId) {
        // Obtiene el tablero y datos del jugador
        ModeloTablero tableroUsuario = this.partida.getTableros().get(clientId);
        ModeloJugador jugador = ClientManager.getJugadorByClientId(clientId);

        // Procesa cada unidad en la solicitud
        List<Map<String, Object>> unidadesData = (List<Map<String, Object>>) request.get("unidades");

        for (Map<String, Object> unidadData : unidadesData) {
            int numNave = (Integer) unidadData.get("numNave");
            ModeloUnidad unidad = unidadBO.obtenerUnidadPorNumNave(jugador, numNave);

            if (unidad == null) {
                System.out.println("No se encontro la unidad");
                break;
            }

            // Obtiene y registra las coordenadas de la unidad
            Map<ModeloCasilla, Boolean> casillas = unidadBO.obtenerCoordenadas(unidadData);

            ModeloUbicacionUnidad ubicacionUnidad = new ModeloUbicacionUnidad(unidad, casillas);

            tableroUsuario.agregarUbicacion(ubicacionUnidad);
        }

        // Actualiza estado y notifica
        jugador.setListo(true);
        notificarEstadoListo(jugador);

        System.out.println("Jugador: " + clientId);
        System.out.println(tableroUsuario.getUnidades());

        // Verifica si todos están listos para comenzar
        if (partida.todosLosJugadoresListos()) {
            // iniciar el temporizador
            partida.iniciarPartida();

            // Notificar para iniciar el juego
            determinarJugadorInicial();
            notificarIniciarJuego();
        } else {
            notificarJugadorEsperando(jugador);
        }
    }

    /**
     * Inicializa el tablero y las unidades del jugador especificado. Se crean
     * las 11 unidades (2 portaaviones, 2 cruceros, 4 submarinos y 3 barcos) y
     * se asignan al jugador en la partida.
     *
     * @param clientId ID del jugador.
     */
    private void iniciarTableroyUnidades(String clientId) {
        // Se añade el tablero del jugador
        partida.addTablero(clientId, new ModeloTablero());

        // Lista para almacenar las unidades del jugador
        List<ModeloUnidad> unidades = new ArrayList<>();

        int numNave = 1;
        // Crear 2 portaaviones
        while (numNave <= 2) {
            ModeloUnidad nave = UnidadFactory.crearUnidad(ModeloTipoUnidad.PORTAAVIONES.NOMBRE);
            nave.setNumNave(numNave);
            unidades.add(nave);
            numNave++;
        }
        // Generación de cruceros
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
        // Generación de los barcos
        while (numNave <= 11) {
            ModeloUnidad nave = UnidadFactory.crearUnidad(ModeloTipoUnidad.BARCO.NOMBRE);
            nave.setNumNave(numNave);
            unidades.add(nave);
            numNave++;
        }

        // Asigna la lista de unidades al jugador correspondiente
        for (ModeloJugador jugador : partida.getJugadores()) {
            if (jugador.getId() == clientId) {
                jugador.setUnidades(unidades);
            }
        }
    }

    /**
     * Construye la respuesta estructurada de un ataque, tanto para el atacante
     * como para el defensor.
     *
     * @param impacto Indica si el ataque fue exitoso
     * @param mensaje Mensaje descriptivo del resultado del ataque
     * @param vidaNave Vida restante de la nave atacada
     * @param numeroNave Identificador numérico de la nave
     * @param x Coordenada X del ataque
     * @param y Coordenada Y del ataque
     * @param clientId ID del jugador que atacó
     * @param jugadorAtacado Objeto que representa al jugador defensor
     * @return Un mapa con la respuesta estructurada para ambos jugadores
     */
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

    /**
     * Verifica si todas las naves han sido destruidas en el tablero del
     * jugador.
     *
     * @param tablero Tablero del jugador
     * @return true si todas las naves están destruidas; false en caso contrario
     */
    public boolean verificarEstadoPartida(ModeloTablero tablero) {
        return tablero.getNumNavesDestruidas() == 11;
    }

    /**
     * Genera un mensaje de victoria para notificar el final del juego y
     * estadísticas del ganador.
     *
     * @param jugadorGanador Jugador que ha ganado la partida
     * @return Mapa con los datos del mensaje de victoria
     */
    private Map<String, Object> generarMensajeVictoria(ModeloJugador jugadorGanador) {
        Map<String, Object> mensajeVictoria = new HashMap<>();

        mensajeVictoria.put("resultado", ControlPartida.PARTIDA_FINALIZADA.name());
        mensajeVictoria.put("mensaje", "¡Felicidades! " + jugadorGanador.getNombre() + " ha ganado la partida.");
        mensajeVictoria.put("accion", AccionesJugador.ATACAR.name());
        mensajeVictoria.put("ganador", jugadorGanador.getNombre());
        mensajeVictoria.put(ControlPartida.DETERMINAR_TURNO.name(), null);

        Map<String, Object> estadisticas = obtenerEstadisticasJugador(jugadorGanador.getId());
        mensajeVictoria.put("estadisticas", estadisticas);

        // Se añade el tiempo de la partida
        String tiempoPartida = formatoDuracion(partida.getDuracion());
        mensajeVictoria.put("tiempo_partida", tiempoPartida);

        return mensajeVictoria;
    }

    /**
     * Permite a un jugador rendirse y declara automáticamente como ganador al
     * oponente.
     *
     * @param request Datos de la solicitud de rendición
     * @param clientId ID del jugador que se rinde
     */
    public void rendirse(Map<String, Object> request, String clientId) {
        ModeloJugador jugadorQueSeRinde = ClientManager.getJugadorByClientId(clientId);
        if (jugadorQueSeRinde == null) {
            // Si el jugador no existe,  manejar error
            return;
        }

        ModeloJugador jugadorGanador = ClientManager.getOtherPlayer(clientId);

        if (jugadorGanador == null) {
            // Si el jugador enemigo no existe, manejar error
            return;
        }

        // Fin partida
        partida.finalizarPartida();
        partida.setGanador(jugadorGanador);

        // Nuevo mensaje de victoria y estadísticas
        Map<String, Object> mensajeVictoria = generarMensajeVictoria(jugadorGanador);

        // Enviar el mensaje a todos los jugadores
        Socket jugadorSocket = ClientManager.getClientSocket(jugadorGanador.getId());
        MessageUtil.enviarMensaje(jugadorSocket, mensajeVictoria);

        Socket otroJugadorSocket = ClientManager.getClientSocket(jugadorQueSeRinde.getId());
        MessageUtil.enviarMensaje(otroJugadorSocket, mensajeVictoria);
    }

    /**
     * Procesa la lógica de un ataque en el tablero enemigo, determina si fue
     * exitoso y evalúa el estado de la partida.
     *
     * @param data Información del ataque (coordenadas, etc.)
     * @param clientId ID del jugador que realiza el ataque
     * @return Mapa con la respuesta estructurada del ataque
     */
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

    /**
     * Genera un conjunto de estadísticas sobre el desempeño del jugador durante
     * la partida.
     *
     * @param jugador Jugador evaluado
     * @param tableroPropio Tablero propio del jugador
     * @param tableroEnemigo Tablero del oponente
     * @param duracionPartida Duración total de la partida
     * @return Mapa con estadísticas del jugador
     */
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

    /**
     * Devuelve las estadísticas tanto del jugador principal como del oponente.
     *
     * @param clientId ID del jugador principal
     * @return Mapa con las estadísticas de ambos jugadores
     */
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

        // Agregar el estado de partida finalizada
        mensajeVictoria.put("resultado", ControlPartida.PARTIDA_FINALIZADA.name());

        // Mensaje indicando que el oponente se ha rendido
        mensajeVictoria.put("mensaje", "El jugador oponente se ha rendido. " + jugadorGanador.getNombre() + " ha ganado la partida.");

        // Acción que causó la victoria
        mensajeVictoria.put("accion", AccionesJugador.RENDIRSE.name());

        // Nombre del jugador que ganó
        mensajeVictoria.put("ganador", jugadorGanador.getNombre());

        // No hay siguiente turno
        mensajeVictoria.put(ControlPartida.DETERMINAR_TURNO.name(), null);

        // Obtener estadísticas del jugador ganador
        Map<String, Object> estadisticas = obtenerEstadisticasJugador(jugadorGanador.getId());
        mensajeVictoria.put("estadisticas", estadisticas); // Agregar estadísticas

        // Agregar duración de la partida
        String tiempoPartida = formatoDuracion(partida.getDuracion());
        mensajeVictoria.put("tiempo_partida", tiempoPartida);

        return mensajeVictoria;
    }

    private void iniciarNuevaPartida() {
        // Reiniciar los datos de la partida
        partida.ReiniciarPartida();
        partida.limpiarTableros();

        // Reiniciar vida de todas las unidades de todos los jugadores
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
            // Manejar error si alguno no se encuentra
            return;
        }

        // Marcar que este jugador quiere revancha
        jugador.setQuiereRevancha(true);

        if (oponente.isQuiereRevancha()) {
            // Ambos jugadores quieren revancha
            iniciarNuevaPartida();
        } else {
            // Notificar al oponente que este jugador quiere jugar de nuevo
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
            // Manejar error si alguno no se encuentra
            return;
        }

        if (acepta) {
            // El jugador acepta la revancha
            jugador.setQuiereRevancha(true);

            if (oponente.isQuiereRevancha()) {
                // Ambos aceptan, iniciar nueva partida
                iniciarNuevaPartida();
            } else {
                // Esperar respuesta del oponente
                // Se puede notificar opcionalmente
            }
        } else {
            // El jugador rechaza la revancha
            jugador.setQuiereRevancha(false);

            // Notificar al oponente que no se jugará otra vez
            Map<String, Object> mensajeOponente = new HashMap<>();
            mensajeOponente.put("accion", "OPONENTE_NO_QUIERE_VOLVER_A_JUGAR");
            mensajeOponente.put("mensaje", jugador.getNombre() + " no quiere volver a jugar.");
            mensajeOponente.put("clientId", oponente.getId());

            MessageUtil.enviarMensaje(ClientManager.getClientSocket(oponente.getId()), mensajeOponente);

            // Reiniciar estados de revancha
            oponente.setQuiereRevancha(false);
        }
    }

    public Map<String, Object> salir(String clientId) {
        Map<String, Object> response = new HashMap<>();
        ModeloJugador jugador = ClientManager.getJugadorByClientId(clientId);
        ModeloJugador oponente = ClientManager.getOtherPlayer(clientId);

        // Resetear estados de revancha y listo
        jugador.setListo(false);
        oponente.setListo(false);
        jugador.setQuiereRevancha(false);
        oponente.setQuiereRevancha(false);

        // Reiniciar vida de todas las unidades de todos los jugadores
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

        // Notificar al oponente que el jugador salió
        if (oponente != null) {
            Map<String, Object> mensajeOponente = new HashMap<>();
            mensajeOponente.put("accion", "OPONENTE_SALIO");

            MessageUtil.enviarMensaje(ClientManager.getClientSocket(oponente.getId()), mensajeOponente);
        }

        // Remover jugador de la partida
        partida.removerJugador(jugador);

        // Si ya no hay jugadores, reiniciar la partida
        if (partida.getJugadores().isEmpty()) {
            partida.ReiniciarPartida();
            partida.limpiarTableros();
        }

        response.put("accion", "SALIR");
        response.put("mensaje", "Has salido de la partida.");

        return response;
    }
}
