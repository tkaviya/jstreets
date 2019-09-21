Database setup:

1. You need a database server running on an accessible machine. For testing,
you can run a Wamp/Xampp Server on your localhost with the MySQL service started.

2. The file str_persistence/src/main/resources/properties/database/application.properties
is read when accessing the database. You can configure the host, username
and password in that file. For the default configuration:
    i) You need to create a MySQL database called streets. (For wamp/xampp, go to http://localhost/phpmyadmin)
    ii)You need to create a MySQL user called 'streets' with password 'sysP@$$WORDADM1N'
    iii) Grant all privileges to 'streets'@'localhost' on str_server.* (All privs on all tables)

3. Make sure the host, username and password set matches application.properties
