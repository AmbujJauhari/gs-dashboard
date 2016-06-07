package com.ambuj.service;


import com.ambuj.domain.GsLookUpDetails;
import com.j_spaces.core.IJSpace;
import com.j_spaces.core.admin.IRemoteJSpaceAdmin;
import com.j_spaces.core.client.SQLQuery;
import org.openspaces.core.GigaSpace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

@Component
public class GsSpaceQueryService {
    @Autowired
    private SpaceDiscoveryService spaceDiscoveryService;

    public List<String> getAllDataTypesForSpace(GsLookUpDetails gsLookUpDetails) {
        List<String> typesInSpace = new ArrayList<>();
        try {
            GigaSpace gigaSpace = spaceDiscoveryService.getSpace(gsLookUpDetails.getEnvName());
            IJSpace ijSpace = gigaSpace.getSpace();
            IRemoteJSpaceAdmin spaceAdmin = (IRemoteJSpaceAdmin) ijSpace.getAdmin();
            typesInSpace = spaceAdmin.getRuntimeInfo().m_ClassNames;
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return typesInSpace;
    }

    public Object[] getDataFromSpaceForType(String envName, String dataType, String criteria) {
        GigaSpace gigaSpace = spaceDiscoveryService.getSpace(envName);
        SQLQuery<Object> documentSQLQuery = new SQLQuery<>(dataType, criteria);
        return gigaSpace.readMultiple(documentSQLQuery);
    }
}
