package fr.sofianelecubeur.dataserializer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.UUID;

/**
 * Created by SofianeLeCubeur on 24/06/2017.
 *
 * @author SofianeLeCubeur
 */
public class BinaryFileDeserializer extends Deserializer {

	public BinaryFileDeserializer(File file, UUID identifier) throws FileNotFoundException {
		super(new FileInputStream(file), identifier);
	}
	
	public BinaryFileDeserializer(FileInputStream in, UUID identifier) throws FileNotFoundException {
		super(in, identifier);
	}
	
	@SuppressWarnings("unchecked")
	public <T> T readObject(Class<T> clazz) throws IOException, ClassNotFoundException {
		ObjectInputStream in = new ObjectInputStream(this.in);
		Object o = in.readObject();
		if(!clazz.isInstance(o))
			return null;
		return (T) o;
	}
	
	public Object readObject() throws IOException, ClassNotFoundException {
		ObjectInputStream in = new ObjectInputStream(this.in);
		return in.readObject();
	}
	
}
