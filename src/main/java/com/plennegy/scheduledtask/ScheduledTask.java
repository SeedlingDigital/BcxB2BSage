package com.plennegy.scheduledtask;

import com.plennegy.io.CsvFile;
import com.plennegy.models.AuditJrnlModel;
import com.plennegy.models.FileProperties;
import com.plennegy.utils.Dates;
import com.plennegy.utils.Environment;
import com.plennegy.utils.GlobalValues;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TimerTask;

public class ScheduledTask  extends TimerTask {

    private static CsvFile csvFile = new CsvFile();
    private static String mainDirectory = "C:\\aaWork\\bcx";
    private static String subFolders[] = {"rosebank", "westcoast"};
    private static List<String> subFolderList = new ArrayList<>();
    private static String subDirectory = "";
    private static String path;
    private final static String LINUXDIVIDER = "/";
    private final static String WINDOWSDIVIDER = "\\";
    private String pathDivider = mainDirectory.contains(LINUXDIVIDER) ? LINUXDIVIDER : WINDOWSDIVIDER;
    private CSVPrinter csvPrinter = null;
    //private List<AuditJrnlModel> auditJrnlModelList = new ArrayList<>();

    public void runBcxCronJob() throws IOException {

        CsvFile csvFile = new CsvFile();


        List<FileProperties> filesList = new ArrayList<>();

        //Production
        if (Environment.PRODENV.label.equals(GlobalValues.environment)) {

            //Get Directory
            mainDirectory = Environment.ROSEBANKFILEBASEDIRECTORY.label;
            filesList.addAll(csvFile.getFiles(mainDirectory,
                    null, //Environment.ROSEBANKSUBDIRECTORY.label,
                    "csv",
                    Environment.ROSEBANKCODE.label));

            //Get Directory
            mainDirectory = Environment.WESTCOASTFILEBASEDIRECTORY.label;
            filesList.addAll(csvFile.getFiles(mainDirectory,
                    null,//Environment.WESTCOASTSUBDIRECTORY.label,
                    "csv",
                    Environment.WESTCOASTCODE.label));

        }
        //Test Local Directory
        else {
            //Your directory structure needs to reflect the following
            //Rosebank => C://aaWork/bcx/rosebank/extracts
            //WestCoast => C://aaWork/bcx/westcoast/extracts

            mainDirectory = Environment.LOCALDIRECTORY.label;
            filesList.addAll(csvFile.getFiles(mainDirectory, Environment.ROSEBANKSUBDIRECTORY.label, "csv", Environment.TESTGCCODE.label));
        }


        if(filesList.size() > 0) {
            for (FileProperties fileProperties : filesList) {

                System.out.println("File Name : " + fileProperties.getFilePath().concat(pathDivider.concat(fileProperties.getFileName())));

                //Read the files content
                //Check Path
                csvFile.readFileContents(new FileProperties(fileProperties.getFilePath(),
                        fileProperties.getSubDirectory(),
                        fileProperties.getFileName().replace(fileProperties.getFilePath().concat(pathDivider), ""),
                        fileProperties.getGardenCenterCd()));
            }
        }
        if (GlobalValues.auditJrnlModelList != null || GlobalValues.auditJrnlModelList.size() > 0) {
            writeAuditReport();
        }


    }//End of runBcxCronJob method


    private void writeAuditReport() throws IOException {
        String filename = "AuditReposrt_GC_BulkJrnlImport_" + Dates.genericDate("ddMMyyyy");
        String fileLocation = Environment.BULKJOURNALFILELOCATION.label + pathDivider  + filename + ".csv";//path + pathDivider + "outputfiles" + pathDivider + filename + ".csv";
        boolean validRecord = false;

        try {
            //Create File object based on the location
            File file = new File(fileLocation);

            //Check if Global List is not empty
            if (GlobalValues.auditJrnlModelList != null) {

                //If File Exists then append the file and dont override it
                if (file.exists()) {
                    try {
                        csvPrinter = new CSVPrinter(new FileWriter(fileLocation, true), CSVFormat.DEFAULT);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
                //Create a new File
                else {
                    csvPrinter = new CSVPrinter(new FileWriter(fileLocation), CSVFormat.DEFAULT);

                    //Create Headers
                    List<String> headers = Arrays.asList("FileName", "TransactionNumber", "TransactionDate", "TransactionType",
                            "BCXAccountNumber", "TotalAmount", "Reason");

                    //Write Headers
                    csvPrinter.printRecord(headers);
                }
            }


            //Details
            for (AuditJrnlModel auditJrnlModelRec : GlobalValues.auditJrnlModelList) {

                //List<String> recordData = new ArrayList<>();
                //recordData.add(gardenCenterModel.);
                csvPrinter.printRecord(auditJrnlModelRec.getList());
            }

            csvPrinter.flush();
            csvPrinter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void run() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        System.out.println("Run Time : " + timestamp);

        try {
            System.out.println("Start job");
            runBcxCronJob();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
