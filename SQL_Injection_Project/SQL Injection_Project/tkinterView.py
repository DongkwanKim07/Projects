import tkinter as tk
from tkinter import *
import sqlInjectionController as sqlic

# Select All useraccounts data


def accountInfo():
    global my_w
    my_w = tk.Tk()
    my_w.geometry("1920x1080")
    my_w.title("User Account Information")

    account_res = sqlic.display_useraccounts()

    Label(my_w, text="Accounts", font="bold").pack()
    for row in account_res:
        Label(my_w, text=" {} ".format(
            f"User ID: {row[0]}\n First Name: {row[2]}, Last Name: {row[1]}, Phone Number: {row[4]}\n Username: {row[3]}, Password: {row[5]}"), font="bold").pack()
        Label(my_w, text="").pack()
    Button(my_w, text="Back", bg="grey", width=8,
           height=1, command=useraccounts_destroy).pack()


# Select All products data
def productInfo():
    global pro_w
    pro_w = tk.Tk()
    pro_w.geometry("1920x1080")
    pro_w.title("Products")

    mycur = sqlic.display_products()

    Label(pro_w, text="Products", font="bold").pack()
    Label(pro_w, text="").pack()
    for row in mycur:
        Label(pro_w, text=" {} ".format(f"{row[0]}")).pack()
        Label(pro_w, text="").pack()
    Button(pro_w, text="Back", bg="grey", width=8,
           height=1, command=products_destroy).pack()


# protected register menu
def Register1():
    global root2
    root2 = Toplevel(root)
    root2.title("Register")
    root2.geometry("300x415")
    global create_firstname
    global create_lastname
    global create_phone
    global create_username
    global create_password
    Label(root2, text="Protected Register", bg="grey",
          fg="black", font="bold", width=300).pack()
    create_firstname = StringVar()
    create_lastname = StringVar()
    create_phone = StringVar()
    create_username = StringVar()
    create_password = StringVar()
    Label(root2, text="").pack()
    Label(root2, text="First Name:", font="bold").pack()
    Entry(root2, textvariable=create_firstname).pack()
    Label(root2, text="").pack()
    Label(root2, text="Last Name:", font="bold").pack()
    Entry(root2, textvariable=create_lastname).pack()
    Label(root2, text="").pack()
    Label(root2, text="Phone number:", font="bold").pack()
    Entry(root2, textvariable=create_phone).pack()
    Label(root2, text="").pack()
    Label(root2, text="Username:", font="bold").pack()
    Entry(root2, textvariable=create_username).pack()
    Label(root2, text="").pack()
    Label(root2, text="Password:", font="bold").pack()
    Entry(root2, textvariable=create_password).pack()
    Label(root2, text="").pack()
    Button(root2, text="Sign up", font="bold", command=create_user).pack()
    Label(root2, text="")

# unprotected register menu


def Register2():
    global root2
    root2 = Toplevel(root)
    root2.title("Register")
    root2.geometry("300x415")
    global create_firstname
    global create_lastname
    global create_phone
    global create_username
    global create_password
    Label(root2, text="Unprotected Register", bg="grey",
          fg="black", font="bold", width=300).pack()
    create_firstname = StringVar()
    create_lastname = StringVar()
    create_phone = StringVar()
    create_username = StringVar()
    create_password = StringVar()
    Label(root2, text="").pack()
    Label(root2, text="First Name:", font="bold").pack()
    Entry(root2, textvariable=create_firstname).pack()
    Label(root2, text="").pack()
    Label(root2, text="Last Name:", font="bold").pack()
    Entry(root2, textvariable=create_lastname).pack()
    Label(root2, text="").pack()
    Label(root2, text="Phone number:", font="bold").pack()
    Entry(root2, textvariable=create_phone).pack()
    Label(root2, text="").pack()
    Label(root2, text="Username:", font="bold").pack()
    Entry(root2, textvariable=create_username).pack()
    Label(root2, text="").pack()
    Label(root2, text="Password:", font="bold").pack()
    Entry(root2, textvariable=create_password).pack()
    Label(root2, text="").pack()
    Button(root2, text="Sign up", font="bold",
           command=unprotected_create_user).pack()
    Label(root2, text="")


# login menu
def Login1():
    global root2
    root2 = Toplevel(root)
    root2.title("Login")
    root2.geometry("300x300")
    global username_varify
    global password_varify
    Label(root2, text="Protected Login", bg="grey",
          fg="black", font="bold", width=300).pack()
    username_varify = StringVar()
    password_varify = StringVar()
    Label(root2, text="").pack()
    Label(root2, text="Username:", font="bold").pack()
    Entry(root2, textvariable=username_varify).pack()
    Label(root2, text="").pack()
    Label(root2, text="Password:", font="bold").pack()
    Entry(root2, textvariable=password_varify).pack()
    Label(root2, text="").pack()
    Button(root2, text="Sign in", font="bold", command=verify_login).pack()
    Label(root2, text="")

# unprotected login menu


def Login2():
    global root2
    root2 = Toplevel(root)
    root2.title("Login")
    root2.geometry("300x300")
    global username_varify
    global password_varify
    Label(root2, text="Unprotected Login",
          bg="grey", fg="black", font="bold", width=300).pack()
    username_varify = StringVar()
    password_varify = StringVar()
    Label(root2, text="").pack()
    Label(root2, text="Username:", font="bold").pack()
    Entry(root2, textvariable=username_varify).pack()
    Label(root2, text="").pack()
    Label(root2, text="Password:", font="bold").pack()
    Entry(root2, textvariable=password_varify).pack()
    Label(root2, text="").pack()
    Button(root2, text="Sign in", font="bold",
           command=unprotected_verify_login).pack()
    Label(root2, text="")

