import java.util.ArrayList;

public class Microbus extends Vehiculo {
    private int cantidadPasajeros;
    private boolean incluyePiloto;

    public Microbus(String placa, String marca, String modelo, double tarifaDiaria, int cantidadPasajeros, boolean incluyePiloto) {

        super(placa, marca, modelo, tarifaDiaria);

        if (cantidadPasajeros <= 0) {
            throw new IllegalArgumentException("La cantidad de pasajeros debe ser mayor que cero.");
        }

        this.cantidadPasajeros = cantidadPasajeros;
        this.incluyePiloto = incluyePiloto;
    }

    @Override
    public double calcularSubtotal(int dias) {
        if (dias <= 0) {
            throw new IllegalArgumentException("Los días de alquiler deben ser mayores que cero.");
        }

        double subtotal = getTarifaDiaria() * dias;

        if (incluyePiloto) {
            subtotal += 250.0 * dias;
        }

        return subtotal;
    }

    @Override
    public int obtenerUmbralMantenimiento() {
        return 25;
    }

    @Override
    public boolean cumpleLicencia(ArrayList<TipoLicencia> licencias) {
        if (incluyePiloto) {
            return true;
        }

        if (licencias == null) {
            return false;
        }

        return licencias.contains(TipoLicencia.A) || licencias.contains(TipoLicencia.B);
    }

    @Override
    public String obtenerCategoria() {
        return "Microbus";
    }

    @Override
    public String obtenerDescripcion() {
        String piloto;

        if (incluyePiloto) {
            piloto = "sí";
        } 
        else {
            piloto = "no";
        }

        return super.obtenerDescripcion() + ", cantidad de pasajeros: " + cantidadPasajeros + ", incluye piloto: " + piloto;
    }
}