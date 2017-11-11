package fr.sofianelecubeur.dataserializer;

/**
 * Created by Sofiane on 25/06/2017.
 *
 * @author Sofiane
 */
public interface TextSerializer<T, K> {

    T compile(K str);
}
