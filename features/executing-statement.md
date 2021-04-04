# **Executing Statement**

***Shortcut :*** *Ctrl + F1*

***File avaiability :*** *`.ml`*

**Description :**

The `Execute OCaml Statement` action can be executed only in `.ml` files ( `.mli` files will later be available for use with OCamlJ ).


Executing this action will send the content of the statement where the cursor is placed in the Editor to the interpreter and move the cursor to the next suitable statement found in the file. 

It will display what has been sent in the editor and the response of ocaml.

> **Note :** The cursor can be placed anywhere in the statement, it will find the entire statement before sending it, even if it is a multi-line statement.
