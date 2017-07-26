package fr.sofianelecubeur.dataserializer;

import java.util.UUID;

/**
 * Created by SofianeLeCubeur on 24/06/2017.
 *
 * @author SofianeLeCubeur
 */
public class FileSerializerBuilder {
	
	private CompilationType type;
	private int length;
	private UUID identifier;

	public FileSerializerBuilder() {
		this.type = CompilationType.BASE64;
		this.length = 1024;
		this.identifier = UUID.randomUUID();
	}
	
	public FileSerializerBuilder type(CompilationType type){
		this.type = type;
		
		return this;
	}
	
	public FileSerializerBuilder length(int length){
		this.length = length;
		
		return this;
	}
	
	public FileSerializerBuilder identifier(UUID identifier){
		this.identifier = identifier;
		
		return this;
	}
	
	public FileSerializer get(){
		try {
			switch (this.type) {
				case JSON:
					return new JsonFileSerializer(this.length, this.identifier);
				case BINARY:
					return new BinaryFileSerializer(this.length, this.identifier);
				case BASE64:
					return new Base64FileSerializer(this.length, this.identifier);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
