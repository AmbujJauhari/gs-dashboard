package com.ambuj.service;

import com.gigaspaces.document.SpaceDocument;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.j_spaces.core.client.SQLQuery;
import org.junit.Test;
import org.openspaces.core.GigaSpace;
import org.openspaces.core.GigaSpaceConfigurer;
import org.openspaces.core.space.UrlSpaceConfigurer;
import org.springframework.integration.support.MutableMessage;
import org.springframework.integration.transformer.ObjectToMapTransformer;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by Aj on 02-06-2016.
 */
public class SpaceDomainTypesTest {


    @Test
    public void testControllerForTypes() throws Exception {
        UrlSpaceConfigurer urlSpaceConfigurer = new UrlSpaceConfigurer("jini://*/*/processorSpace");
        GigaSpace gigaSpace = new GigaSpaceConfigurer(urlSpaceConfigurer).gigaSpace();

        String dataType = "com.ambuj.service.testdomain.Person";
        String criteria = "";
        SQLQuery<SpaceDocument> documentSQLQuery = new SQLQuery<SpaceDocument>(dataType, criteria);

        Object[] documents = gigaSpace.readMultiple(documentSQLQuery);

        System.out.println();
        for (Object spaceDocument : documents) {
            Field[] flds = spaceDocument.getClass().getDeclaredFields();
            List<Method> aClass = getAllGetters(spaceDocument.getClass());
            ObjectToMapTransformer objectToMapTransformer = new ObjectToMapTransformer();
//

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            System.out.println(gson.toJson(spaceDocument));
//
//            Class aClass=        gigaSpace.getClass().getClassLoader().loadClass(dataType);
            Map<String, Object> props = (Map<String, Object>) objectToMapTransformer.doTransform(new MutableMessage<Object>(spaceDocument));
//            GigaSpaceTypeManager gigaSpaceTypeManager = gigaSpace.getTypeManager();
//            SpaceTypeDescriptor spaceTypeDescriptor = gigaSpaceTypeManager.getTypeDescriptor(dataType);
//
//            boolean isConcrete = spaceTypeDescriptor.isConcreteType();
//
//            for (int i = 0; i < spaceTypeDescriptor.getNumOfFixedProperties(); i++) {
//                List<Method> mths = getAllGetters(spaceTypeDescriptor.getFixedProperty(i).getType());
//
//                System.out.println("\n");
//            }
            System.out.println(props);

        }
    }

    public static List<Method> getAllGetters(Class<?> type) {
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(type, Object.class);
            return Arrays.stream(beanInfo.getPropertyDescriptors())
                    .map(PropertyDescriptor::getReadMethod)
                    .filter(Objects::nonNull) // get rid of write-only properties
                    .collect(Collectors.toList());
        } catch (IntrospectionException e) {
            throw new IllegalStateException(e);
        }
    }

    public void printMap(Map<String, String> map) {
        for (String key : map.keySet()) {

        }
    }
}
