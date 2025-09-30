pipeline {
    agent any

    environment {
        AZURE_APP_NAME = 'app-250626000818'
        AZURE_RESOURCE_GROUP = 'rg-app-250626000818'
    }

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
                // Para Windows, usamos 'mvnw.cmd'
                bat 'mvnw.cmd clean package'
            }
        }

        stage('3. Archive Artifacts') {
            steps {
                echo 'Archivando el artefacto JAR para el despliegue...'
                archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
            }
        }

        stage('4. Deploy to Azure') {
            steps {
                echo "Desplegando en Azure Web App: ${AZURE_APP_NAME}..."
                // Este comando usará la sesión que ya iniciaste con 'az login'
                // Para Windows, usamos 'az.cmd'
                bat "az.cmd webapp deploy --resource-group ${AZURE_RESOURCE_GROUP} --name ${AZURE_APP_NAME} --src-path target/*.jar --type jar"
            }
        }
    }

    post {
        always {
            echo 'Pipeline finalizado.'
            cleanWs()
        }
    }
}