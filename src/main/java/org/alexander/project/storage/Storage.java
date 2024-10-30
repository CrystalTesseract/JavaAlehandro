package org.alexander.project.storage;


public class Storage {
    private static int id = 0;

    public static int nextId(){
        id++;
        return id-1;
    }
}
