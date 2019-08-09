package com.codingapi.test.runner;

import com.codingapi.test.annotation.CheckMysqlData;
import com.codingapi.test.annotation.Expected;
import com.codingapi.test.annotation.TestMethod;
import com.codingapi.test.utils.SqlUtils;
import com.codingapi.test.xml.XmlInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.springframework.context.ApplicationContext;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @author lorne 2019/8/7
 */
@Slf4j
public class DefaultRelationalDbRunner implements IRelationalDbRunner {


    @Override
    public void prepare(ApplicationContext applicationContext, XmlInfo xmlInfo) throws SQLException {
        DataSource dataSource = applicationContext.getBean(DataSource.class);
        QueryRunner queryRunner = new QueryRunner();
        for(Object object : xmlInfo.getList()) {
            SqlUtils.SqlParam sqlParam = SqlUtils.parser(xmlInfo.getInitCmd(),object);
            Object rows = queryRunner.insert(dataSource.getConnection(), sqlParam.getSql(),new ScalarHandler<>(),sqlParam.getParams());
            log.info("relational ->{},rows:{}",sqlParam.getSql(),rows);
        }
    }

    @Override
    public void clear(ApplicationContext applicationContext,TestMethod testMethod) throws SQLException {
        String[] tableNames =  testMethod.clearTableNames();
        if(tableNames!=null) {
            DataSource dataSource = applicationContext.getBean(DataSource.class);
            QueryRunner queryRunner = new QueryRunner();
            for (String tableName : tableNames) {
                String sql = SqlUtils.createClearSql(tableName);
                queryRunner.execute(dataSource.getConnection(), sql);
                log.info("relational truncate sql ->{}", sql);
            }
        }
    }

    @Override
    public void check(ApplicationContext applicationContext, TestMethod testMethod) throws Exception{
        for(CheckMysqlData checkMysqlData :testMethod.checkMysqlData()){
            String sql = checkMysqlData.sql();
            DataSource dataSource = applicationContext.getBean(DataSource.class);
            QueryRunner queryRunner = new QueryRunner();
            List<Map<String,Object>> res =  queryRunner.query(dataSource.getConnection(),sql,new MapListHandler());
            log.info("relational - check=> sql:{},res:{}",sql,res);
            Expected expecteds []= checkMysqlData.expected();
            checkVal(res,expecteds,checkMysqlData.desc());
        }
    }
}
