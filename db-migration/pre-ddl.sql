CREATE TABLE attachment
(
    id         INTEGER      DEFAULT NEXTVAL('attachment_id_sequence'),
    created    TIMESTAMP    NOT NULL,
    updated    TIMESTAMP    NOT NULL,
    version    INTEGER      NOT NULL,
    data       OID,
    name       VARCHAR(255) NOT NULL,
    type       VARCHAR(200) NOT NULL,
    CONSTRAINT pk_attachment PRIMARY KEY (id)
);

CREATE TABLE employee
(
    id                    INTEGER      DEFAULT NEXTVAL('employee_id_sequence'),
    created               TIMESTAMP    NOT NULL,
    updated               TIMESTAMP    NOT NULL,
    version               INTEGER      NOT NULL,
    date_of_birth         DATE         NOT NULL,
    driving_status        VARCHAR(10),
    first_name            VARCHAR(100) NOT NULL,
    gender                VARCHAR(20) NOT NULL,
    last_name             VARCHAR(100) NOT NULL,
    phone_number          VARCHAR(15) NOT NULL,
    status                VARCHAR(10)      NOT NULL,
    CONSTRAINT pk_employee PRIMARY KEY (id),
    CONSTRAINT uk_employee_phone_number UNIQUE (phone_number)
);

CREATE TABLE auth_info
(
    id              INTEGER      DEFAULT NEXTVAL('auth_id_sequence'),
    created         TIMESTAMP    NOT NULL,
    updated         TIMESTAMP    NOT NULL,
    version         INTEGER      NOT NULL,
    email           VARCHAR(100) NOT NULL,
    hased_password  VARCHAR(255) NOT NULL,
    salt            VARCHAR(255) NOT NULL,
    employee_id     INTEGER NOT NULL,
    CONSTRAINT pk_auth_info PRIMARY KEY (id),
    CONSTRAINT uk_auth_info_email UNIQUE (email),
    CONSTRAINT fk_auth_employee FOREIGN KEY (employee_id) REFERENCES employee (id),
    CONSTRAINT uk_auth_info_employee UNIQUE (employee_id)
);

CREATE TABLE car
(
    id            INTEGER      DEFAULT NEXTVAL('car_id_sequence'),
    created       TIMESTAMP    NOT NULL,
    updated       TIMESTAMP    NOT NULL,
    version       INTEGER      NOT NULL,
    name          VARCHAR(100) NOT NULL,
    available_seat INTEGER      NOT NULL,
    registration_no    VARCHAR(100) NOT NULL,
    total_seat    INTEGER      NOT NULL,
    manager_id    INTEGER,
    status        VARCHAR(10),
    CONSTRAINT pk_car PRIMARY KEY (id),
    CONSTRAINT uk_car_name UNIQUE (name),
    CONSTRAINT uk_car_registration_no UNIQUE (registration_no),
    CONSTRAINT fk_employee_manager FOREIGN KEY (manager_id) REFERENCES employee (id),
    CONSTRAINT uk_car_manager UNIQUE (manager_id)
);

CREATE TABLE assignment
(
    id          INTEGER      DEFAULT NEXTVAL('assignment_id_sequence'),
    created     TIMESTAMP    NOT NULL,
    updated     TIMESTAMP    NOT NULL,
    version     INTEGER      NOT NULL,
    car_id      INTEGER      NOT NULL,
    employee_id INTEGER      NOT NULL,
    CONSTRAINT pk_assignment PRIMARY KEY (id),
    CONSTRAINT uk_assignment_employee UNIQUE (employee_id),
    CONSTRAINT fk_assignment_car FOREIGN KEY (car_id) REFERENCES car (id),
    CONSTRAINT fk_assignment_employee FOREIGN KEY (employee_id) REFERENCES employee (id)
);

CREATE TABLE car_support_staff
(
    support_staff_id INTEGER NOT NULL,
    car_id           INTEGER NOT NULL,
    CONSTRAINT fk_car_support_staff_employee FOREIGN KEY (support_staff_id) REFERENCES employee (id),
    CONSTRAINT fk_car_support_staff_car FOREIGN KEY (car_id) REFERENCES car (id),
    CONSTRAINT pk_car_support_staff PRIMARY KEY (car_id)
);

CREATE TABLE employee_types
(
    id           INTEGER      NOT NULL,
    employee_type VARCHAR(20) NOT NULL,
    CONSTRAINT pk_employee_types_table PRIMARY KEY (id, employee_type),
    CONSTRAINT fk_employee_types_table_employee FOREIGN KEY (id) REFERENCES employee (id)
);

CREATE TABLE location
(
    id      INTEGER      DEFAULT NEXTVAL('location_id_sequence'),
    created TIMESTAMP    NOT NULL,
    updated TIMESTAMP    NOT NULL,
    version INTEGER      NOT NULL,
    name    VARCHAR(100) NOT NULL,
    image_id INTEGER,
    CONSTRAINT pk_location PRIMARY KEY (id),
    CONSTRAINT uk_location_name UNIQUE (name),
    CONSTRAINT fk_location_attachment FOREIGN KEY (image_id) REFERENCES attachment (id),
    CONSTRAINT uk_location_image UNIQUE (image_id)
);

CREATE TABLE route
(
    id      INTEGER      DEFAULT NEXTVAL('route_id_sequence'),
    created TIMESTAMP    NOT NULL,
    updated TIMESTAMP    NOT NULL,
    version INTEGER      NOT NULL,
    name    VARCHAR(100) NOT NULL,
    CONSTRAINT pk_route PRIMARY KEY (id),
    CONSTRAINT uk_route_name UNIQUE (name)
);

CREATE TABLE car_route
(
    route_id INTEGER NOT NULL,
    car_id   INTEGER NOT NULL,
    CONSTRAINT fk_car_route_route FOREIGN KEY (route_id) REFERENCES route (id),
    CONSTRAINT fk_car_route_car FOREIGN KEY (car_id) REFERENCES car (id),
    CONSTRAINT pk_car_route PRIMARY KEY (car_id)
);

CREATE TABLE request
(
    id          INTEGER      DEFAULT NEXTVAL('request_id_sequence'),
    created     TIMESTAMP    NOT NULL,
    updated     TIMESTAMP    NOT NULL,
    version     INTEGER      NOT NULL,
    comment     VARCHAR(255),
    status      VARCHAR(10)  NOT NULL,
    car_id      INTEGER      NOT NULL,
    employee_id INTEGER      NOT NULL,
    route_id    INTEGER      NOT NULL,
    CONSTRAINT pk_request PRIMARY KEY (id),
    CONSTRAINT fk_request_car FOREIGN KEY (car_id) REFERENCES car (id),
    CONSTRAINT fk_request_employee FOREIGN KEY (employee_id) REFERENCES employee (id),
    CONSTRAINT fk_request_route FOREIGN KEY (route_id) REFERENCES route (id)
);

CREATE INDEX  status_index ON request (status DESC);

CREATE TABLE route_location
(
    route_id    INTEGER NOT NULL,
    location_id INTEGER NOT NULL,
    CONSTRAINT fk_route_location_route FOREIGN KEY (route_id) REFERENCES route (id),
    CONSTRAINT fk_route_location_location FOREIGN KEY (location_id) REFERENCES location (id),
    CONSTRAINT pk_route_location PRIMARY KEY (route_id, location_id)
);
