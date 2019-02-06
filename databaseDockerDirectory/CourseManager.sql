CREATE DATABASE CourseManageDb CHARACTER SET utf8 COLLATE utf8_general_ci;
CREATE USER 'courseManagerUser'@'%' IDENTIFIED BY '12345';
GRANT ALL PRIVILEGES ON CourseManageDb.* TO 'courseManagerUser'@'%';
