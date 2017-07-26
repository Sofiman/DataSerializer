package fr.sofianelecubeur.dataserializer;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.UUID;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Created by SofianeLeCubeur on 24/06/2017.
 *
 * @author SofianeLeCubeur
 */
@SuppressWarnings("unchecked")
public class JsonFileSerializer extends Serializer implements FileSerializer {
	
	private JSONObject json;
	private final int byteArrayLength;
	
	public JsonFileSerializer(int byteArrayLength, UUID identifier) {
		super(CompilationType.JSON, identifier);
		this.json = new JSONObject();
		this.byteArrayLength = byteArrayLength;
	}
	
	@Deprecated
	@Override
	public void write(byte[] o) throws IOException {
		throw new UnsupportedOperationException();
	}

	@Deprecated
	@Override
	public void writeInt(Integer o) throws IOException {
		throw new UnsupportedOperationException();
	}

	@Deprecated
	@Override
	public void writeShort(Short o) throws IOException {
		throw new UnsupportedOperationException();
	}

	@Deprecated
	@Override
	public void writeLong(Long o) throws IOException {
		throw new UnsupportedOperationException();
	}

	@Deprecated
	@Override
	public void writeDouble(Double o) throws IOException {
		throw new UnsupportedOperationException();
	}

	@Deprecated
	@Override
	public void writeFloat(Float o) throws IOException {
		throw new UnsupportedOperationException();
	}

	@Deprecated
	@Override
	public void writeBoolean(Boolean o) throws IOException {
		throw new UnsupportedOperationException();
	}

	@Deprecated
	@Override
	public void writeUTF(String o) throws IOException {
		throw new UnsupportedOperationException();
	}

	@Deprecated
	@Override
	public void writeObject(Object o) throws IOException {
		throw new UnsupportedOperationException();
	}
	
	public void writeObject(String key, Object value){
		this.json.put(key, value.toString());
	}
	
	public void writeArray(String key, Object[] value){
		if (!json.containsKey(key)) {
			json.put(key, new JSONArray());
        }
        JSONArray array = (JSONArray) json.get(key);
        for (int i = 0; i < value.length; i++)
			array.add(value[i]);
        json.put(key, array);
	}
	
	public void writeArray(String key, Collection<?> value){
		if (!json.containsKey(key)) {
			json.put(key, new JSONArray());
        }
        JSONArray array = (JSONArray) json.get(key);
        array.addAll(value);
        json.put(key, array);
	}
	
	@Override
	public long compile(File file) throws IOException {
		long sT = System.currentTimeMillis();
		
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		DataOutputStream outStream = new DataOutputStream(bytes);
		outStream.writeUTF(this.json.toJSONString());
		
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
		json.clear();
		super.close();
	}

}
