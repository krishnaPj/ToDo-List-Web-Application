package com.todo.todolist;

import java.io.IOException;
import java.util.Properties;

public class ApplicationProperties {

    private static ApplicationProperties istance = null;
    Properties prop;

    private ApplicationProperties(){
        try {
            prop = new Properties();
            prop.load(this.getClass().getClassLoader().getResourceAsStream("bundle.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ApplicationProperties getInstance(){
        if(istance==null){
            istance = new ApplicationProperties();
        }
        return istance;
    }

    public String getUriServer() throws IOException {
        return (String) prop.get("uriServer");
    }

    public String getUriMySql() throws IOException {
        return (String) prop.get("uriMySql");
    }

    public String getUserMySql() throws IOException {
        return (String) prop.get("userMySql");
    }

    public String getPswMySql() throws IOException {
        return (String) prop.get("pswMySql");
    }

    public String getUserMail() throws IOException {
        return (String) prop.get("userMail");
    }

    public String getPswMail() throws IOException {
        return (String) prop.get("pswMail");
    }

}

