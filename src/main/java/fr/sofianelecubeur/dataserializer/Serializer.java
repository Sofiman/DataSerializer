package fr.sofianelecubeur.dataserializer;

import java.beans.Encoder;
import java.io.Closeable;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by SofianeLeCubeur on 24/06/2017.
 *
 * @author SofianeLeCubeur
 *
 * Please, for using a new Serializer, use {@link fr.sofianelecubeur.dataserializer.FileSerializerBuilder}
 */
public abstract class Serializer implements Closeable {
	
	private static Map<UUID, Serializer> map = new HashMap<>();
	
	protected final CompilationType type;
	protected final UUID identifier;

	public Serializer(CompilationType type, UUID identifier) {
		this.type = type;
		this.identifier = identifier;
		map.put(this.identifier, this);
	}
	
	public final CompilationType getCompilationType(){
		return this.type;
	}
	
	public final UUID getIdentifier() {
		return this.identifier;
	}
	
	public void close() throws IOException {
		map.remove(this.identifier);
	}
	
	@Override
	public String toString() {
		return "Serializer[id:"+identifier+",type:"+type+"]";
	}
	
	public static Serializer find(String identifier){
		return find(UUID.fromString(identifier));
	}
	
	public static Serializer find(UUID identifier){
		return map.get(identifier);
	}
	
}
