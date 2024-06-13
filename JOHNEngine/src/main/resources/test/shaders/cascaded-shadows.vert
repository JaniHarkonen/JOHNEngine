#version 460

layout(location = 0) in vec3 inPosition;
layout(location = 1) in vec3 inNormal;
layout(location = 2) in vec3 inTangent;
layout(location = 3) in vec3 inBitangent;
layout(location = 4) in vec2 inTextureCoord;

uniform mat4 uShadowProjectionMatrix;
uniform mat4 uShadowModelMatrix;

void main()
{
    gl_Position = uShadowProjectionMatrix * uShadowModelMatrix * vec4(inPosition, 1.0);
}
