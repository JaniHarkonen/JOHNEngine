package johnengine.basic.game.components.geometry.rewrite;

public interface IGeometryComponent<T> {

    public T get();
    public T getCopy();
    public void calculate();
}
