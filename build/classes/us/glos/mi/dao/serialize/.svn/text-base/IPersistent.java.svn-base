package us.glos.mi.dao.serialize;

import java.util.ArrayList;

public interface IPersistent<T> {
	boolean writeTo(T key,Object data);
	byte[] read(T key);
	ArrayList<byte[]> readMore(T key);
	boolean remove(T key);
}
