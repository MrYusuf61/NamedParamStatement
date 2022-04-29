package com.MrYusuf.NamedParamStatementExample.Example;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

public class SqlMapper {
    public static int Execute(Connection cnn, String sql){
        return Execute(cnn, sql, null);
    }

    public static int Execute(Connection cnn, String sql ,Object param){
        try{
            NamedParamStatement nps = new NamedParamStatement(cnn,sql);
            if (param != null){
                for (Field field: param.getClass().getFields()) {
                    nps.setObject(field.getName(),field.get(param));
                }
            }

            int result = nps.executeUpdate();

            nps.close();
            return result;
        } catch (Exception e){
            System.out.println(e);
        }
        return 0;
    }

    public static <T> List<T> Query(Class<T> type, Connection cnn, String sql) {
        return SqlMapper.Query(type,cnn,sql,null);
    }

    public static <T> List<T> Query(Class<T> type,Connection cnn, String sql, Object param) {
        try {
            List<T> OutList = new ArrayList<>();
            NamedParamStatement nps = new NamedParamStatement(cnn,sql);
            if (param != null){
                for (Field field: param.getClass().getFields()) {
                    nps.setObject(field.getName(),field.get(param));
                }
            }

            ResultSet result = nps.executeQuery();

            ResultSetMetaData metaData = result.getMetaData();
            for (int i = 1; i <= metaData.getColumnCount(); i++)
                Logger.Log(metaData.getColumnName(i));

            while (result.next()) {
                T listItem = type.newInstance();
                for (int i = 1; i <= metaData.getColumnCount(); i++) {
                    try {
                        String columnName = metaData.getColumnName(i);
                        Field field = type.getField(columnName);
                        if(field != null){
                            field.set(listItem,result.getObject(columnName));
                        }
                    } catch (Exception e){
                        System.out.println(e);
                    }
                }
                OutList.add(listItem);
            }
            nps.close();
            return OutList;
        } catch (Exception e){
            System.out.println(e);
        }
        return null;
    }

}
