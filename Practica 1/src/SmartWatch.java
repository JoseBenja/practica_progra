import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class SmartWatch extends Interaction {
    public SmartWatch(String mail, String nameDevice, boolean visible, boolean status) {
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

    public void NewSmartwatch() {
        ActionDevice actionDevice = new ActionDevice(null, null, null, null, null, null, null, null, null, null, null);
        Menu menu = new Menu();

        NewDevice();

        try {
            PrintWriter linea = new PrintWriter(new BufferedWriter(new FileWriter("devices.csv", true)));
            linea.println("SmartWatch" + "," + "," + "," + this.getMail() + "," + this.getNameDevice() + "," + this.isVisible() + "," + this.isStatus());
            linea.close();

            actionDevice.Log("Creacion de dispositivo", "Archivo", "Se creo un SmartWatch con nombre " + this.getNameDevice());

        } catch (
                IOException e) {
            System.out.println("No se pudo guardar");
        }

        System.out.println("\n Nota: El dispositivo sera encendido por defecto.");
        System.out.println("--------------------------------------------------");

        menu.MenuGeneral();
    }
}
