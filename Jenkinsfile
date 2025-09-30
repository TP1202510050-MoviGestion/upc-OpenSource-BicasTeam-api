pipeline {
    // El agente necesita JDK 17 y Maven
    agent any

    stages {
        stage('1. Checkout Code') {
            steps {
                echo 'Clonando el repositorio de la API...'
                checkout scm
            }
        }

        stage('2. Build & White-Box Tests') {
            steps {
                echo 'Ejecutando pruebas de caja blanca (unitarias y de integración)...'
                // El comando 'test' es suficiente para compilar y correr las pruebas.
                // Si falla, el pipeline se detendrá aquí.
                bat 'mvnw.cmd clean test'
            }
        }
    }

    post {
        // Se ejecuta al final, sin importar el resultado.
        always {
            // El 'junit' step busca los reportes de resultados de las pruebas
            // generados por Maven y los muestra en la interfaz de Jenkins.
            junit 'target/surefire-reports/*.xml'

            echo 'Pipeline de pruebas finalizado.'
            cleanWs()
        }
    }
}