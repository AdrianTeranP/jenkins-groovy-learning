# Funciones reutilizables y manejo de errores

## Funciones en Jenkinsfile

Cuando tienes lógica que se repite o que necesita parámetros, la conviertes en función. Las funciones se definen **fuera del bloque `pipeline { }`**.

### Sintaxis básica

```groovy
def nombreFuncion(parametro) {
    // lógica aquí
    return resultado
}
```

### Ejemplos

```groovy
def saludar(nombre) {
    echo "Hola, ${nombre}"
}

def calcularVersion(major, minor, patch) {
    return "${major}.${minor}.${patch}"
}

def calcularRuta(ambiente) {
    return "C:\\deploy\\${ambiente}"
}

pipeline {
    agent any
    
    stages {
        stage('Usar funciones') {
            steps {
                script {
                    saludar('Jenkins')
                    
                    def v = calcularVersion(1, 2, 3)
                    echo "Versión: ${v}"
                    
                    def ruta = calcularRuta('prod')
                    echo "Ruta: ${ruta}"
                }
            }
        }
    }
}
```

### Beneficios

- No repetir lógica entre stages
- Pipelines más legibles
- Fácil de testear cambios (solo cambias la función)
- Base para shared libraries del módulo 5

## Manejo de errores con try/catch

Por defecto, si un comando falla, el pipeline entero muere. A veces no quieres eso — quieres recuperarte del error y seguir.

### Sintaxis

```groovy
script {
    try {
        // código que puede fallar
        bat 'comando-riesgoso.exe'
    } catch (Exception e) {
        // código de recuperación
        echo "Error: ${e.message}"
    } finally {
        // SIEMPRE se ejecuta
        echo "Limpieza"
    }
}
```

### Propiedades de la excepción

Cuando atrapas un error con `catch (Exception e)`, la variable `e` es un objeto con info útil:

| Propiedad | Contiene |
|-----------|----------|
| `e.message` | El mensaje del error (el más usado) |
| `e.class.name` | El tipo de excepción (ej. hudson.AbortException) |
| `e.cause` | El error original si hubo encadenamiento |
| `e.stackTrace` | Rastro completo de dónde falló |

### Ejemplo completo

```groovy
stage('Deploy con recuperación') {
    steps {
        script {
            try {
                echo 'Desplegando aplicación...'
                bat 'jboss-cli.bat --connect --command=deploy app.war'
                echo 'Deploy exitoso'
            } catch (Exception e) {
                echo "Deploy falló: ${e.message}"
                echo 'Iniciando rollback...'
                bat 'jboss-cli.bat --connect --command=undeploy app.war'
                bat 'jboss-cli.bat --connect --command=deploy app-anterior.war'
                echo 'Rollback completado'
            } finally {
                echo 'Liberando recursos temporales'
            }
        }
    }
}
```

### Cuándo usar try/catch

- Deploys que pueden fallar y necesitan rollback
- Llamadas a APIs externas inestables
- Validaciones donde quieres seguir aunque algo falle
- Pruebas opcionales que no deben matar el pipeline
- Limpieza de recursos (siempre con finally)

### Cuándo NO usar try/catch

- Validaciones críticas (si fallan, el pipeline DEBE morir)
- Cuando no tienes lógica de recuperación real
- Si solo vas a hacer `echo` del error sin reaccionar

## Iteración con .each

Útil para hacer la misma acción en varios elementos.

### Sobre listas

```groovy
script {
    def servidores = ['srv-1', 'srv-2', 'srv-3']
    
    servidores.each { srv ->
        echo "Procesando ${srv}..."
    }
}
```

### Sobre mapas

```groovy
script {
    def ambientes = [
        dev: '192.168.1.10',
        qa: '192.168.1.20',
        prod: '192.168.1.30'
    ]
    
    ambientes.each { nombre, ip ->
        echo "${nombre} está en ${ip}"
    }
}
```

## Combinado: el patrón profesional

```groovy
def desplegarEnServidor(servidor) {
    try {
        echo "Conectando a ${servidor}..."
        // bat "deploy.bat ${servidor}"
        echo "${servidor}: OK"
    } catch (Exception e) {
        echo "${servidor} falló: ${e.message}"
    }
}

pipeline {
    agent any
    
    stages {
        stage('Deploy múltiple') {
            steps {
                script {
                    def servidores = ['srv-1', 'srv-2', 'srv-3']
                    
                    servidores.each { srv ->
                        desplegarEnServidor(srv)
                    }
                }
            }
        }
    }
}
```

Si un servidor falla, los otros igual se procesan. Esto es exactamente lo que vas a necesitar al desplegar a múltiples ambientes o nodos en tu trabajo.