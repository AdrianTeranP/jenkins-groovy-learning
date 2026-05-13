# El bloque script

## Por qué existe

Los pipelines declarativos tienen una estructura fija. Dentro de `steps` solo se permiten pasos predefinidos como `echo`, `sh`, `bat`, `checkout scm`. No puedes escribir lógica Groovy directamente ahí.

Para usar Groovy real (variables, if, for, funciones) hay que abrir un bloque `script { }`.

## Lo declarativo limitado

```groovy
stage('Simple') {
    steps {
        echo 'Hola'
        echo 'Otro mensaje'
    }
}
```

Esto está bien para casos simples, pero no puedes hacer:
- Declarar variables
- Usar if/else
- Iterar con for/each
- Capturar la salida de un comando
- Llamar a funciones

## Lo declarativo + script

```groovy
stage('Con lógica') {
    steps {
        script {
            def nombre = 'Jenkins'
            def version = 2
            
            if (version > 1) {
                echo "${nombre} versión moderna"
            } else {
                echo "${nombre} versión antigua"
            }
        }
    }
}
```

Dentro de `script { }` puedes usar TODO lo que aprendiste en el módulo 1: variables con `def`, GStrings, listas, mapas, closures, if/else, for, each.

## Cuándo usar script

| Caso | Necesita script |
|------|----------------|
| Solo `echo` o `sh`/`bat` simples | NO |
| Capturar salida de un comando | SÍ |
| Lógica condicional compleja | SÍ |
| Iterar sobre una lista | SÍ |
| Manejo de errores try/catch | SÍ |
| Llamar a una función | SÍ |

## Regla práctica

Empezar siempre con declarativo. Solo meter `script { }` cuando lo necesites de verdad. Si todo tu pipeline está lleno de bloques `script`, considera pasar a pipeline scripted o usar shared libraries (módulo 5).