package PIM;
import java.util.*;
import java.io.*;
import java.sql.*;
import PIM.PIMDB;
import PIM.PIMDB;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;


/*
    @author 1713011001-杨昌帅

	@version 1.3.0
	将Save和Load原来写入文件的方法重写，改为写入数据库
	新增了文件PIMDB.java用来连接数据库
	Save保存类到数据库中
	Load读取数据库中的内容到类中，用List可以查看读取到的内容
	
    @version 1.2.0
        新增了PIMManager类中Save()和Load()方法，
        Save()方法用来存储创建的类到文件PIM.txt中
        Load()方法用来从PIM.txt文件读取内容

    @version 1.1.0
        新增了PIMCollection类，是通过继承ArraryList<PIMEntity>类实现的
        PIMCollection包括了四个方法getNotes(),getTodos(),getContacts(),getAppointments(),
        getItemsForDate(Date d)

    @version 1.0.0
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
	
	static java.sql.Connection con;
	static ResultSet rs = null;
	java.sql.PreparedStatement preparedStatement = null;  
	
    public static void main(String[] args) {
    	con = PIMDB.conDB();
        System.out.println("Welcome to PIM");
        // system.out.print(PIMCollection.i);
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
            ClassArray.listTodo.add(pimtodo);
            ClassArray.list.add(pimtodo);
            break;
        case "note":
            PIMNote pimnote = new PIMNote();
            pimnote.Init();
            ClassArray.listNote.add(pimnote);
            ClassArray.list.add(pimnote);
            break;
        case "contact":
            PIMContact pimcontact = new PIMContact();
            pimcontact.Init();
            ClassArray.listContact.add(pimcontact);
            ClassArray.list.add(pimcontact);
            break;
        case "appointment":
            PIMAppointment pimappointment = new PIMAppointment();
            pimappointment.Init();
            ClassArray.listAppointment.add(pimappointment);
            ClassArray.list.add(pimappointment);
            break;
        }
    }

    // Save the entire list of items
    void Save() {
    	
/*
 *		Save to file
        String str = new String();
        int amount = ClassArray.list.size();
        int i;
        for (i = 0; i < amount; i++) {
            str = ClassArray.list.get(i).toString();
        }
        FileWriter fw = null;
        try {
            fw = new FileWriter("PIM.txt", true);
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
*/
    	//Save to DB
    	String sqlAppointment = "INSERT INTO pimappointment ( Priority, Date, Description) VALUES (?,?,?)";
    	String sqlContact = "INSERT INTO pimcontact ( Priority, FirstName, LastName, EmailAddress) VALUES (?,?,?,?)";
    	String sqlNote = "INSERT INTO pimnote ( Priority, Text) VALUES (?,?)";
    	String sqlTodo = "INSERT INTO pimtodo ( Priority, Text, Date) VALUES (?,?,?)";

    	if(ClassArray.listAppointment.size() != 0) {
    		int sizeAppointment = ClassArray.listAppointment.size();
    		while(sizeAppointment-- != 0)
    		try {
    			preparedStatement = (PreparedStatement) con.prepareStatement(sqlAppointment);
    			preparedStatement.setString(1, ClassArray.listAppointment.get(sizeAppointment).Priority);
    			preparedStatement.setString(2, ClassArray.listAppointment.get(sizeAppointment).Date);
    			preparedStatement.setString(3, ClassArray.listAppointment.get(sizeAppointment).Description);
    			preparedStatement.executeUpdate();
    		}
    		catch(Exception e) {
    			e.printStackTrace();  
    		}
    	}
    	if(ClassArray.listContact.size() != 0) {
    		int sizeContact= ClassArray.listContact.size();
    		while(sizeContact-- != 0)
    		try {
    			preparedStatement = (PreparedStatement) con.prepareStatement(sqlContact);
    			preparedStatement.setString(1, ClassArray.listContact.get(sizeContact).Priority);
    			preparedStatement.setString(2, ClassArray.listContact.get(sizeContact).FirstName);
    			preparedStatement.setString(3, ClassArray.listContact.get(sizeContact).LastName);
    			preparedStatement.setString(4, ClassArray.listContact.get(sizeContact).EmailAddress);
    			preparedStatement.executeUpdate();
    		}
    		catch(Exception e) {
    			e.printStackTrace();  
    		}
    	}
    	if(ClassArray.listNote.size() != 0) {
    		int sizeNote= ClassArray.listNote.size();
    		while(sizeNote-- != 0)
    		try {
    			preparedStatement = (PreparedStatement) con.prepareStatement(sqlNote);
    			preparedStatement.setString(1, ClassArray.listNote.get(sizeNote).Priority);
    			preparedStatement.setString(2, ClassArray.listNote.get(sizeNote).Text);
    			preparedStatement.executeUpdate();
    		}
    		catch(Exception e) {
    			e.printStackTrace();  
    		}
    	}
    	if(ClassArray.listTodo.size() != 0) {
    		int sizeTodo= ClassArray.listTodo.size();
    		while(sizeTodo-- != 0)
    		try {
    			preparedStatement = (PreparedStatement) con.prepareStatement(sqlTodo);
    			preparedStatement.setString(1, ClassArray.listTodo.get(sizeTodo).Priority);
    			preparedStatement.setString(2, ClassArray.listTodo.get(sizeTodo).Text);
    			preparedStatement.setString(3, ClassArray.listTodo.get(sizeTodo).Date);
    			preparedStatement.executeUpdate();
    		}
    		catch(Exception e) {
    			e.printStackTrace();  
    		}
    	}
    	
        System.out.println("Items have been saved.");
    }

    // Read a list of items from a file
    void Load() {
/* 
*		Load from file 
        try {
            BufferedReader bf = new BufferedReader(new FileReader("PIM.txt"));
            String str;
            while ((str = bf.readLine()) != null) {
                System.out.println(str);
            }
        } catch (IOException e) {
            System.out.print(e);
        }
*/
    	//load from DB
    	String sqlAppointment = "SELECT * FROM pimappointment";
    	String sqlContact = "SELECT * FROM pimcontact";
    	String sqlNote = "SELECT * FROM pimnote";
    	String sqlTodo = "SELECT * FROM pimtodo";
    	int item = 0;
    	//PIMAppointment
    	try {
    		preparedStatement = (PreparedStatement) con.prepareStatement(sqlAppointment);
        	rs = preparedStatement.executeQuery(sqlAppointment);
        	if(rs != null) {
        		while(rs.next()) {
        			PIMAppointment pimappointment = new PIMAppointment();
        			pimappointment.Priority = rs.getString(2);
        			pimappointment.Date = rs.getString(3);
        			pimappointment.Description = rs.getString(4);
        			ClassArray.listAppointment.add(pimappointment);
        			ClassArray.list.add(pimappointment);
        		}
        	}
    	}
    	catch(Exception e) {
    		System.out.println("error " + e.getMessage());
    	}
    	//PIMContact
    	try {
    		preparedStatement = (PreparedStatement) con.prepareStatement(sqlContact);
        	rs = preparedStatement.executeQuery(sqlContact);
        	if(rs != null) {
        		while(rs.next()) {
        			PIMContact pimcontact = new PIMContact();
        			pimcontact.Priority = rs.getString(2);
        			pimcontact.FirstName = rs.getString(3);
        			pimcontact.LastName = rs.getString(4);
        			pimcontact.EmailAddress = rs.getString(5);
        			ClassArray.listContact.add(pimcontact);
        			ClassArray.list.add(pimcontact);
        		}
        	}
    	}
    	catch(Exception e) {
    		System.out.println("error " + e.getMessage());
    	}
    	//PIMNote
    	try {
    		preparedStatement = (PreparedStatement) con.prepareStatement(sqlNote);
        	rs = preparedStatement.executeQuery(sqlNote);
        	if(rs != null) {
        		while(rs.next()) {
        			PIMNote pimnote = new PIMNote();
        			pimnote.Priority = rs.getString(2);
        			pimnote.Text = rs.getString(3);
        			ClassArray.listNote.add(pimnote);
        			ClassArray.list.add(pimnote);
        		}
        	}
    	}
    	catch(Exception e) {
    		System.out.println("error " + e.getMessage());
    	}
    	//PIMTodo
    	try {
    		preparedStatement = (PreparedStatement) con.prepareStatement(sqlTodo);
        	rs = preparedStatement.executeQuery(sqlTodo);
        	if(rs != null) {
        		while(rs.next()) {
        			PIMTodo pimtodo = new PIMTodo();
        			pimtodo.Priority = rs.getString(2);
        			pimtodo.Date = rs.getString(3);
        			pimtodo.Text = rs.getString(4);
        			ClassArray.listTodo.add(pimtodo);
        			ClassArray.list.add(pimtodo);
        		}
        	}
    	}
    	catch(Exception e) {
    		System.out.println("error " + e.getMessage());
    	}
    	
    }

    void Quit() {
    	System.out.println("Quit program");
        System.exit(0);
    }
}

