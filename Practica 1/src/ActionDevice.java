import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class ActionDevice {
    private String namePhoto;
    private String textCopy;
    private String documentShare;
    private String nameSong;
    private String numberPhone;
    private String nameDevice;
    private String nameRedMessage;
    private String namePerson;
    private String date;
    private String time;
    private String eventName;

    public ActionDevice(String namePhoto, String textCopy, String documentShare, String nameSong, String numberPhone, String nameDevice, String nameRedMessage, String namePerson, String date, String time, String eventName) {
        this.namePhoto = namePhoto;
        this.textCopy = textCopy;
        this.documentShare = documentShare;
        this.nameSong = nameSong;
        this.numberPhone = numberPhone;
        this.nameDevice = nameDevice;
        this.nameRedMessage = nameRedMessage;
        this.namePerson = namePerson;
        this.date = date;
        this.time = time;
        this.eventName = eventName;
    }

    public String getNamePhoto() {
        return namePhoto;
    }

    public void setNamePhoto(String namePhoto) {
        this.namePhoto = namePhoto;
    }

    public String getTextCopy() {
        return textCopy;
    }

    public void setTextCopy(String textCopy) {
        this.textCopy = textCopy;
    }

    public String getDocumentShare() {
        return documentShare;
    }

    public void setDocumentShare(String documentShare) {
        this.documentShare = documentShare;
    }

    public String getNameSong() {
        return nameSong;
    }

    public void setNameSong(String nameSong) {
        this.nameSong = nameSong;
    }

    public String getNumberPhone() {
        return numberPhone;
    }

    public void setNumberPhone(String numberPhone) {
        this.numberPhone = numberPhone;
    }

    public String getNameDevice() {
        return nameDevice;
    }

    public void setNameDevice(String nameDevice) {
        this.nameDevice = nameDevice;
    }

    public String getNameRedMessage() {
        return nameRedMessage;
    }

    public void setNameRedMessage(String nameRedMessage) {
        this.nameRedMessage = nameRedMessage;
    }

    public String getNamePerson() {
        return namePerson;
    }

    public void setNamePerson(String namePerson) {
        this.namePerson = namePerson;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    Scanner scn = new Scanner(System.in);
    Scanner scnNum = new Scanner(System.in);

    Interaction interaction = new Interaction(null, null, false, false);
    Menu menu = new Menu();
    SmartPhone smartPhone = new SmartPhone(null, null, false, null, false);

    ArrayList<String> MatchDevices;
    ArrayList<String> FromDevice;
    ArrayList<String> ToDevice;
    ArrayList<String> TextCopy;
    ArrayList<String> ListDevices;
    ArrayList<String> ShowDevices;

    public void SelectDeviceAction(String typeDevice, String typeAction) {
        FromDevice = new ArrayList<>();

        //Se buscan las coincidencias de dispositivos activos
        interaction.SearchDevice(typeDevice, typeAction);

        //Se selecciona el nombre del dispositivo
        System.out.println("Seleccione el nombre del dispositivo de la " + typeDevice);
        for (int i = 0; i < interaction.ShowDevice.size(); i++) {
            String copyColumn = interaction.ShowDevice.get(i);
            String[] tmp = copyColumn.split(",");

            System.out.println(i + ". \t" + tmp[4]);
        }

        int option = scnNum.nextInt();

        //Se agrega a un ArrayList que se usara para buscar coincidencias
        FromDevice.add(interaction.ShowDevice.get(option));
    }

    public void CoincidencesDevices() {
        MatchDevices = new ArrayList<>();
        ListDevices = new ArrayList<>();

        //Se evalua que exista un SmartPhone disponible en el ArrayList
        if (!ToDevice.isEmpty()) {
            //Se separan los datos del SmartPhone disponible
            String copyToDevice = ToDevice.get(0);
            String[] tmpToDevice = copyToDevice.split(",");
            if (FromDevice != null) {
                //Se separan los datos del dispositivo externo
                String copyColumn = FromDevice.get(0);
                String[] tmp = copyColumn.split(",");
                //Se evaluan los SmartPhones encendidos con el dispositivo que desea interactuar con el, por medio del correo

                if (tmp[3].equals(tmpToDevice[3])) {
                    MatchDevices.add(tmp[4] + "," + tmpToDevice[4]);
                }
            } else {
                String line = "";

                try {
                    BufferedReader almacen = new BufferedReader(new FileReader(new File("devices.csv")));

                    //Se llena un ArrayList con la informacion del archivo
                    while (line != null) {
                        line = almacen.readLine();
                        ListDevices.add(line);
                    }

                    almacen.close();

                    for (int i = 0; i < (ListDevices.size() - 1); i++) {
                        String copyColumn = ListDevices.get(i);
                        String[] tmp = copyColumn.split(",");
                        if (tmpToDevice[3].equals(tmp[3]) && !tmp[0].equals("Auriculares") && !tmpToDevice[4].equals(tmp[4])) {
                            MatchDevices.add(ListDevices.get(i));
                        }
                    }
                } catch (IOException e) {
                    System.out.println("Archivo no encontrado");
                }
            }
        } else {
            System.out.println("No existe una coincidencia en los correos de los dispositivos");
        }
    }

    public void TakePhoto(String typeDevice) {
        ToDevice = new ArrayList<>();

        //Se buscan las coincidencias de los SmartPhones activos
        smartPhone.SearchSmartphone();

        //Se selecciona el nombre del SmartPhone
        System.out.println("Seleccione el nombre del SmartPhone");
        for (int i = 0; i < smartPhone.ShowSmartphone.size(); i++) {
            String copyColumn = smartPhone.ShowSmartphone.get(i);
            String[] tmp = copyColumn.split(",");

            System.out.println(i + ". \t" + tmp[4]);
        }

        int option2 = scnNum.nextInt();

        //Se agrega a un ArrayList que se usara para buscar coincidencias
        ToDevice.add(smartPhone.ShowSmartphone.get(option2));

        //Se buscan las coincidencias entre el dispositivo y el SmartPhone
        CoincidencesDevices();

        if (!MatchDevices.isEmpty()) {
            String copyColumn = MatchDevices.get(0);
            String[] tmp = copyColumn.split(",");

            System.out.println("\n Fotografia tomada desde: " + tmp[1]);
            System.out.println("Escriba el nombre de la fotografia");
            this.setNamePhoto(scn.nextLine());

            try {
                PrintWriter linea = new PrintWriter(new BufferedWriter(new FileWriter("actions.csv", true)));
                linea.println(typeDevice + "," + tmp[0] + "," + "fotografia");
                linea.close();

                Log("Accion con Dispositivos", "Manual", "Se tomo una fotografia desde " + tmp[0]);

            }catch (IOException e) {
                System.out.println("No se pudo guardar");
            }
        } else {
            System.out.println("No existe ninguna coincidencia para tomar una fotografia");
        }
    }

    public void CopyText(String typeDevice) {
        if (!typeDevice.equals("SmartWatch")) {
            TextCopy = new ArrayList<>();

            System.out.println("Ingrese el texto que desea copiar");
            this.setTextCopy(scn.nextLine());

            TextCopy.add(this.getTextCopy());

            System.out.println("--------------------------------------------------------");
            System.out.println("Ultimo texto copiado: " + TextCopy);
            System.out.println("--------------------------------------------------------");
            System.out.println("Seleccione el dispositivo en el que se va a pegar el texto");
            System.out.println("1---Computadora Portatil");
            System.out.println("2---Tablet");
            System.out.println("3---SmartPhone");
            int option = scnNum.nextInt();

            switch (option) {
                case 1:
                    PasteText("Computadora Portatil");
                    break;
                case 2:
                    PasteText("Tablet");
                    break;
                case 3:
                    PasteText("SmartPhone");
                    break;
            }

            try {
                String copyColumn = FromDevice.get(0);
                String[] tmp = copyColumn.split(",");
                PrintWriter linea = new PrintWriter(new BufferedWriter(new FileWriter("actions.csv", true)));
                linea.println(typeDevice + "," + tmp[4] + "," + "Copiar texto");
                linea.close();

                Log("Accion con Dispositivos", "Manual", "Se copio un texto en " + tmp[4]);

            } catch (IOException e) {
                System.out.println("No se pudo guardar");
            }
        } else {
            System.out.println("No es posible realizar esta accion desde un " + typeDevice);
            System.out.println("--------------------------------------------------------");
            menu.MenuGeneral();
        }
    }

    public void PasteText(String typeDevice) {
        ToDevice = new ArrayList<>();
        ShowDevices = new ArrayList<>();

        interaction.SearchDevice(typeDevice, "");

        for (int i = 0; i < interaction.ShowDevice.size(); i++) {
            String copyColumn = interaction.ShowDevice.get(i);
            String[] tmp = copyColumn.split(",");
            if (!tmp[0].equals("SmartWatch")) {
                ShowDevices.add(interaction.ShowDevice.get(i));
            }
        }

        System.out.println("Eliga el nombre del dispositivo");
        for (int i = 0; i < ShowDevices.size(); i++) {
            String copyColumn = ShowDevices.get(i);
            String[] tmp = copyColumn.split(",");

            System.out.println(i + ". \t" + tmp[4]);
        }

        int option = scnNum.nextInt();

        ToDevice.add(interaction.ShowDevice.get(option));

        CoincidencesDevices();

        System.out.println("--------------------------------------------------------");
        if (!TextCopy.isEmpty()) {
            System.out.println("El texto " + TextCopy + " fue copiado exitosamente");
        } else {
            System.out.println("No hay ningun texto copiado");
        }

        try {
            String copyColumn = ToDevice.get(0);
            String[] tmp = copyColumn.split(",");
            PrintWriter linea = new PrintWriter(new BufferedWriter(new FileWriter("actions.csv", true)));
            linea.println(typeDevice + "," + tmp[4] + "," + "Pegar texto");
            linea.close();

            Log("Accion con Dispositivos", "Manual", "Se pego un texto en " + tmp[4]);

        }catch (IOException e) {
            System.out.println("No se pudo guardar");
        }
        System.out.println("--------------------------------------------------------");
        menu.MenuGeneral();
    }

    public void ShareDocs(String typeDevice, String nameDevice) {
        if (!typeDevice.equals("SmartWatch")) {
            ToDevice = new ArrayList<>();

            System.out.println("Ingrese el nombre del documento que sea compartir");
            this.setDocumentShare(scn.nextLine());

            SearchDeviceToShare(nameDevice);

            System.out.println("--------------------------------------------------------");
            System.out.println("Seleccione el dispositivo al cual enviar el archivo");
            for (int i = 0; i < ShowDevices.size(); i++) {
                String copyColumn = ShowDevices.get(i);
                String[] tmp = copyColumn.split(",");

                System.out.println(i + ".\t" + tmp[4]);
            }

            int option = scnNum.nextInt();

            ToDevice.add(ShowDevices.get(option));

            String copyColumn = FromDevice.get(0);
            String[] tmp = copyColumn.split(",");

            String copyColumn2 = ToDevice.get(0);
            String[] tmp2 = copyColumn2.split(",");

            System.out.println("--------------------------------------------------------");
            System.out.println(tmp2[4] + "\n ¿Desea recibir el documento '" + this.getDocumentShare() + "' del dispositivo " + tmp[4] + "?");
            System.out.println("1---Si");
            System.out.println("2---No");
            int confirm = scnNum.nextInt();

            if (confirm == 1) {
                System.out.println("Documento recibido con exito");

                try {
                    PrintWriter linea = new PrintWriter(new BufferedWriter(new FileWriter("actions.csv", true)));
                    linea.println(tmp[0] + "," + tmp[4] + "," + "Compartir documentos" + "," + tmp2[4]);
                    linea.close();
                }catch (IOException e) {
                    System.out.println("No se pudo guardar");
                }

                Log("Accion con Dispositivos", "Manual", "Se compartio un documento con " + tmp[4]);
            } else if (confirm == 2) {
                System.out.println("Se nego el documento compartido");
            }
        } else {
            System.out.println("No es posible realizar esta accion desde un " + typeDevice);
        }
        System.out.println("--------------------------------------------------------");
        menu.MenuGeneral();
    }

    public void SearchDeviceToShare(String nameDevice) {
        ListDevices = new ArrayList<>();
        ShowDevices = new ArrayList<>();

        String line = "";

        //Se realiza la busqueda de los dispositivos a los cuales se les puede compartir
        try {
            BufferedReader almacen = new BufferedReader(new FileReader(new File("devices.csv")));

            //Se llena un ArrayList con la informacion del archivo
            while (line != null) {
                line = almacen.readLine();
                ListDevices.add(line);
            }

            almacen.close();

            //Se evalua el tipo de dispositivo que exista y sea visible
            for (int i = 0; i < (ListDevices.size() - 1); i++) {
                String copyColumn = ListDevices.get(i);
                String[] tmp = copyColumn.split(",");
                if (!nameDevice.equals(tmp[4]) && !tmp[0].equals("SmartWatch")) {
                    if (Boolean.parseBoolean(tmp[5])) {
                        ShowDevices.add(ListDevices.get(i));
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("El archivo no existe");
        }
    }

    public void PlayMusic() {
        ListDevices = new ArrayList<>();
        ShowDevices = new ArrayList<>();

        System.out.println("Ingrese el nombre de la cancion que desea escuchar");
        this.setNameSong(scn.nextLine());

        interaction.SearchDevice("Auriculares", "PlayMusic");

        for (String copyColumn : FromDevice) {
            String[] tmp = copyColumn.split(",");
            for (int j = 0; j < interaction.ShowDevice.size(); j++) {
                String copyColum2 = interaction.ShowDevice.get(j);
                String[] tmp2 = copyColum2.split(",");
                if (tmp[3].equals(tmp2[3])) {
                    ListDevices.add(interaction.ShowDevice.get(j));
                }
            }
        }

        System.out.println("--------------------------------------------------------");
        System.out.println("¿Desea reproducir la música en los audifonos?");
        System.out.println("1---Si");
        System.out.println("2---No (Se reproducira en el parlante del dispositivo)");
        int option = scnNum.nextInt();

        if (option == 1) {
            System.out.println("--------------------------------------------------------");
            System.out.println("Elegir los audifonos a los que se desea conectar");

            for (int i = 0; i < ListDevices.size(); i++) {
                String copyColumn = ListDevices.get(0);
                String[] tmp = copyColumn.split(",");

                System.out.println(i + ".\t" + tmp[4]);
            }

            int select = scnNum.nextInt();

            ShowDevices.add(ListDevices.get(select));

            //Se separan los datos de los audifonos
            String copyColumn = ShowDevices.get(0);
            String[] tmp = copyColumn.split(",");

            //Se separan los datos del dispositivo
            String saveColumn = FromDevice.get(0);
            String[] save = saveColumn.split(",");

            System.out.println("Reproduciendo '" + this.getNameSong() + "' en los audifonos " + tmp[4]);

            try {
                PrintWriter linea = new PrintWriter(new BufferedWriter(new FileWriter("actions.csv", true)));
                linea.println(save[0] + "," + save[4] + "," + "Reproducir musica" + "," + tmp[4]);
                linea.close();
            }catch (IOException e) {
                System.out.println("No se pudo guardar");
            }

            Log("Accion con Dispositivos", "Manual", "Se reprodujo una cancion en " + tmp[4]);

        } else if (option == 2){
            //Se separan los datos del dispositivo
            String copyColumn = FromDevice.get(0);
            String[] tmp = copyColumn.split(",");
            System.out.println("Reproduciendo '" + this.getNameSong() + "' en los parlantes del dispositivo " + tmp[4]);

            try {
                PrintWriter linea = new PrintWriter(new BufferedWriter(new FileWriter("actions.csv", true)));
                linea.println(tmp[0] + "," + tmp[4] + "," + "Reproducir musica" + "," + tmp[4]);
                linea.close();
            }catch (IOException e) {
                System.out.println("No se pudo guardar");
            }
            Log("Accion con Dispositivos", "Manual", "Se reprodujo una cancion en " + tmp[4]);
        } else {
            System.out.println("No se selecciono ninguna de las opciones");
        }

        System.out.println("--------------------------------------------------------");
        menu.MenuGeneral();
    }

    public void PhoneCall() {
        ToDevice = new ArrayList<>();

        System.out.println("Ingrese el numero de telefono al que desea llamar (Formato: NNNN-NNNN)");
        this.setNumberPhone(scn.nextLine());

        interaction.SearchDevice("SmartPhone", "PhoneCall");

        for (int i = 0; i < interaction.ShowDevice.size(); i++) {
            String copyColumn = interaction.ShowDevice.get(i);
            String[] tmp = copyColumn.split(",");
            if (this.getNumberPhone().equals(tmp[1])) {
                ToDevice.add(interaction.ShowDevice.get(i));
            }
        }

        String copyToDevice = ToDevice.get(0);
        String[] tmpToDevice = copyToDevice.split(",");

        System.out.println("--------------------------------------------------------");
        System.out.println(tmpToDevice[0] + ": " + tmpToDevice[4]);
        System.out.println("Llamada entrante");


        CoincidencesDevices();

        for (String copyMatchDevices : MatchDevices) {
            String[] tmpMatchDevices = copyMatchDevices.split(",");

            System.out.println("--------------------------------------------------------");
            System.out.println(tmpMatchDevices[0] + ": " + tmpMatchDevices[4]);
            System.out.println("Tiene una llamada entrante en el telefono " + tmpToDevice[4]);
        }
        System.out.println("--------------------------------------------------------");

        SaveRegisterExternalActions("PhoneCall");
    }

    public void Message() {
        ToDevice = new ArrayList<>();

        System.out.println("Ingrese el nombre del dispositivo al cual se le va a enviar un mensaje");
        this.setNameDevice(scn.nextLine());

        System.out.println("--------------------------------------------------------");
        System.out.println("1---Mensaje de texto");
        System.out.println("2---Mensaje de red social");
        System.out.println("3---Mensajeria instantanea");
        System.out.println("4---Mensaje de videoconferencia");

        int option = scnNum.nextInt();
        System.out.println("--------------------------------------------------------");

        System.out.println("Ingrese el texto que desea enviar");
        this.setTextCopy(scn.nextLine());

        interaction.SearchDevice("SmartPhone", "Message");

        for (int i = 0; i < interaction.ShowDevice.size(); i++) {
            String copyColumn = interaction.ShowDevice.get(i);
            String[] tmp = copyColumn.split(",");
            if (this.getNameDevice().equals(tmp[4])) {
                ToDevice.add(interaction.ShowDevice.get(i));
            }
        }

        CoincidencesDevices();

        String copyToDevice = ToDevice.get(0);
        String[] tmpToDevice = copyToDevice.split(",");

        switch (option) {
            case 1:
                System.out.println("--------------------Mensaje de Texto--------------------");

                System.out.println("--------------------------------------------------------");
                System.out.println(tmpToDevice[0] + ": " + tmpToDevice[4]);
                System.out.println("Mensaje entrante: " + this.getTextCopy());

                for (String copyMatchDevices : MatchDevices) {
                    String[] tmpMatchDevices = copyMatchDevices.split(",");

                    System.out.println("--------------------------------------------------------");
                    System.out.println(tmpMatchDevices[0] + ": " + tmpMatchDevices[4]);
                    System.out.println("Tiene un mensaje en el telefono " + tmpToDevice[4] + ": " + this.getTextCopy());
                }
                System.out.println("--------------------------------------------------------");

                SaveRegisterExternalActions("Message");
                break;
            case 2:
                System.out.println("-----------------Mensaje de Red Social------------------");

                System.out.println("Ingrese el nombre de la red social desde donde se envia el mensaje");
                this.setNameRedMessage(scn.nextLine());

                System.out.println("--------------------------------------------------------");
                System.out.println(tmpToDevice[0] + ": " + tmpToDevice[4]);
                System.out.println(this.getNameRedMessage() + ": ");
                System.out.println("Mensaje entrante: " + this.getTextCopy());

                for (String copyMatchDevices : MatchDevices) {
                    String[] tmpMatchDevices = copyMatchDevices.split(",");

                    System.out.println("--------------------------------------------------------");
                    System.out.println(tmpMatchDevices[0] + ": " + tmpMatchDevices[4]);
                    System.out.println("Tiene un mensaje en la red social " + this.getNameRedMessage() +" en el telefono " + tmpToDevice[4] + ": " + this.getTextCopy());
                }
                System.out.println("--------------------------------------------------------");

                SaveRegisterExternalActions("Message Red Social");
                break;
            case 3:
                System.out.println("-----------------Mensajeria instantanea-----------------");

                System.out.println("Ingrese el nombre de la aplicacion de mensajeria");
                this.setNameRedMessage(scn.nextLine());

                System.out.println("Ingrese el nombre de la persona que lo envia");
                this.setNamePerson(scn.nextLine());

                System.out.println("--------------------------------------------------------");
                System.out.println(tmpToDevice[0] + ": " + tmpToDevice[4]);
                System.out.println(this.getNameRedMessage() + ": ");
                System.out.println("Mensaje entrante: " + this.getTextCopy());

                for (String copyMatchDevices : MatchDevices) {
                    String[] tmpMatchDevices = copyMatchDevices.split(",");

                    System.out.println("--------------------------------------------------------");
                    System.out.println(tmpMatchDevices[0] + ": " + tmpMatchDevices[4]);
                    System.out.println("Tiene un mensaje de " + this.getNamePerson() + " en " + this.getNameRedMessage() +" en el telefono " + tmpToDevice[4] + ": " + this.getTextCopy());
                }
                System.out.println("--------------------------------------------------------");

                SaveRegisterExternalActions("Instant Message");
                break;
            case 4:
                System.out.println("--------------Mensaje de videoconferencia---------------");

                System.out.println("Ingrese el nombre de la aplicacion de videoconferencia");
                this.setNameRedMessage(scn.nextLine());

                System.out.println("Ingrese el nombre de la persona que lo envia");
                this.setNamePerson(scn.nextLine());

                System.out.println("--------------------------------------------------------");
                System.out.println(tmpToDevice[0] + ": " + tmpToDevice[4]);
                System.out.println(this.getNameRedMessage() + ": ");
                System.out.println("Mensaje entrante: " + this.getTextCopy());

                for (String copyMatchDevices : MatchDevices) {
                    String[] tmpMatchDevices = copyMatchDevices.split(",");

                    System.out.println("--------------------------------------------------------");
                    System.out.println(tmpMatchDevices[0] + ": " + tmpMatchDevices[4]);
                    System.out.println(this.getNameRedMessage() + ":");
                    System.out.println("Tiene un mensaje de " + this.getNamePerson() +" en el telefono " + tmpToDevice[4] + ": " + this.getTextCopy());
                }
                System.out.println("--------------------------------------------------------");

                SaveRegisterExternalActions("Message Video Conference");
                break;
        }
    }

    public void Notification() {
        ListDevices = new ArrayList<>();
        ToDevice = new ArrayList<>();

        System.out.println("--------------------------------------------------------");
        System.out.println("Ingrese el nombre del dispositivo al cual desea enviar la notificacion");
        this.setNameDevice(scn.nextLine());

        System.out.println("--------------------------------------------------------");
        System.out.println("Seleccione el tipo de notificacion");
        System.out.println("1---Correo electronico");
        System.out.println("2---Evento de calendario");
        int option = scnNum.nextInt();

        String line = "";

        //Se realiza la busqueda de los dispositivos
        try {
            BufferedReader almacen = new BufferedReader(new FileReader(new File("devices.csv")));

            //Se llena un ArrayList con la informacion del archivo
            while (line != null) {
                line = almacen.readLine();
                ListDevices.add(line);
            }

            almacen.close();

            //Se busca el dispositivo especifico por el nombre
            for (int i = 0; i < (ListDevices.size() - 1); i++) {
                String copyColumn = ListDevices.get(i);
                String[] tmp = copyColumn.split(",");
                if (this.getNameDevice().equals(tmp[4])) {
                    ToDevice.add(ListDevices.get(i));
                }
            }
        } catch (IOException e) {
            System.out.println("El archivo no existe");
        }

        CoincidencesDevices();

        String copyToDevice = ToDevice.get(0);
        String[] tmpToDevice = copyToDevice.split(",");

        System.out.println("--------------------------------------------------------");
        switch (option) {
            case 1:
                System.out.println("-------------------Correo Electronico-------------------");
                System.out.println("Ingrese el asunto del correo");
                this.setTextCopy(scn.nextLine());

                System.out.println("--------------------------------------------------------");
                System.out.println(tmpToDevice[0] + ": " + tmpToDevice[4]);
                System.out.println("Correo electronico entrando: ");
                System.out.println("Asunto: " + this.getTextCopy());

                for (String copyMatchDevices : MatchDevices) {
                    String[] tmpMatchDevices = copyMatchDevices.split(",");

                    System.out.println("--------------------------------------------------------");
                    System.out.println(tmpMatchDevices[0] + ": " + tmpMatchDevices[4]);
                    System.out.println("Correo electronico entrando en " + this.getNameDevice() + ": ");
                    System.out.println("Asunto: " + this.getTextCopy());
                }

                SaveRegisterExternalActions("E-Mail");
                break;
            case 2:
                System.out.println("Ingrese la fecha (Formato: DD/MM/YYYY)");
                this.setDate(scn.nextLine());

                System.out.println("Ingrese la hora (Formato 24h: HH:MM)");
                this.setTime(scn.nextLine());

                System.out.println("Ingrese el nombre del evento");
                this.setEventName(scn.nextLine());

                System.out.println("--------------------------------------------------------");
                System.out.println(tmpToDevice[0] + ": " + tmpToDevice[4]);
                System.out.println("Evento de calendario: ");
                System.out.println("Nombre: " + this.getEventName());
                System.out.println("Fecha: " + this.getDate());
                System.out.println("Hora: " + this.getTime());

                for (String copyMatchDevices : MatchDevices) {
                    String[] tmpMatchDevices = copyMatchDevices.split(",");

                    System.out.println("--------------------------------------------------------");
                    System.out.println(tmpMatchDevices[0] + ": " + tmpMatchDevices[4]);
                    System.out.println("Evento de calendario recibido en " + this.getNameDevice());
                    System.out.println("Nombre: " + this.getEventName());
                    System.out.println("Fecha: " + this.getDate());
                    System.out.println("Hora: " + this.getTime());
                }

                SaveRegisterExternalActions("Calendar Event");
                break;
        }
        System.out.println("--------------------------------------------------------");
    }

    public void SaveRegisterExternalActions(String typeAction) {
        String copyToDevice = ToDevice.get(0);
        String[] tmpToDevice = copyToDevice.split(",");

        switch (typeAction) {
            case "PhoneCall":
                try {
                    PrintWriter linea = new PrintWriter(new BufferedWriter(new FileWriter("external_actions.csv", true)));
                    linea.println("Llamada telefonica" + "," + tmpToDevice[1] + "," + "," + "," + "," + ",");
                    linea.close();
                } catch (IOException e) {
                    System.out.println("No se pudo guardar");
                }

                Log("Acción Externa con Dispositivo","Manual","Se recibio una llamada al dispositivo " + tmpToDevice[1]);
                break;
            case "Message":
                try {
                    PrintWriter linea = new PrintWriter(new BufferedWriter(new FileWriter("external_actions.csv", true)));
                    linea.println("Mensaje" + "," + "," + this.getNameDevice() + "," + "Mensaje de texto" + "," + this.getTextCopy() + "," + ",");
                    linea.close();
                } catch (IOException e) {
                    System.out.println("No se pudo guardar");
                }

                Log("Acción Externa con Dispositivo","Manual","Se recibio un mensaje de texto para el dispositivo " + tmpToDevice[4]);
                break;
            case "Message Red Social":
                try {
                    PrintWriter linea = new PrintWriter(new BufferedWriter(new FileWriter("external_actions.csv", true)));
                    linea.println("Mensaje" + "," + "," + this.getNameDevice() + "," + "Mensaje de red social" + "," + this.getTextCopy() + "," + this.getNameRedMessage() + ",");
                    linea.close();
                } catch (IOException e) {
                    System.out.println("No se pudo guardar");
                }
                Log("Acción Externa con Dispositivo","Manual","Se recibio un mensaje en " + this.getNameRedMessage() + " para el dispositivo " + tmpToDevice[4]);
                break;
            case "Instant Message":
                try {
                    PrintWriter linea = new PrintWriter(new BufferedWriter(new FileWriter("external_actions.csv", true)));
                    linea.println("Mensaje" + "," + "," + this.getNameDevice() + "," + "Mensajeria instantanea" + "," + this.getTextCopy() + "," + this.getNameRedMessage() + "," + this.getNamePerson());
                    linea.close();
                } catch (IOException e) {
                    System.out.println("No se pudo guardar");
                }
                Log("Acción Externa con Dispositivo","Manual","Se recibio un mensaje en " + this.getNameRedMessage() + " para el dispositivo " + tmpToDevice[4]);
                break;
            case "Message Video Conference":
                try {
                    PrintWriter linea = new PrintWriter(new BufferedWriter(new FileWriter("external_actions.csv", true)));
                    linea.println("Mensaje" + "," + "," + this.getNameDevice() + "," + "Mensaje de videoconferencia" + "," + this.getTextCopy() + "," + this.getNameRedMessage() + "," + this.getNamePerson());
                    linea.close();
                } catch (IOException e) {
                    System.out.println("No se pudo guardar");
                }
                Log("Acción Externa con Dispositivo","Manual","Se recibio un mensaje de videoconferencia en " + this.getNameRedMessage() + " para el dispositivo " + tmpToDevice[4]);
                break;
            case "E-Mail":
                try {
                    PrintWriter linea = new PrintWriter(new BufferedWriter(new FileWriter("notification.csv", true)));
                    linea.println("Correo electronico" + "," + this.getNameDevice() + "," + this.getTextCopy() + "," + "," + ",");
                    linea.close();
                } catch (IOException e) {
                    System.out.println("No se pudo guardar");
                }
                Log("Acción Externa con Dispositivo","Manual","Se recibio un correo electronico para el dispositivo " + tmpToDevice[4]);
                break;
            case "Calendar Event":
                try {
                    PrintWriter linea = new PrintWriter(new BufferedWriter(new FileWriter("notification.csv", true)));
                    linea.println("Evento de calendario" + "," + this.getNameDevice() + "," + "," + this.getEventName() + "," + this.getDate() + "," + this.getTime());
                    linea.close();
                } catch (IOException e) {
                    System.out.println("No se pudo guardar");
                }
                Log("Acción Externa con Dispositivo","Manual","Se recibio un evento llamado " + this.getEventName() + " para el dispositivo " + tmpToDevice[4]);
                break;
        }
    }

    public void Log(String typeAction, String originAction, String description) {
        try {
            Date time = new Date();

            String[] tmpTime = time.toString().split(" ");

            Calendar tmpDate = Calendar.getInstance();

            String date = tmpDate.get(Calendar.DATE) + "/" + (tmpDate.get(Calendar.MONTH) + 1) + "/" + tmpDate.get(Calendar.YEAR);

            PrintWriter linea = new PrintWriter(new BufferedWriter(new FileWriter("log.csv", true)));
            linea.println(typeAction + "," + originAction + "," + description + "," + date + "," + tmpTime[3]);
            linea.close();
        } catch (IOException e) {
            System.out.println("No se pudo guardar");
        }
    }
}