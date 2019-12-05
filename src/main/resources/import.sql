INSERT INTO USER (USER_ID, PASSWORD, NAME, EMAIL) VALUES ('ungk', '1234', '건웅', 'gjrjsdnd@naver.com');
INSERT INTO USER (USER_ID, PASSWORD, NAME, EMAIL) VALUES ('dongk', '1234', '동쿠', 's129307@naver.com');

INSERT INTO QUESTION (writer_id, title, contents, create_date, count_of_answers) VALUES (1, '국내에서 Ruby On Rails가..', 'testing', CURRENT_TIMESTAMP(), 0);
INSERT INTO QUESTION (writer_id, title, contents, create_date, count_of_answers) VALUES (2, '동쿠가 쓴 글', 'testing', CURRENT_TIMESTAMP(), 0);