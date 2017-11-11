package fr.sofianelecubeur.dataserializer;

import java.io.*;
import java.nio.file.Files;
import java.util.Properties;

/**
 * Created by Sofiane on 26/09/2017.
 *
 * @author Sofiane
 */
public class FileInfo {

    private File file;
    private Properties properties;

    public FileInfo(File file) {
        this.file = file;
        this.properties = new Properties();
        try {
            this.properties.load(new FileInputStream(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void put(String propertyName, String value){
        this.properties.setProperty(propertyName, value);
    }

    public Properties getProperties() {
        return this.properties;
    }

    public void create() throws IOException {
        this.file.createNewFile();
    }

    public byte[] readAllBytes() throws IOException {
        return Files.readAllBytes(file.toPath());
    }

    public void save(String comments) {
        try {
            FileOutputStream out = new FileOutputStream(file);
            this.properties.store(out, comments);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void rename(File newFile){
        this.file.renameTo(newFile);
    }

    public void rename(String newName){
        String[] array = this.file.getName().split(".");
        this.rename(new File(newName + (array[1] != null ? array[1] : "")));
    }

    public void delete(){
        this.file.delete();
    }

    public File getFile() {
        return file;
    }

    @Override
    public int hashCode() {
        int result = file != null ? file.hashCode() : 0;
        result = 31 * result + (properties != null ? properties.hashCode() : 0);
        return result;
    }
}
