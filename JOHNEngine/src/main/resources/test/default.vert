#version 460

layout (location=0) in vec3 position;
layout (location=1) in vec3 normal;
layout (location=2) in vec2 texCoord;

out vec3 outPosition;
out vec3 outNormal;
out vec2 outTexCoord;

uniform mat4 uProjectionMatrix;
uniform mat4 uCameraMatrix;
uniform mat4 uModelMatrix;

void main()
{
	mat4 modelViewMatrix = uCameraMatrix * uModelMatrix;
	vec4 modelViewPosition = modelViewMatrix * vec4(position, 1.0);
    //gl_Position = cameraMatrix * objectPositionMatrix * vec4(position, 1.0);
    gl_Position = uProjectionMatrix * modelViewPosition;
    outPosition = modelViewPosition.xyz;
    outNormal = normalize(modelViewMatrix * vec4(normal, 0.0)).xyz;
    outTexCoord = texCoord;
}
