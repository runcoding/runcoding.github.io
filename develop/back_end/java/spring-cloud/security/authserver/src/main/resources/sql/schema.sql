drop table if exists oauth_client_details;
create table oauth_client_details (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  client_id VARCHAR(255) ,
  resource_ids VARCHAR(255),
  client_secret VARCHAR(255),
  scope VARCHAR(255),
  authorized_grant_types VARCHAR(255),
  web_server_redirect_uri VARCHAR(255),
  authorities VARCHAR(255),
  access_token_validity INTEGER,
  refresh_token_validity INTEGER,
  additional_information VARCHAR(4096),
  autoapprove VARCHAR(255),
  PRIMARY KEY (`id`) USING BTREE,
  KEY `client_id` (`client_id`)
);

create table if not exists oauth_client_token (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  token_id VARCHAR(255),
  token LONG VARBINARY,
  authentication_id VARCHAR(255),
  user_name VARCHAR(255),
  client_id VARCHAR(255),
  PRIMARY KEY (`id`) USING BTREE,
  KEY `client_id` (`client_id`),
  KEY `authentication_id` (`authentication_id`)
);

create table if not exists oauth_access_token (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  token_id VARCHAR(255),
  token LONG VARBINARY,
  authentication_id VARCHAR(255),
  user_name VARCHAR(255),
  client_id VARCHAR(255),
  authentication LONG VARBINARY,
  refresh_token VARCHAR(255),
  PRIMARY KEY (`id`) USING BTREE,
  KEY `client_id` (`client_id`),
  KEY `authentication_id` (`authentication_id`)
);

create table if not exists oauth_refresh_token (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  token_id VARCHAR(255),
  token LONG VARBINARY,
  authentication LONG VARBINARY,
  PRIMARY KEY (`id`) USING BTREE
);

create table if not exists oauth_code (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  code VARCHAR(255),
  authentication LONG VARBINARY,
  PRIMARY KEY (`id`) USING BTREE
);

create table if not exists oauth_approvals (
    id bigint(20) NOT NULL AUTO_INCREMENT,
	userId VARCHAR(255),
	clientId VARCHAR(255),
	scope VARCHAR(255),
	status VARCHAR(10),
	expiresAt TIMESTAMP,
	lastModifiedAt  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	PRIMARY KEY (`id`) USING BTREE,
    KEY `clientId` (`clientId`)
);

create table if not exists ClientDetails (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  appId VARCHAR(255),
  resourceIds VARCHAR(255),
  appSecret VARCHAR(255),
  scope VARCHAR(255),
  grantTypes VARCHAR(255),
  redirectUrl VARCHAR(255),
  authorities VARCHAR(255),
  access_token_validity INTEGER,
  refresh_token_validity INTEGER,
  additionalInformation VARCHAR(4096),
  autoApproveScopes VARCHAR(255),
	PRIMARY KEY (`id`) USING BTREE,
  KEY `appId` (`appId`)
);