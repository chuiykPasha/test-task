insert into user(id, name) VALUES (1, 'user-has-a-grade');
insert into user(id, name) VALUES (2, 'user-has-f-grade');

insert into course(id, name) VALUES (1, 'course1');
insert into course(id, name) VALUES (2, 'course2');

insert into user_courses(user_id, course_id, grade) VALUES (1, 1, 'A');
insert into user_courses(user_id, course_id, grade) VALUES (1, 2, 'A');

insert into user_courses(user_id, course_id, grade) VALUES (2, 1, 'F');
insert into user_courses(user_id, course_id, grade) VALUES (2, 2, 'F');