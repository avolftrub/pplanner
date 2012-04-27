<%@ page import="grails.util.Environment" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="main2">
    <title><g:message code="500.title"/></title>
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'errors.css')}" type="text/css">
</head>

<body>

<g:if test="${Environment.currentEnvironment == Environment.PRODUCTION}">
    <div id="list-users" class="content exception">
        <span>
            <g:message code="500.text"/>
        </span>
        <span>
            <g:message code="500.retry"/>
        </span>
        <span>
            <g:message code="500.contact.to"/>&nbsp;<a href="mailto:admin@appliedbiosystems.ru?Subject=${message(code: 'admin.letter.subj')}"><g:message code="500.contact.to.admin"/></a>
        </span>
    </div>
</g:if>
<g:else>
    <g:renderException exception="${exception}" />
</g:else>
</body>
</html>
