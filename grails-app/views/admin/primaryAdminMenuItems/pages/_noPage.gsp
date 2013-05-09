%{--This is the content template to render when category or page instances have been selected for editing--}%
<section id="category_nav">

    <nav>

        <fc:navTree category="${com.merrycoders.furthercms.Category.findByUrlKey('')}"/>

    </nav>

</section>

<section id="content">

    <g:message code="this.page.may.not.be.edited"/>

</section>