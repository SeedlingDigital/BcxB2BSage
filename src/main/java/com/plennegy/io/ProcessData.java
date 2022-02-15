package com.plennegy.io;

import com.plennegy.models.GardenCenterModel;

import java.util.List;

public class ProcessData {

    public void invoiceTransactions(List<GardenCenterModel> gardenCenterModels)
    {

        System.out.println("ProcessData.invoiceTransactions");

        for (int d = 0; d < gardenCenterModels.size(); d++)
        {
            if (gardenCenterModels.get(d).getTransactionNo().trim().contains("TRAN"))
            {
                gardenCenterModels.remove(d);
            }

        }


        for (int i = 0; i < gardenCenterModels.size(); i++)
        {
            System.out.println(gardenCenterModels.get(i).getTransactionNo());
            System.out.println(gardenCenterModels.get(i).getBchCd());
            System.out.println(gardenCenterModels.get(i).getInvoiceType());
            System.out.println(gardenCenterModels.get(i).getBcxUniqueAccountNumber());
            System.out.println(gardenCenterModels.get(i).getTransactionDate());
            System.out.println(gardenCenterModels.get(i).getInvoiceRefNumber());
            System.out.println(gardenCenterModels.get(i).getReferenceCode());
            System.out.println(gardenCenterModels.get(i).getGrossAmount());
            System.out.println(gardenCenterModels.get(i).getDiscountAmount());
            System.out.println(gardenCenterModels.get(i).getVat());
            System.out.println(gardenCenterModels.get(i).getInvoiceTotal());
            System.out.println(gardenCenterModels.get(i).getVendorName());
            System.out.println(gardenCenterModels.get(i).getGmvRef());
            System.out.println(gardenCenterModels.get(i).getInvoiceDate());
            System.out.println(gardenCenterModels.get(i).getTrusted());
            System.out.println(gardenCenterModels.get(i).getAuto());

        }
    }//End of processTransactions


}
