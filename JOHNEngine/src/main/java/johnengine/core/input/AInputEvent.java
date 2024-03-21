package johnengine.core.input;

public abstract class AInputEvent<T> {

    protected int hashCode;
    
    public AInputEvent(int hashCode) {
        this.hashCode = hashCode;
    }
    
    
    @Override
    public int hashCode() {
        return this.hashCode;
    }
    
    protected abstract boolean equalsImpl(Object obj);
    
    @Override
    public boolean equals(Object obj) {
        return this.equalsImpl(obj);
    }
    
    public abstract T getValue();
}
