# Variables dinámicas - capturar salida de comandos

## Por qué importa

En pipelines reales necesitas valores que solo se conocen en tiempo de ejecución:
- Versión actual del proyecto desde el `pom.xml`
- Fecha y hora del build
- Usuario del sistema
- Número de commit de Git
- IP del servidor activo

Esto se logra ejecutando un comando y capturando su salida en una variable.

## La sintaxis clave: returnStdout

```groovy
script {
    def version = bat(
        script: '@echo off & echo 1.2.3',
        returnStdout: true
    ).trim()
    
    echo "Versión: ${version}"
}
```

### Qué hace cada parte

| Parte | Función |
|-------|---------|
| `bat(...)` | Ejecuta un comando batch en Windows |
| `sh(...)` | Lo mismo en Linux/Mac |
| `script:` | El comando real a ejecutar |
| `returnStdout: true` | Captura la salida (sin esto solo se muestra) |
| `.trim()` | Quita espacios y saltos de línea sobrantes |

## Ejemplos comunes en Windows

### Capturar fecha y hora

```groovy
def fecha = bat(
    script: '@echo off & echo %date% %time%',
    returnStdout: true
).trim()
```

### Capturar usuario actual

```groovy
def usuario = bat(
    script: '@echo off & echo %USERNAME%',
    returnStdout: true
).trim()
```

### Capturar versión desde Maven

```groovy
def version = bat(
    script: '@echo off & mvn help:evaluate -Dexpression=project.version -q -DforceStdout',
    returnStdout: true
).trim()
```

## Ejemplos en Linux/Mac

```groovy
def fecha = sh(
    script: 'date "+%Y-%m-%d %H:%M:%S"',
    returnStdout: true
).trim()

def commit = sh(
    script: 'git rev-parse --short HEAD',
    returnStdout: true
).trim()
```

## Usar las variables en stages posteriores

Una vez capturada, la variable está disponible en todo el stage. Si la quieres entre stages, declárala en `environment`:

```groovy
pipeline {
    agent any
    
    environment {
        VERSION = bat(
            script: '@echo off & echo 1.0.0',
            returnStdout: true
        ).trim()
    }
    
    stages {
        stage('Build') {
            steps {
                echo "Building v${VERSION}"
            }
        }
        stage('Deploy') {
            steps {
                echo "Deploying v${VERSION}"
            }
        }
    }
}
```

## Por qué importa para la migración

Varios pipelines del documento de migración necesitan esto:

- **Librerías Java**: capturar versión desde Maven antes de publicar a Nexus
- **Liberaciones de mapas e IMP**: generar timestamp para nombrar el .zip
- **Catalys**: capturar versión del artefacto para registrar el release
- **Balisbatch**: pasar la fecha del proceso como parámetro al .jar