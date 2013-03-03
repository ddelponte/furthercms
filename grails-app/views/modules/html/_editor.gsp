<r:require modules="jquery, jquery-ui"/>

<label><g:message code="plugin.furthercms.module.html.label"/></label>

%{--Destroys previous instances of CKEditor.  Without this, ajax responses will not be rendered--}%
%{--Also updates ckeditor data for ajax submission--}%
<g:javascript>
    delete CKEDITOR.instances[ '${name}' ];

    function CKupdate() {
        for (instance in CKEDITOR.instances) {
            CKEDITOR.instances[instance].updateElement();
        }
    }
</g:javascript>

%{--Takes a name and data in the model--}%
<ckeditor:config var="toolbar_Mytoolbar" enterMode="CKEDITOR.ENTER_DIV">
    [
        ['Bold', 'Italic', 'Underline', 'Link', 'Unlink', '-', 'Styles', 'Format',
            'FontSize', '-', 'TextColor','BGColor', '-', 'NumberedList', 'BulletedList']
    ]
</ckeditor:config>

<ckeditor:editor name="${name}" height="${height ?: '100%'}" width="${width ?: '100%'}" toolbar="Mytoolbar">
    ${data}
</ckeditor:editor>