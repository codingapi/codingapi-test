package com.codingapi.test.xml;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.apache.commons.beanutils.BeanUtils;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;
import java.util.Map;

public class XmlUtils {

    public static <T> String create(XmlInfo xmlInfo) throws JsonProcessingException {
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);
        xmlMapper.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>() {
            @Override
            public void serialize(Object val, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
                jsonGenerator.writeString("");
            }
        });
        String res = xmlMapper.writeValueAsString(xmlInfo);
        return res;
    }


    public static <T> XmlInfo<T>  parser (String xml) throws IOException, InvocationTargetException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        XmlMapper xmlMapper = new XmlMapper();
        XmlInfo<LinkedHashMap<String,Object>> res = xmlMapper.readValue(xml,new TypeReference<XmlInfo<LinkedHashMap<String,Object>>>(){});
        return parser(res, (Class<T>) Class.forName(res.getClassName()));
    }

    private static <T> XmlInfo<T> parser(XmlInfo<LinkedHashMap<String,Object>> res, Class<T> clazz) throws  InvocationTargetException, IllegalAccessException, InstantiationException {
        XmlInfo<T> xmlInfo = new XmlInfo<>();
        BeanUtils.copyProperty(xmlInfo,"initCmd",res.getInitCmd());
        BeanUtils.copyProperty(xmlInfo,"dbType",res.getDbType());
        BeanUtils.copyProperty(xmlInfo,"path",res.getPath());
        BeanUtils.copyProperty(xmlInfo,"name",res.getName());
        BeanUtils.copyProperty(xmlInfo,"className",res.getClassName());
        BeanUtils.copyProperty(xmlInfo,"clearCmd",res.getClearCmd());

        for (Map<String,Object> map:res.getList()){
            T t = clazz.newInstance();
            BeanUtils.populate(t,map);
            xmlInfo.getList().add(t);
        }
        return xmlInfo;
    }

}
