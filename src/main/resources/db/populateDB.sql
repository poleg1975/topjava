DELETE FROM user_roles;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password) VALUES
  ('User', 'user@yandex.ru', 'password'),
  ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);

INSERT INTO meals (user_id, dateTime, description, calories) VALUES
  (100000, (TIMESTAMP '2011-05-16 09:36:38'), 'Завтрак', 500),
  (100001, (TIMESTAMP '2011-05-16 08:17:57'), 'Завтрак', 700),
  (100000, (TIMESTAMP '2011-05-16 15:36:38'), 'Обед', 1000),
  (100001, (TIMESTAMP '2011-05-16 14:36:38'), 'Обед', 1100),
  (100000, (TIMESTAMP '2011-05-16 19:36:38'), 'Ужин', 900)