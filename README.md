# Distributed Battleship Game - Advanced Software Architecture 

A real-time, distributed multiplayer Battleship game engineered in Java 17. The system implements a robust Client-Server topology synchronized via custom TCP/IP Sockets on Port 5000. It serves as a comprehensive demonstration of enterprise design patterns, event-driven reactive mechanics, and strict architectural separation under the MVVM paradigm.

## Architectural Topology & Styles

The application isolates network, presentation, and domain boundaries using state-of-the-art software patterns:

* Client-Server Architecture: Manages asynchronous remote communication via continuous TCP/IP Socket streams, coordinating instances across individual Client nodes and a centralized Server engine.
* MVVM (Model-View-ViewModel): Establishes complete separation of concerns on the client side. The View sends user inputs to the ViewModel, which alters states and reflects asynchronous server changes dynamically.
* Event-Driven Processing: Integrates a central Event Bus ("Bus de Eventos") inside the Server domain to publish state mutations and handle concurrent action-reaction pipelines.

## Design Patterns Applied

* Command Pattern: Encapsulates individual attack targets into behavioral objects via explicit interfaces (ICommand, AtacarCommand). A dedicated CommandManager governs execution paths and historical logging.
* Observer Pattern: Drives real-time reactive UI updating routines using decoupled registration protocols (IObserver, IObservable, VMTablero).
* State Pattern: Systematically transitions domain structures through strict lifecycle states (EstadoPartida: enPreparacion, enCurso, finalizada; EstadoNave: sinDaño, dañada, destruida).
* Strategy Pattern: Alternates user-interaction logic rules dynamically by switching active algorithms on the playing field board (ModoOrganizar, ModoEnemigo, ModoJugador).
* Business Object (BO Pattern): Isolates high-level enterprise verification scopes (PartidaBO) away from raw data definitions.

## Tech Stack & Deployment Parameters

* Environment Runtime: Java 17 JRE
* Build Automator: Maven Dependency Architecture (pom.xml configurations)
* Networking Layer: Low-level Java Networking (TCP/IP Sockets)
* Implementation Nodes:
    * Server Module (BN_Servidor -> ServidorJuego.jar)
    * Client Module (BN_Cliente -> ClienteJuego.jar)

## Installation & Technical Setup

1. Verify that Java 17 JRE is active within your global environment variables.
2. Initialize the backend tracking engine by deploying the server archive:
   java -jar BN_Servidor/target/ServidorJuego.jar
3. Spin up two distinct customer terminal windows to connect competing users:
   java -jar BN_Cliente/target/ClienteJuego.jar
