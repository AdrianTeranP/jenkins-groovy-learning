# Módulo 2 - Jenkins core

Conceptos fundamentales de Jenkins: arquitectura, terminología y los dos estilos de pipeline.

## Contenido

| Archivo | Tema |
|---------|------|
| `01-conceptos-clave.md` | Glosario de términos Jenkins |
| `02-declarativo-vs-scripted.md` | Comparación entre los dos estilos de pipeline |
| `Jenkinsfile-hola-jenkins` | Mi primer pipeline funcional |

## Resumen del módulo

### Arquitectura
- **Controller**: el cerebro de Jenkins. Maneja la UI, jobs, scheduler y credenciales.
- **Agent**: los músculos. Ejecuta los pasos del pipeline (build, test, deploy).
- En entornos locales, controller y agent son la misma máquina.

### Tipos de pipeline
- **Declarativo**: estructura fija con `pipeline { ... }`. Recomendado para empezar.
- **Scripted**: Groovy puro con `node { ... }`. Mayor flexibilidad pero más complejo.
- **Mejor práctica**: usar declarativo y meter bloques `script { }` cuando se necesite lógica avanzada.

### Mi primer pipeline
- Job tipo Pipeline llamado `hola-jenkins`
- 3 stages: Saludar, Mostrar info, Despedida
- Stage View en Jenkins muestra cada stage como un recuadro verde si pasa