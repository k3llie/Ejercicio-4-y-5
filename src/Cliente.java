import java.util.ArrayList;

public abstract class Cliente {
    private String identificador;
    private String nombre;
    private ArrayList<TipoLicencia> licencias;

    protected Cliente(String identificador, String nombre, ArrayList<TipoLicencia> licencias) {

        if (identificador == null || identificador.trim().isEmpty()) {
            throw new IllegalArgumentException("El identificador no puede estar vacío.");
        }

        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }

        if (licencias == null || licencias.isEmpty()) {
            throw new IllegalArgumentException("El cliente debe presentar al menos una licencia.");
        }

        if (licencias.contains(null)) {
            throw new IllegalArgumentException("Las licencias no pueden contener valores nulos.");
        }

        this.identificador = identificador.trim();
        this.nombre = nombre.trim();
        this.licencias = new ArrayList<>(licencias);
    }

    public abstract int obtenerLimiteAlquileresActivos();

    public abstract double calcularDescuento(double subtotal, int alquileresConfirmadosPrevios);

    public String obtenerDescripcion() {
        return "Identificador: " + identificador + ", nombre: " + nombre + ", licencias: " + licencias;
    }

    public String getIdentificador() {
        return identificador;
    }

    public ArrayList<TipoLicencia> getLicencias() {
        return new ArrayList<>(licencias);
    }
}