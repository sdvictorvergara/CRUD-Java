package Modelo;

import java.sql.*;

public class Modelo {

    private final String url = "jdbc:sqlite:Tienda.db";

    public Modelo() {
        crearTablas();
    }

    private Connection conectar() throws SQLException {
        Connection conexion = DriverManager.getConnection(url);
        conexion.createStatement().execute("PRAGMA foreign_keys = ON");
        return conexion;
    }

    private void crearTablas() {
        String customers = """
            CREATE TABLE IF NOT EXISTS Customers(
                customer_id INTEGER PRIMARY KEY AUTOINCREMENT,
                customer_name TEXT NOT NULL
            )
        """;

        String orders = """
            CREATE TABLE IF NOT EXISTS Orders(
                order_id INTEGER PRIMARY KEY AUTOINCREMENT,
                customer_id INTEGER NOT NULL,
                order_date TEXT NOT NULL,
                FOREIGN KEY(customer_id) REFERENCES Customers(customer_id) ON DELETE CASCADE
            )
        """;

        String shipments = """
            CREATE TABLE IF NOT EXISTS Shipments(
                shipment_id INTEGER PRIMARY KEY AUTOINCREMENT,
                order_id INTEGER NOT NULL,
                shipment_date TEXT NOT NULL,
                FOREIGN KEY(order_id) REFERENCES Orders(order_id) ON DELETE CASCADE
            )
        """;

        try (Connection conexion = conectar(); Statement sentencia = conexion.createStatement()) {
            sentencia.execute(customers);
            sentencia.execute(orders);
            sentencia.execute(shipments);
        } catch (SQLException e) {
            System.out.println("Error creando tablas: " + e.getMessage());
        }
    }

    public void insertarCustomer(String nombre) {
        ejecutar("INSERT INTO Customers(customer_name) VALUES(?)", nombre);
    }

    public void actualizarCustomer(int id, String nombre) {
        ejecutar("UPDATE Customers SET customer_name=? WHERE customer_id=?", nombre, id);
    }

    public void eliminarCustomer(int id) {
        ejecutar("DELETE FROM Customers WHERE customer_id=?", id);
    }

    public ResultSet listarCustomers() {
        return consultar("SELECT * FROM Customers");
    }

    public void insertarOrder(int customerId, String fecha) {
        ejecutar("INSERT INTO Orders(customer_id, order_date) VALUES(?, ?)", customerId, fecha);
    }

    public void actualizarOrder(int id, int customerId, String fecha) {
        ejecutar("UPDATE Orders SET customer_id=?, order_date=? WHERE order_id=?", customerId, fecha, id);
    }

    public void eliminarOrder(int id) {
        ejecutar("DELETE FROM Orders WHERE order_id=?", id);
    }

    public ResultSet listarOrders() {
        return consultar("SELECT * FROM Orders");
    }

    public void insertarShipment(int orderId, String fecha) {
        ejecutar("INSERT INTO Shipments(order_id, shipment_date) VALUES(?, ?)", orderId, fecha);
    }

    public void actualizarShipment(int id, int orderId, String fecha) {
        ejecutar("UPDATE Shipments SET order_id=?, shipment_date=? WHERE shipment_id=?", orderId, fecha, id);
    }

    public void eliminarShipment(int id) {
        ejecutar("DELETE FROM Shipments WHERE shipment_id=?", id);
    }

    public ResultSet listarShipments() {
        return consultar("SELECT * FROM Shipments");
    }

    private void ejecutar(String sql, Object... datos) {
        try (Connection conexion = conectar(); PreparedStatement sentencia = conexion.prepareStatement(sql)) {
            for (int i = 0; i < datos.length; i++) {
                sentencia.setObject(i + 1, datos[i]);
            }
            sentencia.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error SQL: " + e.getMessage());
        }
    }

    private ResultSet consultar(String sql) {
        try {
            Connection conexion = conectar();
            PreparedStatement sentencia = conexion.prepareStatement(sql);
            return sentencia.executeQuery();
        } catch (SQLException e) {
            System.out.println("Error consultando: " + e.getMessage());
            return null;
        }
    }
}