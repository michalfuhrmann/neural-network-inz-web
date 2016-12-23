package com.pg.edu.api;

public class Main {

//    private static Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {


        double x2 = 54.5239707456;
        double x1 = 54.54523799999999;

        double dif_x = Math.pow(x2 - x1, 2);

        double y2 = 17.698521316100027;
        double y1 = 17.721869999999967;

        double dif_y = Math.pow(y2 - y1, 2);


        System.out.println(Math.sqrt(dif_x - dif_y));
    }

}