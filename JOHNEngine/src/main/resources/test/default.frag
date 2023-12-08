#version 460

in vec3 outPosition;
in vec3 outNormal;
in vec2 outTexCoord;

out vec4 fragColor;

struct AmbientLight {
	float intensity;
	vec3 color;
};

uniform sampler2D uTextureSampler;
uniform AmbientLight uAmbientLight;

void main()
{
	
    //fragColor = texture(uTextureSampler, outTexCoord);
    //fragColor = vec4(0.5f * vec3(0.5f, 0.5f, 0.5f), 1) * texture(uTextureSampler, outTexCoord);
    fragColor = vec4(uAmbientLight.intensity * uAmbientLight.color, 1) * texture(uTextureSampler, outTexCoord);
}