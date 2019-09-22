# system settings
insert ignore into str_config(config_id,is_enabled,config_name,config_value,config_description) values (1,1,'system_name','Streets','System Name');
insert ignore into str_config(config_id,is_enabled,config_name,config_value,config_description) values (2,1,'config_update_duration','1440','How often application re-reads configs from the database');
insert ignore into str_config(config_id,is_enabled,config_name,config_value,config_description) values (3,1,'thread_core_pool_size','20','Number of initial threads available');
insert ignore into str_config(config_id,is_enabled,config_name,config_value,config_description) values (4,1,'thread_max_pool_size','50','Maximum number threads available');
insert ignore into str_config(config_id,is_enabled,config_name,config_value,config_description) values (5,1,'mutex_lock_wait_time','10000','Milliseconds to wait for mutex');
insert ignore into str_config(config_id,is_enabled,config_name,config_value,config_description) values (6,1,'mutex_lock_wait_interval','1000','Milliseconds between checking mutex lock');
# email settings
insert ignore into str_config(config_id,is_enabled,config_name,config_value,config_description) values (51,1,'email_protocol','smtp','Email protocol');
insert ignore into str_config(config_id,is_enabled,config_name,config_value,config_description) values (52,1,'email_disable','false','Are all emails disabled?');
insert ignore into str_config(config_id,is_enabled,config_name,config_value,config_description) values (53,1,'email_host','localhost','SMTP host');
insert ignore into str_config(config_id,is_enabled,config_name,config_value,config_description) values (54,1,'email_port','25','SMTP port');
insert ignore into str_config(config_id,is_enabled,config_name,config_value,config_description) values (55,1,'email_username','streets','SMTP username');
insert ignore into str_config(config_id,is_enabled,config_name,config_value,config_description) values (56,1,'email_password','streets','SMTP password');
insert ignore into str_config(config_id,is_enabled,config_name,config_value,config_description) values (57,1,'email_smtp_auth','false','Authenticate on SMTP');
insert ignore into str_config(config_id,is_enabled,config_name,config_value,config_description) values (58,1,'email_smtp_starttls_enable','false','Enable TLS Authentication');
insert ignore into str_config(config_id,is_enabled,config_name,config_value,config_description) values (59,1,'email_smtp_debug','false','Show SMTP debug messages?');
insert ignore into str_config(config_id,is_enabled,config_name,config_value,config_description) values (60,1,'email_from','empowerttl@gmail.com','What is shown as sender from in emails from system');
insert ignore into str_config(config_id,is_enabled,config_name,config_value,config_description) values (61,1,'email_alert_to','symbiosis.dev@gmail.com','Where email is sent when there is a system alert');
#localization settings
insert ignore into str_config(config_id,is_enabled,config_name,config_value,config_description) values (80,1,'default_country','Zimbabwe','Default country for user registrations');
insert ignore into str_config(config_id,is_enabled,config_name,config_value,config_description) values (81,1,'default_country_code','263','Default country code for phone numbers');
insert ignore into str_config(config_id,is_enabled,config_name,config_value,config_description) values (82,1,'default_currency_symbol','USD','Default currency for transactions');
insert ignore into str_config(config_id,is_enabled,config_name,config_value,config_description) values (83,1,'default_language','English','Default language for user registrations');
#registration settings
# insert ignore into str_config(config_id,is_enabled,config_name,config_value,config_description) values (100,1,'default_desktop_group','DESKTOP_AGENT','Default auth group for desktop user registrations');
insert ignore into str_config(config_id,is_enabled,config_name,config_value,config_description) values (101,1,'default_web_auth_group','WEB_USER','Default auth group for web user registrations');
insert ignore into str_config(config_id,is_enabled,config_name,config_value,config_description) values (105,1,'default_smart_phone_auth_group','MOBILE_USER','Default auth group for mobile user registrations');
#company settings
insert ignore into str_config(config_id,is_enabled,config_name,config_value,config_description) values (120,1,'domain_name','imthestreets.com','Domain name for where the system is hosted');
insert ignore into str_config(config_id,is_enabled,config_name,config_value,config_description) values (121,1,'contact_address','Harare','Company physical address');
insert ignore into str_config(config_id,is_enabled,config_name,config_value,config_description) values (122,1,'support_email','symbiosis.dev@gmail.com','Company support email');
insert ignore into str_config(config_id,is_enabled,config_name,config_value,config_description) values (123,1,'support_phone','+263785107830','Company support phone number');
#security settings
insert ignore into str_config(config_id,is_enabled,config_name,config_value,config_description) values (160,1,'max_password_tries','5','Maximum password tries before blocking an account');
#web settings
insert ignore into str_config(config_id,is_enabled,config_name,config_value,config_description) values (181,1,'default_reporting_days','7','Number of days to show in history by default');

