<g:each in="${moduleTypeStatusMap}" var="moduleTypeStatus">

    <ui:h1>${moduleTypeStatus?.key}</ui:h1>

    <ul id="${moduleTypeStatus?.key}ModuleTypes" class="sortable connectedSortable">

        <g:hiddenField name="${moduleTypeStatus?.key}" value="{${moduleTypeStatus?.value?.id?.collect{'"' + it + '"' + ':"' + moduleTypeStatus?.key + '"'}?.join(',')}}"/>

        <g:each in="${moduleTypeStatus?.value}" var="moduleType">

            <li class="ui-state-default" data-id="${moduleType?.id}">${moduleType}</li>

        </g:each>

    </ul>

</g:each>