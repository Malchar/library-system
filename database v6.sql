drop database if exists `library_system`;
CREATE DATABASE `library_system` CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_as_cs;
USE `library_system`;

CREATE TABLE `users` (
  `email` varchar(32) primary key,
  `password` varchar(60) not null,
  `name` varchar(255)
);

create table `user_roles` (
	`id` int primary key auto_increment,
    `role_name` varchar(16),
    `user_email` varchar(32),
    foreign key (`user_email`) references `users` (`email`)
);

CREATE TABLE `books` (
  `id` int primary key auto_increment,
  `title` varchar(64),
  `author` varchar(64),
  `description` varchar(255),
  `isbn` varchar(13),
  `image_url` varchar(255),
  `category` varchar(32)
) COLLATE = utf8mb4_0900_as_cs;

CREATE TABLE `items` (
  `id` int primary key auto_increment,
  `book_id` int not null,
  `item_condition` enum("New", "Fine", "Damaged"),
  foreign key (`book_id`) references `books` (`id`)
);

CREATE TABLE `checkouts` (
  `id` int primary key auto_increment,
  `user_email` varchar(32) NOT NULL,
  `pending` bool,
  foreign key (`user_email`) references `users` (`email`)
);

CREATE TABLE `line_items` (
	`id` int primary key auto_increment,
    `item_id` int not null,
    `checkout_id` int not null,
	`due` date NOT NULL,
	`returned` date,
	foreign key (`item_id`) references `items` (`id`),
    foreign key (`checkout_id`) references `checkouts` (`id`)
);

insert into `books`
	(`title`, `author`, `description`, `isbn`, `image_url`, `category`) values
	("The Essentials of Computer Organization and Architecture", "Linda Null & Julia Lobur", "Fifth edition.", "9781284123036", "https://picsum.photos/seed/1/200/300", "Math & Science"),
    ("Data Structures & Other Objects Using Java", "Michael Main", "Fourth edition.", "9780132576246", "https://picsum.photos/seed/2/200/300", "Math & Science"),
    ("Database System Concepts", "Abraham Silberschatz, Henry F. Korth & S. Sudarshan", "Seventh edition.", "9780078022159", "https://picsum.photos/seed/3/200/300", "Math & Science"),
    ("Long Walk to Freedom", "Nelson Mandela", "An autobiography ghostwritten by Richard Stengel. The book profiles his early life, coming of age, education and 27 years spent in prison.", "0316874965", "https://picsum.photos/seed/4/200/300", "History"),
    ("Black Hawk Down", "Mark Bowden", "Documents efforts by the Unified Task Force to capture Somali faction leader Mohamed Farrah Aidid in 1993, and the resulting battle in Mogadishu between United States forces and Aidid's militia.", "9780871137388", "https://picsum.photos/seed/5/200/300", "History"),
    ("Zero to One", "Peter Thiel", "Condensed and updated version of a highly popular set of online notes taken by Masters for the CS183 class on startups, as taught by Thiel at Stanford University in Spring 2012.", "9780804139298", "https://picsum.photos/seed/6/200/300", "Technology"),
    ("The Inevitable", "Kevin Kelly", "Forecasts the twelve technological forces that will shape the next thirty years.", "9780525428084", "https://picsum.photos/seed/7/200/300", "Technology"),
    ("Gone Girl", "Gillian Flynn", "The sense of suspense in the novel comes from whether or not Nick Dunne is involved in the disappearance of his wife Amy.", "9780307588364", "https://picsum.photos/seed/8/200/300", "Horror"),
    ("1984", "George Orwell", "Thematically, it centres on the consequences of totalitarianism, mass surveillance and repressive regimentation of people and behaviours within society.", "9780452284234", "https://picsum.photos/seed/9/200/300", "Science Fiction"),
    ("The Martian", "Andy Weir", "The story follows an American astronaut, Mark Watney, as he becomes stranded alone on Mars in 2035 and must improvise in order to survive.", "9780804139021", "https://picsum.photos/seed/10/200/300", "Science Fiction");

insert into `items`
	(`book_id`, `item_condition`) values
    (1, "Fine"),
    (1, "New"),
    (1, "Fine"),
    (2, "Fine"),
    (3, "New"),
    (4, "Fine"),
    (5, "Damaged"),
    (6, "New");

insert into users values
	("p_schmitz@email.com", "$2a$12$gA7DNVYnOeo4Zj.VaoNhe.8IlgRj87/i0aE3ZeDJph.myMLI4Rvjq", "Paul Schmitz"),
    ("a@b.com", "$2a$12$gA7DNVYnOeo4Zj.VaoNhe.8IlgRj87/i0aE3ZeDJph.myMLI4Rvjq", "Test user");

insert into `user_roles` (`role_name`, `user_email`) values
	("ADMIN", "p_schmitz@email.com"),
    ("USER", "p_schmitz@email.com"),
    ("USER", "a@b.com");






