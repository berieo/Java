/*
    将输入的两个字符串转为int整型
    根据起始下标打印字符串的子串
*/

public class Substring {
    public static void main(String[] args) {
        int i = Integer.parseInt(args[1]); 
        int length = Integer.parseInt(args[2]);
        for(; length > 0; i++,length--){
            System.out.print(args[0].charAt(i));
        }
    }
}