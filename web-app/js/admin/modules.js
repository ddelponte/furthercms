jQuery(document).ready(function () {
    var buttonStatus = jQuery("section#modules-edit div#button-status");
    var totalErrors = 0;
    var totalSavedForms = 0;

    // When user clicks the save button, submit all module forms
    jQuery("section#modules-edit a.btn").click(function (event) {
        event.preventDefault();
        totalErrors = 0;
        totalSavedForms = 0;
        saveModules();
    });

    // Select all module forms and submit them
    function saveModules() {
        CKupdate();

        buttonStatus.attr("style", "visibility: visible");
        buttonStatus.text(buttonStatus.attr("data-saving-message"));

        var moduleForms = jQuery("section#modules-edit form");
        var totalModuleForms = moduleForms.size();

        clearErrors();

        moduleForms.each(function (index, element) {
            event.preventDefault();
            var form = jQuery(this);

            form.ajaxSubmit({
                success: function (jsonData) {

                    if (jsonData.success) {
                        totalSavedForms++;
                        console.log('success');
                    } else {
                        totalSavedForms++;
                        totalErrors++;
                        showErrors(jsonData.errors, form.prev("div.errors"));
                    }

                    updateButtonSaveStatus(totalModuleForms);

                },

                error: function () {
                    totalSavedForms++;
                    totalErrors++;
                    updateButtonSaveStatus(totalModuleForms);
                    console.log('error');
                }

            });

        });

    }

    function clearErrors() {
        var errorsElement = jQuery("div.errors");
        errorsElement.hide();
        errorsElement.empty();
        jQuery("input").removeClass("error");
    }

    /**
     *
     * @return {string} The current date and time.  For example Mar 15, 2525 at 10:00 PM
     */
    function getFormattedDateAndTime() {
        var now = new Date();
        var formattedDate = now.format("mmm dd, yyyy");
        var formattedTime = now.format("h:MM TT");
        return formattedDate + " at " + formattedTime;
    }

    /**
     * Display 'Saving...' under the buttons.  Clear it when done.
     * @param totalModuleForms Total number of forms being submitted
     */
    function updateButtonSaveStatus(totalModuleForms) {

        if (totalModuleForms == totalSavedForms) {
            if (totalErrors == 0) {
                buttonStatus.text(buttonStatus.attr("data-saved-message") + " " + getFormattedDateAndTime());
                buttonStatus.effect("highlight", {}, 3000);
            } else {
                buttonStatus.text(buttonStatus.attr("data-error-saving-message") + " " + getFormattedDateAndTime());
                buttonStatus.effect("highlight", {color: "#f2dede"}, 3000);
            }
        }

    }

    /**
     *
     * @param errors A map of form fields and their error messages
     * @param element The element in which the errors should be displayed
     */
    function showErrors(errors, element) {

        for (field in errors) {
            element.append(errors[field]);
            jQuery('input[name=\"' + field + '\"]').addClass('error');
        }

        element.show();

    }

    // Submit all module forms when user presses enter
    jQuery("section#modules-edit input").bind('keydown', 'return', function (event) {
        event.preventDefault();
        saveModules();
    });

    // Allow ajax submission of ckeditor
    function CKupdate() {
        for (instance in CKEDITOR.instances) {
            CKEDITOR.instances[instance].updateElement();
        }
    }

    // Sortable modules
    var sortableModules = jQuery("#modules-edit ul.sortable");

    sortableModules.sortable({
        placeholder: 'highlight',
        forcePlaceholderSize: true,
        axis: 'y',
        start: function (event, ui) {
            var dataModuleName = getDataModuleName(ui);
            var functionName = dataModuleName + "StartSort";
            callModuleSortMethod(functionName, ui);

            jQuery(this).sortable('refreshPositions');
        },
        stop: function (event, ui) {
            var dataModuleName = getDataModuleName(ui);
            var functionName = dataModuleName + "StopSort";
            callModuleSortMethod(functionName, ui);
        }
    });

    sortableModules.disableSelection();

    /**
     * Returns the name of the module instance being moved.  This is equivalent to calling the Module.toString() method, except the returned string is not
     * capitalized
     * @param ui
     * @return {*|jQuery}
     */
    function getDataModuleName(ui) {
        var dataModuleName = $(ui.item).find("section.module").attr("data-module-name");
        return dataModuleName;
    }

//    var moduleFormInputs = jQuery("section#modules-edit form input");
//    moduleFormInputs.change(function () {
//        alert($(this).attr("name"));
//    });

});

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

/**
 * Every module has JavaScript functions which handle start sorting and end sorting.  This calls them.
 * @param functionName
 * @param ui
 */
function callModuleSortMethod(functionName, ui) {
    var fn = window[functionName];
    if (typeof fn === 'function') {
        fn(ui);
    }
}