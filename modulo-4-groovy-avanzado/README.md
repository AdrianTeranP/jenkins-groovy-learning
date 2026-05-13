# Módulo 4 - Groovy avanzado en Jenkins

Aquí Groovy y Jenkins se fusionan. Aprendí a meter lógica real dentro de pipelines declarativos usando el bloque `script { }`, funciones reutilizables, manejo de errores con `try/catch` y captura de salida de comandos.

## Contenido

| Archivo | Tema |
|---------|------|
| `01-script-block.md` | Cómo y cuándo usar el bloque `script { }` |
| `02-variables-dinamicas.md` | Capturar salida de comandos con returnStdout |
| `03-funciones-y-errores.md` | Funciones reutilizables y manejo de errores |
| `Jenkinsfile-groovy-avanzado` | Pipeline que combina todo lo aprendido |

## Conceptos clave aprendidos

### Bloque script
- Dentro de `steps` solo se permiten pasos declarativos (`echo`, `sh`, `bat`)
- Para lógica Groovy real (if, for, variables, funciones) hay que usar `script { }`
- Dentro de `script { }` puedes usar todo el Groovy aprendido en el módulo 1

### Variables dinámicas
- `bat(script: '...', returnStdout: true).trim()` captura la salida en Windows
- `sh(script: '...', returnStdout: true).trim()` lo mismo en Linux/Mac
- Útil para versiones, fechas, info del sistema, números de commit, etc.

### Funciones
- Se definen fuera del bloque `pipeline { }` o dentro de `script { }`
- Permiten reutilizar lógica entre stages
- Sintaxis: `def nombreFuncion(parametros) { ... }`

### Manejo de errores
- `try { ... } catch (Exception e) { ... } finally { ... }`
- `e.message` contiene el texto del error
- `e.class.name` contiene el tipo de excepción
- El pipeline NO muere si el error se atrapa con catch
- `finally` se ejecuta SIEMPRE, falle o no

### Iteración
- `lista.each { item -> ... }` para listas
- `mapa.each { clave, valor -> ... }` para mapas

## Experimento realizado

Pipeline `pipeline-groovy-avanzado` con 4 stages:

1. **Validar ambiente** - función que verifica que el ambiente sea válido
2. **Capturar info del sistema** - usa `bat` con returnStdout para fecha y usuario
3. **Iterar servidores** - aplica `.each` a una lista
4. **Deploy con manejo de errores** - try/catch que atrapa un error simulado

Probé con `SIMULAR_ERROR = true` y comprobé que el pipeline NO muere: el catch atrapa el error, ejecuta lógica de recuperación, y el stage termina en verde aunque adentro haya una X roja del error original.