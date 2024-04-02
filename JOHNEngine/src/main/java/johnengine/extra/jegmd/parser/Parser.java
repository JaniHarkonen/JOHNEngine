package johnengine.extra.jegmd.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import johnengine.extra.jegmd.Elements;
import johnengine.extra.jegmd.SpecialCharacters;
import johnengine.extra.jegmd.tokenizer.TokenType;
import johnengine.extra.jegmd.tokenizer.Tokenizer;
import johnengine.testing.DebugUtils;

public class Parser {
    
    private enum Statement {
        START,
        TEMPLATE,
        GUI,
        FRAME,
        BLOCK,
        ELEMENT,
        PROPERTY
    }
    
    public class Node {
        public String type;
        public List<Node> children;
        public Map<String, Tokenizer.Token> properties;
        
        private Node(String type) {
            this.type = type;
            this.children = new ArrayList<>();
            this.properties = new HashMap<>();
        }
        
        private Node(Node baseNode) {
            this.type = baseNode.type;
            this.children = baseNode.children;
            this.properties = new HashMap<>();
            
            for( Map.Entry<String, Tokenizer.Token> en : baseNode.properties.entrySet() )
            this.setProperty(en.getKey(), en.getValue());
        }
        
        
        private void addChild(Node child) {
            this.children.add(child);
        }
        
        public void print() {
            String propertyString = "";
            for( Map.Entry<String, Tokenizer.Token> en : this.properties.entrySet() )
            propertyString += en.getKey() + " - " + en.getValue().value + "\n";
                
            DebugUtils.log(this, "type: " + this.type, propertyString);
            
            for( Node child : this.children )
            child.print();
        }
        
        private void setProperty(String propertyKey, Tokenizer.Token valueToken) {
            this.properties.put(propertyKey, valueToken);
        }
    }
    

    private List<Tokenizer.Token> tokens;
    private List<Node> frameNodes;
    private Node guiNode;
    private Map<String, Node> templates;
    private int position;
    
    public Parser(List<Tokenizer.Token> tokens) {
        this.tokens = tokens;
        this.position = 0;
        this.frameNodes = null;
        this.guiNode = null;
        this.templates = new HashMap<>();
    }
    
    
    public void parse() {
        this.position = 0;
        this.parse(Statement.START, null);
    }
    
