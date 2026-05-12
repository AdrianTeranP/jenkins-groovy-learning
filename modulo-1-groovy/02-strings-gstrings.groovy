// Strings y GStrings en Groovy

def app = "balisboot"
def env = "produccion"

// String literal con comillas simples — NO interpola
def mensaje1 = 'Desplegando ${app} en ${env}'
println mensaje1
// Imprime: Desplegando ${app} en ${env}

// GString con comillas dobles — SÍ interpola
def mensaje2 = "Desplegando ${app} en ${env}"
println mensaje2
// Imprime: Desplegando balisboot en produccion

// Sintaxis corta para variables simples (sin llaves)
def mensaje3 = "App: $app"
println mensaje3

// Strings multilínea
def comando = """
mvn clean package
java -jar target/${app}.jar --env=${env}
"""
println comando