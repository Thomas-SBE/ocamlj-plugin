[![Build](https://github.com/Thomas-SBE/ocamlj-plugin/actions/workflows/build.yml/badge.svg)](https://github.com/Thomas-SBE/ocamlj-plugin/actions/workflows/build.yml)

<img align="left" alt="OCamlJ" width="64px" src="https://raw.githubusercontent.com/Thomas-SBE/ocamlj-plugin/138c2932e2446aa9d56cd5284e5944991f576d0e/src/main/resources/META-INF/pluginIcon.svg"><h1><b>OCamlJ</b> <sub><sub><sup><sup>Embeded OCaml IntelliJ Interpreter, File & Statement Executer</sup></sup></sub></sub></h1>

<!-- Plugin description -->
**OcamlJ** is an embeded Ocaml terminal / console in IntelliJ, it also allows file reading
and executing with one click. This plugin **does not highlight syntax yet**, it is recommended
to download another plugin for that. 

I suggest [ReasonML Plugin](https://plugins.jetbrains.com/plugin/9440-reasonml) for syntax highlighting, for now.

Full functionnalities description [here](https://github.com/Thomas-SBE/ocamlj-plugin#readme).

<!-- Plugin description end -->

------

## Getting Started

**For both installations an OCaml installation is required, you can find instructions [HERE](https://ocaml.org/docs/install.html) !**

### <img aling="left" alt="Windows" width="16px" src="https://img.icons8.com/color/72/microsoft.png" /> For Windows Users :

To properly use this plugin it is required to define where your OCaml folder is, this
can be done in the `Settings -> Tools -> OCamlJ`, from there you can either type the path to where
OCaml is installed or browse for it.

> **_IMPORTANT NOTE :_** _You must select a folder containing the following directories :_ 
> 
> `.\bin` _where ocaml.exe is installed._
> 
> `.\lib\ocaml` _where all ocaml modules are stored._

**After defining the location of OCaml, either restart IntelliJ or Restart the interpreter, see below under `Restart the OCaml Interpreter` section in actions.**

### <img aling="left" alt="Linux" width="16px" src="https://img.icons8.com/color/72/linux.png" /> For Linux Users :

If Ocaml has been installed properly, you should not need to set an installation location in the settings.
To verify that Ocaml is properly installed, launch a terminal and type `ocaml`, it should let you in the Ocaml interpreter.
This is a requirement for Linux users !

## Current Features

> *Note that this plugin is still under development, errors and bugs may occur, please report them by creating issues* [*here*](https://github.com/Thomas-SBE/ocamlj-plugin/issues).

## Ocaml Interpreter
By default the interpreter is located in the bottom right hand corner of the IDE,
simply open it by clicking it. It can otherwise be found in the `View -> Tool Windows -> Ocaml Interpreter`.

> **Note :** You may setup your OCaml according to the `Getting started` guide above depending on your operating system.

![Interpreter](https://i.imgur.com/DiUzmw8.png)

If everything has been set up correctly, the panel should open and display your current version of OCaml.

![Interpreter Ready](https://i.imgur.com/MvbWU69.png)

From there, you can enter commands like you would in your OCaml terminal next to the `#`, or use other features of the plugin.

## Actions
### Execute Ocaml File

The `Execute OCaml File` action can be executed only in `.ml` files ( `.mli` files will later be available for use with OCamlJ )
Executing this action will send the content of the current file opened in the Editor to the interpreter. It will then display in the
interpreter which file you just executed, and the response of ocaml after each statement.

![Execute File in Interpreter](https://i.imgur.com/NyKtXXZ.png)

### Execute Selection

The `Execute OCaml Selection` action can be executed only in `.ml` files ( `.mli` files will later be available for use with OCamlJ )
Executing this action will send the content of the current selection in the Editor to the interpreter. 
It will display what has been sent in the editor and the response of ocaml.

> **Note :** It only takes the selection and not its surroundings, event if the statement is not complete, in which
> case it will mostly result in an OCaml error !

![Execute Selection in Interpreter](https://i.imgur.com/SPNhr5z.png)

### Restart the OCaml Interpreter

In case your interpreter run into trouble, or crash after a certain error, there is a button to reload OCaml which can be useful in case of an infinite loop
by pressing the button it will stop the old instance of OCaml, all previous given variables and functions will need to be executed again.
Reloading the instance may take around a second or so.

![Reloading Core](https://i.imgur.com/PeiTqTh.png)

# Feedback & Bugs-Reports

Feedback, positive or negative is welcome, you can post suggestions to the [Discussion Panel](https://github.com/Thomas-SBE/ocamlj-plugin/discussions). If you encounter
any bug or error, please report them in the [Issues Panel](https://github.com/Thomas-SBE/ocamlj-plugin/issues).

*Thanks a lot for visiting and hopefuly using my plugin !*
