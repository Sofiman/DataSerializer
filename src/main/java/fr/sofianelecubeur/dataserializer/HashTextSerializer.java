package fr.sofianelecubeur.dataserializer;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

/**
 * Created by Sofiane on 26/07/2017.
 *
 * @author Sofiane
 */
public class HashTextSerializer extends Serializer implements TextSerializer<String, byte[]> {

    private Hash hash;

    public HashTextSerializer(UUID identifier, Hash hash) {
        super(CompilationType.HASH, identifier);
        this.hash = hash;
    }

    public HashTextSerializer() {
        this(UUID.randomUUID(), Hash.SHA1);
    }

    @Override
    public String compile(byte[] data) {
        if(this.hash == null || data == null) throw new NullPointerException();
        try {
            MessageDigest md = MessageDigest.getInstance(hash.f);
            byte[] b = md.digest(data);
            String result = "";
            for (int i = 0; i < b.length; i++) {
                result += Integer.toString((b[i] & 0xff) + 0x100,16).substring(1);
            }
            return result.toUpperCase();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String compile(String s, String encoding) {
        return this.compile(s.getBytes(Charset.forName(encoding)));
    }

    public String compile(String s) {
        return this.compile(s, "UTF-8");
    }

    public boolean set(Hash hash) {
        if(this.hash == hash) return false;
        this.hash = hash;
        return true;
    }

    public Hash getHash() {
        return hash;
    }

    public enum Hash {
        SHA1("SHA-1"),
        SHA256("SHA-256"),
        MD5("MD5");

        private final String f;

        Hash(String f){
            this.f = f;
        }
    }
}
