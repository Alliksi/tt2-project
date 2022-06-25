package com.storage.logger.database;


public class DatabaseLogger {

    public DatabaseLogger(){
    }

    public static void logToDatabase(String message){
        System.out.println(message);         // TODO : log to database -- TIME, CONTROLLER NAME, MESSAGE, STATUS, ETC.
    }
}
