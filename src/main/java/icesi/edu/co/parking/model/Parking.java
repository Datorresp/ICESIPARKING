package icesi.edu.co.parking.model;

import java.sql.*;
import java.time.Duration;
import java.time.LocalDateTime;


public class Parking {
    private Connection connection;


    public Parking() {
        connection = obtenerConexion();
    }

    public void agregarVehiculo(Vehicle vehicle) {
        try {
            String query = "INSERT INTO vehicles (type, id, entryTime) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, vehicle.gettype());
            statement.setString(2, vehicle.getid());
            statement.setObject(3, vehicle.getEntryTime());
            statement.executeUpdate();
            System.out.println("Vehículo agregado correctamente a la base de datos.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Vehicle getVehicle(String id) {

        Vehicle vehiculo = null;

        try {
            String query = "SELECT type, id, entryTime FROM vehicles WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, id);

            // Ejecutar la sentencia SQL para obtener el vehículo
            ResultSet resultSet = statement.executeQuery();

            // Verificar si se encontró el vehículo y procesar los resultados
            if (resultSet.next()) {
                String type = resultSet.getString("type");
                LocalDateTime entryTime = (LocalDateTime) resultSet.getObject("entryTime");

                // Crear un objeto Vehiculo con los datos obtenidos
                vehiculo = new Vehicle(type, id, entryTime);


            } else {
                System.out.println("No se encontró el vehículo con la matrícula especificada.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return vehiculo;
    }

    public double calculateFee(String id){

        double fee = 0;
        Vehicle vehicle = getVehicle(id);

        return vehicle.calcularCargo(vehicle, LocalDateTime.now());
    }

    public void DeleteVehicle(String id) {
        try {
            String query = "DELETE FROM vehiculos_estacionados WHERE matricula = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void Pay(double money, String id){

        double fee = calculateFee(id);
        if (money < fee){

            System.out.println("monto insuficiente");
        }else if (money == fee){

            System.out.println("Muchas Gracias por su visita. Tiene 15 minutos para salir");
            UpdateDate(id);
        }else{

            double diff = money-fee;
            System.out.println("Muchas Gracias por su visita, su devuelta es: "+diff+" Pesos. Tiene 15 minutos para salir");
            UpdateDate(id);
        }
    }

    private void UpdateDate(String id){

        try  {
            // Preparar la sentencia SQL para actualizar el vehículo
            String query = "UPDATE vehiculos_estacionados SET duracion_estadia = ? WHERE matricula = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setObject(1, LocalDateTime.now());
            statement.setString(2, id);

            int filasActualizadas = statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean isValid(String id){

        boolean isvalid = false;
        Vehicle vehicle = getVehicle(id);
        Duration total = Duration.between(vehicle.getEntryTime(), LocalDateTime.now());
        long minutes = total.toMinutes();

        if (minutes <= 15){

            isvalid = true;
        }

        return isvalid;
    }

    public void checkOut(String id){

        if (isValid(id)){

            DeleteVehicle(id);

        }else{

            System.out.println("Dirijase a Caja");

        }
    }

    private Connection obtenerConexion() {
        Connection connection = null;
        try {
            // Establecer la conexión a la base de datos utilizando JDBC
            String url = "jdbc:mysql://localhost:3306/nombre_base_datos";
            String usuario = "usuario";
            String contraseña = "contraseña";
            connection = DriverManager.getConnection(url, usuario, contraseña);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}