CREATE TABLE tokens(
    id                  bigserial NOT NULL,
    id_account          bigseial,
    token               varchar(20),
    commercial_ally     varchar(20),
    CONSTRAINT pk_token PRIMARY KEY (id),
    CONSTRAINT fk_bank_account_t FOREIGN KEY ( id_account ) REFERENCES bank_accounts (id) ON DELETE NO ACTION
);

CREATE TABLE bank_accounts(

    id                  bigserial NOT NULL,
    no_account          varchar(20),
    amount              decimal,
    dni_customer        varchar(20),
    is_active           boolean,
    CONSTRAINT pk_bank_account PRIMARY KEY (id),
    CONSTRAINT fk_customer_ba FOREIGN KEY ( dni_customer ) REFERENCES customers (dni_customer) ON DELETE NO ACTION
);

CREATE TABLE banking_movements(

    id                  bigserial NOT NULL,
    type_movement	    varchar(20),
    customer_account_id  bigserial,
    amount              decimal,
    type_account_other  varchar(20),
    no_account_other	varchar(20),
    bank				varchar(20),
    CONSTRAINT pk_banking_movement PRIMARY KEY (id),
    CONSTRAINT fk_bank_account_bm FOREIGN KEY (  customer_account_id ) REFERENCES bank_accounts (id) ON DELETE NO ACTION
);

CREATE TABLE customers(

    id                  bigserial NOT NULL,
    name                varchar(20),
    last_name           varchar(20),
    dni_customer        varchar(20),
    CONSTRAINT pk_customer PRIMARY KEY (id)
);

create sequence customer_sequence
start with 1
increment by 1
no minvalue
no maxvalue;

alter table customers alter column id set default nextval('customer_sequence');

create sequence token_sequence
start with 1
increment by 1
no minvalue
no maxvalue;

alter table tokens alter column id set default nextval('token_sequence');

create sequence banking_movement_sequence
start with 1
increment by 1
no minvalue
no maxvalue;

alter table banking_movements alter column id set default nextval('banking_movement_sequence');

create sequence bank_account_sequence
start with 1
increment by 1
no minvalue
no maxvalue;

alter table bank_accounts alter column id set default nextval('bank_account_sequence');
