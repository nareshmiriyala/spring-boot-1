CREATE DATABASE clientdb;
CREATE USER 'clientdb'@'localhost' IDENTIFIED BY '123';
GRANT ALL PRIVILEGES ON clientdb.* TO 'clientdb'@'localhost';
use clientdb;
create table client_user(
	id bigint auto_increment primary key,
    username varchar(100),
    password varchar(255),
    access_token varchar(100) null,
    access_token_validity datetime null,
    refresh_token varchar(100) null
);
insert into client_user (username, password) value ('aeloy', 'abc');
insert into client_user (username, password) value ('user', 'user');