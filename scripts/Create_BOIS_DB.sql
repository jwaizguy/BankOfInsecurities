--> I get the following line from running createdb with '-e'
SELECT pg_catalog.set_config('search_path', '', false);
\c postgres

--> Create a new database for BOIS
DROP DATABASE IF EXISTS cigital_boi;
CREATE DATABASE cigital_boi;

--> Connect to the new database
\c cigital_boi

--> Create a new user 
DROP ROLE IF EXISTS boi;
CREATE ROLE boi SUPERUSER CREATEDB CREATEROLE INHERIT LOGIN;
ALTER USER boi WITH ENCRYPTED PASSWORD 'boi123';

--> Granting permissions to the new user - boi
GRANT CONNECT, CREATE ON DATABASE cigital_boi TO boi;

--> Create a new schema (boi) for the user - boi
CREATE SCHEMA AUTHORIZATION boi;

--> Create a test table called TEST
CREATE TABLE boi.TEST 
	(
	id varchar(10) NOT NULL, 
	password varchar(15) NOT NULL
	);

--> Insert some records to test the TEST table.
INSERT INTO boi.TEST(id, password) 
	VALUES ('admin', 'adminadmin');

--> Create a table to store login credentials
CREATE TABLE boi.TB_USER_CREDENTIALS
	(
	id varchar(15) NOT NULL,
	password varchar(15) NOT NULL,
	security_question varchar(50) NOT NULL,
	security_answer varchar(15) NOT NULL
	);

--> Populate the login table
INSERT INTO boi.TB_USER_CREDENTIALS(id, password, security_question, security_answer)
	VALUES('john_doe', 'john#doe', 'In which city were you born?', 'Madrid');
INSERT INTO boi.TB_USER_CREDENTIALS(id, password, security_question, security_answer)
	VALUES('jane_doe', 'jane#doe', 'Which is your favorite sports team?', 'ManU');
INSERT INTO boi.TB_USER_CREDENTIALS(id, password, security_question, security_answer)
	VALUES('tom_walker', 'tom@walker', 'Which is your favorite color?', 'turquoise');
INSERT INTO boi.TB_USER_CREDENTIALS(id, password, security_question, security_answer)
	VALUES('emilia_parker', 'emilia$parker', 'In which city were you born?', 'LosAngeles');
INSERT INTO boi.TB_USER_CREDENTIALS(id, password, security_question, security_answer)
	VALUES('zoe_bell', 'zoe#bell', 'Which is your favorite sports team?', 'Chicago Cubs');
INSERT INTO boi.TB_USER_CREDENTIALS(id, password, security_question, security_answer)
	VALUES('Patrick Kisner', 'patrick$kisner', 'In which city were you born?', 'Bloomington');
INSERT INTO boi.TB_USER_CREDENTIALS(id, password, security_question, security_answer)
	VALUES('Neil S', 'neil#s', 'Which is your favorite color?', 'beige');
INSERT INTO boi.TB_USER_CREDENTIALS(id, password, security_question, security_answer)
	VALUES('Sandra Wilkie', 'sandra#wilk', 'In which city were you born?', 'Austin');
INSERT INTO boi.TB_USER_CREDENTIALS(id, password, security_question, security_answer)
	VALUES('Nick Solomon', 'nick$solomon', 'Which is your favorite sports team?', 'Lakers');
INSERT INTO boi.TB_USER_CREDENTIALS(id, password, security_question, security_answer)
	VALUES('Mike Gasser', 'mike#gasser', 'Which is your favorite sports team?', 'Houston Rockets');

--> CREATE a Roles table to assign different privileges to users
CREATE TABLE boi.TB_ROLES
	(
	roleID varchar(1) PRIMARY KEY NOT NULL,
	roleDescription varchar(100)
	);
	
--> Insert privilege data
--> admin
INSERT INTO boi.TB_ROLES(roleID, roleDescription)
	VALUES('A', 'Admin User with Super privileges');
--> normal user
INSERT INTO boi.TB_ROLES(roleID, roleDescription)
	VALUES('N', 'Normal user with limited privileges');

--> Create a user table which will store the user information
CREATE TABLE boi.TB_USER_DETAILS
	(
	userID varchar(15) PRIMARY KEY NOT NULL,
	fName varchar(15) NOT NULL,
	lName varchar(15) NOT NULL,
	ssn varchar(9) NOT NULL,
	emailID varchar(255) NOT NULL,
	contact varchar(20) NOT NULL,
	address varchar(255),
	roleID varchar(1) REFERENCES boi.TB_ROLES(roleID) NOT NULL
	);

