package johnengine.basic.game.components;

public class CAttenuation {

    public static final float DEFAULT_CONSTANT = 0.0f;
    public static final float DEFAULT_LINEAR = 0.0f;
    public static final float DEFAULT_EXPONENT = 1.0f;
    
    private float constant;
    private float linear;
    private float exponent;
    
    public CAttenuation(float constant, float linear, float exponent) {
        this.constant = constant;
        this.linear = linear;
        this.exponent = exponent;
    }
    
    public CAttenuation() {
        this(DEFAULT_CONSTANT, DEFAULT_LINEAR, DEFAULT_EXPONENT);
    }
    
    
    public float getConstant() {
        return this.constant;
    }
    
    public float getLinear() {
        return this.linear;
    }
    
    public float getExponent() {
        return this.exponent;
    }
    
    
    public void setConstant(float constant) {
        this.constant = constant;
    }
    
    public void setLinear(float linear) {
        this.linear = linear;
    }
    
    public void setExponent(float exponent) {
        this.exponent = exponent;
    }
}
