package fr.sofianelecubeur.dataserializer;

import fr.sofianelecubeur.dataserializer.CompilationType;
import fr.sofianelecubeur.dataserializer.Serializer;
import fr.sofianelecubeur.dataserializer.TextSerializer;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

/**
 * Created by Sofiane on 26/07/2017.
 *
 * @author Sofiane
 */
public class Sha1TextSerializer extends Serializer implements TextSerializer<String, byte[]> {

    public Sha1TextSerializer(UUID identifier) {
        super(CompilationType.SHA1, identifier);
    }

    public Sha1TextSerializer() {
        this(UUID.randomUUID());
    }

    @Override
    public String compile(byte[] data) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] b = md.digest(data);
            String result = "";
            for (int i = 0; i < b.length; i++) {
                result += Integer.toString((b[i] & 0xff) + 0x100,16).substring(1);
            }
            return result.toUpperCase();
        } catch (NoSuchAlgorithmException e) {}
        return null;
    }

    public String compile(String s, String encoding) {
        return this.compile(s.getBytes(Charset.forName(encoding)));
    }

    public String compile(String s) {
        return this.compile(s, "UTF-8");
    }
}
