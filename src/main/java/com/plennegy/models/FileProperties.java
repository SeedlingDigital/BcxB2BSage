package com.plennegy.models;

public class FileProperties {

    private String filePath;
    private String subDirectory;
    private String fileName;
    private String gardenCenterCd;


    public FileProperties()
    {
    }

    public FileProperties(String filePath, String subDirectory, String fileName, String gardenCenterCd)
    {
        this.filePath = filePath;
        this.subDirectory = subDirectory;
        this.fileName = fileName;
        this.gardenCenterCd = gardenCenterCd;
    }

    public String getFilePath()
    {
        return filePath;
    }

    public void setFilePath(String filePath)
    {
        this.filePath = filePath;
    }

    public String getSubDirectory() {
        return subDirectory;
    }

    public void setSubDirectory(String subDirectory) {
        this.subDirectory = subDirectory;
    }

    public String getFileName()
    {
        return fileName;
    }

    public void setFileName(String fileName)
    {
        this.fileName = fileName;
    }

    public String getGardenCenterCd() {
        return gardenCenterCd;
    }

    public void setGardenCenterCd(String gardenCenterCd) {
        this.gardenCenterCd = gardenCenterCd;
    }

    @Override
    public String toString() {
        return "FileProperties{" +
                "filePath='" + filePath + '\'' +
                ", subDirectory='" + subDirectory + '\'' +
                ", fileName='" + fileName + '\'' +
                ", gardenCenterCd='" + gardenCenterCd + '\'' +
                '}';
    }

}
