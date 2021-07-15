CREATE TABLE boi.TB_ACCOUNTS
	(
	accountID BIGINT PRIMARY KEY NOT NULL,
	accountType char(1) NOT NULL,
	userID varchar(15) REFERENCES boi.TB_USER_DETAILS(userID) NOT NULL,
	accountBalance real NOT NULL,
	created date NOT NULL,
	isActive boolean NOT NULL
	);

CREATE SEQUENCE TS_ACCOUNTID MINVALUE 1000000000 START 5555555555 OWNED BY TB_ACCOUNTS.accountID;

INSERT INTO boi.TB_ACCOUNTS(accountID, accountType, userID, accountBalance, created, isActive)
		VALUES(nextval('TS_ACCOUNTID'), 'C', 'john_doe', 100.00, '2016-02-24', TRUE);
INSERT INTO boi.TB_ACCOUNTS(accountID, accountType, userID, accountBalance, created, isActive)
	VALUES(nextval('TS_ACCOUNTID'), 'S', 'john_doe', 1000.00, '2016-02-24', TRUE);
INSERT INTO boi.TB_ACCOUNTS(accountID, accountType, userID, accountBalance, created, isActive)
	VALUES(nextval('TS_ACCOUNTID'), 'C', 'jane_doe', 100.00, '2016-02-24', TRUE);
INSERT INTO boi.TB_ACCOUNTS(accountID, accountType, userID, accountBalance, created, isActive)
	VALUES(nextval('TS_ACCOUNTID'), 'S', 'tom_walker', 500.00, '2016-02-24', TRUE);
INSERT INTO boi.TB_ACCOUNTS(accountID, accountType, userID, accountBalance, created, isActive)
	VALUES(nextval('TS_ACCOUNTID'), 'C', 'tom_walker', 300.00, '2016-02-24', TRUE);
INSERT INTO boi.TB_ACCOUNTS(accountID, accountType, userID, accountBalance, created, isActive)
	VALUES(nextval('TS_ACCOUNTID'), 'C', 'emilia_parker', 250.00, '2016-02-24', TRUE);
INSERT INTO boi.TB_ACCOUNTS(accountID, accountType, userID, accountBalance, created, isActive)
	VALUES(nextval('TS_ACCOUNTID'), 'C', 'zoe_bell', 2500.00, '2016-02-24', TRUE);
INSERT INTO boi.TB_ACCOUNTS(accountID, accountType, userID, accountBalance, created, isActive)
	VALUES(nextval('TS_ACCOUNTID'), 'C', 'patrick_kisner', 6500.00, '2016-02-24', TRUE);
INSERT INTO boi.TB_ACCOUNTS(accountID, accountType, userID, accountBalance, created, isActive)
	VALUES(nextval('TS_ACCOUNTID'), 'S', 'patrick_kisner', 8000.00, '2016-02-24', TRUE);
INSERT INTO boi.TB_ACCOUNTS(accountID, accountType, userID, accountBalance, created, isActive)
	VALUES(nextval('TS_ACCOUNTID'), 'C', 'neil_s', 1800.00, '2016-02-24', TRUE);
INSERT INTO boi.TB_ACCOUNTS(accountID, accountType, userID, accountBalance, created, isActive)
	VALUES(nextval('TS_ACCOUNTID'), 'C', 'sandra_wilkinson', 3300.00, '2016-02-24', TRUE);
INSERT INTO boi.TB_ACCOUNTS(accountID, accountType, userID, accountBalance, created, isActive)
	VALUES(nextval('TS_ACCOUNTID'), 'S', 'sandra_wilkinson', 5800.00, '2016-02-24', TRUE);
INSERT INTO boi.TB_ACCOUNTS(accountID, accountType, userID, accountBalance, created, isActive)
	VALUES(nextval('TS_ACCOUNTID'), 'C', 'nick_solomon', 850.00, '2016-02-24', TRUE);
INSERT INTO boi.TB_ACCOUNTS(accountID, accountType, userID, accountBalance, created, isActive)
	VALUES(nextval('TS_ACCOUNTID'), 'S', 'nick_solomon', 7310.00, '2016-02-24', TRUE);
INSERT INTO boi.TB_ACCOUNTS(accountID, accountType, userID, accountBalance, created, isActive)
	VALUES(nextval('TS_ACCOUNTID'), 'C', 'nick_solomon', 3400.00, '2016-02-24', TRUE);
INSERT INTO boi.TB_ACCOUNTS(accountID, accountType, userID, accountBalance, created, isActive)
	VALUES(nextval('TS_ACCOUNTID'), 'C', 'mike_gasser', 1700.00, '2016-02-24', TRUE);