/*#version 460
layout (location = 0) in vec3 position;
layout (location = 1) in vec2 uv;

out vec3 outUV;

uniform mat4 cameraProjectionMatrix;
uniform mat4 cameraOrientationMatrix;

void main()
{
	gl_Position = cameraProjectionMatrix * cameraOrientationMatrix * vec4(position, 1.0);
	outUV = uv;
}*/

#version 460

layout (location=0) in vec3 position;
layout (location=1) in vec2 texCoord;

out vec2 outTexCoord;

void main()
{
    gl_Position = vec4(position, 1.0);
    outTexCoord = texCoord;
}
