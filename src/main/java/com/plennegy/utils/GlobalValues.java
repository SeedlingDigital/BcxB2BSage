package com.plennegy.utils;

import com.plennegy.models.AuditJrnlModel;

import java.util.ArrayList;
import java.util.List;

public class GlobalValues {

    public static String environment = "TEST";

    public static List<AuditJrnlModel> auditJrnlModelList = new ArrayList<>();

    public static boolean blankLine = false;




    public static String connectionString(String environment)
    {

        if(environment.equals(Environment.TESTENV.label))
        {
            return Environment.TESTCONNSTRING.label;
        }
        else
        {
            return Environment.PRODUCTIONCONNSTRING.label;
        }

    }//End of connectionString


}
