create table if not exists users
(
    id       bigserial primary key,
    username varchar(100) not null,
    email    varchar(255) not null,
    password varchar(255) not null
);

create table if not exists applications
(
    id                       bigserial primary key,
    status                   varchar(255) not null,
    text                     varchar(500) not null,
    username                 varchar(255) not null,
    phone_number             varchar(20)  not null,
    date_created_application timestamp    not null,
    user_id                  bigint,
    operator_id              bigint,
    constraint fk_users_applications_users foreign key (user_id) references users (id),
    constraint fk_operators_applications_operators foreign key (operator_id) references users (id)
);
create table if not exists users_roles
(
    user_id bigint   not null,
    role    varchar(30) not null,
    primary key (user_id, role),
    constraint fk_users_roles_users foreign key (user_id) references users (id) on delete cascade on update no action
);