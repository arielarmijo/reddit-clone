insert into user (id, username, password, email, enabled, created_at) values (0, 'aarmijo', '$2a$10$O.udAvsveX.UPRbsUyka9.mzvbzEa5FGISE1iXlVfs9esQirvOWB6', 'arielarmijo@yahoo.es', true, timestamp'2021-06-29 10:07:55.008913');

insert into subreddit (id, name, description, created_at, user_id) values (0, 'First Subreddit', 'This is my first subreddit', timestamp'2021-06-29 10:07:55.008913', 0);

insert into post (id, name, description, created_at, vote_count, subreddit_id, user_id) values (0, 'First Post', 'This is my first post', timestamp'2021-06-29 10:07:55.008913', 0, 0, 0);