# Módulo 1 - Groovy básico

Fundamentos del lenguaje Groovy antes de meterlo en Jenkins. Todo lo que está aquí es Groovy puro, sin pipelines todavía.

## Contenido

| Archivo | Tema |
|---------|------|
| `01-variables-y-tipos.groovy` | Declaración con `def`, tipado dinámico |
| `02-strings-gstrings.groovy` | Diferencia entre `'string'` y `"GString"` |
| `03-listas-y-mapas.groovy` | Estructuras de datos básicas |
| `04-closures.groovy` | Concepto clave para entender Jenkinsfiles |

## Conceptos clave aprendidos

- Groovy corre sobre la JVM (Java Virtual Machine)
- `def` permite tipado dinámico — Groovy infiere el tipo
- Las **GStrings** (comillas dobles) interpolan variables con `${variable}`
- Las strings simples (comillas simples) NO interpolan
- Los mapas se acceden con notación de punto: `config.app`
- Las **closures** son bloques de código `{ }` que se pueden guardar y ejecutar
- Toda la sintaxis de Jenkins está basada en closures

## Por qué importa para Jenkins

Cada bloque `{ }` que escribes en un Jenkinsfile (`pipeline {}`, `stages {}`, `steps {}`) es una closure de Groovy. Sin entender este concepto, los Jenkinsfiles parecen magia. Con este concepto claro, todo cobra sentido.