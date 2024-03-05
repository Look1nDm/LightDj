insert into users(username, email, password)
values ('Василий', 'usermail1.com', '$2a$12$CfUeT8MArv3ef/RNGVEeSeFSaLwWdrCHw6euT3UuDit9wcVGI0yHK'),
       ('Петр', 'usermail2.com', '$2a$12$95V38s77ATT2K3FbXRztKOeYXld37ObsIzyCrCJvj7T08Dgc3F8dO'),
       ('Степан', 'usermail3.com', '$2a$12$PsoU7BSYyxO27Ww4N2s17.KkGqPVyOSVvKVUVArftt7XfI/Q8txq.'),
       ('Игорь', 'usermail4.com', '$2a$12$3KDbRcw/SsraPFYsHnMmqekwyNAwtVVUV2dIdk7uGs..wEszEjdWq'),
       ('Вероника', 'usermail5.com', '$2a$12$568pG.PZpj9rwlJfVYvlyOxLxofbtFdeRIq1O8JGMjUxQGAJwVv3q'),
       ('Екатерина', 'usermail6.com', '$2a$12$GbJ7hHkNFK/rCfV0WB3TqO8ftARaS80KkAUqc/zsD1fZoScBIKtL6'),
       ('Юлия', 'usermail7.com', '$2a$12$GbJ7hHkNFK/rCfV0WB3TqO8ftARaS80KkAUqc/zsD1fZoScBIKtL6'),
       ('Яна', 'usermail8.com', '$2a$12$GbJ7hHkNFK/rCfV0WB3TqO8ftARaS80KkAUqc/zsD1fZoScBIKtL6');


insert into applications(status, text, username, phone_number, date_created_application, user_id)
values ('DRAFT', 'Почему нет горячей воды!!!', 'Василий', '+79992097901', '2023-01-29 9:00', 1),
       ('DRAFT', 'Почему нет горячей воды!!!', 'Петр', '+79992097902', '2023-01-29 10:00', 2),
       ('SEND', 'В чем смысл....', 'Степан', '+79992097903', '2023-01-29 10:00', 3),
       ('ACCEPTED', 'Мусор на этаже', 'Игорь', '+79992097904', '2023-01-29 11:00', 4),
       ('DRAFT', '....', 'Василий', '+79992097901', '2023-01-29 12:00', 1),
       ('SEND', 'Почему не работает лифт', 'Петр', '+79992097902', '2023-01-29 13:00', 2),
       ('ACCEPTED', 'Почему нет света', 'Степан', '+79992097903', '2023-01-29 12:00', 3),
       ('SEND', 'Почему не работает лифт', 'Игорь', '+79992097904', '2023-01-29 13:00', 4),
       ('SEND', 'Почему не работает лифт', 'Василий', '+79992097901', '2023-01-29 13:00', 1),
       ('SEND', 'Почему не работает лифт', 'Петр', '+79992097902', '2023-01-29 13:00', 2);

insert into users_roles(user_id, role)
values (1, 'USER'),
       (2, 'USER'),
       (3, 'USER'),
       (4, 'USER'),
       (5, 'ADMIN'),
       (5, 'OPERATOR'),
       (6, 'OPERATOR');

