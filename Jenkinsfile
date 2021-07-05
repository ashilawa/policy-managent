node{

   def tomcatWeb = 'C:\\installer\\Tomcat\\webapps'
   def tomcatBin = 'C:\\installer\\Tomcat\\bin'
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
     bat "copy policy-management-client\\target\\policy-management.war \"C:\\installer\\Tomcat\\webapps\\policy-management.war\""
   }
      stage ('Start Tomcat') {
         sleep(time:5,unit:"SECONDS") 
         bat "${tomcatBin}\\startup.bat"
         sleep(time:100,unit:"SECONDS")
   }
}