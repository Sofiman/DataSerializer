package fr.sofianelecubeur.dataserializer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

/**
 * Created by Sofiane on 07/08/2017.
 *
 * @author Sofiane
 */
public class CompressionUtils {

   public static byte[] compress(byte[] data, int level){
       try {
           Deflater deflater = new Deflater(level);
           deflater.setInput(data);
           ByteArrayOutputStream out = new ByteArrayOutputStream(data.length);
           deflater.finish();
           byte[] buffer = new byte[1024];
           while (!deflater.finished()) {
               int length = deflater.deflate(buffer);
               out.write(buffer, 0, length);
           }
           out.close();
           return out.toByteArray();
       } catch (IOException e) {
           e.printStackTrace();
       }
       return data;
   }

   public static byte[] decompress(byte[] data){
       try {
           Inflater inflater = new Inflater();
           inflater.setInput(data);
           ByteArrayOutputStream out = new ByteArrayOutputStream(data.length);
           byte[] buffer = new byte[1024];
           while (!inflater.finished()) {
               int length = inflater.inflate(buffer);
               out.write(buffer, 0, length);
           }
           out.close();
           return out.toByteArray();
       }catch(Exception e){
           e.printStackTrace();
       }
       return data;
   }

}
