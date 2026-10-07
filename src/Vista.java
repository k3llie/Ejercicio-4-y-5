import java.util.ArrayList;
import java.util.Scanner;

public class Vista {
    private Scanner scanner;

    public Vista() {
        scanner = new Scanner(System.in);
    }

    public int mostrarMenu() {
        System.out.println("\n===== RENTAMOVIL =====");
        System.out.println("1. Registrar vehículo");
        System.out.println("2. Registrar cliente");
        System.out.println("3. Consultar vehículos");
        System.out.println("4. Consultar clientes");
        System.out.println("5. Cotizar");
        System.out.println("6. Alquilar vehículo");
        System.out.println("7. Registrar devolución");
        System.out.println("8. Finalizar mantenimiento");
        System.out.println("9. Consultar reportes");
        System.out.println("0. Salir");

        return leerEntero("Seleccione una opción: ");
    }

    public String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine().trim();
    }

    public int leerEntero(String mensaje) {
        while (true) {
            String entrada = leerTexto(mensaje);

            try {
                return Integer.parseInt(entrada);
            } 
            catch (NumberFormatException e) {
                mostrarMensaje("Ingrese un número entero válido.");
            }
        }
    }

    public double leerDecimal(String mensaje) {
        while (true) {
            String entrada = leerTexto(mensaje);

            try {
                double numero = Double.parseDouble(entrada);

                if (Double.isFinite(numero)) {
                    return numero;
                }

                mostrarMensaje("Ingrese un número finito.");
            } 
            catch (NumberFormatException e) {
                mostrarMensaje("Ingrese un número válido. Use punto para los decimales.");
            }
        }
    }

    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }

    public boolean solicitarConfirmacion(String mensaje) {
        while (true) {
            String respuesta = leerTexto(mensaje + " (s/n): ");

            if (respuesta.equalsIgnoreCase("s") || respuesta.equalsIgnoreCase("si") || respuesta.equalsIgnoreCase("sí")) {
                return true;
            }

            if (respuesta.equalsIgnoreCase("n") || respuesta.equalsIgnoreCase("no")) {
                return false;
            }

            mostrarMensaje("Responda s o n.");
        }
    }

    public void mostrarCotizacion(Cotizacion cotizacion) {
        System.out.println("\n===== COTIZACIÓN =====");
        System.out.println(
            "Cliente: " + cotizacion.getCliente().obtenerDescripcion()
        );
        System.out.println(
            "Vehículo: " + cotizacion.getVehiculo().obtenerDescripcion()
        );
        System.out.println("Días: " + cotizacion.getDias());

        System.out.printf(
            "Subtotal: Q%.2f%n", cotizacion.getSubtotal()
        );
        System.out.printf(
            "Descuento: Q%.2f%n", cotizacion.getDescuento()
        );
        System.out.printf(
            "Total: Q%.2f%n", cotizacion.getTotal()
        );

        if (cotizacion.puedeAlquilar()) {
            mostrarMensaje("El cliente puede alquilar en este momento.");
        } 
        else {
            mostrarMensaje("No se puede confirmar por estas razones:");

            for (String razon : cotizacion.getRazonesRechazo()) {
                mostrarMensaje("- " + razon);
            }
        }
    }

    public void mostrarVehiculos(ArrayList<Vehiculo> vehiculos) {
        System.out.println("\n===== FLOTA =====");

        if (vehiculos.isEmpty()) {
            mostrarMensaje("No hay vehículos registrados.");
            return;
        }

        for (Vehiculo vehiculo : vehiculos) {
            mostrarMensaje(
                vehiculo.obtenerCategoria() + ": "
                + vehiculo.obtenerDescripcion()
            );
        }
    }

    public void mostrarClientes(ArrayList<Cliente> clientes) {
        System.out.println("\n===== CLIENTES =====");

        if (clientes.isEmpty()) {
            mostrarMensaje("No hay clientes registrados.");
            return;
        }

        for (Cliente cliente : clientes) {
            mostrarMensaje(cliente.obtenerDescripcion());
        }
    }

    public void mostrarAlquileres(ArrayList<Alquiler> alquileres) {
        if (alquileres.isEmpty()) {
            mostrarMensaje("No hay alquileres para mostrar.");
            return;
        }

        for (Alquiler alquiler : alquileres) {
            System.out.println(
                "\nAlquiler #" + alquiler.getNumeroCorrelativo()
            );
            System.out.println(
                "Cliente: " + alquiler.getCliente().getIdentificador()
            );
            System.out.println(
                "Vehículo: " + alquiler.getVehiculo().getPlaca()
            );
            System.out.println("Días: " + alquiler.getDias());

            System.out.printf(
                "Subtotal: Q%.2f%n", alquiler.getSubtotal()
            );
            System.out.printf(
                "Descuento: Q%.2f%n", alquiler.getDescuento()
            );
            System.out.printf(
                "Total pagado: Q%.2f%n", alquiler.getTotal()
            );

            if (alquiler.getActivo()) {
                mostrarMensaje("Estado del alquiler: activo");
            } 
            else {
                mostrarMensaje("Estado del alquiler: finalizado");
            }
        }
    }

    public void mostrarResumenCategorias(ArrayList<ResumenCategoria> resumenes) {

        System.out.println("\n===== RESUMEN POR CATEGORÍA =====");

        if (resumenes.isEmpty()) {
            mostrarMensaje("No hay categorías registradas.");
            return;
        }

        for (ResumenCategoria resumen : resumenes) {
            System.out.println(
                "\nCategoría: " + resumen.getCategoria()
            );
            System.out.println(
                "Vehículos registrados: " + resumen.getCantidadVehiculos()
            );
            System.out.println(
                "Disponibles: " + resumen.getDisponibles()
            );
            System.out.println(
                "Alquilados: " + resumen.getAlquilados()
            );
            System.out.println(
                "En mantenimiento: " + resumen.getEnMantenimiento()
            );
            System.out.printf(
                "Ingresos: Q%.2f%n", resumen.getIngresos()
            );
        }
    }
}