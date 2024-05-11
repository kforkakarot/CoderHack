# CoderHack
Spring boot API's  for CorderHack


#Installing Java
Refer to the below document for easy installation
https://www3.cs.stonybrook.edu/~amione/CSE114_Course/materials/resources/InstallingJava17.pdf


#Installing mongoDB
Refer to the following website for installing MongoDB
https://www.geeksforgeeks.org/how-to-install-mongodb-on-windows/

#environment SetUp
once You have Installed the Java and MongoDB 
1) Import the project using Maven Build in one of the IDE (STS, Intellij, Eclipse)
2) create a collection in MongoDB using MongoDB compass with name as "mydb"
3) check if the application.properties file has the following set of lines
   spring.data.mongodb.host=localhost
   
   spring.data.mongodb.port=27017
   
   spring.data.mongodb.database=mydb

#run
1) Once this is done run the application using your ide

2) You can also use terminal or cmd commands to run you application on windows / linus / mac.