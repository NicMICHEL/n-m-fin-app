Poseidon Capital Solutions
A web application for financial transactions.

Prerequisites
This app uses Java to run and stores the data in Mysql DB. To install the software, you need Java 17 , Maven 3.9.6 , Mysql 8.0.34

After installing mysql, you will be asked to configure the password for the default root account. This code uses :
- the default root account to connect and the password can be set as rootrootA.1
- another account with admin as username and adminA.1 as password.

Running App
Post installation of MySQL, Java and Maven, you will have to set up the tables and data in the data base. For this, please run the sql commands present in the PCSDataBase.sql file under the resources folder in the code base. Two users are already registered in the database : user (password : aA!1user, role : USER), admin (password : aA!1admin, role : ADMIN). Only the ADMIN role can create, read, update and delete a user.

Testing
The app has unit tests written. To run the tests from maven, execute the following command : mvn test
