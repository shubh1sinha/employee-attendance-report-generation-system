pipeline{
    agent any
    stages{
        stage("checkout"){
            steps{
                sh "git clone https://github.com/shubh1sinha/employee-attendance-report-generation-system.git"
            }
        }
        
        stage("Package-Application"){
            steps{
				sh " /home/azureuser/apache-maven-3.8.6/bin/mvn clean package"
                sh "pwd"
            }
        }
		
		stage("Dockerize Eureka-Server"){
            steps{
			    dir('employee-eureka-gateway') {
				    sh "cd"
                    sh "pwd"
                    sh "sudo docker build -t shubh1sinha/eureka-server:1.0 ."
                }
			    dir('employee-attendance-report-generation-system') {
				    sh "cd"
                    sh "pwd"
                }
            }
        }
		
        stage("Dockerize Employee-Tracking-Microservice"){
            steps{
			    dir('employee-tracking-microservice') {
				    sh "cd"
                    sh "pwd"
                    sh "sudo docker build -t shubh1sinha/employee-tracking-microservice:1.1 ."
                }
			    dir('employee-attendance-report-generation-system') {
				    sh "cd"
                    sh "pwd"
                }
            }
        }
		stage("Dockerize Attendance-Computing-Microservice"){
            steps{
			    dir('report-generation-microservice') {
				    sh "cd"
                    sh "pwd"
                    sh "sudo docker build -t shubh1sinha/report-generation-microservice:1.1 ."
                }
			    dir('employee-attendance-report-generation-system') {
				    sh "cd"
                    sh "pwd"
                }
            }
        }

		stage("Dockerize API-Gateway"){
            steps{
			    dir('api-gateway-microservice') {
				    sh "cd"
                    sh "pwd"
                    sh "sudo docker build -t shubh1sinha/gateway-microservice:1.1 ."
                }
			    dir('employee-attendance-report-generation-system') {
				    sh "cd"
                    sh "pwd"
                }
            }
        }

		stage("Pushing Images"){
            steps{
				sh " sudo docker push shubh1sinha/eureka-server:1.0"
                sh " sudo docker push shubh1sinha/employee-tracking-microservice:1.1"
                sh " sudo docker push shubh1sinha/report-generation-microservice:1.1"
				sh " sudo docker push shubh1sinha/gateway-microservice:1.1"
            }
        }

        stage("helm-chart"){
            steps{
                        sh 'pwd'
                        sh 'cp -R helm/* .'
						sh 'ls -ltr'
                        sh 'pwd'
						sh '/usr/local/bin/helm upgrade --install eureka-app eureka'
                        sh '/usr/local/bin/helm upgrade --install employee-app employee'
						sh '/usr/local/bin/helm upgrade --install attendance-app attendance'
						sh '/usr/local/bin/helm upgrade --install gateway-app gateway'
            }
        }
		
		stage("Kubernetes-check-pods"){
            steps{
				sh 'sudo docker image ls'
                sh 'helm list'
                sh 'kubectl get pods'
                sh 'kubectl get svc'
            }
        }
		
    }
 }
