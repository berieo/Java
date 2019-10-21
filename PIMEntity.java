import java.util.*;
import java.util.ArrayList;
import java.util.List;
import jdk.internal.jshell.tool.resources.version;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.RandomAccessFile;

/*
    PIMManager中实现了main,List,Create,Save,Load,Quit方法
        main()中打印欢迎语句，调用打印语句方法
        List()调用toString方法打印ArrayList类中存放的类数组
        Save()中以字符串方式保存ArrayList类中的类数组到文件中
        Load()中读取文件中字符串并打印
        Quit()退出程序

    PIMEntity接口中有fromString和toString抽象方法，
        Priority变量和更改Priority的相关方法改到在实现类中定义，
        PIMEntity有四个实现类PIMTodo,PIMNote,PIMAppointment,PIMContact
        每个类中声明了特有的变量，定义了Init(),toString(),fromString(),PIMEntity(),
        PIMEntity(String priority),getPriority(),setPriority(String p)方法

    ClassArray类中存放了一些全局变量和ArrayList数组，用来保存实例化的对象

    MainPage类中定义了打印交互语句的方法，并对输入的语句做处理
*/

class PIMManager {
    public static void main(String[] args) {
        System.out.println("Welcome to PIM");
        MainPage mainpage = new MainPage();
        mainpage.mainpage();
    }

    // Print a list of all PIM items
    void List() {
        int amount = ClassArray.list.size();
        int i;
        int j;
        System.out.println("There are " + amount + " items.");
        for (i = 0; i < amount; i++) {
            System.out.println("Item " + (i + 1) + ": " + ClassArray.list.get(i).toString());
        }
    }

    // Add a new item
    void Create() {
        System.out.println("Enter an item type (todo, note, contact or appointment)");
        Scanner scanner = new Scanner(System.in);
        String command = scanner.next();
        switch (command) {
        case "todo":
            PIMTodo pimtodo = new PIMTodo();
            pimtodo.Init();
            ClassArray.list.add(pimtodo);
            break;
        case "note":
            PIMNote pimnote = new PIMNote();
            pimnote.Init();
            ClassArray.list.add(pimnote);
            break;
        case "contact":
            PIMContact pimcontact = new PIMContact();
            pimcontact.Init();
            ClassArray.list.add(pimcontact);
            break;
        case "appointment":
            PIMAppointment pimappointment = new PIMAppointment();
            pimappointment.Init();
            ClassArray.list.add(pimappointment);
            break;
        }
    }

    // Save the entire list of items
    void Sava() {
        String str = new String();
        int amount = ClassArray.list.size();
        int i;
        for (i = 0; i < amount; i++) {
            str = ClassArray.list.get(i).toString();
            ClassArray.Write(str);
        }
        System.out.println("Items have been saved.");
    }

    // Read a list of items from a file
    void Load() {
        try {
            BufferedReader bf = new BufferedReader(new FileReader("C:/Users/ycs19/source/repos/java/src/PIM.txt"));
            String str;
            while ((str = bf.readLine()) != null) {
                System.out.println(str);
            }
        } catch (IOException e) {
            System.out.print(e);
        }
    }
    
    void Quit(){
        System.exit(0);
    }
}

public interface PIMEntity {

    // Each PIMEntity needs to be able to set all state infomation
    // (fields) from a single text string.
    abstract public void fromString(String s);

    // This is actually already defined by the super class
    // Object, but redefined here as abstract to make sure
    // that derived classes actually implement it
    abstract public String toString();
}

class PIMTodo implements PIMEntity {
    static String Priority = "priority";
    String Date;
    String Text;

    void Init() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter date for todo item: ");
        this.Date = scanner.next();
        System.out.println("Enter todo text: ");
        this.Text = scanner.next();
        System.out.println("Enter todo priority: ");
        this.Priority = scanner.next();
    }

    public String toString() {
        String tostring = "TODO " + this.Priority + " " + this.Date + " " + this.Text;
        return tostring;
    }

    public void fromString(String s) {
        String[] strArr = s.split(" ");
        Date = strArr[1];
        Text = strArr[2];
        Priority = strArr[3];
    }

    void PIMEntity() {
        Priority = "normal";
    }

    void PIMEntity(String priority) {
        Priority = priority;
    }

    // accessor method for getting the priority string
    public static String getPriority() {
        return Priority;
    }

    // method that changes the priority string
    public static void setPriority(String p) {
        Priority = p;
    }
}

