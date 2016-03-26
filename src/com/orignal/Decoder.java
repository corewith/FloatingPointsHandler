package com.orignal;

/**
 * Created by corew on 1/21/2016 0021.
 */
public class Decoder {
    public String decode(String value){
        int length=value.length();
        int point=Integer.parseInt(value.substring(0,3));
        StringBuilder str=new StringBuilder();
        boolean isPositive=true;
        if(point>=193){
            isPositive=true;
        }
        if(point<=62){
            isPositive=false;
        }
        // positive
        if(isPositive){
            point-=193;
            for(int i=3;i<length;i+=3){
                if(i==(point+2)*3){
                    str.append(".");
                }
                int temp=Integer.parseInt(value.substring(i,i+3));
                if(i==3){  // the first value and the last value
                    str.append(temp);
                }else{
                    str.append(String.format("%02d",temp));
                }
            }
        }else{
            point=62-point;
            for(int i=3;i<length;i+=3){
                if(i==(point+2)*3){
                    str.append(".");
                }
                int temp=Integer.parseInt(value.substring(i,i+3));
                temp=100-temp;
                if(i==3){
                    str.append(temp);
                }else{
                    str.append(String.format("%02d",temp));
                }
            }
            str=new StringBuilder("-").append(str);
        }
        return str.toString();
    }
}
