-- client_secret密码=secret new BCryptPasswordEncoder().encode("secret")
INSERT INTO oauth_client_details
	(client_id, client_secret, scope, authorized_grant_types,
	web_server_redirect_uri, authorities, access_token_validity,
	refresh_token_validity, additional_information, autoapprove)
VALUES
	('fooClientIdPassword', '{bcrypt}$2a$10$o49FhBnJHIfq8HBP0JMve./lN6lpvIkmux9H86.Y3jvaEvzdmGmxe', 'foo,read,write,openid',
	'password,authorization_code,refresh_token,client_credentials,implicit', 'http://localhost:9999/dashboard/login', 'ROLE_USER,ROLE_ANONYMOUS', 36000, 36000, null, 'openid');

INSERT INTO oauth_client_details
	(client_id, client_secret, scope, authorized_grant_types,
	web_server_redirect_uri, authorities, access_token_validity,
	refresh_token_validity, additional_information, autoapprove)
VALUES
	('sampleClientId', '{bcrypt}$2a$10$o49FhBnJHIfq8HBP0JMve./lN6lpvIkmux9H86.Y3jvaEvzdmGmxe', 'read,write,foo,bar,openid',
	'implicit', 'http://localhost:9090/auth/login', 'ROLE_USER,ROLE_ANONYMOUS', 36000, 36000, null, 'openid');

INSERT INTO oauth_client_details
	(client_id, client_secret, scope, authorized_grant_types,
	web_server_redirect_uri, authorities, access_token_validity,
	refresh_token_validity, additional_information, autoapprove)
VALUES
	('barClientIdPassword', '{bcrypt}$2a$10$o49FhBnJHIfq8HBP0JMve./lN6lpvIkmux9H86.Y3jvaEvzdmGmxe', 'bar,read,write,openid',
	'password,authorization_code,refresh_token,implicit', 'http://localhost:9999/dashboard/login', 'ROLE_USER,ROLE_ANONYMOUS', 36000, 36000, null, true);



