// Closures en Groovy - el concepto más importante para Jenkins

// Closure básica con parámetro
def saludar = { nombre ->
    println "Hola, ${nombre}"
}
saludar("Jenkins")

// Closure sin parámetros usa 'it' por defecto
def ambientes = ['dev', 'qa', 'prod']
ambientes.each {
    println "Ambiente: ${it}"
}

// Closure con múltiples parámetros
def desplegar = { app, env ->
    println "Desplegando ${app} en ${env}"
}
desplegar('balisboot', 'produccion')

// IMPORTANTE: cada bloque { } en un Jenkinsfile es una closure
// Ejemplo conceptual de cómo se ven los closures en Jenkins:
//
// pipeline {           <-- closure
//     stages {         <-- closure
//         stage('X') { <-- closure
//             steps {  <-- closure
//                 ...
//             }
//         }
//     }
// }