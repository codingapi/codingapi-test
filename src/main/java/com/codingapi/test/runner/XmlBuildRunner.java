package com.codingapi.test.runner;

import com.codingapi.test.annotation.DBType;
import com.codingapi.test.annotation.XmlBuild;
import com.codingapi.test.config.TestConfig;
import com.codingapi.test.utils.SqlUtils;
import com.codingapi.test.xml.XmlInfo;
import com.codingapi.test.xml.XmlUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.atteo.classindex.ClassIndex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

/**
 * @author lorne
 * @date 2019/8/1
 * @description
 */
@Component
@ConditionalOnProperty(name = "test.coverXml",havingValue = "true")
@Slf4j
public class XmlBuildRunner implements CommandLineRunner {

    @Autowired
    private TestConfig testConfig;

    @Override
    public void run(String... args) throws IOException, IllegalAccessException, InstantiationException {
        String outPath = testConfig.getOutPath();
        Iterable<Class<?>> annotated = ClassIndex.getAnnotated(XmlBuild.class);
        Iterator<Class<?>> iterator = annotated.iterator();

        while (iterator.hasNext()){
            Class<?> clazz=  iterator.next();
            XmlBuild xmlBuild =  clazz.getAnnotation(XmlBuild.class);
            String filePath = xmlBuild.name()+".xml";
            XmlInfo xmlInfo = new XmlInfo();
            xmlInfo.setName(xmlBuild.name());
            xmlInfo.setClassName(clazz.getName());
            xmlInfo.setPath(filePath);
            xmlInfo.setDbType(xmlBuild.dbType());

            if(StringUtils.isEmpty(xmlBuild.initCmd())
                    &&xmlBuild.dbType().equals(DBType.Mysql)){
                String sql = SqlUtils.createInsertSql(xmlBuild.name(),clazz);
                xmlInfo.setInitCmd(sql);
            }else{
                xmlInfo.setInitCmd(xmlBuild.initCmd());
            }

            if(StringUtils.isEmpty(xmlBuild.clearCmd())
                    &&xmlBuild.dbType().equals(DBType.Mysql)){
                String sql = SqlUtils.createClearSql(xmlBuild.name());
                xmlInfo.setClearCmd(sql);
            }else{
                xmlInfo.setClearCmd(xmlBuild.clearCmd());
            }


            Object obj = clazz.newInstance();
            xmlInfo.getList().add(obj);
            File file = new File(outPath+"/"+filePath);
            String content = XmlUtils.create(xmlInfo);
            log.info("file:{},content->{}",file,content);
            FileUtils.writeStringToFile(file, content);
        }

    }
}
