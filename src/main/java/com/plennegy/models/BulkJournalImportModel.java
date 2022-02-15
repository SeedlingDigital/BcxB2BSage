package com.plennegy.models;

import java.util.ArrayList;
import java.util.List;

public class BulkJournalImportModel {
    private String fcy;
    private String jou;
    private String accDat;
    private String ref;
    private String desVcr;
    private String typ;
    private String blankLine;
    private String fcyLin;
    private String acc;
    private String amtCur;
    private String bpr;
    private String sac;
    private String dimCc;
    private String dimDpt;
    private String des;

    public BulkJournalImportModel()
    {
    }

    public BulkJournalImportModel(String fcy, String jou, String accDat, String ref, String desVcr, String typ, String blankLine, String fcyLin, String acc, String amtCur, String bpr, String sac, String dimCc, String dimDpt, String des)
    {
        this.fcy = fcy;
        this.jou = jou;
        this.accDat = accDat;
        this.ref = ref;
        this.desVcr = desVcr;
        this.typ = typ;
        this.blankLine = blankLine;
        this.fcyLin = fcyLin;
        this.acc = acc;
        this.amtCur = amtCur;
        this.bpr = bpr;
        this.sac = sac;
        this.dimDpt = dimDpt;
        this.des = des;
    }


    public List<String> getList()
    {
        List<String> record = new ArrayList<>();
        record.add(this.fcy);
        record.add(this.jou);
        record.add(this.accDat);
        record.add(this.ref);
        record.add(this.desVcr);
        record.add(this.typ);
        record.add(this.blankLine);
        record.add(this.fcyLin);
        record.add(this.acc);
        record.add(this.amtCur);
        record.add(this.bpr);
        record.add(this.sac);
        record.add(this.dimDpt);
        record.add(this.des);

        return record;
    }



    public String getFcy()
    {
        return fcy;
    }

    public void setFcy(String fcy)
    {
        this.fcy = fcy;
    }

    public String getJou()
    {
        return jou;
    }

    public void setJou(String jou)
    {
        this.jou = jou;
    }

    public String getAccDat()
    {
        return accDat;
    }

    public void setAccDat(String accDat)
    {
        this.accDat = accDat;
    }

    public String getRef()
    {
        return ref;
    }

    public void setRef(String ref)
    {
        this.ref = ref;
    }

    public String getDesVcr()
    {
        return desVcr;
    }

    public void setDesVcr(String desVcr)
    {
        this.desVcr = desVcr;
    }

    public String getTyp()
    {
        return typ;
    }

    public void setTyp(String typ)
    {
        this.typ = typ;
    }

    public String getFcyLin()
    {
        return fcyLin;
    }

    public void setFcyLin(String fcyLin)
    {
        this.fcyLin = fcyLin;
    }

    public String getAcc()
    {
        return acc;
    }

    public void setAcc(String acc)
    {
        this.acc = acc;
    }

    public String getAmtCur()
    {
        return amtCur;
    }

    public void setAmtCur(String amtCur)
    {
        this.amtCur = amtCur;
    }

    public String getBpr()
    {
        return bpr;
    }

    public void setBpr(String bpr)
    {
        this.bpr = bpr;
    }

    public String getSac()
    {
        return sac;
    }

    public void setSac(String sac)
    {
        this.sac = sac;
    }

    public String getDimCc()
    {
        return dimCc;
    }

    public void setDimCc(String dimCc)
    {
        this.dimCc = dimCc;
    }

    public String getDimDpt()
    {
        return dimDpt;
    }

    public void setDimDpt(String dimDpt)
    {
        this.dimDpt = dimDpt;
    }

    public String getDes()
    {
        return des;
    }

    public void setDes(String des)
    {
        this.des = des;
    }

    public String getBlankLine() {
        return blankLine;
    }

    public void setBlankLine(String blankLine) {
        this.blankLine = blankLine;
    }


}
