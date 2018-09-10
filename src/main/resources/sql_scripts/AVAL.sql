CREATE OR REPLACE PACKAGE aval_rndm_pkg AS
    FUNCTION random_group RETURN VARCHAR2;

    FUNCTION random_function RETURN VARCHAR2;

    FUNCTION random_parameter RETURN VARCHAR2;

    FUNCTION random_dscr RETURN VARCHAR2;

END aval_rndm_pkg;
/

CREATE OR REPLACE PACKAGE BODY aval_rndm_pkg AS

    FUNCTION random_group RETURN VARCHAR2 AS
        out_st   VARCHAR2(15);
        in_num   NUMBER(1);
    BEGIN
        in_num   := dbms_random.value(1,9);
        out_st   := dbms_random.string('U',in_num)
                  || '_GROUP';
        return(out_st);
    END random_group;

    FUNCTION random_function RETURN VARCHAR2 AS

        in_st      VARCHAR2(1);
        in_num     NUMBER(1);
        in_num_2   NUMBER(2);
        out_st     VARCHAR2(20);
    BEGIN
        in_num   := dbms_random.value(1,6);
        out_st   := 'f(';
        FOR i IN 1..in_num LOOP
            in_num_2   := dbms_random.value(1,9);
            IF ( i < in_num ) THEN
                in_st   := ',';
            ELSE
                in_st   := '';
            END IF;

            out_st     := out_st
                      || 'x'
                      || in_num_2
                      || in_st;
        END LOOP;

        out_st   := out_st || ')';
        return(out_st);
    END random_function;

    FUNCTION random_parameter RETURN VARCHAR2 AS
        out_st   VARCHAR2(15);
        in_num   NUMBER(1);
    BEGIN
        in_num   := dbms_random.value(1,9);
        out_st   := dbms_random.string('U',in_num)
                  || '_PARAM';
        return(out_st);
    END random_parameter;

    FUNCTION random_dscr RETURN VARCHAR2 AS
        out_st   VARCHAR2(15);
        in_num   NUMBER(2);
    BEGIN
        in_num   := dbms_random.value(1,15);
        out_st   := dbms_random.string('A',in_num);
        return(out_st);
    END random_dscr;

END aval_rndm_pkg;
/

CREATE OR REPLACE PACKAGE aval_pkg AS
    PROCEDURE create_and_fill;
    PROCEDURE drop_all_data;

END aval_pkg;
/

CREATE OR REPLACE PACKAGE BODY aval_pkg AS
    PROCEDURE create_tab (
        name_tab VARCHAR2
    ) IS
    BEGIN
        EXECUTE IMMEDIATE 'truncate table ' || name_tab;
    EXCEPTION
        WHEN OTHERS THEN
            IF ( name_tab = 'GROUP_FUNCTION' ) THEN
                EXECUTE IMMEDIATE 'CREATE TABLE GROUP_FUNCTION(
                  id NUMBER(18) NOT NULL,
                  name VARCHAR2(20),
                  descr VARCHAR2(255),
                  ctime DATE DEFAULT sysdate NOT NULL,
                  PRIMARY KEY (id))'
                ;
                EXECUTE IMMEDIATE 'COMMENT ON COLUMN GROUP_FUNCTION.ctime IS ''Дата регистрации записи в таблицу.'''
                ;
            ELSIF ( name_tab = 'FUNCTIONS' ) THEN
                EXECUTE IMMEDIATE 'CREATE TABLE FUNCTIONS(
                  id NUMBER(18) NOT NULL,
                  id_group NUMBER(10) NOT NULL,
                  name VARCHAR2(20),
                  descr VARCHAR2(255),
                  ctime DATE DEFAULT sysdate NOT NULL,
                  PRIMARY KEY (id))'
                ;
                EXECUTE IMMEDIATE 'COMMENT ON COLUMN FUNCTIONS.ctime IS ''Дата регистрации записи в таблицу.'''
                ;
            ELSIF ( name_tab = 'FUN_PARAM' ) THEN
                EXECUTE IMMEDIATE 'CREATE TABLE FUN_PARAM(
                  id NUMBER(18) NOT NULL,
                  id_fun NUMBER(10) NOT NULL,
                  name VARCHAR2(20),
                  descr VARCHAR2(255),
                  ctime DATE DEFAULT sysdate NOT NULL,
                  PRIMARY KEY (id))'
                ;
                EXECUTE IMMEDIATE 'COMMENT ON COLUMN FUN_PARAM.ctime IS ''Дата регистрации записи в таблицу.'''
                ;
            ELSE
                NULL;
                COMMIT;
            END IF;
    END create_tab;

    PROCEDURE seq_gen AS
    BEGIN
        EXECUTE IMMEDIATE '
