DROP TABLE IF EXISTS storeimage ;
DROP TABLE IF EXISTS trade ;
DROP TABLE IF EXISTS store ;
DROP TABLE IF EXISTS admin;
DROP TABLE IF EXISTS user;
DROP TABLE IF EXISTS coin;

CREATE TABLE coin(
                     coin_id BIGINT NOT NULL AUTO_INCREMENT ,
                     name VARCHAR(255) NOT NULL,
                     ISSUANCE BIGINT NOT NULL,
                     deleted BOOL NOT NULL,
                     PRIMARY KEY(coin_id),
                     UNIQUE KEY(name)
);

CREATE TABLE user(
                     user_id BIGINT NOT NULL AUTO_INCREMENT,
                     identifier VARCHAR(255) NOT NULL ,
                     password VARCHAR(255) NOT NULL,
                     role VARCHAR(255) NOT NULL,
                     name VARCHAR(255) NOT NULL,
                     date_created datetime not null,
                     last_updated datetime not null,
                     PRIMARY KEY(user_id),
                     unique key (identifier)
);

CREATE TABLE admin(
                    admin_id BIGINT NOT NULL AUTO_INCREMENT,
                    email varchar(255) NOT NULL,
                    password varchar(255) not null,
                    date_created datetime not null,
                    last_updated datetime not null,
                    primary key (admin_id),
                    unique key (email)

);

CREATE TABLE store(
                     store_id BIGINT NOT NULL AUTO_INCREMENT ,
                     name VARCHAR(255) NOT NULL,
                     phone_number VARCHAR(255) NOT NULL,
                     address VARCHAR(255) NOT NULL,
                     storeimage_id BIGINT,
                     date_created datetime not null,
                     last_updated datetime not null,
                     PRIMARY KEY(store_id),
                     foreign key(storeimage_id) references storeimage(storeimage_id)
);

CREATE TABLE trade(
                      trade_id BIGINT NOT NULL AUTO_INCREMENT,
                      TRANSACTION_ID VARCHAR(255) NOT NULL ,
                      sender_id BIGINT NOT NULL,
                      receiver_id BIGINT NOT NULL ,
                      coin_id BIGINT NOT NULL,
                      amount BIGINT NOT NULL,
                      date_created datetime not null,
                      last_updated datetime not null,
                      PRIMARY KEY(trade_id),
                      FOREIGN KEY(sender_id) REFERENCES user(user_id),
                      FOREIGN KEY(receiver_id) REFERENCES user(user_id),
                      FOREIGN KEY(coin_id) REFERENCES coin(coin_id)
);


CREATE TABLE storeimage(
                          storeimage_id BIGINT NOT NULL AUTO_INCREMENT ,
                          name VARCHAR(255) NOT NULL,
                          size BIGINT NOT NULL,
                          extension VARCHAR(255) NOT NULL,
                          date_created datetime not null,
                          last_updated datetime not null,
                          PRIMARY KEY(storeimage_id)
);
