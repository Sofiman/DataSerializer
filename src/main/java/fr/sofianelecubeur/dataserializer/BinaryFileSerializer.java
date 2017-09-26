package fr.sofianelecubeur.dataserializer;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.UUID;

/**
 * Created by SofianeLeCubeur on 24/06/2017.
 *
 * @author SofianeLeCubeur
 */
public class BinaryFileSerializer extends Serializer implements FileSerializer {

	private ByteArrayOutputStream bytes;
	private DataOutputStream outStream;
	private final int byteArrayLength;
	
	public BinaryFileSerializer(int byteArrayLength, UUID identifier) {
		super(CompilationType.BINARY, identifier);
		this.byteArrayLength = byteArrayLength;
		this.bytes = new ByteArrayOutputStream();
		this.outStream = new DataOutputStream(bytes);
	}
	
	@Override
	public void write(byte[] o) throws IOException {
		this.outStream.write(o);
	}
	
	@Override
	public void writeInt(Integer o) throws IOException {
		this.outStream.writeInt(o);
	}
	
	@Override
	public void writeShort(Short o) throws IOException {
		this.outStream.writeShort(o);
	}
	
	@Override
	public void writeLong(Long o) throws IOException {
		this.outStream.writeLong(o);
	}
	
	@Override
	public void writeDouble(Double o) throws IOException {
		this.outStream.writeDouble(o);
	}
	
	@Override
	public void writeFloat(Float o) throws IOException {
		this.outStream.writeFloat(o);
	}
	
	@Override
	public void writeBoolean(Boolean o) throws IOException {
		this.outStream.writeBoolean(o);
	}
	
	@Override
	public void writeUTF(String o) throws IOException {
		this.outStream.writeUTF(o);
	}
	
	@Override
	public void writeObject(Object o) throws IOException {
		ObjectOutputStream oos = new ObjectOutputStream(bytes);
		oos.writeObject(o);
		oos.close();
	}
	
	@Override
	public long compile(File file) throws IOException {
		long sT = System.currentTimeMillis();
		
		BufferedInputStream in = new BufferedInputStream(new ByteArrayInputStream(bytes.toByteArray()));
		BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file));
		byte[] bf = new byte[this.byteArrayLength];
		
		while(in.read(bf) != -1){
			out.write(bf);
		}
		
		out.close();
		in.close();
		
		return (System.currentTimeMillis() - sT);
	}
	
	@Override
	public void close() throws IOException {
		this.bytes.close();
		this.outStream.close();
		super.close();
	}

	public void compress(int level) throws IOException {
		byte[] old = bytes.toByteArray();
		byte[] compressed = CompressionUtils.compress(old, level);
		bytes = new ByteArrayOutputStream();
		bytes.write(compressed);
	}
	
	public ByteArrayOutputStream getBytes() {
		return bytes;
	}
	
	public DataOutputStream getStream() {
		return outStream;
	}

}
