INSERT INTO USER (USER_ID, PASSWORD, NAME, EMAIL) VALUES ('ungk', '1234', '건웅', 'gjrjsdnd@naver.com');
INSERT INTO USER (USER_ID, PASSWORD, NAME, EMAIL) VALUES ('dongk', '1234', '동쿠', 's129307@naver.com');

INSERT INTO QUESTION (writer_id, title, contents, create_date) VALUES (1, '국내에서 Ruby On Rails가..', 'testing', CURRENT_TIMESTAMP());
INSERT INTO QUESTION (writer_id, title, contents, create_date) VALUES (2, '동쿠가 쓴 글', 'testing', CURRENT_TIMESTAMP());