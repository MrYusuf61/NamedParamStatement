package com.MrYusuf.NamedParamStatementExample.Example;

import com.MrYusuf.NamedParamStatementExample.Example.SqlConnection;
import com.MrYusuf.NamedParamStatementExample.Example.Tables.Person;
import com.MrYusuf.NamedParamStatementExample.Example.SqlMapper;
import java.util.List;
import java.sql.Connection;

public class Main {
    public void getPerson(){
        try {
            Person person = new Person();
            person.name = "Yusuf";
            person.age = 17;

            List<Person> sqlList = SqlMapper.Query(Person.class,SqlConnection.getMsSqlConnection(),"SELECT * FROM [Person] where [name]=@name and [age]=@age",person);
        
            for (Person listItem : sqlList) {
                    System.out.println(listItem.name);
            }
        } catch (Exception e){
            System.out.println(e);
        }        
    }
    
    public void setPerson(){
        try {
            Person person = new Person();
            person.name = "Yusuf";
            person.age = 17;

            int result = SqlMapper.Execute(SqlConnection.getMsSqlConnection(),"UPDATE [Person] SET [name]=@name ,[age]=@age where [name]='Ysf' and [age]=@age",person);
        
        } catch (Exception e){
            System.out.println(e);
        }        
    }
}
