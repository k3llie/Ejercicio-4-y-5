import java.util.ArrayList;

public class Cotizacion {
    private Cliente cliente;
    private Vehiculo vehiculo;
    private int dias;
    private double subtotal;
    private double descuento;
    private double total;
    private ArrayList<String> razonesRechazo;

    public Cotizacion(Cliente cliente, Vehiculo vehiculo, int dias, double subtotal, double descuento, ArrayList<String> razonesRechazo) {

        if (cliente == null || vehiculo == null) {
            throw new IllegalArgumentException("La cotización debe tener un cliente y un vehículo.");
        }

        if (dias <= 0) {
            throw new IllegalArgumentException(
            "Los días de alquiler deben ser mayores que cero.");
        }

        if (!Double.isFinite(subtotal) || subtotal <= 0) {
            throw new IllegalArgumentException("El subtotal debe ser un número positivo.");
        }

        if (!Double.isFinite(descuento) || descuento < 0 || descuento > subtotal) {
            throw new IllegalArgumentException("El descuento debe estar entre cero y el subtotal.");
        }

        if (razonesRechazo == null) {
            throw new IllegalArgumentException("La lista de razones de rechazo no puede ser nula.");
        }

        this.cliente = cliente;
        this.vehiculo = vehiculo;
        this.dias = dias;
        this.subtotal = subtotal;
        this.descuento = descuento;
        this.total = subtotal - descuento;
        this.razonesRechazo = new ArrayList<>(razonesRechazo);
    }

    public boolean puedeAlquilar() {
        return razonesRechazo.isEmpty();
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public int getDias() {
        return dias;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public double getDescuento() {
        return descuento;
    }

    public double getTotal() {
        return total;
    }

    public ArrayList<String> getRazonesRechazo() {
        return new ArrayList<>(razonesRechazo);
    }
}