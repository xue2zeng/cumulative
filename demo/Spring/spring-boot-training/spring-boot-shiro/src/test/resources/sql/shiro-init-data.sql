delete from users;
delete from user_roles;
delete from roles_permissions;
insert into users(username, password, password_salt) values('zeng', '123##', null);
insert into user_roles(username, role_name) values('zeng', 'role1');
insert into user_roles(username, role_name) values('zeng', 'role2');
insert into roles_permissions(role_name, permission) values('role1', '+user1+10');
insert into roles_permissions(role_name, permission) values('role1', 'user1:*');
insert into roles_permissions(role_name, permission) values('role1', '+user2+10');
insert into roles_permissions(role_name, permission) values('role1', 'user2:*');


insert into users(username, password, password_salt) values('wu', '$shiro1$SHA-512$1$$PJkJr+wlNU1VHa4hWQuybjjVPyFzuNPcPu5MBH56scHri4UQPjvnumE7MbtcnDYhTcnxSkL9ei/bhIVrylxEwg==', null);
insert into users(username, password, password_salt) values('liu', 'a9a114054aa6758184314fbb959fbda4', '24520ee264eab73ec09451d0e9ea6aac');
