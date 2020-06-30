/*
 * Zyonic Software - 2020 - Tobias Rempe
 * This File, its contents and by extention the corresponding project may be used freely in compliance with the Apache 2.0 License.
 *
 * tobiasrempe@zyonicsoftware.com
 */

package com.zyonicsoftware.maddox.core.mysql;

import com.zyonicsoftware.maddox.core.main.Maddox;
import de.daschi.core.MySQL;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MySQLHandler {

    private final Maddox maddox;
    private MySQL mySQL;

    public MySQLHandler(Maddox maddox) {
        this.maddox = maddox;
    }


    public MySQL connectToMysql(String hostname, int port, String database, String user, String password) {
        MySQL mySQL = new MySQL(hostname, port, user, password, database);
        MySQL.using(mySQL);
        this.mySQL = mySQL;

        return mySQL;
    }

    public String getServerLanguage(String guildID) {//ToDo
        return "EN";
    }

    public void addServerToDatabase(String serverID, String prefix, String language) {
        try {
            mySQL.executeQuery("INSERT INTO Server_Settings(id, prefix, language) VALUES (" + serverID + "," + prefix + "," + language + ";");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setLanguage(String language, String guildID) {
        try {
            mySQL.executeQuery("UPDATE Server_Settings SET language = "+ language + " WHERE ServerID = " + guildID + ";");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getLanguage(String guildID) {
        try {
            final ResultSet resultSet = mySQL.executeQuery("SELECT language FROM Server_Settings WHERE id = " + guildID + ";");
            while (resultSet.next()) {
                return resultSet.getString("language");
            }
        } catch (Exception e){ }
        return " ";
    }

    public String getPrefix(String guildID){
        try {
            final ResultSet resultSet = mySQL.executeQuery("SELECT language FROM Server_Settings WHERE id = " + guildID + ";");
            while (resultSet.next()) {
                return resultSet.getString("language");
            }
        } catch (Exception e){ }
        return " ";
    }

    public void setPrefix(String prefix, String guildID) {
        //ToDo
    }

}
