package com.ambuj.controller;

import com.ambuj.domain.GsLookUpDetails;
import com.ambuj.domain.SpaceQueryRestRequest;
import com.ambuj.service.GsSpaceQueryService;
import com.ambuj.util.SpaceDocumentConverterUtil;
import com.gigaspaces.document.SpaceDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(value = "query/getDataFromSpaceForType", headers = "Accept=*/*")
    public
    @ResponseBody
    List<Map<String, Object>> getDataFromSpaceForType(@RequestBody SpaceQueryRestRequest spaceQueryRestRequest) {
        String documentName = spaceQueryRestRequest.getDataType();
        String criteria = spaceQueryRestRequest.getCriteria();
        String envName = spaceQueryRestRequest.getGridName();
        SpaceDocument[] spaceDocuments = gsSpaceQueryService.getDataFromSpaceForType(envName, documentName, criteria);
        return SpaceDocumentConverterUtil.convertSpaceDocumentToMap(spaceDocuments);
    }

}
