# Pipeline declarativo vs scripted

Jenkins acepta dos sintaxis para escribir Jenkinsfiles.

## Declarativo (recomendado)

Estructura fija. Más fácil de leer y mantener.

```groovy
pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
                sh 'mvn clean package'
            }
        }
    }
}
```

**Ventajas:**
- Sintaxis predecible
- Validación temprana de errores
- Más fácil de leer
- Se integra mejor con Blue Ocean

## Scripted (avanzado)

Groovy puro. Máxima flexibilidad.

```groovy
node {
    stage('Build') {
        sh 'mvn clean package'
    }
    if (env.BRANCH == 'main') {
        stage('Deploy') {
            sh './deploy.sh'
        }
    }
}
```

**Ventajas:**
- Es código Groovy de verdad
- Permite lógica compleja
- Más libertad

**Desventajas:**
- Mayor curva de aprendizaje
- Más responsabilidad del desarrollador

## Regla práctica

Empezar siempre con **declarativo**. Si en algún stage se necesita lógica avanzada, meter un bloque `script { }` dentro del declarativo. Ahí se escribe Groovy puro solo para ese pedazo.

```groovy
pipeline {
    agent any
    stages {
        stage('Lógica compleja') {
            steps {
                script {
                    // aquí va Groovy puro
                    def items = ['a', 'b', 'c']
                    items.each { item ->
                        echo "Procesando ${item}"
                    }
                }
            }
        }
    }
}
```
