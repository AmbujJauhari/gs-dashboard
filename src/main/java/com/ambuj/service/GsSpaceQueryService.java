package com.ambuj.service;


import com.ambuj.domain.DataHolderForType;
import com.ambuj.domain.GsLookUpDetails;
import com.gigaspaces.query.IdQuery;
import com.j_spaces.core.IJSpace;
import com.j_spaces.core.admin.IRemoteJSpaceAdmin;
import com.j_spaces.core.client.SQLQuery;
import org.openspaces.core.GigaSpace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.support.MutableMessage;
import org.springframework.integration.transformer.MapToObjectTransformer;
import org.springframework.integration.transformer.ObjectToMapTransformer;
import org.springframework.stereotype.Component;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class GsSpaceQueryService {
    @Autowired
    private SpaceDiscoveryService spaceDiscoveryService;

    private ObjectToMapTransformer objectToMapTransformer = new ObjectToMapTransformer();

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

    public Map<String, Object> getDataFromSpaceForTypeForSpaceId(String envName, String dataType, String spaceId) throws Exception {
        GigaSpace gigaSpace = spaceDiscoveryService.getSpace(envName);
        IdQuery<Object> objectIdQuery = new IdQuery<Object>(dataType, spaceId);
        Object queriedObject = gigaSpace.readById(objectIdQuery);
        return (Map<String, Object>) objectToMapTransformer.doTransform(new MutableMessage<Object>(queriedObject));
    }

    public void updataDataForSpaceTypeSpaceId(String envName, String dataType, String spaceIdName, DataHolderForType[] dataHolderForType) throws Exception {
        GigaSpace gigaSpace = spaceDiscoveryService.getSpace(envName);
        Object spaceId = null;

        Map<String, Object> stringObjectMap = new HashMap<>();
        for(DataHolderForType holderForType : dataHolderForType) {
            if(holderForType.getKey().equals(spaceIdName)) {
                spaceId=holderForType.getValue();
            }
            stringObjectMap.put(holderForType.getKey(), holderForType.getValue());
        }
        IdQuery<Object> objectIdQuery = new IdQuery<Object>(dataType, spaceId);
        Object obj = gigaSpace.takeById(objectIdQuery);
        MapToObjectTransformer mapToObjectTransformer = new MapToObjectTransformer(obj.getClass());
        Object o = mapToObjectTransformer.doTransform(new MutableMessage<Object>(stringObjectMap));
        gigaSpace.write(o);
        System.out.println(o);
    }

    public String getSpaceIdFieldNameForType(String envName, String dataType) {
        GigaSpace gigaSpace = spaceDiscoveryService.getSpace(envName);
        return gigaSpace.getTypeManager().getTypeDescriptor(dataType).getIdPropertyName();
    }
}
