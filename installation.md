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
