DELETE FROM user;
DELETE FROM trade;
DELETE FROM coin;

ALTER TABLE user auto_increment = 0;
ALTER TABLE trade auto_increment = 0;
ALTER TABLE coin auto_increment = 0;

INSERT INTO hyperledgerfabrictransfer.coin
(coin_id, name, ISSUANCE, deleted)
VALUES(1, 'test', 1000, 0);

INSERT INTO hyperledgerfabrictransfer.`user`
(user_id, student_id, password, `role`, name, date_created, last_updated)
VALUES(1, 20170001, '$2a$10$j/Afbq3/i8LBz7u5/KYQOucfYgMRoyZOfYWX3c47gdPVyoSqNLf2e', 'ROLE_USER', 'test', '2022-07-08 10:20:22', '2022-07-08 10:20:22');

INSERT INTO hyperledgerfabrictransfer.`user`
(user_id, student_id, password, `role`, name, date_created, last_updated)
VALUES(2, 20170002, '$2a$10$N.w78Ut.hFdsfiNkn54jJ.iEaZPdjNPfVlJUHUZr2fcYebKE2QuCe', 'ROLE_USER', 'test', '2022-07-08 10:35:39', '2022-07-08 10:35:39');

INSERT INTO hyperledgerfabrictransfer.trade
(TRANSACTION_ID, trade_id, sender_id, received_user_id, received_shop_id, coin_id, amount, date_created, last_updated)
VALUES('2', 2, 1, 2, NULL, 1, 1, '2022-07-09 16:34:08', '2022-07-09 16:34:08');
INSERT INTO hyperledgerfabrictransfer.trade
(TRANSACTION_ID, trade_id, sender_id, received_user_id, received_shop_id, coin_id, amount, date_created, last_updated)
VALUES('3', 3, 1, 2, NULL, 1, 2, '2022-07-09 16:34:27', '2022-07-09 16:34:27');
INSERT INTO hyperledgerfabrictransfer.trade
(TRANSACTION_ID, trade_id, sender_id, received_user_id, received_shop_id, coin_id, amount, date_created, last_updated)
VALUES('4', 4, 1, 2, NULL, 1, 3, '2022-07-09 16:34:31', '2022-07-09 16:34:31');
INSERT INTO hyperledgerfabrictransfer.trade
(TRANSACTION_ID, trade_id, sender_id, received_user_id, received_shop_id, coin_id, amount, date_created, last_updated)
VALUES('5', 5, 1, 2, NULL, 1, 4, '2022-07-09 16:34:37', '2022-07-09 16:34:37');
INSERT INTO hyperledgerfabrictransfer.trade
(TRANSACTION_ID, trade_id, sender_id, received_user_id, received_shop_id, coin_id, amount, date_created, last_updated)
VALUES('6', 6, 1, 2, NULL, 1, 5, '2022-07-09 16:34:40', '2022-07-09 16:34:40');
INSERT INTO hyperledgerfabrictransfer.trade
(TRANSACTION_ID, trade_id, sender_id, received_user_id, received_shop_id, coin_id, amount, date_created, last_updated)
VALUES('7', 7, 1, 2, NULL, 1, 6, '2022-07-09 16:34:44', '2022-07-09 16:34:44');
INSERT INTO hyperledgerfabrictransfer.trade
(TRANSACTION_ID, trade_id, sender_id, received_user_id, received_shop_id, coin_id, amount, date_created, last_updated)
VALUES('8', 8, 1, 2, NULL, 1, 7, '2022-07-09 16:34:48', '2022-07-09 16:34:48');
INSERT INTO hyperledgerfabrictransfer.trade
(TRANSACTION_ID, trade_id, sender_id, received_user_id, received_shop_id, coin_id, amount, date_created, last_updated)
VALUES('9', 9, 1, 2, NULL, 1, 8, '2022-07-09 16:34:52', '2022-07-09 16:34:52');
INSERT INTO hyperledgerfabrictransfer.trade
(TRANSACTION_ID, trade_id, sender_id, received_user_id, received_shop_id, coin_id, amount, date_created, last_updated)
VALUES('10', 10, 1, 2, NULL, 1, 9, '2022-07-09 16:34:56', '2022-07-09 16:34:56');
INSERT INTO hyperledgerfabrictransfer.trade
(TRANSACTION_ID, trade_id, sender_id, received_user_id, received_shop_id, coin_id, amount, date_created, last_updated)
VALUES('11', 11, 1, 2, NULL, 1, 10, '2022-07-09 16:35:00', '2022-07-09 16:35:00');
INSERT INTO hyperledgerfabrictransfer.trade
(TRANSACTION_ID, trade_id, sender_id, received_user_id, received_shop_id, coin_id, amount, date_created, last_updated)
VALUES('12', 12, 1, 2, NULL, 1, 11, '2022-07-09 16:35:03', '2022-07-09 16:35:03');
INSERT INTO hyperledgerfabrictransfer.trade
(TRANSACTION_ID, trade_id, sender_id, received_user_id, received_shop_id, coin_id, amount, date_created, last_updated)
VALUES('13', 13, 1, 2, NULL, 1, 12, '2022-07-09 16:35:06', '2022-07-09 16:35:06');
INSERT INTO hyperledgerfabrictransfer.trade
(TRANSACTION_ID, trade_id, sender_id, received_user_id, received_shop_id, coin_id, amount, date_created, last_updated)
VALUES('14', 14, 1, 2, NULL, 1, 13, '2022-07-09 16:35:09', '2022-07-09 16:35:09');
INSERT INTO hyperledgerfabrictransfer.trade
(TRANSACTION_ID, trade_id, sender_id, received_user_id, received_shop_id, coin_id, amount, date_created, last_updated)
VALUES('15', 15, 1, 2, NULL, 1, 14, '2022-07-09 16:35:14', '2022-07-09 16:35:14');
INSERT INTO hyperledgerfabrictransfer.trade
(TRANSACTION_ID, trade_id, sender_id, received_user_id, received_shop_id, coin_id, amount, date_created, last_updated)
VALUES('16', 16, 1, 2, NULL, 1, 15, '2022-07-09 16:35:18', '2022-07-09 16:35:18');
INSERT INTO hyperledgerfabrictransfer.trade
(TRANSACTION_ID, trade_id, sender_id, received_user_id, received_shop_id, coin_id, amount, date_created, last_updated)
VALUES('17', 17, 1, 2, NULL, 1, 16, '2022-07-09 16:35:21', '2022-07-09 16:35:21');
INSERT INTO hyperledgerfabrictransfer.trade
(TRANSACTION_ID, trade_id, sender_id, received_user_id, received_shop_id, coin_id, amount, date_created, last_updated)
VALUES('18', 18, 1, 2, NULL, 1, 17, '2022-07-09 16:35:25', '2022-07-09 16:35:25');
INSERT INTO hyperledgerfabrictransfer.trade
(TRANSACTION_ID, trade_id, sender_id, received_user_id, received_shop_id, coin_id, amount, date_created, last_updated)
VALUES('19', 19, 1, 2, NULL, 1, 18, '2022-07-09 16:35:28', '2022-07-09 16:35:28');
INSERT INTO hyperledgerfabrictransfer.trade
(TRANSACTION_ID, trade_id, sender_id, received_user_id, received_shop_id, coin_id, amount, date_created, last_updated)
VALUES('20', 20, 1, 2, NULL, 1, 19, '2022-07-09 16:35:31', '2022-07-09 16:35:31');
INSERT INTO hyperledgerfabrictransfer.trade
(TRANSACTION_ID, trade_id, sender_id, received_user_id, received_shop_id, coin_id, amount, date_created, last_updated)
VALUES('21', 21, 1, 2, NULL, 1, 20, '2022-07-09 16:35:35', '2022-07-09 16:35:35');
INSERT INTO hyperledgerfabrictransfer.trade
(TRANSACTION_ID, trade_id, sender_id, received_user_id, received_shop_id, coin_id, amount, date_created, last_updated)
VALUES('22', 22, 1, 2, NULL, 1, 21, '2022-07-09 16:35:38', '2022-07-09 16:35:38');
INSERT INTO hyperledgerfabrictransfer.trade
(TRANSACTION_ID, trade_id, sender_id, received_user_id, received_shop_id, coin_id, amount, date_created, last_updated)
VALUES('23', 23, 2, 1, NULL, 1, 21, '2022-07-09 16:35:39', '2022-07-09 16:35:39');
INSERT INTO hyperledgerfabrictransfer.trade
(TRANSACTION_ID, trade_id, sender_id, received_user_id, received_shop_id, coin_id, amount, date_created, last_updated)
VALUES('24', 24, 2, 1, NULL, 1, 21, '2022-07-09 16:35:40', '2022-07-09 16:35:40');
INSERT INTO hyperledgerfabrictransfer.trade
(TRANSACTION_ID, trade_id, sender_id, received_user_id, received_shop_id, coin_id, amount, date_created, last_updated)
VALUES('25', 25, 2, 1, NULL, 1, 21, '2022-07-09 16:35:41', '2022-07-09 16:35:41');

select t.trade_id ,t.date_created
from trade t
where date_created between '2022-07-09 16:34:00' and '2022-07-09 16:35:00'
and t.trade_id = 2;
