package fr.sofianelecubeur.dataserializer;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.util.UUID;

public class TestSerializer {

	public static void main(String[] args) throws IOException {
		System.out.println("Starting writing data...");
		Base64FileSerializer serializer = (Base64FileSerializer) new FileSerializerBuilder().type(CompilationType.BASE64).get();
		serializer.writeInt(192);
		serializer.writeUTF("test");
		serializer.writeFloat(42.5f);
		serializer.writeObject(InetAddress.getLocalHost());
		long time = serializer.compile(new File("data"));
		serializer.close();
		System.out.println("Finished in "+(time / 1000)+"s");
		System.out.println(serializer);

		System.out.println("-----------------------");
		System.out.println("Starting writing data...");
		JsonFileSerializer serializer1 = (JsonFileSerializer) new FileSerializerBuilder().type(CompilationType.JSON).get();
		serializer1.writeObject("int", 192);
		serializer1.writeObject("str", "test");
		serializer1.writeObject("flt", 42.5f);
		serializer1.writeObject("ipt", InetAddress.getLocalHost());
		long time1 = serializer1.compile(new File("data1"));
		serializer1.close();
		System.out.println("Finished in "+(time1 / 1000)+"s");

		System.out.println(HashUtils.sha1("Test"));

		CSVFileSerializer serializer2 = new CSVFileSerializer(UUID.randomUUID());
		serializer2.setDelemiter(";");
		serializer2.addColumns("id", "value");
		serializer2.setValue("id", "1");
		serializer2.setValue("value", "123");
		serializer2.addLine();
		serializer2.setValue("id", "2");
		serializer2.setValue("value", "456");
		serializer2.compile(new File("file.csv"));
	}

}
