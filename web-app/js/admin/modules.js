jQuery(document).ready(function () {
    var buttonStatus = jQuery("section#modules-edit div#button-status");

    // When user clicks the save button, submit all module forms
    jQuery("section#modules-edit a.btn").click(function (event) {
        event.preventDefault();
        saveModules();
    });

    // Select all module forms and submit them
    function saveModules() {
        CKupdate();

        buttonStatus.attr("style", "visibility: visible");
        var moduleForms = jQuery("section#modules-edit form");
        var totalModuleForms = moduleForms.size();

        moduleForms.each(function (index, element) {
            event.preventDefault();

            jQuery(this).ajaxSubmit({
                success: function (jsonData) {

                    updateButtonSaveStatus(totalModuleForms, index);

                    if (jsonData.success) {

                        console.log('success');

                    } else {

                        showErrors(jsonData.errors);

                    }

                },

                error: function () {
                    updateButtonSaveStatus(totalModuleForms, index);
                    console.log('error');
                }

            });

        });

    }

    // Display 'Saving...' under the buttons.  Clear it when done.
    function updateButtonSaveStatus(totalModuleForms, index) {

        if (totalModuleForms == (index + 1)) {

            buttonStatus.attr("style", "visibility: none");

        }
    }

    function showErrors(errors, element) {

        var errorList = $("<ul>");

        for (field in errors) {

            errorList.append("<li>" + errors[field] + "</li>");
            $('input[name=\"' + field + '\"]').addClass('error');

        }

        if (!element) {

            $(".errors").html("").append(errorList).show(500);

        } else {

            $(element).html("").append(errorList).show(500);

        }
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

//    var moduleFormInputs = jQuery("section#modules-edit form input");
//    moduleFormInputs.change(function () {
//        alert($(this).attr("name"));
//    });

});