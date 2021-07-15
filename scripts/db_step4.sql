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