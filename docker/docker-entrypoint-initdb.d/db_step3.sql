CREATE TABLE boi.TB_ROLES
	(
	roleID varchar(1) PRIMARY KEY NOT NULL,
	roleDescription varchar(100)
	);
INSERT INTO boi.TB_ROLES(roleID, roleDescription)
		VALUES('A', 'Admin User with Super privileges');	
INSERT INTO boi.TB_ROLES(roleID, roleDescription)
	VALUES('N', 'Normal user with limited privileges');

