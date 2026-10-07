public class Alquiler {
    private Cliente cliente;
    private Vehiculo vehiculo;
    private int numeroCorrelativo;
    private int dias;
    private double subtotal;
    private double descuento;
    private double total;
    private boolean activo;

    public Alquiler(int numeroCorrelativo, Cliente cliente, Vehiculo vehiculo, int dias, double subtotal, double descuento) {

        if (numeroCorrelativo <= 0) {
            throw new IllegalArgumentException("El número correlativo debe ser mayor que cero.");
        }

        if (cliente == null || vehiculo == null) {
            throw new IllegalArgumentException("El alquiler debe tener un cliente y un vehículo.");
        }

        if (dias <= 0) {
            throw new IllegalArgumentException("Los días de alquiler deben ser mayores que cero.");
        }

        if (!Double.isFinite(subtotal) || subtotal <= 0) {
            throw new IllegalArgumentException("El subtotal debe ser un número positivo.");
        }

        if (!Double.isFinite(descuento)
                || descuento < 0 || descuento > subtotal) {
            throw new IllegalArgumentException("El descuento debe estar entre cero y el subtotal.");
        }

        this.numeroCorrelativo = numeroCorrelativo;
        this.cliente = cliente;
        this.vehiculo = vehiculo;
        this.dias = dias;
        this.subtotal = subtotal;
        this.descuento = descuento;
        this.total = subtotal - descuento;
        this.activo = true;
    }

    public boolean finalizar() {
        if (!activo) {
            return false;
        }

        activo = false;
        return true;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public int getNumeroCorrelativo() {
        return numeroCorrelativo;
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

    public boolean getActivo() {
        return activo;
    }
}   