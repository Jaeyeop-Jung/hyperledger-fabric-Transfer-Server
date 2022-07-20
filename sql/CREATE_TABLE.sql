DROP TABLE IF EXISTS storeimage ;
DROP TABLE IF EXISTS trade ;
DROP TABLE IF EXISTS store ;
DROP TABLE IF EXISTS STUDENT;
DROP TABLE IF EXISTS STOREMANGER;
DROP TABLE IF EXISTS ADMIN;
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
                     password VARCHAR(255) NOT NULL,
                     role VARCHAR(255) NOT NULL,
                     name VARCHAR(255) NOT NULL,
                     date_created datetime not null,
                     last_updated datetime not null,
                     PRIMARY KEY(user_id)
);

CREATE TABLE STUDENT(
                    user_id BIGINT NOT NULL ,
                    student_number VARCHAR(255) NOT NULL,
                    primary key (user_id),
                    foreign key (user_id) references user(user_id)

);

CREATE TABLE STOREMANGER(
                        user_id BIGINT NOT NULL ,
                        PHONE_NUMBER VARCHAR(255) NOT NULL,
                        primary key (user_id),
                        foreign key (user_id) references user(user_id)

);

CREATE TABLE ADMIN(
                        user_id BIGINT NOT NULL ,
                        primary key (user_id),
                        foreign key (user_id) references user(user_id)

);

CREATE TABLE store(
                     store_id BIGINT NOT NULL AUTO_INCREMENT ,
                     name VARCHAR(255) NOT NULL,
                     phone_number VARCHAR(255) NOT NULL,
                     address VARCHAR(255) NOT NULL,
                     date_created datetime not null,
                     last_updated datetime not null,
                     PRIMARY KEY(store_id)
);

CREATE TABLE trade(
                      trade_id BIGINT NOT NULL AUTO_INCREMENT,
                      TRANSACTION_ID VARCHAR(255) NOT NULL ,
                      sender_id BIGINT NOT NULL,
                      received_user_id BIGINT,
                      received_shop_id BIGINT,
                      coin_id BIGINT NOT NULL,
                      amount BIGINT NOT NULL,
                      date_created datetime not null,
                      last_updated datetime not null,
                      PRIMARY KEY(trade_id),
                      FOREIGN KEY(sender_id) REFERENCES user(user_id),
                      FOREIGN KEY(received_user_id) REFERENCES user(user_id),
                      FOREIGN KEY(received_shop_id) REFERENCES shop(shop_id),
                      FOREIGN KEY(coin_id) REFERENCES coin(coin_id)
);


CREATE TABLE storeimage(
                          storeimage_id BIGINT NOT NULL AUTO_INCREMENT ,
                          shop_id BIGINT NOT NULL,
                          name VARCHAR(255) NOT NULL,
                          size BIGINT NOT NULL,
                          extension VARCHAR(255) NOT NULL,
                          date_created datetime not null,
                          last_updated datetime not null,
                          PRIMARY KEY(storeimage_id),
                          FOREIGN KEY(storeimage_id) REFERENCES store(store_id)
);
