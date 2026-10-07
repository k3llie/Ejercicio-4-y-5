import java.util.ArrayList;

public class ClienteCorporativo extends Cliente {
    private String empresa;
    private String contacto;

    public ClienteCorporativo(String identificador, String nombre, ArrayList<TipoLicencia> licencias, String empresa, String contacto) {

        super(identificador, nombre, licencias);

        if (empresa == null || empresa.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la empresa no puede estar vacío.");
        }

        if (contacto == null || contacto.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del contacto no puede estar vacío.");
        }

        this.empresa = empresa.trim();
        this.contacto = contacto.trim();
    }

    @Override
    public int obtenerLimiteAlquileresActivos() {
        return 3;
    }

    @Override
    public double calcularDescuento(double subtotal, int alquileresConfirmadosPrevios) {

        if (!Double.isFinite(subtotal) || subtotal < 0) {
            throw new IllegalArgumentException("El subtotal debe ser un número no negativo.");
        }

        return subtotal * 0.10;
    }

    @Override
    public String obtenerDescripcion() {
        return super.obtenerDescripcion() + ", empresa: " + empresa + ", contacto: " + contacto;
    }
}