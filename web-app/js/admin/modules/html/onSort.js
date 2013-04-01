var htmlModuleCurrentText

function htmlModuleStartSort(ui) {
    htmlModuleCurrentText = "";
    var ckedname = getHtmlEditorName(ui);
    htmlModuleCurrentText = CKEDITOR.instances[ckedname].getData();
    delete CKEDITOR.instances[ckedname];
}

function htmlModuleStopSort(ui) {
    var ckedname = getHtmlEditorName(ui);
    CKEDITOR.instances[ckedname].destroy(true);
    renderHtmlEditor(ui, ckedname);
}

/**
 *
 * @param ui The sortable ui element.  Usually a list item
 * @return The unique name of the HTML editor as defined in the DOM
 */
function getHtmlEditorName(ui) {
    var ckedname = $(ui.item).find("div.html_ckeditor").find("span").attr("id");
    if (ckedname) {
        var ckedname_arr = ckedname.split("_");
        ckedname = ckedname_arr[1] + "_" + ckedname_arr[2];
    }
    return ckedname;
}

function renderHtmlEditor(ui, ckedname) {
    if (ui) {
        // Make ajax call, replace with ckeditor, set content
        var url = getDataHtmlModuleEditorUrl(ui);
        var jqxhr = $.ajax(url)
                .done(function (editor) {
                    jQuery("div.html_ckeditor").html(editor);
                    CKEDITOR.instances[ckedname].setData(htmlModuleCurrentText);
                })
                .fail(function () {
                    alert("error");
                });
    }

}

function getDataHtmlModuleEditorUrl(ui) {
    var controller = $(ui.item).find("div.html_ckeditor").attr("data-html-module-editor-action");
    return controller;
}