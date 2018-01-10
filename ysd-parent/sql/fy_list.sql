-- 最低提现金额
insert into fy_listing (id, create_date,modify_date,name,sign,grade,order_list,key_value,is_enabled,description,path,parent_id)
VALUES (2065, now(), now(), '最小提现金额', 'cash_type_minnum', 1, 1, 100, '1', '配置的最小提现金额', '1507,2065', 1507);

insert into fy_listing (create_date,modify_date,name,sign,grade,order_list,key_value,is_enabled,description,path,parent_id)
VALUES (now(), now(), '会员总人数', 'total_user_num', 1, 1, 2000, 1, '会员总人数', NULL , NULL );

insert into fy_listing (create_date,modify_date,name,sign,grade,order_list,key_value,is_enabled,description,path,parent_id)
VALUES (now(), now(), '累计投资金额', 'total_tender_money', 1, 1, 30000000, 1, '累计投资金额', NULL , NULL );

insert into fy_listing (create_date,modify_date,name,sign,grade,order_list,key_value,is_enabled,description,path,parent_id)
VALUES (now(), now(), '为用户赚取', 'total_get_money', 1, 1, 30000, 1, '为用户赚取', NULL , NULL );