package com.plennegy.models;

public class ConnectionsModel {
    private String userName;
    private String passWord;
    private String environment;
    private String connectionString;

    public ConnectionsModel()
    {
    }

    public ConnectionsModel(String userName, String passWord, String environment, String connectionString)
    {
        this.userName = userName;
        this.passWord = passWord;
        this.environment = environment;
        this.connectionString = connectionString;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public String getPassWord()
    {
        return passWord;
    }

    public void setPassWord(String passWord)
    {
        this.passWord = passWord;
    }

    public String getEnvironment()
    {
        return environment;
    }

    public void setEnvironment(String environment)
    {
        this.environment = environment;
    }

    public String getConnectionString()
    {
        return connectionString;
    }

    public void setConnectionString(String connectionString)
    {
        this.connectionString = connectionString;
    }

    @Override
    public String toString()
    {
        return "{\"ConnectionsModel\":{"
                + "                        \"userName\":\"" + userName + "\""
                + ",                         \"passWord\":\"" + passWord + "\""
                + ",                         \"environment\":\"" + environment + "\""
                + ",                         \"connectionString\":\"" + connectionString + "\""
                + "}" +
                "}";
    }


}
