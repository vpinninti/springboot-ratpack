
CREATE TABLE User(
	id bigint AUTO_INCREMENT,
	creation_date date,
	PRIMARY KEY (id)
	
);

CREATE TABLE Currency(
	id bigint AUTO_INCREMENT,
	code VARCHAR(10),
	PRIMARY KEY (id)
);
CREATE TABLE txn_account(
	id bigint AUTO_INCREMENT,
	user_id bigint,
	balance double,
	currency_id bigint,
	PRIMARY KEY (id),
    FOREIGN KEY (currency_id) REFERENCES Currency(id),
	FOREIGN KEY (user_id) REFERENCES User(id),
);


CREATE TABLE Transaction(
	id bigint AUTO_INCREMENT,
	txn_account_id bigint,
	user_id bigint,
	amount double,
	currency_id bigint,
	description VARCHAR(100),
	date date,
	PRIMARY KEY (id),
    FOREIGN KEY (currency_id) REFERENCES Currency(id),
	FOREIGN KEY (txn_account_id) REFERENCES txn_account(id),
	FOREIGN KEY (user_id) REFERENCES User(id),
);

INSERT INTO Currency(id,code) VALUES (1,'INR');