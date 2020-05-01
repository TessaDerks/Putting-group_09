
attribute vec3 a_Normal;
attribute vec3 a_Position;

uniform vec3 lightDirection;

uniform mat4 viewProj;
uniform mat4 model;

varying vec3 varColor;

void main(){
    float dotValue = max(dot(a_Normal, lightDirection), 0.3);
    float dotValue1 = max(dot(a_Normal, lightDirection*-1), 0.3);

    varColor = vec3(dotValue+dotValue1*0.5/*, 1.0, 0*/);

    gl_Position = viewProj * model * vec4(a_Position,1.0);
}

