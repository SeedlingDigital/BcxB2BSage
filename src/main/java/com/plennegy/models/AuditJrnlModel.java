package com.plennegy.models;

import java.util.ArrayList;
import java.util.List;

public class AuditJrnlModel {
    private String fileName;
    private String transactionNumber;
    private String transactionDate;
    private String transactionType;
    private String bcxAccountNumber;
    private String totalAmount;
    private String comment;

    public AuditJrnlModel()
    {
    }

    public AuditJrnlModel(String fileName, String transactionNumber, String transactionDate, String transactionType, String bcxAccountNumber, String totalAmount, String comment)
    {
        this.fileName = fileName;
        this.transactionNumber = transactionNumber;
        this.transactionDate = transactionDate;
        this.transactionType = transactionType;
        this.bcxAccountNumber = bcxAccountNumber;
        this.totalAmount = totalAmount;
        this.comment = comment;
    }

    public List<String> getList()
    {
        List<String> auditJrnlList = new ArrayList<>();

        auditJrnlList.add(this.fileName);
        auditJrnlList.add(this.transactionNumber);
        auditJrnlList.add(this.transactionDate);
        auditJrnlList.add(this.transactionType);
        auditJrnlList.add(this.bcxAccountNumber);
        auditJrnlList.add(this.totalAmount);
        auditJrnlList.add(this.comment);

        return auditJrnlList;
    };


    public String getFileName()
    {
        return fileName;
    }

    public void setFileName(String fileName)
    {
        this.fileName = fileName;
    }

    public String getTransactionNumber()
    {
        return transactionNumber;
    }

    public void setTransactionNumber(String transactionNumber)
    {
        this.transactionNumber = transactionNumber;
    }

    public String getTransactionDate()
    {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate)
    {
        this.transactionDate = transactionDate;
    }

    public String getTransactionType()
    {
        return transactionType;
    }

    public void setTransactionType(String transactionType)
    {
        this.transactionType = transactionType;
    }

    public String getBcxAccountNumber()
    {
        return bcxAccountNumber;
    }

    public void setBcxAccountNumber(String bcxAccountNumber)
    {
        this.bcxAccountNumber = bcxAccountNumber;
    }

    public String getTotalAmount()
    {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount)
    {
        this.totalAmount = totalAmount;
    }

    public String getComment()
    {
        return comment;
    }

    public void setComment(String comment)
    {
        this.comment = comment;
    }




}
