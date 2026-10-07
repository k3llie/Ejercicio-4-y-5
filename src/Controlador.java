import java.util.ArrayList;

public class Controlador {
    private Vista vista;
    private RentaMovil rentaMovil;

    public Controlador(Vista vista) {
        if (vista == null) {
            throw new IllegalArgumentException("El controlador necesita una vista.");
        }

        this.vista = vista;
        this.rentaMovil = new RentaMovil();
        DatosIniciales datosIniciales = new DatosIniciales();
        datosIniciales.cargar(this.rentaMovil);
    }

    public void iniciar() {
        boolean continuar = true;

        while (continuar) {
            try {
                int opcion = vista.mostrarMenu();

                switch (opcion) {
                    case 1:
                        gestionarRegistroVehiculo();
                        break;
                    case 2:
                        gestionarRegistroCliente();
                        break;
                    case 3:
                        consultarVehiculos();
                        break;
                    case 4:
                        consultarClientes();
                        break;
                    case 5:
                        gestionarCotizacion();
                        break;
                    case 6:
                        gestionarAlquiler();
                        break;
                    case 7:
                        gestionarDevolucion();
                        break;
                    case 8:
                        gestionarFinMantenimiento();
                        break;
                    case 9:
                        gestionarReportes();
                        break;
                    case 0:
                        continuar = false;
                        vista.mostrarMensaje("Programa finalizado.");
                        break;
                    default:
                        vista.mostrarMensaje("Opción no válida.");
                }
            } 
            catch (IllegalArgumentException e) {
                vista.mostrarMensaje("Error: " + e.getMessage());
            } 
            catch (IllegalStateException e) {
                vista.mostrarMensaje("No se pudo realizar la operación:\n" + e.getMessage());
            }
        }
    }

    private void gestionarRegistroVehiculo() {
        vista.mostrarMensaje("\n===== REGISTRAR VEHÍCULO =====");
        vista.mostrarMensaje("1. Automóvil");
        vista.mostrarMensaje("2. Motocicleta");
        vista.mostrarMensaje("3. Camioneta de carga");
        vista.mostrarMensaje("4. Microbús");

        int categoria = vista.leerEntero("Seleccione la categoría: ");

        if (categoria < 1 || categoria > 4) {
            vista.mostrarMensaje("Categoría no válida.");
            return;
        }

        String placa = vista.leerTexto("Placa: ");
        String marca = vista.leerTexto("Marca: ");
        String modelo = vista.leerTexto("Modelo: ");
        double tarifaDiaria = vista.leerDecimal("Tarifa diaria: Q");

        Vehiculo vehiculo;

        switch (categoria) {
            case 1: {
                int pasajeros = vista.leerEntero("Cantidad de pasajeros: ");
                boolean automatica = vista.solicitarConfirmacion("¿La transmisión es automática?");

                vehiculo = new Automovil(placa, marca, modelo, tarifaDiaria, pasajeros, automatica);
                break;
            }

            case 2: {
                int cilindraje = vista.leerEntero("Cilindraje en cc: ");

                vehiculo = new Motocicleta(placa, marca, modelo, tarifaDiaria, cilindraje);
                break;
            }

            case 3: {
                double capacidad = vista.leerDecimal("Capacidad máxima en toneladas: ");

                vehiculo = new CamionetaCarga(placa, marca, modelo, tarifaDiaria, capacidad);
                break;
            }

            case 4: {
                int pasajeros = vista.leerEntero("Cantidad de pasajeros: ");
                boolean piloto = vista.solicitarConfirmacion("¿Incluye piloto de la empresa?");

                vehiculo = new Microbus(placa, marca, modelo, tarifaDiaria, pasajeros, piloto);
                break;
            }

            default:
                return;
        }

        if (rentaMovil.registrarVehiculo(vehiculo)) {
            vista.mostrarMensaje("Vehículo registrado correctamente.");
        } 
        else {
            vista.mostrarMensaje("Ya existe un vehículo con esa placa.");
        }
    }

    private void gestionarRegistroCliente() {
        vista.mostrarMensaje("\n===== REGISTRAR CLIENTE =====");
        vista.mostrarMensaje("1. Individual");
        vista.mostrarMensaje("2. Corporativo");

        int tipo = vista.leerEntero("Seleccione el tipo: ");

        if (tipo != 1 && tipo != 2) {
            vista.mostrarMensaje("Tipo de cliente no válido.");
            return;
        }

        String identificador;

        if (tipo == 1) {
            identificador = vista.leerTexto("DPI: ");
        } 
        else {
            identificador = vista.leerTexto("NIT: ");
        }

        String nombre = vista.leerTexto("Nombre del cliente: ");
        ArrayList<TipoLicencia> licencias = new ArrayList<>();

        int cantidad = vista.leerEntero("¿Cuántos tipos de licencia presentará? (1 a 4): ");

        while (cantidad < 1 || cantidad > 4) {
            vista.mostrarMensaje("Debe ingresar una cantidad entre 1 y 4.");
            cantidad = vista.leerEntero("Cantidad de tipos de licencia: ");
        }

        while (licencias.size() < cantidad) {
            String entrada = vista.leerTexto("Ingrese una licencia (A, B, C o M): ");

            TipoLicencia licencia;

            if (entrada.equalsIgnoreCase("A")) {
                licencia = TipoLicencia.A;
            } 
            else if (entrada.equalsIgnoreCase("B")) {
                licencia = TipoLicencia.B;
            } 
            else if (entrada.equalsIgnoreCase("C")) {
                licencia = TipoLicencia.C;
            } 
            else if (entrada.equalsIgnoreCase("M")) {
                licencia = TipoLicencia.M;
            } 
            else {
                vista.mostrarMensaje("Tipo de licencia no válido.");
                continue;
            }

            if (licencias.contains(licencia)) {
                vista.mostrarMensaje("Esa licencia ya fue ingresada.");
            } 
            else {
                licencias.add(licencia);
            }
        }

        Cliente cliente;

        if (tipo == 1) {
            cliente = new ClienteIndividual(identificador, nombre, licencias);
        } 
        else {
            String empresa = vista.leerTexto("Nombre de la empresa: ");
            String contacto = vista.leerTexto("Nombre del contacto: ");

            cliente = new ClienteCorporativo(identificador, nombre, licencias, empresa, contacto);
        }

        if (rentaMovil.registrarCliente(cliente)) {
            vista.mostrarMensaje("Cliente registrado correctamente.");
        } 
        else {
            vista.mostrarMensaje("Ya existe un cliente con ese identificador.");
        }
    }

