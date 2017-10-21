package fr.sofianelecubeur.dataserializer;

import java.io.File;
import java.util.UUID;

/**
 * Created by SofianeLeCubeur on 24/06/2017.
 *
 * @author SofianeLeCubeur
 */
public class FileDeserializerBuilder {
	
	private CompilationType type;
	private File file;
	private UUID identifier;

	public FileDeserializerBuilder() {
		this.type = CompilationType.BASE64;
		this.file = null;
		this.identifier = UUID.randomUUID();
	}
	
	public FileDeserializerBuilder type(CompilationType type){
		this.type = type;
		
		return this;
	}
	
	public FileDeserializerBuilder file(File file){
		this.file = file;
		
		return this;
	}
	
	public FileDeserializerBuilder identifier(UUID identifier){
		this.identifier = identifier;
		
		return this;
	}
	
	public Deserializer get(){
		try {
			switch (this.type) {
				case JSON:
					return new JsonFileDeserializer(this.file, this.identifier);
				case BINARY:
					return new BinaryFileDeserializer(this.file, this.identifier);
				case BASE64:
					return new Base64FileDeserializer(this.file, this.identifier);
				case CVS:
					return new CSVFileDeserializer(this.file, this.identifier);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}