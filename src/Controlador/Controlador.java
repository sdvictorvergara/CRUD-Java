package Controlador;

import Modelo.Modelo;
import Vista.VentanaPrincipal;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class Controlador {

    private VentanaPrincipal vista;
    private Modelo modelo;

    public Controlador(VentanaPrincipal vista, Modelo modelo) {
        this.vista = vista;
        this.modelo = modelo;
        iniciarEventos();
        cargarTablas();
    }

    private void iniciarEventos() {
        vista.getBInsertar().addActionListener(e -> insertarCustomer());
        vista.getBActualizar().addActionListener(e -> actualizarCustomer());
        vista.getBEliminar().addActionListener(e -> eliminarCustomer());

        vista.getBInsertar1().addActionListener(e -> insertarOrder());
        vista.getBActualizar1().addActionListener(e -> actualizarOrder());
        vista.getBEliminar1().addActionListener(e -> eliminarOrder());

        vista.getBInsertar2().addActionListener(e -> insertarShipment());
        vista.getBActualizar2().addActionListener(e -> actualizarShipment());
        vista.getBEliminar2().addActionListener(e -> eliminarShipment());

        vista.getRegistrosCustomer().addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int fila = vista.getRegistrosCustomer().getSelectedRow();
                vista.getTIDCustomer().setText(vista.getRegistrosCustomer().getValueAt(fila, 0).toString());
                vista.getTNombre().setText(vista.getRegistrosCustomer().getValueAt(fila, 1).toString());
            }
        });

        vista.getRegistrosOrder().addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int fila = vista.getRegistrosOrder().getSelectedRow();
                vista.getTIDOrder().setText(vista.getRegistrosOrder().getValueAt(fila, 0).toString());
                vista.getTIDCustomer2().setText(vista.getRegistrosOrder().getValueAt(fila, 1).toString());
                vista.getTFechaPedido().setText(vista.getRegistrosOrder().getValueAt(fila, 2).toString());
            }
        });

        vista.getRegistrosShipment().addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int fila = vista.getRegistrosShipment().getSelectedRow();
                vista.getTIDShipment().setText(vista.getRegistrosShipment().getValueAt(fila, 0).toString());
                vista.getTIDOrders2().setText(vista.getRegistrosShipment().getValueAt(fila, 1).toString());
                vista.getTFechaEnvio().setText(vista.getRegistrosShipment().getValueAt(fila, 2).toString());
            }
        });
    }

    private void insertarCustomer() {
        modelo.insertarCustomer(vista.getTNombre().getText());
        cargarCustomers();
        limpiarCustomers();
    }

    private void actualizarCustomer() {
        modelo.actualizarCustomer(
                Integer.parseInt(vista.getTIDCustomer().getText()),
                vista.getTNombre().getText()
        );
        cargarCustomers();
        limpiarCustomers();
    }

    private void eliminarCustomer() {
        modelo.eliminarCustomer(Integer.parseInt(vista.getTIDCustomer().getText()));
        cargarTablas();
        limpiarCustomers();
    }

    private void insertarOrder() {
        modelo.insertarOrder(
                Integer.parseInt(vista.getTIDCustomer2().getText()),
                vista.getTFechaPedido().getText()
        );
        cargarOrders();
        limpiarOrders();
    }

    private void actualizarOrder() {
        modelo.actualizarOrder(
                Integer.parseInt(vista.getTIDOrder().getText()),
                Integer.parseInt(vista.getTIDCustomer2().getText()),
                vista.getTFechaPedido().getText()
        );
        cargarOrders();
        limpiarOrders();
    }

    private void eliminarOrder() {
        modelo.eliminarOrder(Integer.parseInt(vista.getTIDOrder().getText()));
        cargarTablas();
        limpiarOrders();
    }

    private void insertarShipment() {
        modelo.insertarShipment(
                Integer.parseInt(vista.getTIDOrders2().getText()),
                vista.getTFechaEnvio().getText()
        );
        cargarShipments();
        limpiarShipments();
    }

    private void actualizarShipment() {
        modelo.actualizarShipment(
                Integer.parseInt(vista.getTIDShipment().getText()),
                Integer.parseInt(vista.getTIDOrders2().getText()),
                vista.getTFechaEnvio().getText()
        );
        cargarShipments();
        limpiarShipments();
    }

    private void eliminarShipment() {
        modelo.eliminarShipment(Integer.parseInt(vista.getTIDShipment().getText()));
        cargarShipments();
        limpiarShipments();
    }

    private void cargarTablas() {
        cargarCustomers();
        cargarOrders();
        cargarShipments();
    }

    private void cargarCustomers() {
        DefaultTableModel tabla = new DefaultTableModel(new String[]{"ID", "Nombre"}, 0);

        try {
            ResultSet datos = modelo.listarCustomers();

            while (datos != null && datos.next()) {
                tabla.addRow(new Object[]{
                    datos.getInt("customer_id"),
                    datos.getString("customer_name")
                });
            }

            vista.getRegistrosCustomer().setModel(tabla);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista, "Error cargando Customers");
        }
    }

    private void cargarOrders() {
        DefaultTableModel tabla = new DefaultTableModel(new String[]{"ID Order", "ID Customer", "Fecha pedido"}, 0);

        try {
            ResultSet datos = modelo.listarOrders();

            while (datos != null && datos.next()) {
                tabla.addRow(new Object[]{
                    datos.getInt("order_id"),
                    datos.getInt("customer_id"),
                    datos.getString("order_date")
                });
            }

            vista.getRegistrosOrder().setModel(tabla);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista, "Error cargando Orders");
        }
    }

    private void cargarShipments() {
        DefaultTableModel tabla = new DefaultTableModel(new String[]{"ID Shipment", "ID Order", "Fecha envío"}, 0);

        try {
            ResultSet datos = modelo.listarShipments();

            while (datos != null && datos.next()) {
                tabla.addRow(new Object[]{
                    datos.getInt("shipment_id"),
                    datos.getInt("order_id"),
                    datos.getString("shipment_date")
                });
            }

            vista.getRegistrosShipment().setModel(tabla);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista, "Error cargando Shipments");
        }
    }

    private void limpiarCustomers() {
        vista.getTIDCustomer().setText("");
        vista.getTNombre().setText("");
    }

    private void limpiarOrders() {
        vista.getTIDOrder().setText("");
        vista.getTIDCustomer2().setText("");
        vista.getTFechaPedido().setText("");
    }

    private void limpiarShipments() {
        vista.getTIDShipment().setText("");
        vista.getTIDOrders2().setText("");
        vista.getTFechaEnvio().setText("");
    }
}