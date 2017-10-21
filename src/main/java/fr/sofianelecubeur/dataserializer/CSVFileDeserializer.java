package fr.sofianelecubeur.dataserializer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by Sofiane on 17/10/2017.
 *
 * @author Sofiane
 */
public class CSVFileDeserializer extends Deserializer {

    private File file;

    public CSVFileDeserializer(File file, UUID identifier) throws IOException {
        this(new FileInputStream(file), identifier);
        this.file = file;
    }

    public CSVFileDeserializer(FileInputStream in, UUID identifier) throws IOException {
        super(CompilationType.BINARY, in, identifier);
    }

}