insert ignore into str_country(id,name,is_enabled,iso_code_2,iso_code_3,dialing_code) values (1,'ZIMBABWE',0,'ZW','ZWE',263);

insert ignore into str_channel(channel_id,name,is_enabled) values (1,'DESKTOP',1);
insert ignore into str_channel(channel_id,name,is_enabled) values (2,'WEB',1);
insert ignore into str_channel(channel_id,name,is_enabled) values (3,'SMART_PHONE',1);

insert ignore into str_currency(currency_id,currency_name,iso_4217_code,iso_4217_num) values (1,'United States Dollar','USD','840');
insert ignore into str_currency(currency_id,currency_name,iso_4217_code,iso_4217_num) values (2,'South African Rand','ZAR','710');
insert ignore into str_currency(currency_id,currency_name,iso_4217_code,iso_4217_num) values (3,'Botswana Pula','BWP','072');
insert ignore into str_currency(currency_id,currency_name,iso_4217_code,iso_4217_num) values (4,'Pound Sterling','GBP','826');
insert ignore into str_currency(currency_id,currency_name,iso_4217_code,iso_4217_num) values (5,'Euro','EUR','978');
insert ignore into str_currency(currency_id,currency_name,iso_4217_code,iso_4217_num) values (6,'Zambian Kwacha','ZMW','967');

insert ignore into str_event_type(id,name,is_enabled) values (1000,'USER_REGISTRATION',1);
insert ignore into str_event_type(id,name,is_enabled) values (1001,'USER_ASSIGN_CHANNEL',1);
insert ignore into str_event_type(id,name,is_enabled) values (1002,'USER_CREATE',1);
insert ignore into str_event_type(id,name,is_enabled) values (1003,'USER_LOGIN',1);
insert ignore into str_event_type(id,name,is_enabled) values (1004,'USER_LOGOUT',1);
insert ignore into str_event_type(id,name,is_enabled) values (1005,'USER_UPDATE',1);
insert ignore into str_event_type(id,name,is_enabled) values (1006,'USER_PASSWORD_UPDATE',1);
insert ignore into str_event_type(id,name,is_enabled) values (1007,'USER_PASSWORD_RESET',1);
insert ignore into str_event_type(id,name,is_enabled) values (1008,'USER_PIN_RESET',1);

insert ignore into str_event_type(id,name,is_enabled) values (1100,'WALLET_LOAD',1);
insert ignore into str_event_type(id,name,is_enabled) values (1101,'WALLET_CASHOUT',1);
insert ignore into str_event_type(id,name,is_enabled) values (1102,'WALLET_UPDATE',1);
insert ignore into str_event_type(id,name,is_enabled) values (1103,'WALLET_GET_CASHOUT_ACCOUNTS',1);
insert ignore into str_event_type(id,name,is_enabled) values (1104,'WALLET_ADD_CASHOUT_ACCOUNT',1);
insert ignore into str_event_type(id,name,is_enabled) values (1105,'WALLET_DISABLE_CASHOUT_ACCOUNT',1);
insert ignore into str_event_type(id,name,is_enabled) values (1106,'WALLET_GROUP_CREATE',1);
insert ignore into str_event_type(id,name,is_enabled) values (1107,'WALLET_GROUP_UPDATE',1);
insert ignore into str_event_type(id,name,is_enabled) values (1108,'WALLET_GROUP_VOUCHER_DISCOUNT_UPDATE',1);
insert ignore into str_event_type(id,name,is_enabled) values (1109,'WALLET_GROUP_TRANSFER_CHARGE_UPDATE',1);
insert ignore into str_event_type(id,name,is_enabled) values (1110,'WALLET_SWIPE_IN',1);
insert ignore into str_event_type(id,name,is_enabled) values (1111,'WALLET_TRANSFER',1);
insert ignore into str_event_type(id,name,is_enabled) values (1112,'WALLET_HISTORY',1);

