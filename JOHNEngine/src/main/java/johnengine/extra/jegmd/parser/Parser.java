package johnengine.extra.jegmd.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import johnengine.core.logger.Logger;
import johnengine.extra.jegmd.Elements;
import johnengine.extra.jegmd.SpecialCharacters;
import johnengine.extra.jegmd.tokenizer.TokenType;
import johnengine.extra.jegmd.tokenizer.Tokenizer;

public class Parser {
    
    /************************ Node-class ************************/
    
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
        
        public String printToString(String indentation) {
            String log = this.indent("AST NODE", indentation) + "\n";
            indentation += "  ";
            log += this.indent("type: " + this.type, indentation) + "\n";
            
            for( Map.Entry<String, Tokenizer.Token> en : this.properties.entrySet() )
            log += this.indent(en.getKey() + " - " + en.getValue().value + "\n", indentation);
            
            log += this.indent("CHILDREN: ", indentation);
            
            if( this.children.size() == 0 )
            log += "NONE";
            
            log += "\n";
            
            for( Node child : this.children )
            log += child.printToString(indentation + "  ");
            
            return log;
        }
        
        private void setProperty(String propertyKey, Tokenizer.Token valueToken) {
            this.properties.put(propertyKey, valueToken);
        }
        
        private String indent(String string, String indentation) {
            return indentation + string;
        }
    }
    
    
    /************************ Parser-class ************************/

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
        this.parse(Statement.DOCUMENT, null);
        this.logAST();
    }
    
    private boolean parse(Statement expectedStatement, Node currentNode) {
        Tokenizer.Token tCurrent = this.getToken(this.position);
        int nextPosition = this.position + 1;
        Tokenizer.Token tNext = 
            (nextPosition < this.tokens.size()) ? this.getToken(nextPosition) : null;
            
        switch( expectedStatement )
        {
            case DOCUMENT: {
                if( !this.parseUntilNoneLeft(null, Statement.GUI, Statement.TEMPLATE) )
                {
                    Logger.log(
                        Logger.VERBOSITY_MINIMAL, 
                        Logger.SEVERITY_FATAL, 
                        this, 
                        "Invalid document body!"
                    );
                    return false;
                }
                
                break;
            }
            
            case GUI: {
                if( !tCurrent.equals(TokenType.ELEMENT, Elements.GUI) )
                return false;
                
                if( this.guiNode != null )
                {
                    Logger.log(
                        Logger.VERBOSITY_MINIMAL, 
                        Logger.SEVERITY_FATAL, 
                        this, 
                        "Document must have only one GUI!"
                    );
                    return false;
                }
                
                this.position += 2;
                this.frameNodes = new ArrayList<>();
                this.guiNode = new Node("gui");
                
                boolean didParse = this.parseUntilNoneLeft(
                    this.guiNode, Statement.FRAME, Statement.PROPERTY
                );
                
                if( !didParse )
                {
                    Logger.log(
                        Logger.VERBOSITY_MINIMAL, 
                        Logger.SEVERITY_FATAL, 
                        this, 
                        "Invalid GUI body!"
                    );
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
                    Tokenizer.Token tInvalid = this.getToken(this.position);
                    Logger.log(
                        Logger.VERBOSITY_VERBOSE, 
                        Logger.SEVERITY_WARNING, 
                        this, 
                        "Frame body expected, got: '" + tInvalid.value + "'!"
                    );
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
                    Tokenizer.Token tInvalid = this.getToken(this.position);
                    Logger.log(
                        Logger.VERBOSITY_VERBOSE, 
                        Logger.SEVERITY_WARNING, 
                        this, 
                        "Template body expected, got: '" + tInvalid.value + "'!"
                    );
                    return false;
                }
                
                break;
            }
            
            case BLOCK: {
                    // Start block {
                if( !tCurrent.equals(TokenType.SPECIAL, SpecialCharacters.BLOCK_START) )
                {
                    Logger.log(
                        Logger.VERBOSITY_VERBOSE, 
                        Logger.SEVERITY_WARNING, 
                        this, 
                        "Block start, '{', expected, got: '" + tCurrent.value + "'!"
                    );
                    return false;
                }
                
                this.position++;
                this.parseUntilNoneLeft(currentNode, Statement.PROPERTY, Statement.ELEMENT);
                
                Tokenizer.Token tEnd = this.getToken(this.position);
                
                    // End block }
                if( !tEnd.equals(TokenType.SPECIAL, SpecialCharacters.BLOCK_END) )
                {
                    Logger.log(
                        Logger.VERBOSITY_VERBOSE, 
                        Logger.SEVERITY_WARNING, 
                        this, 
                        "Block end, '}', expected, got: '" + tEnd.value + "'!"
                    );
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
                    Logger.log(
                        Logger.VERBOSITY_STANDARD, 
                        Logger.SEVERITY_WARNING, 
                        this, 
                        "String or number value expected after property name!"
                    );
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
                        Logger.log(
                            Logger.VERBOSITY_VERBOSE, 
                            Logger.SEVERITY_WARNING, 
                            this, 
                            "Element or template '" + tCurrent.value + "' does not exist!"
                        );
                        return false;
                    }
                    
                        // Copy the template
                    templateNode = new Node(templateNode);
                    currentNode.addChild(templateNode);
                    
                    this.position++;
                    
                    if( !this.parse(Statement.BLOCK, templateNode) )
                    {
                        Logger.log(
                            Logger.VERBOSITY_VERBOSE, 
                            Logger.SEVERITY_WARNING, 
                            this, 
                            "Template element body expected! Use {} for an empty body!"
                        );
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
                        Logger.log(
                            Logger.VERBOSITY_VERBOSE, 
                            Logger.SEVERITY_WARNING, 
                            this, 
                            "Element body expected!"
                        );
                        return false;
                    }
                }
                else
                return false;
                
                break;
            }
            
            default: {
                Logger.log(
                    Logger.VERBOSITY_MINIMAL, 
                    Logger.SEVERITY_FATAL, 
                    this, 
                    "Expecting a non-existing statement!"
                );
                return false;
            }
        }
        
        return true;
    }
    
    private void logAST() {
        if( Logger.getVerbosity() != Logger.VERBOSITY_VERBOSE )
        return;
        
        String log = "Parsed following AST:\n";
        for( Node frameNode : this.frameNodes )
        log += frameNode.printToString("");
        
        Logger.log(
            Logger.VERBOSITY_VERBOSE, 
            Logger.SEVERITY_NOTIFICATION, 
            this, 
            log
        );
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