    private boolean parse(Statement expectedStatement, Node currentNode) {
        Tokenizer.Token tCurrent = this.getToken(this.position);
        int nextPosition = this.position + 1;
        Tokenizer.Token tNext = 
            (nextPosition < this.tokens.size()) ? this.getToken(nextPosition) : null;
            
        switch( expectedStatement )
        {
            case START: {
                if( !this.parseUntilNoneLeft(null, Statement.GUI, Statement.TEMPLATE) )
                {
                    DebugUtils.log(this, "ERROR: Invalid document body!");
                    return false;
                }
                
                break;
            }
            
            case GUI: {
                if( !tCurrent.equals(TokenType.ELEMENT, Elements.GUI) )
                return false;
                
                if( this.guiNode != null )
                {
                    DebugUtils.log(this, "ERROR: Document must have only one GUI!");
                    return false;
                }
                
                this.position += 2;
                
                this.frameNodes = new ArrayList<>();
                this.guiNode = new Node("gui");
                if( !this.parseUntilNoneLeft(this.guiNode, Statement.FRAME, Statement.PROPERTY) )
                {
                    DebugUtils.log(this, "ERROR: GUIs must only consist of frames!");
                    return false;
                }
                
                break;
            }
            
            case FRAME: {
                if( !tCurrent.equals(TokenType.ELEMENT, Elements.FRAME) )
                return false;
                
                Node frameNode = newFrame();
                this.position++;
                
                if( !this.parse(Statement.BLOCK, frameNode) )
                {
                    DebugUtils.log(this, "ERROR: Body expected after 'frame'!");
                    return false;
                }
                
                break;
            }
            
            case TEMPLATE: {
                if( !tCurrent.hasType(TokenType.ELEMENT) )
                return false;
                
                if( !tNext.hasType(TokenType.IDENTIFIER) )
                return false;
                
                this.position += 2;
                
                Node templateNode = new Node(tCurrent.value);
                this.templates.put(tNext.value, templateNode);
                
                if( !this.parse(Statement.BLOCK, templateNode) )
                {
                    DebugUtils.log(this, "ERROR: Template missing a body!");
                    return false;
                }
                
                break;
            }
            
            case BLOCK: {
                    // Start block {
                if( !tCurrent.equals(TokenType.SPECIAL, SpecialCharacters.BLOCK_START) )
                {
                    DebugUtils.log(this, "ERROR: Expected a block start token '{', got '" + tCurrent.value + "'!", tCurrent.type);
                    return false;
                }
                
                this.position++;
                this.parseUntilNoneLeft(currentNode, Statement.PROPERTY, Statement.ELEMENT);
                
                Tokenizer.Token tEnd = this.getToken(this.position);
                
                    // End block }
                if( !tEnd.equals(TokenType.SPECIAL, SpecialCharacters.BLOCK_END) )
                {
                    DebugUtils.log(this, "ERROR: Expected a block end token '}', go'" + tCurrent.value + "'!");
                    return false;
                }
                
                this.position++;
                
                break;
            }
            
            case PROPERTY: {
                if( 
                    !(
                        tCurrent.hasType(TokenType.PROPERTY) && 
                        tNext.equals(TokenType.SPECIAL, SpecialCharacters.FIELD_VALUE_SEPARATOR)
                    )
                )
                return false;
                
                this.position += 2;
                
                Tokenizer.Token tValue = this.getToken(this.position);
                
                if(
                    tValue.hasType(TokenType.STRING) ||
                    tValue.hasType(TokenType.NUMBER)
                )
                currentNode.setProperty(tCurrent.value, tValue);
                else
                {
                    DebugUtils.log(this, "ERROR: Value (string or number) expected after property name!");
                    return false;
                }
                
                this.position++;
                break;
            }
            
            case ELEMENT : {
                    // Element derived from a template
                if( tCurrent.hasType(TokenType.IDENTIFIER) )
                {
                    Node templateNode = this.getTemplate(tCurrent.value);
                    
                    if( templateNode == null )
                    {
                        DebugUtils.log(this, "ERROR: Element or template '" + tCurrent.value + "' does not exist!");
                        return false;
                    }
                    
                        // Copy the template
                    templateNode = new Node(templateNode);
                    currentNode.addChild(templateNode);
                    
                    this.position++;
                    
                    if( !this.parse(Statement.BLOCK, templateNode) )
                    {
                        DebugUtils.log(this, "ERROR: Template element body expected! Use {} for an empty body.");
                        return false;
                    }
                }
                    // Built-in element
                else if( tCurrent.hasType(TokenType.ELEMENT) )
                {
                    Node elementNode = new Node(tCurrent.value);
                    currentNode.addChild(elementNode);
                    
                    this.position++;
                    
                    if( !this.parse(Statement.BLOCK, elementNode) )
                    {
                        DebugUtils.log(this, "ERROR: Element body expected!");
                        return false;
                    }
                }
                else
                return false;
                
                break;
            }
            
            default: {
                DebugUtils.log(this, "ERROR: Expecting a non-existing statement!");
                return false;
            }
        }
        
        return true;
    }
    
    private boolean parseUntilNoneLeft(
        Node currentNode, Statement... expectedStatements
    ) {
        boolean wasSuccessful = false;
        for( int i = 0; i < expectedStatements.length; i++ )
        {
            if( !this.parse(expectedStatements[i], currentNode) )
            continue;
            
            wasSuccessful = true;
            i = -1;
        }
        
        return wasSuccessful;
    }
    
    private Node newFrame() {
        Node frameNode = new Node(Elements.FRAME);
        this.frameNodes.add(frameNode);
        return frameNode;
    }
    
    private Node getTemplate(String templateName) {
        return this.templates.get(templateName);
    }
    
    private Tokenizer.Token getToken(int position) {
        return this.tokens.get(position);
    }
    
    public List<Node> getFrameNodes() {
        return this.frameNodes;
    }
}