public abstract class PIMEntity {

    abstract public void fromString(String s);

    abstract public String toString();
}

class PIMTodo extends PIMEntity {
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

    
    public static String getPriority() {
        return Priority;
    }

    // method that changes the priority string
    public static void setPriority(String p) {
        Priority = p;
    }

}

class PIMNote extends PIMEntity {
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
    public static void setPriority(String p) {
        Priority = p;
    }

}

class PIMAppointment extends PIMEntity {
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
    public static void setPriority(String p) {
        Priority = p;
    }


}

class PIMContact extends PIMEntity {
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
    public static void  setPriority(String p) {
       Priority = p;
    }


}

class ClassArray {
	static ArrayList<PIMEntity> list = new ArrayList<PIMEntity>();
    static ArrayList<PIMAppointment> listAppointment = new ArrayList<PIMAppointment>();
    static ArrayList<PIMTodo> listTodo = new ArrayList<PIMTodo>();
    static ArrayList<PIMContact> listContact = new ArrayList<PIMContact>();
    static ArrayList<PIMNote> listNote = new ArrayList<PIMNote>();
    static int i = 0;

    public static void Write(String str) {
        
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
            pimmanager.Save();
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

class PIMCollection extends ArrayList<PIMEntity> {
    public PIMCollection getNotes() {
        PIMCollection pimnote = new PIMCollection();
        PIMEntity[] pimentity = this.toArray(new PIMEntity[0]);
        for (int i = 0; i < this.size(); i++) {
            pimnote.add(pimentity[i]);   
        }
        return pimnote;
    }

    public PIMCollection getTodos() {
        PIMCollection pimtodo = new PIMCollection();
        PIMEntity[] pimentity = this.toArray(new PIMEntity[0]);
        for (int i = 0; i < this.size(); i++) {
            pimtodo.add(pimentity[i]);
         }
        return pimtodo; 
    }

	public PIMCollection getContacts() {
        PIMCollection pimcontact = new PIMCollection();
        PIMEntity[] pimentity = this.toArray(new PIMEntity[0]);
        for (int i = 0; i < this.size(); i++) {
            pimcontact.add(pimentity[i]);   
        }
        return pimcontact;    
    }
    
    public PIMCollection getAppointments() {
        PIMCollection pimappointment = new PIMCollection();
        PIMEntity[] pimentity = this.toArray(new PIMEntity[0]);
        for (int i = 0; i < this.size(); i++) {
            pimappointment.add(pimentity[i]);      
        }
        return pimappointment; 
    }

	public PIMCollection getItemsForDate(String d) {
        PIMCollection pimcollection = new PIMCollection();
        PIMEntity[] pimentity = this.toArray(new PIMEntity[0]);
        for (int i = 0; i < this.size(); i++) {
            if(pimentity[i] instanceof PIMTodo) {
                PIMTodo pimtodo = (PIMTodo)pimentity[i];
                if (pimtodo.Date == d)
                    pimcollection.add(pimentity[i]); 
            }
            if(pimentity[i] instanceof PIMAppointment) {
                PIMAppointment pimappointment = (PIMAppointment)pimentity[i];
                if (pimappointment.Date == d)
                    pimcollection.add(pimentity[i]); 
            }        
        }
        return pimcollection;  
    }
}
