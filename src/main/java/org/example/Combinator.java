package org.example;

import lombok.Builder;
import lombok.Data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.io.FileUtils;

@Data @Builder
public class Combinator {


    public static void combine(String csvFile, String templateFile){
        String parrafo;

        String textoTemplate ="";

       try(BufferedReader br2 = new BufferedReader(new FileReader(templateFile))){
           String linea ="";
           while ((linea = br2.readLine()) != null){
               textoTemplate+=linea+"\n";
           }
           System.out.println(textoTemplate);

       } catch (FileNotFoundException e) {
           throw new RuntimeException(e);
       } catch (IOException e) {
           throw new RuntimeException(e);
       }

        try(BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
        FileUtils.cleanDirectory(new File("src/main/java/Salida"));
            String line;
            ArrayList<String>compilarLine =new ArrayList<>();
            int contador = 0;
            String txtTemp ="";
            while ((line =br.readLine()) != null){
                txtTemp = textoTemplate;
                compilarLine.addAll(List.of(line.split(",")));
                //Medida de seguridad para evitar archivos con datos faltantes
                if (compilarLine.size()!=5+contador){
                    int linea=contador/5+1;
                    int resto =compilarLine.size()%5;
                    for (int i = 0; i<resto;i++){
                        compilarLine.removeLast();
                    }
                    System.out.println("Los datos de la linea "+linea+"estan incompletos, no se creará ese archivo\n");
                }else {
                    String file_code = compilarLine.get(0+contador);
                    String file_enterpriseName = compilarLine.get(1+contador);
                    String file_ubication = compilarLine.get(2+contador);
                    String file_mail = compilarLine.get(3+contador);
                    String file_personName = compilarLine.get(4+contador);

                    try(BufferedWriter bw = new BufferedWriter(new FileWriter("src/main/java/Salida/template-"+file_code+".txt"))){
                        System.out.println(file_code);
                        txtTemp = txtTemp.replace("%%nombre%%",file_personName);
                        txtTemp = txtTemp.replace("%%empresa%%",file_enterpriseName);
                        txtTemp = txtTemp.replace("%%correo%%",file_mail);
                        txtTemp = txtTemp.replace("%%ubicacion%%",file_ubication);
                        bw.write(txtTemp);
                        bw.newLine();
                        contador+=5;

                    }
                }


            }
    }catch (IOException e){
        throw new RuntimeException(e);
    }
    }

}