drop sequence AVAL_SCHEMA.GFP_SEQ';
        EXECUTE IMMEDIATE '
CREATE SEQUENCE gfp_seq
START WITH 1
INCREMENT BY 1
NOCACHE
NOCYCLE
NOMAXVALUE'
        ;
    EXCEPTION
        WHEN OTHERS THEN
            EXECUTE IMMEDIATE '
CREATE SEQUENCE gfp_seq
START WITH 1
INCREMENT BY 1
NOCACHE
NOCYCLE
NOMAXVALUE'
            ;
    END seq_gen;

    PROCEDURE create_trg AS
    BEGIN
        EXECUTE IMMEDIATE '
create or replace TRIGGER G_ID_TRG
BEFORE INSERT ON GROUP_FUNCTION
for each row
BEGIN
  if :new.id is null then
    :NEW.id := gfp_seq.nextval;
  end if;
END;'
        ;
        EXECUTE IMMEDIATE '
create or replace TRIGGER F_ID_TRG
BEFORE INSERT ON FUNCTIONS
for each row
BEGIN
  if :new.id is null then
    :NEW.id := gfp_seq.nextval;
  end if;
END;'
        ;
        EXECUTE IMMEDIATE '
create or replace TRIGGER P_ID_TRG
BEFORE INSERT ON FUN_PARAM
for each row
BEGIN
  if :new.id is null then
    :NEW.id := gfp_seq.nextval;
  end if;
END;'
        ;
    EXCEPTION
        WHEN OTHERS THEN
            NULL;
    END create_trg;

    PROCEDURE fill_tab AS
        out_st        VARCHAR2(32767);
        local_val_g   PLS_INTEGER;
        local_val_f   PLS_INTEGER;
    BEGIN
        out_st   := 'INSERT ALL ';
        FOR i IN 1..5 LOOP
            EXECUTE IMMEDIATE 'select gfp_seq.nextval from dual'
            INTO local_val_g;
            out_st   := out_st
                      || '
 INTO group_function (
    id,
    name,
    descr
) VALUES ('
                      || local_val_g
                      || ',
    aval_rndm_pkg.random_group,
    aval_rndm_pkg.random_dscr
)'
                      ;
            FOR k IN 1..7 LOOP
                EXECUTE IMMEDIATE 'select gfp_seq.nextval from dual'
                INTO local_val_f;
                out_st   := out_st
                          || '
 INTO functions (
    id,
    id_group,
    name,
    descr
) VALUES ('
                          || local_val_f
                          || ','
                          || local_val_g
                          || ',
    aval_rndm_pkg.random_function,
    aval_rndm_pkg.random_dscr
)'
                          ;

                FOR j IN 1..3 LOOP
                    out_st   := out_st
                              || '
     INTO fun_param (
        id_fun,
        name,
        descr
    )
VALUES ('
                              || local_val_f
                              || ',
    aval_rndm_pkg.random_parameter,
    aval_rndm_pkg.random_dscr
)'
                              ;
                END LOOP;

            END LOOP;

        END LOOP;

        out_st   := out_st || ' SELECT * FROM dual';
        EXECUTE IMMEDIATE out_st;
        COMMIT;
    END fill_tab;

    PROCEDURE drop_all_data AS
    BEGIN
    EXECUTE IMMEDIATE 'DROP   TABLE aval_schema.fun_param';
    EXECUTE IMMEDIATE 'DROP   TABLE aval_schema.functions';
    EXECUTE IMMEDIATE 'DROP   TABLE aval_schema.group_function';
    EXECUTE IMMEDIATE 'DROP   PACKAGE aval_rndm_pkg';
    END    drop_all_data;

    PROCEDURE create_and_fill AS
    BEGIN
        aval_pkg.create_tab('GROUP_FUNCTION');
        aval_pkg.create_tab('FUNCTIONS');
        aval_pkg.create_tab('FUN_PARAM');
        aval_pkg.seq_gen;
        aval_pkg.create_trg;
        aval_pkg.fill_tab;
    END create_and_fill;

END aval_pkg;
/

