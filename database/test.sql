select count(1) from sys_item where order_id = 1;

select u.user_name,o.no,p.name,p.image,
	   i.num,i.price,o.price 'o.price',
	   (select count(1) from sys_item where order_id = o.id) 'count'
from sys_order o
left join sys_user u
	on o.user_id = u.user_id
left join sys_item i
	on o.id = i.order_id
left join sys_product p
	on i.product_id = p.product_id
where o.id = 1;

---escape '/'表示当遇到/时,/后面的字符不作为任何关键字,只作为普通字符来表示
select *
from sys_user
where login_name like '%/%%' escape '/';
select *
from sys_user
where login_name like '%/a/%%' escape '/';


alter table sys_attache change file_type file_type varchar(10);
alter table sys_staff change role role varchar(10);


--查询所有部门信息
select d.dept_id,d.dept_name,d.dept_no,d.remark,d.is_valid,
	   fd.dept_id,fd.dept_name
from sys_dept d
left join sys_dept fd
	on d.father_dept_id = fd.dept_id


---查询管理员信息
select s.staff_id,s.staff_name,s.login_name,
	   s.phone,s.email,s.role,s.is_valid,
	   d.dept_id,d.dept_name
from sys_staff s
left join sys_dept d
	on s.dept_id = d.dept_id
	
	
