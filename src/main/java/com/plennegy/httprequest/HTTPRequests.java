package com.plennegy.httprequest;

import com.plennegy.models.BcxVendorLink;
import com.plennegy.models.ConnectionsModel;
import com.plennegy.utils.Environment;
import com.plennegy.utils.GlobalValues;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class HTTPRequests {
    private String baseUrl = "http://localhost:8082/sagex3/bcx/list/";// "http://api.plennegy.com/sagex3/bcx/list/";  //"/ROSE/476";
    private URI uri;
    private RestTemplate restTemplate = new RestTemplate();
    private BcxVendorLink bcxVendorLink = new BcxVendorLink();

    public ConnectionsModel getConnectionValues()
    {
        ConnectionsModel connectionsModel = new ConnectionsModel();
        if(GlobalValues.environment.equals(Environment.PRODENV.label))
        {
            //TODO make call to api with PROD
            //
            //     restTemplate.getForObject(uri, bcxVendorLink);
        }
        else
        {
            //TODO make call to api with TEST   GlobalValues.environment
        }

        return connectionsModel;

    }


    public List<BcxVendorLink> getLinkValue(String gardenCenter, String bcxCode) throws URISyntaxException {

        List<BcxVendorLink> bcxVendorLinkList = new ArrayList<>();

        try {

            String connectionString = baseUrl + gardenCenter + "/" + bcxCode;
            uri = new URI(connectionString);

            ResponseEntity<List<BcxVendorLink>> responseEntity = restTemplate.exchange(connectionString, HttpMethod.GET, null,
                    new ParameterizedTypeReference<List<BcxVendorLink>>() {});
            //result = restTemplate.getForEntity(uri, String.class);

            bcxVendorLinkList = responseEntity.getBody();


        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return bcxVendorLinkList;
    }
}
