package org.example;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main (String[] args) {
        String csvFile="src/main/java/Data/data.csv";
        String templateFile="src/main/java/Plantillas/plantilla01";

        Combinator.combine(csvFile,templateFile);
    }
}