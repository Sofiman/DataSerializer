package fr.sofianelecubeur.dataserializer;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by SofianeLeCubeur on 24/06/2017.
 *
 * @author SofianeLeCubeur
 */
public class SimpleFileSerializer extends Serializer implements FileSerializer {

	public SimpleFileSerializer(UUID identifier) {
		super(CompilationType.SIMPLE, identifier);
	}

	@Override
	public void write(byte[] o) throws IOException {
	}

	@Override
	public void writeInt(Integer o) throws IOException {
	}

	@Override
	public void writeShort(Short o) throws IOException {
	}

	@Override
	public void writeLong(Long o) throws IOException {
	}

	@Override
	public void writeDouble(Double o) throws IOException {
	}

	@Override
	public void writeFloat(Float o) throws IOException {
	}

	@Override
	public void writeBoolean(Boolean o) throws IOException {
	}

	@Override
	public void writeUTF(String o) throws IOException {
	}

	@Override
	public void writeObject(Object o) throws IOException {
	}

	@Override
	public long compile(File file) throws IOException {
		return 0;
	}
	
	@Override
	public void close() throws IOException {
		super.close();
	}

}
