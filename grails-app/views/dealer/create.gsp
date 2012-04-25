<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="main2"/>
    <title><g:message code="dealer.action.edit.title"/></title>
    <script src="${resource(dir: "js", file: "dealers.js")}" type="text/javascript"></script>
</head>

<body>

<g:form method="post" controller="dealer" action="save" name="dealerForm">
    <g:render template="/dealer/form"/>
    <hr/>

    <div class="buttonBlock">
        <button type="submit" class="Save"><span><g:message code="save"/></span></button>
        <g:link action="list" class="Cancel SaveCancel"><span><g:message code="cancel"/></span></g:link>
    </div>
</g:form>

</body>
</html>