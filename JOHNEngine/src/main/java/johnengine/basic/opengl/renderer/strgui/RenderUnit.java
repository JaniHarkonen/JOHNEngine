package johnengine.basic.opengl.renderer.strgui;

import johnengine.basic.assets.font.Font;

public class RenderUnit {

    final String text;
    final Font font;
    final float x;
    final float y;
    
    RenderUnit(String text, Font font, float x, float y) {
        this.text = text;
        this.font = font;
        this.x = x;
        this.y = y;
    }
}
