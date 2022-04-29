package com.MrYusuf.NamedParamStatementExample;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;


public class NamedParamStatement {
    public NamedParamStatement(Connection conn, String sql) throws SQLException {
        int pos;
        // prefix @
        while((pos = sql.indexOf("@")) != -1) {
            int end = findParamEnd(sql.substring(pos));
            if (end == -1)
                end = sql.length();
            else
                end += pos;
            fields.add(sql.substring(pos+1,end));
            sql = sql.substring(0, pos) + "?" + sql.substring(end);
        }
        prepStmt = conn.prepareStatement(sql);
    }

    private int findParamEnd(String text){
        char[] charArray = text.toCharArray();
        for (int i = 1; i < charArray.length; i++)
            if(Pattern.matches("[a-z|A-z|0-9]",charArray[i] + "")) continue;
            else return i;
        return -1;
    }
    public PreparedStatement getPreparedStatement() {
        return prepStmt;
    }
    public ResultSet executeQuery() throws SQLException {
        return prepStmt.executeQuery();
    }
    public int executeUpdate() throws SQLException{
        return prepStmt.executeUpdate();
    }
    public void close() throws SQLException {
        prepStmt.close();
    }

    public void setObject(String name,Object value) throws SQLException {
        for (int index : getIndexes(name)){
            // parameter index starts from 1
            prepStmt.setObject(index + 1,value);
        }
    }

    private List<Integer> getIndexes(String name) {
        List<Integer> indexes = new ArrayList<Integer>();
        for (int i = 0; i < fields.size(); i++) {
            if(fields.get(i).equals(name))
                indexes.add(i);
        }
        return indexes;
    }
    private PreparedStatement prepStmt;
    private List<String> fields = new ArrayList<String>();
}
