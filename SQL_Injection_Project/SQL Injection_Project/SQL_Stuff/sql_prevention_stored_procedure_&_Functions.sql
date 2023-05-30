DELIMITER $$

CREATE DEFINER=`cst8276`@`localhost` PROCEDURE `sql_prevention` (
	IN un VARCHAR(100),
    IN pwd VARCHAR(30), 
    OUT sqlerror int
)	
BEGIN
	DECLARE checked_un INT;
    DECLARE checked_pwd INT;
    SET checked_un = check_username(un);
    SET checked_pwd = check_password(pwd);
	IF (checked_un AND checked_pwd = 1) THEN 
		SELECT * FROM sqlinjection.useraccount WHERE username = un AND password = pwd;
	ELSE
		SET sqlerror = 0;
        SELECT @sqlerror;
    END IF;
END$$

DELIMITER ;

-- ----------------------------------------------------------------------------------------------------
-- ----------------------------------------------------------------------------------------------------
-- ----------------------------------------------------------------------------------------------------

DELIMITER $$

CREATE DEFINER=`cst8276`@`localhost` FUNCTION `check_username`(
    un VARCHAR(100)
)
RETURNS INT
DETERMINISTIC
BEGIN
	DECLARE username_injection INT;
	SET username_injection = 1;

	IF (un like '%''%') THEN 
		SET username_injection = 0;
        
	ELSEIF (un like '%--%') THEN
		SET username_injection = 0;
            
	ELSEIF (un like '%/*%') THEN
		SET username_injection = 0;
		
    ELSEIF (un like '%*/%') THEN 
		SET username_injection = 0;
		
    ELSEIF (un like '%@') THEN 
		SET username_injection = 0;
		
	ELSEIF (un like '%@@%') THEN 
		SET username_injection = 0;
		
	ELSEIF (un like '%char%') THEN 
		SET username_injection = 0;
		
	ELSEIF (un like '%nchar%') THEN 
		SET username_injection = 0;
		
	ELSEIF (un like '%varchar%') THEN 
		SET username_injection = 0;
		
	ELSEIF (un like '%nvarchar%') THEN 
		SET username_injection = 0;
			
	ELSEIF (un like '%select%') THEN 
		SET username_injection = 0;
		
	ELSEIF (un like '%insert%') THEN 
		SET username_injection = 0;
		
	ELSEIF (un like '%update%') THEN 
		SET username_injection = 0;
		
	ELSEIF (un like '%delete%') THEN 
		SET username_injection = 0;
		
	ELSEIF (un like '%from%') THEN 
		SET username_injection = 0;
		
	ELSEIF (un like '%table%') THEN 
		SET username_injection = 0;
		
	ELSEIF (un like '%drop%') THEN 
		SET username_injection = 0;
		
	ELSEIF (un like '%create%') THEN 
		SET username_injection = 0;
		
	ELSEIF (un like '%alter%') THEN 
		SET username_injection = 0;
		 
	ELSEIF (un like '%begin%') THEN 
		SET username_injection = 0;
		
	ELSEIF (un like '%end%') THEN 
		SET username_injection = 0;
		
	ELSEIF (un like '%grant%') THEN 
		SET username_injection = 0;
		
	ELSEIF (un like '%deny%') THEN 
		SET username_injection = 0;
		
	ELSEIF (un like '%exec%') THEN 
		SET username_injection = 0;
		
	ELSEIF (un like '%sp_%') THEN 
		SET username_injection = 0;
		
	ELSEIF (un like '%xp_%') THEN 
		SET username_injection = 0;
		 
	ELSEIF (un like '%cursor%') THEN 
		SET username_injection = 0;
		
	ELSEIF (un like '%fetch%') THEN 
		SET username_injection = 0;
		 
	ELSEIF (un like '%kill%') THEN 
		SET username_injection = 0;
		
	ELSEIF (un like '%open%') THEN 
		SET username_injection = 0;
		 
	ELSEIF (un like '%sysobjects%') THEN 
		SET username_injection = 0;
		
	ELSEIF (un like '%syscolumns%') THEN 
		SET username_injection = 0;
        
	ELSEIF (un like '%sys%') THEN 
		SET username_injection = 0;
        
	END IF;
    	RETURN username_injection;
