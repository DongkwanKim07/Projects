import dbConnection as dbc

# calls the display all products query


def display_products():
    return dbc.select_all_products()

# calls the display all user accounts query


def display_useraccounts():
    return dbc.select_all_useraccounts()

# calls the create user account query


def protected_create_account(lastname, firstname, phone, username, password):
    dbc.protected_db_create_account(
        lastname, firstname, phone, username, password)

# calls the create user account query


def unprotected_create_account(lastname, firstname, phone, username, password):
    dbc.unprotected_db_create_account(
        lastname, firstname, phone, username, password)

# calls the verify user query


def protected_login(username, password):
    return dbc.protected_db_login(username, password)

# calls the verify user query that is sql injection vulnerable


def unprotected_login(username, password):
    return dbc.unprotected_db_login(username, password)

# calls the verify user query that is sql injection protected


def protected_login_stored_procedure(username, password):
    test = dbc.protected_db_login_stored_procedure(username, password)
    for row in test:
        if (None,) == row:
            test = False
    return test

# calls the create products table/insert queries


def recover_products_table():
    dbc.create_products_table()

# calls the create useraccount table/insert queries


def recover_useraccount_table():
    dbc.create_useraccount_table()
