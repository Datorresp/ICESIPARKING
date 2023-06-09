package icesi.edu.co.parking.model;

import java.time.*;

public class Vehicle {

    private String type;
    private String id;
    private LocalDateTime entryTime;

    public Vehicle(String type, String id, LocalDateTime entryTime) {
        this.type = type;
        this.id = id;
        this.entryTime = entryTime;
    }

    public String gettype() {
        return type;
    }

    public void settype(String type) {
        this.type = type;
    }

    public String getid() {
        return id;
    }

    public void setid(String id) {
        this.id = id;
    }

    public LocalDateTime getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(LocalDateTime entryTime) {
        this.entryTime = entryTime;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "type='" + type + '\'' +
                ", id='" + id + '\'' +
                ", entry Time=" + entryTime +
                '}';
    }

    public static double calcularCargo(Vehicle vehicle, LocalDateTime checkOutTime) {

        double cargo = 0.0;

        Duration total = Duration.between(vehicle.getEntryTime(), checkOutTime);
        long minutes = total.toMinutes();

        if (vehicle.gettype().equals(CONS.CAR)){

            if (minutes <= 30){

                cargo = 1900;

            } else if (minutes <= 60) {

                cargo = 2900;

            }else if (minutes <= 90){

                cargo = 3500;

            } else if (minutes > 120) {

                cargo = 5900;
            }

        } else if (vehicle.gettype().equals(CONS.MOTORCYCLE)) {

            if (minutes <= 60) {

                cargo = 1000;

            }else if (minutes <= 120){

                cargo = 3000;

            } else if (minutes > 300) {

                cargo = 5000;
            }
        }

        return cargo;
    }

}
