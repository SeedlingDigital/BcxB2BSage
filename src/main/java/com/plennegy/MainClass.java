package com.plennegy;

import com.plennegy.scheduledtask.ScheduledTask;
import com.plennegy.utils.Dates;
import com.plennegy.utils.Environment;
import com.plennegy.utils.GlobalValues;

import java.io.IOException;
import java.sql.Timestamp;

public class MainClass {

    public static void main(String[] args) throws IOException {
        System.out.println("(1) :" + Dates.getDate1());
        System.out.println("(2) :" + Dates.genericDate("EEEEE dd MMMMM yyyy HH:mm:ss.SSSZ"));

        System.out.println("(3) :" + Dates.getDefDate());
        System.out.println("(4) :" + Dates.getShortDefDate());

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        System.out.println("Start Time Time : " + timestamp);

        System.out.println("Main Start");

        ScheduledTask scheduledTask = new ScheduledTask();

        GlobalValues.auditJrnlModelList.clear();


        //Test
        /**
         * TEST
         * comment out Productions code tyo stop the time task
         */
          //GlobalValues.environment = Environment.TESTENV.label;
          //scheduledTask.runBcxCronJob();

         /**
          * PRODUCTION
          * comment out Productions code tyo stop the time task
          */

//Production
        GlobalValues.environment = Environment.PRODENV.label;
        scheduledTask.runBcxCronJob();
    }

}
