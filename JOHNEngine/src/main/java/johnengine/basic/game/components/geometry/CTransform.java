package johnengine.basic.game.components.geometry;

import java.util.ArrayList;
import java.util.List;

import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class CTransform {
    
    public abstract class ATransformComponent { 
        protected final Vector3f offset;
        protected final Vector3f absolute;
        protected Vector3f origin;
        
        protected ATransformComponent(Vector3f origin, Vector3f offset, Vector3f absolute) {
            this.offset = offset;
            this.absolute = absolute;
            this.origin = origin;
        }
        
        
        protected void setOrigin(Vector3f origin) {
            this.absolute.sub(origin, this.offset);
            this.origin = origin;
        }
        
        protected void detachOrigin() {
            this.origin = new Vector3f(this.absolute);
            this.offset.set(0.0f, 0.0f, 0.0f);
        }
        
        protected void updateTransform() {
            CTransform.this.update();
        }
        
        protected void transformChanged() {
            CTransform.this.changed();
        }
        
        public void inherit() {
            this.offset.set(0.0f, 0.0f, 0.0f);
            CTransform.this.changed();
        }
    }
    
    public class Position extends ATransformComponent {
        public Position() {
            super(
                new Vector3f(0.0f, 0.0f, 0.0f), 
                new Vector3f(0.0f, 0.0f, 0.0f), 
                new Vector3f(0.0f, 0.0f, 0.0f)
            );
        }
        
        
        private void recalculate(Quaternionf rotation) {
                // 1. rotate offset according to rotation quaternion, store to absolute
                // 2. add absolute to origin, store to absolute
            this.offset.rotate(rotation, this.absolute);
            this.origin.add(this.absolute, this.absolute);
        }
        
        public Vector3f getUnsafe() {
            return this.absolute;
        }
        
        public Vector3f get() {
            this.updateTransform();
            return this.getUnsafe();
        }
        
        public void setPosition(float x, float y, float z) {
            if( !CTransform.this.isRoot() )
            return;
            
            this.origin.set(x, y, z);
            this.transformChanged();
        }
        
        public void setPosition(Vector3f position) {
            this.setPosition(position.x, position.y, position.z);
        }
        
        public void shift(float x, float y, float z) {
            this.setPosition(
                this.origin.x + x,
                this.origin.y + y,
                this.origin.z + z
            );
        }
        
        public void shift(Vector3f shift) {
            this.shift(shift.x, shift.y, shift.z);
        }
    }
    
    public class Rotation extends ATransformComponent {
        private final Quaternionf quaternion;
        
        public Rotation() {
            super(
                new Vector3f(0.0f, 0.0f, 0.0f), 
                new Vector3f(0.0f, 0.0f, 0.0f), 
                new Vector3f(0.0f, 0.0f, 0.0f)
            );
            this.quaternion = new Quaternionf();
        }
        
        
        private void recalculate() {
            this.origin.add(this.offset, this.absolute);
            this.quaternion
            .fromAxisAngleDeg(X_AXIS, this.absolute.x)
            .mul((new Quaternionf()).fromAxisAngleDeg(Y_AXIS, this.absolute.y));
        }
        
        public Vector3f getAnglesUnsafe() {
            return this.absolute;
        }
        
        public Vector3f getAngles() {
            this.updateTransform();
            return this.getAnglesUnsafe();
        }
        
        public Quaternionf getUnsafe() {
            return this.quaternion;
        }
        
        public Quaternionf get() {
            this.updateTransform();
            return this.getUnsafe();
        }
        
        public void setRotation(float x, float y, float z) {
            this.absolute.set(x, y, z);
            this.setOrigin(this.origin);
            this.transformChanged();
        }
        
        public void setRotation(Vector3f rotation) {
            this.setRotation(rotation.x, rotation.y, rotation.z);
        }
        
        public void rotate(float x, float y, float z) {
            this.offset.add(x, y, z);
            this.transformChanged();
        }
        
        public void rotate(Vector3f shift) {
            this.offset.add(shift.x, shift.y, shift.z);
        }
        
        public Vector3f getForwardVectorUnsafe(Vector3f result) {
            return this.quaternion.positiveZ(result);
        }
        
        public Vector3f getBackwardVectorUnsafe(Vector3f result) {
            return this.quaternion.positiveZ(result).negate();
        }
        
        public Vector3f getLeftVectorUnsafe(Vector3f result) {
            return this.quaternion.positiveX(result);
        }
        
        public Vector3f getRightVectorUnsafe(Vector3f result) {
            return this.quaternion.positiveX(result).negate();
        }
        
        public Vector3f getForwardVector(Vector3f result) {
            this.updateTransform();
            return this.getForwardVectorUnsafe(result);
        }
        
        public Vector3f getBackwardVector(Vector3f result) {
            this.updateTransform();
            return this.getBackwardVectorUnsafe(result);
        }
        
        public Vector3f getLeftVector(Vector3f result) {
            this.updateTransform();
            return this.getLeftVectorUnsafe(result);
        }
        
        public Vector3f getRightVector(Vector3f result) {
            this.updateTransform();
            return this.getRightVectorUnsafe(result);
        }
    }
    
    public class Scale extends ATransformComponent {
        public Scale() {
            super(
                new Vector3f(1.0f, 1.0f, 1.0f), 
                new Vector3f(0.0f, 0.0f, 0.0f), 
                new Vector3f(0.0f, 0.0f, 0.0f)
            );
        }
        
        
        private void recalculate() {
            this.origin.add(this.offset, this.absolute);
        }
        
        public Vector3f getUnsafe() {
            return this.absolute;
        }
        
        public Vector3f get() {
            this.updateTransform();
            return this.getUnsafe();
        }
        
        public void setScale(float x, float y, float z) {
            this.absolute.set(x, y, z);
            this.setOrigin(this.origin);
            this.transformChanged();
        }
        
        public void setScale(Vector3f scale) {
            this.setScale(scale.x, scale.y, scale.z);
        }
        
        public void scale(float x, float y, float z) {
            this.setScale(
                this.offset.x + x,
                this.offset.y + y,
                this.offset.z + z
            );
        }
        
        public void scale(Vector3f scale) {
            this.scale(scale.x, scale.y, scale.z);
        }
    }
    

    private static final Vector3f X_AXIS = new Vector3f(1.0f, 0.0f, 0.0f);
    private static final Vector3f Y_AXIS = new Vector3f(0.0f, 1.0f, 0.0f);
    private static final Vector3f Z_AXIS = new Vector3f(0.0f, 0.0f, 1.0f);

    private final List<CTransform> children;
    private CTransform parent;
    private Matrix4f transform;
    private Position position;
    private Scale scale;
    private Rotation rotation;
    private boolean isStale;
    
    public CTransform() {
        this.children = new ArrayList<>();
        this.parent = null;
        this.transform = new Matrix4f();
        this.position = new Position();
        this.scale = new Scale();
        this.rotation = new Rotation();
        this.isStale = true;
    }
    
    
    public void update() {
        if( this.isUpToDate() )
        return;
        
            // Update parent transforms
        if( !this.isRoot() )
        this.parent.update();
        
            // Recalculate transform components
        this.rotation.recalculate();
        this.scale.recalculate();
        this.position.recalculate(this.rotation.getUnsafe());
        this.updateTransformMatrix();
        
        this.updated();
    }
    
    protected void updateTransformMatrix() {
        this.transform.translationRotateScale(
            this.position.getUnsafe(),
            this.rotation.getUnsafe(),
            this.scale.getUnsafe()
        );
    }
    
    private void updated() {
        this.isStale = false;
    }
    
    private void changed() {
        this.isStale = true;
        
        for( CTransform child : this.children )
        child.changed();
    }
    
    public void attach(CTransform child) {
        this.children.add(child);
        child.attached(this);
    }
    
    public void detach(int childIndex) {
        this.children
        .remove(childIndex)
        .detached();
    }
    
    public void detach(CTransform child) {
        int s = this.children.size();
        for( int i = 0; i < s; i++ )
        {
            CTransform myChild = this.children.get(i);
            
            if( myChild != child )
            continue;
            
            this.detach(i);
            break;
        }
    }
    
    public void attachTo(CTransform parent) {
        parent.attach(this);
    }
    
    public void unparent() {
        this.parent.detach(this);
    }
    
    private void attached(CTransform parent) {
        this.parent = parent;
        this.update();
        
            // The parent was updated before child's attached() is called,
            // thus stale getters can be used for performance
        this.position.setOrigin(this.parent.position.getUnsafe());
        this.rotation.setOrigin(this.parent.rotation.getAnglesUnsafe());
        this.scale.setOrigin(this.parent.scale.getUnsafe());
    }
    
    private void detached() {
        this.parent = null;
        
            // Detach transform components and reset offsets
        this.position.detachOrigin();
        this.rotation.detachOrigin();
        this.scale.detachOrigin();
    }
    
    
    /********************* GETTERS ***********************/
    
    public boolean isStale() {
        return this.isStale;
    }
    
    public boolean isUpToDate() {
        return (this.isStale == false);
    }
    
    public boolean isRoot() {
        return (this.parent == null);
    }
    
    public Matrix4f get() {
        this.update();
        return this.getUnsafe();
    }
    
    public Matrix4f getUnsafe() {
        return this.transform;
    }
    
    public Position getPosition() {
        return this.position;
    }
    
    public Rotation getRotation() {
        return this.rotation;
    }
    
    public Scale getScale() {
        return this.scale;
    }
}
