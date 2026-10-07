import java.util.ArrayList;

public class CamionetaCarga extends Vehiculo {
    private double capacidadMaxima;

    public CamionetaCarga(String placa, String marca, String modelo, double tarifaDiaria, double capacidadMaxima) {

        super(placa, marca, modelo, tarifaDiaria);

        if (!Double.isFinite(capacidadMaxima) || capacidadMaxima <= 0) {
            throw new IllegalArgumentException("La capacidad máxima debe ser un número positivo.");
        }

        this.capacidadMaxima = capacidadMaxima;
    }

    @Override
    public double calcularSubtotal(int dias) {
        if (dias <= 0) {
            throw new IllegalArgumentException("Los días de alquiler deben ser mayores que cero.");
        }

        double subtotal = getTarifaDiaria() * dias;
        double recargo = 100 * capacidadMaxima * dias;

        return subtotal + recargo;
    }

    @Override
    public int obtenerUmbralMantenimiento() {
        return 15;
    }

    @Override
    public boolean cumpleLicencia(ArrayList<TipoLicencia> licencias) {
        if (licencias == null) {
            return false;
        }

        return licencias.contains(TipoLicencia.A) || licencias.contains(TipoLicencia.B);
    }

    @Override
    public String obtenerCategoria() {
        return "CamionetaCarga";
    }

    @Override
    public String obtenerDescripcion() {
        return super.obtenerDescripcion() + ", capacidad máxima: " + capacidadMaxima + " toneladas";
    }
}