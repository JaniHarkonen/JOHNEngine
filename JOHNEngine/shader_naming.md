## Shader naming conventions

Because shaders are terrible when it comes to debugging, the following
naming conventions should be used for identifiers in GLSL-based shaders.


### Uniforms
Uniforms should be declared immediately after the declaration of
outgoing data.
All uniforms should have the prefix 'u' while omitting the type of
the uniform:
- uUniformName

Uniforms should be in camel case.


### Structs
Structs should be declared immediately after the version directive.
- struct SomeStructure {}

Structs should be in pascal case.


### Constants
Constants should be declared immediately after the struct definitions.
Constants can omit the type.
- CONSTANT_NAME

Constants should be in screaming snake case.


### In/out
Incoming data should be declared immediately after constant definitions
followed by the declaration of outgoing data.
There are three types of data:
	i   incoming data that is received via the 'layout' keyword
	ii  incoming data that is received from the previous shader in the 
	    pipeline
	iii	outgoing data that will be sent out of the shader pipeline
		(typically to the rasterizer)
The first type of incoming data (i) should have the prefix 'in' as it
is only incoming. The second type (ii) should have the prefix 'io' as 
in this case, the output of one shader will also be the input of another.
Finally, the thir type (iii) should have the prefix 'out' as it will 
only be outgoing.
The type can be omitted:
- layout(location = 0) in vec3 inPosition : incoming through layout
- out ioTexCoord : 'out' from vertex shader, 'in' to fragment shader

Data should be in camel case.


### Types
Variables that use GLSL's built-in types should have a prefix that 
identifies their type:
- float fVariableName : float
- int iVariableName : int
- bool bVariableName : boolean
- vec2 v2VariableName : vec2
- vec3 v3VariableName : vec3
- mat4 m4VariableName : mat4
- sampler2D s2dSamplerName : s2d

There are some exceptions, however, as alternate prefixes may be more 
descriptive of the intent of the variable. Following special prefixes
exist:
- vec3 c3Ambient : 'c3' stands for RGB-color represented by a 
3D-vector
- vec4 c4Texture : 'c4' stands for RGBA-color represented by a 
4D-vector

Variables that are of type struct don't need a prefix.
Variables should be in camel case.
