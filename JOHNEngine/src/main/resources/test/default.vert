#version 460

layout(location = 0) in vec3 inPosition;
layout(location = 1) in vec3 inNormal;
layout(location = 2) in vec3 inTangent;
layout(location = 3) in vec3 inBitangent;
layout(location = 4) in vec2 inTextureCoord;

out vec3 ioPosition;
out vec3 ioNormal;
out vec3 ioTangent;
out vec3 ioBitangent;
out vec2 ioTexCoord;

uniform mat4 uProjectionMatrix;
uniform mat4 uCameraMatrix;
uniform mat4 uModelMatrix;

void main()
{
	mat4 m4ModelViewMatrix = uCameraMatrix * uModelMatrix;
	vec4 v4ModelViewPosition = m4ModelViewMatrix * vec4(inPosition, 1.0);
    ioPosition = v4ModelViewPosition.xyz;
    ioNormal = normalize(m4ModelViewMatrix * vec4(inNormal, 0.0)).xyz;
    ioTangent = normalize(m4ModelViewMatrix * vec4(inNormal, 0)).xyz;
    ioBitangent = normalize(m4ModelViewMatrix * vec4(inNormal, 0)).xyz;
    ioTexCoord = inTextureCoord;

    gl_Position = uProjectionMatrix * v4ModelViewPosition;
}
