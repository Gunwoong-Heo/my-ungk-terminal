INSERT INTO USER (USER_ID, PASSWORD, NAME, EMAIL, CREATE_DATE) VALUES ('ungk', '1234', '건웅', 'gjrjsdnd@naver.com', CURRENT_TIMESTAMP());
INSERT INTO USER (USER_ID, PASSWORD, NAME, EMAIL, CREATE_DATE) VALUES ('dongk', '1234', '동쿠', 's129307@naver.com', CURRENT_TIMESTAMP());

INSERT INTO QUESTION (writer_id, title, contents, create_date, count_of_answers) VALUES (1, '국내에서 Ruby On Rails가..', 'testing', CURRENT_TIMESTAMP(), 0);
INSERT INTO QUESTION (writer_id, title, contents, create_date, count_of_answers) VALUES (2, '동쿠가 쓴 글', 'testing', CURRENT_TIMESTAMP(), 0);