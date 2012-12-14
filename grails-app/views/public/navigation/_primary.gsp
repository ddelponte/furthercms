<theme:zone name="navigation">
    <nav:primary custom="true" scope="plugin.furthercms.app">
        <li class="active">
            <p:callTag tag="g:link" attrs="${[url: "urlKey"]}">
                <nav:title item="${[titleMessageCode: "code", titleDefault: 'blah']}"/>
            </p:callTag>
        </li>
    </nav:primary>
</theme:zone>