#version 460

in vec2 outTexCoord;
out vec4 fragColor;

uniform sampler2D texSampler;

void main()
{
    fragColor = texture(texSampler, outTexCoord);
}