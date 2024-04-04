package johnengine.extra.jegmd.parser;

/**
 * Types of statements that can be parsed by the
 * JEGMD-parser.
 */
enum Statement {
    
    /**
     * Highest level element that contains the body of 
     * a JEGMD-document. Documents must contain only 
     * template element definitions and a single GUI-
     * element.
     */
    DOCUMENT,
    
    /**
     * Template element that can be used as a short-hand
     * for a collection of GUI-elements. Each template must 
     * be derived from a built-in element. Templates can 
     * only be defined in the document body as they are 
     * global to the entire document.
     */
    TEMPLATE,
    
    /**
     * Highest level GUI-element whose body determines the 
     * makeup of the GUI. The GUI must have base pixel 
     * dimensions that determine the placement and the 
     * dimensions of its child elements if the GUI is 
     * stretched to deviate from its original dimensions.
     * <br/><br/>
     * <b>Valid properties:</b>
     * <ul>
     *  <li>width</li>
     *  <li>height</li>
     * </ul>
     */
    GUI,
    
    /**
     * A portion of the GUI that is to be used for rendering 
     * the frame's child elements. Frames are the only GUI-
     * element whose placement and dimension are configured 
     * in terms of pixels rather than columns and rows.
     * <br/><br/>
     * <b>Valid properties:</b>
     * <ul>
     *  <li>x</li>
     *  <li>y</li>
     *  <li>width</li>
     *  <li>height</li>
     *  <li>color</li>
     *  <li>textColor</li>
     * </ul>
     */
    FRAME,
    
    /**
     * Default body of a GUI-element, indicated by a leading {
     * and a trailing }, consiting of properties and elements
     * without restrictions. Although any property can be 
     * added to any element, invalid properties will be ignored 
     * when the GUI is compiled.
     * 
     * @see johnengine.extra.jegmd.Properties List of all available 
     * properties
     */
    BLOCK,
    
    /**
     * A built-in GUI-element beginning with the element name 
     * followed by a BLOCK-body. See Statement.BLOCK for more 
     * information.
     * 
     * @see johnengine.extra.jegmd.Elements List of all available 
     * elements
     */
    ELEMENT,
    
    /**
     * Property of a GUI-element beginning with the name of the 
     * property followed by its value. The name and the property 
     * are separated by a colon ':'.
     * 
     * @see johnengine.extra.jegmd.Properties List of all available 
     * properties
     */
    PROPERTY
}
