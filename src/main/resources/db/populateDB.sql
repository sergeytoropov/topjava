DELETE FROM user_roles;
DELETE FROM users;
delete from meals;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password');

INSERT INTO users (name, email, password)
VALUES ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);

insert into meals(user_id, datetime, description, calories)
values (100000, '2015-12-20 10:00', 'Завтрак', 500);
insert into meals(user_id, datetime, description, calories)
values (100000, '2015-12-20 13:00', 'Обед', 1500);
insert into meals(user_id, datetime, description, calories)
values (100000, '2015-12-20 19:00', 'Ужин', 200);

insert into meals(user_id, datetime, description, calories)
values (100000, '2015-12-21 10:00', 'Завтрак', 400);
insert into meals(user_id, datetime, description, calories)
values (100000, '2015-12-21 14:00', 'Обед', 1700);
insert into meals(user_id, datetime, description, calories)
values (100000, '2015-12-21 19:00', 'Ужин', 300);

insert into meals(user_id, datetime, description, calories)
values (100000, '2015-12-22 10:00', 'Завтрак', 300);
insert into meals(user_id, datetime, description, calories)
values (100000, '2015-12-22 14:00', 'Обед', 1100);
insert into meals(user_id, datetime, description, calories)
values (100000, '2015-12-22 19:00', 'Ужин', 400);
