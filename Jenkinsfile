node('vagrant-vm'){
   def mvnHome
   stage('Git Clone') { // for display purposes
      // Get some code from a GitHub repository
      git 'https://github.com/samit2040/carthage.git'
      // Get the Maven tool.
      // ** NOTE: This 'M3' Maven tool must be configured
      // **       in the global configuration.           
     // mvnHome = tool 'M3'
   }
   stage('Build and Test') {
          // Run the maven build
        try{
          if (isUnix()) {
             sh "mvn clean package"
            }
          }catch(err){
                junit '**/target/surefire-reports/TEST-*.xml'
                archive '**/target/*.war'
                throw err
           }
       }
   stage('Archive Results') {
      junit '**/target/surefire-reports/TEST-*.xml'
      archive '**/target/*.war'
   }
   stage('Build and Publish DockerImage') {
      if (isUnix()) {
         sh "./buildDockerImage.sh"
      }
   }
}