# protected login menu


def Login3():
    global root2
    root2 = Toplevel(root)
    root2.title("Login")
    root2.geometry("300x300")
    global username_varify
    global password_varify
    Label(root2, text="Protected Login",
          bg="grey", fg="black", font="bold", width=300).pack()
    username_varify = StringVar()
    password_varify = StringVar()
    Label(root2, text="").pack()
    Label(root2, text="Username:", font="bold").pack()
    Entry(root2, textvariable=username_varify).pack()
    Label(root2, text="").pack()
    Label(root2, text="Password:", font="bold").pack()
    Entry(root2, textvariable=password_varify).pack()
    Label(root2, text="").pack()
    Button(root2, text="Sign in", font="bold",
           command=protected_verify_login).pack()
    Label(root2, text="")


def logg_destroy():
    logg.destroy()
    root2.destroy()


def useraccounts_destroy():
    my_w.destroy()


def products_destroy():
    pro_w.destroy()


def fail_destroy():
    fail.destroy()

# displays user information if successfully logged in


def display_user_information(results):
    global logg
    logg = Toplevel(root2)
    logg.title("Welcome")
    logg.geometry("1920x1080")

    Label(logg, text="Account Information", font="bold").pack()
    for row in results:
        Label(logg, text="Welcome {} ".format(
            row[2] + " " + row[1]), font="bold").pack()
        Label(logg, text=" {} ".format(
            f"User ID: {row[0]}\n First Name: {row[2]}, Last Name: {row[1]}, Phone Number: {row[4]}\n Username: {row[3]}, Password: {row[5]}"), font="bold").pack()
        Label(logg, text="").pack()
    Button(logg, text="Sign Out", bg="grey", width=10,
           height=2, command=logg_destroy).pack()

# displays error message if unsucessful log in


def login_failed():
    global fail
    fail = Toplevel(root2)
    fail.title("Invalid")
    fail.geometry("400x100")
    Label(fail, text="Incorrect login Credentials", fg="red", font="bold").pack()
    Label(fail, text="").pack()
    Button(fail, text="Ok", bg="grey", width=8,
           height=1, command=fail_destroy).pack()

# fetches user info. If it doesn't exist the login_failed function is called


def verify_login():
    username = username_varify.get()
    password = password_varify.get()

    results = sqlic.protected_login(username, password)
    if results:
        display_user_information(results)
    else:
        login_failed()

# passes protected new user values into function


def create_user():
    lastname = create_lastname.get()
    firstname = create_firstname.get()
    phone = create_phone.get()
    username = create_username.get()
    password = create_password.get()

    sqlic.protected_create_account(
        lastname, firstname, phone, username, password)
    user_created_message()

# passes unprotected new user values into function


def unprotected_create_user():
    lastname = create_lastname.get()
    firstname = create_firstname.get()
    phone = create_phone.get()
    username = create_username.get()
    password = create_password.get()

    sqlic.unprotected_create_account(
        lastname, firstname, phone, username, password)
    user_created_message()


# displays user created message
def user_created_message():
    global logg
    logg = Toplevel(root2)
    logg.title("User Created")
    logg.geometry("400x100")
    Label(logg, text="User Successfully Created",
          fg="green", font="bold").pack()
    Label(logg, text="").pack()
    Button(logg, text="Ok", bg="grey", width=8,
           height=1, command=logg_destroy).pack()


# fetches user info using unprotected login function. If it doesn't exist the login_failed function is called.
def unprotected_verify_login():
    username = username_varify.get()
    password = password_varify.get()

    results = sqlic.unprotected_login(username, password)
    if results:
        display_user_information(results)
    else:
        login_failed()

# fetches user info using protected login function. If it doesn't exist the login_failed function is called.


def protected_verify_login():
    username = username_varify.get()
    password = password_varify.get()

    results = sqlic.protected_login_stored_procedure(username, password)
    if results == False:
        login_failed()
    elif (not results):
        login_failed()
    else:
        display_user_information(results)


# Recover Products data
def recover_products():
    sqlic.recover_products_table()

# Recover User Account data


def recover_useraccounts():
    sqlic.recover_useraccount_table()

# main menu display with options


def main_menu():
    global root
    root = Tk()
    root.title("SQL Injection Demo")
    root.geometry("300x525")
    Label(root, text="SQL Injection Demo", font="bold",
          bg="grey", fg="black", width=300).pack()
    Label(root, text="").pack()

    Button(root, text="User Accounts", width="15", height="1",
           font="bold", command=accountInfo).pack()
    Label(root, text="").pack()

    Button(root, text="Products", width="8", height="1",
           font="bold", command=productInfo).pack()
    Label(root, text="").pack()

    Button(root, text="Prepared Statement Register", width="24", height="1",
           font="bold", fg="blue", command=Register1).pack()
    Label(root, text="").pack()

    Button(root, text="Prepared Statement Login", width="22",
           height="1", font="bold", fg="blue", command=Login1).pack()
    Label(root, text="").pack()

    Button(root, text="Unprotected Register", width="18", height="1",
           font="bold", fg="red", command=Register2).pack()
    Label(root, text="").pack()

    Button(root, text="Unprotected Login", width="17", height="1",
           font="bold", fg="red", command=Login2).pack()
    Label(root, text="").pack()

    Button(root, text="Stored Procedure Login", width="20",
           height="1", font="bold", fg="green", command=Login3).pack()
    Label(root, text="").pack()

    Button(root, text="Recover Products", width="15", height="1",
           font="bold", command=recover_products).pack()
    Label(root, text="").pack()

    Button(root, text="Recover User Accounts", width="20", height="1",
           font="bold", command=recover_useraccounts).pack()
    Label(root, text="").pack()
