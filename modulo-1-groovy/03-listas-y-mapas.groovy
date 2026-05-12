// Listas y mapas en Groovy

// LISTAS
def ambientes = ['dev', 'qa', 'prod']

println ambientes[0]      // dev
println ambientes[1]      // qa
println ambientes.size()  // 3

// Agregar elementos
ambientes << 'staging'
println ambientes  // [dev, qa, prod, staging]

// MAPAS
def config = [
    app: 'balisboot',
    puerto: 8080,
    activo: true,
    version: '1.0.0'
]

// Dos formas de acceder
println config.app
println config['puerto']

// Agregar o modificar
config.entorno = 'produccion'
println config