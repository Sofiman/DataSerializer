package fr.sofianelecubeur.dataserializer;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.UUID;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Created by SofianeLeCubeur on 24/06/2017.
 *
 * @author SofianeLeCubeur
 *
 *  lease use internal read methods with keys !
 */
public class JsonFileDeserializer extends Deserializer {
	
	private JSONObject json;
	
	public JsonFileDeserializer(File file, UUID identifier) throws ParseException, IOException {
		this(new FileInputStream(file), identifier);
	}
	
	public JsonFileDeserializer(FileInputStream in, UUID identifier) throws ParseException, IOException {
		super(in, identifier);
		this.createJSONObject(in);
	}
	
	private void createJSONObject(FileInputStream in) throws ParseException, IOException {
		DataInputStream stream = new DataInputStream(in);
		JSONParser parser = new JSONParser();
		this.json = (JSONObject) parser.parse(stream.readUTF());
		stream.close();
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
	
	public Integer readInt(String key){
		return Integer.valueOf((String) this.json.get(key));
	}
	
	public Short readShort(String key){
		return Short.valueOf((String) this.json.get(key));
	}
	
	public Long readLong(Long key){
		return Long.valueOf((String) this.json.get(key));
	}
	
	public Float readFloat(String key){
		return Float.valueOf((String) this.json.get(key));
	}
	
	public Double readDouble(String key){
		return Double.valueOf((String) this.json.get(key));
	}
	
	public Boolean readBoolean(String key){
		return Boolean.valueOf((String) this.json.get(key));
	}
	
	public String readUTF(String key){
		return (String) this.json.get(key);
	}
	
	public Object readObject(String key){
		return (String) this.json.get(key);
	}
	
}
