package com.ambuj.controller;

import com.ambuj.domain.GsLookUpDetails;
import com.ambuj.service.GsSpaceQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class QueryController {
    @Autowired
    private GsSpaceQueryService gsSpaceQueryService;

    @RequestMapping(value = "query/queryboard", method = RequestMethod.GET)
    public String contactinfo(@ModelAttribute(value = "gsLookUpDetails") GsLookUpDetails gsLookUpDetails) {
        ModelAndView gsEnvironmentList = new ModelAndView("index");
        gsEnvironmentList.addObject("dataTypes", gsSpaceQueryService.getAllDataTypesForSpace(gsLookUpDetails));
        return "query/queryboard";
    }

    @RequestMapping(value = "query/queryboardfull", method = RequestMethod.GET)
    public String contactinfofull() {
        return "query/queryboard";
    }

    @ModelAttribute("gsLookUpDetails")
    public GsLookUpDetails gsLookUpDetails(@RequestParam(value = "envName") String envName) {
        GsLookUpDetails gsLookUpDetails = new GsLookUpDetails.GsLookUpDetailsBuilder().withEnvName(envName).build();
        return gsLookUpDetails;
    }

}
