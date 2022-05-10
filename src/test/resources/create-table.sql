DROP TABLE IF EXISTS coin;
DROP TABLE IF EXISTS user;
DROP TABLE IF EXISTS usertrade ;
DROP TABLE IF EXISTS shop ;
DROP TABLE IF EXISTS shopimage ;
DROP TABLE IF EXISTS shoptrade ;

CREATE TABLE coin(
	coin_id BIGINT NOT NULL AUTO_INCREMENT ,
	name VARCHAR(255) NOT NULL,
	deleted BOOL NOT NULL,
	PRIMARY KEY(coin_id),
	UNIQUE KEY(name)
);

CREATE TABLE user(
	user_id BIGINT NOT NULL AUTO_INCREMENT,
	student_id BIGINT NOT NULL,
	password VARCHAR(255) NOT NULL,
	role VARCHAR(255) NOT NULL,
	name VARCHAR(255) NOT NULL,
	date_created datetime not null,
	last_updated datetime not null,
	PRIMARY KEY(user_id),
	UNIQUE KEY(student_id)
);

CREATE TABLE usertrade( 
	usertrade_id BIGINT NOT NULL AUTO_INCREMENT,
	sender_id BIGINT NOT NULL,
	receiver_id BIGINT NOT NULL,
	coin_id BIGINT NOT NULL,
	amount BIGINT NOT NULL,
	date_created datetime not null,
	last_updated datetime not null,
	PRIMARY KEY(usertrade_id),
	FOREIGN KEY(sender_id) REFERENCES user(user_id),
	FOREIGN KEY(receiver_id) REFERENCES user(user_id),
	FOREIGN KEY(coin_id) REFERENCES coin(coin_id)
);


CREATE TABLE shop(
	shop_id BIGINT NOT NULL AUTO_INCREMENT ,
	name VARCHAR(255) NOT NULL,
	phone_number VARCHAR(255) NOT NULL,
	address VARCHAR(255) NOT NULL,
	date_created datetime not null,
	last_updated datetime not null,
	PRIMARY KEY(shop_id)
);

CREATE TABLE shopimage( 
	shopimage_id BIGINT NOT NULL AUTO_INCREMENT ,
	shop_id BIGINT NOT NULL,
	name VARCHAR(255) NOT NULL,
	size BIGINT NOT NULL,
	extension VARCHAR(255) NOT NULL,
	date_created datetime not null,
	last_updated datetime not null,
	PRIMARY KEY(SHOPIMAGE_ID),
	FOREIGN KEY(shop_id) REFERENCES shop(shop_id)
);

CREATE TABLE shoptrade(
	shoptrade_id BIGINT NOT NULL AUTO_INCREMENT ,
	sender_id BIGINT NOT NULL,
	shop_id BIGINT NOT NULL,
	coin_id BIGINT NOT NULL,
	amount BIGINT NOT NULL,
	date_created datetime not null,
	last_updated datetime not null,
	PRIMARY KEY(shoptrade_id),
	FOREIGN KEY(sender_id) REFERENCES user(user_id),
	FOREIGN KEY(shop_id) REFERENCES shop(shop_id),
	FOREIGN KEY(coin_id) REFERENCES coin(coin_id)
);