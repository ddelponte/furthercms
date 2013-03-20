<r:require modules="jquery, jquery-ui"/>

<label><g:message code="plugin.furthercms.module.html.label"/></label>

%{--Takes a name and data in the model--}%
<ckeditor:config var="toolbar_Mytoolbar" enterMode="CKEDITOR.ENTER_DIV">
    [
        ['Bold', 'Italic', 'Underline', 'Link', 'Unlink', '-', 'Styles', 'Format',
            'FontSize', '-', 'TextColor','BGColor', '-', 'NumberedList', 'BulletedList']
    ]
</ckeditor:config>

<%
    Random rand = new Random()
    Integer max = Integer.MAX_VALUE - 1
    def randomInteger = rand.nextInt(max + 1)
%>

<ckeditor:editor name="${name}_${randomInteger}" height="${height ?: '100%'}" width="${width ?: '100%'}" toolbar="Mytoolbar">
    ${data}
</ckeditor:editor>