--> Populate user information
INSERT INTO boi.TB_USER_DETAILS(userID, fName, lName, ssn, emailID, contact, address, roleID)
	VALUES('john_doe', 'John', 'Doe', '123987011', 'johndoe@gmail.com', '8127895500', 'Bloomington, IN', 'A');
INSERT INTO boi.TB_USER_DETAILS(userID, fName, lName, ssn, emailID, contact, address, roleID)
	VALUES('jane_doe', 'Jane', 'Doe', '784391245', 'janedoe@yahoo.com', '8121230099', 'Bloomington, IN', 'N');
INSERT INTO boi.TB_USER_DETAILS(userID, fName, lName, ssn, emailID, contact, address, roleID)
	VALUES('tom_walker', 'Tom', 'Walker', '654324987', 'tomwalker@hotmail.com', '6345687777', 'Chicago, IL', 'N');
INSERT INTO boi.TB_USER_DETAILS(userID, fName, lName, ssn, emailID, contact, address, roleID)
	VALUES('emilia_parker', 'Emilia', 'Parker', '098654395', 'emiliap@gmail.com', '7758043307', 'Austin, TX', 'N');
INSERT INTO boi.TB_USER_DETAILS(userID, fName, lName, ssn, emailID, contact, address, roleID)
	VALUES('zoe_bell', 'Zoe', 'Bell', '657382147', 'zoe_bell@gmail.com', '6658282364', 'Indianapolis, IN', 'A');
INSERT INTO boi.TB_USER_DETAILS(userID, fName, lName, ssn, emailID, contact, address, roleID)
	VALUES('patrick_kisner', 'Patrick', 'Kisner', '354974312', 'kpatrick@gmail.com', '654895308', 'Louisville, KY', 'N');
INSERT INTO boi.TB_USER_DETAILS(userID, fName, lName, ssn, emailID, contact, address, roleID)
	VALUES('neil_s', 'Neil', 'S', '312684005', 'neils@hotmail.com', '8197944532', 'Chicago, IL', 'A');
INSERT INTO boi.TB_USER_DETAILS(userID, fName, lName, ssn, emailID, contact, address, roleID)
	VALUES('sandra_wilkie', 'Sandra', 'Wilkie', '012643992', 'swilkie@gmail.com', '8126788898', 'Chicago, IL', 'A');
INSERT INTO boi.TB_USER_DETAILS(userID, fName, lName, ssn, emailID, contact, address, roleID)
	VALUES('nick_solomon', 'Nick', 'Solomon', '478963412', 'nsolomon@yahoo.com', '6723864444', 'Peoria, IL', 'N');
INSERT INTO boi.TB_USER_DETAILS(userID, fName, lName, ssn, emailID, contact, address, roleID)
	VALUES('mike_gasser', 'Mike', 'Gasser', '012643992', 'mikegasser@hotmail.com', '3127755489', 'Bloomington, IN', 'N');

--> Create an account table to maintain all the customer accounts that our bank handles
CREATE TABLE boi.TB_ACCOUNTS
	(
	accountID BIGINT PRIMARY KEY NOT NULL,
	accountType char(1) NOT NULL,
	userID varchar(15) REFERENCES boi.TB_USER_DETAILS(userID) NOT NULL,
	accountBalance real NOT NULL,
	created date NOT NULL,
	isActive boolean NOT NULL
	);


/* Please create this sequence every time the TB_ACCOUNTS is dropped and created. */
--> Creating a sequence to auto-generate the accountID
CREATE SEQUENCE boi.TS_ACCOUNTID MINVALUE 1000000000 START 5555555555 OWNED BY boi.TB_ACCOUNTS.accountID;

--> Populate with user data
INSERT INTO boi.TB_ACCOUNTS(accountID, accountType, userID, accountBalance, created, isActive)
	VALUES(nextval('boi.TS_ACCOUNTID'), 'C', 'john_doe', 100.00, '2016-02-24', TRUE);
INSERT INTO boi.TB_ACCOUNTS(accountID, accountType, userID, accountBalance, created, isActive)
	VALUES(nextval('boi.TS_ACCOUNTID'), 'S', 'john_doe', 1000.00, '2016-02-24', TRUE);
INSERT INTO boi.TB_ACCOUNTS(accountID, accountType, userID, accountBalance, created, isActive)
	VALUES(nextval('boi.TS_ACCOUNTID'), 'C', 'jane_doe', 100.00, '2016-02-24', TRUE);
