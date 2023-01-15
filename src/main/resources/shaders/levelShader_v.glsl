#version 330

uniform mat4 uViewProjectionMatrix;

in vec4 aVertex;
in vec2 aTexCoord;

in mat3x2 aTexCoordMatrix;
in mat4 aModelMatrix;

in float aOpacity;

out vec4 vVertex;
out vec2 vTexCoord;

out float vOpacity;

void main() {
    vVertex = aVertex;
    vTexCoord = aTexCoordMatrix * vec3(aTexCoord, 1.0);
    vOpacity = aOpacity;

    gl_Position = uViewProjectionMatrix * aModelMatrix * aVertex;
}