jQuery(document).ready(function () {
    var buttonStatus = jQuery("section#modules-edit div#button-status");
    var totalErrors = 0;
    var totalSavedForms = 0;

    // When user clicks the save button, submit all module forms
    jQuery("section#modules-edit a.save").click(function (event) {
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

});