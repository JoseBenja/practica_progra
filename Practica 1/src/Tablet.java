import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Tablet extends Interaction {
    public Tablet(String mail, String nameDevice, boolean visible, boolean status) {
        super(mail, nameDevice, visible, status);
    }

    @Override
    public void NewDevice() {
        super.NewDevice();
    }

    @Override
    public void SearchDevice(String typeDevice, String typeAction) {
        super.SearchDevice(typeDevice, typeAction);
    }

    public void NewTablet() {
        NewDevice();

        try {
            PrintWriter linea = new PrintWriter(new BufferedWriter(new FileWriter("devices.csv", true)));
            linea.println("Tablet" + "," + "," + "," + this.getMail() + "," + this.getNameDevice() + "," + this.isVisible() + "," + this.isStatus());
            linea.close();
        } catch (
                IOException e) {
            System.out.println("No se pudo guardar");
        }

        System.out.println("\n Nota: El dispositivo sera encendido por defecto.");
        System.out.println("-------------------------------------------------");


    }
}
