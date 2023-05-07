create table cards(

    id bigint not null auto_increment,
    numberCard varchar(20) not null,
    password varchar(255) not null,
    balance decimal(6,2) null,
    primary key(id)
);