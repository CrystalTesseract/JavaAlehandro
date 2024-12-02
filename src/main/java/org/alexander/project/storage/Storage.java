package org.alexander.project.storage;


import lombok.Getter;

public class Storage {
    @Getter
    private static int id = 0;

    public static int nextId(){
        id++;
        return id-1;
    }
}
