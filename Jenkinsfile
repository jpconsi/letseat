#!/usr/bin/env groovy

timestamps {
    node("nodeBuilds") {
        dir ("${WORKSPACE}") {

            stage('Clean Workspace') {
                    deleteDir()
                }

            stage('Checkout Source') {
                    checkout scm
                  }
            stage('Create Ephemeral .npmrc') {
                    withCredentials([usernamePassword(credentialsId: "07a7b94c-d205-4208-929f-e7a638cd7de0", passwordVariable: "npmAPIKey", usernameVariable: "npmUser")]) {
                        sh "touch ./.npmrc"
                        sh "echo 'registry=${NPM_ARTIFACTORY_URL}npm-virtual' >> ./.npmrc"
                        sh "curl -u${npmUSER}:${npmAPIKey} ${NPM_ARTIFACTORY_AUTH_URL} >> ./.npmrc"
                        sh "echo '@bb:registry = \"${NPM_ARTIFACTORY_URL}npm-virtual\"' >> ./.npmrc"
                        sh "echo 'email=no.reply@blackboard.com' >> ./.npmrc"
                        sh "echo 'always-auth=true' >> ./.npmrc"
                        sh "echo 'package-lock=false' >> ./.npmrc"
                    }
                }

            stage('git config'){
                    withCredentials([usernamePassword(credentialsId: "b823c8d2-d918-48b7-9f50-0027f09d9f08", passwordVariable: "GIT_PASSWORD", usernameVariable: "GIT_USERNAME")]) {
                    sh "git config user.email ${GIT_USERNAME}"
                    sh "git config user.name ${GIT_USERNAME}"
                    sh "git config user.password ${GIT_PASSWORD}"
                    sh "git checkout DEVOPS-390"
                    }

            stage('Version') {
                        sh 'VERSION=$(npm version patch)'
                        sh 'VERSION=$(echo ${VERSION} | cut -c 2-)'
                        sh 'VERSION=${VERSION}.${BUILD_NUMBER}'
                    }
                }

            stage('npm install'){
              withCredentials([usernamePassword(credentialsId: "b823c8d2-d918-48b7-9f50-0027f09d9f08", passwordVariable: "GIT_PASSWORD", usernameVariable: "GIT_USERNAME")]) {
                        sh "npm i --no-package-lock"
                        sh 'npm version ${VERSION}'
                        sh "git config remote.origin.url > GIT_URL"
                        sh "git config --global push.default current"
                        def gitUrl = readFile("GIT_URL").trim().replace("bitbucket.org", "${GIT_USERNAME}:${GIT_PASSWORD}@bitbucket.org")
                        sh "git push ${gitUrl}"
                }
            // /*
            // stage('npm test'){
            //             sh "npm test"
            //     }
            // */
            stage('Publish Artifact') {
                if (env.BRANCH_NAME == "master") {
                        withCredentials([usernamePassword(credentialsId: "07a7b94c-d205-4208-929f-e7a638cd7de0", passwordVariable: "npmAPIKey", usernameVariable: "npmUser")]) {
                            sh "npm publish --registry https://artifactory.transactdevops.com/artifactory/api/npm/sms-npm-release/"
                        }
                    }
                if (env.BRANCH_NAME == "DEVOPS-390") {
                        withCredentials([usernamePassword(credentialsId: "07a7b94c-d205-4208-929f-e7a638cd7de0", passwordVariable: "npmAPIKey", usernameVariable: "npmUser")]) {
                           sh "npm publish --registry https://artifactory.transactdevops.com/artifactory/api/npm/sms-npm-snapshot/"
                        }
                      }
                    }
            }

        }
    }
}
