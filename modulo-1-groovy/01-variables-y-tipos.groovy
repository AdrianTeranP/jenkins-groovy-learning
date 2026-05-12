// Variables y tipos en Groovy

// Forma Java clásica (sigue siendo válida)
String nombre = "Jenkins"
int version = 2

// Forma Groovy con def (tipado dinámico)
def app = "balisboot"
def puerto = 8080
def activo = true

// Imprimir valores
println nombre
println "App: ${app}, puerto: ${puerto}, activo: ${activo}"

// Groovy infiere el tipo automáticamente
println app.getClass()    // java.lang.String
println puerto.getClass() // java.lang.Integer
println activo.getClass() // java.lang.Boolean