<theme:zone name="body">
    <ui:displayMessage/>
    <ui:h1 text="sidebar.page.body.heading"/>
    <p>Main Content</p>
</theme:zone>

<theme:zone name="sidebar">
    <ui:block title="Your profile">
        <ui:avatar user="marc@anyware.co.uk" size="50"/>
        <p>Not everybody is this ugly</p>
        <fc:navTree category="${com.merrycoders.furthercms.Category.findByUrlKey('')}"/>
    </ui:block>
</theme:zone>