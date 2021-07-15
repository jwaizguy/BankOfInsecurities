CREATE TABLE boi.TB_TRANSACTIONS
	(
	transactionID varchar(20) PRIMARY KEY NOT NULL,
	userID varchar(15) REFERENCES boi.TB_USER_DETAILS(userID) NOT NULL,
	transactionType char(1) NOT NULL,
	transactionWith varchar(30) NOT NULL,
	transactionAmount real NOT NULL,
	transactionTime timestamp without time zone NOT NULL DEFAULT NOW()
	);