class PIMNote implements PIMEntity {
    static String Priority;
    String Text;

    void Init() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter note text: ");
        this.Text = scanner.next();
        System.out.println("Enter note priority: ");
        this.Priority = scanner.next();
    }

    public String toString() {
        String tostring = "NOTE " + this.Priority + " " + this.Text;
        return tostring;
    }

    public void fromString(String s) {
        String[] strArr = s.split(" ");
        Text = strArr[1];
        Priority = strArr[2];
    }

    void PIMEntity() {
        Priority = "normal";
    }

    void PIMEntity(String priority) {
        Priority = priority;
    }

    public static String getPriority() {
        return Priority;
    }

    // method that changes the priority string
    public static void setPriority(String p) 
    {
        Priority = p;
    }
}

class PIMAppointment implements PIMEntity {
    static String Priority;
    String Date;
    String Description;

    void Init() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter appointment date: ");
        this.Date = scanner.next();
        System.out.println("Enter appointment description: ");
        this.Description = scanner.next();
        System.out.println("Enter appointment priority: ");
        this.Priority = scanner.next();
    }

    public String toString() {
        String tostring = "APPOINTMENT " + this.Priority + " " + this.Date + " " + this.Description;
        return tostring;
    }

    public void fromString(String s) {
        String[] strArr = s.split(" ");
        Date = strArr[1];
        Description = strArr[2];
    }

    void PIMEntity() {
        Priority = "normal";
    }

    public static String getPriority() {
        return Priority;
    }

    // method that changes the priority string
    public static void setPriority(String p) 
    {
        Priority = p;
    }
}

class PIMContact implements PIMEntity {
    static String Priority;
    String FirstName;
    String LastName;
    String EmailAddress;

    void Init() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter contact first name: ");
        this.FirstName = scanner.next();
        System.out.println("Enter contact last name: ");
        this.LastName = scanner.next();
        System.out.println("Enter contact email address: ");
        this.EmailAddress = scanner.next();
        System.out.println("Enter cantact priority: ");
        this.Priority = scanner.next();
    }

    public String toString() {
        String tostring = "CONTACT " + this.Priority + " " + this.FirstName + " " + this.LastName + " "
                + this.EmailAddress;
        return tostring;
    }

    public void fromString(String s) {
        String[] strArr = s.split(" ");
        FirstName = strArr[1];
        LastName = strArr[2];
        EmailAddress = strArr[3];
    }

    void PIMEntity() {
        Priority = "normal";
    }

    public static String getPriority() {
        return Priority;
    }

    // method that changes the priority string
    public static void setPriority(String p) 
    {
        Priority = p;
    }
}

class ClassArray {
    static ArrayList<PIMEntity> list = new ArrayList<PIMEntity>();
    static int i = 0;
    public static void Write(String str) {
        FileWriter fw = null;
        try {
            File f = new File("C:/Users/ycs19/source/repos/java/src/PIM.txt");
            fw = new FileWriter(f, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        PrintWriter pw = new PrintWriter(fw);
        pw.println(str);
        pw.flush();
        try {
            fw.flush();
            pw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class MainPage {
    void mainpage() {
        System.out.println("---Enter a command (suported commands are List Create Save Load Quit)--- ");
        Scanner scanner = new Scanner(System.in);
        PIMManager pimmanager = new PIMManager();
        String command = scanner.next();
        switch (command) {
        case "List":
            pimmanager.List();
            mainpage();
            break;
        case "Create":
            pimmanager.Create();
            mainpage();
            break;
        case "Save":
            pimmanager.Sava();
            mainpage();
            break;
        case "Load":
            pimmanager.Load();
            mainpage();
            break;
        case "Quit":
            pimmanager.Quit();
        default:
            System.out.println("Command Error");
            mainpage();
            break;
        }
    }
}