insert ignore into str_event_type (id,name,is_enabled) values (1200,'VOUCHER_IMPORT',1);
insert ignore into str_event_type (id,name,is_enabled) values (1201,'VOUCHER_CREATE',1);
insert ignore into str_event_type (id,name,is_enabled) values (1202,'VOUCHER_PURCHASE',1);
insert ignore into str_event_type (id,name,is_enabled) values (1203,'VOUCHER_UPDATE',1);
insert ignore into str_event_type (id,name,is_enabled) values (1204,'VOUCHER_TYPE_CREATE',1);
insert ignore into str_event_type (id,name,is_enabled) values (1205,'VOUCHER_TYPE_UPDATE',1);
insert ignore into str_event_type (id,name,is_enabled) values (1206,'VOUCHER_PURCHASE_QUERY',1);
insert ignore into str_event_type (id,name,is_enabled) values (1207,'VOUCHER_PROVIDER_CREATE',1);
insert ignore into str_event_type (id,name,is_enabled) values (1208,'VOUCHER_PROVIDER_UPDATE',1);
insert ignore into str_event_type (id,name,is_enabled) values (1209,'SERVICE_PROVIDER_CREATE',1);
insert ignore into str_event_type (id,name,is_enabled) values (1210,'SERVICE_PROVIDER_UPDATE',1);

insert ignore into str_event_type(id,name,is_enabled) values (1300,'DEVICE_POS_MACHINE_UPDATE',1);
insert ignore into str_event_type(id,name,is_enabled) values (1301,'DEVICE_PHONE_UPDATE',1);

insert ignore into str_language(name,is_enabled) values ('ENGLISH',1);

insert ignore into str_response_code(id,name,is_enabled,response_message) values(1, 'GENERAL_ERROR',1,'A general error occurred');
insert ignore into str_response_code(id,name,is_enabled,response_message) values(-1,'CONFIGURATION_INVALID',1,'Specified configuration is not valid');
insert ignore into str_response_code(id,name,is_enabled,response_message) values(15,'DATA_NOT_FOUND',1,'Data does not exist');
insert ignore into str_response_code(id,name,is_enabled,response_message) values(16,'NOT_SUPPORTED',1,'Not supported');

insert ignore into str_response_code(id,name,is_enabled,response_message) values(2,'INPUT_INCOMPLETE_REQUEST',1,'Incomplete request specified');
insert ignore into str_response_code(id,name,is_enabled,response_message) values(3,'INPUT_INVALID_REQUEST',1,'Invalid request specified');
insert ignore into str_response_code(id,name,is_enabled,response_message) values(4,'INPUT_INVALID_EMAIL',1,'Email provided was not valid');
insert ignore into str_response_code(id,name,is_enabled,response_message) values(5,'INPUT_INVALID_MSISDN',1,'Phone number provided was not valid');
insert ignore into str_response_code(id,name,is_enabled,response_message) values(6,'INPUT_INVALID_FIRST_NAME',1,'First name provided was not valid');
insert ignore into str_response_code(id,name,is_enabled,response_message) values(7,'INPUT_INVALID_LAST_NAME',1,'Last name provided was not valid');
insert ignore into str_response_code(id,name,is_enabled,response_message) values(8,'INPUT_INVALID_USERNAME',1,'Username provided was not valid');
insert ignore into str_response_code(id,name,is_enabled,response_message) values(9,'INPUT_INVALID_PASSWORD',1,'Password provided was not valid');
insert ignore into str_response_code(id,name,is_enabled,response_message) values(10,'INPUT_INVALID_NAME',1,'Name provided was not valid');
insert ignore into str_response_code(id,name,is_enabled,response_message) values(11,'INPUT_INVALID_AMOUNT',1,'Invalid amount specified');
insert ignore into str_response_code(id,name,is_enabled,response_message) values(12,'INPUT_INVALID_WALLET',1,'Invalid wallet specified');
insert ignore into str_response_code(id,name,is_enabled,response_message) values(13,'INPUT_INVALID_CASHIER',1,'Invalid cashier name specified');

insert ignore into str_response_code(id,name,is_enabled,response_message) values(20,'AUTH_INSUFFICIENT_PRIVILEGES',1,'Insufficient privileges for current operation');
insert ignore into str_response_code(id,name,is_enabled,response_message) values(21,'AUTH_AUTHENTICATION_FAILED',1,'Authentication failed');
insert ignore into str_response_code(id,name,is_enabled,response_message) values(22,'AUTH_INCORRECT_PASSWORD',1,'Password is incorrect');
insert ignore into str_response_code(id,name,is_enabled,response_message) values(23,'AUTH_NON_EXISTENT',1,'Account does not exist');

