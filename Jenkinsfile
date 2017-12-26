#!/usr/bin/env groovy

pipeline {
    agent { docker 'maven:3.3.3' }
    stages {
        stage('build') {
            steps {
                sh 'mvn --version'
                echo 'Building ...'
                sh 'printenv'
                echo "Running ${env.BUILD_ID} on ${env.JENKINS_URL}"
                sh 'mvn package'
                archiveArtifacts artifacts: '**/target/*.jar', fingerprint: true
            }
        }
        stage('test') {
            steps {
                echo 'Testing ...'
                sh 'mvn test'
            }
        }
        stage('Deploy') {
            when {
              expression {
                currentBuild.result == null || currentBuild.result == 'SUCCESS' 
              }
            }
            steps {
                echo 'deploying...'
            }
        }
    }
}
