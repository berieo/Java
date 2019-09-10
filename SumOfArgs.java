public class SumOfArgs{
    public static void main(String args[]){
        int i;                      //args[]计数器
        int j;                      //字符串计数器
        int n = 0;                  //记录数组长度
        int m = 0;                  //十的次方
        int num;                    //记录字符串长度
        int sum = 0;                //记录求和结果
        int number[] = new int[10]; //存储数字
        boolean isnumber = false;   //判断是否为数字
        //判断字符串是否都为数字并且存储在number[]中
        for(i = 0; i < args.length; i++){
            num = args[i].length();
            for(j = 0; j < num; j++){
                if(args[i].charAt(j) < 48 || args[i].charAt(j) > 57){
                    isnumber = false;
                    break;
                }
                if(j == num - 1){
                    isnumber = true;
                }
            }
            m = 0;   //次方数初始化
            if(isnumber == true){
                for(j = num - 1; j >= 0; j--)
                    number[n] = number[n] + (args[i].charAt(j) - 48) * (int)Math.pow(10, m++);
                    n++;
            }
        }
        //将number[]求和
        for(i = 0; i < n; i++){
            sum = sum + number[i];
        }
        System.out.println(sum);
    }
}