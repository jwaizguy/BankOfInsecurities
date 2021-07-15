CREATE TABLE boi.TB_USER_CREDENTIALS
	(
	id varchar(15) NOT NULL,
	password varchar(15) NOT NULL,
	security_question varchar(50) NOT NULL,
	security_answer varchar(15) NOT NULL
	);
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

