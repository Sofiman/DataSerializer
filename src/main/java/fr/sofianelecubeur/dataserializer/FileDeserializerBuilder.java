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
	private CSVFileSerializer.ColumnType columnType;
	private String delimiter;
	private UUID identifier;

	public FileDeserializerBuilder() {
		this.type = CompilationType.BASE64;
		this.file = null;
		this.columnType = CSVFileSerializer.ColumnType.VERTICAL;
		this.identifier = UUID.randomUUID();
	}

	/**
	 * Set the deserializer type
	 * @param type the deserializer type
	 * @return this
	 */
	public FileDeserializerBuilder type(CompilationType type){
		this.type = type;
		
		return this;
	}

	/**
	 * Set the target file
	 * @param file the target file
	 * @return this
	 */
	public FileDeserializerBuilder file(File file){
		this.file = file;
		
		return this;
	}

	/**
	 * Set a custom identifier to the deserializer
	 * @param identifier the custom identifier
	 * @return this
	 */
	public FileDeserializerBuilder identifier(UUID identifier){
		this.identifier = identifier;

		return this;
	}

	/**
	 * Set the delimiter (Only used for CSV deserializer)
	 * @param delimiter the delimiter
	 * @return this
	 */
	public FileDeserializerBuilder delemiter(String delimiter){
		this.delimiter = delimiter;

		return this;
	}

	/**
	 * Set the column Type (Only used for CSV deserializer)
	 * @param columnType the column type
	 * @return this
	 */
	public FileDeserializerBuilder type2(CSVFileSerializer.ColumnType columnType){
		this.columnType = columnType;

		return this;
	}

	/**
	 * Build the Deserializer
	 * @return Deserializer
	 */
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
					return (delimiter != null ? new CSVFileDeserializer(this.file, columnType, delimiter, this.identifier)
							: new CSVFileDeserializer(this.file, this.identifier));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}