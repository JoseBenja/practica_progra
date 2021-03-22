import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Headphone extends Interaction {
    private String deviceAssociate;

    public Headphone(String mail, String nameDevice, boolean visible, String deviceAssociate, boolean status) {
        super(mail, nameDevice, visible, status);
        this.deviceAssociate = deviceAssociate;
    }

    public String getDeviceAssociate() {
        return deviceAssociate;
    }

    public void setDeviceAssociate(String deviceAssociate) {
        this.deviceAssociate = deviceAssociate;
    }

    Scanner scn = new Scanner(System.in);
    Scanner scnNum = new Scanner(System.in);

    Menu menu = new Menu();
    ActionDevice actionDevice = new ActionDevice(null, null, null, null, null, null, null, null, null, null, null);

    ArrayList<String> ListHeadphone = new ArrayList<>();
    ArrayList<String> SelectHeadphone = new ArrayList<>();
    ArrayList<String> ShowHeadphone = new ArrayList<>();
    ArrayList<String> ListDevice;
    ArrayList<String> ShowDevice;
    ArrayList<String> RestDevice;

    @Override
    public void NewDevice() {
        super.NewDevice();
    }

    public void NewHeadphone() {
        NewDevice();
        SearchAssociation();

        System.out.println("\n Seleccione el dispositivo con el cual se va a enlazar");

        //Se muestran los resultados de los dispositivos enlazables
        for (int i = 0; i < ShowHeadphone.size(); i++) {
            System.out.println(i + ". \t" + ShowHeadphone.get(i));
        }

        //Se selecciona la opcion a enlazar
        int opcion = scn.nextInt();

        this.setDeviceAssociate(ShowHeadphone.get(opcion));

        System.out.println("Elegido "+ this.getDeviceAssociate());

        try {
            PrintWriter linea = new PrintWriter(new BufferedWriter(new FileWriter("devices.csv", true)));
            linea.println("Auriculares" + "," + "," + this.getDeviceAssociate() + "," + this.getMail() + "," + this.getNameDevice() + "," + this.isVisible() + "," + this.isStatus());
            linea.close();

            actionDevice.Log("Creacion de dispositivo", "Archivo", "Se crearon unos audifonos con nombre " + this.getNameDevice());
        } catch (IOException e) {
            System.out.println("No se pudo guardar");
        }

        System.out.println("\n Nota: El dispositivo sera encendido por defecto.");
        System.out.println("-------------------------------------------------");

        menu.MenuGeneral();
    }

    public void ModifyHeadphone() {
        Device device = new Device();
        device.CreateFileAdmin();

        String typeEdit = null;

        System.out.println("Eliga el dispositivo que desea administrar");

        for (int i = 0; i < ShowDevice.size(); i++) {
            String copyColumn = ShowDevice.get(i);
            String[] tmp = copyColumn.split(",");
            if (Boolean.parseBoolean(tmp[6])) {
                System.out.println(i + ". \t" + tmp[4] + "\t" + "Encendido");
            } else {
                System.out.println(i + ". \t" + tmp[4] + "\t" + "Apagado");
            }
        }

        int option = scnNum.nextInt();

        String copyColumn = ShowDevice.get(option);
        String[] tmp = copyColumn.split(",");

        System.out.println("\n----Administracion de auriculares----\n");
        System.out.println("Nombre del dispositivo: " + tmp[4] + "\t\n");
        System.out.println("1---Editar nombre del dispositivo");
        System.out.println("2---Eliminar dispisitivo sincronizado");
        System.out.println("3---Apagar dispositivo \n");
        System.out.println("Seleccione la opcion a editar");
        int edit = scnNum.nextInt();

        System.out.println("-------------------------------------------------");

        switch (edit) {
            case 1:
                System.out.println("Ingrese el nuevo nombre");
                this.setNameDevice(scn.nextLine());
                this.setDeviceAssociate(tmp[2]);
                this.setMail(tmp[3]);
                this.setVisible(Boolean.parseBoolean(tmp[5]));
                this.setStatus(Boolean.parseBoolean(tmp[6]));
                typeEdit = "nombre";
                break;
            case 2:
                this.setDeviceAssociate("");
                System.out.println("Sincronizacion interrumpida");
                this.setMail(tmp[3]);
                this.setNameDevice(tmp[4]);
                this.setVisible(Boolean.parseBoolean(tmp[5]));
                this.setStatus(Boolean.parseBoolean(tmp[6]));
                typeEdit = "eliminar sincronización";
                break;
            case 3:
                System.out.println("Selecione si desea apargarlo o encenderlo");
                System.out.println("1---Apagar");
                System.out.println("2---Encender");
                edit = scnNum.nextInt();

                if (edit == 1) {
                    this.setStatus(false);
                } else if (edit == 2) {
                    this.setStatus(true);
                }

                this.setDeviceAssociate(tmp[2]);
                this.setMail(tmp[3]);
                this.setNameDevice(tmp[4]);
                this.setVisible(Boolean.parseBoolean(tmp[5]));
                typeEdit = "encender";
                break;
        }

        if (!this.getNameDevice().isEmpty()) {

            File file = new File("devices.csv");

            ShowDevice.remove(option);

            RestDevice.addAll(ShowDevice);

            file.delete();

            for (String s : RestDevice) {
                try {
                    PrintWriter line = new PrintWriter(new BufferedWriter(new FileWriter("devices.csv", true)));
                    line.println(s);
                    line.close();
                } catch (IOException e) {
                    System.out.println("Fallo al recrear el archivo devices.csv");
                }
            }

            try {
                PrintWriter line = new PrintWriter(new BufferedWriter(new FileWriter("devices.csv", true)));
                line.println("Auriculares" + "," + "," + this.getDeviceAssociate() + "," + this.getMail() + "," + this.getNameDevice() + "," + this.isVisible() + "," + this.isStatus());
                line.close();
                System.out.println("\n Cambio realizado con exito");
                System.out.println("-------------------------------------------------");
            } catch (IOException e) {
                System.out.println("Error al agregar el dispositivo modificado");
            }
        }else {
            System.out.println("El nombre o email estan vacios");
        }

        if (typeEdit != null) {
            if (edit == 1) {
                try {
                    PrintWriter line = new PrintWriter(new BufferedWriter(new FileWriter("administration.csv", true)));
                    line.println("Auriculares" + "," + tmp[4] + "," + typeEdit + "," + this.getNameDevice());
                    line.close();

                    actionDevice.Log("Modificacion de dispositivo", "Archivo", "Se modifico el nombre de " + this.getNameDevice());
                } catch (IOException e) {
                    System.out.println("Error al agregar el cambio");
                }
            } else if (edit == 2) {
                try {
                    PrintWriter line = new PrintWriter(new BufferedWriter(new FileWriter("administration.csv", true)));
                    line.println("Auriculares" + "," + tmp[4] + "," + typeEdit + "," + "true");
                    line.close();

                    actionDevice.Log("Modificacion de dispositivo", "Archivo", "Se elimino la sincronizacion de " + this.getNameDevice());
                } catch (IOException e) {
                    System.out.println("Error al agregar el cambio");
                }
            } else if (edit == 3) {
                try {
                    PrintWriter line = new PrintWriter(new BufferedWriter(new FileWriter("administration.csv", true)));
                    line.println("Auriculares" + "," + tmp[4] + "," + typeEdit + "," + this.isStatus());
                    line.close();

                    actionDevice.Log("Modificacion de dispositivo", "Archivo", "Se apago el dispositivo " + this.getNameDevice());
                } catch (IOException e) {
                    System.out.println("Error al agregar el cambio");
                }
            }
        }
        menu.MenuGeneral();
    }

    public void SearchHeadphone() {
        ListDevice = new ArrayList<>();
        ShowDevice = new ArrayList<>();
        RestDevice = new ArrayList<>();

        String line = "";

        //Se realiza la busqueda de los auriculares
        try {
            BufferedReader almacen = new BufferedReader(new FileReader(new File("devices.csv")));

            //Se llena un ArrayList con la informacion del archivo
            while (line != null) {
                line = almacen.readLine();
                ListDevice.add(line);
            }

            almacen.close();

            //Se evalua que los auriculares existan en el archivo
            for (int i = 0; i < (ListDevice.size() - 1); i++) {
                String copyColumn = ListDevice.get(i);
                String[] tmp = copyColumn.split(",");
                if (tmp[0].equals("Auriculares")) {
                    ShowDevice.add(ListDevice.get(i));
                }else {
                    RestDevice.add(ListDevice.get(i));
                }
            }

        } catch (IOException e) {
            System.out.println("El archivo no existe");
        }

        ModifyHeadphone();
    }

    public void SearchAssociation() {
        String line = "";
        String hdphones = "Auriculares";

        //Se realiza la busqueda de dispositivos en el archivo para evaluar con cuales es posible enlazarse
        try {
            BufferedReader almacen = new BufferedReader(new FileReader(new File("devices.csv")));

            //Se llena un ArrayList con la informacion del archivo
            while (line != null) {
                line = almacen.readLine();
                ListHeadphone.add(line);
            }

            almacen.close();

            //Se evalua que el sea vinculable el dispositivo
            for (int i = 1; i < (ListHeadphone.size() - 1); i++) {
                String copyColumn = ListHeadphone.get(i);
                String[] tmp = copyColumn.split(",");
                if (!hdphones.equals(tmp[0])) {
                    SelectHeadphone.add(ListHeadphone.get(i));
                }
            }

            //Se evalua que los emails coincidan, y que los dispositivos esten visibles y encendidos
            for (String copyColumn : SelectHeadphone) {
                String[] tmp = copyColumn.split(",");
                if (this.getMail().equals(tmp[3]) && this.isVisible() && this.isStatus() && Boolean.parseBoolean(tmp[5]) && Boolean.parseBoolean(tmp[6])) {
                    ShowHeadphone.add(tmp[4]);
                }
            }
        }catch (IOException e) {
            System.out.println("Fallo al buscar");
        }
    }
}
