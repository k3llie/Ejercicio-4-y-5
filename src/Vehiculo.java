import java.util.ArrayList;

public abstract class Vehiculo {
    private String placa;
    private String marca;
    private String modelo;
    private double tarifaDiaria;
    private EstadoVehiculo estado;
    private int diasAcumulados;

    protected Vehiculo(String placa, String marca, String modelo, double tarifaDiaria){

        if (placa == null || placa.trim().isEmpty()) {
            throw new IllegalArgumentException("La placa no puede estar vacía.");
        }

        if (marca == null || marca.trim().isEmpty()) {
            throw new IllegalArgumentException("La marca no puede estar vacía.");
        }

        if (modelo == null || modelo.trim().isEmpty()) {
            throw new IllegalArgumentException("El modelo no puede estar vacío.");
        }

        if (!Double.isFinite(tarifaDiaria) || tarifaDiaria <= 0) {
            throw new IllegalArgumentException("La tarifa diaria debe ser un número positivo.");
        }

        this.placa = placa.trim();
        this.marca = marca.trim(); //.trim() quita los espacios que podrían ponerse por error al inicio y final del texto
        this.modelo = modelo.trim();
        this.tarifaDiaria = tarifaDiaria;
        this.estado = EstadoVehiculo.DISPONIBLE;
        this.diasAcumulados = 0;
    }    

    public abstract double calcularSubtotal(int dias);

    public abstract int obtenerUmbralMantenimiento();

    public abstract boolean cumpleLicencia(ArrayList<TipoLicencia> licencias);

    public abstract String obtenerCategoria();

    public String obtenerDescripcion() {
        return "Placa: " + placa + ", marca: " + marca + ", modelo: " + modelo + ", tarifa diaria: Q" 
            + String.format("%.2f", tarifaDiaria) + ", estado: " + estado + ", días acumulados: " + diasAcumulados;
    }

    public boolean marcarAlquilado() {
        if (estado != EstadoVehiculo.DISPONIBLE) {
            return false;
        }

        estado = EstadoVehiculo.ALQUILADO;
        return true;
    }

    public boolean registrarDevolucion(int diasAlquilados) {
        if (estado != EstadoVehiculo.ALQUILADO || diasAlquilados <= 0) {
            return false;
        }

        diasAcumulados += diasAlquilados;

        if (diasAcumulados >= obtenerUmbralMantenimiento()) {
            estado = EstadoVehiculo.MANTENIMIENTO;
        } 
        else {
            estado = EstadoVehiculo.DISPONIBLE;
        }

        return true;
    }

    public boolean finalizarMantenimiento() {
        if (estado != EstadoVehiculo.MANTENIMIENTO) {
            return false;
        }

        estado = EstadoVehiculo.DISPONIBLE;
        diasAcumulados = 0;
        return true;
    }

    public String getPlaca(){
        return placa;
    }

    public double getTarifaDiaria() {
        return tarifaDiaria;
    }

    public EstadoVehiculo getEstado() {
        return estado;
    }
}

