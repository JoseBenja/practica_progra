//Clase padre

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Interaction {
    private String mail;
    private String nameDevice;
    private boolean visible;
    private boolean status;

    public Interaction (String mail, String nameDevice, boolean visible, boolean status) {
        this.mail = mail;
        this.nameDevice = nameDevice;
        this.visible = visible;
        this.status = status;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getNameDevice() {
        return nameDevice;
    }

    public void setNameDevice(String nameDevice) {
        this.nameDevice = nameDevice;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    ArrayList<String> ListDevice;
    ArrayList<String> ShowDevice;
    ArrayList<String> RestDevice;

    public void NewDevice() {
        Scanner scn = new Scanner(System.in);

        System.out.println("Ingrese el correo electronico");
        this.setMail(scn.nextLine());
        System.out.println("Ingrese el nombre del dispositivo");
        this.setNameDevice(scn.nextLine());
        System.out.println("Desea que el dispositivo sea visible");
        System.out.println("1---Si");
        System.out.println("2---No");
        int visible = scn.nextInt();

        switch (visible) {
            case 1:
                this.setVisible(true);
                break;
            case 2:
                this.setVisible(false);
                break;
            default:
                System.out.println("la opcion no esta disponible");
                break;
        }

        this.setStatus(true);

        System.out.println("--------------------------------------------------------");
        System.out.println("Correo electronico: " + this.getMail());
        System.out.println("Nombre del dispositivo: " + this.getNameDevice());
        System.out.println("Visible para conexion: " + this.isVisible());
    }

    public void SearchDevice(String typeDevice, String typeAction) {
        //Se crean los arrays aca por incompatibilidad con otras funciones
        ListDevice = new ArrayList<>();
        ShowDevice = new ArrayList<>();
        RestDevice = new ArrayList<>();

        String line = "";

        //Se realiza la busqueda de los dispositivos
        try {
            BufferedReader almacen = new BufferedReader(new FileReader(new File("devices.csv")));

            //Se llena un ArrayList con la informacion del archivo
            while (line != null) {
                line = almacen.readLine();
                ListDevice.add(line);
            }

            almacen.close();

            //Se evalua el tipo de dispositivo que exista en relacion al que se desea editar
            for (int i = 0; i < (ListDevice.size() - 1); i++) {
                String copyColumn = ListDevice.get(i);
                String[] tmp = copyColumn.split(",");
                if (typeDevice.equals(tmp[0])) {
                    ShowDevice.add(ListDevice.get(i));
                }else {
                    RestDevice.add(ListDevice.get(i));
                }
            }

        } catch (IOException e) {
            System.out.println("El archivo no existe");
        }

        if (typeAction.equals("ModifyDevice")) {
            if (typeDevice.equals("Auriculares")) {
                Headphone headphone = new Headphone(null, null, false, null, false);
                headphone.ModifyHeadphone();
            } else {
                ModifyDevice(typeDevice);
            }
        }
    }

    public void ModifyDevice(String typeDevice) {
        ActionDevice actionDevice = new ActionDevice(null, null, null, null, null, null, null, null, null, null, null);
        Menu menu = new Menu();

        Scanner scn = new Scanner(System.in);
        Scanner scnNum = new Scanner(System.in);

        Device device = new Device();
        device.CreateFileAdmin();

        String number = "";
        String typeEdit = null;

        System.out.println("--------------------------------------------------------");
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

        System.out.println("--------------------------------------------------------");
        System.out.println("Nombre del dispositivo: " + tmp[4] + "\n");
        System.out.println("1---Editar correo electronico");
        System.out.println("2---Editar nombre");
        System.out.println("3---Apagar visibilidad");
        System.out.println("4---Apagar dispositivo");
        int edit = scnNum.nextInt();
        System.out.println("--------------------------------------------------------");

        switch (edit) {
            case 1:
                System.out.println("Ingrese el nuevo correo electronico");
                this.setMail(scn.nextLine());
                number = tmp[1];
                this.setNameDevice(tmp[4]);
                this.setVisible(Boolean.parseBoolean(tmp[5]));
                this.setStatus(Boolean.parseBoolean(tmp[6]));
                typeEdit = "correo electronico";
                break;
            case 2:
                System.out.println("Ingrese el nuevo nombre");
                this.setNameDevice(scn.nextLine());
                number = tmp[1];
                this.setMail(tmp[3]);
                this.setVisible(Boolean.parseBoolean(tmp[5]));
                this.setStatus(Boolean.parseBoolean(tmp[6]));
                typeEdit = "nombre";
                break;
            case 3:
                System.out.println("Seleccione si desea o no cambiar visibilidad");
                System.out.println("1---Visible");
                System.out.println("2---Invisible");
                int visible = scn.nextInt();

                if (visible == 1) {
                    this.setVisible(true);
                }else if (visible == 2){
                    this.setVisible(false);
                }
                number = tmp[1];
                this.setMail(tmp[3]);
                this.setNameDevice(tmp[4]);
                this.setStatus(Boolean.parseBoolean(tmp[6]));
                typeEdit = "visible";
                break;
            case 4:
                System.out.println("Seleccione si desea apagarlo o encenderlo");
                System.out.println("1---Encender");
                System.out.println("2---Apagar");
                int case4 = scn.nextInt();

                if (case4 == 1) {
                    this.setStatus(true);
                }else if (case4 == 2) {
                    this.setStatus(false);
                }
                number = tmp[1];
                this.setMail(tmp[3]);
                this.setNameDevice(tmp[4]);

                typeEdit = "encender";
                break;
            default:
                System.out.println("Opcion seleccionada no existe");
                menu.MenuGeneral();
        }

        if (!this.getNameDevice().isEmpty() && !this.getMail().isEmpty()) {

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
                line.println(typeDevice + "," + number + "," + "," + this.getMail() + "," + this.getNameDevice() + "," + this.isVisible() + "," + this.isStatus());
                line.close();
                System.out.println("Cambio realizado con exito");
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
                    line.println(typeDevice + "," + tmp[4] + "," + typeEdit + "," + this.getMail());
                    line.close();

                    actionDevice.Log("Modificacion de dispositivo", "Archivo", "Se modifico el correo electronico de " + this.getNameDevice());
                } catch (IOException e) {
                    System.out.println("Error al agregar el cambio");
                }
            } else if (edit == 2) {
                try {
                    PrintWriter line = new PrintWriter(new BufferedWriter(new FileWriter("administration.csv", true)));
                    line.println(typeDevice + "," + tmp[4] + "," + typeEdit + "," + this.getNameDevice());
                    line.close();

                    actionDevice.Log("Modificacion de dispositivo", "Archivo", "Se modifico el nombre de " + this.getNameDevice());
                } catch (IOException e) {
                    System.out.println("Error al agregar el cambio");
                }
            } else if (edit == 3) {
                try {
                    PrintWriter line = new PrintWriter(new BufferedWriter(new FileWriter("administration.csv", true)));
                    line.println(typeDevice + "," + tmp[4] + "," + typeEdit + "," + this.isVisible());
                    line.close();

                    actionDevice.Log("Modificacion de dispositivo", "Archivo", "Se cambio la visibilidad de " + this.getNameDevice());
                } catch (IOException e) {
                    System.out.println("Error al agregar el cambio");
                }
            } else {
                try {
                    PrintWriter line = new PrintWriter(new BufferedWriter(new FileWriter("administration.csv", true)));
                    line.println(typeDevice + "," + tmp[4] + "," + typeEdit + "," + status);
                    line.close();

                    if (status) {
                        actionDevice.Log("Modificacion de dispositivo", "Archivo", "Se encendio el dispositivo " + this.getNameDevice());
                    } else {
                        actionDevice.Log("Modificacion de dispositivo", "Archivo", "Se apago el dispositivo " + this.getNameDevice());
                    }
                } catch (IOException e) {
                    System.out.println("Error al agregar el cambio");
                }
            }
        }

        menu.MenuGeneral();
    }
}