END$$

DELIMITER ;

-- ----------------------------------------------------------------------------------------------------
-- ----------------------------------------------------------------------------------------------------
-- ----------------------------------------------------------------------------------------------------

DELIMITER $$

CREATE DEFINER=`cst8276`@`localhost` FUNCTION `check_password`(
    pwd VARCHAR(30)
)
RETURNS INT
DETERMINISTIC
BEGIN
	DECLARE password_injection INT;
	SET password_injection = 1;

	IF (pwd like '%''%') THEN 
		SET password_injection = 0;
        
	ELSEIF (pwd like '%--%') THEN
		SET password_injection = 0;
            
	ELSEIF (pwd like '%/*%') THEN
		SET password_injection = 0;
		
    ELSEIF (pwd like '%*/%') THEN 
		SET password_injection = 0;
		
    ELSEIF (pwd like '%@') THEN 
		SET password_injection = 0;
		
	ELSEIF (pwd like '%@@%') THEN 
		SET password_injection = 0;
		
	ELSEIF (pwd like '%char%') THEN 
		SET password_injection = 0;
		
	ELSEIF (pwd like '%nchar%') THEN 
		SET password_injection = 0;
		
	ELSEIF (pwd like '%varchar%') THEN 
		SET password_injection = 0;
		
	ELSEIF (pwd like '%nvarchar%') THEN 
		SET password_injection = 0;
			
	ELSEIF (pwd like '%select%') THEN 
		SET password_injection = 0;
		
	ELSEIF (pwd like '%insert%') THEN 
		SET password_injection = 0;
		
	ELSEIF (pwd like '%update%') THEN 
		SET password_injection = 0;
		
	ELSEIF (pwd like '%delete%') THEN 
		SET password_injection = 0;
		
	ELSEIF (pwd like '%from%') THEN 
		SET password_injection = 0;
		
	ELSEIF (pwd like '%table%') THEN 
		SET password_injection = 0;
		
	ELSEIF (pwd like '%drop%') THEN 
		SET password_injection = 0;
		
	ELSEIF (pwd like '%create%') THEN 
		SET password_injection = 0;
		
	ELSEIF (pwd like '%alter%') THEN 
		SET password_injection = 0;
		 
	ELSEIF (pwd like '%begin%') THEN 
		SET password_injection = 0;
		
	ELSEIF (pwd like '%end%') THEN 
		SET password_injection = 0;
		
	ELSEIF (pwd like '%grant%') THEN 
		SET password_injection = 0;
		
	ELSEIF (pwd like '%deny%') THEN 
		SET password_injection = 0;
		
	ELSEIF (pwd like '%exec%') THEN 
		SET password_injection = 0;
		
	ELSEIF (pwd like '%sp_%') THEN 
		SET password_injection = 0;
		
	ELSEIF (pwd like '%xp_%') THEN 
		SET password_injection = 0;
		 
	ELSEIF (pwd like '%cursor%') THEN 
		SET password_injection = 0;
		
	ELSEIF (pwd like '%fetch%') THEN 
		SET password_injection = 0;
		 
	ELSEIF (pwd like '%kill%') THEN 
		SET password_injection = 0;
		
	ELSEIF (pwd like '%open%') THEN 
		SET password_injection = 0;
		 
	ELSEIF (pwd like '%sysobjects%') THEN 
		SET password_injection = 0;
		
	ELSEIF (pwd like '%syscolumns%') THEN 
		SET password_injection = 0;
		
	ELSEIF (pwd like '%sys%') THEN 
		SET password_injection = 0;
        
	END IF;
		RETURN password_injection;
END$$

DELIMITER ;

