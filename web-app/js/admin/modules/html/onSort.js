var htmlModuleCurrentText

function htmlModuleStartSort(ui) {
    htmlModuleCurrentText = "";
    var ckedname = getHtmlEditorName(ui);
    htmlModuleCurrentText = CKEDITOR.instances[ckedname].getData();
    delete CKEDITOR.instances[ckedname];
}

function htmlModuleStopSort(ui) {
    alert(htmlModuleCurrentText);
    var dataModuleName = getDataModuleName(ui);
    var ckedname = getHtmlEditorName(ui);
    if (ckedname) {
        // Make ajax call, replace with ckeditor, set content
        CKEDITOR.replace(ckedname);
    }
}