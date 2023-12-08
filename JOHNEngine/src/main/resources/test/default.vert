#version 460

layout (location=0) in vec3 position;
layout (location=1) in vec2 texCoord;

out vec2 outTexCoord;

uniform mat4 cameraMatrix;
uniform mat4 objectPositionMatrix;

void main()
{
    gl_Position = cameraMatrix * objectPositionMatrix * vec4(position, 1.0);
    outTexCoord = texCoord;
}
