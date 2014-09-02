package ninja.littleed.dndtest;

public interface DnDObjectHandler<T> {

	public void addObject(T object);
	
	public T getObject();
}
