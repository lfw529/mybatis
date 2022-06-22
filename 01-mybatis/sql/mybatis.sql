drop database if exists mybatis;
create database mybatis;
use mybatis;
create table t_user(
	`id` int primary key auto_increment,
    `username` varchar(20),
    `password` varchar(20),
    `age` int,
    `gender` char,
    `email` varchar(25)
);

create table t_department(
	`did` int primary key,
    `dname` varchar(20)
);

create table t_emp(
	`id` int primary key auto_increment,
	`emp_name` varchar(20),
    `password` varchar(20),
    `gender` char,
    `age` int,
    `email` varchar(25),
    `did` int,
	constraint fk_employee_department foreign key(`did`) references t_department(`did`)
);

insert into t_department values(1,'开发部');
insert into t_department values(2,'测试部');
insert into t_department values(3,'运维部');
insert into t_emp values (null, '张三', 'aaa', '男', 22, 'zhangsan@168.com', 1);
insert into t_emp values (null, '李四', 'bbb', '女', 22, 'lisi@168.com', 2);
insert into t_emp values (null, '王五', 'ccc', '男', 22, 'wangwu@168.com', 3);
insert into t_emp values (null, '赵六', 'ddd', '女', 22, 'zhaoliu@168.com', 1);
insert into t_emp values (null, '田七', 'eee', '男', 22, 'tianqi@168.com', 2);