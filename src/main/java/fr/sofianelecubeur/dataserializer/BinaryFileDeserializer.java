package fr.sofianelecubeur.dataserializer;

import fr.sofianelecubeur.dataserializer.base64.Base64Coder;

import java.io.*;
import java.util.UUID;

/**
 * Created by SofianeLeCubeur on 24/06/2017.
 *
 * @author SofianeLeCubeur
 */
public class BinaryFileDeserializer extends Deserializer {

	private File file;

	public BinaryFileDeserializer(File file, UUID identifier) throws FileNotFoundException {
		this(new FileInputStream(file), identifier);
		this.file = file;
	}
	
	public BinaryFileDeserializer(FileInputStream in, UUID identifier) throws FileNotFoundException {
		super(CompilationType.BINARY, in, identifier);
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

	public FileInfo info(){
		if(this.file == null) throw new NullPointerException();
		return new FileInfo(this.file);
	}
	
}
