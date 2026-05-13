# Parameters y when

## Parameters - inputs del usuario

Cuando un pipeline tiene `parameters`, el botón "Build Now" cambia a "Build with Parameters" y muestra un formulario.

### Tipos disponibles

```groovy
parameters {
    string(
        name: 'USUARIO',
        defaultValue: 'admin',
        description: 'Quién ejecuta'
    )

    choice(
        name: 'AMBIENTE',
        choices: ['dev', 'qa', 'prod'],
        description: 'Dónde desplegar'
    )

    booleanParam(
        name: 'EJECUTAR_TESTS',
        defaultValue: true,
        description: '¿Correr tests?'
    )

    password(
        name: 'TOKEN',
        defaultValue: '',
        description: 'Token de acceso'
    )

    text(
        name: 'NOTAS',
        defaultValue: '',
        description: 'Notas del deploy'
    )
}
```

### Cómo usar los parámetros

```groovy
echo "Usuario: ${params.USUARIO}"
echo "Ambiente: ${params.AMBIENTE}"
```

Siempre con `params.NOMBRE`, no solo `NOMBRE`.

## When - ejecución condicional

El stage solo corre si la condición se cumple. Si no, se salta (aparece con icono ⏩ en la Stage View).

### Condiciones simples

```groovy
stage('Deploy a producción') {
    when {
        expression { params.AMBIENTE == 'prod' }
    }
    steps {
        echo 'Solo si AMBIENTE = prod'
    }
}
```

### Otras formas de when

```groovy
when { branch 'main' }                          // Solo en rama main
when { environment name: 'DEPLOY', value: 'true' }  // Si la variable es 'true'
when { anyOf {                                  // Cualquiera se cumple
    branch 'main'
    branch 'develop'
}}
when { allOf {                                  // Todas se cumplen
    branch 'main'
    expression { params.DEPLOY == true }
}}
when { not { branch 'main' } }                  // Negación
```

## Post - acciones al final

```groovy
post {
    always {
        echo 'Siempre se ejecuta'
    }
    success {
        echo 'Solo si todo OK'
    }
    failure {
        echo 'Solo si algo falló'
    }
    unstable {
        echo 'Tests fallidos pero el build vive'
    }
}
```

Útil para:
- Enviar notificaciones (correo, Slack)
- Limpiar archivos temporales
- Registrar resultado en logs
- Etiquetar artefactos