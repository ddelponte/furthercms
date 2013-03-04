<div class="admin main-module">

    <g:each in="${module?.moduleData}" var="moduleData">

        <fc:htmlEditor name="modules.id_${module?.id}_version_${module?.version}_displayOrder_${module?.displayOrder}_moduleTypeId_${module?.moduleType?.id}_moduleDataId_${moduleData?.id}_moduleDataVersion_${moduleData?.version}_dataKey_${moduleData?.dataKey}" data="${module?.moduleData?.first()}"/>

    </g:each>

</div>