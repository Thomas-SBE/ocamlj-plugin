package org.tsbe.camlj.editor;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Caret;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;
import org.tsbe.camlj.console.CamlInterface;

public class ExecuteOcamlStatementAction extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        final Editor editor = e.getRequiredData(CommonDataKeys.EDITOR);
        final Project project = e.getRequiredData(CommonDataKeys.PROJECT);
        final Document doc = editor.getDocument();

        Caret caret = editor.getCaretModel().getPrimaryCaret();

        String documentText = doc.getText();

        int startOffset = 0;
        int endOffset = 0;
        int nextOffset = 0;

        boolean startFound = false;
        boolean endFound = false;
        boolean nextFound = false;
        boolean cantFindNext = false;

        String statement = "";

        while(!startFound || !endFound || !nextFound && !cantFindNext){

            statement = doc.getText(new TextRange(caret.getOffset() - startOffset, caret.getOffset() + endOffset));

            startFound = statement.startsWith(";;") && !statement.equals(";;") || (caret.getOffset()-startOffset <= 0);
            endFound = statement.endsWith(";;") && !statement.equals(";;")  || (caret.getOffset()+endOffset >= doc.getText().length());


            if(endFound && !cantFindNext) {
                String next = doc.getText(new TextRange(endOffset, caret.getOffset() + nextOffset)).toUpperCase();
                if(caret.getOffset() + nextOffset >= doc.getText().length()-1){ cantFindNext = true; continue;
                }else if(next.equals("")){
                }else if(next.charAt(next.length()-1) >= 'A' && next.charAt(next.length()-1) <= 'Z' || next.charAt(next.length()-1) >= '0' && next.charAt(next.length()-1) <= '9'){
                    nextFound = true;
                }
            }

            startOffset += !startFound ? 1 : 0;
            endOffset += !endFound ? 1 : 0;
            nextOffset += !nextFound ? 1 : 0;
        }

        if(statement.startsWith(";;")){statement = statement.replaceFirst(";;", "");}

        CamlInterface.getInstance().sendCommandWithAction(statement.trim());

        if(nextFound)
            caret.moveToOffset(caret.getOffset() + nextOffset - 1);

    }

    @Override
    public void update(@NotNull final AnActionEvent e){
        final Project project = e.getProject();
        final Editor editor = e.getData(CommonDataKeys.EDITOR);
        final VirtualFile file = e.getRequiredData(CommonDataKeys.VIRTUAL_FILE);
        e.getPresentation().setEnabledAndVisible(project != null && editor != null && file.getName().endsWith(".ml"));
    }
}
