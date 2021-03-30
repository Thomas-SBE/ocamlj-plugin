package com.github.thomassbe.camlintellij.services

import com.github.thomassbe.camlintellij.MyBundle
import com.intellij.openapi.project.Project

class MyProjectService(project: Project) {

    init {
        println(MyBundle.message("projectService", project.name))
    }
}
