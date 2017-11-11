package fr.sofianelecubeur.dataserializer;

import java.io.File;
import java.io.IOException;
import java.util.StringJoiner;

public class TestDeserializer {

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		Base64FileDeserializer deserializer = (Base64FileDeserializer) new FileDeserializerBuilder().type(CompilationType.BASE64)
				.file(new File("data")).get();
		System.out.println(deserializer.readInt());
		System.out.println(deserializer.readUTF());
		System.out.println(deserializer.readFloat());
		System.out.println(deserializer.readObject());
		
		deserializer.close();
		System.out.println("------------------------");
		JsonFileDeserializer deserializer1 = (JsonFileDeserializer) new FileDeserializerBuilder().type(CompilationType.JSON)
				.file(new File("data1")).get();
		System.out.println(deserializer1.readInt("int"));
		System.out.println(deserializer1.readUTF("str"));
		System.out.println(deserializer1.readFloat("flt"));
		System.out.println(deserializer1.readObject("ipt"));
		
		deserializer1.close();

		System.out.println("------------------------");
		CSVFileDeserializer deserializer2 = (CSVFileDeserializer) new FileDeserializerBuilder().type(CompilationType.CVS)
                .file(new File("file.csv")).delemiter(";").type2(CSVFileSerializer.ColumnType.HORIZONTAL).get();
        System.out.println("Columns: "+deserializer2.getColumnsString());
        System.out.println("Rows: "+deserializer2.getRows());
        System.out.println("Values: "+deserializer2.getValuesString());


	}
	
}
