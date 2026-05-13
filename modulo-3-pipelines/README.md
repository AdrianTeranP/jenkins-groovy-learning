# Módulo 3 - Jenkinsfile a fondo

Profundización en las directivas del pipeline declarativo: variables, parámetros, condiciones y acciones post-ejecución.

## Contenido

| Archivo | Tema |
|---------|------|
| `01-directivas-jenkinsfile.md` | Anatomía completa de un Jenkinsfile |
| `02-parameters-y-when.md` | Parámetros interactivos y when básico |
| `03-when-a-fondo.md` | Los 7 tipos de condiciones, anyOf, allOf, beforeAgent |
| `Jenkinsfile-pipeline-completo` | Primer pipeline real con todas las directivas |
| `Jenkinsfile-when-experimentos` | Pipeline para experimentar con when |

## Conceptos clave aprendidos

### Directivas principales
- `agent` - dónde se ejecuta el pipeline
- `environment` - variables globales
- `parameters` - inputs del usuario al ejecutar
- `stages` y `stage` - las fases del pipeline
- `when` - condiciones para ejecutar un stage
- `post` - acciones después de todos los stages

### When a fondo
- `expression` - código Groovy
- `branch` - solo ciertas ramas
- `environment` - comparar variables
- `anyOf` - OR lógico
- `allOf` - AND lógico
- `not` - negar
- `changeRequest` - solo Pull Requests
- `beforeAgent` - optimización para evaluar antes de preparar el agent

## Experimentos realizados

### Experimento 1: pipeline-completo

| Build | Ambiente | Tests | Stages ejecutados |
|-------|----------|-------|-------------------|
| #3 | prod | true | 6 stages (todos) |
| #4 | dev | false | 4 stages (Tests y Validación saltados) |
| #5 | qa | true | 5 stages (Validación saltado) |

### Experimento 2: combinaciones de when avanzado

Pipeline con `anyOf`, `allOf` y múltiples `expression`:

| Caso | AMBIENTE | TESTS | SLACK | Verdes | Saltados |
|------|----------|-------|-------|--------|----------|
| 1 | dev | true | false | 3 | 3 |
| 2 | qa | true | false | 4 | 2 |
| 3 | prod | false | true | 3 | 3 |
| 4 | prod | true | true | 5 | 1 |