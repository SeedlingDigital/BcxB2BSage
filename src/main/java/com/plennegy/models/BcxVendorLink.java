package com.plennegy.models;

public class BcxVendorLink {
    private String gardenCenterCode;
    private int bpCodeSageX3;
    private String bcxCode;
    private String vendorCodeSamis;

    public BcxVendorLink() {
    }

    public BcxVendorLink(String gardenCenterCode, int bpCodeSageX3, String bcxCode, String vendorCodeSamis) {
        this.gardenCenterCode = gardenCenterCode;
        this.bpCodeSageX3 = bpCodeSageX3;
        this.bcxCode = bcxCode;
        this.vendorCodeSamis = vendorCodeSamis;
    }

    public String getGardenCenterCode() {
        return gardenCenterCode;
    }

    public void setGardenCenterCode(String gardenCenterCode) {
        this.gardenCenterCode = gardenCenterCode;
    }

    public int getBpCodeSageX3() {
        return bpCodeSageX3;
    }

    public void setBpCodeSageX3(int bpCodeSageX3) {
        this.bpCodeSageX3 = bpCodeSageX3;
    }

    public String getBcxCode() {
        return bcxCode;
    }

    public void setBcxCode(String bcxCode) {
        this.bcxCode = bcxCode;
    }

    public String getVendorCodeSamis() {
        return vendorCodeSamis;
    }

    public void setVendorCodeSamis(String vendorCodeSamis) {
        this.vendorCodeSamis = vendorCodeSamis;
    }
}
