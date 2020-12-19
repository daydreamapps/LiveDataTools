pipeline {

    agent any

    triggers {
        githubPush()
        pollSCM('')
    }

    stages {
        stage('Setup') {
            steps {
                sh 'echo "Setup"'
                sh 'chmod +x gradlew'
                sh './gradlew :app:clean --stacktrace'
            }
        }

        stage('Assemble') {
            steps {
                sh 'echo "Assemble"'
                sh './gradlew :app:assemble --stacktrace'
            }
        }

        stage('Lint & Unit Test') {
            parallel {

                stage('Check Style') {
                    steps {
                        sh 'echo "Check Style"'
                        sh './gradlew :app:lintRelease'
                        androidLint()
                    }
                }

                stage('Unit Tests') {
                    steps {
                        sh 'echo "Unit Tests"'
                        sh './gradlew :app:testReleaseUnitTest --stacktrace'
                        sh './gradlew :app:testReleaseUnitTestCoverage --stacktrace'
                    }
                }
            }
        }
    }

    post {
        always {
            archiveArtifacts(
                    allowEmptyArchive: true,
                    artifacts: 'app/build/outputs/aar/app-release.aar'
            )

            junit "**/app/build/test-results/testReleaseUnitTest/*.xml"

            jacoco classPattern: 'tmp/kotlin-classes/, app/build/tmp/kotlin-classes/release',
                    execPattern: 'app/build/jacoco/**.exec',
                    sourceExclusionPattern: 'generated/**.*',
                    sourceInclusionPattern: '**/*.kt',
                    exclusionPattern: '**/*$*.*' +
                            '**/*.xml' +
                            '**/*.json' +
                            '**/R$*.class' +
                            '**/BuildConfig.*' +
                            '**/Manifest*.*' +
                            '**/*Test*.*' +
                            '**/com/example/databinding/*' +
                            '**/com/example/generated/callback/*' +
                            '**/android/databinding/*' +
                            '**/androidx/databinding/*' +
                            '**/di/module/*' +
                            '**/*MapperImpl*.*' +
                            '**/*$ViewInjector*.*' +
                            '**/*$ViewBinder*.*' +
                            '**/BuildConfig.*' +
                            '**/*Component*.*' +
                            '**/*BR*.*' +
                            '**/Manifest*.*' +
                            '**/*$Lambda$*.*' +
                            '**/*Companion*.*' +
                            '**/*Module.*' +
                            '**/*Dagger*.*' +
                            '**/*MembersInjector*.*' +
                            '**/*_Factory*.*' +
                            '**/*_Provide*Factory*.*' +
                            '**/*Extensions*.*' +
                            '**/*$Result.*' +
                            '**/*$Result$*.*' +
                            '**/*Activity.*' +
                            '**/*Fragment.*' +
                            '**/*View.*' +
                            '**/*Args.*' +
                            '**/*Adapter.*' +
                            '**/*Dao.*'
        }
    }
}
