package com.plennegy.utils;

public enum Environment {
    TESTCONNSTRING("jdbc:oracle:thin:@10.3.15.102:1526:SAMISTST"),      //"jdbc:oracle:thin:@10.3.15.102:1526:STARKE"),
    PRODUCTIONCONNSTRING("jdbc:oracle:thin:@10.3.15.102:1526:SAMISTST"),     //"jdbc:oracle:thin:@10.3.15.102:1526:STARKE"),
    TESTENV("TEST"),
    PRODENV("PROD"),
    //ROSEBANKFILEBASEDIRECTORY("Z:\\"), //""\\10.3.18.10\\"),  //Drives were mapped by Ops
    ROSEBANKFILEBASEDIRECTORY("C:\\bcx\\rosebank\\"), //""\\10.3.18.10\\"),  //Drives were mapped by Ops
    ROSEBANKSUBDIRECTORY("\\rosebank\\extracts"),
    ROSEBANKCODE("ROSE"),
    //WESTCOASTFILEBASEDIRECTORY("Y:\\"), //""\\10.3.18.10\\"),   //Drives were mapped by Ops
    WESTCOASTFILEBASEDIRECTORY("C:\\bcx\\westcoast\\"), //""\\10.3.18.10\\"),   //Drives were mapped by Ops
    WESTCOASTSUBDIRECTORY("\\westbank\\extracts"),
    WESTCOASTCODE("WEST"),
    LOCALDIRECTORY("C:\\aaWork\\bcx"),
    TESTGCCODE("ROSE"),
    BULKJOURNALFILELOCATION("C:\\bcx\\bulkjournals"),
    BULKJOURNALARCHIVELOCATION("C:\\bcx\\archive");



    public final String label;

    Environment(String label)
    {
        this.label = label;
    }
}
