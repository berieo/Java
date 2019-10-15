import java.util.Calendar;
import java.util.Date;
import java.text.*;

public class Cal {
    enum WeekdAY{
          Mon, Tue, Wed, Thu, Fri, Sat, Sun
    }
    public static void main(String args[]) {
        String[] Month = {"January","Februray","March","April","May","June","July","August","September","October","November","December"};
        int month = Integer.parseInt(args[0]); 
        int year = Integer.parseInt(args[1]);
        int weekday = 0;
        int lastday;
        int i = 0;
        int j = 1;
        String strdate = year + "-" + month + "-" + "1"; 
        System.out.println(Month[month-1] + " " + args[1]);
        System.out.println("Su\tMo\tTu\tWe\tTh\tFr\tSa");
        SimpleDateFormat format = new SimpleDateFormat("E",java.util.Locale.US);
        
		Date date = transfStrToDateTwo(strdate);//new Date();
        String time = format.format(date);
        //得知每月最后一天
        lastday = getLastDayOfMonth(year, month);
        
        switch(time){
            case "Sun":
                weekday = 0;
                break;
            case "Mon":
                weekday = 1;
                System.out.print("\t");
                break;
            case "Tue":
                weekday = 2;
                System.out.print("\t\t");
                break;
            case "Wed":
                weekday = 3;
                System.out.print("\t\t\t");
                break;
            case "Thu":
                weekday = 4;
                System.out.print("\t\t\t\t");
                break;
            case "Fir":
                weekday = 5;
                System.out.print("\t\t\t\t\t");
                break;
            case "Sat":
                weekday = 6;
                System.out.print("\t\t\t\t\t\t");
                break;
        }
        for(i = weekday; j <= lastday; i++,j++){
            System.out.printf("%d\t",j);
            if(i == 6){
                i = -1;
                System.out.println();
            } 
        }
    }

    //获取某年某月最后一天
    public static int getLastDayOfMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();
        // 设置年份
        cal.set(Calendar.YEAR, year);
        // 设置月份
        cal.set(Calendar.MONTH, month - 1);
        // 获取某月最大天数
        int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        // 设置日历中月份的最大天数
        cal.set(Calendar.DAY_OF_MONTH, lastDay);
        return lastDay;
    }

    //将字符串日期转化为date格式日期
    public static Date transfStrToDateTwo(String dateStr) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = sdf.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
    }
}