insert ignore into str_response_code(id,name,is_enabled,response_message) values(30,'ACC_ACTIVE',1,'Account is active');
insert ignore into str_response_code(id,name,is_enabled,response_message) values(31,'ACC_INACTIVE',1,'Account is inactive');
insert ignore into str_response_code(id,name,is_enabled,response_message) values(32,'ACC_SUSPENDED',1,'Account has been suspended');
insert ignore into str_response_code(id,name,is_enabled,response_message) values(33,'ACC_CLOSED',1,'Account has been closed');
insert ignore into str_response_code(id,name,is_enabled,response_message) values(34,'ACC_PASSWORD_TRIES_EXCEEDED',1,'Password tries exceeded');
insert ignore into str_response_code(id,name,is_enabled,response_message) values(35,'ACC_PASSWORD_EXPIRED',1,'Password expired');

insert ignore into str_response_code(id,name,is_enabled,response_message) values(40,'CONNECTION_FAILED',1,'Connection failed');
insert ignore into str_response_code(id,name,is_enabled,response_message) values(41,'UNKNOWN_HOST',1,'Unknown host');
insert ignore into str_response_code(id,name,is_enabled,response_message) values(42,'CONNECTION_REFUSED',1,'Connection Refused');
insert ignore into str_response_code(id,name,is_enabled,response_message) values(43,'TIMEOUT',1,'Timeout elapsed before transaction completion');

insert ignore into str_response_code(id,name,is_enabled,response_message) values(51,'INSUFFICIENT_FUNDS',1,'Insufficient funds');

insert ignore into str_response_code(id,name,is_enabled,response_message) values(60,'INSUFFICIENT_STOCK',1,'Insufficient stock');

insert ignore into str_response_code(id,name,is_enabled,response_message) values(80,'EXISTING_DATA_FOUND',1,'Existing data found');

insert ignore into str_response_code(id,name,is_enabled,response_message) values(351,'REGISTRATION_FAILED',1,'Registration Failed');
insert ignore into str_response_code(id,name,is_enabled,response_message) values(352,'PREVIOUS_USERNAME_FOUND',1,'Username has been previously registered');
insert ignore into str_response_code(id,name,is_enabled,response_message) values(353,'PREVIOUS_MSISDN_FOUND',1,'Mobile number has been previously registered');
insert ignore into str_response_code(id,name,is_enabled,response_message) values(354,'PREVIOUS_EMAIL_FOUND',1,'Email has been previously registered');
insert ignore into str_response_code(id,name,is_enabled,response_message) values(355,'PREVIOUS_REGISTRATION_FOUND',1,'Previous registration found');

insert ignore into str_response_code(id,name,is_enabled,response_message) values(0, 'SUCCESS',1,'Successful');
update str_response_code set id = 0 where name = 'SUCCESS';

insert ignore into str_auth_group(auth_group_id, name, is_enabled) values (1, 'SUPER_USER',1);
insert ignore into str_auth_group(auth_group_id, name, is_enabled) values (2, 'WEB_ADMIN',1);
insert ignore into str_auth_group(auth_group_id, name, is_enabled) values (3, 'WEB_USER',1);
insert ignore into str_auth_group(auth_group_id, name, is_enabled) values (4, 'MOBILE_USER',1);

insert ignore into str_role(name, is_enabled) values ('ROLE_WEB_MANAGE_EVD', 1);
insert ignore into str_role(name, is_enabled) values ('ROLE_WEB_MANAGE_STOCK', 1);
insert ignore into str_role(name, is_enabled) values ('ROLE_WEB_MANAGE_PROVIDERS', 1);
insert ignore into str_role(name, is_enabled) values ('ROLE_WEB_MANAGE_VOUCHERS', 1);
insert ignore into str_role(name, is_enabled) values ('ROLE_WEB_MANAGE_SYSTEM', 1);
insert ignore into str_role(name, is_enabled) values ('ROLE_WEB_MANAGE_WALLETS', 1);
insert ignore into str_role(name, is_enabled) values ('ROLE_WEB_MANAGE_USERS', 1);
insert ignore into str_role(name, is_enabled) values ('ROLE_WEB_ADVANCED_MANAGE_USERS', 1);
insert ignore into str_role(name, is_enabled) values ('ROLE_WEB_VIEW_REPORTS', 1);
insert ignore into str_role(name, is_enabled) values ('ROLE_WEB_VIEW_SYSTEM_REPORTS', 1);
insert ignore into str_role(name, is_enabled) values ('ROLE_WEB_VIEW_AUTHENTICATION_REPORTS', 1);
insert ignore into str_role(name, is_enabled) values ('ROLE_WEB_VIEW_PAYMENT_REPORTS', 1);
insert ignore into str_role(name, is_enabled) values ('ROLE_WEB_VIEW_TRANSACTION_REPORTS', 1);
insert ignore into str_role(name, is_enabled) values ('ROLE_WEB_VIEW_SINGLE_REPORTS', 1);
insert ignore into str_role(name, is_enabled) values ('ROLE_WEB_VIEW_SINGLE_AUTHENTICATION_REPORTS', 1);
insert ignore into str_role(name, is_enabled) values ('ROLE_WEB_VIEW_SINGLE_PAYMENT_REPORTS', 1);
insert ignore into str_role(name, is_enabled) values ('ROLE_WEB_VIEW_SINGLE_TRANSACTION_REPORTS', 1);

