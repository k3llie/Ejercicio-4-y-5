import java.util.ArrayList;

public class RentaMovil {
    private ArrayList<Vehiculo> vehiculos;
    private ArrayList<Cliente> clientes;
    private ArrayList<Alquiler> alquileres;
    private int siguienteNumeroAlquiler;

    public RentaMovil() {
        vehiculos = new ArrayList<>();
        clientes = new ArrayList<>();
        alquileres = new ArrayList<>();
        siguienteNumeroAlquiler = 1;
    }

    public boolean registrarVehiculo(Vehiculo vehiculo) {
        if (vehiculo == null) {
            throw new IllegalArgumentException("Debe proporcionar un vehículo.");
        }

        if (buscarVehiculo(vehiculo.getPlaca()) != null) {
            return false;
        }

        vehiculos.add(vehiculo);
        return true;
    }

    public Vehiculo buscarVehiculo(String placa) {
        if (placa == null || placa.trim().isEmpty()) {
            throw new IllegalArgumentException("La placa no puede estar vacía.");
        }

        String placaBuscada = placa.trim();

        for (Vehiculo vehiculo : vehiculos) {
            if (vehiculo.getPlaca().equalsIgnoreCase(placaBuscada)) {
                return vehiculo;
            }
        }

        return null;
    }

    public boolean registrarCliente(Cliente cliente) {
        if (cliente == null) {
            throw new IllegalArgumentException("Debe proporcionar un cliente.");
        }

        if (buscarCliente(cliente.getIdentificador()) != null) {
            return false;
        }

        clientes.add(cliente);
        return true;
    }

    public Cliente buscarCliente(String identificador) {
        if (identificador == null
                || identificador.trim().isEmpty()) {
            throw new IllegalArgumentException("El identificador no puede estar vacío.");
        }

        String identificadorBuscado = identificador.trim();

        for (Cliente cliente : clientes) {
            if (cliente.getIdentificador().equalsIgnoreCase(identificadorBuscado)) {
                return cliente;
            }
        }

        return null;
    }

    public int contarAlquileresActivos(Cliente cliente) {
        if (cliente == null) {
            throw new IllegalArgumentException("Debe proporcionar un cliente.");
        }

        int cantidad = 0;

        for (Alquiler alquiler : alquileres) {
            if (alquiler.getCliente().getIdentificador().equalsIgnoreCase(cliente.getIdentificador()) && alquiler.getActivo()) {
                cantidad++;
            }
        }

        return cantidad;
    }

    public int contarAlquileresConfirmados(Cliente cliente) {
        if (cliente == null) {
            throw new IllegalArgumentException("Debe proporcionar un cliente.");
        }

        int cantidad = 0;

        for (Alquiler alquiler : alquileres) {
            if (alquiler.getCliente().getIdentificador().equalsIgnoreCase(cliente.getIdentificador())) {
                cantidad++;
            }
        }

        return cantidad;
    }

    public Cotizacion cotizar(String placa, String identificador, int dias) {
        if (dias <= 0) {
            throw new IllegalArgumentException("Los días de alquiler deben ser mayores que cero.");
        }

        Vehiculo vehiculo = buscarVehiculo(placa);
        Cliente cliente = buscarCliente(identificador);

        if (vehiculo == null) {
            throw new IllegalArgumentException("No existe un vehículo con esa placa.");
        }

        if (cliente == null) {
            throw new IllegalArgumentException("No existe un cliente con ese identificador.");
        }

        ArrayList<String> razonesRechazo = new ArrayList<>();

        if (vehiculo.getEstado() != EstadoVehiculo.DISPONIBLE) {
            razonesRechazo.add("El vehículo no está disponible. Estado: " + vehiculo.getEstado());
        }

        if (!vehiculo.cumpleLicencia(cliente.getLicencias())) {
            razonesRechazo.add("Las licencias del cliente no autorizan este vehículo.");
        }

        int activos = contarAlquileresActivos(cliente);

        if (activos >= cliente.obtenerLimiteAlquileresActivos()) {
            razonesRechazo.add("El cliente alcanzó su límite de alquileres activos.");
        }

        double subtotal = vehiculo.calcularSubtotal(dias);

        int confirmadosPrevios = contarAlquileresConfirmados(cliente);

        double descuento = cliente.calcularDescuento(
            subtotal, confirmadosPrevios
        );

        return new Cotizacion(
            cliente, vehiculo, dias,
            subtotal, descuento, razonesRechazo
        );
    }

