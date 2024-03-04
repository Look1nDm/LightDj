insert into users(email, password)
values ('usermail1.com', '$2a$12$95V38s77ATT2K3FbXRztKOeYXld37ObsIzyCrCJvj7T08Dgc3F8dO'),
       ('usermail2.com', '$2a$12$CfUeT8MArv3ef/RNGVEeSeFSaLwWdrCHw6euT3UuDit9wcVGI0yHK'),
       ('usermail3.com', '$2a$12$GbJ7hHkNFK/rCfV0WB3TqO8ftARaS80KkAUqc/zsD1fZoScBIKtL6'),
       ('usermail4.com', '$2a$12$PsoU7BSYyxO27Ww4N2s17.KkGqPVyOSVvKVUVArftt7XfI/Q8txq.'),
       ('usermail5.com', '$2a$12$3KDbRcw/SsraPFYsHnMmqekwyNAwtVVUV2dIdk7uGs..wEszEjdWq'),
       ('usermail6.com', '$2a$12$568pG.PZpj9rwlJfVYvlyOxLxofbtFdeRIq1O8JGMjUxQGAJwVv3q');

insert into applications(status, text, username, phone_number, date_created_application, user_id)
values ('DRAFT', 'Почему нет горячей воды!!!', 'User4', '+79992097904', '2023-01-29 9:00', 4),
       ('DRAFT', 'Почему нет горячей воды!!!', 'User2', '+79992097902', '2023-01-29 9:00', 2),
       ('SEND', 'В чем смысл....', 'User2', '+79992097902', '2023-01-29 10:00', 2),
       ('ACCEPTED', 'Мусор на этаже', 'User4', '+79992097904', '2023-01-29 11:00', 4),
       ('DRAFT', '....', 'User3', '+79992097903', '2023-01-29 12:00', 3),
       ('SEND', 'Почему не работает лифт', 'User6', '+79992097906', '2023-01-29 13:00', 3),
       ('ACCEPTED', 'Почему нет света', 'User2', '+79992097902', '2023-01-29 12:00', 2),
       ('SEND', 'Почему не работает лифт', 'User6', '+79992097906', '2023-01-29 13:00', 1),
       ('SEND', 'Почему не работает лифт', 'User6', '+79992097906', '2023-01-29 13:00', 1),
       ('SEND', 'Почему не работает лифт', 'User6', '+79992097906', '2023-01-29 13:00', 1);


insert into users_roles(user_id, role)
values (1, 'ADMIN'),
       (1, 'OPERATOR'),
       (5, 'ADMIN'),
       (3, 'OPERATOR'),
       (4, 'USER'),
       (2, 'USER'),
       (6, 'USER');

