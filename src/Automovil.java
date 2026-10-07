import java.util.ArrayList;

public class Automovil extends Vehiculo {
    private int cantidadPasajeros;
    private boolean TransmisionAutomatica;

    public Automovil(String placa, String marca, String modelo, double tarifaDiaria, int cantidadPasajeros, boolean TransmisionAutomatica) {

        super(placa, marca, modelo, tarifaDiaria);

        if (cantidadPasajeros <= 0) {
            throw new IllegalArgumentException("La cantidad de pasajeros debe ser mayor que cero.");
        }

        this.cantidadPasajeros = cantidadPasajeros;
        this.TransmisionAutomatica = TransmisionAutomatica;
    }

    @Override
    public double calcularSubtotal(int dias) {
        if (dias <= 0) {
            throw new IllegalArgumentException("Los días de alquiler deben ser mayores que cero.");
        }

        double subtotal = getTarifaDiaria() * dias;

        if (TransmisionAutomatica) {
            subtotal += 50 * dias;
        }

        return subtotal;
    }

    @Override
    public int obtenerUmbralMantenimiento() {
        return 30;
    }

    @Override
    public boolean cumpleLicencia(ArrayList<TipoLicencia> licencias) {
        if (licencias == null) {
            return false;
        }

        return licencias.contains(TipoLicencia.A) || licencias.contains(TipoLicencia.B) || licencias.contains(TipoLicencia.C);
    }

    @Override
    public String obtenerCategoria() {
        return "Automovil";
    }

    @Override
    public String obtenerDescripcion() {
        String transmision;

        if (TransmisionAutomatica) {
            transmision = "automática";
        } 
        else {
            transmision = "manual";
        }

        return super.obtenerDescripcion() + ", cantidad de pasajeros: " + cantidadPasajeros + ", transmisión: " + transmision;
    }
}