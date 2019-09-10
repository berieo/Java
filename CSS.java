public class CSS{
    public static void main(String args[]){
        //通用字符串
        String select = new String("select");
        String withbooks = new String("with books");
        String and = new String("; and");
        //每一科目对应的书名
        String javabook[] = new String[]{"Thinking in Java","Java"};
        String webbook[] = new String[]{"Http","Web"};
        //科目种类
        String coursename[] = new String[]{"Java","WebEngineering"};
        //书籍类初始化
        Book java = new Book(javabook);
        Book web = new Book(webbook);
        //课程类初始化
        Course course = new Course(coursename);
        //计数器
        int i;
        int j;
        //判断学生输入类型
        //判断为Stuid类型
        if(args[0].charAt(0) >= 48 && args[0].charAt(0) <= 57){
            Stuid stuid = new Stuid(args[0]);
            Student student = new Student(stuid);
        }
        //判断为Stuname类型
        else if (args[0].charAt(0) == 46){
            Stuname stuname = new Stuname(args[0]); 
            Student student = new Student(stuname);
        }
        //判断为Stuemail类型
        else{
            Stuemail stuemail = new Stuemail(args[0]);
            Student student = new Student(stuemail);
        }
        //输出到命令行
        for(i = 1; i < args.length; i++){
            if(i > 1){
                System.out.print(and + " ");
            }
            if(i == 1){
                System.out.print(args[0] + " ");
            }
            if(args[i].equals(course.course[0])){
                System.out.print(select + " " + args[i] + " " + withbooks + " ");
                for(j = 0; j < java.book.length; j++){
                    System.out.print(java.book[j]);
                    if(j != java.book.length - 1){
                        System.out.print(", ");
                    }
                }
            }
            if(args[i].equals(course.course[1])){
                System.out.print(select + " " + args[i] + " " + withbooks + " ");
                for(j = 0; j < web.book.length; j++){
                    System.out.print(web.book[j]);
                    if(j != java.book.length - 1)
                        System.out.print(", ");
                }
            }
            
        }
    }
}

//图书类型,图书种类是已知的，所以在代码中写入后进行字符串数组复制
class Book{
    String book[];
    Book(String str[]){
        book = str.clone();
    }
}

//课程类型，课程种类未知，
class Course{
    String course[];
    Course(String str[]){
        course = str.clone();
    }
}

//学生类型
class Student{
    Stuid stuid;
    Stuname stuname;
    Stuemail stuemail;
    Student(Stuid id){
        stuid = id;
    }
    Student(Stuname name){
        stuname = name;
    }
    Student(Stuemail email){
        stuemail = email;
    }
}


//学生id类型
class Stuid{
    String id;
    Stuid(String str){
        id = str;
    }
}
//学生姓名类型
class Stuname{
    String name;
    Stuname(String str){
        name = str;
    }
}
//学生邮箱类型
class Stuemail{
    String email;
    Stuemail(String str){
        email = str;
    }
}


