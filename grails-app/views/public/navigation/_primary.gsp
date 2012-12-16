<theme:zone name="navigation">
    <nav:primary custom="true" scope="plugin.furthercms.app">
        <g:each in="${primaryCategoryInstanceList}" var="primaryCategory">
            <li class="active">
                <p:callTag tag="g:link" attrs="${[url: "${primaryCategory?.category?.urlKey}"]}">
                    <nav:title item="${[titleMessageCode: "code", titleDefault: primaryCategory?.category]}"/>
                </p:callTag>
            </li>
        </g:each>
    </nav:primary>
</theme:zone>