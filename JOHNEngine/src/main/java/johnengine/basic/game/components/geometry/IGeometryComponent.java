package johnengine.basic.game.components.geometry;

public interface IGeometryComponent<T> {
    
    public interface Calculatable<TT> extends IGeometryComponent<TT> {
        public void calculate();
    }
    
    public interface DirectAccess<TT> extends IGeometryComponent<TT> {
        public void set(TT value);
    }

    public T get();
    public T getCopy();
}
