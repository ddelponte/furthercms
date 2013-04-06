jQuery("a.reorder").click(function (event) {
    event.preventDefault();
    var sortableModules = jQuery("#modules-edit ul li section.module");
    openReorderModulesDialog(sortableModules);
});

var reorderModulesDialogForm = jQuery("div#reorder-modules-dialog-form");

var sortableModulesList = jQuery("div#reorder-modules-dialog-form ul.sortable");

/**
 *
 * @param modulesList List of jQuery objects representing sortable modules
 */
function openReorderModulesDialog(moduleList) {
    jQuery.each(moduleList, function (key, value) {
        // update dialog
    });

    reorderModulesDialogForm.dialog("open");
}

/**
 * The module reorder dialog
 */
reorderModulesDialogForm.dialog({
    autoOpen: false,
    modal: true,
    buttons: {
        Cancel: function () {
            $(this).dialog("close");
        },
        "Save": function () {
            //Do stuff
        }
    },
    close: function () {
        //Do stuff
    }
});

sortableModulesList.sortable({
    forcePlaceholderSize: true,
    axis: 'y',
    start: function (event, ui) {
        alert("start");
//        var dataModuleName = getDataModuleName(ui);
//        var functionName = dataModuleName + "StartSort";
//        callModuleSortMethod(functionName, ui);

        jQuery(this).sortable('refreshPositions');
    },
    stop: function (event, ui) {
        alert("stop");
//        var dataModuleName = getDataModuleName(ui);
//        var functionName = dataModuleName + "StopSort";
//        callModuleSortMethod(functionName, ui);
    }
});

sortableModulesList.disableSelection();