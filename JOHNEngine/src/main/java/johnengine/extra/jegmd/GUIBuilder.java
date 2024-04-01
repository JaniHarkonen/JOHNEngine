package johnengine.extra.jegmd;

import java.util.List;

import johnengine.basic.game.gui.JGUI;
//import johnengine.extra.jegmd.tokenizer.AToken;
//import johnengine.extra.jegmd.tokenizer.Tokenizer;
import johnengine.extra.jegmd.parser.Parser;
import johnengine.extra.jegmd.tokenizer2.Tokenizer;

public class GUIBuilder {

    private JGUI resultGUI;
    private String source;
    
    public GUIBuilder(String source) {
        this.resultGUI = null;
        this.source = source;
    }
    
    
    public void buildGUI() {
        if( this.source == null )
        return;
        
        Tokenizer tokenizer = new Tokenizer(this.source);
        List<Tokenizer.Token> tokens;
        tokenizer.tokenize();
        tokens = tokenizer.getTokens();
        
        Parser parser = new Parser(tokens);
        List<Parser.Node> frameNodes;
        parser.parse();
        frameNodes = parser.getFrameNodes();
    }
    
    public JGUI getGUI() {
        return this.resultGUI;
    }
}
