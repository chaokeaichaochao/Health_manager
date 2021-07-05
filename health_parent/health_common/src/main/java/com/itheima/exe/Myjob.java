package com.itheima.exe;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Myjob {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public void  abc(){
        System.out.println(sdf.format(new Date()));
    }
}
