#version 460

struct Material {
    vec4 c4Diffuse;
    vec4 c4Specular;
    float fReflectance;
};

struct Attenuation {
    float fConstant;
    float fLinear;
    float fExponent;
};

struct AmbientLight {
	float fIntensity;
	vec3 c3Ambient;
};

struct PointLight {
    float fIntensity;
    vec3 c3Light;
    vec3 v3Position;
    Attenuation attenuation;
};

const int   MAX_POINT_LIGHT_COUNT = 5;
const float SPECULAR_POWER = 10;
const vec4  FAIL_VEC = vec4(1,1,1,1);

in vec3 ioPosition;
in vec3 ioNormal;
in vec2 ioTexCoord;

out vec4 outFragmentColor;

uniform sampler2D       uTextureSampler;
uniform AmbientLight    uAmbientLight;
uniform PointLight      uPointLight[MAX_POINT_LIGHT_COUNT];
uniform Material        uMaterial;

vec4 CalculateLightColor(vec4 c4BaseDiffuse, vec4 c4BaseSpecular, vec3 c3Light, float fLightIntensity, vec3 v3Position, vec3 v3DirectionToLight, vec3 v3Normal) {
    //vec4 c4ResultDiffuse = vec4(0, 0, 0, 1);
    //vec4 c4ResultSpecular specColor = vec4(0, 0, 0, 1);

        // Diffuse Light
    float fDiffuseFactor = max(dot(v3Normal, v3DirectionToLight), 0.0);
    vec4 c4ResultDiffuse = c4BaseDiffuse * vec4(c3Light, 1.0) * fLightIntensity * fDiffuseFactor;

        // Specular Light
    vec3 v3CameraDirection = normalize(-v3Position);
    vec3 v3DirectionFromLight = -v3DirectionToLight;
    vec3 v3ReflectedLight = normalize(reflect(v3DirectionFromLight, v3Normal));
    float fSpecularFactor = max(dot(v3CameraDirection, v3ReflectedLight), 0.0);
    fSpecularFactor = pow(fSpecularFactor, SPECULAR_POWER);
    vec4 c4ResultSpecular = c4BaseSpecular * fLightIntensity  * fSpecularFactor * uMaterial.fReflectance * vec4(c3Light, 1.0);

    return c4ResultDiffuse + c4ResultSpecular;
}

vec4 CalculatePointLight(vec4 c4Diffuse, vec4 c4Specular, PointLight light, vec3 v3Position, vec3 v3Normal) {
    vec3 v3LightDirection = light.v3Position + v3Position;
    vec3 v3DirectionToLight  = normalize(v3LightDirection);
    vec4 c4Light = CalculateLightColor(c4Diffuse, c4Specular, light.c3Light, light.fIntensity, v3Position, v3DirectionToLight, v3Normal);

        // Apply Attenuation
    float fDistance = length(v3LightDirection);
    float fInverseAttenuation = light.attenuation.fConstant + light.attenuation.fLinear * fDistance + light.attenuation.fExponent * fDistance * fDistance;
    return c4Light / fInverseAttenuation;
}

void main()
{
    vec4 c4AmbienceColor = vec4(uAmbientLight.fIntensity * uAmbientLight.c3Ambient, 1);
    vec4 c4TextureColor = texture(uTextureSampler, ioTexCoord);
    vec4 c4DiffuseSpecular = CalculatePointLight(uMaterial.c4Diffuse, uMaterial.c4Specular, uPointLight[0], ioPosition, ioNormal);

    /*for( int i=1; i < MAX_POINT_LIGHT_COUNT; i++ )
    {
        if (uPointLight[i].intensity > 0)
        {
            c4DiffuseSpecular += CalculatePointLight(uMaterial.c4Diffuse, uPointLight[i], ioPosition, ioNormal);
        }
    }*/
    outFragmentColor = (c4AmbienceColor * c4TextureColor) + c4DiffuseSpecular;
}