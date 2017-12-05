-- DATABASE
create database serviconvi;
-- USER
create user 'usr_servi'@'localhost' identified by 'S3rviconv1#';
-- PERMISSIONS
GRANT ALL ON serviconvi TO 'usr_servi'@'localhost';
FLUSH PRIVILEGES;
-- TABLE
create table cat_tipo_documento ( codigo int(4) unsigned auto_increment primary key,
descripcion varchar(50) );
