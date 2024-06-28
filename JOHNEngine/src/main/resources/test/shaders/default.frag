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

struct DirectionalLight {
    vec3 c3Light;
    vec3 v3Direction;
    float fIntensity;
};

struct PointLight {
    float fIntensity;
    vec3 c3Light;
    vec3 v3Position;
    Attenuation attenuation;
};

struct SpotLight {
    vec3 v3Direction;
    float fCutOff;
    PointLight pointLight;
};

struct CascadedShadow {
	mat4 m4ProjectionMatrix;
	float fPortionLength;
};

const int   MAX_POINT_LIGHT_COUNT = 5;
const int   MAX_SPOT_LIGHT_COUNT = 5;
const float SPECULAR_POWER = 10;

	// cascaded shadow maps
const int MAX_CASCADED_SHADOW_COUNT = 3;
//const float BIAS = 0.0005;
const float BIAS = 0.5;
const float SHADOW_FACTOR = 0.25;

in vec3 ioPosition;
in vec3 ioNormal;
in vec3 ioTangent;
in vec3 ioBitangent;
in vec2 ioTexCoord;
in mat4 ioModelViewMatrix;
in vec3 ioViewPosition; // cascaded shadow maps
in vec4 ioWorldPosition; // cascaded shadow maps

out vec4 outFragmentColor;

uniform sampler2D           uTextureSampler;
uniform sampler2D           uNormalSampler;
uniform sampler2D           uRoughnessSampler;
uniform sampler2D			uShadowSampler[MAX_CASCADED_SHADOW_COUNT];
uniform AmbientLight        uAmbientLight;
uniform DirectionalLight    uDirectionalLight;
uniform PointLight          uPointLight[MAX_POINT_LIGHT_COUNT];
uniform SpotLight           uSpotLight[MAX_SPOT_LIGHT_COUNT];
uniform Material            uMaterial;
uniform mat4                uCameraMatrix;
uniform CascadedShadow		uCascadedShadow[MAX_CASCADED_SHADOW_COUNT]; // cascaded shadow maps

vec4 CalculateLightColor(
    vec4 c4BaseDiffuse, 
    vec4 c4BaseSpecular, 
    vec3 c3Light, 
    float fLightIntensity, 
    vec3 v3Position, 
    vec3 v3DirectionToLight, 
    vec3 v3Normal
) 
{
    //vec4 c4ResultDiffuse = vec4(0, 0, 0, 1);
    //vec4 c4ResultSpecular specColor = vec4(0, 0, 0, 1);

        // Diffuse Light
    float fDiffuseFactor = max(dot(v3Normal, v3DirectionToLight), 0.0);
    vec4 c4ResultDiffuse = c4BaseDiffuse * vec4(c3Light, 1.0) * fLightIntensity * fDiffuseFactor;

        // Specular Light
    vec3 v3CameraDirection = normalize(-v3Position);
    vec3 v3DirectionFromLight = -v3DirectionToLight;
    vec3 v3ReflectedLight = normalize(reflect(v3DirectionFromLight, v3Normal));
    float fSpecularFactor = max(dot(v3CameraDirection, v3ReflectedLight), 0.0);//max(dot(v3CameraDirection, v3ReflectedLight), 0.0);
    fSpecularFactor = pow(fSpecularFactor, SPECULAR_POWER);
    vec4 c4ResultSpecular = 
        c4BaseSpecular * 
        fLightIntensity * 
        fSpecularFactor * 
        (1 / texture(uRoughnessSampler, ioTexCoord)) * 
        //uMaterial.fReflectance * 
        vec4(c3Light, 1.0);

    return c4ResultDiffuse + c4ResultSpecular;
}

vec4 CalculatePointLight(
    vec4 c4Diffuse, vec4 c4Specular, PointLight light, vec3 v3Position, vec3 v3Normal
) 
{
    vec3 v3LightPosition = (uCameraMatrix * vec4(uPointLight[0].v3Position, 1.0)).xyz;
    //vec3 v3LightDirection = light.v3Position - v3Position;
    vec3 v3LightDirection = v3LightPosition - v3Position;
    vec3 v3DirectionToLight = normalize(v3LightDirection);
    vec4 c4Light = CalculateLightColor(
        c4Diffuse, 
        c4Specular, 
        light.c3Light, 
        light.fIntensity, 
        v3Position, 
        v3DirectionToLight, 
        v3Normal
    );

        // Apply Attenuation
    float fDistance = length(v3LightDirection);
    float fInverseAttenuation = 
        light.attenuation.fConstant + 
        light.attenuation.fLinear * 
        fDistance + 
        light.attenuation.fExponent * 
        fDistance * 
        fDistance;
    return c4Light / fInverseAttenuation;
}

vec4 CalculateSpotLight(
    vec4 c4Diffuse, vec4 c4Specular, SpotLight light, vec3 v3Position, vec3 v3Normal
) 
{
    vec3 v3LightDirection = light.pointLight.v3Position - v3Position;
    vec3 v3DirectionToLight = normalize(v3LightDirection);
    vec3 v3DirectionFromLight = -v3DirectionToLight;
    float fSpotLightAlpha = dot(v3DirectionFromLight, normalize(light.v3Direction));
    vec4 c4Light = vec4(0, 0, 0, 0);

    if( fSpotLightAlpha > light.fCutOff )
    {
        c4Light = CalculatePointLight(
            c4Diffuse, c4Specular, light.pointLight, v3Position, v3Normal
        );
        c4Light *= (1.0 - (1.0 - fSpotLightAlpha) / (1.0 - light.fCutOff));
    }

    return c4Light;
}

