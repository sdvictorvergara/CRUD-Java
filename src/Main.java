import Controlador.Controlador;
import Modelo.Modelo;
import Vista.VentanaPrincipal;

public class Main {

    public static void main(String[] args) {
        Modelo modelo = new Modelo();
        VentanaPrincipal vista = new VentanaPrincipal();

        Controlador controlador = new Controlador(vista, modelo);

        vista.setLocationRelativeTo(null);
        vista.setVisible(true);
    }
}