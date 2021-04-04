# **Executing Selection**

***Shortcut :*** *None*

***File avaiability :*** *`.ml`*

**Description :**

The `Execute OCaml Selection` action can be executed only in `.ml` files ( `.mli` files will later be available for use with OCamlJ ).


Executing this action will send the content of the current selection in the Editor to the interpreter. 

It will display what has been sent in the editor and the response of ocaml.

> **Note :** It only takes the selection and not its surroundings, event if the statement is not complete, in which
> case it will mostly result in an OCaml error !
