package com.plennegy.io;

import com.plennegy.httprequest.HTTPRequests;
import com.plennegy.models.*;
import com.plennegy.utils.Dates;
import com.plennegy.utils.Environment;
import com.plennegy.utils.GardenCenterGlobals;
import com.plennegy.utils.GlobalValues;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class CsvFile {

    private final static String LINUXDIVIDER = "/";
    private final static String WINDOWSDIVIDER = "\\";

    private static String subDirectory = "";
    private static String path;
    private CSVPrinter csvPrinter = null;
    private boolean blankHeaderLine = false;


    private List<BulkJournalImportModel> bulkJournalImportModels = new ArrayList<>();



    public String getDivider(String path) {
        return path.contains(LINUXDIVIDER) ? LINUXDIVIDER : WINDOWSDIVIDER;
    }


    private List<String> getFilePerPath(String path, String pathDivider, String extension) {
        //List to store all the files retrieved
        List<String> fileList = new ArrayList<>();

        //Get the Direcotory as a File object
        File dir = new File(path);

        String fileContents[] = dir.list();

        if(fileContents != null) {
            System.out.println("Files Contents" + fileContents.length);
        }
        else
        {
            System.out.println("Files null");
        }
        //Get all files based on the location and the extension
        File[] files = dir.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                if (extension != null) {
                    return name.toLowerCase().endsWith("." + extension);
                } else {
                    return Boolean.parseBoolean(name.toLowerCase());
                }

            }
        });

        //Loop through all files and return the file name in a list


        if (files != null) {
            System.out.println("Files " + files.length);

            for (File file : files) {
                fileList.add(file.toString().replace(path.concat(pathDivider), ""));
            }
        } else
        {
            System.out.println("Files 0");
        }
        //Return value
        return fileList;
    }//End of getFilePerPath


    public List<FileProperties> getFiles(String mainDirectory, String subDirectory, String extension, String gardenCenterCd) {
        //Create a new Arraylist to hold all the file names and return the list
        List<FileProperties> fileList = new ArrayList<>();

        //Determine the path divider if it is Linux or Windows
        String pathDivider = mainDirectory.contains(LINUXDIVIDER) ? LINUXDIVIDER : WINDOWSDIVIDER;

        //This will be the list returned from the getFiles per path method
        List<String> returnList = new ArrayList<>();

        //Check if any sub directories was supplied
        if (subDirectory == null) {
            //For no sub directories

            //Pass the main directory + the sub directory along with the pathdivider and the extension
            //All files names will be returned per sub directory
            returnList = getFilePerPath(mainDirectory, pathDivider, extension);

            //Loop through the returned list and add it to the main return object
            if(returnList != null) {
                for (String files : returnList) {
                    fileList.add(new FileProperties(mainDirectory, null, files, gardenCenterCd));
                }
            }
        } else {
            //Loop through all provided sub direcotires
            //Build up the main Path
            path = mainDirectory.concat(pathDivider.concat(subDirectory));

            //Pass the main directory + the sub directory along with the pathdivider and the extension
            //All files names will be returned per sub directory
            returnList = getFilePerPath(path, pathDivider, extension);

            //Loop through the returned list and add it to the main return object
            if(returnList != null) {
                for (String files : returnList) {
                    fileList.add(new FileProperties(path, subDirectory, files, gardenCenterCd));
                }
            }

        }
        //Return the complete list
        return fileList;
    }

    public void readFileContents(FileProperties fileProperties) throws IOException {

        String activeFileName = fileProperties.getFileName().replace(fileProperties.getFilePath(), "").replace(WINDOWSDIVIDER, "").replace(LINUXDIVIDER, "");
        DirectoryContents directoryContents = new DirectoryContents();
        Path csvPath = directoryContents.getCsvDirectoryPath(fileProperties.getFilePath(), null);
        csvPath = csvPath.resolve(fileProperties.getFileName());


        try {
            CSVParser csvParser = CSVParser.parse(csvPath, Charset.defaultCharset(),
                    CSVFormat.DEFAULT.withHeader(
                            "TRAN",
                            "BCH",
                            "TYPE",
                            "ACCNO",
                            "DATE",
                            "REF1",
                            "REF2",
                            "GROSS",
                            "DISCOUNT",
                            "VAT",
                            "TOTAL",
                            "NAME",
                            "GMV REF",
                            "INV DATE",
                            "TRUSTED",
                            "AUTO"));
            Stream<CSVRecord> csvRecordStream = StreamSupport.stream(csvParser.spliterator(), true); //Small file can be false

            List<GardenCenterModel> gardenCenterModelList = csvRecordStream
                    .skip(0)                                 //Skip the n'th rows here in this case we will skip row 1 where the headers are
                    .map(csvRecord -> csvRecord.toMap())     //Create a map to the records
                    .map(row -> new GardenCenterModel(row))  //Map the data to the POJO
                    .collect(Collectors.toList());           //Create a List that you can use later on (You will need this if you want
            // to store it in the List of GardenCenterModel variable

            ProcessData processData = new ProcessData();
            processData.invoiceTransactions(gardenCenterModelList);



            //Check if any records were in the list if it was a blank file we dont want to process it then
            if(gardenCenterModelList.size() > 0) {
                for (GardenCenterModel gardenRecord : gardenCenterModelList) {
                    gardenRecord.setGardenCenterCode(fileProperties.getGardenCenterCd());
                    gardenRecord.setFileName(fileProperties.getFileName());
                }
                writeBulkJournalImportFile(fileProperties.getFilePath(), fileProperties.getGardenCenterCd(), gardenCenterModelList);
            }


            //Move file to Archive
            archiveFile(fileProperties.getFilePath(), activeFileName);


        } catch (IOException e) {
            e.printStackTrace();
        }
    } //End of Method readCsvFileContents

    private void writeBulkJournalImportFile(String filePath, String gardenCenterCd, List<GardenCenterModel> gardenCenterModelList) {
        String filename = "GC_BulkJrnlImport" + Dates.genericDate("ddMMyyyy");
        boolean validRecord = false;
        String bprNumber = null;


        //Get all bcx Records
        //Get Http request test
        HTTPRequests httpRequests = new HTTPRequests();
        List<BcxVendorLink> bcxVendorLinkList = new ArrayList<>();
        try {
            bcxVendorLinkList = httpRequests.getBcxVendorList();  //"ROSE", "476"
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        //If we found a valid link then we can proceed else
//        if (bcxVendorLinkList.size() > 0) {
//
//            validRecord = true;

//            String bprNumber = null;
//            for (BcxVendorLink bcxVendorLink : bcxVendorLinkList) {
//                bprNumber = String.valueOf(bcxVendorLink.getBpCodeSageX3());
//            }




        if (gardenCenterModelList != null) {

            String fcy = "BM200";
            String jou = "GCCOS";
            String type = "APJ";
            String ref = "DIVF" + jou + type;
            String fcyLin = fcy;
            String desVcr = ref;
            String sac = "TRAC";
            String dimCc = ref;
            String dimDpt = "Group";
            String accountCd;

            //Blank Line, We require a blank line just after the headings for SAGE to import correctly
            //Only one blank line per file
            if(!GlobalValues.blankLine) {
                bulkJournalImportModels.add(
                        new BulkJournalImportModel("", "", "", "", "", "", "", "", "", "",
                                "", "", "", "", ""));

                GlobalValues.blankLine = true;
            }
            for (GardenCenterModel gardenCenterModel : gardenCenterModelList)
            {

                String comment = null;
                accountCd = GardenCenterGlobals.WESTCOASTACCCD.label;
                if(gardenCenterModel.getGardenCenterCode().equals(GardenCenterGlobals.ROSEBANKCD.label)) {
                    accountCd = GardenCenterGlobals.ROSEBANKACCCD.label;
                }

//                //Get Http request test
//                HTTPRequests httpRequests = new HTTPRequests();
//                List<BcxVendorLink> bcxVendorLinkList = new ArrayList<>();
//                try {
//                    bcxVendorLinkList = httpRequests.getLinkValue(gardenCenterCd, gardenCenterModel.getBcxUniqueAccountNumber());  //"ROSE", "476"
//                } catch (URISyntaxException e) {
//                    e.printStackTrace();
//                }
//
//                //If we found a valid link then we can proceed else
//                if (bcxVendorLinkList.size() > 0) {
//
//                    validRecord = true;
//
//                    String bprNumber = null;
//                    for (BcxVendorLink bcxVendorLink : bcxVendorLinkList) {
//                        bprNumber = String.valueOf(bcxVendorLink.getBpCodeSageX3());
//                    }


                bprNumber = "NOCODE";
                for (BcxVendorLink bcxVendorLink : bcxVendorLinkList)
                {
                    if(gardenCenterModel.getBcxUniqueAccountNumber().equals(bcxVendorLink.getBcxCode()))
                    {
                        bprNumber = String.valueOf(bcxVendorLink.getBpCodeSageX3());
                    }

                }



                if(!bprNumber.equals("NOCODE"))
                {
                    //vendorCodeResult = sqlResults.GetBPNumber(gardenCenterModel.getBcxUniqueAccountNumber());

                    //TODO - (4) Calculate the Vat variance and report on the mismatch of 0.05c
                    double grossAmount = Double.parseDouble(gardenCenterModel.getGrossAmount());
                    double discountAmount = Double.parseDouble(gardenCenterModel.getDiscountAmount());
                    double vatPerc = 0.15;
                    double vatTolaranceAllowed = 0.05;
                    double vatAmount = Math.round(Double.parseDouble(gardenCenterModel.getVat()));
                    double vatCalculationResult = Math.round((grossAmount + discountAmount) * vatPerc);
                    double vatTolarance = vatCalculationResult - vatAmount;

                    //Error Checking
                    if (Math.round(vatTolarance) != 0.0)
                    {
                        if ((Math.round(vatTolarance) > vatTolaranceAllowed)) {
                            System.out.println("ERROR : Vat is above tolerance");
                            comment = "ERROR : Vat is above tolerance";
                            validRecord = false;
                        }
                        if ((Math.round(vatTolarance) < vatTolaranceAllowed)) {
                            System.out.println("ERROR : Vat is below tolerance");
                            comment = "ERROR : Vat is below tolerance";
                            validRecord = false;
                        }
                    }

                    if(bprNumber == null)
                    {

                        comment = "Error : No matching Vendor Code listed for Bcx Code " + gardenCenterModel.getBcxUniqueAccountNumber();
                        validRecord = false;
                    }
                    else
                    {
                        try
                        {
                            int bcxNumberTmp = Integer.parseInt(bprNumber);
                        }
                        catch (Exception e)
                        {
                            comment = "Error : No matching Vendor Code listed for Bcx Code " + gardenCenterModel.getBcxUniqueAccountNumber();
                            validRecord = false;
                        }
                    }
                    //Error Checking Done

                    if (validRecord)
                    {
                        //First Line Gross + Discount
                        bulkJournalImportModels.add(
                                new BulkJournalImportModel(
                                        fcy,
                                        jou,
                                        Dates.getShortDateYYYY(),
                                        ref,
                                        ref,
                                        type,
                                        "  ",
                                        fcyLin,
                                        accountCd,
                                        gardenCenterModel.getGrossAmount() + gardenCenterModel.getDiscountAmount(),//Gross + Discount no vat
                                        bprNumber,
                                        null,
                                        dimCc,
                                        dimDpt,
                                        null) ); //Still need to get this desc


                        //Second Line
                        double journalAmount = Double.parseDouble(gardenCenterModel.getInvoiceTotal()) * -1;


                        //Second Line Total = Gross + discount + vat
                        bulkJournalImportModels.add(
                                new BulkJournalImportModel(
                                        fcy,
                                        jou,
                                        Dates.getShortDateYYYY(),
                                        ref,
                                        ref,
                                        type,
                                        "   ",
                                        fcyLin,
                                        accountCd,
                                        String.valueOf(journalAmount), //If the first one was Minus then this must be positive
                                        bprNumber,
                                        sac,
                                        dimCc,
                                        dimDpt,
                                        null));  //Still need to get this desc


                        //Third Line First lines Vat Amount
                        bulkJournalImportModels.add(
                                new BulkJournalImportModel(
                                        fcy,
                                        jou,
                                        Dates.getShortDateYYYY(),
                                        ref,
                                        ref,
                                        type,
                                        "    ",
                                        fcyLin,
                                        GardenCenterGlobals.GCVATACCD.label,
                                        String.valueOf(gardenCenterModel.getVat()), //Just the vat from line one
                                        bprNumber,
                                        null,
                                        dimCc,
                                        dimDpt,
                                        null) ); //Still need to get this desc



                        //TODO - Capture Valid Audit Record


                    }
                    //Write out Error Logging for the Audit report
                    else
                    {
                        //TODO - Capture Error Audit record
                        GlobalValues.auditJrnlModelList.add(new AuditJrnlModel(gardenCenterModel.getFileName(), gardenCenterModel.getTransactionNo(),
                                gardenCenterModel.getTransactionDate(), gardenCenterModel.getInvoiceType(),
                                gardenCenterModel.getBcxUniqueAccountNumber(),
                                gardenCenterModel.getInvoiceTotal(),
                                comment));
                    }
                }
                else
                {
                    //TODO - Capture Error Audit record
                    GlobalValues.auditJrnlModelList.add(new AuditJrnlModel(gardenCenterModel.getFileName(), gardenCenterModel.getTransactionNo(),
                            gardenCenterModel.getTransactionDate(), gardenCenterModel.getInvoiceType(),
                            gardenCenterModel.getBcxUniqueAccountNumber(),
                            gardenCenterModel.getInvoiceTotal(),
                            GardenCenterGlobals.NOVENDOPRLINKED.label + " for " + gardenCenterModel.getBcxUniqueAccountNumber()));
                }

            } //End of gardenCenterModelList Loop

            if (bulkJournalImportModels != null) {
                String fileLocation = Environment.BULKJOURNALFILELOCATION.label + getDivider(Environment.BULKJOURNALFILELOCATION.label)  + filename + ".csv";//path + getDivider(path) + "outputfiles" + getDivider(path) + filename + ".csv";

                try {
                    //Create File object based on the location
                    File file = new File(fileLocation);

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
                        List<String> headers = Arrays.asList("FCY", "JOU", "ACCDAT", "REF", "DESVCR", "TYP", "     ", "FCYLIN", "ACC", "AMTCUR", "BPR", "SAC",
                                "DIM:CC", "DIM:DPT", "DES");
                        csvPrinter.printRecord(headers);
                    }

                    //Write Data out
                    for (BulkJournalImportModel bulkJournalImportModel : bulkJournalImportModels) {

                        //List<String> recordData = new ArrayList<>();
                        //recordData.add(gardenCenterModel.);
                        csvPrinter.printRecord(bulkJournalImportModel.getList());
                    }

                    csvPrinter.flush();
                    csvPrinter.close();


                } catch (IOException e) {
                    e.printStackTrace();
                }

            }


        }


    }//End Of writeBulkJournalImportFile


    private void archiveFile(String filePath, String fileName) throws IOException {


        //From Directory
        String fromPathFile = filePath.contains(LINUXDIVIDER)
                ? filePath.concat(LINUXDIVIDER.concat(fileName))
                : filePath.concat(WINDOWSDIVIDER.concat(fileName));

        //To Directory
        //Determine if the directory is Windows based or Linux
        String archiveFolderName = "archive";
        String archiveFolderFile = filePath.contains(LINUXDIVIDER)
                ? filePath.concat(LINUXDIVIDER.concat(archiveFolderName.concat(LINUXDIVIDER.concat(fileName))))
                : filePath.concat(WINDOWSDIVIDER
                .concat(archiveFolderName
                        .concat(WINDOWSDIVIDER
                                .concat(fileName
                                        .replace("CSV", "bck")
                                        .replace("csv", "bck")))));


        try {
            File file = new File(archiveFolderFile);
            if (file.exists()) {
                try {
                    file.delete();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            Path archivePath = Files.move(Paths.get(fromPathFile),
                    Paths.get(archiveFolderFile));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeFile(String path, String filename, List<String> headers, List<GardenCenterModel> fileData) {

        //Headers
        /*
        //String path, String filename, List<String> headers, List<GardenCenterModel> fileData
            ArrayList<String> headers = new ArrayList<>();
            headers.add("TRAN");
            headers.add("BCH");
            headers.add("TYPE");
            headers.add("ACCNO");
            headers.add("DATE");
            headers.add("REF1");
            headers.add("REF2");
            headers.add("GROSS");
            headers.add("DISCOUNT");
            headers.add("VAT");
            headers.add("TOTAL");
            headers.add("NAME");
            headers.add("GMV REF");
            headers.add("INV DATE");
            headers.add("TRUSTED");
            headers.add("AUTO");
         */

        ///Local Drive
        //C:\\aWork\\bcs\\rosebank\\outputfiles\\testfile.csv
        String fileLocation = path + getDivider(path) + "outputfiles" + getDivider(path) + filename;

        try {
            //Create File object based on the location
            File file = new File(fileLocation);

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
                csvPrinter.printRecord(headers);
            }

            //Write Data out
            for (GardenCenterModel gardenCenterModel : fileData) {

                List<String> recordData = new ArrayList<>();
                //recordData.add(gardenCenterModel.);
                csvPrinter.printRecord(gardenCenterModel.getList());
            }

            csvPrinter.flush();
            csvPrinter.close();


        } catch (IOException e) {
            e.printStackTrace();
        }


    }//End of writeFile method




}
