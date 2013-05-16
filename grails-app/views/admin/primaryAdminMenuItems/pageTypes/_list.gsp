<ui:displayMessage/>

<ui:table>

    <thead>

        <tr>

            <ui:th>${message(code: 'pageType.name.label', default: 'Name')}</ui:th>

            <ui:th>${message(code: 'pageType.description.label', default: 'Description')}</ui:th>

        </tr>

    </thead>

    <tbody>

        <g:each in="${pageTypeInstanceList}" status="i" var="pageTypeInstance">

            <ui:tr>

                <td><g:link controller="pageType" action="edit" id="${pageTypeInstance.id}">${fieldValue(bean: pageTypeInstance, field: "name")}</g:link></td>

                <td>${fieldValue(bean: pageTypeInstance, field: "description")}</td>

            </ui:tr>

        </g:each>

    </tbody>

</ui:table>