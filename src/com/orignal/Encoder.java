package com.orignal;

import java.util.regex.Pattern;

/**
 * Created by corew on 1/21/2016 0021.
 */
public class Encoder {
    private boolean isPositive=true;
    private boolean isInteger=false;
    public String encode(String value){
        // do something preview
        value=specify(init(value));
        int length=value.length();
        int point=value.indexOf(".");
        if(point==-1){
            point=value.length();
        }
        StringBuilder encodingValue=new StringBuilder();
        if(this.isPositive){  // a positive floating-point number
            // tag value
            encodingValue.append(String.format("%03d",((int)(point/2)-1)+193));
            // integer part
            for(int i=0;i<point;i+=2){
                encodingValue.append(String.format("%03d",Integer.parseInt(value.substring(i,i+2))));
            }
            // decimal part
            for(int i=point+1;i<length;i+=2){
                encodingValue.append(String.format("%03d",Integer.parseInt(value.substring(i,i+2))));
            }
        }else{
            // tag value
            encodingValue.append(String.format("%03d",62-((int)(point/2)-1)));
            // integer part
            for(int i=0;i<point;i+=2){
                encodingValue.append(String.format("%03d",100-Integer.parseInt(value.substring(i,i+2))));
            }
            // decimal part
            for(int i=point+1;i<length;i+=2){
                encodingValue.append(String.format("%03d",100-Integer.parseInt(value.substring(i,i+2))));
            }
        }
        return encodingValue.toString();
    }
    // initialization
    private String init(String value){
        // delete the black space
        value=value.trim();
        // judge whether the value is a decimal.
        if(!(Pattern.matches("^[+|-]?\\d+\\.?\\d*$",value))){
            throw new IllegalArgumentException();
        }
        // judge whether the value is poaitive or negative.
        if(value.substring(0,1).equals("-")){  // e.g.,-12.34
            this.isPositive=false;
            value=value.substring(1,value.length());
        }else if(value.substring(0,1).equals("+")){  // e.g., +12.34
            this.isPositive=true;
            value=value.substring(1,value.length());
        }else{  // e.g., 12.34
            this.isPositive=true;
        }
        return value;
    }
    // specification
    private String specify(String value){ // what value represents a positive floating-point number.
        StringBuilder strValue=new StringBuilder(value);
        int point=strValue.indexOf(".");

        // judge if what value is a integer£¬e.g., 1234.
        if(point==-1){
            point=strValue.length();
            this.isInteger=true;
        }
        // if what value is like 0000.00-->0.00, 0003.00-->3.00
        int i=0;
        int zeroCount=0;
        while(i<point){
            if(value.substring(i,i+1).equals("0")){
                zeroCount++;
                i++;
            }else{
                break;
            }
        }
        if(zeroCount!=0){
            if(zeroCount==point){  // e.g., 0000.00-->0.00
                strValue=new StringBuilder("0").append(strValue.substring(point,strValue.length()));
            }else { // e.g., 0003.00-->3.00
                strValue = new StringBuilder(strValue.substring(zeroCount, strValue.length()));
            }
            if(this.isInteger==false){
                point=strValue.indexOf("."); // regain the point.
            }else{
                point=strValue.length();
            }
        }
        // decimal part
        int lengthAfterPoint=strValue.length()-1-point;
        if(lengthAfterPoint!=-1) {  // it's not a integer value.
            if (lengthAfterPoint % 2 != 0) {    // if the length of decimal part is a odd number£¬supplement 0 in the end.
                strValue.append("0");
                //metadata.setSupplement(true);
            }
        }
        // integer part
        int lengthBeforePoint=point;
        if(lengthBeforePoint % 2 !=0){ // if the length of integer part is a odd number, supplement 0 at the beginning.
            strValue=new StringBuilder("0").append(strValue);
        }

        return strValue.toString();
    }
    // test
    public static void main(String[] args) {
        String str;
        str = "12.34";
        str = "12.340";
        str = "112.340";
        str = "012.34";
        str = "00012.34";
        str = "0000.34";
        str = "-12.34";
        str = "-12.340";
        str = "+0112.34";
        str = " 12.34";
        str = "012.3 4";
        str = "123";
        str = "0000";
        str = "0.0";
        str = "-0.0";
        str = "-000.00000011";
        str="51275.12496";
        str="5.9";
        System.out.println(new Encoder().encode(str));
    }
}
