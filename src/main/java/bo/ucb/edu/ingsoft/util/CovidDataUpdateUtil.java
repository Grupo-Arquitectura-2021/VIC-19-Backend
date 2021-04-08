package bo.ucb.edu.ingsoft.util;

import java.io.*;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CovidDataUpdateUtil {

    public void creatingEmptyFile(String filePath, String url){
        try {
            File file = new File(filePath);
            file.createNewFile();
            System.out.println("Se crea archivo csv vacio " + file.length());
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Se descarga el archivo
        URL urlObj = null;
        ReadableByteChannel rbcObj = null;
        FileOutputStream fOutStream  = null;

        // Se verifica si el archivo existe en la locacion especificada
        Path filePathObj = Paths.get(filePath);
        boolean fileExists = Files.exists(filePathObj);
        if(fileExists) {
            System.out.println("Archivo: "+filePathObj);
            try {
                urlObj = new URL(url);
                rbcObj = Channels.newChannel(urlObj.openStream());
                fOutStream = new FileOutputStream(filePath);

                fOutStream.getChannel().transferFrom(rbcObj, 0, Long.MAX_VALUE);
                //Where member variables are declared:
                System.out.println("El archivo se descargó correctamente");
            } catch (IOException ioExObj) {
                System.out.println("Error al descargar el archivo: " + ioExObj.getMessage());
            } finally {
                try {
                    if(fOutStream != null){
                        fOutStream.close();
                    }
                    if(rbcObj != null) {
                        rbcObj.close();
                    }
                } catch (IOException ioExObj) {
                    System.out.println("Error al cerrar el objeto: " + ioExObj.getMessage());
                }
            }
        } else {
            System.out.println("El archivo no se encuentra");
        }

        readingFileData(filePath);
    }

    public void readingFileData(String csvFile){
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        try {
            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {
                String[] datos = line.split(cvsSplitBy); // todos los datos

                int dataSize = datos.length;

                for (int i=0; i<dataSize; i++){

                    if(i==0){
                        System.out.println("");
                    }else {
                        System.out.print(" , ");
                    }
                    System.out.print(datos[i]); //cada dato
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
