package icesi.edu.co.parking.ui;

import java.time.Duration;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        LocalDateTime entrada = LocalDateTime.of(2023, 6, 8, 9, 0); // Fecha y hora de entrada del vehículo
        LocalDateTime salida = LocalDateTime.of(2023, 6, 8, 15, 30); // Fecha y hora de salida del vehículo

        Duration duracionTotal = Duration.between(entrada, salida);
        long horas = duracionTotal.toHours();
        long minutos = duracionTotal.toMinutes();

        System.out.println("El vehículo ha estado en el parqueadero durante " + horas + " horas y" + minutos + " minutos");
    }
}