package org.tsbe.camlj.editor;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;
import org.tsbe.camlj.console.CamlInterface;
import org.tsbe.camlj.console.WindowCaml;

public class ExecuteOcamlFileAction extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {

        final Editor editor = e.getRequiredData(CommonDataKeys.EDITOR);
        final Project project = e.getRequiredData(CommonDataKeys.PROJECT);
        final Document doc = editor.getDocument();
        final VirtualFile file = e.getRequiredData(CommonDataKeys.VIRTUAL_FILE);

        String content = doc.getText(new TextRange(0, doc.getTextLength()));
        String[] statements = content.split(";;");

        WindowCaml.getInstance().clearConsole();
        WindowCaml.getInstance().addHeaderLines("File Execution : " + file.getName());

        CamlInterface.getInstance().nextInIsRead();

        for(String s : statements){

            CamlInterface.getInstance().sendCommand(s.trim() + ";;");

        }

    }

    @Override
    public void update(@NotNull final AnActionEvent e){
        final Project project = e.getProject();
        final Editor editor = e.getData(CommonDataKeys.EDITOR);
        final VirtualFile file = e.getRequiredData(CommonDataKeys.VIRTUAL_FILE);

        e.getPresentation().setEnabledAndVisible(project != null && editor != null && file.getName().endsWith(".ml"));
    }
}
