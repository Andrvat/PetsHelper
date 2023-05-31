create table role
(
    id   bigint not null
        constraint role_pkey
            primary key,
    name varchar(255)
);

create table user_credential
(
    id       bigint not null
        constraint user_credential_pkey
            primary key,
    email    varchar(255),
    password varchar(255),
    username varchar(255)
);

create table user_credential_roles
(
    user_credential_id bigint   not null
        constraint fkkynpylfhbrgvcrwomwwischj5
            references user_credential,
    roles_id           bigint not null
        constraint fkct7c60rgxeyyfv6kai9wlxwa
            references role,
    constraint user_credential_roles_pkey
        primary key (user_credential_id, roles_id)
);

INSERT INTO Role (id, name)
VALUES (1, 'ROLE_USER');
INSERT INTO Role (id, name)
VALUES (2, 'ROLE_ADMIN');