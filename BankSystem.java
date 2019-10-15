
/*  
    0、你需要设计BankAccount类去模型化用户银行账户，CashAccount, CreditAccount
    1、账户保存用户名称和余额，精确到分
    2、用户应该可以进行存取操作
    3、可以改变用户名
    4、系统需要可以发现注册了多少银行账户
    5、对于每一个账户，只有最后6个事务应该能够按升序存储并打印
    6、余额为负数，存款为负数，取的钱大于余额时抛出IllegalArgumentException异常
*/
import java.util.Arrays;
import java.util.List;

public class BankSystem{
    //记录注册用户数
    static int account_num = 0;
    // 记录已经登记的用户身份证号
    static String[] idtable = new String[10];

    static boolean checkin(String idnumber) {
        List<String> list = Arrays.asList(idtable);
        if (list.contains(idnumber))
            return true;
        else
            return false;
    }

    public static void main(String args[]) {
        BookAccount account1 = new BookAccount("Eric", 0, "12345678", 0, "17130110001");
        CreditAccount account2 = new CreditAccount("Nancy", 1, "87654321", 0, "1713110040");
        CheckingAccount check = new CheckingAccount();
        //检查余额是否为负数
        check.checkBalance(account1);
        System.out.println("----------------------------------------------");
        System.out.println("initital Class BookAccount, Class CreditAccoun");
        System.out.println("----------------------------------------------");
        System.out.println("Class BookAccount");
        System.out.println("name:" + account1.name + "   ");
        System.out.println("sex:" + account1.sex);
        System.out.println("idnumber:" + account1.idnumber);
        System.out.println("balance:" + account1.balance);
        System.out.println("booknumber:" + account1.booknumber);
        System.out.println("----------------------------------------------");
        System.out.println("Class CreditAccount");
        System.out.println("name:" + account2.name + "   ");
        System.out.println("sex:" + account2.sex);
        System.out.println("idnumber:" + account2.idnumber);
        System.out.println("balance:" + account2.balance);
        System.out.println("booknumber:" + account2.cardnumber);
        System.out.println("----------------------------------------------");
        System.out.println("Deposits and Withdrawals");
        System.out.println("----------------------------------------------");
        System.out.println("Deposits 100 yuan");
        double deposits = 100;
        //检查存款是否为负数
        check.checkBalance(account1);
        account1.deposits(deposits);
        System.out.println("balance:" + account1.balance);
        double withdrawals = 55.36;
        //检查是否取款大于余额
        check.checkOverdrawn(withdrawals,account1);
        System.out.println("Withdrawals 55.36 yuan");
        account1.withdrawals(withdrawals);
        System.out.println("balance:" + account1.balance);
        System.out.println("----------------------------------------------");
        System.out.println("Change Name");
        System.out.println("----------------------------------------------");
        System.out.println("Old name:" + account1.name);
        account1.changename("Steve");
        System.out.println("New name:" + account1.name);
        System.out.println("----------------------------------------------");
        System.out.println("The amount of account : " + account_num);
        System.out.println("----------------------------------------------");
        System.out.println("Print the transaction")
        System.out.println("----------------------------------------------");
        System.out.println("");

    }
    static int numofaccount(){
        return account_num;
    }
}

class BankAccount {
    protected String name = new String("uninitialized"); // 姓名
    protected int sex = -1; // 性别，0为男性，1为女性
    protected String idnumber = new String("uninitialized"); // 身份证号
    protected double balance = 0; // 余额
    protected int i = 0;
    protected double[] record = new double[6];
    //更改用户名
    void changename(String newname) {
        name = newname;
    }
    //存钱
    void deposits(double amount){
        this.balance += amount;
        if(i == 6)
            i = 0;
        record[i] = amount;
    }
    //取钱
    void withdrawals(double amount){
        this.balance -= amount;
        if(i == 6)
            i = 0;
        record[i] = -amount;
    }
    void printRecord(){
        System.out.println(record);
    }
}

// 存折和信用卡账户，一个人仅能拥有存折和信用卡其中的一个
// 存折账户
class BookAccount extends BankAccount {
    String booknumber = new String();
    BookAccount(String name, int sex, String idnumber, double balance, String booknumber) {
        if (BankSystem.checkin(idnumber)) {
            System.out.println("idnumber exited");
            System.exit(0);
        } else {
            //System.out.print("added");
            //
            BankSystem.account_num++;
        }
        this.name = name;
        this.sex = sex;
        this.idnumber = idnumber;
        this.booknumber = booknumber;
    }
}

//信用卡账户
class CreditAccount extends BankAccount{
    String cardnumber = new String(); //信用卡号
    CreditAccount(String name, int sex, String idnumber, double balance, String cardnumber){
        if (BankSystem.checkin(idnumber)) {
            System.out.println("idnumber exited");
            System.exit(0);
        } else {
            //System.out.print("added");
            //
            BankSystem.account_num++;
        }
        this.name = name;
        this.sex = sex;
        this.idnumber = idnumber;
        this.cardnumber = cardnumber;
    }
}

class CheckingAccount {
    void checkBalance(BankAccount account){
        try{
            if(account.balance < 0)
                throw new IllegalArgumentException();
        }
        catch(IllegalArgumentException arg){
            System.out.println("The account is constructed with a negative balance");
        }
    }
    void checkDeposited(double deposits){
        try{
            if(deposits < 0)
                throw new IllegalArgumentException();
        }
        catch(IllegalArgumentException arg){
            System.out.println("a negative amount is deposited");
        }
    }
    void checkOverdrawn(double withdrawals,BankAccount account){
        try{
            if(withdrawals > account.balance)
                throw new IllegalArgumentException();
        }
        catch(IllegalArgumentException arg){
            System.out.println("The amount withdrawn exceeds the current balance");
        }
    }
}

