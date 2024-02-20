#version 460

in vec2 ioTexCoord;

out vec4 outFragmentColor;

uniform sampler2D uTextureSampler;

void main()
{
    outFragmentColor = texture(uTextureSampler, ioTexCoord);
}
