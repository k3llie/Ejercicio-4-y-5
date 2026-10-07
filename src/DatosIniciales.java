import java.util.ArrayList;

public class DatosIniciales {

    public void cargar(RentaMovil rentaMovil) {
        rentaMovil.registrarVehiculo(
            new Automovil("P001AAA", "Toyota", "Corolla", 200.0, 5, true));

        rentaMovil.registrarVehiculo(
            new Automovil("P002AAA", "Hyundai", "Accent", 180.0, 5, false));

        rentaMovil.registrarVehiculo(
            new Motocicleta("M001AAA", "Honda", "CB250", 100.0, 250));

        rentaMovil.registrarVehiculo(
            new Motocicleta("M002AAA", "Yamaha", "MT-03", 150.0, 321));

        rentaMovil.registrarVehiculo(
            new CamionetaCarga("C001AAA", "Toyota", "Hilux", 200.0, 1.5));

        rentaMovil.registrarVehiculo(
            new CamionetaCarga("C002AAA", "Isuzu", "D-Max", 250.0, 2.0));

        rentaMovil.registrarVehiculo(
            new Microbus("B001AAA", "Toyota", "Hiace", 450.0, 15, true));

        rentaMovil.registrarVehiculo(
            new Microbus("B002AAA", "Nissan", "Urvan", 350.0, 15, false));

        ArrayList<TipoLicencia> licenciasAna = new ArrayList<>();
        licenciasAna.add(TipoLicencia.C);

        rentaMovil.registrarCliente(
            new ClienteIndividual("1234567890101", "Ana López", licenciasAna));

        ArrayList<TipoLicencia> licenciasLuis = new ArrayList<>();
        licenciasLuis.add(TipoLicencia.M);

        rentaMovil.registrarCliente(
            new ClienteIndividual("1234567890102", "Luis Pérez", licenciasLuis));

        ArrayList<TipoLicencia> licenciasEmpresa1 = new ArrayList<>();
        licenciasEmpresa1.add(TipoLicencia.A);
        licenciasEmpresa1.add(TipoLicencia.M);

        rentaMovil.registrarCliente(new ClienteCorporativo("1234567-8", "Cuenta Transportes del Valle", licenciasEmpresa1, 
            "Transportes del Valle", "María García"));

        ArrayList<TipoLicencia> licenciasEmpresa2 = new ArrayList<>();
        licenciasEmpresa2.add(TipoLicencia.B);

        rentaMovil.registrarCliente(
            new ClienteCorporativo("7654321-0", "Cuenta Distribuidora Central", licenciasEmpresa2, "Distribuidora Central", 
                "Carlos Martínez"));
    }
}