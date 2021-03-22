import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class Device {
    int option;

    Scanner scn = new Scanner(System.in);

    Menu menu = new Menu();
    ActionDevice actionDevice = new ActionDevice(null, null, null, null, null, null, null, null, null, null, null);
    Computer computer = new Computer(null, null, false, false);
    Tablet tablet = new Tablet(null, null, false, false);
    SmartPhone smartPhone = new SmartPhone(null, null, false, null, false);
    SmartWatch smartWatch = new SmartWatch(null, null, false, false);
    Headphone headphone = new Headphone(null, null,false, null, false);

    public void MenuNewDevice() {
        CreateFileDevice();
        System.out.println("\n----------------Creacion de dispositivos----------------\n");
        System.out.println("1---Computadora Portatil");
        System.out.println("2---Tablet");
        System.out.println("3---SmartWatch");
        System.out.println("4---SmartPhone");
        System.out.println("5---Auriculares inalambricos");
        System.out.println("6---Volver al menu principal");
        option = scn.nextInt();
        System.out.println("--------------------------------------------------------");

        switch (option) {
            case 1:
                computer.NewComputer();
                break;
            case 2:
                tablet.NewTablet();
                break;
            case 3:
                smartWatch.NewSmartwatch();
                break;
            case 4:
                smartPhone.NewSmartphone();
                break;
            case 5:
                headphone.NewHeadphone();
                break;
            case 6:
                menu.MenuGeneral();
                break;
            default:
                menu.MenuGeneral();
        }
    }

    public void MenuModifyDevice() {
        System.out.println("\n----------------Administrar Dispositivos----------------\n");
        System.out.println("1---Computadora Portatil");
        System.out.println("2---Tablet");
        System.out.println("3---SmartWatch");
        System.out.println("4---SmartPhone");
        System.out.println("5---Auriculares inalambricos");
        System.out.println("6---Volver al menu principal");
        option = scn.nextInt();

        System.out.println("--------------------------------------------------------");

        switch (option) {
            case 1:
                computer.SearchDevice("Computadora Portatil", "ModifyDevice");
                break;
            case 2:
                tablet.SearchDevice("Tablet", "ModifyDevice");
                break;
            case 3:
                smartWatch.SearchDevice("SmartWatch", "ModifyDevice");
                break;
            case 4:
                smartPhone.SearchDevice("SmartPhone", "ModifyDevice");
                break;
            case 5:
                headphone.SearchHeadphone();
                break;
            case 6:
                menu.MenuGeneral();
                break;
            default:
                System.out.println("Opcion seleccionada no existe");
                menu.MenuGeneral();
        }
    }

    public void MenuSelectDevice() {
        System.out.println("\n---------------Acciones con Dispositivos----------------\n");
        System.out.println("1---Computadora Portatil");
        System.out.println("2---Tablet");
        System.out.println("3---SmartWatch");
        System.out.println("4---SmartPhone");
        System.out.println("5---Volver al menu principal");
        option = scn.nextInt();

        System.out.println("--------------------------------------------------------");

        switch (option) {
            case 1:
                MenuActionDevice("Computadora Portatil");
                break;
            case 2:
                MenuActionDevice("Tablet");
                break;
            case 3:
                MenuActionDevice("SmartWatch");
                break;
            case 4:
                MenuActionDevice("SmartPhone");
                break;
            case 5:
                menu.MenuGeneral();
                break;
            default:
                System.out.println("Opcion seleccionada no existe");
                menu.MenuGeneral();
        }
    }

    public void MenuActionDevice(String typeDevice) {
        actionDevice.SelectDeviceAction(typeDevice, "");

        String copyColumn = actionDevice.FromDevice.get(0);
        String[] tmp = copyColumn.split(",");

        System.out.println("\n---------------Acciones con Dispositivos----------------\n");
        System.out.println("Tipo de dispositivo: " + typeDevice);
        System.out.println("Nombre del dispositivo: " + tmp[4]);

        CreateFileAction();

        System.out.println("\n--------Seleccione el tipo de accion a realizar---------\n");
        System.out.println("1---Tomar fotografia");
        System.out.println("2---Copiar y Pegar texto");
        System.out.println("3---Compartir documento");
        System.out.println("4---Reproducir musica");
        int edit = scn.nextInt();

        System.out.println("--------------------------------------------------------");

        switch (edit) {
            case 1:
                actionDevice.TakePhoto(typeDevice);
                break;
            case 2:
                actionDevice.CopyText(typeDevice);
                break;
            case 3:
                actionDevice.ShareDocs(typeDevice, tmp[4]);
                break;
            case 4:
                actionDevice.PlayMusic();
                break;
            default:
                System.out.println("Opcion seleccionada no existe");
                menu.MenuGeneral();
        }
    }

    public void MenuExternalActionDevice() {
        CreateFileExternalAction();

        System.out.println("------------Acciones Externas de Dipositivos------------");
        System.out.println("\n--------Seleccione el tipo de accion a realizar---------\n");
        System.out.println("1---Llamada telefonica");
        System.out.println("2---Mensaje");
        System.out.println("3---Notificacion");
        System.out.println("4---Volver al Menu General");
        option = scn.nextInt();

        switch (option) {
            case 1:
                actionDevice.PhoneCall();
                break;
            case 2:
                actionDevice.Message();
                break;
            case 3:
                CreateFileNotification();
                actionDevice.Notification();
                break;
            case 4:
                menu.MenuGeneral();
                break;
            default:
                System.out.println("Opcion seleccionada no existe");
                menu.MenuGeneral();
        }

    }

    public void CreateFileDevice() {
        File listDevice = new File("devices.csv");

        if (!listDevice.exists()) {
            try {
                PrintWriter linea = new PrintWriter(new BufferedWriter(new FileWriter("devices.csv", true)));
                linea.println("Tipo de dispositivo"+ "," +"Numero de telefono"+ "," +"Dispositivo asociado"+ "," +"Correo electronico"+ "," +"Nombre del dispositivo"+ "," +"Visible" + "," + "Encendido");
                linea.close();
            }catch (IOException e){
                System.out.println("No se pudo crear devices.csv");
            }
        }
    }

    public void CreateFileAdmin() {
        File listDevice = new File("administration.csv");

        if (!listDevice.exists()) {
            try {
                PrintWriter linea = new PrintWriter(new BufferedWriter(new FileWriter("actions.csv", true)));
                linea.println("Tipo de dispositivo"+ "," +"Nombre del dispositivo"+ "," +"Campo a editar"+ "," +"Nuevo valor");
                linea.close();
            }catch (IOException e){
                System.out.println("No se pudo crear actions.csv");
            }
        }
    }

    public void CreateFileAction() {
        File listDevice = new File("actions.csv");

        if (!listDevice.exists()) {
            try {
                PrintWriter linea = new PrintWriter(new BufferedWriter(new FileWriter("actions.csv", true)));
                linea.println("Tipo de dispositivo"+ "," +"Nombre del dispositivo"+ "," +"Accion"+ "," +"Dispositivo destino");
                linea.close();
            }catch (IOException e){
                System.out.println("No se pudo crear actions.csv");
            }
        }
    }

    public void CreateFileExternalAction() {
        File listDevice = new File("external_actions.csv");

        if (!listDevice.exists()) {
            try {
                PrintWriter linea = new PrintWriter(new BufferedWriter(new FileWriter("external_actions.csv", true)));
                linea.println("Accion"+ "," +"Número de teléfono"+ "," +"Nombre de dispositivo"+ "," +"Tipo acción" +
                        "," + "Texto de mensaje" + "," + "Nombre de app/red social" + "," + "Remitente de mensaje");
                linea.close();
            }catch (IOException e){
                System.out.println("No se pudo crear external_actions.csv");
            }
        }
    }

    public void CreateFileNotification() {
        File listDevice = new File("notification.csv");

        if (!listDevice.exists()) {
            try {
                PrintWriter linea = new PrintWriter(new BufferedWriter(new FileWriter("notification.csv", true)));
                linea.println("Notificacion" + "," + "Nombre del equipo" + "," + "Asunto del correo" + "," + "Nombre del evento" + "," + "Fecha" + "," + "Hora");
                linea.close();
            }catch (IOException e){
                System.out.println("No se pudo crear notification.csv");
            }
        }
    }

    public void CreateLogHtml() {
        ArrayList<String> ListDevice = new ArrayList<>();
        ArrayList<String> ShowDevice = new ArrayList<>();

        Date time = new Date();

        String[] tmpTime = time.toString().split(" ");

        Calendar tmpDate = Calendar.getInstance();

        String date = tmpDate.get(Calendar.DATE) + "/" + (tmpDate.get(Calendar.MONTH) + 1) + "/" + tmpDate.get(Calendar.YEAR);

        File listDevice = new File("Log_complete.html");

        if (listDevice.exists()) {
            listDevice.delete();
        }

        String line = "";
        try {
            BufferedReader almacen = new BufferedReader(new FileReader(new File("log.csv")));

            //Se llena un ArrayList con la informacion del archivo
            while (line != null) {
                line = almacen.readLine();
                ListDevice.add(line);
            }

            almacen.close();

            for (int i = 0; i < (ListDevice.size() - 1); i++) {
                ShowDevice.add(ListDevice.get(i));
            }

        } catch (IOException e) {
            System.out.println("El archivo no existe");
        }

        String logIndex = "<html>\n" +
                "   <head >\n" +
                "        <h1>Archivo Logs</h1>\n" +
                "        <h2>Fecha y hora de generacion: " + date + ", " + tmpTime[3] +" horas</h2>" +
                "        <style>\n" +
                "            table, th, td {\n" +
                "                border: 4px solid black;\n" +
                "                border-collapse: collapse;\n" +
                "            }\n" +
                "        </style>\n" +
                "    </head>" +
                "    <table>\n" +
                "        <th>\n" +
                "            <tr>\n" +
                "                <td>No. Linea</td>\n" +
                "                <td>Tipo de accion</td>\n" +
                "                <td>Origen de accion</td>\n" +
                "                <td>Descripcion</td>\n" +
                "                <td>Fecha</td>\n" +
                "                <td>Hora</td>\n" +
                "            </tr>\n" +
                "        </th>\n" +
                "        <tbody>";

        try {
            PrintWriter linea = new PrintWriter(new BufferedWriter(new FileWriter("Log_complete.html", true)));
            linea.println(logIndex);
            linea.close();
        } catch (IOException e) {
            System.out.println("No se pudo guardar");
        }

        int number = 1;

        for (String copyColumn : ShowDevice) {
            String[] tmp = copyColumn.split(",");

            String table = "<tr>\n" +
                    "                <td>" + number + "</td>\n" +
                    "                <td>" + tmp[0] + "</td>\n" +
                    "                <td>" + tmp[1] + "</td>\n" +
                    "                <td>" + tmp[2] + "</td>\n" +
                    "                <td>" + tmp[3] + "</td>\n" +
                    "                <td>" + tmp[4] + "</td>\n" +
                    "       </tr>\n";
            try {
                PrintWriter linea = new PrintWriter(new BufferedWriter(new FileWriter("Log_complete.html", true)));
                linea.println(table);
                linea.close();
            } catch (IOException e) {
                System.out.println("No se pudo guardar");
            }
            number++;
        }

        String endTable = "</tbody>\n" +
                "    </table>\n" +
                "</html>";

        try {
            PrintWriter linea = new PrintWriter(new BufferedWriter(new FileWriter("Log_complete.html", true)));
            linea.println(endTable);
            linea.close();
        } catch (IOException e) {
            System.out.println("No se pudo guardar");
        }

        System.out.println("Log creado con exito");
        menu.MenuGeneral();
    }
}
