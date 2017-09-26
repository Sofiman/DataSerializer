package fr.sofianelecubeur.dataserializer;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.UUID;

import fr.sofianelecubeur.dataserializer.base64.Base64Coder;

/**
 * Created by SofianeLeCubeur on 24/06/2017.
 *
 * @author SofianeLeCubeur
 */
public class Base64FileDeserializer extends Deserializer {

	private File file;

	public Base64FileDeserializer(File file, UUID identifier) throws IOException {
		this(new FileInputStream(file), identifier);
		this.file = file;
	}
	
	public Base64FileDeserializer(FileInputStream in, UUID identifier) throws IOException {
		super(CompilationType.BINARY, createBase64Stream(in), identifier);
	}

	public Base64FileDeserializer(FileInputStream in, boolean compression, UUID identifier) throws IOException {
		super(CompilationType.BINARY, (compression ? createBase64StreamWithCompression(in) : createBase64Stream(in)), identifier);
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
	
	protected static DataInputStream createBase64Stream(FileInputStream in) throws IOException {
		DataInputStream bytes = new DataInputStream(in);
		
		byte[] bf = Base64Coder.decodeLines(bytes.readUTF());
		ByteArrayInputStream stream = new ByteArrayInputStream(bf);
		
		in.close();
		bytes.close();
		return new DataInputStream(stream);
	}

	protected static DataInputStream createBase64StreamWithCompression(FileInputStream in) throws IOException {
		DataInputStream bytes = new DataInputStream(in);

		byte[] bf = Base64Coder.decodeLines(bytes.readUTF());
		byte[] data = CompressionUtils.decompress(bf);
		ByteArrayInputStream stream = new ByteArrayInputStream(data);

		in.close();
		bytes.close();
		return new DataInputStream(stream);
	}
		
}
