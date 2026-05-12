# Conceptos clave de Jenkins

## Glosario

| Término | Definición |
|---------|------------|
| **Job / Project** | Una tarea automatizada definida en Jenkins (ej. "buildear la app X") |
| **Build** | Una ejecución específica de un job (ej. "build #47 del job balisboot") |
| **Pipeline** | Tipo de job especial: una secuencia de pasos definida como código |
| **Jenkinsfile** | El archivo de texto que contiene esa secuencia, escrito en Groovy |
| **Stage** | Una fase visible del pipeline (Build, Test, Deploy) |
| **Step** | Una acción concreta dentro de un stage (ej. `sh 'mvn clean'`) |
| **Workspace** | Carpeta temporal donde Jenkins clona el código y trabaja |
| **Plugin** | Extensión que agrega funcionalidad (Git, Docker, Slack, etc.) |
| **Controller** | El servidor central que coordina todo |
| **Agent** | La máquina que ejecuta los trabajos |

## Arquitectura visual
┌─────────────────────┐
    │  Jenkins Controller │
    │  (UI, scheduler)    │
    └──────────┬──────────┘
               │
    ┌──────────┼──────────┐
    ▼          ▼          ▼
┌───────┐  ┌───────┐  ┌───────┐
│Agent 1│  │Agent 2│  │Agent 3│
│ Build │  │ Test  │  │Deploy │
└───────┘  └───────┘  └───────┘
## Stage View

Cuando un pipeline tiene varios stages, Jenkins genera una vista donde:
- Cada **columna** es un stage
- Cada **fila** es una ejecución (build)
- Cada **recuadro** muestra duración y estado:
  - Verde = pasó OK
  - Rojo = falló
  - Amarillo = inestable
  - Gris animado = corriendo
  