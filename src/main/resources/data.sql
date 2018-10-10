INSERT INTO USER (login, password, first_name, last_name, enabled) values ('user', '{noop}user', 'Ada', 'Nowak', true);
INSERT INTO USER(login, password, first_name, last_name, enabled) values ('admin', '{noop}admin', 'Iwona', 'Malec', true);

INSERT INTO USER_ROLE(login, role) values ('user', 'ROLE_USER');
INSERT INTO USER_ROLE(login, role) values ('admin', 'ROLE_ADMIN');
