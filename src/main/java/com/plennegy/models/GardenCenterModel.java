package com.plennegy.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GardenCenterModel {
    private String transactionNo;               //Col A :
    private String bchCd;                       //Col B : NotInUse
    private String InvoiceType;                 //Col C : Invoice Type INV. = Invoice : C/NOTE = Credit Note  => Type
    private String bcxUniqueAccountNumber;      //Col D : This will link back to the Vendor code in Sage, We need to keep a lookup database => ACCNO
    private String transactionDate;             //Col E : Transaction => DATE
    private String InvoiceRefNumber;            //Col F : SAGE Ref number to link back to the Bcx Reference => REF1
    private String referenceCode;               //Col G : Not in Use => REF2
    private String grossAmount;                 //Col H : => GROSS (the minus is at the back ant needs to be removed)
    private String discountAmount;              //Col I : => DISCOUNT; (the minus is at the back ant needs to be removed)
    private String vat;                         //Col J : => VAT (the minus is at the back ant needs to be removed)
    private String invoiceTotal;                //Col K : Invoice total => TOTAL = (GROSS + DISCOUNT + VAT) (the minus is at the back ant needs to be removed)
    private String vendorName;                  //Col L : Vendor Name => NAME
    private String gmvRef;                      //Col M : Not in Use => GMV REF
    private String invoiceDate;                 //Col N : Not in Use => INV DATE
    private String trusted;                     //Col O : Not in Use => TRUSTED
    private String auto;                        //Col P : Not in Use => AUTO
    private String gardenCenterCode;
    private String fileName;

    public GardenCenterModel()
    {
    }

    public GardenCenterModel(String transactionNo, String bchCd, String invoiceType, String bcxUniqueAccountNumber,
                             String transactionDate, String invoiceRefNumber, String referenceCode, String grossAmount,
                             String discountAmount, String vat, String invoiceTotal, String vendorName, String gmvRef,
                             String invoiceDate, String trusted, String auto, String gardenCenterCode, String fileName)
    {
        this.transactionNo = transactionNo;
        this.bchCd = bchCd;
        this.InvoiceType = invoiceType;
        this.bcxUniqueAccountNumber = bcxUniqueAccountNumber;
        this.transactionDate = transactionDate;
        this.InvoiceRefNumber = invoiceRefNumber;
        this.referenceCode = referenceCode;
        this.grossAmount = grossAmount;
        this.discountAmount = discountAmount;
        this.vat = vat;
        this.invoiceTotal = invoiceTotal;
        this.vendorName = vendorName;
        this.gmvRef = gmvRef;
        this.invoiceDate = invoiceDate;
        this.trusted = trusted;
        this.auto = auto;
        this.gardenCenterCode = gardenCenterCode;
        this.fileName = fileName;
    }


    public GardenCenterModel(Map<String, String> row)
    {
        this.transactionNo = row.get("TRAN").trim();
        this.bchCd = row.get("BCH").trim();
        this.InvoiceType = row.get("TYPE").trim();
        this.bcxUniqueAccountNumber = row.get("ACCNO").trim();
        this.transactionDate = row.get("DATE").trim();
        this.InvoiceRefNumber = row.get("REF1").trim();
        this.referenceCode = row.get("REF2").trim();

        this.grossAmount = row.get("GROSS").trim();
        if (this.grossAmount.trim().contains("-"))
        {
            this.grossAmount = "-" + this.grossAmount.trim().replace("-", "");
        }

        this.discountAmount = row.get("DISCOUNT").trim();
        if (this.discountAmount.trim().contains("-"))
        {
            this.discountAmount = "-" + this.discountAmount.trim().replace("-", "");
        }

        this.vat = row.get("VAT").trim();
        if (this.vat.trim().contains("-"))
        {
            this.vat = "-" + this.vat.trim().replace("-", "");
        }

        this.invoiceTotal = row.get("TOTAL").trim();
        if (this.invoiceTotal.trim().contains("-"))
        {
            this.invoiceTotal = "-" + this.invoiceTotal.trim().replace("-", "");
        }
        this.vendorName = row.get("NAME").trim();
        this.gmvRef = row.get("GMV REF").trim();
        this.invoiceDate = row.get("INV DATE").trim();
        this.trusted = row.get("TRUSTED").trim();
        this.auto = row.get("AUTO").trim();
    }

    public String getTransactionNo()
    {
        return transactionNo;
    }

    public void setTransactionNo(String transactionNo)
    {
        this.transactionNo = transactionNo.trim();
    }

    public String getBchCd()
    {
        return bchCd;
    }

    public void setBchCd(String bchCd)
    {
        this.bchCd = bchCd.trim();
    }

    public String getInvoiceType()
    {
        return InvoiceType;
    }

    public void setInvoiceType(String invoiceType)
    {
        InvoiceType = invoiceType.trim();
    }

    public String getBcxUniqueAccountNumber()
    {
        return bcxUniqueAccountNumber;
    }

    public void setBcxUniqueAccountNumber(String bcxUniqueAccountNumber)
    {
        this.bcxUniqueAccountNumber = bcxUniqueAccountNumber.trim();
    }

    public String getTransactionDate()
    {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate)
    {
        this.transactionDate = transactionDate.trim();
    }

    public String getInvoiceRefNumber()
    {
        return InvoiceRefNumber;
    }

    public void setInvoiceRefNumber(String invoiceRefNumber)
    {
        InvoiceRefNumber = invoiceRefNumber.trim();
    }

    public String getReferenceCode()
    {
        return referenceCode;
    }

    public void setReferenceCode(String referenceCode)
    {
        this.referenceCode = referenceCode.trim();
    }

    public String getGrossAmount()
    {
        return grossAmount;
    }

    public void setGrossAmount(String grossAmount)
    {
        if (grossAmount.trim().contains("-"))
        {
            grossAmount = "-" + grossAmount.trim().replace("-", "");
        }

        this.grossAmount = grossAmount.trim();
    }

    public String getDiscountAmount()
    {
        return discountAmount;
    }

    public void setDiscountAmount(String discountAmount)
    {
        if (discountAmount.trim().contains("-"))
        {
            discountAmount = "-" + discountAmount.trim().replace("-", "");
        }

        this.discountAmount = discountAmount.trim();
    }

    public String getVat()
    {

        return vat;
    }

    public void setVat(String vat)
    {
        if (vat.trim().contains("-"))
        {
            vat = "-" + vat.trim().replace("-", "");
        }

        this.vat = vat.trim();
    }

    public String getInvoiceTotal()
    {
        return invoiceTotal;
    }

    public void setInvoiceTotal(String invoiceTotal)
    {
        if (invoiceTotal.trim().contains("-"))
        {
            invoiceTotal = "-" + invoiceTotal.trim().replace("-", "");
        }

        this.invoiceTotal = invoiceTotal.trim();
    }

    public String getVendorName()
    {
        return vendorName;
    }

    public void setVendorName(String vendorName)
    {
        this.vendorName = vendorName.trim();
    }

    public String getGmvRef()
    {
        return gmvRef;
    }

    public void setGmvRef(String gmvRef)
    {
        this.gmvRef = gmvRef.trim();
    }

    public String getInvoiceDate()
    {
        return invoiceDate;
    }

    public void setInvoiceDate(String invoiceDate)
    {
        this.invoiceDate = invoiceDate.trim();
    }

    public String getTrusted()
    {
        return trusted;
    }

    public void setTrusted(String trusted)
    {
        this.trusted = trusted.trim();
    }

    public String getAuto()
    {
        return auto;
    }

    public void setAuto(String auto)
    {
        this.auto = auto.trim();
    }

    public String getGardenCenterCode() {
        return gardenCenterCode;
    }

    public void setGardenCenterCode(String gardenCenterCode) {
        this.gardenCenterCode = gardenCenterCode;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public List<String> getList()
    {
        List<String> record = new ArrayList<>();
        record.add(this.transactionNo);
        record.add(this.bchCd);
        record.add(this.InvoiceType);
        record.add(this.bcxUniqueAccountNumber);
        record.add(this.transactionDate);
        record.add(this.InvoiceRefNumber);
        record.add(this.referenceCode);
        record.add(this.grossAmount);
        record.add(this.discountAmount);
        record.add(this.vat);
        record.add(this.invoiceTotal);
        record.add(this.vendorName);
        record.add(this.gmvRef);
        record.add(this.invoiceDate);
        record.add(this.trusted);
        record.add(this.auto);

        return record;
    }


}
