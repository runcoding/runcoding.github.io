DROP DATABASE IF EXISTS db_sh;
DROP TABLE IF EXISTS db_sh.tick_a_2017_01;
CREATE TABLE db_sh.tick_a_2017_01
(
  id BIGINT(20) PRIMARY KEY NOT NULL,
  name VARCHAR(2),
  exchange VARCHAR(10),
  ask INT(11),
  bid INT(11),
  time DATETIME
);
DROP TABLE IF EXISTS db_sh.tick_a_2017_02;
CREATE TABLE db_sh.tick_a_2017_02
(
  id BIGINT(20) PRIMARY KEY NOT NULL,
  name VARCHAR(2),
  exchange VARCHAR(10),
  ask INT(11),
  bid INT(11),
  time DATETIME
);
DROP TABLE IF EXISTS db_sh.tick_b_2017_01;
CREATE TABLE db_sh.tick_b_2017_01
(
  id BIGINT(20) PRIMARY KEY NOT NULL,
  name VARCHAR(2),
  exchange VARCHAR(10),
  ask INT(11),
  bid INT(11),
  time DATETIME
);
DROP TABLE IF EXISTS db_sh.tick_b_2017_02;
CREATE TABLE db_sh.tick_b_2017_02
(
  id BIGINT(20) PRIMARY KEY NOT NULL,
  name VARCHAR(2),
  exchange VARCHAR(10),
  ask INT(11),
  bid INT(11),
  time DATETIME
);
DROP DATABASE IF EXISTS db_sz;
DROP TABLE IF EXISTS db_sz.tick_a_2017_01;
CREATE TABLE db_sz.tick_a_2017_01
(
  id BIGINT(20) PRIMARY KEY NOT NULL,
  name VARCHAR(2),
  exchange VARCHAR(10),
  ask INT(11),
  bid INT(11),
  time DATETIME
);
DROP TABLE IF EXISTS db_sz.tick_a_2017_02;
CREATE TABLE db_sz.tick_a_2017_02
(
  id BIGINT(20) PRIMARY KEY NOT NULL,
  name VARCHAR(2),
  exchange VARCHAR(10),
  ask INT(11),
  bid INT(11),
  time DATETIME
);
DROP TABLE IF EXISTS db_sz.tick_b_2017_01;
CREATE TABLE db_sz.tick_b_2017_01
(
  id BIGINT(20) PRIMARY KEY NOT NULL,
  name VARCHAR(2),
  exchange VARCHAR(10),
  ask INT(11),
  bid INT(11),
  time DATETIME
);
DROP TABLE IF EXISTS db_sz.tick_b_2017_02;
CREATE TABLE db_sz.tick_b_2017_02
(
  id BIGINT(20) PRIMARY KEY NOT NULL,
  name VARCHAR(2),
  exchange VARCHAR(10),
  ask INT(11),
  bid INT(11),
  time DATETIME
);