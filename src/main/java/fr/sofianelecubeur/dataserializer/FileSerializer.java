package fr.sofianelecubeur.dataserializer;

import java.io.File;
import java.io.IOException;

/**
 * Created by SofianeLeCubeur on 24/06/2017.
 *
 * @author SofianeLeCubeur
 */
public interface FileSerializer {
	
	void write(byte[] o) throws IOException;
	void writeInt(Integer o) throws IOException;
	void writeShort(Short o) throws IOException;
	void writeLong(Long o) throws IOException;
	void writeDouble(Double o) throws IOException;
	void writeFloat(Float o) throws IOException;
	void writeBoolean(Boolean o) throws IOException;
	void writeUTF(String o) throws IOException;
	void writeObject(Object o) throws IOException;
	
	long compile(File file) throws IOException;
	
}
