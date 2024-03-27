#version 460

layout(location = 0) in vec3 inPosition;
layout(location = 1) in vec3 inNormal;
layout(location = 2) in vec3 inTangent;
layout(location = 3) in vec3 inBitangent;
layout(location = 4) in vec2 inTextureCoord;

out vec2 ioTexCoord;

uniform mat4 uProjectionMatrix;
uniform mat4 uModelMatrix;
uniform vec3 uTextOffset;

void main()
{
    gl_Position = uProjectionMatrix * (uModelMatrix * vec4(inPosition, 1.0));
    ioTexCoord = inTextureCoord;
}  
