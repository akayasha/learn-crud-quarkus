CREATE TABLE student
(
    nim     VARCHAR(255) NOT NULL,
    name    VARCHAR(255) NULL,
    classes VARCHAR(255) NULL,
    dob     DATE NULL,
    phoneNo VARCHAR(255) NOT NULL,
    email   VARCHAR(255) NOT NULL,
    CONSTRAINT pk_student PRIMARY KEY (nim)
);