public class ResumenCategoria {
    private String categoria;
    private int cantidadVehiculos;
    private int disponibles;
    private int alquilados;
    private int enMantenimiento;
    private double ingresos;

    public ResumenCategoria(String categoria) {
        if (categoria == null || categoria.trim().isEmpty()) {
            throw new IllegalArgumentException("La categoría no puede estar vacía.");
        }

        this.categoria = categoria.trim();
        this.cantidadVehiculos = 0;
        this.disponibles = 0;
        this.alquilados = 0;
        this.enMantenimiento = 0;
        this.ingresos = 0.0;
    }

    public void acumularVehiculo(EstadoVehiculo estado) {
        if (estado == null) {
            throw new IllegalArgumentException("El estado del vehículo no puede ser nulo.");
        }

        cantidadVehiculos++;

        switch (estado) {
            case DISPONIBLE:
                disponibles++;
                break;

            case ALQUILADO:
                alquilados++;
                break;

            case MANTENIMIENTO:
                enMantenimiento++;
                break;
        }
    }

    public void acumularIngreso(double monto) {
        if (!Double.isFinite(monto) || monto < 0) {
            throw new IllegalArgumentException("El ingreso debe ser un número no negativo.");
        }

        ingresos += monto;
    }

    public String getCategoria() {
        return categoria;
    }

    public int getCantidadVehiculos() {
        return cantidadVehiculos;
    }

    public int getDisponibles() {
        return disponibles;
    }

    public int getAlquilados() {
        return alquilados;
    }

    public int getEnMantenimiento() {
        return enMantenimiento;
    }

    public double getIngresos() {
        return ingresos;
    }
}