package com.ambuj.controller;

import com.ambuj.domain.GsLookUpDetails;
import com.ambuj.domain.SpaceEntry;
import com.ambuj.domain.SpaceQueryRestRequest;
import com.ambuj.service.GsSpaceQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.transformer.ObjectToMapTransformer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class QueryController {
    @Autowired
    private GsSpaceQueryService gsSpaceQueryService;

    @RequestMapping(value = "query/queryboard", method = RequestMethod.GET)
    public String queryboard() {
        return "query/queryboard";
    }

    @RequestMapping(value = "query/getAllDocumentTypesForSpace", headers = "Accept=*/*")
    public
    @ResponseBody
    List<String> getAllDocumentTypesForSpace(@RequestParam String envName) {
        GsLookUpDetails gsLookUpDetails = new GsLookUpDetails.GsLookUpDetailsBuilder().withEnvName(envName).build();
        return gsSpaceQueryService.getAllDataTypesForSpace(gsLookUpDetails);
    }

    @RequestMapping(value = "query/getDataFromSpaceForType", headers = "Accept=*/*", method = RequestMethod.POST)
    public
    @ResponseBody
    SpaceEntry getDataFromSpaceForType(@RequestBody SpaceQueryRestRequest spaceQueryRestRequest) throws Exception {
        String documentName = spaceQueryRestRequest.getDataType();
        String criteria = spaceQueryRestRequest.getCriteria();
        String envName = spaceQueryRestRequest.getGridName();
        Object[] spaceDocuments = gsSpaceQueryService.getDataFromSpaceForType(envName, documentName, criteria);
        List<List<SpaceEntry>> listsOfSpaceEntries = new ArrayList<>();
        SpaceEntry spaceEntry = new SpaceEntry();
        for (int i = 0; i < spaceDocuments.length; i++) {
            Map<String, String> valuesMap = org.apache.commons.beanutils.BeanUtils.describe(spaceDocuments[i]);
            if (i == 0) {
                spaceEntry.setHeaderColumns(valuesMap.keySet());
            }
            spaceEntry.getTableData().add(
                    valuesMap);
        }
        System.out.println(spaceEntry);
        return spaceEntry;
    }

}
