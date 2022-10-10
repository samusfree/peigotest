CREATE DATABASE peigobd;

use peigobd;

CREATE TABLE `account` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `account_number` varchar(255) DEFAULT NULL,
  `balance` decimal(19,2) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_account_account_number` (`account_number`)
);

CREATE TABLE `transaction` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `amount` decimal(19,2) DEFAULT NULL,
  `destination_account_balance_after_transaction` decimal(19,2) DEFAULT NULL,
  `destination_account_balance_before_transaction` decimal(19,2) DEFAULT NULL,
  `destination_account_number` varchar(255) DEFAULT NULL,
  `origin_account_balance_after_transaction` decimal(19,2) DEFAULT NULL,
  `origin_account_balance_before_transaction` decimal(19,2) DEFAULT NULL,
  `origin_account_number` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
);

