package com.packer;

import com.packer.exception.ApiException;
import static com.packer.Packer.pack;

public class Main {

    public static void main(String args[]) {
        String filePath = "C:\\Babu\\rabo\\packer-demo\\inputs.txt";
        try {
            pack(filePath);
        } catch (ApiException ex) {
            ex.printStackTrace();
        }
    }
}
