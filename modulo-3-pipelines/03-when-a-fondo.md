# When a fondo - los 7 tipos de condiciones

`when` va dentro de un `stage` y decide si los `steps` se ejecutan o no. Si la condición es falsa, el stage se salta (icono ⏩ en Stage View) pero el pipeline continúa.

## Estructura básica

```groovy
stage('Mi stage') {
    when {
        // condición aquí
    }
    steps {
        // solo corren si la condición se cumple
    }
}
```

## Los 7 tipos de condiciones

### 1. expression - código Groovy (el más usado)

```groovy
when {
    expression { params.AMBIENTE == 'prod' }
}
```

Permite cualquier expresión Groovy que devuelva true/false.

### 2. branch - solo en ciertas ramas de Git

```groovy
when { branch 'main' }
when { branch 'feature/*' }     // con wildcards
when { branch 'release/*' }
```

### 3. environment - comparar variables de entorno

```groovy
when {
    environment name: 'DEPLOY_ACTIVO', value: 'true'
}
```

### 4. anyOf - OR lógico (al menos una)

```groovy
when {
    anyOf {
        branch 'main'
        branch 'develop'
        expression { params.FORZAR == true }
    }
}
```

### 5. allOf - AND lógico (todas se cumplen)

```groovy
when {
    allOf {
        branch 'main'
        expression { params.AMBIENTE == 'prod' }
        expression { params.APROBADO == true }
    }
}
```

### 6. not - negar una condición

```groovy
when {
    not { branch 'main' }
}
```

### 7. changeRequest - solo si es Pull Request

```groovy
when { changeRequest() }
```

## Combinaciones poderosas

```groovy
when {
    allOf {
        branch 'main'
        not { changeRequest() }
        anyOf {
            expression { params.AMBIENTE == 'prod' }
            expression { env.BUILD_CAUSE == 'SCHEDULED' }
        }
    }
}
```

Se lee: "rama=main Y no es PR Y (ambiente=prod O fue scheduled)".

## beforeAgent: optimización importante

Por defecto, `when` se evalúa después de preparar el agent. Si el agent es pesado (ej. contenedor Docker), conviene evaluar ANTES:

```groovy
stage('Deploy pesado') {
    agent { docker 'maven:3.9' }
    when {
        beforeAgent true
        expression { params.AMBIENTE == 'prod' }
    }
    steps {
        sh 'mvn deploy'
    }
}
```

Con `beforeAgent true`, si la condición es falsa Jenkins NO descarga el contenedor.

## Experimento realizado

Probé un pipeline con 3 parámetros (AMBIENTE, EJECUTAR_TESTS, NOTIFICAR_SLACK) y 6 stages con diferentes condiciones:

| Caso | AMBIENTE | TESTS | SLACK | Verdes | Saltados |
|------|----------|-------|-------|--------|----------|
| 1 | dev | true | false | 3 | 3 |
| 2 | qa | true | false | 4 | 2 |
| 3 | prod | false | true | 3 | 3 |
| 4 | prod | true | true | 5 | 1 |

Esto confirmó visualmente cómo `expression`, `anyOf` y `allOf` reaccionan a diferentes combinaciones de parámetros.

## Resumen visual de estados

| Icono | Significado |
|-------|-------------|
| ✓ verde | Stage ejecutado correctamente |
| ⏩ gris doble flecha | Stage saltado por `when` false |
| ✗ rojo | Stage falló (error en algún step) |
| Animado gris | Stage en ejecución |