insert ignore into str_role(name, is_enabled) values ('ROLE_WEB_MANAGE_SETTINGS', 1);

insert ignore into str_role(name, is_enabled) values ('ROLE_POS_MANAGE_SETTINGS', 1);

insert ignore into str_role(name, is_enabled) values ('ROLE_MOBILE_MANAGE_SETTINGS', 1);

/* Insert all roles for SUPER_USER */
insert ignore into str_auth_group_role(auth_group_id,role_id,name,is_enabled) select sg.auth_group_id,sr.role_id,CONCAT(sg.name,'_',sr.name),1 from str_auth_group sg,str_role sr where sg.name = 'SUPER_USER' and sr.name LIKE '%';

/* Insert roles for EMPOWER_SYSTEM_ADMIN */
insert ignore into str_auth_group_role(auth_group_id,role_id,name,is_enabled) select sg.auth_group_id,sr.role_id,CONCAT(sg.name,'_',sr.name),1 from str_auth_group sg,str_role sr where sg.name = 'WEB_ADMIN' and sr.name LIKE 'ROLE_WEB_%';

/* Insert roles for EMPOWER_SYSTEM_CLERK */
insert ignore into str_auth_group_role(auth_group_id,role_id,name,is_enabled) select sg.auth_group_id,sr.role_id,CONCAT(sg.name,'_',sr.name),1 from str_auth_group sg,str_role sr where sg.name = 'WEB_CLERK' and (sr.name IN ('ROLE_WEB_MANAGE_SYSTEM', 'ROLE_WEB_MANAGE_EVD', 'ROLE_WEB_MANAGE_STOCK', 'ROLE_WEB_MANAGE_WALLETS', 'ROLE_WEB_MANAGE_USERS', 'ROLE_WEB_MANAGE_SETTINGS') or sr.name LIKE 'ROLE_WEB_VIEW_%');

/* Insert roles for EMPOWER_SYSTEM_AGENT */
insert ignore into str_auth_group_role(auth_group_id,role_id,name,is_enabled) select sg.auth_group_id,sr.role_id,CONCAT(sg.name,'_',sr.name),1 from str_auth_group sg,str_role sr where sg.name = 'WEB_AGENT' and (sr.name LIKE 'ROLE_WEB_VIEW_SINGLE_%' or sr.name = 'ROLE_WEB_MANAGE_SETTINGS');

/* Insert roles for POS_ADMIN */
insert ignore into str_auth_group_role(auth_group_id,role_id,name,is_enabled) select sg.auth_group_id,sr.role_id,CONCAT(sg.name,'_',sr.name),1 from str_auth_group sg,str_role sr where sg.name = 'POS_ADMIN' and sr.name LIKE ('ROLE_POS_%');

/* Insert roles for MOBILE_ADMIN */
insert ignore into str_auth_group_role(auth_group_id,role_id,name,is_enabled) select sg.auth_group_id,sr.role_id,CONCAT(sg.name,'_',sr.name),1 from str_auth_group sg,str_role sr where sg.name = 'MOBILE_ADMIN' and sr.name LIKE ('ROLE_MOBILE_%');

insert ignore into str_user(first_name,last_name,username,email,msisdn,salt,user_status_id,country_id,language_id,pin,pin_tries) values ('Tsungai','Kaviya','admin','tsungai.kaviya@gmail.com','263785107830','kX4NDlXT2ySxR7e3',30,2,1,'8e77fa77fa7c4c6488fedaaeabf595a09a96cdff2e9196668e817a012f812',0);
insert ignore into str_auth_user (str_user_id,channel_id,auth_group_id,device_id,registration_date,last_auth_date,last_login_date) SELECT su.str_user_id,2,1,null,sysdate(),NULL,NULL FROM str_user su WHERE su.username = 'admin';
insert ignore into str_wallet (wallet_id, current_balance) values (1, '0.00');
update str_user set wallet_id = 1 where str_user_id = 1;