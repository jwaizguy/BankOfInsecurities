CREATE TABLE boi.TB_USER_MESSAGES
	(
	msgID varchar(15) PRIMARY KEY NOT NULL,
	subject varchar(250),
	msg varchar(1000),
	sender varchar(25),
	fileName varchar(25)
	);