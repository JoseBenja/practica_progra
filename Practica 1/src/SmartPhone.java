import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class SmartPhone extends Interaction {
    private String phoneNumber;

    public SmartPhone(String mail, String nameDevice, boolean visible, String phoneNumber, boolean status) {
        super(mail, nameDevice, visible, status);
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    Scanner scn = new Scanner(System.in);
    ArrayList<String> ListSmartphone;
    ArrayList<String> ShowSmartphone;
    ArrayList<String> RestSmartphone;

    @Override
    public void NewDevice() {
        super.NewDevice();
    }

    @Override
    public void SearchDevice(String typeDevice, String typeAction) {
        super.SearchDevice(typeDevice, typeAction);
    }

    public void NewSmartphone() {
        ActionDevice actionDevice = new ActionDevice(null, null, null, null, null, null, null, null, null, null, null);
        Menu menu = new Menu();

        System.out.println("Ingrese el numero de telefono (Formato: NNNN-NNNN)");
        this.setPhoneNumber(scn.nextLine());

        NewDevice();

        try {
            PrintWriter linea = new PrintWriter(new BufferedWriter(new FileWriter("devices.csv", true)));
            linea.println("SmartPhone" + "," + this.getPhoneNumber() + "," + "," + this.getMail() + "," + this.getNameDevice() + "," + this.isVisible() + "," + this.isStatus());
            linea.close();

            actionDevice.Log("Creacion de dispositivo", "Archivo", "Se creo un SmartPhone con nombre " + this.getNameDevice());
        } catch (IOException e) {
            System.out.println("No se pudo guardar");
        }

        System.out.println("Numero: " + this.getPhoneNumber());
        System.out.println("\n Nota: El dispositivo sera encendido por defecto.");
        System.out.println("-------------------------------------------------");

        menu.MenuGeneral();
    }

    public void SearchSmartphone() {
        ListSmartphone = new ArrayList<>();
        ShowSmartphone = new ArrayList<>();
        RestSmartphone = new ArrayList<>();

        String line = "";
        String typeDevice = "SmartPhone";

        try {
            BufferedReader almacen = new BufferedReader(new FileReader(new File("devices.csv")));

            //Se llena un ArrayList con la informacion del archivo
            while (line != null) {
                line = almacen.readLine();
                ListSmartphone.add(line);
            }

            almacen.close();

            //Se evalua que dispositivos son SmartPhones y cuales estan encendidos
            for (int i = 0; i < (ListSmartphone.size() - 1); i++) {
                String copyColumn = ListSmartphone.get(i);
                String[] tmp = copyColumn.split(",");
                if (typeDevice.equals(tmp[0])) {
                    if (Boolean.parseBoolean(tmp[5]) && Boolean.parseBoolean(tmp[6])) {
                        ShowSmartphone.add(ListSmartphone.get(i));
                    }
                }
            }
        }catch (IOException e) {
            System.out.println("El archivo devices.csv no se ha encontrado");
        }
    }
}
