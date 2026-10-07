import java.util.ArrayList;

public class ClienteIndividual extends Cliente {

    public ClienteIndividual(String identificador, String nombre, ArrayList<TipoLicencia> licencias) {

        super(identificador, nombre, licencias);

        if (!getIdentificador().matches("[0-9]{13}")) {
            throw new IllegalArgumentException("El DPI debe contener exactamente 13 dígitos.");
        }
    }

    @Override
    public int obtenerLimiteAlquileresActivos() {
        return 1;
    }

    @Override
    public double calcularDescuento(double subtotal, int alquileresConfirmadosPrevios) {

        if (!Double.isFinite(subtotal) || subtotal < 0) {
            throw new IllegalArgumentException("El subtotal debe ser un número no negativo.");
        }

        if (alquileresConfirmadosPrevios < 0) {
            throw new IllegalArgumentException("La cantidad de alquileres previos no puede ser negativa.");
        }

        if (alquileresConfirmadosPrevios >= 3) {
            return subtotal * 0.05;
        }

        return 0.0;
    }
}