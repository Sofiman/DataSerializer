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

    /**
     * Set the serializer type
     * @param type the serialier type
     * @return this
     */
	public FileSerializerBuilder type(CompilationType type){
		this.type = type;
		
		return this;
	}

	/**
	 * Use a custom buffer length
	 * @param length the buffer length
	 * @return this
	 */
	public FileSerializerBuilder length(int length){
		this.length = length;
		
		return this;
	}

	/**
	 * Set a custom identifier
	 * @param identifier the custom identifier
	 * @return this
	 */
	public FileSerializerBuilder identifier(UUID identifier){
		this.identifier = identifier;
		
		return this;
	}

    /**
     * Build the Deserializer
     * @return FileSerializer
     */
	public FileSerializer get(){
		try {
			switch (this.type) {
				case JSON:
					return new JsonFileSerializer(this.length, this.identifier);
				case BINARY:
					return new BinaryFileSerializer(this.length, this.identifier);
				case BASE64:
					return new Base64FileSerializer(this.length, this.identifier);
				case CVS:
					return new CSVFileSerializer(this.identifier);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
