#version 460

in vec2 ioTexCoord;

out vec4 outFragmentColor;

uniform int uHasTexture;
uniform vec4 uElementColor;
uniform vec4 uTextColor;
uniform sampler2D uTextureSampler;

void main()
{
    if( uHasTexture == 1 )
    outFragmentColor = texture(uTextureSampler, ioTexCoord) * uElementColor;
    else
    outFragmentColor = uElementColor;
}