INSERT INTO boi.TB_ACCOUNTS(accountID, accountType, userID, accountBalance, created, isActive)
	VALUES(nextval('boi.TS_ACCOUNTID'), 'S', 'tom_walker', 500.00, '2016-02-24', TRUE);
INSERT INTO boi.TB_ACCOUNTS(accountID, accountType, userID, accountBalance, created, isActive)
	VALUES(nextval('boi.TS_ACCOUNTID'), 'C', 'tom_walker', 300.00, '2016-02-24', TRUE);
INSERT INTO boi.TB_ACCOUNTS(accountID, accountType, userID, accountBalance, created, isActive)
	VALUES(nextval('boi.TS_ACCOUNTID'), 'C', 'emilia_parker', 250.00, '2016-02-24', TRUE);
INSERT INTO boi.TB_ACCOUNTS(accountID, accountType, userID, accountBalance, created, isActive)
	VALUES(nextval('boi.TS_ACCOUNTID'), 'C', 'zoe_bell', 2500.00, '2016-02-24', TRUE);
INSERT INTO boi.TB_ACCOUNTS(accountID, accountType, userID, accountBalance, created, isActive)
	VALUES(nextval('boi.TS_ACCOUNTID'), 'C', 'patrick_kisner', 6500.00, '2016-02-24', TRUE);
INSERT INTO boi.TB_ACCOUNTS(accountID, accountType, userID, accountBalance, created, isActive)
	VALUES(nextval('boi.TS_ACCOUNTID'), 'S', 'patrick_kisner', 8000.00, '2016-02-24', TRUE);
INSERT INTO boi.TB_ACCOUNTS(accountID, accountType, userID, accountBalance, created, isActive)
	VALUES(nextval('boi.TS_ACCOUNTID'), 'C', 'neil_s', 1800.00, '2016-02-24', TRUE);
INSERT INTO boi.TB_ACCOUNTS(accountID, accountType, userID, accountBalance, created, isActive)
	VALUES(nextval('boi.TS_ACCOUNTID'), 'C', 'sandra_wilkie', 3300.00, '2016-02-24', TRUE);
INSERT INTO boi.TB_ACCOUNTS(accountID, accountType, userID, accountBalance, created, isActive)
	VALUES(nextval('boi.TS_ACCOUNTID'), 'S', 'sandra_wilkie', 5800.00, '2016-02-24', TRUE);
INSERT INTO boi.TB_ACCOUNTS(accountID, accountType, userID, accountBalance, created, isActive)
	VALUES(nextval('boi.TS_ACCOUNTID'), 'C', 'nick_solomon', 850.00, '2016-02-24', TRUE);
INSERT INTO boi.TB_ACCOUNTS(accountID, accountType, userID, accountBalance, created, isActive)
	VALUES(nextval('boi.TS_ACCOUNTID'), 'S', 'nick_solomon', 7310.00, '2016-02-24', TRUE);
INSERT INTO boi.TB_ACCOUNTS(accountID, accountType, userID, accountBalance, created, isActive)
	VALUES(nextval('boi.TS_ACCOUNTID'), 'C', 'nick_solomon', 3400.00, '2016-02-24', TRUE);
INSERT INTO boi.TB_ACCOUNTS(accountID, accountType, userID, accountBalance, created, isActive)
	VALUES(nextval('boi.TS_ACCOUNTID'), 'C', 'mike_gasser', 1700.00, '2016-02-24', TRUE);


--> Create a transaction table to save all the customer transactions
CREATE TABLE boi.TB_TRANSACTIONS
	(
	transactionID varchar(20) PRIMARY KEY NOT NULL,
	/* 
	The transactionID will be a combination of following:
	"TX" -- Fixed Static String;
	"MMDDYY" -- Date;
	"UUID" -- Random value generated from Java Server
	*/
	userID varchar(15) REFERENCES boi.TB_USER_DETAILS(userID) NOT NULL,
	transactionType char(1) NOT NULL,
	transactionWith varchar(30) NOT NULL,
	transactionAmount real NOT NULL,
	transactionTime timestamp without time zone NOT NULL DEFAULT NOW()
	);

--> Inserting dummy transaction data

INSERT INTO boi.TB_TRANSACTIONS(transactionID, userID, transactionType, transactionWith, transactionAmount)
	VALUES('TX022416046B6C7F', 'john_doe', 'C', 'Jane Doe', 25.99);


--> Create a table to store user feedback messages
CREATE TABLE boi.TB_USER_MESSAGES
	(
	msgID varchar(15) PRIMARY KEY NOT NULL,
	subject varchar(250),
	msg varchar(1000),
	sender varchar(25),
	fileName varchar(25)
	);

