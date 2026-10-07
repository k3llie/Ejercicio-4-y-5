import java.util.ArrayList;

public class Motocicleta extends Vehiculo {
    private int cilindraje;

    public Motocicleta(String placa, String marca, String modelo, double tarifaDiaria, int cilindraje) {

        super(placa, marca, modelo, tarifaDiaria);

        if (cilindraje <= 0) {
            throw new IllegalArgumentException("El cilindraje debe ser mayor que cero.");
        }

        this.cilindraje = cilindraje;
    }

    @Override
    public double calcularSubtotal(int dias) {
        if (dias <= 0) {
            throw new IllegalArgumentException("Los días de alquiler deben ser mayores que cero.");
        }

        double subtotal = getTarifaDiaria() * dias;

        if (cilindraje > 250) {
            subtotal += 75;
        }

        return subtotal;
    }

    @Override
    public int obtenerUmbralMantenimiento() {
        return 20;
    }

    @Override
    public boolean cumpleLicencia(ArrayList<TipoLicencia> licencias) {
        if (licencias == null) {
            return false;
        }

        return licencias.contains(TipoLicencia.M);
    }

    @Override
    public String obtenerCategoria() {
        return "Motocicleta";
    }

    @Override
    public String obtenerDescripcion() {
        return super.obtenerDescripcion() + ", cilindraje: " + cilindraje + " cc";
    }
}