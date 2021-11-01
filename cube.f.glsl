#version 330

in  vec2 outTexCoord;
in  vec3 outNormal;
out vec4 fragColor;

uniform sampler2D texture_sampler;

void main()
{
    vec4 color = texture(texture_sampler, outTexCoord);
    vec3 lightDir = vec3(0, 0, 1);
    float d = dot(outNormal, lightDir);
    fragColor = color;
}