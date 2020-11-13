package com.example.demo.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.Timestamp;

public class timemapper {
    static public Date timestamp2date(Timestamp timestamp) {
        String pat1 = "yyyy-MM-dd HH:mm:ss.SSS" ;
        SimpleDateFormat sdf1 = null;
        try{
            sdf1 = new SimpleDateFormat(pat1);
            System.out.println(new Timestamp(sdf1.parse(timestamp.toString()).getTime()));
            return sdf1.parse(timestamp.toString());
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    static public Timestamp date2timestamp(Date date){
        String pat1 = "yyyy-MM-dd HH:mm:ss.SSS" ;
        SimpleDateFormat sdf1 = null;
        try{
            sdf1 = new SimpleDateFormat(pat1);
            return new Timestamp(date.getTime());
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    static public Timestamp datestr2timestamp(String datestr){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss" );
        try {
            Date date=sdf.parse(datestr);
            return new Timestamp(date.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
