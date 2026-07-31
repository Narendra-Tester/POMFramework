pipeline{
    agent any 
    stages {
        stage("build"){
            steps(){
                echo("build the project")
            }
        }
        stage("run unit test"){
            steps(){
                echo("run UTs")
            }
        }         
        stage("run integration test"){
            steps(){
               echo("run ITs") 
            }
        }
        stage("deploy to dev"){
            steps(){
               echo("deploy to dev") 
            }
        }
        stage("deploy to qa"){
            steps(){
               echo("deploy to qa") 
            }
        }
        stage("run regression test cases on qa"){
            steps(){
               echo("run test cases on qa") 
            }
        }
        stage("deploy to stage"){
            steps(){
               echo("deploy to stage") 
            }
        }
        stage("run sanity test cases on stage"){
            steps(){
               echo("run sanity test cases on stage") 
            }
        }
        stage("deploy to uat"){
            steps(){
               echo("deploy to uat") 
            }
        }
        stage("deploy to prod"){
            steps(){
               echo("deploy to prod") 
            }
        }
        stage("run smoke test cases on prod"){
            steps(){
               echo("run smoke test cases on prod") 
            }
        }
    }
}