    private void consultarVehiculos() {
        vista.mostrarVehiculos(rentaMovil.getVehiculos());
    }

    private void consultarClientes() {
        vista.mostrarClientes(rentaMovil.getClientes());
    }

    private void gestionarCotizacion() {
        String placa = vista.leerTexto("Placa del vehículo: ");
        String identificador = vista.leerTexto("Identificador del cliente: ");
        int dias = vista.leerEntero("Días de alquiler: ");

        Cotizacion cotizacion = rentaMovil.cotizar(placa, identificador, dias);

        vista.mostrarCotizacion(cotizacion);
    }

    private void gestionarAlquiler() {
        String placa = vista.leerTexto("Placa del vehículo: ");
        String identificador = vista.leerTexto("Identificador del cliente: ");
        int dias = vista.leerEntero("Días de alquiler: ");

        Cotizacion cotizacion = rentaMovil.cotizar(placa, identificador, dias);

        vista.mostrarCotizacion(cotizacion);

        if (!cotizacion.puedeAlquilar()) {
            return;
        }

        boolean acepta = vista.solicitarConfirmacion("¿Acepta el cobro y desea confirmar el alquiler?");

        if (!acepta) {
            vista.mostrarMensaje("Operación cancelada. No se registró ningún alquiler.");
            return;
        }

        Alquiler alquiler = rentaMovil.confirmarAlquiler(placa, identificador, dias);

        vista.mostrarMensaje("Alquiler confirmado correctamente.");

        ArrayList<Alquiler> comprobante = new ArrayList<>();
        comprobante.add(alquiler);
        vista.mostrarAlquileres(comprobante);
    }

    private void gestionarDevolucion() {
        String placa = vista.leerTexto("Placa del vehículo que se devuelve: ");

        if (rentaMovil.registrarDevolucion(placa)) {
            Vehiculo vehiculo = rentaMovil.buscarVehiculo(placa);

            vista.mostrarMensaje("Devolución registrada. Estado del vehículo: " + vehiculo.getEstado());
        } 
        else {
            vista.mostrarMensaje("No se pudo registrar la devolución. " + "El vehículo debe estar alquilado.");
        }
    }

    private void gestionarFinMantenimiento() {
        String placa = vista.leerTexto("Placa del vehículo: ");

        if (rentaMovil.finalizarMantenimiento(placa)) {
            vista.mostrarMensaje("Mantenimiento finalizado. El vehículo está disponible.");
        } 
        else {
            vista.mostrarMensaje("No se puede finalizar el mantenimiento: " + "el vehículo no está en mantenimiento.");
        }
    }

    private void gestionarReportes() {
        vista.mostrarMensaje("\n===== REPORTES =====");
        vista.mostrarMensaje("1. Vehículos e ingresos por categoría");
        vista.mostrarMensaje("2. Ingresos totales");
        vista.mostrarMensaje("3. Descuentos totales");
        vista.mostrarMensaje("4. Alquileres activos");
        vista.mostrarMensaje("5. Historial y total pagado por cliente");
        vista.mostrarMensaje("0. Volver");

        int opcion = vista.leerEntero("Seleccione un reporte: ");

        switch (opcion) {
            case 1:
                vista.mostrarResumenCategorias(rentaMovil.generarResumenCategorias());
                break;

            case 2:
                vista.mostrarMensaje("Ingresos totales: Q" + String.format("%.2f", rentaMovil.calcularIngresosTotales()));
                break;

            case 3:
                vista.mostrarMensaje("Descuentos otorgados: Q" + String.format("%.2f", rentaMovil.calcularDescuentosTotales()));
                break;

            case 4:
                vista.mostrarMensaje("\n===== ALQUILERES ACTIVOS =====");
                vista.mostrarAlquileres(rentaMovil.obtenerAlquileresActivos());
                break;

            case 5: {
                String identificador = vista.leerTexto("Identificador del cliente: ");

                ArrayList<Alquiler> historial = rentaMovil.obtenerHistorialCliente(identificador);

                vista.mostrarMensaje("\n===== HISTORIAL DEL CLIENTE =====");
                vista.mostrarAlquileres(historial);

                vista.mostrarMensaje("Total pagado: Q" + String.format("%.2f", rentaMovil.calcularTotalPagadoCliente(identificador)));
                break;
            }

            case 0:
                break;

            default:
                vista.mostrarMensaje("Opción no válida.");
        }
    }
}