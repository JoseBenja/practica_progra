import java.util.Scanner;

public class Menu {
    public void MenuGeneral() {
        Scanner scn = new Scanner(System.in);

        Device device = new Device();

        int opcion;

        System.out.println("---------------Ecosistema de Dispositivos---------------");
        System.out.println("1---Crear Dispositivo");
        System.out.println("2---Administrar Dispositivo");
        System.out.println("3---Acciones con Dispositivos");
        System.out.println("4---Acciones externas de Dispositivos");
        System.out.println("5---Log");
        System.out.println("6---Cerrar");
        opcion = scn.nextInt();
        System.out.println("--------------------------------------------------------");

        switch (opcion) {
            case 1:
                device.MenuNewDevice();
                break;
            case 2:
                device.MenuModifyDevice();
                break;
            case 3:
                device.MenuSelectDevice();
                break;
            case 4:
                device.MenuExternalActionDevice();
                break;
            case 5:
                device.CreateLogHtml();
                break;
            case 6:
                System.out.println("Cerrando...");
                break;
            default:
                MenuGeneral();
        }
    }
}
