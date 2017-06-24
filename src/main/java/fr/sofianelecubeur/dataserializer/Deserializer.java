package fr.sofianelecubeur.dataserializer;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by SofianeLeCubeur on 24/06/2017.
 *
 * @author SofianeLeCubeur
 *
 * Please, for using a new Deserializer, use {@link fr.sofianelecubeur.dataserializer.FileDeserializerBuilder}
 */
public abstract class Deserializer extends DataInputStream  {

	private static Map<UUID, Deserializer> map = new HashMap<>();
	
	protected final UUID identifier;
	
	public Deserializer(InputStream in, UUID identifier) {
		super(in);
		this.identifier = identifier;
		map.put(this.identifier, this);
	}
	
	@Override
	public final void close() throws IOException {
		map.remove(this.identifier);
		super.close();
	}
	
	@Override
	public String toString() {
		return "Deserializer[id:"+identifier+",in:"+in+"]";
	}
	
	public static Deserializer find(String identifier){
		return find(UUID.fromString(identifier));
	}
	
	public static Deserializer find(UUID identifier){
		return map.get(identifier);
	}
	
}
