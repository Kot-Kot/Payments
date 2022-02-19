drop table if exists users cascade
;
create table users
(
	fio varchar(100) not null,
	email varchar(50) primary key,
	phone varchar(15) unique not null
	)
;

drop table if exists users_billing_address;
create table users_billing_address
(
	id bigserial primary key,
	billing_address varchar(100) ,
	contact varchar(100) references users(phone)
)
;
ALTER SEQUENCE users_billing_address_id_seq RESTART WITH 1000001;

drop table if exists templates cascade
;
create table templates
(
	id bigserial primary key,
	template_name varchar(100),
	iban varchar(100),
	purpose varchar(100),
	contact varchar(100) references users(phone)
)
;
ALTER SEQUENCE templates_id_seq RESTART WITH 10000001;



drop table if exists payments
;
create table payments
(
	id bigserial primary key,
	template_id int references templates(id),
	card_number varchar(100),
	p_sum numeric(12,2),
	status varchar(10),
	creation_dt timestamp,
	status_changed_dt timestamp
)
;
ALTER SEQUENCE payments_id_seq RESTART WITH 100000001;
