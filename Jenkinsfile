node{

   def tomcatWeb = 'C:\\Program Files\\Apache Software Foundation\\Tomcat 9.0\\webapps'
   def tomcatBin = 'C:\\Program Files\\Apache Software Foundation\\Tomcat 9.0\\bin'
   def tomcatStatus = ''
   stage('Checkout'){
     git 'https://github.com/ashilawa/policy-managent.git'
   }


   stage('Compile-Package-create-war-file'){
      // Get maven home path
      def mvnHome =  tool name: 'maven-3', type: 'maven'   
      bat "${mvnHome}/bin/mvn package"
      }

   stage('Deploy'){
     bat "copy policy-management-client\\target\\policy-management.war \"C:\\Program Files\\Apache Software Foundation\\Tomcat 9.0\\webapps\\policy-management.war\""
   }
      stage ('Start Tomcat') {
         sleep(time:5,unit:"SECONDS") 
         bat "${tomcatBin}\\startup.bat"
         sleep(time:100,unit:"SECONDS")
   }
}