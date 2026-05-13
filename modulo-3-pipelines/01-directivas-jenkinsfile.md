# Directivas del Jenkinsfile declarativo

## Estructura general

```groovy
pipeline {
    agent any
    environment { ... }
    parameters { ... }
    options { ... }
    stages {
        stage('Nombre') {
            when { ... }
            steps { ... }
        }
    }
    post { ... }
}
```

## agent - dónde corre

```groovy
agent any                    // Cualquier agent disponible
agent none                   // Cada stage define el suyo
agent { label 'linux' }      // Agent con esa etiqueta
agent { docker 'maven:3.9' } // Dentro de un contenedor
```

## environment - variables globales

Disponibles en todos los stages.

```groovy
environment {
    APP_NAME = 'mi-app'
    VERSION = '1.0.0'
    DEPLOY_PATH = 'C:/deploy'
}
```

Se usan así: `${APP_NAME}` o `${env.APP_NAME}`.

### Variables predefinidas útiles

| Variable | Contenido |
|----------|-----------|
| `env.BUILD_NUMBER` | Número del build actual (1, 2, 3...) |
| `env.JOB_NAME` | Nombre del job |
| `env.WORKSPACE` | Ruta de trabajo en el agent |
| `env.BUILD_URL` | URL completa del build |

## stages y stage - el corazón del pipeline

```groovy
stages {
    stage('Build') {
        steps {
            echo 'Construyendo...'
            sh 'mvn clean package'
        }
    }
    stage('Test') {
        steps {
            sh 'mvn test'
        }
    }
}
```

Cada `stage` aparece como una columna en la Stage View.

## Steps comunes

| Step | Para qué |
|------|----------|
| `echo 'texto'` | Imprimir mensaje en la consola |
| `sh 'comando'` | Ejecutar comando shell (Linux/Mac) |
| `bat 'comando'` | Ejecutar comando batch (Windows) |
| `checkout scm` | Clonar el código del repositorio |
| `script { ... }` | Bloque de Groovy puro |