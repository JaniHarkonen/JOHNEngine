package johnengine.utils;

import org.json.JSONObject;

import johnengine.basic.assets.font.Font;
import johnengine.basic.assets.texture.Texture;

public class FontUtils {

    public static Font jsonToFont(
        String fontName, 
        Texture fontTexture, 
        String characterSet,
        JSONObject json
    ) {
        Font font = new Font(
            fontName, 
            fontTexture, 
            json.getFloat("width"), 
            json.getFloat("height")
        );
        
        JSONObject characters = json.getJSONObject("characters");
        for( int i = 0; i < characterSet.length(); i++ )
        {
            char character = characterSet.charAt(i);
            JSONObject characterJson = 
                characters.getJSONObject(""+character);
            
            Font.Glyph glyph = new Font.Glyph(
                character,
                characterJson.getFloat("x"),
                characterJson.getFloat("y"),
                characterJson.getFloat("width"),
                characterJson.getFloat("height"),
                characterJson.getFloat("originX"),
                characterJson.getFloat("originY"),
                characterJson.getFloat("advance")
            );
            font.addGlyph(character, glyph);
        }
        
        return font;
    }
}
