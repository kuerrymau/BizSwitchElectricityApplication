USE ipay_test_exercise;

CREATE TABLE meter (
	meter_id INT(18) NOT NULL AUTO_INCREMENT,
	meter_number VARCHAR(40) NOT NULL DEFAULT '',
	address VARCHAR(255) NULL DEFAULT '',
	enabled INT(4) NOT NULL DEFAULT 1,
	PRIMARY KEY (meter_id)
)
ENGINE=InnoDB
; 
INSERT INTO meter VALUES (NULL, 01060029608, '12 New Avenue, New World, Under Earth', 0);
INSERT INTO meter VALUES (NULL, 01075416576, '44 Old Avenue, Old World, Above Earth', 0);
	   
	CREATE TABLE pay_type (
	pay_type_id INT(18) NOT NULL AUTO_INCREMENT,
	name VARCHAR(30) NOT NULL DEFAULT '',
	description VARCHAR(255) NULL DEFAULT '',
	enabled INT(4) NOT NULL DEFAULT 1,
	PRIMARY KEY (pay_type_id)
)
ENGINE=InnoDB
;
INSERT INTO pay_type VALUES (NULL, 'Credit Card', 'Payment by Credit Card', 0);
INSERT INTO pay_type VALUES (NULL, 'Direct Deposit', 'Payment by Direct Deposit', 0);
	   
	CREATE TABLE elec_trans (
	elec_trans_id INT(18) NOT NULL AUTO_INCREMENT,
	meter_id INT(18) NOT NULL DEFAULT 1,
	pay_type_id INT(18) NOT NULL DEFAULT 1,
	token_number INT(18) NOT NULL DEFAULT 1,
	amount INT(18) NOT NULL DEFAULT 1,
	PRIMARY KEY (elec_trans_id),
	CONSTRAINT elec_trans_ibfk_1 FOREIGN KEY (meter_id) REFERENCES meter (meter_id),
	CONSTRAINT elec_trans_ibfk_2 FOREIGN KEY (pay_type_id) REFERENCES pay_type (pay_type_id)
)
ENGINE=InnoDB
;