vec3 CalculateNormal(vec3 v3Normal, vec3 v3Tangent, vec3 v3Bitangent, vec2 v2UVs) 
{
    mat3 tbn = mat3(v3Tangent, v3Bitangent, v3Normal);
    vec3 v3Result = texture(uNormalSampler, v2UVs).rgb;
    v3Result = normalize(v3Result * 2.0 - 1.0);
    v3Result = normalize(tbn * v3Result);
    return v3Result;
}

float ProjectShadow(vec4 v4ShadowCoordinate, vec2 v2Offset, int iIndex)
{
	float fShadow = 1.0;

	if( v4ShadowCoordinate.z > -1.0 && v4ShadowCoordinate.z < 1.0 )
	{
		float fDistance = 0.0;
		fDistance = texture(uShadowSampler[iIndex], vec2(v4ShadowCoordinate.xy + v2Offset)).r;
		
		if( v4ShadowCoordinate.w > 0 && fDistance < v4ShadowCoordinate.z - BIAS )
		fShadow = SHADOW_FACTOR;
	}
	
	return fShadow;
}

float CalculateShadow(vec4 v4WorldPosition, int iIndex) 
{
	vec4 v4ShadowMapPosition = uCascadedShadow[iIndex].m4ProjectionMatrix * ioWorldPosition;
	float fShadow = 1.0;
	vec4 v4ShadowCoordinate = (v4ShadowMapPosition / v4ShadowMapPosition.w) * 0.5 + 0.5;
	fShadow = ProjectShadow(v4ShadowCoordinate, vec2(0, 0), iIndex);
	return fShadow;
}

void main()
{
    vec4 c4Ambience = vec4(uAmbientLight.fIntensity * uAmbientLight.c3Ambient, 1);
    vec4 c4Texture = texture(uTextureSampler, ioTexCoord);
    vec3 v3Normal = CalculateNormal(ioNormal, ioTangent, ioBitangent, ioTexCoord);
    vec4 c4DiffuseSpecular = CalculateLightColor(
        c4Texture + uMaterial.c4Diffuse, 
        c4Texture + uMaterial.c4Specular, 
        uDirectionalLight.c3Light, 
        uDirectionalLight.fIntensity, 
        ioViewPosition, 
        normalize(uDirectionalLight.v3Direction), 
        v3Normal
    );
    
    c4DiffuseSpecular += CalculatePointLight(
        //uMaterial.c4Diffuse, uMaterial.c4Specular, uPointLight[0], ioPosition, v3Normal
        uMaterial.c4Diffuse, uMaterial.c4Specular, uPointLight[0], ioViewPosition, v3Normal
    );
    
    int iCascadeIndex = 0;
    for( int i = 0; i < MAX_CASCADED_SHADOW_COUNT - 1; i++ )
    {
    	if( ioViewPosition.z < uCascadedShadow[i].fPortionLength )
    	iCascadeIndex = i + 1;
    }
    
    float fShadowFactor = CalculateShadow(ioWorldPosition, iCascadeIndex);

    for( int i = 1; i < MAX_POINT_LIGHT_COUNT; i++ )
    {
        if( uPointLight[i].fIntensity > 0 )
        {
            c4DiffuseSpecular += CalculatePointLight(
                //uMaterial.c4Diffuse, uMaterial.c4Specular, uPointLight[i], ioPosition, v3Normal
                uMaterial.c4Diffuse, uMaterial.c4Specular, uPointLight[i], ioViewPosition, v3Normal
            );
        }
    }

    /*vec4 c4DiffuseSpecular;
    for( int i = 0; i < MAX_SPOT_LIGHT_COUNT; i++ )
    {
        if( uSpotLight[i].pointLight.fIntensity > 0 )
        {
            c4DiffuseSpecular = CalculateSpotLight(
                c4Texture + uMaterial.c4Diffuse, 
                c4Texture + uMaterial.c4Specular, 
                uSpotLight[i], 
                ioPosition, 
                v3Normal
            );
        }
    }*/
    
    outFragmentColor = (c4Ambience * c4Texture) + c4DiffuseSpecular;
    outFragmentColor.rgb = outFragmentColor.rgb * fShadowFactor;
    //outFragmentColor = vec4(fShadowFactor, 0, 0, 1);

    /*switch (iCascadeIndex) {
        case 0:
        outFragmentColor.rgb *= vec3(1.0f, 0.25f, 0.25f);
        break;
        case 1:
        outFragmentColor.rgb *= vec3(0.25f, 1.0f, 0.25f);
        break;
        case 2:
        outFragmentColor.rgb *= vec3(0.25f, 0.25f, 1.0f);
        break;
        default :
        outFragmentColor.rgb *= vec3(1.0f, 1.0f, 0.25f);
        break;
    }*/
}