    public Alquiler confirmarAlquiler(String placa, String identificador, int dias) {

        Cotizacion cotizacion = cotizar(placa, identificador, dias);

        if (!cotizacion.puedeAlquilar()) {
            throw new IllegalStateException(String.join("\n", cotizacion.getRazonesRechazo()));
        }

       
        Alquiler alquiler = new Alquiler(siguienteNumeroAlquiler, cotizacion.getCliente(), cotizacion.getVehiculo(), 
            cotizacion.getDias(), cotizacion.getSubtotal(), cotizacion.getDescuento()
        );

        if (!cotizacion.getVehiculo().marcarAlquilado()) {
            throw new IllegalStateException("El vehículo ya no está disponible.");
        }

        alquileres.add(alquiler);
        siguienteNumeroAlquiler++;

        return alquiler;
    }

    public Alquiler buscarAlquilerActivo(String placa) {
        if (placa == null || placa.trim().isEmpty()) {
            throw new IllegalArgumentException("La placa no puede estar vacía.");
        }

        String placaBuscada = placa.trim();

        for (Alquiler alquiler : alquileres) {
            if (alquiler.getActivo() && alquiler.getVehiculo().getPlaca().equalsIgnoreCase(placaBuscada)) {
                return alquiler;
            }
        }

        return null;
    }

    public boolean registrarDevolucion(String placa) {
        Vehiculo vehiculo = buscarVehiculo(placa);

        if (vehiculo == null) {
            throw new IllegalArgumentException("No existe un vehículo con esa placa.");
        }

        if (vehiculo.getEstado() != EstadoVehiculo.ALQUILADO) {
            return false;
        }

        Alquiler alquiler = buscarAlquilerActivo(placa);

        if (alquiler == null) {
            throw new IllegalStateException("El vehículo figura alquilado, pero no tiene " + "un alquiler activo registrado.");
        }

        if (!vehiculo.registrarDevolucion(alquiler.getDias())) {
            return false;
        }

        alquiler.finalizar();
        return true;
    }

    public boolean finalizarMantenimiento(String placa) {
        Vehiculo vehiculo = buscarVehiculo(placa);

        if (vehiculo == null) {
            throw new IllegalArgumentException("No existe un vehículo con esa placa.");
        }

        return vehiculo.finalizarMantenimiento();
    }

    public double calcularIngresosTotales() {
        double ingresos = 0.0;

        for (Alquiler alquiler : alquileres) {
            ingresos += alquiler.getTotal();
        }

        return ingresos;
    }

    public double calcularDescuentosTotales() {
        double descuentos = 0.0;

        for (Alquiler alquiler : alquileres) {
            descuentos += alquiler.getDescuento();
        }

        return descuentos;
    }

    public ArrayList<Alquiler> obtenerAlquileresActivos() {
        ArrayList<Alquiler> activos = new ArrayList<>();

        for (Alquiler alquiler : alquileres) {
            if (alquiler.getActivo()) {
                activos.add(alquiler);
            }
        }

        return activos;
    }

    public ArrayList<Alquiler> obtenerHistorialCliente(String identificador) {

        Cliente cliente = buscarCliente(identificador);

        if (cliente == null) {
            throw new IllegalArgumentException("No existe un cliente con ese identificador.");
        }

        ArrayList<Alquiler> historial = new ArrayList<>();

        for (Alquiler alquiler : alquileres) {
            if (alquiler.getCliente().getIdentificador().equalsIgnoreCase(cliente.getIdentificador())) {
                historial.add(alquiler);
            }
        }

        return historial;
    }

    public double calcularTotalPagadoCliente(String identificador) {
        ArrayList<Alquiler> historial = obtenerHistorialCliente(identificador);

        double totalPagado = 0.0;

        for (Alquiler alquiler : historial) {
            totalPagado += alquiler.getTotal();
        }

        return totalPagado;
    }

    public ArrayList<ResumenCategoria> generarResumenCategorias() {
        ArrayList<ResumenCategoria> resumenes = new ArrayList<>();


        for (Vehiculo vehiculo : vehiculos) {
            String categoria = vehiculo.obtenerCategoria();
            ResumenCategoria resumenEncontrado = null;

            for (ResumenCategoria resumen : resumenes) {
                if (resumen.getCategoria().equals(categoria)) {
                    resumenEncontrado = resumen;
                    break;
                }
            }

            if (resumenEncontrado == null) {
                resumenEncontrado = new ResumenCategoria(categoria);
                resumenes.add(resumenEncontrado);
            }

            resumenEncontrado.acumularVehiculo(vehiculo.getEstado());
        }

   
        for (Alquiler alquiler : alquileres) {
            String categoria =
                alquiler.getVehiculo().obtenerCategoria();

            for (ResumenCategoria resumen : resumenes) {
                if (resumen.getCategoria().equals(categoria)) {
                    resumen.acumularIngreso(alquiler.getTotal());
                    break;
                }
            }
        }

        return resumenes;
    }

    public ArrayList<Vehiculo> getVehiculos() {
        return new ArrayList<>(vehiculos);
    }

    public ArrayList<Cliente> getClientes() {
        return new ArrayList<>(clientes);
    }
}