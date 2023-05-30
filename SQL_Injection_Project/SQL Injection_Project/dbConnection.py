import mysql.connector
from mysql.connector import errorcode

# database connection


def db_Connection():
    """Connects to the database."""
    try:
        db_conn = mysql.connector.connect(
            host='localhost',
            user='cst8276',
            passwd='8276',
            database='sqlinjection'
        )
    except mysql.connector.Error as err:
        if err.errno == errorcode.ER_ACCESS_DENIED_ERROR:
            print("Something is wrong with your user name or password")
        elif err.errno == errorcode.ER_BAD_DB_ERROR:
            print("Database does not exist")
        else:
            print(err)
    return db_conn

# selects all products from the database


def select_all_products():
    db_conn = db_Connection()
    cursor = db_conn.cursor()
    sql = "SELECT * FROM products;"
    cursor.execute(sql)
    return cursor.fetchall()

# selects all the useraccounts from the database


def select_all_useraccounts():
    db_conn = db_Connection()
    cursor = db_conn.cursor()
    sql = "SELECT * FROM useraccount;"
    cursor.execute(sql)
    return cursor.fetchall()

# creates a new user account


def protected_db_create_account(lastname, firstname, phone, username, password):
    db_conn = db_Connection()
    cursor = db_conn.cursor()
    sql = "INSERT INTO useraccount(last_name, first_name, username, phone, password) VALUES (%s, %s, %s, %s, %s);"
    cursor.execute(sql, (lastname, firstname, phone, username, password))
    db_conn.commit()
    cursor.close()
    db_conn.close()

# unprotected insert statement


def unprotected_db_create_account(lastname, firstname, phone, username, password):
    db_conn = db_Connection()
    cursor = db_conn.cursor()
    sql = "INSERT INTO useraccount(last_name, first_name, username, phone, password) VALUES ('" + \
        lastname + "', '" + firstname + "', '" + username + \
        "', '" + phone + "', '" + password + "');"
    cursor.execute(sql)
    db_conn.commit()
    cursor.close()
    db_conn.close()


# verifies that the user exists


def protected_db_login(username, password):
    db_conn = db_Connection()
    cursor = db_conn.cursor()
    sql = "SELECT * FROM useraccount WHERE username = %s and password = %s;"
    cursor.execute(sql, (username, password))
    return cursor.fetchall()


# unprotected sql injection to the database


def unprotected_db_login(username, password):
    db_conn = db_Connection()
    cursor = db_conn.cursor()
    sql = "SELECT * FROM useraccount WHERE username = '" + \
        username + "' AND password = '" + password + "';"
    cursor.execute(sql)
    return cursor.fetchall()

# protected from sql injection to the database


def protected_db_login_stored_procedure(username, password):
    db_conn = db_Connection()
    cursor = db_conn.cursor()
    args = [username, password, 1]
    cursor.callproc('sql_prevention', args)
    for result in cursor.stored_results():
        return result.fetchall()
    cursor.close()
    db_conn.close()

# creates the products table again and inserts values


def create_products_table():
    db_conn = db_Connection()
    cursor = db_conn.cursor()
    sql = "CREATE TABLE IF NOT EXISTS `SQLInjection`.`products`(`product_name` VARCHAR(60) NOT NULL)ENGINE = InnoDB;"
    cursor.execute(sql)
    db_conn.commit()

    sql = "INSERT INTO `SQLInjection`.`products`(`product_name`) VALUES ('Oracle 19C Cracked Key'), ('Unlimited Burger King Coupons'), ('TempleOS Key'),('Bulk Supply of Werthers Original'),('iPhone 14 Pro Max');"
    cursor.execute(sql)
    db_conn.commit()
    cursor.close()
    db_conn.close()

# creates the useraccount table again and inserts values


def create_useraccount_table():
    db_conn = db_Connection()
    cursor = db_conn.cursor()
    sql = "CREATE TABLE IF NOT EXISTS `SQLInjection`.`useraccount`(`userid` INT NOT NULL AUTO_INCREMENT, `last_name` VARCHAR(50) NOT NULL, `first_name` VARCHAR(50) NOT NULL, `username` VARCHAR(100) NOT NULL, `phone` VARCHAR(10) NOT NULL, `password` VARCHAR(30) NOT NULL, PRIMARY KEY (`userid`)) ENGINE = InnoDB;"
    cursor.execute(sql)
    db_conn.commit()

    sql1 = "INSERT INTO `SQLInjection`.`useraccount`(`last_name`, `first_name`, `username`, `phone`, `password`) VALUES ('Zangerl', 'Alan', 'zang0005', '6137683838', 'iloveoracle');"
    sql2 = "INSERT INTO `SQLInjection`.`useraccount`(`last_name`, `first_name`, `username`, `phone`, `password`) VALUES ('Parker', 'Nathan', 'park2342', '6138348292', 'ilovemysql');"
    sql3 = "INSERT INTO `SQLInjection`.`useraccount`(`last_name`, `first_name`, `username`, `phone`, `password`) VALUES ('Taylor', 'Rhys', 'tayl7685', '6130986232', 'ilovepostgres');"
    sql4 = "INSERT INTO `SQLInjection`.`useraccount`(`last_name`, `first_name`, `username`, `phone`, `password`) VALUES ('Goulding', 'Aaron', 'goul1262', '6134332323', 'ilovemsaccess');"
    sql5 = "INSERT INTO `SQLInjection`.`useraccount`(`last_name`, `first_name`, `username`, `phone`, `password`) VALUES ('Kim', 'Dongkwan', 'kim4353', '8194657373', 'ilovemariadb');"
    sql6 = "INSERT INTO `SQLInjection`.`useraccount`(`last_name`, `first_name`, `username`, `phone`, `password`) VALUES ('Kim', 'Taeyeon', 'kim8672', '6473832821', 'ilovemongodb');"
    cursor.execute(sql1)
    cursor.execute(sql2)
    cursor.execute(sql3)
    cursor.execute(sql4)
    cursor.execute(sql5)
    cursor.execute(sql6)
    db_conn.commit()
    cursor.close()
    db_conn.close()
