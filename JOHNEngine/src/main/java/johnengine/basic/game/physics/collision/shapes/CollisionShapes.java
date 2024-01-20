package johnengine.basic.game.physics.collision.shapes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import johnengine.basic.game.physics.collision.shapes.plane.CheckPlaneAndBox;
import johnengine.basic.game.physics.collision.shapes.plane.CheckPlaneAndPlane;
import johnengine.basic.game.physics.collision.shapes.plane.CheckPlaneAndSphere;
import johnengine.basic.game.physics.collision.shapes.sphere.CheckSphereAndSphere;

public class CollisionShapes {
    
    /********************** Table-class **********************/
    
    private static class Table {
        
        /******* Pair-class *******/
        private class IntermediaryEntry {
            private int column;
            private int row;
            private ICollisionCheck checker;
            
            public IntermediaryEntry(ICollisionCheck checker, int column, int row) {
                this.checker = checker;
                this.column = column;
                this.row = row;
            }
        }
        
        
        /******* Table-class *******/
        
        private ICollisionCheck[][] table;
        private List<IntermediaryEntry> intermediaryTable;
        private int highestPrecedence;
        
        private Table() {
            this.table = null;
            this.intermediaryTable = new ArrayList<>();
            this.highestPrecedence = -1;
        }
        
        
        private void assemble() {
            int size = this.highestPrecedence + 1;
            this.table = new ICollisionCheck[size][size];
            
            for( IntermediaryEntry entry : this.intermediaryTable )
            {
                this.table[entry.row][entry.column] = entry.checker;
                this.table[entry.column][entry.row] = entry.checker;
            }
            
            this.intermediaryTable = null;
        }
        
        private void addChecker(ICollisionCheck checker, int lowPrecedence, int highPrecedence) {
            IntermediaryEntry en = new IntermediaryEntry(checker, lowPrecedence, highPrecedence);
            this.intermediaryTable.add(lowPrecedence, en);
            
            int maxPrecedence = Math.max(lowPrecedence, highPrecedence);
            
            if( maxPrecedence > this.highestPrecedence )
            this.highestPrecedence = maxPrecedence;
        }
        
        private ICollisionCheck getChecker(int shape1, int shape2) {
            return this.table[shape2][shape1];
        }
    }
    
    
    /*********************** CollisionShapes-class ***********************/
    
    private static final Map<String, Integer> SHAPES;
    private static final Table CHECKER_TABLE;
    
    static {
        SHAPES = new HashMap<>();
        CHECKER_TABLE = new Table();
        initialize();
        CHECKER_TABLE.assemble();
    }
    
    
    private static void addChecker(ICollisionCheck checker, String shapeName1, String shapeName2) {
        int precedence1 = SHAPES.get(shapeName1);
        int precedence2 = SHAPES.get(shapeName2);
        
        CHECKER_TABLE.addChecker(
            checker, 
            Math.min(precedence1, precedence2), 
            Math.max(precedence1, precedence2)
        );
    }
    
    private static void registerShape(String shapeName, int shapePrecedence) {
        SHAPES.put(shapeName, shapePrecedence);
    }
    
    public static ICollisionCheck getChecker(Shape shape1, Shape shape2) {
        return CHECKER_TABLE.getChecker(shape1.getPrecedence(), shape2.getPrecedence());
    }
    
    public static Integer getShapePrecedence(String shapeName) {
        Integer shapePrecedence = SHAPES.get(shapeName);
        
        if( shapePrecedence != null )
        return shapePrecedence;
        
        return -1;
    }
    
    
    /************************** INITIALIZATION **************************/ 
    
    private static void initialize() {
            // Shapes must be registered before checkers can be assigned
            // to them, additionally, each shape must be registered with 
            // its precedence
        registerShape("plane", 0);
        registerShape("sphere", 1);
        registerShape("box", 2);
        
            // Checker initializations
        addChecker(new CheckPlaneAndPlane(), "plane", "plane");
        addChecker(new CheckPlaneAndSphere(), "plane", "sphere");
        addChecker(new CheckPlaneAndBox(), "plane", "box");
        addChecker(new CheckSphereAndSphere(), "sphere", "sphere");
        addChecker(new CheckPlaneAndPlane(), "sphere", "box");
        addChecker(new CheckPlaneAndPlane(), "box", "box");
    }
}
