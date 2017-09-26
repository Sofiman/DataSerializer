package fr.sofianelecubeur.dataserializer;

import java.util.UUID;

/**
 * Created by Sofiane on 26/09/2017.
 *
 * @author Sofiane
 */
public class HashUtils {

    private static final HashTextSerializer hasher = new HashTextSerializer(UUID.randomUUID(), HashTextSerializer.Hash.SHA1);

    public static String sha1(byte[] array){
       hasher.set(HashTextSerializer.Hash.SHA1);
       return hasher.compile(array);
    }

    public static String sha1(String s){
        hasher.set(HashTextSerializer.Hash.SHA1);
        return hasher.compile(s);
    }

    public static String sha256(byte[] array){
        hasher.set(HashTextSerializer.Hash.SHA256);
        return hasher.compile(array);
    }

    public static String sha256(String s){
        hasher.set(HashTextSerializer.Hash.SHA256);
        return hasher.compile(s);
    }

    public static String md5(byte[] array){
        hasher.set(HashTextSerializer.Hash.MD5);
        return hasher.compile(array);
    }

    public static String md5(String s){
        hasher.set(HashTextSerializer.Hash.MD5);
        return hasher.compile(s);
    }

}
