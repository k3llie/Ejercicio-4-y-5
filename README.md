# Ejercicio 4 y 5 - Herencia y Polimorfismo: RentaMovil

**Nombre completo:** Kellie Sophia López Torres
**Carné:** 261551

## Descripción
RentaMovil permite administrar vehículos, clientes y alquileres. El programa registra vehículos y clientes, genera cotizaciones, confirma alquileres, registra devoluciones y finales de mantenimiento, y presenta reportes de ingresos e historial.

El programa está organizado con MVC: la Vista muestra el menú y pide los datos, el Controlador coordina las acciones y el modelo gestiona los vehículos, clientes y alquileres.

Las clases abstractas Vehiculo y Cliente reúnen los datos y comportamientos comunes. Sus clases hijas heredan esa base y aplican las reglas propias de cada tipo mediante polimorfismo. Los vehículos, clientes y alquileres se almacenan en listas ArrayList.

Main inicia el programa y DatosIniciales carga ocho vehículos y cuatro clientes. Todos los vehículos comienzan disponibles y los ingresos en cero.

La clase DatosIniciales carga ocho vehículos y cuatro clientes al iniciar el programa. Todos los vehículos comienzan disponibles, sin alquileres ni ingresos registrados. El Controlador realiza esta carga automáticamente. 

## Cómo ejecutar
```bash
javac -d bin src/*.java
java -cp bin Main
```