INSERT INTO employee (id, created, updated, version, date_of_birth, driving_status, first_name, gender, last_name, phone_number, status)
SELECT NEXTVAL('employee_id_sequence'), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0, '1999-03-20', 0, 'Akibul', 'MALE', 'Doe', '01815980623', 'ACTIVE';

INSERT INTO auth_info (id, created, updated, version, email, hased_password, salt, employee_id)
SELECT NEXTVAL('auth_id_sequence'), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0, 'admin@gmail.com', 'd5070b89996754bdb7a8c773a335b3a78ea3ae0a19f7cf54d933279ef26400fc', 'a118915d123da3c7e1e970ad47e18c8b', 1;

INSERT INTO employee_types (id, employee_type)
VALUES (1, 'SUPER_ADMIN'), (1, 'ADMIN');